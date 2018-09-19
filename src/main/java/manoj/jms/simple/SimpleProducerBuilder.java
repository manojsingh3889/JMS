package manoj.jms.simple;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import manoj.jms.core.CustomExceptionListener;
import manoj.jms.core.MessageProducer;
import manoj.jms.core.ProducerBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

public class SimpleProducerBuilder implements ProducerBuilder<SimpleResourceBuilderContext> {
	private static final Logger LOGGER = Logger.getLogger(SimpleProducerBuilder.class);
	private SimpleResourceBuilderContext context;
	
	public Object build(String queueName) throws NamingException, JMSException {
		QueueConnectionFactory qconFactory;
		QueueConnection qcon;
		QueueSession qsession;
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
		
		MessageProducer sender = new MessageProducer();
		sender.setQsender(qsession.createSender(queue));
		sender.setMsg(qsession.createTextMessage());
		sender.setObjectMessage(qsession.createObjectMessage());

		qcon.setExceptionListener(new CustomExceptionListener());
		qcon.start();
		LOGGER.info("Producer for "+queueName+" is ONLINE : "+Boolean.TRUE);
		return sender;
	}

	@Override
	public void setContext(SimpleResourceBuilderContext context) {
		this.context = context;
	}
}
