/*
 * File created on Jul 5, 2013
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

package org.ualerts.chat.web.controller;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserName;
import org.ualerts.chat.service.api.concrete.ConcreteConversation;
import org.ualerts.chat.service.api.concrete.ConcreteParticipant;
import org.ualerts.chat.web.context.ChatClientContext;
import org.ualerts.chat.web.sockjs.SockJsChatClient;

/**
 * This test will validate the NameCheckController
 * 
 * @author Ransom Roberson
 */
public class NameCheckControllerTest {
  private Mockery context;
  private NameCheckController nameCheckController;
  private Participant participant;
  private Participant participant2;
  private Conversation conversation;
  
  private final String USER_NAME1 = "Test1";
  private final String USER_NAME2 = "Test2";
  private final String VALID = "{\"result\":\"valid\"}";    
  private final String INVALID = "{\"result\":\"invalid\"}";;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    conversation = new ConcreteConversation();
    
    participant = new ConcreteParticipant();  
    participant.setUserName(new UserName(USER_NAME1));
    participant.setConversation(conversation);
    conversation.addParticipant(participant);

    participant2 = new ConcreteParticipant();  
    participant2.setUserName(new UserName(USER_NAME2));
    participant2.setConversation(conversation);
    conversation.addParticipant(participant2);
    
    nameCheckController = new NameCheckController();
  }

  @Test
  public void testCheckNameValid() throws Exception {
     final ChatClientContext chatClientContext = context.mock(ChatClientContext.class);
     final SockJsChatClient chatClient = context.mock(SockJsChatClient.class);
     
     nameCheckController.setChatClientContext(chatClientContext);     

     context.checking(new Expectations() {
      {
        oneOf(chatClientContext).setChatClient(chatClient);

        oneOf(chatClientContext).getChatClient();
        will(returnValue(chatClient));
        
        oneOf(chatClient).getParticipant();
        will(returnValue(participant));
      }
    });
    
    chatClientContext.setChatClient(chatClient);
     
    assertEquals(VALID,
        nameCheckController.checkName("Test"));
    context.assertIsSatisfied();
  }
  
  
  @Test
  public void testCheckNameInvalid() throws Exception {
     final ChatClientContext chatClientContext = context.mock(ChatClientContext.class);
     final SockJsChatClient chatClient = context.mock(SockJsChatClient.class);
     
     nameCheckController.setChatClientContext(chatClientContext);     

     context.checking(new Expectations() {
      {
        oneOf(chatClientContext).setChatClient(chatClient);

        oneOf(chatClientContext).getChatClient();
        will(returnValue(chatClient));
        
        oneOf(chatClient).getParticipant();
        will(returnValue(participant));
      }
    });
    
    chatClientContext.setChatClient(chatClient);
     
    assertEquals(INVALID,
        nameCheckController.checkName("Test2"));
    context.assertIsSatisfied();
  }

}
