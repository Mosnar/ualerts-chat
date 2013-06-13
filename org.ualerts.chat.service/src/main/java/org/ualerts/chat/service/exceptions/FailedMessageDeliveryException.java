package org.ualerts.chat.service.exceptions;

/**
 * Indicates that a message could not be delivered to intented
 * recipients
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public class FailedMessageDeliveryException extends Exception {

	/**
	 * Constructs a new instance
	 */
	public FailedMessageDeliveryException() {
		super();
	}

	/**
	 * Constructs a new instance
	 * @param e the exception that triggered this
	 */
	public FailedMessageDeliveryException(Exception e) {
		super(e);
		// TODO Auto-generated constructor stub
	}

	
}
