package de.tu_berlin.cit.prime.server;

import org.apache.cxf.frontend.ServerFactoryBean;

public class Main extends Thread {

	public static void main(String args[]) throws java.lang.Exception {

		Object implementor = new PrimeServiceImpl();

		ServerFactoryBean svrFactory = new ServerFactoryBean();
		svrFactory.setServiceClass(PrimeService.class);
		svrFactory.setAddress("http://0.0.0.0:9000/PrimeService");
		svrFactory.setServiceBean(implementor);
		svrFactory.create();
	}
}
