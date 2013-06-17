package org.ualerts.chat.service.api;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Test;
import org.ualerts.chat.service.api.concrete.ConcreteChatService;
import org.ualerts.chat.service.api.concrete.ConcreteConversation;

public class ConcreteChatServiceTest {

	
	@Test
	public void testArgConstructor() {
		Conversation conversation = new ConcreteConversation();
		ChatService chatService = new ConcreteChatService(conversation);
		assertSame(conversation, chatService.findDefaultConversation());		
	}
	
	@Test
	public void testNoArgConstructor() {
		ChatService chatService = new ConcreteChatService();
		Conversation conversation = chatService.findDefaultConversation();
		assertNotNull(conversation);
	}
}
