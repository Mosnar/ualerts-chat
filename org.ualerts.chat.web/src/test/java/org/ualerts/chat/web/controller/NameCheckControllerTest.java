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
import org.springframework.web.util.HtmlUtils;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserIdentifier;
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

  private final String USER_NAME1 = "Test1";
  private final String VALID = "{\"result\":\"valid\"}";    
  private final String INVALID = "{\"result\":\"invalid\"}";;

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    nameCheckController = new NameCheckController();
  }

  @Test
  public void testCheckNameValid() throws Exception {
     final ChatClientContext chatClientContext = context.mock(ChatClientContext.class);
     final SockJsChatClient chatClient = context.mock(SockJsChatClient.class);
     final Participant participant1 = context.mock(Participant.class);
     final Conversation conversation = context.mock(Conversation.class);

     context.checking(new Expectations() {
      {
        oneOf(chatClientContext).getChatClient();
        will(returnValue(chatClient));
        
        oneOf(chatClient).getParticipant();
        will(returnValue(participant1));
        
        oneOf(participant1).getConversation();
        will(returnValue(conversation));
        
        oneOf(conversation).isValidUserName(USER_NAME1);
        will(returnValue(true));
        
        UserIdentifier userName = new UserIdentifier(HtmlUtils.htmlEscape(USER_NAME1.trim()));
        oneOf(participant1).setUserName(with(equal(userName)));
      }
    });
    nameCheckController.setChatClientContext(chatClientContext);     

    assertEquals(VALID,
        nameCheckController.checkName(USER_NAME1));
    context.assertIsSatisfied();
  }
 
  @Test
  public void testCheckNameInvalid() throws Exception {
     final ChatClientContext chatClientContext = context.mock(ChatClientContext.class);
     final SockJsChatClient chatClient = context.mock(SockJsChatClient.class);
     final Participant participant1 = context.mock(Participant.class);
     final Conversation conversation = context.mock(Conversation.class);

     context.checking(new Expectations() {
      {
        oneOf(chatClientContext).getChatClient();
        will(returnValue(chatClient));
        
        oneOf(chatClient).getParticipant();
        will(returnValue(participant1));
        
        oneOf(participant1).getConversation();
        will(returnValue(conversation));
        
        oneOf(conversation).isValidUserName(USER_NAME1);
        will(returnValue(false));
      }
    });
    nameCheckController.setChatClientContext(chatClientContext);     

    assertEquals(INVALID,
        nameCheckController.checkName(USER_NAME1));
    context.assertIsSatisfied();
  }
 
}
