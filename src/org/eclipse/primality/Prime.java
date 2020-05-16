
package org.eclipse.primality;

import java.lang.Math;
import java.util.ArrayList;
import java.util.Random;

/*
 * Simple primality tester, must first be instanciated, 
 * access is handled via getter and setter.
 * 
 * @author chairs
 * @references
 * 		https://en.wikipedia.org/wiki/Fermat_primality_test
 * 		https://en.wikipedia.org/wiki/Primality_test
 */
public final class Prime {
	
	private long[] small_primes = {
		
		// should be expanded with a prime number sieve
		2, 3, 5, 7, 11, 13, 17, 19, 
		23, 29, 31, 37, 41, 43, 47, 
		53, 59, 61, 67, 71, 73, 79, 
		83, 89, 97};
	private Random r = new Random();
	private int digits;
	private long n = 0;

	
	/*
	 * Gets the current value of n 
	 * @return n (long) the current number in question
	 */
	public long get () {return (long) n;}
	
	/*
	 * Sets the current value of n  
	 * @param newNumber (long) the new number in question
	 */
	public void set (long newNumber) {
		
		digits = length(newNumber);
		n = newNumber;
	}
	
	/*
	 * Calculates the length in digits of a base 10 number
	 * @param n (long) number to size up
	 * @return size (int) length of numbeer in question in digits
	 */
	private int length (long n) {
	
		return (int) Math.log10(n) + 1;
	}
	
	/*
	 * Bruteforce primality test
	 * @return prime (bool) primality of the number in question
	 */
	private boolean bruteForce () {
		
		if (n <= 3) return n > 1;
		if (n % 2 == 0 || n % 3 == 0) return false;
		
		long divisor = 5;
		while (divisor * divisor < n) {
			
			if (n % divisor == 0 || n % (divisor + 2) == 0) {
				return false;
			
			} 
			divisor += 6;	
		}
		return true;
	}
	
	/*
	 * Fermats primality test
	 * @return prime (bool) probable primality of the number in question
	 */
	private boolean[] fermatTest () {

		int a = (int) n / 2;
		int a2 = a + ((int) a / 2);
		return new boolean[] {Math.pow(a, (n - 1)) % n == 1,
				Math.pow(a2, (n - 1)) % n == 1};
	}
	
	/*
	 * Check table of small prime numbers 
	 * for divisibility (is not accurate unless n is smaller than
	 * the biggest element in small_primes)
	 * 
	 * @return prime (bool) probable primality of the number in question
	 */
	private boolean checkTable () {
		
		for (long p : small_primes)
			
			// definitely composite
			if (n % p == 0) return false;
		
		// probably prime 
		return true;
	}

	/*
	 * Public method to test primality
	 * @return prime (bool) primality of the number in question
	 */
	public boolean isPrime () {
		
		if (checkTable()) {
			
			System.out.println(fermatTest()[0]);
			return bruteForce();	
		} else {
			
			return false;
		}
		
	}
}
