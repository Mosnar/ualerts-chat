package org.ualerts.chat.service.api.concrete;

import java.util.HashSet;
import java.util.Set;

import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;

/**
 * The default conversation
 * @author Billy Coleman1
 * @author Ransom Roberson
 *
 */
public class ConcreteConversation implements Conversation {

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
	public void deliverMessage(Message message) {
		
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
