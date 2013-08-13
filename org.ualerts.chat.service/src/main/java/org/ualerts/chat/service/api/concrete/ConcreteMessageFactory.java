/*
 * File created on Aug 12, 2013
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

import org.springframework.stereotype.Service;
import org.ualerts.chat.service.api.UserIdentifier;
import org.ualerts.chat.service.api.message.InviteMessage;
import org.ualerts.chat.service.api.message.MessageFactory;

/**
 * DESCRIBE THE TYPE HERE.
 *
 * @author Brandon Foster
 */
@Service
public class ConcreteMessageFactory implements MessageFactory {

  /**
   * {@inheritDoc}
   */
  @Override
  public InviteMessage newInviteMessage(UserIdentifier userIdentifier) {
    InviteMessage invite = new InviteMessage();
    invite.setFrom(userIdentifier.getName());
    invite.setSubType(userIdentifier.getDomain());
    invite.setTo(userIdentifier.getFullIdentifier());
    invite.setUserIdentifier(userIdentifier.getFullIdentifier());
    return invite;
  }


}
