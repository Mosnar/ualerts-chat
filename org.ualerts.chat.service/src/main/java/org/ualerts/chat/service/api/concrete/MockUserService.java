/*
 * File created on Jul 23, 2013
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatClientContext;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.UserName;
import org.ualerts.chat.service.api.UserNameConflictException;
import org.ualerts.chat.service.api.UserService;

/**
 * A mock implementation of the UserService interface.
 *
 * @author Ransom Robersom
 * @author Brandon Foster
 */
@Service
public class MockUserService implements UserService {
  private ChatClientContext chatClientContext;
  private Set<ChatClient> chatClients = new HashSet<ChatClient>();
  private ChatService chatService;
  private static String DEFAULT_DOMAIN = "ualerts.org";
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setUserName(String name) throws UserNameConflictException /*throws UserNameConflictException*/ {
    if (this.findClient(name) != null) {
      throw new UserNameConflictException("Name already in use");
    }
    ChatClient chatClient;
    chatClient = chatClientContext.getChatClient();
    chatClient.getParticipant().setUserName(new UserName(name));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String login() {
    ChatClient  chatClient = chatClientContext.getChatClient();
    String userName = chatClient.getParticipant().getUserName().getName();
    if (this.findClient(userName) != null) {
      return userName + "@" + DEFAULT_DOMAIN;
    }
    chatClients.add(chatClient);
    chatClient.getParticipant().setConversation(chatService.findDefaultConversation());
    chatService.findDefaultConversation().finalizeRegisterParticipant(userName);
    return userName + "@" + DEFAULT_DOMAIN;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void logout() {
    ChatClient  chatClient = chatClientContext.getChatClient();
    chatClients.remove(chatClient);
    String userName = chatClient.getParticipant().getUserName().getName();
    chatService.findDefaultConversation().finalizeRemoveParticipant(userName);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChatClient findClient(String name) {
    for (ChatClient client: chatClients) {
      if (client.getParticipant().getUserName().getName().equals(name)) {
        return client;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ChatClient findClientById(String id) {
    for (ChatClient client: chatClients) {
      if (client.getParticipant().getUserName().getName().equals(id)) {
        return client;
      }
    }
    return null;
  }

 
  public void setChatClientContext(ChatClientContext chatClientContext) {
    this.chatClientContext = chatClientContext;
  }
  
  
  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }
  
}
