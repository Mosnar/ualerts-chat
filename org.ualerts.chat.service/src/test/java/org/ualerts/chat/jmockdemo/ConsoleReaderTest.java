package org.ualerts.chat.jmockdemo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.alerts.chat.jmockdemo.ConsoleReader;
import org.alerts.chat.jmockdemo.DefaultConsoleReader;
import org.alerts.chat.jmockdemo.StringAction;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

public class ConsoleReaderTest {

	private Mockery context;
	private ConsoleReader consoleReader;
	
	@Before
	public void setUp() {
		context = new Mockery();
		consoleReader = new DefaultConsoleReader();
	}

	@Test
	public void testAddAction() {
		StringAction action = context.mock(StringAction.class);
		consoleReader.addAction(action);
		Set<StringAction> actions = consoleReader.getActions();
		assertEquals(1, actions.size());
		assertTrue(actions.contains(action));
	}
	
	@Test
	public void testDeliverMessage() {
		final StringAction action = context.mock(StringAction.class);
		consoleReader.addAction(action);
		final String message = "Hi there";
		
		context.checking(new Expectations() { { 
			oneOf(action).workOnString(message);
		//	will(throwException(throwable))
		} });
		
		consoleReader.deliverMessage(message);
		context.assertIsSatisfied();
	}
}
