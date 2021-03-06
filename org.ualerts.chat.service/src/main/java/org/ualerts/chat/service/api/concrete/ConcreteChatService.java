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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatClientContext;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.ParticipantFactory;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;
import org.ualerts.chat.service.api.message.InviteMessage;
import org.ualerts.chat.service.api.message.MessageFactory;

/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * @author Brandon Foster
 * 
 */
@Service
public class ConcreteChatService implements ChatService {

  //private static final Logger logger = LoggerFactory.getLogger(ConcreteChatService.class);
  
  private MessageFactory messageFactory;
  private ParticipantFactory participantFactory;
  private Set<Conversation> conversations = new HashSet<Conversation>();
  private UserService userService;
  private ConversationFactory conversationFactory;
  private ChatClientContext chatClientContext;

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
    createAndJoinConversation(userIdentifier, false, false);
  }

  /**
   * Create a new conversation and adds it to the collection
   * @param userIdentifier
   * @return The created conversation
   */
  public Conversation createConversation(UserIdentifier userIdentifier,
      boolean privateConversation) {
    return createAndJoinConversation(userIdentifier, privateConversation,
        true);
  }

  /**
   * Method used to create a conversation for the provided UserIdentifier, if
   * one does not already exist. Once a Conversation is obtained, the provided
   * UserIdentifier is added to the list of Participants in the Conversation.
   * 
   * If the Conversation is created, the privateConversation flag is used to
   * flag the conversation as either public or private.
   * 
   * If the conversation is created, the isAdmin flag is used to determine if
   * the newly created Participant is an admin in the new Conversation. If a
   * Conversation already existed and has participants in it, the isAdmin flag
   * is changed to always be false.
   * 
   * @param userIdentifier The identifier for the user to join
   * @param privateConversation If a new conversation is needed, should it be
   *        private?
   * @param isAdmin Should the new participant be an admin (also see method doc)
   * @return The newly created or updated Conversation
   */
  private Conversation createAndJoinConversation(
      UserIdentifier userIdentifier, boolean privateConversation,
      boolean isAdmin) {
    Conversation conversation = getConversation(userIdentifier);
    if (conversation == null) {
      conversation = conversationFactory.newConversation(userIdentifier);
      conversation.setPrivate(privateConversation);
      this.conversations.add(conversation);
    }
    else {
      isAdmin = false;
    }
    Participant participant = generateParticipant(userIdentifier, isAdmin);
    conversation.addParticipant(participant);
    return conversation;
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
   * ds
   * {@inheritDoc}
   */
  public void inviteUser(UserIdentifier userIdentifier) throws UserException {
    ChatClient chatClient = userService.findClient(userIdentifier.getName());
    if (chatClient == null) {
      throw new UnknownUserException();
    }
    Conversation conversation = getConversation(userIdentifier);
    if (conversation == null) { // TODO Should we do something else here?
      return;
    }
    // If the conversation exists, I'm in it, and I'm an admin or convo is
    // public, send the invite
    Participant myParticipant =
        conversation.findParticipant(new UserIdentifier(chatClientContext
            .getChatClient().getUserName(), userIdentifier.getDomain()));
    if (!isAuthorized(myParticipant, conversation)) {
      throw new RuntimeException("User not authorized to send invite");
    }
    
    Participant participant =
        participantFactory.newParticipant(Status.INVITED, chatClient,
            userIdentifier);
    if (conversation.addParticipant(participant)) {
      InviteMessage invite =
          messageFactory.newInviteMessage(userIdentifier);
      chatClient.deliverMessage(invite);
    }
  }
  
  protected boolean isAuthorized(Participant participant, Conversation conversation) {
    return (participant != null
        && (participant.isAdmin() || !conversation.isPrivate()));
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

  /**
   * Sets the {@code conversations} property.
   * @param conversations the value to set
   */
  protected void setConversations(Set<Conversation> conversations) {
    this.conversations = conversations;
  }

  /**
   * Sets the {@code messageFactory} property.
   * @param messageFactory the value to set
   */
  @Autowired
  public void setMessageFactory(MessageFactory messageFactory) {
    this.messageFactory = messageFactory;
  }

  /**
   * Sets the {@code participantFactory} property.
   * @param participantFactory the value to set
   */
  @Autowired
  public void setParticipantFactory(ParticipantFactory participantFactory) {
    this.participantFactory = participantFactory;
  }
}
