/*
 * File created on Jun 25, 2013
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

/**
 * Representing a participant of a conversation
 *
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public interface Participant {
  
  public enum Status {
    SETUP, ONLINE;
  }
  
  /**
   * Provide a reference to the conversation this participant
   * is a member of
   * @param conversation
   */
  void setConversation(Conversation conversation);
  
  /**
   * Provide access to a participant's conversation
   * @return the conversation this participant is a member of
   */
  Conversation getConversation();
  
  /**
   * Represents a participant's screen name
   * @param userName
   */
  void setUserName(UserName userName);
  
  /**
   * Retrieve a participant's user name
   * @return
   */
  UserName getUserName();
  
  /**
   * Provide a reference to a participant's chat session
   * @param chatClient
   */
  void setChatClient(ChatClient chatClient);

  /**
   * Returns the associated ChatClient object
   * @return the ChatClient associated with a participant
   */
  ChatClient getChatClient();
  
  /**
   * Provides facility to deliver a message
   * @param message to deliver
   */
  void deliverMessage(Message message);
  
  /**
   * Retrieve a Particpant's status
   * @param status
   */
  void setStatus(Status status);
  
  /**
   * Provide access to a Particpant's status
   * @return the particpant's Status
   */
  Status getStatus();
  
  
}
