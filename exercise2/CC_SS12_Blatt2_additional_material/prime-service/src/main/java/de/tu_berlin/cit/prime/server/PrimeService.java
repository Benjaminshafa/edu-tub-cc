package de.tu_berlin.cit.prime.server;

import javax.jws.WebService;

@WebService
public interface PrimeService {

	boolean isPrime(long number);
}
