package org.ualerts.chat.service.api;

import org.ualerts.chat.service.exceptions.ConversationNotFoundException;

/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public class ChatServiceImpl implements ChatService {

	public final Conversation defaultConversation;
	
	public ChatServiceImpl() {
		this.defaultConversation = new DefaultConversation();
	}
	
	@Override
	public Conversation findDefaultConversation() {
		return defaultConversation;
	}

}
