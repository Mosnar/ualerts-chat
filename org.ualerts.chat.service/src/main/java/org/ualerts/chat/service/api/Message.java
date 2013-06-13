package org.ualerts.chat.service.api;

import java.util.Calendar;
/**
 * Abstract class representing a genaral chat message
 * @author Billy Coleman
 * @author Ransom Roberson
 * 
 */
public abstract class Message {
	
	String from, to, type;
	Calendar messageDate;
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Calendar getMessageDate() {
		return messageDate;
	}
	public void setMessageDate(Calendar messageDate) {
		this.messageDate = messageDate;
	}
	
	

}
