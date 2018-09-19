package manoj.jms.core;

import javax.jms.ExceptionListener;
import javax.jms.JMSException;

import org.apache.log4j.Logger;

public class CustomExceptionListener implements ExceptionListener {
	private static final Logger LOGGER = Logger.getLogger(CustomExceptionListener.class);
	@Override
	public void onException(JMSException arg0) {
		LOGGER.error("Exception Listener Invoked", arg0);
	}
}
