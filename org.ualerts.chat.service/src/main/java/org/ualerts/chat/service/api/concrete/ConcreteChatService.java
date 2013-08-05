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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.InviteMessage;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;

/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * @author Brandon Foster
 * 
 */
@Service
public class ConcreteChatService implements ChatService {

  private Set<Conversation> conversations = new HashSet<Conversation>();
  private DateTimeService dateTimeService;
  private UserService userService;
  private ConversationFactory conversationFactory;

  @Autowired
  public ConcreteChatService(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Conversation getConversation(UserIdentifier userIdentifier) {
    for (Conversation conversation : conversations) {
      if (conversation.getName().equals(userIdentifier.getDomain())) {
        return conversation;
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void joinConversation(UserIdentifier userIdentifier, boolean defaultConversation) {
    Conversation conversation = getConversation(userIdentifier);
    if (conversation == null) {
      conversation = createConversation(userIdentifier, defaultConversation);
    }

    ChatClient chatClient = this.userService.findClient(userIdentifier.getName());
    Participant participant = new ConcreteParticipant();
    participant.setUserName(userIdentifier);
    participant.setConversation(conversation);
    participant.setChatClient(chatClient);
    conversation.addParticipant(participant);
    chatClient.setParticipant(participant);
  }
  
  /**
   * Create a new conversation and adds it to the collection
   * @param userIdentifier
   * @return The created conversation
   */
  public Conversation createConversation(UserIdentifier userIdentifier, boolean defaultConversation) {
    Conversation conversation = conversationFactory.newConversation(userIdentifier, defaultConversation);
    conversations.add(conversation);
    return conversation;
  }
  
  /**
   * 
   * {@inheritDoc}
   */
  public void inviteUser(UserIdentifier userIdentifier) {
    ChatClient chatClient = userService.findClient(userIdentifier.getName());
    // For now, if we can't find the user, dismiss the problem
    if (chatClient != null)
    {
      Participant participant = new ConcreteParticipant();
      participant.setStatus(Status.INVITED);
      participant.setChatClient(chatClient);
      participant.setUserName(userIdentifier);
      
      InviteMessage invite = new InviteMessage();
      invite.setFrom(userIdentifier.getName());
      
      UserIdentifier generalId = new UserIdentifier(userIdentifier.getName(), "all");
      invite.setTo(generalId.getFullIdentifier());
      invite.setUserIdentifier(userIdentifier.getFullIdentifier());
      chatClient.deliverMessage(invite);
    }
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }

  @Autowired
  public void setConcreteConversationFactory(
      ConversationFactory conversationFactory) {
    this.conversationFactory = conversationFactory;
  }
}
