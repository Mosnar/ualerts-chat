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

package org.ualerts.chat.web.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.ualerts.chat.service.api.ChatClient;

/**
 * A ChatClientContext that is a HandlerInterceptorAdapter and stores the 
 * ChatClient into the Http Session.  Upon each request, it pulls the ChatClient
 * out of the session and stores it in a ThreadLocal. When the request is
 * finished, the ChatClient is again stored in the Session.
 *
 * @author Michael Irwin (mikesir87)
 */
public class ChatClientContextInterceptor extends HandlerInterceptorAdapter
    implements ChatClientContext, DisposableBean {
  
  private static final ThreadLocal<ChatClient> chatClientContext =
      new ThreadLocal<ChatClient>() { };
      
  private static final String CHAT_CLIENT_SESSION_ATTR = 
      "org.ualerts.chat.web.chatClient";
  
  public boolean preHandle(HttpServletRequest request, 
      HttpServletResponse response, Object handler) throws Exception {
    
    chatClientContext.set((ChatClient) 
        request.getSession().getAttribute(CHAT_CLIENT_SESSION_ATTR));
    return true;
  };
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void postHandle(HttpServletRequest request,
      HttpServletResponse response, Object handler, ModelAndView modelAndView)
      throws Exception {
    request.getSession().setAttribute(CHAT_CLIENT_SESSION_ATTR, getChatClient());
  }
      
  /**
   * {@inheritDoc}
   */
  @Override
  public ChatClient getChatClient() {
    return chatClientContext.get();
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void setChatClient(ChatClient chatClient) {
    chatClientContext.set(chatClient);
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void destroy() throws Exception {
    chatClientContext.set(null);
  }
  
}
