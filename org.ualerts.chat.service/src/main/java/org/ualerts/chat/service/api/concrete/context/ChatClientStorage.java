/*
 * File created on Jul 30, 2013
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

package org.ualerts.chat.service.api.concrete.context;

import org.ualerts.chat.service.api.ChatClient;


/**
 * Provides a reference to a ChatClient
 *
 * @author Brian Early
 */
public class ChatClientStorage {
  private ChatClient chatClient;

  /**
   * Gets the {@code chatClient} property.
   * @return property value
   */
  public ChatClient getChatClient() {
    return chatClient;
  }

  /**
   * Sets the {@code chatClient} property.
   * @param chatClient the value to set
   */
  public void setChatClient(ChatClient chatClient) {
    this.chatClient = chatClient;
  }

}
