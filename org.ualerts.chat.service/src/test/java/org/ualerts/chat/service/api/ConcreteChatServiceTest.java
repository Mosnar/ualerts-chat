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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.ualerts.chat.service.api.concrete.ConcreteChatService;
import org.ualerts.chat.service.api.concrete.ConcreteConversation;

public class ConcreteChatServiceTest {

	
	@Test
	public void testArgConstructor() {
		Conversation conversation = new ConcreteConversation();
		ChatService chatService = new ConcreteChatService(conversation);
		assertSame(conversation, chatService.findDefaultConversation());		
	}
	
	@Test
	public void testNoArgConstructor() {
		ChatService chatService = new ConcreteChatService();
		Conversation conversation = chatService.findDefaultConversation();
		assertNotNull(conversation);
	}
}
