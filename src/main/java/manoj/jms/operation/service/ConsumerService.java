package manoj.jms.operation.service;

import manoj.jms.core.JMSQueueConnection;
import org.apache.log4j.Logger;

import javax.jms.Connection;
import javax.jms.JMSException;
import java.util.List;

/**
 * Created by stpl on 18/5/18.
 */
public class ConsumerService {

    private static final Logger log = Logger
            .getLogger(ConsumerService.class);

    public void detachedFromQueue() throws Exception{
        List<Connection> consumerQConnections= JMSQueueConnection.getConsumerQconnections();
        for (Connection connection : consumerQConnections) {
            try {
                connection.stop();
            } catch (JMSException e) {
                throw e;
            }

            log.info("----Consumer Service is disable--");
        }
    }
}
