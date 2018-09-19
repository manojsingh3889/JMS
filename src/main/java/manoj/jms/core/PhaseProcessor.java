package manoj.jms.core;

import javax.jms.Message;

/**
 * Phase processor : hook for pre/post processing of message received by Consumer or producer
 * @author manoj
 *
 */
public interface PhaseProcessor {
	void process(Message message);
}
