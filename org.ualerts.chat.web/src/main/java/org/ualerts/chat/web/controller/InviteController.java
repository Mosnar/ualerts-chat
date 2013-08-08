/*
 * File created on Aug 5, 2013
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

import org.omg.CORBA.UserException;
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
 * This controller will handle invitation delivery
 * 
 * @author Ransom Roberson
 */
@Controller
public class InviteController {

  private final String VALID = "{\"result\":\"valid\"}";
  private final String INVALID = "{\"result\":\"invalid\"}";;

  private ChatService chatService;
  private UserService userService;

  /**
   * Invites a user to a conversation based on a generated user ID
   * @param userIdentifier
   * @return valid or invalid JSON POST response
   */
  @RequestMapping(value = "/sendInvite", method = RequestMethod.POST)
  @ResponseBody
  public String sendInvite(
      @RequestParam("userIdentifier") String userIdentifier) {
    try {
      UserIdentifier userId = new UserIdentifier(userIdentifier);
      chatService.inviteUser(userId);
      return VALID;
    }
    catch (IllegalArgumentException e) {
      // User not found
      return INVALID;
    }
    catch (UserException e) {
      // User ID didn't have proper format. (user@domain) Only 1 "@" symbol!
      return INVALID;
    }
  }

  /**
   * Moves a participant from the invited state to the active state
   * @return VALID or INVALID
   */
  @RequestMapping(value = "/acceptInvite", method = RequestMethod.POST)
  @ResponseBody
  public String acceptInvite(
      @RequestParam("userIdentifier") String userIdentifier) {
    UserIdentifier userId = new UserIdentifier(userIdentifier);
    Conversation conversation = chatService.getConversation(userId);
    if (conversation != null) {
      chatService.joinConversation(userId, false);
      return VALID;
    }
    else {
      return INVALID;
    }
  }

  @Autowired
  public void setChatService(ChatService chatService) {
    this.chatService = chatService;
  }

  @Autowired
  public void setUserService(UserService userService) {
    this.userService = userService;
  }
}
