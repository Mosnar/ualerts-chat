/*
 * File created on Jul 23, 2013
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

package org.ualerts.chat.service.api;

/**
 * This interface manages user connections
 *
 * @author Ransom Roberson
 * @author Brandon Foster
 */
public interface UserService {

  /**
   * Attempt to set the user's name on the chatclient
   * @param name new name
   * @throws UserNameConflictException 
   */
  public void setUserName(String name, String domain) throws UserNameConflictException;
  
  /**
   * Performs user finalizing actions
   * @return UserIdentifier generated
   */
  public String login();
  
  /**
   * Removes the user from its conversations
   */
  public void logout();
  
  /**
   * Finds a ChatClient by their name
   * @param name search name
   * @return ChatClient located
   */
  public ChatClient findClient(String name);
  
  /**
   * Finds a ChatClient by their unique ID
   * @param id unique ID
   * @return ChatClient found
   */
  public ChatClient findClientById(String id);
  
  /**
   * Returns the default domain
   * @return the default domain
   */
  public String getDefaultDomain();
  
}
