
package org.eclipse.primality;

/**
 * testing our utility class
 * 
 * @author ChairsDaily
 * @license MIT
 * @email chairs.daily.2019@gmail.com
 */
public class Test {
	
	public static void main (String[] args) {
		
		for (long n : new long[] {53813425671L}) 
			System.out.println(Prime.isPrime(n));	
	}
}

