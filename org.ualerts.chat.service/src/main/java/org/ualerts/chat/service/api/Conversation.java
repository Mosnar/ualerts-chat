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
