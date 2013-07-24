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
import org.ualerts.chat.service.api.ChatTextMessage;
import org.ualerts.chat.service.api.ConcreteDateTimeService;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Message;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.Participant.Status;
import org.ualerts.chat.service.api.RosterAddedMessage;
import org.ualerts.chat.service.api.RosterMessage;
import org.ualerts.chat.service.api.UserIdentifier;

public class ConcreteConversationTest {

  private Mockery context;
  private ConcreteConversation conversation;
  private DateTimeService dateTimeService = new ConcreteDateTimeService();
  private static final String USER_NAME1 = "testname1";
  private static final String USER_NAME2 = "testname2";
  private static final String BROADCAST = "all";
 
  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    conversation = new ConcreteConversation();
    conversation.setDateTimeService(dateTimeService);
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
  public void testRemoveParticipantNotFound() {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
      }
    });

    conversation.addParticipant(participant1);
    conversation.removeParticipant(participant2);
    assertEquals(1, conversation.getParticipants().size());
    assertFalse(conversation.getParticipants().contains(participant2));

    context.assertIsSatisfied();
  }

  @Test
  public void testDeliverMessageToBroadcast() throws Exception {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 = context.mock(Participant.class, "second");

    final UserIdentifier userName1 = new UserIdentifier(USER_NAME1);
    final UserIdentifier userName2 = new UserIdentifier(USER_NAME2);
    
    final ChatTextMessage message = new ChatTextMessage();
    message.setTo(BROADCAST);

    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant1).deliverMessage(message);
        oneOf(participant1).getStatus();
        will(returnValue(Status.ONLINE));
        oneOf(participant1).getUserName();
        will(returnValue(userName1));
        
        oneOf(participant2).setConversation(conversation);
        oneOf(participant2).deliverMessage(message);
        oneOf(participant2).getStatus();
        will(returnValue(Status.ONLINE));
        oneOf(participant2).getUserName();
        will(returnValue(userName2));
      }
    });

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    conversation.deliverMessage(message);
    context.assertIsSatisfied();
  }

  @Test
  public void testDeliverMessageToParticipant() throws Exception {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 =
        context.mock(Participant.class, "second");
    final UserIdentifier userName1 = new UserIdentifier(USER_NAME1);
    final UserIdentifier userName2 = new UserIdentifier(USER_NAME2);
    final ChatTextMessage message = new ChatTextMessage();
    message.setTo(USER_NAME1);
    message.setFrom(USER_NAME2);

    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);

        oneOf(participant1).deliverMessage(message);
        oneOf(participant2).deliverMessage(message);

        oneOf(participant1).getStatus();
        will(returnValue(Status.ONLINE));
        oneOf(participant2).getStatus();
        will(returnValue(Status.ONLINE));

        allowing(participant1).getUserName();
        will(returnValue(userName1));
        allowing(participant2).getUserName();
        will(returnValue(userName2));
      }
    });

    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    conversation.deliverMessage(message);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testDeliverParticipantMessageNULL_USER() throws Exception {
    final Participant participant = context.mock(Participant.class);
    final UserIdentifier userName = UserIdentifier.NULL_USER;
    final ChatTextMessage message = new ChatTextMessage();
  
    context.checking(new Expectations() {
      {
        oneOf(participant).getUserName();
        will(returnValue(userName));
      }
    });
    
    conversation.deliverParticipantMessage(participant, message);
    context.assertIsSatisfied();
  }
  
  @Test
  public void testDeliverParticipantMessage() throws Exception {
    final Participant participant = context.mock(Participant.class);
    final UserIdentifier userName = new UserIdentifier(USER_NAME1);
    final ChatTextMessage message = new ChatTextMessage();
  
    context.checking(new Expectations() {
      {
        oneOf(participant).getUserName();
        will(returnValue(userName));
        oneOf(participant).getStatus();
        will(returnValue(Status.ONLINE));
        oneOf(participant).deliverMessage(with(any(Message.class)));
        
      }
    });
    
    conversation.deliverParticipantMessage(participant, message);
    context.assertIsSatisfied();
  }

  @Test
  public void testIsValidUserNameTrue() {
    final Participant participant1 = context.mock(Participant.class, "first");

    final UserIdentifier userName = new UserIdentifier(USER_NAME1);

    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);

        allowing(participant1).getUserName();
        will(returnValue(userName));
      }
    });

    conversation.addParticipant(participant1);

    assertTrue(conversation.isValidUserName("SomethingElse"));
  }

  @Test
  public void testIsValidUserNameFalse() {
    final Participant participant = context.mock(Participant.class, "first");
    final UserIdentifier userName = new UserIdentifier(USER_NAME1);

    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);

        allowing(participant).getUserName();
        will(returnValue(userName));
      }
    });

    conversation.addParticipant(participant);
    assertEquals(1, conversation.getParticipants().size());

    assertFalse(conversation.isValidUserName(USER_NAME1));
    context.assertIsSatisfied();
  }
  
  @Test
  public void testFinalizeRegisterParticipant() {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 = context.mock(Participant.class, "second");
    final UserIdentifier userName1 = new UserIdentifier(USER_NAME1);
    final UserIdentifier userName2 = new UserIdentifier(USER_NAME2);
    

    final RosterAddedMessage message = new RosterAddedMessage();
    message.setFrom(USER_NAME1);
    message.setTo(BROADCAST);
   
    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);
        oneOf(participant1).setStatus(Status.ONLINE); 
              
       allowing(participant1).getUserName();
        will(returnValue(userName1));
        
        allowing(participant2).getUserName();
        will(returnValue(userName2));
        
        allowing(participant1).getStatus();
        will(returnValue(Status.ONLINE));
        
        allowing(participant2).getStatus();
        will(returnValue(Status.ONLINE));
                
        allowing(participant1).deliverMessage(with(any(RosterMessage.class)));
        allowing(participant2).deliverMessage(with(any(RosterMessage.class)));
        
      } 
    });
    
    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    conversation.finalizeRegisterParticipant(USER_NAME1);
    
    assertEquals(2, conversation.getParticipants().size());    
    context.assertIsSatisfied();
  }
  
  @Test
  public void testFinalizeRegisterParticipantNull() throws Exception {
    final Participant participant = context.mock(Participant.class);
  
    final UserIdentifier userName1 = new UserIdentifier(USER_NAME1);
    
    context.checking(new Expectations() {
      {
        oneOf(participant).setConversation(conversation);
        allowing(participant).getUserName();
        will(returnValue(userName1));             
      } 
    });
    
    conversation.addParticipant(participant);
    assertEquals(1, conversation.getParticipants().size()); 
    conversation.finalizeRegisterParticipant(USER_NAME2);
     
    context.assertIsSatisfied();
  }
  
  @Test
  public void testFinalizeRegisterParticipantNULL_USER() throws Exception {
    final Participant participant1 = context.mock(Participant.class, "first");
    final Participant participant2 = context.mock(Participant.class, "second");
    
    final UserIdentifier userName = UserIdentifier.NULL_USER;
    final UserIdentifier userName2 = new UserIdentifier(USER_NAME2);
    
    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);
        allowing(participant1).getUserName();
        will(returnValue(userName));  
        allowing(participant2).getUserName();
        will(returnValue(userName2));
        
        oneOf(participant2).setStatus(Status.ONLINE);
        oneOf(participant2).getStatus();
        will(returnValue(Status.ONLINE));
        oneOf(participant2).deliverMessage(with(any(RosterMessage.class)));
      } 
    });
    
    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    assertEquals(2, conversation.getParticipants().size()); 
    conversation.finalizeRegisterParticipant(USER_NAME2);
     
    context.assertIsSatisfied();
  }
  
  @Test
  public void testFinalizeRemoveParticipant() throws Exception {
    final Participant participant1 = context.mock(Participant.class,"first");  
    final Participant participant2 = context.mock(Participant.class, "second");
    
    final UserIdentifier userName1 = new UserIdentifier(USER_NAME1);
    final UserIdentifier userName2 = new UserIdentifier(USER_NAME2); 
    
    context.checking(new Expectations() {
      {
        oneOf(participant1).setConversation(conversation);
        oneOf(participant2).setConversation(conversation);
       
        allowing(participant1).getUserName();
        will(returnValue(userName1));  
        
        allowing(participant2).getUserName();
        will(returnValue(userName2));
        
        oneOf(participant2).getStatus();
        will(returnValue(Status.ONLINE));
       
       oneOf(participant2).deliverMessage(with(any(RosterMessage.class)));
      } 
    });
    
    conversation.addParticipant(participant1);
    conversation.addParticipant(participant2);
    assertTrue(conversation.getParticipants().contains(participant1));
    conversation.finalizeRemoveParticipant(USER_NAME1);
     
    context.assertIsSatisfied();
  }
  

}
