package manoj.jms;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;

//import org.apache.log4j.Logger;


public class MainClass {
	private static String FILENAME = "JMS.properties";
	private static String JMS_TYPE = "JMS_TYPE";

	public static boolean RECIEVER_ONLINE = false;
	/*connection factory name*/
	public final static String JMS_FACTORY="java:/ConnectionFactory";
	/*queue destination name used as buffer storage for generic message*/
	public final static String QUEUE="MyQueue";

	//	private static final Logger LOGGER = Logger.getLogger(JMSInit.class);

	static final Map<String,String> config = new HashMap<>();

	public static void main(String...a) {

		try {
			PropertiesConfiguration config = createConfig();
			
			String jmsTypeStr = config.getString(JMS_TYPE);
			
			if(StringUtils.isEmpty(jmsTypeStr))
				throw new IllegalArgumentException("JMS type not defined");
			
			Class<?> jmsType = Class.forName("com.stpl.jms."+config.getString(JMS_TYPE));
			JMS jms = (JMS) jmsType.newInstance();
			jms.start(config);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (ConfigurationException e) {
			e.printStackTrace();
		}catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private static PropertiesConfiguration createConfig() throws ConfigurationException{
		PropertiesConfiguration configuration = new PropertiesConfiguration(MainClass.class.getClassLoader().getResource(FILENAME));
		return configuration;
	}
}
