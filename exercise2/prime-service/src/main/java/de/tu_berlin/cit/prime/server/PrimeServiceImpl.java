package de.tu_berlin.cit.prime.server;

import javax.jws.WebService;

@WebService(endpointInterface = "de.tu_berlin.cit.prime.server.PrimeService", serviceName = "PrimeService")
public class PrimeServiceImpl implements PrimeService {

	public boolean isPrime(long number) {
	  // IMPLEMENT ME
	  return false;
	}
}
