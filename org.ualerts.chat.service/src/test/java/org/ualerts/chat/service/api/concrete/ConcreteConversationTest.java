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
    final UserIdentifier userId = new UserIdentifier(USER_NAME1, DOMAIN);
    
    context.checking(new Expectations() {
      {
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
      }
    });

    conversation.addParticipant(participant);
    Set<Participant> participants = conversation.getParticipants();
    assertEquals(1, participants.size());
    assertTrue(participants.contains(participant));
    context.assertIsSatisfied();
  }
}
