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

package org.ualerts.chat.service.api.concrete;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserName;

/**
 * Concrete implementation of a participant
 *
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class ConcreteParticipant implements Participant {
  
  private Conversation conversation;
  private UserName userName;
  private ChatClient chatClient;
  private Status status;
  /**
   * {@inheritDoc}
   */
  @Override
  public void setConversation(Conversation conversation) {
    this.conversation = conversation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Conversation getConversation() {
    return conversation;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserName(UserName userName) {
    this.userName = userName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UserName getUserName() {
    return userName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChatClient(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChatClient getChatClient() {
    return chatClient;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deliverMessage(Message message) {
    this.getChatClient().deliverMessage(message);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Status getStatus() {
    return status;
  }
  
}
