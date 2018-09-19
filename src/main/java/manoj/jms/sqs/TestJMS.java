package manoj.jms.sqs;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;


public class TestJMS{

//	@Resource(lookup = "java:/ConnectionFactory")
//	ConnectionFactory cf;

//	@Resource(lookup = "java:jboss/activemq/queue/TestQueue")
//	private Queue queue;

    private static final Logger Log = Logger.getLogger(TestJMS.class);
	public void example() throws Exception {

		InitialContext ctx = new InitialContext();
		ConnectionFactory cf = null;
		Connection connection =  null;
		Queue queue = null;
		try {         
			cf = (ConnectionFactory) ctx.lookup("java:/ConnectionFactory");
			queue = (Queue) ctx.lookup("queues/myQueue");
			
			connection = cf.createConnection();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			MessageProducer publisher = session.createProducer(queue);

			connection.start();

			TextMessage message = session.createTextMessage("Hello!");
			publisher.send(message);  
		}finally{         
			if(connection != null){
				try{
					connection.close();
				}catch(Exception e) {
					throw e;
				}
			}  
			closeConnection(connection);
		}
	}
	private void closeConnection(Connection con)            {      
		try  {
			if (con != null) {
				con.close();
			}         
		}
		catch(JMSException jmse) {
			Log.info("Could not close connection " + con +" exception was " + jmse);
		}   
	}
}