package manoj.jms.core;

import java.io.Serializable;

public class JMSMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Class targetClass;
	private String targetMethod;
	private Object[] args;
	private Class[] paramTypes;
	
	public JMSMessage(Class targetClass, String targetMethod, Object[] args, Class[] paramTypes) {
		this.targetClass = targetClass;
		this.targetMethod = targetMethod;
		this.args = args;
		this.paramTypes = paramTypes;
	}

	public Class getTargetClass() {
		return targetClass;
	}

	public void setTargetClass(Class targetClass) {
		this.targetClass = targetClass;
	}

	public String getTargetMethod() {
		return targetMethod;
	}

	public void setTargetMethod(String targetMethod) {
		this.targetMethod = targetMethod;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public Class[] getParamTypes() {
		return paramTypes;
	}

	public void setParamTypes(Class[] paramTypes) {
		this.paramTypes = paramTypes;
	}
}
