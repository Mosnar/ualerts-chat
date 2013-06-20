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

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.TextWebSocketHandlerAdapter;
import org.springframework.web.util.HtmlUtils;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.ChatTextMessage;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.web.context.ChatClientContext;

/**
 * socksjs message handler
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class SockJsHandler extends TextWebSocketHandlerAdapter {
  
  private ChatService chatService;
  private ObjectMapper mapper = new ObjectMapper();
  private SockJsChatClient chatClient;
  private ChatClientContext chatClientContext;
  private DateTimeService dateTimeService;

  @Override
  public void afterConnectionEstablished(final WebSocketSession session)
      throws Exception {
    super.afterConnectionEstablished(session);

    chatClient = getChatClient();
    chatClient.setSession(session);
    Conversation conversation = chatService.findDefaultConversation();
    conversation.addClient(chatClient);

    if (chatClient.getMissedMessages().size() > 0) {
      sendMissedMessages(chatClient.getMissedMessages());
    }
  }
  
  private SockJsChatClient getChatClient() {
    if (chatClientContext.getChatClient() == null) {
      chatClientContext.setChatClient(new ConcreteChatClient());
    }
    return (SockJsChatClient) chatClientContext.getChatClient();
  }

  private void sendMissedMessages(List<Message> missedMessages) {
    for (Message missedMessage : missedMessages) {
      this.chatClient.getConversation().deliverMessage(missedMessage);
    }
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    ChatTextMessage chatMessage = 
        mapper.readValue(message.getPayload(), ChatTextMessage.class);
    chatMessage.setMessageDate(dateTimeService.getCurrentDate());
    chatMessage.setText( HtmlUtils.htmlEscape(chatMessage.getText()) );
    this.chatClient.getConversation().deliverMessage(chatMessage);
  }

  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }

  /**
   * Sets the {@code chatClientContext} property.
   * @param chatClientContext the value to set
   */
  @Autowired
  public void setChatClientContext(ChatClientContext chatClientContext) {
    this.chatClientContext = chatClientContext;
  }

  /**
   * Sets the {@code dateTimeService} property.
   * @param dateTimeService the value to set
   */
  @Autowired
  public void setDateTimeService(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }
}
