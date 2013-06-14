package org.ualerts.chat.service.api;

import java.util.HashSet;
import java.util.Set;

import org.ualerts.chat.service.exceptions.FailedMessageDeliveryException;

/**
 * The default conversation
 * @author Billy Coleman1
 * @author Ransom Roberson
 *
 */
public class DefaultConversation implements Conversation {

	private static final String BROADCAST = "ALL"; 
	private Set<ChatClient> clients = new HashSet<ChatClient>();

	@Override
	public void addClient(ChatClient chatClient) {
		this.clients.add(chatClient);
	}

	@Override
	public void removeClient(ChatClient chatClient) {
		
		if(clients.contains(chatClient)){
			clients.remove(chatClient);
		}
	}

	@Override
	public void deliverMessage(Message message)
			throws FailedMessageDeliveryException {
		
			for(ChatClient client : clients)
			{
				client .deliverMessage(message);
			}
		
	}

	@Override
	public Set<ChatClient> getChatClients() {
		return clients;
	}

	

}
