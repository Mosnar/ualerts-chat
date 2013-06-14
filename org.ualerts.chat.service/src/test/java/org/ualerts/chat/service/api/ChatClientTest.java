package org.ualerts.chat.service.api;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ChatClientTest {

	private Mockery context;
	private ChatClient chatClient;
	
	@Before
	public void setUp() throws Exception {
		context = new Mockery();
		chatClient = new ChatClientImpl();
	}

	@Test
	public void testDeliverMessage() throws Exception{
		final Conversation conversation = context.mock(Conversation.class);
		final Message message = context.mock(Message.class);
		
		context.checking(new Expectations() { { 
			oneOf(conversation).deliverMessage(message);
		} });
		chatClient.setConversation(conversation);
		chatClient.deliverMessage(message);
	}

}
