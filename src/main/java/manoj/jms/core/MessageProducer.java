package manoj.jms.core;

import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.QueueConnection;
import javax.jms.QueueSession;
import javax.jms.TextMessage;

import org.apache.log4j.Logger;



public class MessageProducer
{
	private static final Logger LOGGER = Logger.getLogger(MessageProducer.class);

	private QueueConnection qcon;
	private QueueSession qsession;
	private javax.jms.MessageProducer qsender;
	private TextMessage msg;
	private ObjectMessage objectMessage;

	public void send(String message) throws JMSException {
		LOGGER.info("Sending text/json/flat message "+message);
		msg.setText(message);
		qsender.send(msg);
	}

	public void send(JMSMessage message) throws JMSException {
		LOGGER.info("Sending JMS message "+message);
		objectMessage.setObject(message);
		qsender.send(objectMessage);
	}

	public void send(RunnableJMSmessage runnable) throws JMSException{
		LOGGER.info("Sending JMS runnable message "+runnable);
		objectMessage.setObject(runnable);
		qsender.send(objectMessage);
	}

	public void close() throws JMSException {
		qsender.close();
		qsession.close();
		qcon.close();
	}

	public MessageProducer setMessage(JMSMessage message) throws JMSException{
		this.objectMessage.setObject(message);
		return this;
	}

	public ObjectMessage getObjectMessage() {
		return objectMessage;
	}

	public void submit(Class targetClass, String targetMethod, Object[] args) throws Exception{
		if (getObjectMessage() != null) {
			
			Class[] argTypes = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				argTypes[i] = args[i].getClass();
			}
			
			JMSMessage message = new JMSMessage(targetClass, targetMethod, args, argTypes);

				send(message);


		}else{
			throw new Exception("Queue down");
		}
	}

	public void submit(RunnableJMSmessage runnable) throws Exception{
		if (this.getObjectMessage() != null) {
				send(runnable);
		}else{
			throw new Exception("Queue Down");
		}
	}

	public void setQsession(QueueSession qsession) {
		this.qsession = qsession;
	}

	public void setQsender(javax.jms.MessageProducer qsender) {
		this.qsender = qsender;
	}

	public void setMsg(TextMessage msg) {
		this.msg = msg;
	}

	public void setObjectMessage(ObjectMessage objectMessage) {
		this.objectMessage = objectMessage;
	}
}
