package manoj.jms;

import manoj.jms.sqs.SQSConsumerBuilder;
import manoj.jms.sqs.SQSResourceBuilderContext;
import org.apache.commons.configuration.PropertiesConfiguration;

import manoj.jms.sqs.SQSProducerBuilder;

public class SQS extends JMS {

//	private static final Logger LOGGER = Logger.getLogger(SQS.class);

	@Override
	public void start(PropertiesConfiguration prop) {
		try {
			SQSResourceBuilderContext context = SQSResourceBuilderContext.parseConfig(prop);
			
			//Receiver builder
			SQSConsumerBuilder consumerBuilder = new SQSConsumerBuilder();
			consumerBuilder.setContext(context);
			setConsumerBuilder(consumerBuilder);
			
			//Sender builder
			SQSProducerBuilder producerBuilder = new SQSProducerBuilder();
			producerBuilder.setContext(context);
			setProducerBuilder(producerBuilder);
			
			//start construction
			constructConsumers(prop);
			constructProducers(prop);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}