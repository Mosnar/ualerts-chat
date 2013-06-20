/*
 * File created on Jun 17, 2013
 *
 * Copyright 2008-2013 Virginia Polytechnic Institute and State University
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package org.ualerts.chat.web.sockjs;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Message;


class ConcreteChatClient implements SockJsChatClient {

	private Conversation conversation;
  private WebSocketSession session;
  private ObjectMapper mapper = new ObjectMapper();
	private List<Message> missedMessages = new ArrayList<Message>();
  
	@Override
	public void deliverMessage(Message message) {
	  
	  try {
	    session.sendMessage(new TextMessage(mapper.writeValueAsString(message)));
    }
    catch (Exception e) {
       missedMessages.add(message);
    }
	}
	
  @Override
  public Conversation getConversation() {
    return this.conversation;
  }
  
	@Override
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}

  @Override
  public List<Message> getMissedMessages() {
    return this.missedMessages;
  }

  /**
   * Sets the {@code session} property.
   * @param session the value to set
   */
  public void setSession(WebSocketSession session) {
    this.session = session;
  }
 
}
