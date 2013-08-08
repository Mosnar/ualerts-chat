/*
 * File created on Jun 17, 2013
 *
 * Copyright 2008-2013 Virginia Polytechnic Institute and State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ualerts.chat.service.api.concrete;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.RosterAddedMessage;
import org.ualerts.chat.service.api.RosterContentMessage;
import org.ualerts.chat.service.api.RosterRemovedMessage;
import org.ualerts.chat.service.api.UserIdentifier;

/**
 * The default conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */

public class ConcreteConversation implements Conversation {

  private static final Logger logger = LoggerFactory
      .getLogger(ConcreteConversation.class);
  private static final String BROADCAST_MESSAGE = "all@";

  private Set<Participant> participants = new HashSet<Participant>();

  private DateTimeService dateTimeService;

  private String name;
  private boolean defaultConversation;
  private boolean isPrivate;

  @Override
  public void addParticipant(Participant participant) {
    if (participant == null)
      return;

    Participant searchParticipant =
        findParticipant(participant.getUserName().getName());
    if (searchParticipant == null) {
      this.participants.add(participant);
      participant.setConversation(this);
    }
    else {
      participant = searchParticipant;
    }
    String name = participant.getChatClient().getUserName();
    String fullyQualifiedName = participant.getUserName().getFullIdentifier();

    // send a message to all participants announcing user joining
    participant.setStatus(Status.ONLINE);
    if (defaultConversation) {
      Message rosterMessage =
          getRosterAddedMessage(fullyQualifiedName, BROADCAST_MESSAGE
              + this.name);
      deliverMessage(rosterMessage);
    }

    // send a message to the newly joined user announcing the presence of the
    // other users
    if (defaultConversation) {
      Message replyMessage;
      for (Participant thisParticipant : participants) {
        if (thisParticipant.getUserName() != UserIdentifier.NULL_USER) {
          if (!thisParticipant.getUserName().matches(name)) {
            replyMessage =
                getRosterContentMessage(thisParticipant.getUserName()
                    .getFullIdentifier(), fullyQualifiedName);
            deliverMessage(replyMessage);
          }
        }
      }
    }
  }

  @Override
  public void removeParticipant(Participant participant) {
    if (participants.contains(participant)) {
      participants.remove(participant);
    }

    String userName = participant.getChatClient().getUserName();

    Message message =
        getRosterRemovedMessage(userName, BROADCAST_MESSAGE + this.name);
    deliverMessage(message);
  }

  @Override
  public void deliverMessage(Message message) {
    if (message.getTo().equalsIgnoreCase(BROADCAST_MESSAGE + this.name)) {
      for (Participant participant : getParticipants()) {
        if (participant.getStatus() == Status.ONLINE) {
          deliverParticipantMessage(participant, message);
        }
      }
    }
    else {

      Participant toParticipant = findParticipant(parseName(message.getTo()));
      Participant fromParticipant =
          findParticipant(parseName(message.getFrom()));
      deliverParticipantMessage(toParticipant, message);
      deliverParticipantMessage(fromParticipant, message);
    }
  }

  protected String parseName(String fullyQualifiedName) {
    int idx = fullyQualifiedName.indexOf("@");
    return fullyQualifiedName.substring(0, idx);
  }

  protected void deliverParticipantMessage(Participant participant,
      Message message) {
    if (participant != null
        && participant.getUserName() != UserIdentifier.NULL_USER
        && participant.getStatus() == Status.ONLINE) {
      participant.deliverMessage(message);
    }
  }

  @Override
  public boolean isValidUserName(String name) {
    if (findParticipant(name) != null) {
      return false;
    }
    return true;
  }

  /*
   * Find a specific participant by name
   */
  public Participant findParticipant(String name) {
    Participant thisParticipant = null;
    for (Participant participant : participants) {
      if (participant.getUserName() == UserIdentifier.NULL_USER)
        continue;

      if (participant.getUserName().matches(name)) {
        thisParticipant = participant;
        break;
      }
    }
    return thisParticipant;
  }

  /*
   * Construct a roster message
   */
  protected Message getRosterAddedMessage(String from, String to) {
    RosterAddedMessage message = new RosterAddedMessage();
    setRosterMessageAttributes(from, to, message);

    return message;
  }

  protected Message getRosterRemovedMessage(String from, String to) {
    RosterRemovedMessage message = new RosterRemovedMessage();
    setRosterMessageAttributes(from, to, message);

    return message;
  }

  protected Message getRosterContentMessage(String from, String to) {
    RosterContentMessage message = new RosterContentMessage();
    setRosterMessageAttributes(from, to, message);
    message.setUserName(from);

    return message;
  }

  protected void setRosterMessageAttributes(String from, String to,
      Message message) {
    message.setFrom(from);
    message.setTo(to);
    message.setMessageDate(dateTimeService.getCurrentDate());
  }

  @Override
  public Set<Participant> getParticipants() {
    return new HashSet<Participant>(participants);
  }

  @Override
  public void setDateTimeService(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isDefaultConversation() {
    return this.defaultConversation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setDefaultConversation(boolean defaultConversation) {
    this.defaultConversation = defaultConversation;
  }

  /**
   * {@inheritDoc}
   */
  public void setPrivate(boolean state) {
    this.isPrivate = state;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isPrivate() {
    return this.isPrivate;
  }

}
