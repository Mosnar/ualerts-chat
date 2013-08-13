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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.UnknownUserException;
import org.omg.CORBA.UserException;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatClientContext;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.ParticipantFactory;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;
import org.ualerts.chat.service.api.message.InviteMessage;
import org.ualerts.chat.service.api.message.MessageFactory;

public class ConcreteChatServiceTest {

  private Mockery context;
  private ConcreteChatService chatService;
  private UserIdentifier userIdentity;
  private ConversationFactory convoFactory;
  private ParticipantFactory participantFactory;
  private MessageFactory messageFactory;
  private ChatClient chatClient;
  private ChatClientContext chatClientContext;
  private Conversation conversation;
  private UserService userService;


  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    userIdentity = new UserIdentifier("name", "ualerts.org");
    
    convoFactory = context.mock(ConversationFactory.class);
    participantFactory = context.mock(ParticipantFactory.class);
    messageFactory = context.mock(MessageFactory.class);
    chatClient = context.mock(ChatClient.class);
    chatClientContext = context.mock(ChatClientContext.class);
    conversation = context.mock(Conversation.class);
    userService = context.mock(UserService.class);
    
    chatService = new ConcreteChatService();
    chatService.setChatClientContext(chatClientContext);
    chatService.setConcreteConversationFactory(convoFactory);
    chatService.setUserService(userService);
    chatService.setParticipantFactory(participantFactory);
    chatService.setMessageFactory(messageFactory);
  }
  
  @Test
  public void testGetConversationWithNoConversations() {
    assertNull(chatService.getConversation(userIdentity));
  }
  
  @Test
  public void testGetConversationsWithExistingConversation() {
    final Conversation badConvo = context.mock(Conversation.class, "badGuy");
    Set<Conversation> conversations = new HashSet<Conversation>();
    conversations.add(badConvo);
    chatService.setConversations(conversations);
    
    context.checking(new Expectations() { { 
      atLeast(1).of(badConvo).getName();
      will(returnValue(""));

      atLeast(1).of(conversation).getName();
      will(returnValue(userIdentity.getDomain()));
    } });
    Conversation convo = chatService.getConversation(userIdentity);
    assertNull(convo);
    
    conversations.add(conversation);
    convo = chatService.getConversation(userIdentity);
    
    context.assertIsSatisfied();
    assertEquals(conversation, convo);
  }
  
  private void setCreateConversationExpectations(final boolean isPrivate) {
    context.checking(new Expectations() { { 
      oneOf(convoFactory).newConversation(userIdentity);
      will(returnValue(conversation));
      
      oneOf(conversation).setPrivate(isPrivate);
      
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
      
      oneOf(conversation).addParticipant(with(any(Participant.class)));
    } });
  }
  
  @Test
  public void testJoinConversationWithNonexistingConversation() {
    setCreateConversationExpectations(false);
    
    chatService.joinConversation(userIdentity);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testCreateConversationPrivate() {
    setCreateConversationExpectations(true);
    Conversation newConversation = chatService.createConversation(userIdentity, true);
    assertEquals(conversation, newConversation);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testCreateConversationPublic() {
    setCreateConversationExpectations(false);
    Conversation newConversation = chatService.createConversation(userIdentity, false);
    assertEquals(conversation, newConversation);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testJoinExistingConversation() {
    Set<Conversation> conversations = new HashSet<Conversation>();
    conversations.add(conversation);
    chatService.setConversations(conversations);
    
    context.checking(new Expectations(){ { 
      oneOf(conversation).getName();
      will(returnValue(userIdentity.getDomain()));
      
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
      
      oneOf(conversation).addParticipant(with(any(Participant.class)));
    } });
    chatService.joinConversation(userIdentity);
    context.assertIsSatisfied();
  }
  
  @Test (expected = UnknownUserException.class)
  public void testInviteUserWithNoChatClient() throws Exception {
    context.checking(new Expectations() { {
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(null));
    } });
    
    chatService.inviteUser(userIdentity);
  }
  
  @Test
  public void testInviteUserWithNoConversation() throws Exception {
    context.checking(new Expectations() { {
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
    } });
    
    chatService.inviteUser(userIdentity);
    context.assertIsSatisfied();
  }
  
  @Test (expected = RuntimeException.class)
  public void testInviteUserWithNoParticipant() throws UserException {
    Set<Conversation> conversations = new HashSet<Conversation>();
    conversations.add(conversation);
    chatService.setConversations(conversations);
    
    context.checking(new Expectations() { { 
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
    
      allowing(conversation).getName();
      will(returnValue(userIdentity.getDomain()));
      
      oneOf(conversation).findParticipant(with(any(UserIdentifier.class)));
      will(returnValue(null));
      
      oneOf(chatClientContext).getChatClient();
      will(returnValue(chatClient));
      oneOf(chatClient).getUserName();
      will(returnValue("aUsername"));
    } });
    
    chatService.inviteUser(userIdentity);
  }
  
  @Test
  public void testInviteUserWithParticipantAdded() throws UserException {
    Set<Conversation> conversations = new HashSet<Conversation>();
    conversations.add(conversation);
    chatService.setConversations(conversations);
    
    final Participant participant = context.mock(Participant.class, "first");
    final Participant participant2 = context.mock(Participant.class, "second");
    final InviteMessage invite = new InviteMessage();
    
    
    context.checking(new Expectations() { {
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
    
      allowing(conversation).getName();
      will(returnValue(userIdentity.getDomain()));
      
      oneOf(conversation).findParticipant(with(any(UserIdentifier.class)));
      will(returnValue(participant));
      
      oneOf(chatClientContext).getChatClient();
      will(returnValue(chatClient));
      oneOf(chatClient).getUserName();
      will(returnValue("aUsername"));
      
      oneOf(participant).isAdmin();
      will(returnValue(true));
      
      oneOf(participantFactory).newParticipant(Status.INVITED, chatClient, userIdentity);
      will(returnValue(participant2));
      
      oneOf(conversation).addParticipant(participant2);
      will(returnValue(true));
      
      oneOf(messageFactory).newInviteMessage(userIdentity);
      will(returnValue(invite));
      
      oneOf(chatClient).deliverMessage(invite);
    } } );
    
    chatService.inviteUser(userIdentity);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testInviteUserWithParticipantNotAdded() throws Exception {
    Set<Conversation> conversations = new HashSet<Conversation>();
    conversations.add(conversation);
    chatService.setConversations(conversations);
    
    final Participant participant = context.mock(Participant.class, "first");
    final Participant participant2 = context.mock(Participant.class, "second");    
    
    context.checking(new Expectations() { {
      oneOf(userService).findClient(userIdentity.getName());
      will(returnValue(chatClient));
    
      allowing(conversation).getName();
      will(returnValue(userIdentity.getDomain()));
      
      oneOf(conversation).findParticipant(with(any(UserIdentifier.class)));
      will(returnValue(participant));
      
      oneOf(chatClientContext).getChatClient();
      will(returnValue(chatClient));
      oneOf(chatClient).getUserName();
      will(returnValue("aUsername"));
      
      oneOf(participant).isAdmin();
      will(returnValue(true));
      
      oneOf(participantFactory).newParticipant(Status.INVITED, chatClient, userIdentity);
      will(returnValue(participant2));
      
      oneOf(conversation).addParticipant(participant2);
      will(returnValue(false));
    } } );
    
    chatService.inviteUser(userIdentity);
    context.assertIsSatisfied();
  }

  @Test
  public void testIsAuthorizedWithNullParticipant() {
    assertFalse(chatService.isAuthorized(null, conversation));
  }
  
  @Test
  public void testIsAuthorizedWithNonAdminInPrivateConversation() {
    final Participant participant = context.mock(Participant.class);
    
    context.checking(new Expectations() { { 
      oneOf(participant).isAdmin();
      will(returnValue(false));
    
      oneOf(conversation).isPrivate();
      will(returnValue(true));
    } });
    assertFalse(chatService.isAuthorized(participant, conversation));
  }
  
  @Test
  public void testIsAuthorizedWithAdminInPrivateConversation() {
    final Participant participant = context.mock(Participant.class);
    
    context.checking(new Expectations() { { 
      oneOf(participant).isAdmin();
      will(returnValue(true));
    
      oneOf(conversation).isPrivate();
      will(returnValue(true));
    } });
    assertTrue(chatService.isAuthorized(participant, conversation));
  }
  
  @Test
  public void testIsAuthorizedWithAdminPublicConversation() {
    final Participant participant = context.mock(Participant.class);
    
    context.checking(new Expectations() { { 
      oneOf(participant).isAdmin();
      will(returnValue(true));
    
      oneOf(conversation).isPrivate();
      will(returnValue(false));
    } });
    assertTrue(chatService.isAuthorized(participant, conversation));
  }
}
