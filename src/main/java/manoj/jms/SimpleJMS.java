package manoj.jms;

import org.apache.commons.configuration.PropertiesConfiguration;

import manoj.jms.simple.SimpleConsumerBuilder;
import manoj.jms.simple.SimpleProducerBuilder;
import manoj.jms.simple.SimpleResourceBuilderContext;

public class SimpleJMS extends JMS {

//	private static final Logger LOGGER = Logger.getLogger(SimpleJMS.class);

	@Override
	public void start(PropertiesConfiguration prop) {
		try {
			SimpleResourceBuilderContext context = SimpleResourceBuilderContext.parseConfig(prop);
			
			//Receiver builder
			SimpleConsumerBuilder consumerBuilder = new SimpleConsumerBuilder();
			consumerBuilder.setContext(context);
			setConsumerBuilder(consumerBuilder);
			
			//Sender builder
			SimpleProducerBuilder producerBuilder = new SimpleProducerBuilder();
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