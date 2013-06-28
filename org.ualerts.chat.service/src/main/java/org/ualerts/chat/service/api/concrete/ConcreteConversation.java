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
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserName;

/**
 * The default conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */

public class ConcreteConversation implements Conversation {

  private Set<Participant> participants = new HashSet<Participant>();

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

    for (Participant participant : participants) {
      if (participant.getUserName() != UserName.NULL_USER) {
        participant.deliverMessage(message);
      }
    }
  }

  @Override
  public boolean isValidUserName(String userName) {
    boolean valid = true;

    for (Participant participant : participants) {
      if (participant.getUserName() == UserName.NULL_USER)
        continue;

      if (participant.getUserName().getName().trim()
          .equalsIgnoreCase(userName)) {
        valid = false;
        break;
      }

    }
    return valid;
  }

  @Override
  public Set<Participant> getParticipants() {
    return participants;
  }

}
