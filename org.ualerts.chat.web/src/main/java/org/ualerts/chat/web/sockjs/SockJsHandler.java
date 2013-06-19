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

package org.ualerts.chat.web.sockjs;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.TextWebSocketHandlerAdapter;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.ChatTextMessage;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.concrete.ConcreteChatClient;

/**
 * socksjs message handler
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class SockJsHandler extends TextWebSocketHandlerAdapter{

  private ChatService chatService;
  private ObjectMapper mapper = new ObjectMapper();
  private ChatClient chatClient;
  
  @Override
  public void afterConnectionEstablished(final WebSocketSession session)
      throws Exception {
    super.afterConnectionEstablished(session);
    //TODO need code from Michael on how to save chatClient into 'session'
    ChatClient chatClient = new ConcreteChatClient(session);
    this.chatClient = chatClient; 
    Conversation conversation = chatService.findDefaultConversation();
    conversation.addClient(chatClient);
    chatClient.setConversation(conversation);
    
    if(chatClient.getMissedMessages().size() > 0)
    {
      sendMissedMessages(chatClient.getMissedMessages());
    }
  }
  
 
  private void sendMissedMessages(List<Message> missedMessages)
  {
    for(Message missedMessage : missedMessages)
    {
      this.chatClient.getConversation().deliverMessage(missedMessage);
    }
  }
  
  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
    String data = message.getPayload();
    System.out.println("Got " + data);
    this.chatClient.getConversation().deliverMessage(mapper.readValue(data, ChatTextMessage.class));
    
  }

  public ChatClient getChatClient() {
    return chatClient;
  }

  public void setChatClient(ChatClient chatClient) {
    this.chatClient = chatClient;
  }
  
 @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }

}
