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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
 
  /**
   * Create a new Conversation 
   * @param the name of the new Conversation
   * @return json String indicating result
   */
  
  @RequestMapping (value = "/createNewConversation", method = RequestMethod.POST)
  @ResponseBody
  public String createConversation(@RequestParam("conversationName") String conversationName) {
    if (true) {
      //TODO  Implementation to follow in later task ACE-72
      System.out.println("in createConversation");
      return this.VALID;
    }
    else {
      return this.INVALID;
    }
  }
}
