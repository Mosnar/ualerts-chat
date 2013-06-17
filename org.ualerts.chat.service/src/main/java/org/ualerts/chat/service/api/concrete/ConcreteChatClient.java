package org.ualerts.chat.service.api.concrete;

import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;


public class ConcreteChatClient implements ChatClient {

	private Conversation conversation;

	@Override
	public void deliverMessage(Message message) {

	}
	
	@Override
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
}
