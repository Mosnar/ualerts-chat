/*
 * File created on Jun 27, 2013
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.ualerts.chat.service.api.ChatClient;
import org.ualerts.chat.service.api.Conversation;
import org.ualerts.chat.service.api.Participant;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.web.context.ChatClientContext;

/**
 * Controller for loggin in a chat client
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
@Controller
public class RosterAddedController {

  private final String VALID = "{\"result\":\"valid\"}";
  private final String INVALID = "{\"result\":\"invalid\"}";

  private ChatClientContext chatClientContext;
  private ChatClient chatClient;

  @RequestMapping(value="/submitName", method = RequestMethod.POST)
  @ResponseBody
  public String sendRosterAddedMessage() {
    chatClient = chatClientContext.getChatClient();
    Participant participant = chatClient.getParticipant();
    
    if (participant.getUserName() == UserIdentifier.NULL_USER) {
      return INVALID;
    }
    Conversation conversation = participant.getConversation();
    conversation.finalizeRegisterParticipant(participant.getUserName().getName());
    return VALID;
  }
  
  @Autowired
  public void setChatClientContext(ChatClientContext chatClientContext) {
    this.chatClientContext = chatClientContext;
  }
  
}


