package manoj.jms.core;

import javax.jms.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by stpl on 18/5/18.
 */
public abstract class JMSQueueConnection {


    private static List<Connection> consumerQconnections =new ArrayList<>();

    public static List<Connection> getConsumerQconnections() {
        return consumerQconnections;
    }



}
