
package org.eclipse.primality;

/*
 * Simple test for the primality class
 * @author chairs
 */
public class Test {
	
	private static long[] testNumbers;
	
	public static void main (String[] args) {
		
		// numbers to test
		testNumbers = new long[] {
				481232109, 
				770219231, 
				5381,
				23918221039111L};
		
		// create instance of the primality tester
		Prime p = new Prime();
		
		for (long n : testNumbers) {
			p.set(n);
			System.out.println(p.isPrime());	
		}
	}
}

