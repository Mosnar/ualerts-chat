/*
 * File created on Jul 5, 2013
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

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;
import org.ualerts.chat.service.api.UserNameConflictException;
import org.ualerts.chat.service.api.UserService;
/**
* This test will validate the NameCheckController
*
* @author Ransom Roberson
*/
public class NameCheckControllerTest {
  private Mockery context;
  private NameCheckController nameCheckController;

  private final String USER_NAME1 = "Test1";
  private final String VALID = "{\"result\":\"valid\"}";
  private final String INVALID = "{\"result\":\"invalid\"}";
  private final String DOMAIN = "@ualerts.chat";

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    nameCheckController = new NameCheckController();
  }

  @Test
  public void testChatNameValid() throws Exception {
    final UserService userService = context.mock(UserService.class);

    context.checking(new Expectations() {
      {
        oneOf(userService).setUserName(USER_NAME1,DOMAIN);
      }

    });
    nameCheckController.setUserService(userService);
    assertEquals(VALID, nameCheckController.checkName(USER_NAME1));
    context.assertIsSatisfied();
  }

  @Test
  public void testCheckNameInvalid() throws Exception {
    final UserService userService = context.mock(UserService.class);

    context.checking(new Expectations() {
      {
        oneOf(userService).setUserName(USER_NAME1,DOMAIN);
        will(throwException(new UserNameConflictException("error")));
      }

    });
    nameCheckController.setUserService(userService);
    assertEquals(INVALID, nameCheckController.checkName(USER_NAME1),DOMAIN);
    context.assertIsSatisfied();
  }

}