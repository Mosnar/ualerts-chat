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

package org.ualerts.chat.spring;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.server.endpoint.SpringConfigurator;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.TextMessage;

/**
 * WebSocket message handler
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
@ServerEndpoint(value = "/ws", configurator = SpringConfigurator.class)
public class WsEndpoint {

  private ChatService chatService;
  private ObjectMapper mapper = new ObjectMapper();

  @OnMessage
  public void onMessage(Session session, String message) throws Exception {
    TextMessage textMessage = mapper.readValue(message, TextMessage.class);
    Conversation conversation = chatService.findDefaultConversation();
    conversation.deliverMessage(textMessage);
  }

  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }

}
