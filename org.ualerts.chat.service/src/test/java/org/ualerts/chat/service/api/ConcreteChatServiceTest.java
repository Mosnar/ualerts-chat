package org.ualerts.chat.service.api;

import static org.junit.Assert.assertSame;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.concrete.ConcreteChatService;
import org.ualerts.chat.service.api.concrete.ConcreteConversation;

public class ConcreteChatServiceTest {

	
	private Mockery context;
	private ChatService chatService;
	
	@Before
	public void setUp() {
		context = new Mockery();
	}
	
	@Test
	public void testFindDefaultConversation() {
		Conversation conversation = new ConcreteConversation();
		ChatService chatService = new ConcreteChatService(conversation);
		assertSame(conversation, chatService.findDefaultConversation());
		
	}
	
}
