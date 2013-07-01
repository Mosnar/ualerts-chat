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

import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.RosterMessage;
import org.ualerts.chat.service.api.UserName;

/**
 * The default conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */

public class ConcreteConversation implements Conversation {

  private static final String BROADCAST_MESSAGE = "all";
  private static final String ROSTER_ADDED = "ROSTER_ADDED";
  private static final String ROSTER_CONTENTS = "ROSTER_CONTENT";
  private static final String ROSTER_REMOVE = "ROSTER_REMOVED";

  private Set<Participant> participants = new HashSet<Participant>();
  private DateTimeService dateTimeService;

  @Override
  public void addParticipant(Participant participant) {
    this.participants.add(participant);
    participant.setConversation(this);
  }

  @Override
  public void removeParticipant(Participant participant) {
    if (participants.contains(participant)) {
      participants.remove(participant);
    }
  }

  @Override
  public void deliverMessage(Message message) {
    if (message.getTo().equalsIgnoreCase(BROADCAST_MESSAGE)) {
      for (Participant participant : participants) {
        deliverParticipantMessage(participant, message);
      }
    }
    else {
      Participant toParticipant = findParticipant(message.getTo());
      Participant fromParticipant = findParticipant(message.getFrom());
      deliverParticipantMessage(toParticipant, message);
      message.setTo(fromParticipant.getUserName().getName());
      deliverParticipantMessage(fromParticipant, message);
    }
  }
  
  private void deliverParticipantMessage(Participant participant, Message message) {
    if(participant != null && participant.getUserName() != UserName.NULL_USER
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
  private Participant findParticipant(String name) {
    Participant thisParticipant = null;
    for (Participant participant : participants) {
      if (participant.getUserName() == UserName.NULL_USER)
        continue;

      if (participant.getUserName().matches(name)) {
        thisParticipant = participant;
        break;
      }
    }
    return thisParticipant;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void finalizeRegisterParticipant(String name) {

    Participant participant = findParticipant(name);
    if (participant != null) {
      // send a message to all particpants announcing user joining
      participant.setStatus(Status.ONLINE);
      Message rosterMessage =
          getRosterMessage(name, BROADCAST_MESSAGE, ROSTER_ADDED);
      deliverMessage(rosterMessage);

      // send a message to the newly joined user announcing the presence of the
      // other users
      Message replyMessage;
      for (Participant thisParticipant : participants) {
        if (thisParticipant.getUserName() != UserName.NULL_USER) {
          if(!thisParticipant.getUserName().getName().matches(name)){
          replyMessage =
              getRosterMessage(thisParticipant.getUserName().getName(), name,
                  ROSTER_CONTENTS);
          deliverMessage(replyMessage);
        }
      }}
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void finalizeRemoveParticipant(String userName) {
    Participant participant = findParticipant(userName);
    removeParticipant(participant);
    Message message =
        getRosterMessage(userName, BROADCAST_MESSAGE, ROSTER_REMOVE);
    deliverMessage(message);
  }

  /*
   * Construct a roster message
   */
  public Message getRosterMessage(String from, String to, String type) {
    RosterMessage message = new RosterMessage();
    message.setFrom(from);
    message.setTo(to);
    message.setType(type);
    message.setMessageDate(dateTimeService.getCurrentDate());

    return message;
  }

  @Override
  public Set<Participant> getParticipants() {
    return participants;
  }

  @Override
  public void setDateTimeService(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

}
