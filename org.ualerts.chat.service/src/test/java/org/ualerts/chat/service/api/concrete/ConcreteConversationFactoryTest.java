/*
 * File created on Jul 29, 2013
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

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.UserIdentifier;

/**
 *
 * @author Ransom Roberson
 * @author Brandon Foster
 */
public class ConcreteConversationFactoryTest {
  private Mockery context;

  @Before
  public void setUp() throws Exception {
    // intentionally left blank
  }
  
  @Test
  public void createConversationTest() {
    context = new Mockery();
    final ConversationFactory convoFactory =
        context.mock(ConversationFactory.class);
    final Conversation conversation = context.mock(Conversation.class);
    
    context.checking(new Expectations() {
      {
        exactly(1).of(convoFactory).createConversation(
            with(any(UserIdentifier.class)));
        will(returnValue(conversation));    
      }
    });
  }
}
