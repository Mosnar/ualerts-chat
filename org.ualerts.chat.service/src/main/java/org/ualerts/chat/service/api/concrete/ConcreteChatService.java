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

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;


/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 * @author Brandon Foster
 *
 */
@Service
public class ConcreteChatService implements ChatService {
  
	private Set<Conversation> conversations = new HashSet<Conversation>();
	private DateTimeService dateTimeService;
	private UserService userService;
	
  
	@Autowired
	public ConcreteChatService(DateTimeService dateTimeService) {
	  this.dateTimeService = dateTimeService;
	}

  /**
   * {@inheritDoc}
   */
  @Override
  public Conversation getConversation(UserIdentifier userIdentifier) {
    for (Conversation conversation: conversations) {
      if (conversation.getName().equals(userIdentifier.getDomain())) {
        return conversation;
      }
    }
    addConversation(userIdentifier);
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void joinConversation(UserIdentifier userIdentifier) {
    Conversation conversation = getConversation(userIdentifier);
    if (conversation == null) {
      
      conversation = addConversation(userIdentifier);
    }
      Participant participant = new ConcreteParticipant();
      participant.setUserName(userIdentifier);
      participant.setConversation(conversation);
      participant.setChatClient(this.userService.findClient(userIdentifier.getName()));
      conversation.addParticipant(participant);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Conversation addConversation(UserIdentifier userIdentifier) {
    ConcreteConversation conversation = new ConcreteConversation();
    conversation.setDateTimeService(this.dateTimeService);
    conversation.setName(userIdentifier.getDomain());
    conversations.add(conversation);
    return conversation;
  }
  
  @Autowired
  public void getUserService(UserService userService) {
    this.userService = userService;
  }
}
