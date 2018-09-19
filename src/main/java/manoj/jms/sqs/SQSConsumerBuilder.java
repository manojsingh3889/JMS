package manoj.jms.sqs;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.naming.NamingException;

import manoj.jms.core.JMSQueueConnection;
import org.apache.log4j.Logger;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnection;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import manoj.jms.core.ConsumerBuilder;
import manoj.jms.core.MessageConsumer;

public class SQSConsumerBuilder implements ConsumerBuilder<SQSResourceBuilderContext>{
	private static final Logger LOGGER = Logger.getLogger(SQSConsumerBuilder.class);
	private SQSResourceBuilderContext context;
	
	public Object build(String queueName) throws NamingException, JMSException {
        // Create the connection factory based on the context
		LOGGER.info("Obtaing SQSConnectionFactory using aws on region "+context.getRegion().getName());
		AmazonSQSClientBuilder amazonSQSClientBuilder = AmazonSQSClientBuilder.standard()
                .withRegion(context.getRegion().getName())
                .withCredentials(context.getCredentialsProvider());
                
        SQSConnectionFactory connectionFactory = new SQSConnectionFactory(
                new ProviderConfiguration(),amazonSQSClientBuilder);

        // Create the connection
        LOGGER.info("Obtaining QueueConnection using (access="+context.getCredentialsProvider().getCredentials().getAWSAccessKeyId()+
        		", secret=******");	
        SQSConnection connection = connectionFactory.createConnection();
         
        // Create the session
        LOGGER.info("creating session on SQSConnectionFactory, Session.AUTO_ACKNOWLEDGE:false");
        Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        LOGGER.info("Obtaining Destination Queue from connection "+queueName);
        javax.jms.MessageConsumer consumer = session.createConsumer( session.createQueue(queueName) );
        
        //adding post processor
        SQSPostPhaseProcessor postPhaseProcessor = new SQSPostPhaseProcessor(amazonSQSClientBuilder);
        MessageConsumer callback = new MessageConsumer(null,postPhaseProcessor);
        consumer.setMessageListener(callback);

        // No messages are processed until this is called
        connection.start();

        //make a Consumer Connection list
        JMSQueueConnection.getConsumerQconnections().add(connection);
        LOGGER.info("Consumer for "+queueName +" ONLINE : "+Boolean.TRUE);
		return consumer;
	}

	@Override
	public void setContext(SQSResourceBuilderContext context) {
		this.context = context;
	}
}
