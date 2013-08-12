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
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ConcreteDateTimeService;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.RosterMessage;
import org.ualerts.chat.service.api.UserIdentifier;

public class ConcreteConversationTest {

  private Mockery context;
  private ConcreteConversation conversation;
  private DateTimeService dateTimeService = new ConcreteDateTimeService();
  private static final String USER_NAME1 = "testname1";
  private static final String USER_NAME2 = "testname2";
  private static final String DOMAIN = "ualerts.org";
  private static final String BROADCAST = "all";

  private static final UserIdentifier userId = new UserIdentifier(USER_NAME1,
      DOMAIN);
  private static final UserIdentifier userId2 = new UserIdentifier(USER_NAME2,
      DOMAIN);

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    conversation = new ConcreteConversation();
    conversation.setDateTimeService(dateTimeService);
    conversation.setName(DOMAIN);

  }

  @Test
  public void testAddParticipantMe() {
    final Participant participant = context.mock(Participant.class);
    final ChatClient chatClient = context.mock(ChatClient.class);

    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);
        oneOf(participant).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME1));
        oneOf(participant).setStatus(Status.ONLINE);

        allowing(participant).getStatus();
        will(returnValue(Status.ONLINE));

        allowing(participant).deliverMessage(with(any(RosterMessage.class)));

        atLeast(1).of(participant).getUserName();
        will(returnValue(userId));
      }
    });

    conversation.addParticipant(participant);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(1, participants.size());
    assertTrue(participants.contains(participant));
    context.assertIsSatisfied();
  }

  @Test
  public void testAddParticipantNotMe() {
    final Participant participant = context.mock(Participant.class, "one");
    final Participant participant2 = context.mock(Participant.class, "two");
    final ChatClient chatClient = context.mock(ChatClient.class);
    context.checking(new Expectations() {
      {
        // Participant 1
        oneOf(participant).setConversation(conversation);
        oneOf(participant).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME1));
        oneOf(participant).setStatus(Status.ONLINE);

        oneOf(participant).getStatus();
        will(returnValue(Status.ONLINE));

        oneOf(participant).deliverMessage(with(any(RosterMessage.class)));

        oneOf(participant).getUserName();
        will(returnValue(userId));

        exactly(2).of(participant).getUserName();
        will(returnValue(userId));
        
        
        // Participant 2
        oneOf(participant2).setConversation(conversation);
        oneOf(participant2).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME2));
        allowing(participant2).setStatus(Status.ONLINE);

        allowing(participant2).getStatus();
        will(returnValue(Status.ONLINE));
        allowing(participant).getStatus();
        will(returnValue(Status.ONLINE));

        allowing(participant2).deliverMessage(with(any(RosterMessage.class)));
        allowing(participant).deliverMessage(with(any(RosterMessage.class)));

        allowing(participant2).getUserName();
        will(returnValue(userId2));

        allowing(participant2).getUserName();
        will(returnValue(userId2));
        
        allowing(participant).getUserName();
        will(returnValue(userId));
        
      }
    });

    conversation.addParticipant(participant);
    conversation.addParticipant(participant2);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(2, participants.size());
    assertTrue(participants.contains(participant));
    assertTrue(participants.contains(participant2));
    context.assertIsSatisfied();
  }

  @Test
  public void testRemoveParticipant()
  {
    final Participant participant = context.mock(Participant.class, "one");
    final Participant participant2 = context.mock(Participant.class, "two");
    final ChatClient chatClient = context.mock(ChatClient.class);
    context.checking(new Expectations() {
      {
        // Participant 1
        oneOf(participant).setConversation(conversation);
        oneOf(participant).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME1));
        oneOf(participant).setStatus(Status.ONLINE);

        oneOf(participant).getStatus();
        will(returnValue(Status.ONLINE));

        oneOf(participant).deliverMessage(with(any(RosterMessage.class)));

        oneOf(participant).getUserName();
        will(returnValue(userId));

        exactly(2).of(participant).getUserName();
        will(returnValue(userId));
        
        
        // Participant 2
        oneOf(participant2).setConversation(conversation);
        oneOf(participant2).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME2));
        oneOf(participant2).setStatus(Status.ONLINE);

        atLeast(1).of(participant2).getStatus();
        will(returnValue(Status.ONLINE));
        atLeast(1).of(participant).getStatus();
        will(returnValue(Status.ONLINE));

        atLeast(1).of(participant2).deliverMessage(with(any(RosterMessage.class)));
        atLeast(1).of(participant).deliverMessage(with(any(RosterMessage.class)));

        oneOf(participant2).getUserName();
        will(returnValue(userId2));

        atLeast(2).of(participant2).getUserName();
        will(returnValue(userId2));
        
        atLeast(1).of(participant).getUserName();
        will(returnValue(userId));
        
        
        // Remove Participant 1
        oneOf(participant).getChatClient();
        will(returnValue(chatClient));
        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME1));
      }
    });

    conversation.addParticipant(participant);
    conversation.addParticipant(participant2);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(2, participants.size());
    assertTrue(participants.contains(participant));
    assertTrue(participants.contains(participant2));
    
    conversation.removeParticipant(participant);
    Set<Participant> participants2 = conversation.getParticipants();
    assertEquals(1, participants2.size());
    assertFalse(participants2.contains(participant));
    assertTrue(participants2.contains(participant2));    
    context.assertIsSatisfied();    
  }
  
  @Test
  public void testIsValidUserNameTrue() {
    assertTrue(conversation.isValidUserName("Test"));
  }
  
  @Test
  public void testIsValidUserNameFalse() {
    final Participant participant = context.mock(Participant.class);
    final ChatClient chatClient = context.mock(ChatClient.class);
    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);
        
        oneOf(participant).getChatClient();
        will(returnValue(chatClient));

        oneOf(chatClient).getUserName();
        will(returnValue(USER_NAME1));
        
        oneOf(participant).setStatus(Status.ONLINE);

        atLeast(1).of(participant).getStatus();
        will(returnValue(Status.ONLINE));
       

        oneOf(participant).deliverMessage(with(any(RosterMessage.class)));

        oneOf(participant).getUserName();
        will(returnValue(userId));

        atLeast(2).of(participant).getUserName();
        will(returnValue(userId));
        
      }
    });

    conversation.addParticipant(participant);
    context.assertIsSatisfied();
    assertFalse(conversation.isValidUserName(USER_NAME1));
  }
  
}
