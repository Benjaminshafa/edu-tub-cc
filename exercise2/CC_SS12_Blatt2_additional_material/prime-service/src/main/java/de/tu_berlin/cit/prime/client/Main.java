package de.tu_berlin.cit.prime.client;

import java.net.URL;

import javax.xml.namespace.QName;


import java.lang.Thread;
import java.util.Random;

public class Main extends Thread {

	private static final QName SERVICE_NAME = new QName(
			"http://server.prime.cit.tu_berlin.de/", "PrimeService");
	private static URL wsdlURL;
	
	private static final int pause=100;
	

	public static void main(String[] args) throws Exception {
		
		if(args.length < 2){
			printUsage();
		}else{
			wsdlURL = new URL(args[0] + "?wsdl");
			int numberThreads = Integer.parseInt(args[1]);
			
			Thread[] threads = new Thread[numberThreads];
			
			for(int i=0; i< numberThreads; i++){
				threads[i] = new Main();
				threads[i].start();
			}
		}
		
	}

	private static void printUsage() {
		System.out.println("Usage: prime-client <service-endpoint> <no-of-threads>");
		System.err.println("\tservice-endpoint\tthe service endpoint, for example http://localhost:9000/PrimeService");
		System.out.println("\tno-of-threads\tNo of threads performing synchronous requests.");
	}
	
	@Override
	public void run(){
		PrimeService ss = new PrimeService(wsdlURL,SERVICE_NAME);
		PrimeServicePortType port = ss.getPrimeServicePort();
		boolean result=false;
		
		Random r = new Random();
		while(true){
			long number = Math.abs(r.nextLong());
			System.out.println("ID:" + Thread.currentThread().getId() + " checking number:" + number);
			try{
				result = port.isPrime(number);
			}catch(Throwable t){
				System.out.println("Timeout received");
			}
			
			System.out.println("ID:" + Thread.currentThread().getId() +" number:" + number + " is prime:" + result);
			
			try {
				sleep(pause);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
