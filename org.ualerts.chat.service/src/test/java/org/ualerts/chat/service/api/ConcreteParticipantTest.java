/*
 * File created on Jun 25, 2013
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

package org.ualerts.chat.service.api;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.socket.TextMessage;
import org.ualerts.chat.service.api.concrete.ConcreteParticipant;

/**
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class ConcreteParticipantTest {
  private Participant participant;
  private Mockery context;

  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    participant = new ConcreteParticipant();
  }

  @Test
  public void testSetConversation() {
    final Conversation conversation = context.mock(Conversation.class);
    participant.setConversation(conversation);
    assertNotNull(participant.getConversation());
  }

  @Test
  public void testGetConversation() {
    final Conversation conversation = context.mock(Conversation.class);
    participant.setConversation(conversation);
    assertSame(conversation, participant.getConversation());
  }

  @Test
  public void testSetAndGetUserNameNotNull() {
    UserName userName = new UserName("TestName");
    participant.setUserName(userName);
    assertSame(userName, participant.getUserName());
    assertFalse(userName == userName.NULL_USER);
  }

  @Test
  public void testSetAndGetUserNameNull() {
    UserName userName = UserName.NULL_USER;
    participant.setUserName(userName);
    assertSame(userName, participant.getUserName());
    assertTrue(userName == UserName.NULL_USER);
  }

  @Test
  public void testSetandGetChatClient() {
    final ChatClient chatClient = context.mock(ChatClient.class);
    participant.setChatClient(chatClient);
    assertSame(chatClient, participant.getChatClient());
  }

  @Test
  public void testDeliverMessage() {
    final ChatClient chatClient = context.mock(ChatClient.class);
    participant.setChatClient(chatClient);
    final Message message = new ChatTextMessage();

    context.checking(new Expectations() {
      {
        oneOf(chatClient).deliverMessage(message);
      }
    });
    participant.deliverMessage(message);
    context.assertIsSatisfied();
  }

}
