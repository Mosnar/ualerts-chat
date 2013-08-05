/*
 * File created on Aug 1, 2013
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

package org.ualerts.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.chat.service.api.ChatService;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.UserService;

/**
 * Controller used to create a new Conversation
 *
 * @author Billy Coleman
 * @author Brandon Foster
 */
@Controller
public class CreateConversationController {
  
  private final String VALID = "{\"result\":\"valid\"}";
  private final String INVALID = "{\"result\":\"invalid\"}";;
  
  private ChatService chatService;
  private UserService userService;
  /**
   * Create a new Conversation 
   * @param the name of the new Conversation
   * @return json String indicating result
   */  
  @RequestMapping (value = "/createNewConversation", method = RequestMethod.POST)
  @ResponseBody
  public String createConversation(@RequestParam("conversationName") String conversationName,
      @RequestParam("username") String username) {
    
    UserIdentifier userIdentifier = 
        new UserIdentifier(username, conversationName+"."+userService.getDefaultDomain());
    Conversation conversation = chatService.getConversation(userIdentifier);
    if (conversation == null) {
      chatService.joinConversation(userIdentifier,false);
      return this.VALID;
    }
    else {
      return this.INVALID;
    }
  }
  
  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
  
  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }
}


