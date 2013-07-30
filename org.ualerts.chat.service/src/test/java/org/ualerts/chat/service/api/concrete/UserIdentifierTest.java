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
import org.ualerts.chat.service.api.UserIdentifier;

/**
 * @author Billy Coleman
 * @author Ransom Roberson
 */
public class UserIdentifierTest {

  @Test
  public void testUserNameNotNull() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    assertFalse(userName == UserIdentifier.NULL_USER);
  }

  @Test
  public void testUserIdentifierNull() {
    UserIdentifier userName = UserIdentifier.NULL_USER;
    assertTrue(userName == UserIdentifier.NULL_USER);
  }
  
  @Test
  public void testNullUser() {
    UserIdentifier userName = UserIdentifier.NULL_USER;
    assertFalse(userName.matches("Test"));
  }

  @Test
  public void testMatchesTrue() {
    UserIdentifier userName = new UserIdentifier("TeSt", "ualerts.org");
    assertTrue(userName.matches("test"));
  }

  @Test
  public void testMatchesFalse() {
    UserIdentifier userName = new UserIdentifier("test", "ualerts.org");
    assertFalse(userName.matches("testing"));
  }

  @Test
  public void testEqualsTrue() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    UserIdentifier userName2 = new UserIdentifier("Test", "ualerts.org");
    assertEquals(userName, userName2);
  }
  
  @Test
  public void testEqualsFalse() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    UserIdentifier userName2 = new UserIdentifier("Test2", "ualerts.org");
    assertNotEquals(userName, userName2);
  }
  
  @Test
  public void testEqualsFalseDomain() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    UserIdentifier userName2 = new UserIdentifier("Test", "alerts.org");
    assertNotEquals(userName, userName2);   
  }
  
  @Test
  public void testEqualsFalseWrongType() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    String userName2 = "Test";
    assertNotEquals(userName, userName2);   
  }
  
  @Test
  public void testGetFullIdentifier() {
    UserIdentifier userName = new UserIdentifier("Test", "ualerts.org");
    String expected = "Test@ualerts.org";
    assertEquals(expected, userName.getFullIdentifier());
  }
  
  @Test
  public void testFailedConstructor() {
    Exception exception = null;
    
    try {
      UserIdentifier userName = new UserIdentifier(null, null);
    }
    catch (Exception e) {
      exception = e;
    }
    assertTrue(exception instanceof IllegalArgumentException);
  }
}