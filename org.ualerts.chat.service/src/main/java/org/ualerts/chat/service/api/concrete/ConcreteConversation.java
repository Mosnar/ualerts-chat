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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.ualerts.chat.service.api.ConcreteDateTimeService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.RosterAddedMessage;
import org.ualerts.chat.service.api.RosterRemovedMessage;
import org.ualerts.chat.service.api.UserName;

/**
 * The default conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */

public class ConcreteConversation implements Conversation {

  private DateTimeService dateTimeService = new ConcreteDateTimeService();
  private Set<Participant> participants = new HashSet<Participant>();
  private Map<String, Participant> participantsMap =
      new HashMap<String, Participant>();

  @Override
  public void addParticipant(Participant participant) {
    this.participants.add(participant);
    this.participantsMap
        .put(participant.getUserName().getName(), participant);
    participant.setConversation(this);

  }

  @Override
  public void removeParticipant(Participant participant) {

    if (participants.contains(participant)) {
      participants.remove(participant);
    }
    if (participantsMap.containsKey(participant.getUserName().getName())) {
      participantsMap.remove(participant.getUserName().getName());
    }
  }

  @Override
  public void deliverMessage(Message message) {

    for (Participant participant : participants) {
      if (participant.getUserName() != UserName.NULL_USER) {
        participant.deliverMessage(message);
      }
    }
  }

  @Override
  public Message getRosterAddedMessage(String userName) {
    RosterAddedMessage message = new RosterAddedMessage();
    message.setMessageDate(dateTimeService.getCurrentDate());
    message.setText(userName);
    message.setFrom(userName);

    return message;
  }

  @Override
  public Message getRosterRemovedMessage(String userName) {
    RosterRemovedMessage message = new RosterRemovedMessage();
    message.setMessageDate(dateTimeService.getCurrentDate());
    message.setText(userName);
    message.setFrom(userName);

    return message;
  }

  @Override
  public Set<Participant> getParticipants() {
    return participants;
  }

  public Map<String, Participant> getParticipantsMap() {
    return participantsMap;
  }

  @Override
  public boolean isValidUserName(String userName) {
    if (participantsMap.containsKey(userName)) {
      return false;
    }
    return true;
  }

}
