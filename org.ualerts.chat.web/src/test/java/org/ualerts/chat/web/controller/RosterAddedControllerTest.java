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
import org.ualerts.chat.service.api.UserService;

/**
 * This test will validate the RosterAddedController functionality
 * 
 * @author Ransom Roberson
 */
public class RosterAddedControllerTest {
  private Mockery context;
  private RosterAddedController rosterAddedController;

  private final String USER_NAME = "Test1@org.ualerts.chat";

  @Before
  public void setUp() throws Exception {
    context = new Mockery();
    rosterAddedController = new RosterAddedController();
  }

  @Test
  public void testLogin() throws Exception {
    final UserService userService = context.mock(UserService.class);

    context.checking(new Expectations() {
      {
        oneOf(userService).login();
        will(returnValue(USER_NAME));
      }

    });
    rosterAddedController.setUserService(userService);
    assertEquals(USER_NAME, rosterAddedController.login());
    context.assertIsSatisfied();
  }

}
