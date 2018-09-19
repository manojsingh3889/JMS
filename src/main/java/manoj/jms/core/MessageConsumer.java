package manoj.jms.core;

import java.lang.reflect.Method;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

public class MessageConsumer implements MessageListener {
	private static final Logger LOGGER = Logger.getLogger(MessageConsumer.class);
	private PhaseProcessor prePhaseProcessor;
	private PhaseProcessor postPhaseProcessor;

	public MessageConsumer(PhaseProcessor prePhaseProcessor, PhaseProcessor postPhaseProcessor) {
		this.prePhaseProcessor = prePhaseProcessor;
		this.postPhaseProcessor = postPhaseProcessor;
	}

	public void onMessage(Message message)
	{	
		//pre-processing
		if(prePhaseProcessor!=null)
			prePhaseProcessor.process(message);
		
		try {
			if(message instanceof ObjectMessage){

				ObjectMessage objectMessage = (ObjectMessage) message; 
				LOGGER.info("Recieved JMS message : "+objectMessage.getObject());

				if(objectMessage.getObject() instanceof JMSMessage){
					JMSMessage jmsMessage = (JMSMessage) objectMessage.getObject();
					Object o = jmsMessage.getTargetClass().newInstance();
					
					@SuppressWarnings("unchecked")
					Method method = jmsMessage.getTargetClass()
							.getMethod(jmsMessage.getTargetMethod(),jmsMessage.getParamTypes());
					
					method.invoke(o, jmsMessage.getArgs());
				}else if(objectMessage.getObject() instanceof RunnableJMSmessage){
					RunnableJMSmessage runnableJMSmessage = (RunnableJMSmessage) objectMessage.getObject();
					runnableJMSmessage.run();
				}else{
							throw new IllegalArgumentException("Expected Message type are "+JMSMessage.class
									+" and "+RunnableJMSmessage.class);
				}
				LOGGER.info("Finished JMS message : "+objectMessage.getObject().getClass());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//post-processing
			if(postPhaseProcessor!=null)
				postPhaseProcessor.process(message);
		}
	}
}
