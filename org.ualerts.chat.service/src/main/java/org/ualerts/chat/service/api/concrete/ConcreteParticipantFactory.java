/*
e * File created on Aug 12, 2013
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

import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.ParticipantFactory;
import org.ualerts.chat.service.api.UserIdentifier;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Brandon Foster
 */
public class ConcreteParticipantFactory implements ParticipantFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public Participant newParticipant(Status status, ChatClient chatClient,
      UserIdentifier userName) {
    Participant participant = new ConcreteParticipant();
    participant.setStatus(Status.INVITED);
    participant.setChatClient(chatClient);
    participant.setUserName(userName);
    return participant;
  }
}
