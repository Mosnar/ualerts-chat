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

import org.omg.CORBA.UnknownUserException;
import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatClientContext;
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
  private ChatClientContext chatClientContext;

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
  public void joinConversation(UserIdentifier userIdentifier) {
    Conversation conversation = getConversation(userIdentifier);
    if (conversation != null) {
      Participant participant = conversation.findParticipant(userIdentifier);
      if (participant != null) {
        return;
      }
      // If the conversation isn't private or the user is invited, connect them
      if (!conversation.isPrivate() || canJoin(participant)) {
        participant = generateParticipant(userIdentifier, false);
        conversation.addParticipant(participant);
      }
    } else {
      conversation = createConversation(userIdentifier, false);
      conversation.setPrivate(false);
      Participant participant = generateParticipant(userIdentifier, false);
      conversation.addParticipant(participant);
    }
  }

  /**
   * Creates a participant based on given parameters
   * @param userIdentifier
   * @param isAdmin
   * @return Participant
   */
  protected Participant generateParticipant(UserIdentifier userIdentifier,
      boolean isAdmin) {
    Participant participant;
    ChatClient chatClient =
        this.userService.findClient(userIdentifier.getName());

    participant = new ConcreteParticipant();
    participant.setChatClient(chatClient);
    participant.setUserName(userIdentifier);
    participant.setStatus(Status.ONLINE);
    return participant;
  }

  /**
   * Returns true if the user is allowed to join a conversation
   * @param participant
   * @return boolean
   */
  protected boolean canJoin(Participant participant) {
    return (participant != null && (participant.getStatus() == Status.INVITED || participant
        .isAdmin()));
  }

  /**
   * Create a new conversation and adds it to the collection
   * @param userIdentifier
   * @return The created conversation
   */
  public Conversation createConversation(UserIdentifier userIdentifier,
      boolean privateConversation) {
    Conversation conversation = getConversation(userIdentifier);
    if (conversation == null) {
      // No conversation exists, make a new one
      conversation =
          conversationFactory.newConversation(userIdentifier,
              true);
      conversations.add(conversation);
      conversation.setPrivate(privateConversation);
    }
    joinConversation(userIdentifier);
    return conversation;
  }

  /**
   * 
   * {@inheritDoc}
   */
  public void inviteUser(UserIdentifier userIdentifier) throws UserException {
    ChatClient chatClient = userService.findClient(userIdentifier.getName());
    if (chatClient != null) {
      Conversation conversation = getConversation(userIdentifier);
      // TODO: Do something if the conversation is null. Throw error?
      if (conversation != null) {
        // If the conversation exists, I'm in it, and I'm an admin or convo is
        // public, send the invite
        Participant participantOriginal =
            conversation.findParticipant(new UserIdentifier(chatClientContext
                .getChatClient().getUserName(), userIdentifier.getDomain()));
        if (participantOriginal != null
            && (participantOriginal.isAdmin() || !conversation.isPrivate())) {
          Participant participant = new ConcreteParticipant();
          participant.setStatus(Status.INVITED);
          participant.setChatClient(chatClient);
          participant.setUserName(userIdentifier);
          conversation.addParticipant(participant);
          
          InviteMessage invite = new InviteMessage();
          invite.setFrom(userIdentifier.getName());
          invite.setSubType(userIdentifier.getDomain());
          invite.setTo(userIdentifier.getFullIdentifier());
          invite.setUserIdentifier(userIdentifier.getFullIdentifier());
          chatClient.deliverMessage(invite);

          conversation.addParticipant(participant);
        }
      }
    }
    else {
      throw new UnknownUserException();
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

  @Autowired
  public void setChatClientContext(ChatClientContext chatClientContext) {
    this.chatClientContext = chatClientContext;
  }

}
