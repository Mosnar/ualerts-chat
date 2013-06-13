package org.ualerts.chat.service.api;

import org.ualerts.chat.service.exceptions.ConversationNotFoundException;

/**
 * Provides a chat conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public interface ChatService {
	
	/**
	 * Provides a default Conversation
	 * @return the default Conversation
	 * @throws ConversationNotFoundException
	 */
	Conversation findDefaultConversation() throws ConversationNotFoundException;
}
