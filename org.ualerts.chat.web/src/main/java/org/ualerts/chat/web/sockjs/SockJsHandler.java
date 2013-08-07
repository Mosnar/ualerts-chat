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

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.adapter.TextWebSocketHandlerAdapter;
import org.springframework.web.util.HtmlUtils;
import org.ualerts.chat.service.api.ChatClientContext;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.ChatTextMessage;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.concrete.ConcreteParticipant;

/**
 * socksjs message handler
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class SockJsHandler extends TextWebSocketHandlerAdapter {

  private ObjectMapper mapper = new ObjectMapper();
  private SockJsChatClient chatClient;
  private ChatClientContext chatClientContext;
  private DateTimeService dateTimeService;
  private Participant participant;
  private ChatService chatService;

  @Override
  public void afterConnectionEstablished(final WebSocketSession session)
      throws Exception {
    super.afterConnectionEstablished(session);

    chatClient = getChatClient();
    if (chatClient.getParticipant() == null) {
      participant = getParticipant();
      participant.setChatClient(chatClient);
      participant.setUserName(UserIdentifier.NULL_USER);
      participant.setStatus(Status.SETUP);
      chatClient.setParticipant(participant);
    }
    chatClient.setSession(session);
  }

  private Participant getParticipant() {

    if (chatClient.getParticipant() == null) {
      participant = new ConcreteParticipant();
      participant.setChatClient(chatClient);
      participant.setUserName(UserIdentifier.NULL_USER);
      participant.setStatus(Status.SETUP);
      chatClient.setParticipant(participant);
    }
    else {
      participant = chatClient.getParticipant();
    }
    return participant;
  }

  private SockJsChatClient getChatClient() {
    if (chatClientContext.getChatClient() == null) {
      chatClientContext.setChatClient(new ConcreteChatClient());
    }
    return (SockJsChatClient) chatClientContext.getChatClient();
  }

  @Override
  public void handleTextMessage(WebSocketSession session, TextMessage message)
      throws Exception {
    ChatTextMessage chatMessage =
        mapper.readValue(message.getPayload(), ChatTextMessage.class);
    chatMessage.setMessageDate(dateTimeService.getCurrentDate());
    chatMessage.setText(HtmlUtils.htmlEscape(chatMessage.getText()));
    Conversation conversation = 
        chatService.getConversation(getUserIdentifier(chatMessage.getFrom(), chatMessage.getTo()));
    if (conversation != null) {
      conversation.deliverMessage(chatMessage);
    }

  }

  /**
   * Get a UserIdentifier based on a message from address
   * @param the fullyQualified from address
   * @return a UserIdentifier
   */
  private UserIdentifier getUserIdentifier(String from, String to) {
    int idx = from.indexOf("@");
    int idx2 = to.indexOf("@");
    return new UserIdentifier(from.substring(0, idx), to.substring(idx2 + 1));

  }

  @Override
  public void afterConnectionClosed(WebSocketSession session,
      CloseStatus status) throws Exception {
    chatClient.setSession(session);
    participant = chatClient.getParticipant();
    Conversation conversation = participant.getConversation();
    conversation.removeParticipant(participant);
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

  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }
}
