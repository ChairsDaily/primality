
package org.eclipse.primality;

/*
 * Simple test for the primality class
 * @author chairs
 */
public class Test {
	
	private static long[] testNumbers;
	
	public static void main (String[] args) {
		
		// create instance of the primality tester
		Prime p = new Prime();
		
		for (long n : new long[] {5381L}) {
			p.set(n);
			System.out.println(p.isPrime());	
		}
	}
}

