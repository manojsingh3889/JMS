package manoj.jms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import manoj.jms.core.ConsumerBuilder;
import manoj.jms.core.MessageProducer;
import manoj.jms.core.ProducerBuilder;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

public abstract class JMS {
	public static Map<String, MessageProducer> producers = new HashMap<>();
	private static final Logger LOGGER = Logger.getLogger(JMS.class);
	
	protected ConsumerBuilder consumerBuilder;
	public void setConsumerBuilder(ConsumerBuilder consumerBuilder) {
		this.consumerBuilder = consumerBuilder;
	}

	protected ProducerBuilder producerBuilder;
	public void setProducerBuilder(ProducerBuilder producerBuilder) {
		this.producerBuilder = producerBuilder;
	}

	public abstract void start(PropertiesConfiguration prop);

	protected void constructConsumers(PropertiesConfiguration prop){
		//get list of queue which are supposed to be consumed
		String[] strings = prop.getStringArray("consumers");
		for(String queue: strings){
			try {
				consumerBuilder.build(queue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	protected void constructProducers(PropertiesConfiguration prop) {
		Iterator<String> it = prop.getKeys("producer");

		while(it.hasNext()){
			try {
				String key=it.next();
				MessageProducer producer = (MessageProducer)producerBuilder.build(prop.getString(key));
				LOGGER.info("Type of producer "+key+" built.");
				JMS.producers.put(key, producer);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
