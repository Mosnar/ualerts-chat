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

import java.util.Set;

/**
 * Represents a conversation between chat participants
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public interface Conversation {
	
	/**
	 * Add a chat participant to a conversation
	 * @param chatClient
	 */
	void addClient(ChatClient client);
	
	/**
	 * Remove a chat participant from a conversation
	 * @param chatClient
	 */
	void removeClient(ChatClient client);
	
	/**
	 * Returns a Set of ChatClient associated with this Conversation
	 * @return Set of ChatClient
	 */
	Set<ChatClient> getChatClients();
	
	/**
	 * Deliver a message to chat participants participating
	 * in this conversation
	 * @param the message to be delivered
	 */
	void deliverMessage(Message message);

	
}
