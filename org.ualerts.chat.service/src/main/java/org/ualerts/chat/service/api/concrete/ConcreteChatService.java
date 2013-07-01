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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.DateTimeService;


/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
@Service
public class ConcreteChatService implements ChatService {

	private final Conversation defaultConversation;
	private DateTimeService dateTimeService;
	
	public ConcreteChatService() {
		this(new ConcreteConversation());
	}
	
	public ConcreteChatService(Conversation conversation) {
		this.defaultConversation = conversation;
		this.defaultConversation.setDateTimeService(dateTimeService);
	}
	
	@Override
	public Conversation findDefaultConversation() {
		return defaultConversation;
	}
	
	@Autowired
	public void setDateTimeService(DateTimeService dateTimeService)
	{
	  this.dateTimeService = dateTimeService;
	}

}
