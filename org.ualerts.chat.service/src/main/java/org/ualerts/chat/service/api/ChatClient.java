package org.ualerts.chat.service.api;

import org.ualerts.chat.service.exceptions.FailedMessageDeliveryException;

/**
 * Representing a member of a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public interface ChatClient {
	/**
	 * Provide a reference to the conversation this participant
	 * is a member of
	 * @param conversation
	 */
	void setConversation(Conversation conversation);
	
	/**
	 * Send a message
	 * @param the message to be sent
	 * @throws FailedMessageDeliveryException
	 */
	void deliverMessage(Message message) throws FailedMessageDeliveryException;

}
