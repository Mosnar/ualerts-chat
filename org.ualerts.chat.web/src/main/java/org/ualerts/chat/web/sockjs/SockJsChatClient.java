/*
 * File created on Jun 19, 2013
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

import org.springframework.web.socket.WebSocketSession;
import org.ualerts.chat.service.api.ChatClient;

/**
 * An extension to the ChatClient that adds a setter that is specific to a
 * SockJS implementation.
 *
 * @author Michael Irwin (mikesir87)
 */
public interface SockJsChatClient extends ChatClient {

  /**
   * Set the session that should be used for communication.
   * @param session The sockjs session
   */
  void setSession(WebSocketSession session);
  
}
