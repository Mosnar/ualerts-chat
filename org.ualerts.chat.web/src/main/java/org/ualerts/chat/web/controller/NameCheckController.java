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

package org.ualerts.chat.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserName;
import org.ualerts.chat.web.context.ChatClientContext;
import org.ualerts.chat.web.sockjs.SockJsChatClient;

/**
 * Controller used to determine if a user name is available
 * for use
 *
 * @author Billy Coleman
 * @author Ransom Roberson
 */

@Controller
public class NameCheckController {
  
  private final String VALID = "VALID";
  private final String INVALID = "INVALID";
  
  private ChatClientContext chatClientContext;
  private SockJsChatClient chatClient;
  
  @RequestMapping(value="/checkName", method = RequestMethod.POST)
  @ResponseBody
  public String checkName(@RequestParam("name") String name) {
    
      chatClient = (SockJsChatClient)chatClientContext.getChatClient();
      Participant participant = chatClient.getParticipant();
      Conversation conversation = participant.getConversation();
      
      if(conversation.isValidUserName(name)) {
        UserName userName = new UserName(name);
        participant.setUserName(userName);
        return VALID;
      }
    return INVALID;
  }

  @Autowired
  public void setChatClientContext(ChatClientContext chatClientContext) {
    this.chatClientContext = chatClientContext;
  }
  
  
}
