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
import static org.junit.Assert.assertSame;
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
  private static final String USER_NAME2 = "testname2";

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    conversation = new ConcreteConversation();

  }

  @Test
  public void testAddParticipant() throws Exception {
    final Participant participant = context.mock(Participant.class);
    final UserName userName = new UserName(USER_NAME1); //

    context.checking(new Expectations() {
      {
        oneOf(participant).getUserName();
        will(returnValue(userName));
        oneOf(participant).setConversation(conversation);

      }
    });

    conversation.addParticipant(participant);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(1, participants.size());
    assertTrue(participants.contains(participant));
    assertSame(participant, (Participant) conversation.getParticipantsMap()
        .get(USER_NAME1));
    context.assertIsSatisfied();
  }

  @Test
  public void testRemoveParticipant() {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    final UserName userName1 = new UserName(USER_NAME1);
    final UserName userName2 = new UserName(USER_NAME2);

    context.checking(createAddParticipantExpectations(participant1, participant2,
            userName1, userName2));

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    assertEquals(2, conversation.getParticipants().size());
    assertEquals(2, conversation.getParticipantsMap().size());
    conversation.removeParticipant(participant1);
    assertEquals(1, conversation.getParticipants().size());
    assertEquals(1, conversation.getParticipantsMap().size());
    assertTrue(conversation.getParticipants().contains(participant2));
    assertTrue(conversation.getParticipantsMap().containsKey(USER_NAME2));
    context.assertIsSatisfied();
  }

  @Test
  public void testDeliverMessage() throws Exception {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");

    final UserName userName1 = new UserName(USER_NAME1);
    final UserName userName2 = new UserName(USER_NAME2);

    final ChatTextMessage message = new ChatTextMessage();

    context.checking(new Expectations() {
      {

        atLeast(2).of(participant1).getUserName();
        will(returnValue(userName1));

        atLeast(2).of(participant2).getUserName();
        will(returnValue(userName2));

        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);

        oneOf(participant1).deliverMessage(message);
        oneOf(participant2).deliverMessage(message);

      }
    });
    message.setText("Hello");

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);

    conversation.deliverMessage(message);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testIsValidUserNameTrue()
  {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    final UserName userName1 = new UserName(USER_NAME1);
    final UserName userName2 = new UserName(USER_NAME2);

    context.checking(createAddParticipantExpectations(participant1, participant2,
            userName1, userName2));

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    
    assertTrue(conversation.isValidUserName("SomethingElse"));
  }
  
  @Test
  public void testIsValidUserNameFalse()
  {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    final UserName userName1 = new UserName(USER_NAME1);
    final UserName userName2 = new UserName(USER_NAME2);

    context.checking(createAddParticipantExpectations(participant1, participant2,
            userName1, userName2));

      

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    
    assertFalse(conversation.isValidUserName(USER_NAME1));
  }

  /**
   * @param participant1
   * @param participant2
   * @param userName1
   * @param userName2
   */
  private Expectations createAddParticipantExpectations(
      final Participant participant1, final Participant participant2,
      final UserName userName1, final UserName userName2) {
    
    return new Expectations() {
      {
        atLeast(2).of(participant1).getUserName();
        will(returnValue(userName1));

        oneOf(participant2).getUserName();
        will(returnValue(userName2));

        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);   }
    };
    
  }
}
