package org.ualerts.chat.service.api;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ChatServiceTest {

	
	private Mockery context;
	
	@Before
	public void setUp() {
		context = new Mockery();
	}
	
	@Test
	public void testFindDefaultConversation() {
		final ChatService chatService = context.mock(ChatService.class);
		
		context.checking(new Expectations() { { 
			oneOf(chatService).findDefaultConversation();
		} });
		
		chatService.findDefaultConversation();
		context.assertIsSatisfied();
	}
	
}
