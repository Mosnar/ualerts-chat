package org.ualerts.chat.service.api;

import org.ualerts.chat.service.exceptions.FailedMessageDeliveryException;

public class ChatClientImpl implements ChatClient {

//	public ChatClientImpl(WebSocketSession session)
//	{
//		
//	}
	
	private ChatService chatService;
	//private ObjectMapper objectMapper; 
	private Conversation conversation;

	@Override
	public void deliverMessage(Message message) throws 
	FailedMessageDeliveryException {
		
		

	}
	
	@Override
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;

	}
	


}
