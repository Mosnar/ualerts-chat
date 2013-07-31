/*
 * File created on Jun 26, 2013
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

import org.ualerts.chat.service.api.ChatClientContext;

/**
 * Container for a ChatClientContextInterceptor
 *
 * @author Michael Irwin
 */
public class ChatClientContextInterceptorFactoryBean {
  private static ChatClientContextInterceptor interceptor = new ChatClientContextInterceptor();
  
  /**
   * Gets the {@code interceptor} property.
   * @return property value
   */
  public ChatClientContextInterceptor getInterceptor() {
    return interceptor;
  }
  
  /**
   * Sets the {@code chatClientContext} property.
   * @param chatClientContext the value to set
   */
  public void setChatClientContext(ChatClientContext chatClientContext) {
    interceptor.setChatClientContext(chatClientContext);
  }
}
