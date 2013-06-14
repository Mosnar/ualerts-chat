package org.ualerts.chat.service.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.hibernate.engine.internal.TwoPhaseLoad;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ConversationTest {
	
	private Mockery context;
	private Conversation conversation;
	
		
	@Before
	public void setUp() throws Exception {
		context = new Mockery();
		conversation = new DefaultConversation();
		
		
	}

	@Test
	public void testAddClient()
	{
		ChatClient client = context.mock(ChatClient.class);
		conversation.addClient(client);
		Set<ChatClient> clients = conversation.getChatClients();
		assertEquals(1, clients.size());
		assertTrue(clients.contains(client));
		
	}
	
	@Test
	public void testRemoveClient()
	{
		ChatClient client = context.mock(ChatClient.class);
		conversation.addClient(client);
		Set<ChatClient> clients = conversation.getChatClients();
		assertEquals(1, clients.size());
		clients.remove(client);
		assertEquals(0, clients.size());
	}

	@Test
	public void testDeliverMessage() throws Exception{
		final ChatClient client1 = context.mock(ChatClient.class,"first");
		final ChatClient client2 = context.mock(ChatClient.class,"second");
		final Message message = context.mock(Message.class); 
		conversation.addClient(client1);
		conversation.addClient(client2);
		
		
		context.checking(new Expectations() { { 
			oneOf(client1).deliverMessage(message);
		} });
		
		context.checking(new Expectations() { { 
			oneOf(client2).deliverMessage(message);
		} });
		
		conversation.deliverMessage(message);
		context.assertIsSatisfied();
	}
	
	

}
