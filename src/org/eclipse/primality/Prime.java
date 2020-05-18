
package org.eclipse.primality;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Deterministic primality test, no randomness
 * Utility class, no instanciation
 * 
 * @author ChairsDaily
 * @license MIT
 * @email chairs.daily.2019@gmail.com
 * @references
 * 	https://en.wikipedia.org/wiki/Primality_test#Simple_methods
 * 	https://en.wikipedia.org/wiki/Fermat_primality_test
 * 	https://en.wikipedia.org/wiki/Miller%E2%80%93Rabin_primality_test
 * 	https://en.wikipedia.org/wiki/Trial_division
 */	
public final class Prime {

	private static ArrayList<Long> smallPrimes = new ArrayList<Long>(Arrays.asList(
	
			2L, 3L, 5L, 7L, 11L, 17L, 19L, 23L, 29L, 31L,
			31L, 37L, 41L, 43L, 47L, 53L, 59L, 61L, 67L, 
			71L, 73L, 79L, 83L, 89L, 97L));
	
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
		ArrayList<Long> witnesses;
		
		if (number < 341550071728321L) witnesses = witnessFilter(0, 6);
		if (number < 3474749660383L) witnesses = witnessFilter(0, 5);
		if (number < 2152302898747L) witnesses = witnessFilter(0, 4);
		
		if (number < 1122004669633L) witnesses = new ArrayList<Long>(
				Arrays.asList(2L, 13L, 23L, 1662803L));
		if (number < 4759123141L) witnesses = new ArrayList<Long>(
				Arrays.asList(2L, 7L, 61L));
		
		if (number < 3215031751L) witnesses = witnessFilter(0, 3);
		if (number < 25326001L) witnesses = witnessFilter(0, 2);
		
		if (number < 9080191L) witnesses = new ArrayList<Long>(
				Arrays.asList(31L, 73L));
		if (number < 1373653L) witnesses = witnessFilter(0, 1);
		if (number < 2047L) witnesses = witnessFilter(0, 0);
		else
			witnesses = new ArrayList<Long>();
		
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
	 * Helper method for millerRabinTest, generates a witness range
	 * using some proven constants from wikipedia to improve speed
	 * @param .number target number
	 * @return .witnesses best witnesses to use for a miller test
	 */
	private static ArrayList<Long> witnessFilter (int start, int end) {
		
		return new ArrayList<Long>(smallPrimes.subList(start, end));	
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

