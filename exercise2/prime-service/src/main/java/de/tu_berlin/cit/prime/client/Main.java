package de.tu_berlin.cit.prime.client;

import java.net.URL;

import javax.xml.namespace.QName;

public class Main {

	private static final QName SERVICE_NAME = new QName(
			"http://server.prime.cit.tu_berlin.de/", "PrimeService");

	public static void main(String[] args) throws Exception {

	  // SAMPLE CODE
	  // URL wsdlURL = new URL(args[0] + "?wsdl");
	  // PrimeService ss = new PrimeService(wsdlURL, SERVICE_NAME);
	  // PrimeServicePortType port = ss.getPrimeServicePort();
	  // port.isPrime(number);

	  printUsage();
	}

	private static void printUsage() {
		System.out.println("Usage: prime-client <service-endpoint> <no-of-threads>");
		System.err.println("\tservice-endpoint\tthe service endpoint, for example http://localhost:9000/PrimeService");
		System.out.println("\tno-of-threads\tNo of threads performing synchronous requests.");
	}
}
