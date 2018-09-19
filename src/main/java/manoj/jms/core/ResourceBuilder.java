package manoj.jms.core;

public interface ResourceBuilder<T extends ResourceBuilderContext> {
	public void setContext(T context);
	public Object build(String queue) throws Exception;
}
