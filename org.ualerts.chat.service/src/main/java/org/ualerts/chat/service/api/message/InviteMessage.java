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

package org.ualerts.chat.service.api.message;

/**
 * This message type is used for notifying users of conversation invitations
 *
 * @author Ransom Roberson
 */
public class InviteMessage extends Message {
  private static final String TYPE = "INVITE";
  private String userIdentifier;
  @Override
  public String getType() {
    return TYPE;
  }
  
  /**
   * Returns the generated UserIdentifier for the invited user & converesation
   * @return full string user identifier (name@domain)
   */
  public String getUserIdentifier() {
    return userIdentifier;
  }
  
  /**
   * Sets the user identifier string
   * @param userId string user identifier
   */
  public void setUserIdentifier(String userId) {
    this.userIdentifier = userId;
  }
}
