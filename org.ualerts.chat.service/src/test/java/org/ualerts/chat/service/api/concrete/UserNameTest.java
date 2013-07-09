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

package org.ualerts.chat.service.api.concrete;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import org.junit.Test;
import org.ualerts.chat.service.api.UserName;

/**
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class UserNameTest {

  @Test
  public void testUserNameNotNull() {
    UserName userName = new UserName("Test");
    assertFalse(userName == UserName.NULL_USER);
  }

  @Test
  public void testUserNameNull() {
    UserName userName = UserName.NULL_USER;
    assertTrue(userName == UserName.NULL_USER);
  }

  @Test
  public void testMatchesTrue() {
    UserName userName = new UserName("TeSt");
    assertTrue(userName.matches("test"));
  }

  @Test
  public void testMatchesFalse() {
    UserName userName = new UserName("test");
    assertFalse(userName.matches("testing"));
  }

  @Test
  public void testEqualsTrue() {
    UserName userName = new UserName("Test");
    UserName userName2 = new UserName("Test");
    assertEquals(userName, userName2);
  }
  
  @Test
  public void testEqualsFalse() {
    UserName userName = new UserName("Test");
    UserName userName2 = new UserName("Test2");
    assertNotEquals(userName, userName2);
  }
}
