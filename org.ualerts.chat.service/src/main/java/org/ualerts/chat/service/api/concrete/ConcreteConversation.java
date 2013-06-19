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

import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;

/**
 * The default conversation
 * @author Billy Coleman1
 * @author Ransom Roberson
 * 
 */
public class ConcreteConversation implements Conversation {

  private Set<ChatClient> clients = new HashSet<ChatClient>();

  @Override
  public void addClient(ChatClient chatClient) {
    this.clients.add(chatClient);
  }

  @Override
  public void removeClient(ChatClient chatClient) {

    if (clients.contains(chatClient)) {
      clients.remove(chatClient);
    }
  }

  @Override
  public void deliverMessage(Message message) {

    for (ChatClient client : clients) {
      client.deliverMessage(message);
    }

  }

  @Override
  public Set<ChatClient> getChatClients() {
    return clients;
  }

}
