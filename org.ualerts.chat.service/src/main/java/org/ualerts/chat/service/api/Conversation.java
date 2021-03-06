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

package org.ualerts.chat.service.api;

import java.util.Set;

import org.ualerts.chat.service.api.message.Message;

/**
 * Represents a conversation between chat participants
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */
public interface Conversation {

  /**
   * Add a chat participant to a conversation
   * @param chatClient
   */
  boolean addParticipant(Participant participant);

  /**
   * Remove a chat participant from a conversation
   * @param chatClient
   */
  void removeParticipant(Participant participant);

  /**
   * Returns a Set of Participants associated with this Conversation
   * @return Set of Participant
   */
  Set<Participant> getParticipants();

  /**
   * Deliver a message to chat participants participating in this conversation
   * @param the message to be delivered
   */
  void deliverMessage(Message message);

  /**
   * Indicates a valid user name. A valid user name is one that has not been
   * assigned to a participant yet.
   * @return boolean indicator
   */
  boolean isValidUserName(String userName);

  /**
   * Provide consistent DateTimeService
   * @param dateTimeService
   */
  void setDateTimeService(DateTimeService dateTimeService);

  /**
   * Return domain name
   * @return the Conversation's Domain
   */
  String getName();

  /**
   * Sets the private indicator
   */
  void setPrivate(boolean state);

  /**
   * Attempts to find a participant in the conversation.
   * @param name name of participant
   * @return participant or null
   */
  Participant findParticipant(UserIdentifier userIdentifier);

  /**
   * Gets the private indicator
   */
  boolean isPrivate();
}
