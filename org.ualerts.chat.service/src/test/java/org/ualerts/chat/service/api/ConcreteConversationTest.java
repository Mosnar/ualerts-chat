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

package org.ualerts.chat.service.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.concrete.ConcreteConversation;

public class ConcreteConversationTest {

  private Mockery context;
  private Conversation conversation;
  private static final String USER_NAME1 = "testname1";

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    conversation = new ConcreteConversation();

  }

  @Test
  public void testAddParticipant() throws Exception {
    final Participant participant = context.mock(Participant.class);

    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);
      }
    });

    conversation.addParticipant(participant);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(1, participants.size());
    assertTrue(participants.contains(participant));
    context.assertIsSatisfied();
  }

  @Test
  public void testRemoveParticipant() {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);
      }
    });

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    assertEquals(2, conversation.getParticipants().size());

    conversation.removeParticipant(participant1);
    assertEquals(1, conversation.getParticipants().size());
    assertTrue(conversation.getParticipants().contains(participant2));

    context.assertIsSatisfied();
  }

  @Test
  public void testDeliverMessage() throws Exception {
    final Participant participant = context.mock(Participant.class);

    final UserName userName = new UserName(USER_NAME1);

    final ChatTextMessage message = new ChatTextMessage();
    message.setTo("all");

    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);
        oneOf(participant).deliverMessage(message);
        oneOf(participant).getUserName();
        will(returnValue(userName));
      }
    });

    conversation.addParticipant(participant);
    conversation.deliverMessage(message);
    context.assertIsSatisfied();
  }

  @Test
  public void testIsValidUserNameTrue() {
    final Participant participant1 = context.mock(Participant.class, "first");

    final UserName userName = new UserName(USER_NAME1);

    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);

        atLeast(2).of(participant1).getUserName();
        will(returnValue(userName));
      }
    });

    conversation.addParticipant(participant1);

    assertTrue(conversation.isValidUserName("SomethingElse"));
  }

  @Test
  public void testIsValidUserNameFalse() {
    final Participant participant = context.mock(Participant.class, "first");
    final UserName userName = new UserName(USER_NAME1);

    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);

        atLeast(2).of(participant).getUserName();
        will(returnValue(userName));
      }
    });

    conversation.addParticipant(participant);
    assertEquals(1, conversation.getParticipants().size());

    assertFalse(conversation.isValidUserName(USER_NAME1));
    context.assertIsSatisfied();
  }

}
