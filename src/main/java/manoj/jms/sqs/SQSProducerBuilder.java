package manoj.jms.sqs;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import manoj.jms.core.ProducerBuilder;
import org.apache.log4j.Logger;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import manoj.jms.core.MessageProducer;

public class SQSProducerBuilder implements ProducerBuilder<SQSResourceBuilderContext> {
	private static final Logger LOGGER = Logger.getLogger(SQSProducerBuilder.class);
	private SQSResourceBuilderContext context;
	
	public Object build(String queueName) throws NamingException, JMSException {
		// Create the connection factory based on the config
		LOGGER.info("Obtaing SQSConnectionFactory using aws on region "+context.getRegion().getName());
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),
                AmazonSQSClientBuilder.standard()
                        .withRegion(context.getRegion().getName())
                        .withCredentials(context.getCredentialsProvider())
                );
        
        // Create the connection
        LOGGER.info("Obtaining QueueConnection using (access="+context.getCredentialsProvider().getCredentials().getAWSAccessKeyId()+
        		", secret=******");	
        SQSConnection connection = connectionFactory.createConnection();
        
        // Create the queue if needed
//        ExampleCommon.ensureQueueExists(connection, context.getQueueName());
            
        // Create the session
		LOGGER.info("creating session on SQSConnectionFactory, Session.AUTO_ACKNOWLEDGE:false");
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = new MessageProducer();
        LOGGER.info("Obtaining Destination Queue from connection "+queueName);
        producer.setQsender(session.createProducer(session.createQueue(queueName)));
        producer.setMsg(session.createTextMessage());
        producer.setObjectMessage(session.createObjectMessage());
        connection.start();
        LOGGER.info("Producer for "+queueName+" is ONLINE : "+Boolean.TRUE);
		return producer;
	}

	@Override
	public void setContext(SQSResourceBuilderContext context) {
		this.context = context;
	}
}
