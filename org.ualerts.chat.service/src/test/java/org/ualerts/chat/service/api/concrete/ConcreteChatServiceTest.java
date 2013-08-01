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

import static org.junit.Assert.assertNotNull;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatClientContext;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserIdentifier;

public class ConcreteChatServiceTest {

  private Mockery context;
  private ConcreteChatService chatService;
  private UserIdentifier userIdentity;
  private DateTimeService dateTimeService;
  private ConversationFactory convoFactory;
  private ChatClient chatClient;
  private ChatClientContext chatClientContext;
  private Conversation conversation;
  private ConcreteUserService userService;


  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    userIdentity = new UserIdentifier("name", "ualerts.org");
    
    dateTimeService =
        context.mock(DateTimeService.class);
    convoFactory =
        context.mock(ConversationFactory.class);
    chatClient = context.mock(ChatClient.class);
    conversation = context.mock(Conversation.class);
    
    chatService = new ConcreteChatService(dateTimeService);
    chatService.setConcreteConversationFactory(convoFactory);
    userService = new ConcreteUserService();
    
    userService.setChatClientContext(chatClientContext);
    userService.setChatService(chatService);
    chatService.setUserService(userService);
  }

  @Test
  public void testJoinConversationNoConversation() {

    context.checking(new Expectations() {
      {
        exactly(1).of(convoFactory).createConversation(
            with(any(UserIdentifier.class)));
        will(returnValue(conversation));        
        exactly(1).of(conversation).addParticipant(with(any(Participant.class)));
        atLeast(0).of(conversation).getName();
        will(returnValue(userIdentity.getDomain()));
      }
    });

    chatService.joinConversation(userIdentity);
    context.assertIsSatisfied();
    assertNotNull(chatService.getConversation(userIdentity));
  }
  
  @Test
  public void testJoinConversationWithConversation() {    
    context.checking(new Expectations() {
      {
        exactly(1).of(convoFactory).createConversation(
            with(any(UserIdentifier.class)));
        will(returnValue(conversation));        
        exactly(1).of(conversation).addParticipant(with(any(Participant.class)));
        atLeast(0).of(conversation).getName();
        will(returnValue(userIdentity.getDomain()));
      }
    });

    chatService.createConversation(userIdentity);
    chatService.joinConversation(userIdentity);
    context.assertIsSatisfied();
    assertNotNull(chatService.getConversation(userIdentity));
  }
}