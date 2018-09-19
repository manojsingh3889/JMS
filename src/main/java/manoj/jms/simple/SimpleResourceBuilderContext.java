package manoj.jms.simple;

import manoj.jms.core.ResourceBuilderContext;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.StringUtils;


public class SimpleResourceBuilderContext implements ResourceBuilderContext {
	
	private String queueConnectionFactory;
	private String jmsUser;
	private String jmsPassword;
	
	public SimpleResourceBuilderContext(String queueConnectionFactory, String jmsUser, String jmsPassword) {
		this.queueConnectionFactory = queueConnectionFactory;
		this.jmsUser = jmsUser;
		this.jmsPassword = jmsPassword;
	}
	
	public String getQueueConnectionFactory() {
		return queueConnectionFactory;
	}
	public String getJmsUser() {
		return jmsUser;
	}
	public String getJmsPassword() {
		return jmsPassword;
	}
	
	public static SimpleResourceBuilderContext parseConfig(PropertiesConfiguration prop) throws Exception {
		String connFactory = prop.getString("SimpleJMS.JMS_CONN_FACTORY");
		String user = prop.getString("SimpleJMS.JMS_USER");
		String pass = prop.getString("SimpleJMS.JMS_PASSWORD");
		
		if(StringUtils.isEmpty(connFactory))
			throw new IllegalArgumentException("JMS_CONN_FACTORY is missing. Please add SimpleJMS.JMS_CONN_FACTORY in property file.");
		
		return new SimpleResourceBuilderContext(connFactory, user, pass);
	}
	
}
