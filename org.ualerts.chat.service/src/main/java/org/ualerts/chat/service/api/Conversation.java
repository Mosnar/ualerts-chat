package org.ualerts.chat.service.api;

import org.ualerts.chat.service.exceptions.FailedMessageDeliveryException;

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
	 * Deliver a message to chat participants participating
	 * in this conversation
	 * @param the message to be delivered
	 * @throws FailedMessageDeliveryException
	 */
	void deliverMessage(Message message) throws FailedMessageDeliveryException;

}
