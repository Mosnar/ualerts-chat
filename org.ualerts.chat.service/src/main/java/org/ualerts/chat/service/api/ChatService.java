package org.ualerts.chat.service.api;


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
	Conversation findDefaultConversation();
}
