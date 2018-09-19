package manoj.jms.proxies;

import manoj.jms.core.RunnableJMSmessage;

public class TestProxy implements RunnableJMSmessage {
    @Override
    public void run() throws Exception {
        System.out.println("running proxy message");
    }
}
