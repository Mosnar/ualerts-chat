package org.alerts.chat.jmockdemo;

import java.util.HashSet;
import java.util.Set;

public class DefaultConsoleReader implements ConsoleReader {

	private Set<StringAction> actions = new HashSet<StringAction>();
	
	@Override
	public void addAction(StringAction action) {
		actions.add(action);
	}
	
	@Override
	public Set<StringAction> getActions() {
		return actions;
	}
	
	@Override
	public void deliverMessage(String message) {
		for (StringAction action : actions) {
			action.workOnString(message);
		}
	}

}
