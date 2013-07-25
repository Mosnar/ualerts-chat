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


/**
 * Provides a chat conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public interface ChatService {
	
//	/**
//	 * Provides a default Conversation
//	 * @return the default Conversation
//	 * @throws ConversationNotFoundException
//	 */
//	Conversation findDefaultConversation();
  
  /**
   * Get a conversation whose name is the domain portion of the passed in
   * UserIdentifier
   * @param userIdentifier The full UserIdentifier to get the Conversation
   *        whose name is that of the UserIdentifier's domain
   * @return a conversation
   */
  Conversation getConversation(UserIdentifier userIdentifier);
  
  /**
   * Join the user to a conversation
   * @param userIdentifier The full userIdentifier of the Participant to join
   *        to the Conversation
   */
  void joinConversation(UserIdentifier userIdentifier);
  
  /**
   * Add a Conversation to the collection of Conversations
   * @param userIdentifier The full UserIdentifier to create a Conversation
   *        whose name is that of the UserIdentifier's domain
   */
  Conversation addConversation(UserIdentifier userIdentifier);
}
