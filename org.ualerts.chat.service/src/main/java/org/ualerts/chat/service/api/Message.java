package org.ualerts.chat.service.api;

import java.util.Calendar;

/**
 * Represents a Message
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public interface Message {
	
	Object getMessageBody();
	
	void setMessageBody();
	
	String getFrom();
	void setFrom(String from);
	
	String getTo();
	void setTo(String to);
	
	String getType();
	void setType(String type);
	
	Calendar getMessageDate();
	void setMessageDate(Calendar messageDate);
	

}
