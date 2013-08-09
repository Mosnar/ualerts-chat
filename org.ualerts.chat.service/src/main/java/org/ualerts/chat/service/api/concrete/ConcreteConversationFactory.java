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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.ConversationFactory;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;

/**
 * Default implementation of the ConversationFactory to provide Conversations
 *
 * @author Ransom Roberson
 * @author Brandon Foster
 */
@Service
public class ConcreteConversationFactory implements ConversationFactory {

  private DateTimeService dateTimeService;
  
  
  @Autowired
  public ConcreteConversationFactory(DateTimeService dateTimeService) {
    this.dateTimeService = dateTimeService;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public Conversation newConversation(UserIdentifier userIdentifier, boolean defaultConversation) {
    ConcreteConversation conversation = new ConcreteConversation();
    conversation.setDateTimeService(this.dateTimeService);
    conversation.setName(userIdentifier.getDomain());
    return conversation;
  }

}