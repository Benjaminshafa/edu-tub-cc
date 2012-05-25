package de.tu_berlin.cit.prime.server;

import javax.jws.WebService;
import java.lang.Math;

@WebService(endpointInterface = "de.tu_berlin.cit.prime.server.PrimeService", serviceName = "PrimeService")
public class PrimeServiceImpl implements PrimeService {

	public boolean isPrime(long number) {
		long bound = Math.round(Math.sqrt(number))+1;
		for(int i =2; i<= bound; i++){
			if(number%i ==0){
				return false;
			}
		}
		return true;
	}
}
