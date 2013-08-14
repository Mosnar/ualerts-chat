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

import org.apache.commons.lang.StringUtils;

/**
 * Represents a user name
 * 
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class UserIdentifier {

  public static final UserIdentifier NULL_USER = new UserIdentifier();

  private String name;
  private String domain;

  public UserIdentifier(String userName, String domainName) {
    if (StringUtils.isBlank(userName) || StringUtils.isBlank(domainName)) {
      throw new IllegalArgumentException();
    }
    this.name = userName;
    this.domain = domainName;
  }

  /**
   * Converts a full User ID string (user@domain) into a UserIdentifier object
   * Constructs a new instance.
   * @param fullUserId (user@domain)
   */
  public UserIdentifier(String fullUserId) {
    if (StringUtils.isBlank(fullUserId)) {
      throw new IllegalArgumentException();
    }
    String[] tokens = fullUserId.split("\\@", 2);
    if (tokens.length == 2) {
      this.name = tokens[0];
      this.domain = tokens[1];
    } else {
      throw new IllegalArgumentException();
    }
  }

  protected UserIdentifier() {

  }

  public String getName() {
    return name;
  }

  public String getDomain() {
    return domain;
  }

  public String getFullIdentifier() {
    return name + "@" + domain;
  }

  /**
   * Determines if this name's value is the same as the argument's value (trims
   * and ignores case)
   * @param name the name to compare to this
   * @return boolean
   */
  public boolean matches(String name) {
    if (this != NULL_USER) {
      if (this.name.trim().equalsIgnoreCase(name.trim()))
        return true;
    }

    return false;
  }

  /**
   * Determines if an object is equal to the UserName object based on its name
   * field.
   * @param Object to compare against
   * @return boolean
   */
  public boolean equals(Object obj) {
    if (!obj.getClass().isAssignableFrom(UserIdentifier.class))
      return false;
    UserIdentifier username = (UserIdentifier) obj;
    if (this.name.trim().equals(username.getName().trim())
        && this.domain.trim().equals(username.getDomain().trim())) {
      return true;
    }
    return false;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return getFullIdentifier();
  }

}