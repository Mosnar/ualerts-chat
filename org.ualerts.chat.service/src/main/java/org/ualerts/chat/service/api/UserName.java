/*
 * File created on Jun 25, 2013
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
 * Represents a user name
 *
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class UserName {
  
  public static final UserName NULL_USER = new UserName();
  
  private String name;
  
  public UserName(String userName){
    if(userName == null || userName.trim().isEmpty())
    {
      throw new IllegalArgumentException();
    }
    this.name = userName;
  }
  
  protected UserName() {

  }

  public String getName() {
    return name;
  }
    
}
