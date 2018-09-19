package manoj.jms.simple;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import manoj.jms.core.JMSQueueConnection;
import manoj.jms.core.ConsumerBuilder;
import manoj.jms.core.CustomExceptionListener;
import manoj.jms.core.MessageConsumer;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SimpleConsumerBuilder implements ConsumerBuilder<SimpleResourceBuilderContext> {
	private static final Logger LOGGER = Logger.getLogger(SimpleConsumerBuilder.class);
	private SimpleResourceBuilderContext context;
	
	public Object build(String queueName) throws NamingException, JMSException {
		QueueConnectionFactory qconFactory;
		QueueConnection qcon;
		QueueSession qsession;
		QueueReceiver qreceiver;
		Queue queue;

		InitialContext ic = new InitialContext();
		LOGGER.info("Obtaing QueueConnectionFactory using JNDI Lookup for "+context.getQueueConnectionFactory());
		qconFactory = (QueueConnectionFactory) ic.lookup(context.getQueueConnectionFactory());

		if(StringUtils.isNoneEmpty(context.getJmsUser()) && StringUtils.isNoneEmpty(context.getJmsPassword())){
			LOGGER.info("Obtaining QueueConnection using (user="+context.getJmsUser()+", password=******");	
			qcon = qconFactory.createQueueConnection(context.getJmsUser(),context.getJmsPassword());
		}else{
			LOGGER.info("Obtaining QueueConnection without any user");
			qcon = qconFactory.createQueueConnection();
		}

		LOGGER.info("creating session on QueueConnection, Session.AUTO_ACKNOWLEDGE:false");
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		LOGGER.info("Obtaing Destination Queue using JNDI lookup for "+queueName);
		queue = (Queue) ic.lookup(queueName);
		qreceiver = qsession.createReceiver(queue);
		
		MessageConsumer consumer = new MessageConsumer(null,null);
		qreceiver.setMessageListener(consumer);
		qcon.setExceptionListener(new CustomExceptionListener());
		qcon.start();


		//make a consumer connection list
		JMSQueueConnection.getConsumerQconnections().add(qcon);
		LOGGER.info("Producer for "+queueName +" ONLINE : "+Boolean.TRUE);
		return consumer;
	}

	@Override
	public void setContext(SimpleResourceBuilderContext context) {
		this.context = context;
	}
}

