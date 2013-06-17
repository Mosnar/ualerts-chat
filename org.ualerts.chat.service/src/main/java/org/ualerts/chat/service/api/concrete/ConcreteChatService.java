package org.ualerts.chat.service.api.concrete;

import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;


/**
 * Provides a Conversation
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public class ConcreteChatService implements ChatService {

	private final Conversation defaultConversation;
	
	public ConcreteChatService() {
		this(new ConcreteConversation());
	}
	
	public ConcreteChatService(Conversation conversation) {
		this.defaultConversation = conversation;
	}
	
	@Override
	public Conversation findDefaultConversation() {
		return defaultConversation;
	}

}
