/**
 * 
 */
package manoj.jms.core;

/**
 * @author Manoj Singh
 *
 */
public class JMSJsonModelContainer {
	private String type;
	private JMSJsonModel data;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public JMSJsonModel getData() {
		return data;
	}
	public void setData(JMSJsonModel data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JMSModelContainer [type=" + type + ", data=" + data + "]";
	}
	
	
}
