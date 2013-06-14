package org.alerts.chat.jmockdemo;

import java.util.Set;


public interface ConsoleReader {

	/**
	 * Register a StringAction
	 * @param action
	 */
	void addAction(StringAction action);
	
	Set<StringAction> getActions();
	
	/**
	 * During run, the reader will read from the console and pass the read
	 * data to each registered StringAction.
	 */
	void deliverMessage(String message);
	
}
