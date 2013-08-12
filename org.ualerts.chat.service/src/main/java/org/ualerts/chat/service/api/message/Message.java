/*
 * File created on Jun 17, 2013
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

import java.util.Date;
/**
 * Abstract class representing a general chat message
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */
public abstract class Message {
	
	
	String from, to, type, subType;
	Date messageDate;
	
	/**
	 * Get the from value
	 * @return the value identifying who this Message is from
	 */
	public String getFrom() {
		return from;
	}
	/**
	 * Set the from value
	 * @param the value identifying who this Message is from
	 */
	public void setFrom(String from) {
		this.from = from;
	}
	
	/**
	 * Get the to value
	 * @return the value identifying who this message is to
	 */
	public String getTo() {
		return to;
	}
	
	/**
	 * Set the to value
	 * @param the value identifying who this message is to
	 */
	public void setTo(String to) {
		this.to = to;
	}
	
	/**
	 * Get the type of message
	 * @return the value representing the type of message
	 */
	public  String getType() {
	  return this.type;
	}
	
	/**
	 * Get the subType of message 
	 * @return the value representing the subType of message
	 */
	public String getSubType() {
	  return this.subType;
	}
	
	/**
	 * Set the subType of message
	 * @param the value representing the subType of message
	 */
  public void setSubType(String subType) {
    this.subType = subType;
  }
  
  /**
   * Set the type of message
   * @param the value representing the type of message
   */
  public void setType(String type) {
		this.type = type;
	}
  
  /**
   * Get the date of this message
   * @return message date
   */
	public Date getMessageDate() {
		return messageDate;
	}
	
	/**
	 * Set the date of this message
	 * @param messageDate
	 */
	public void setMessageDate(Date messageDate) {
		this.messageDate = messageDate;
	}
}
