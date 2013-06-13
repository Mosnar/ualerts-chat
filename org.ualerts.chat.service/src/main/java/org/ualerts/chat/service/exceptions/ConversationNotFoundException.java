package org.ualerts.chat.service.exceptions;

/**
 * Indicates that a conversation cannot be found
 * @author Billy Coleman
 * @author Ransom Roberson
 *
 */
public class ConversationNotFoundException extends Exception {

	private static final long serialVersionUID = 380920715794289221L;

	/**
	 * Constructs a new instance
	 */
	public ConversationNotFoundException() {
		super();
	}

	/**
	 * Constructs a new instance
	 * @param ex the exception that triggered this
	 */
	public ConversationNotFoundException(Exception ex) {
		super(ex);
	}


}
