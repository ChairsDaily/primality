
package org.eclipse.primality;

import java.lang.Math;
import java.util.ArrayList;


/**
 * Deterministic primality test, no randomness
 * Utility class, no instanciation
 * 
 * @author ChairsDaily
 * @license MIT
 * @email chairs.daily.2019@gmail.com
 */
public final class Prime2 {

	private static long[] smallPrimes = {
		
		// should be expanded with a prime number sieve
		2, 3, 5, 7, 11, 13, 17, 19, 
		23, 29, 31, 37, 41, 43, 47, 
		53, 59, 61, 67, 71, 73, 79, 
		83, 89, 97};
	
	/*
	 * Finds a divisor using smallPrimes as candidates
	 * @param .number target number
	 * @return .divisible was a divisor found
	 */
	private static boolean checkTable (long number) {
		
		for (long p : smallPrimes) 
			if (number % p == 0) return false;
		return true;
	}
	
	/*
	 * Finds a divisor using successive integers as candidates 
	 * NOTE - assumes divisibility for 2 and 3 has already been tested
	 * so that 5 numbers each iteration can be skipped safely
	 * @param .number target number
	 * @return .divisible was a divisor found
	 */
	private static boolean bruteForce (long number) {
		
		// current trial divisor
		long d = 5;
		do {
			if (number % d == 0 || number % (d + 2) == 0)
				return false;
			d += 6;
			
		} while (d < (int) Math.sqrt(number));
		
		return true;
	}
		
	/*
	 * Guesses primality probabilistically using a mathematically
	 * proved formula and set of witnesses to the compositeness of the number
	 * NOTE - if this returns true, it should be double checked by 
	 * bruteForce, if this returns false, trust and return
	 * @param .number target number
	 * @return .prime primality of the target number
	 */
	private static boolean millerRabinTest (long number) {
		
		
		double logn = Math.log(number);
		boolean prime = false;
		long m = number - 1;			
		long count = 0; // number of times 2 divides n
		
		int upper = 2 * (int) (logn * Math.log(logn)/Math.log(2));
		ArrayList<Long> witnesses = new ArrayList<Long>();
		
		// populate witness list with proven best range
		// of test coefficients
		for (long i = 2; i < upper + 1; i++) 
			witnesses.add(i);
		
		// remove all factors of 2 from m, not n
		do {
			m = (int) m / 2; count += 1;
		} while (m % 2 == 0);
		
		// move through witness list
		for (long a : witnesses) {
			
			long x = modularExponentation(a, m, number);
			boolean negative = false;
			
			// square the odd power x until we hit the (n-1)st power
			for (long j = 0; j < count + 1; j++) {
				
				if (x == 1) {
					prime = true; break;
				}
				else if (x == (number - 1)) {
					negative = true;
				}
			}
			if (prime) break;
			
			prime = negative;
		}
		return prime;
	}
	
	/*
	 * Gets length in digits of a number
	 * @param .number target number
	 * @return .size length in digits 
	 */
	private static int length (long number) {
		
		return (int) Math.log10(number) + 1;
	}
	
	/*
	 * Modular exponentation, NOTE - I did not write this function
	 * @param .base the base number
	 * @param .power the power to raise the base to
	 * @param .mod the modulo to operate in
	 * @return .result the result after mod exp.
	 */
	private static long modularExponentation (
			long base, 
			long power, 
			long mod) {
		
		long x = 1; long y = base;
		while (power > 0) {
			
			if (power % 2 == 0) {
				x = ( x * y) % mod;
			}
			y = (y * y) % mod;
			power /= 2;
		}
		return (int) x % mod;
	}
	
	/*
	 * Only public method in the class - isPrime
	 * @param .number target number
	 * @return .prime is the target number prime
	 */
	public static boolean isPrime (long number) {
		
		if (length(number) >= 10) {
			
			// this number is large. we should 
			// use all primality tests available
			// in the class to save time
			if (checkTable(number)) {
				
				// this doesnt mean number is prime
				// it just means it didnt have a divisor
				// in our table of pre-computed primes
				if (millerRabinTest(number))
					
					// its probably (almost definitely) prime
					// since number is big, just take the chance
					// and return it
					return true;
			
				// a witness for the compositeness of 
				// number was found, terminate
				return false;
			}
			// a compositeness for number
			// was found in smallPrimes
			// terminate now to save time
			return false;
		}
		
		// number is pretty small. check table
		// and use wheel mod 6 trial division
		if (checkTable(number))
			return bruteForce(number);
		
		return false;	
	}
}

