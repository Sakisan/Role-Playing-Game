package rpgGame;

import java.math.BigDecimal;
import java.util.Random;

/**
 * A class offering useful static methods with mathematical meanings. 
 * 
 * @author Antoine Snyers & Pieter Willemsen
 * @version 3.0
 */
public class MathHelp {

	/**
	 * Checks whether a given number is a prime number.
	 * 
	 * @param 	number
	 * 			The number to be checked
	 * @pre		The number must be a positive non zero integer
	 * @effect	Checks whether a given long number is a prime number with number as 
	 * 			the long number.
	 */
	public static boolean isPrime(int number){
		return isPrime((long)number);
	}
	
	/**
	 * Checks whether a given long number is a prime number.
	 * 
	 * @param 	number
	 * 			The number to be checked
	 * @pre		The given long must be integer.
	 * @return 	False if the number isn't a positive non zero long.
	 * @return	True if and only if the given number is a prime number.
	 */
	public static boolean isPrime(long number){
		if(number <= 0)
			return false;
		if (number == 1 || number == 2)
			return true;
		for (int i=2; i<(long)(number/2); i++)
		{
			if ( (number/(double)i) == (long)(number/i) )
				return false;
		}
		return true;
	}
	
	/**
	 * Return the largest prime number smaller than a given number.
	 * 
	 * @param 	number
	 * 			The maximum value the prime number may have.
	 * @pre		number is a positive non zero integer
	 * @effect	The result is the same as the result  of the 
	 * 			method greatesPrimeNumberUnder((long)number)
	 * 			cast to an integer.
	 */
	public static int greatestPrimeNumberUnder(int number){
		long  primeNumber = (int)MathHelp.greatestPrimeNumberUnder((long)number);
		return (int)primeNumber;
	}
	
	/**
	 * Return the largest long prime number smaller than a given number.
	 * 
	 * @param 	number
	 * 			The maximum value the prime number may have.
	 * @pre		number is a positive non zero integer
	 * 			| number > 0
	 * @return	The largest long integer smaller than or equal to the given number that is a prime number.
	 * 			| let I = number
	 * 			| while(!MathHelp.isPrime(I))
	 * 			| 	I--
	 * 			| result == I
	 */
	public static long  greatestPrimeNumberUnder(long number){
		while(!MathHelp.isPrime(number)){
			number--;
		}
		return number;
	}

	/**
	 * Returns the n-th binomial coefficient.
	 * 
	 * @param 	n
	 * 			The grade of the binomial coefficient.
	 */
	public static long getSumOfBinomials(long n) {
		long sum = 1;
		long binomial = 1;
		int k;
		
		for(int i = 1; i <= n; i++){
			k = i-1;
			binomial = ((n - k)/(k+1))*binomial;
			sum+=binomial;
		}
		
		return sum;
	}
	
	/**
	 * Checks whether a given number is a binomial coefficient (of any grade).
	 * 
	 * @param 	number
	 * 			The number to check.
	 * @return	True if the given number is a binomial coefficient.
	 */
	public static boolean isSumOfBinomials(long number){
		int grade = 0;
		boolean found = false;
		
		while(!found && grade < Integer.MAX_VALUE - 1){
			if(getSumOfBinomials(grade) == number){
				found = true;
			}
			grade++;
		}
		
		return found;
	}
	
	
	/**
	 * Rounds a given double to a given precision. The given double is
	 * rounded towards the "nearest neighbor" unless both neighbors are 
	 * equidistant, in which case, round towards the even neighbor.
	 * 
	 * @param 	number
	 * 			The double to be converted.
	 * @param 	precision
	 * 			The precision that the result will have.
	 * @pre		The given precision must be positive.
	 * @return	The given double rounded to the given precision.
	 * 			| result == (double)(new BigDecimal(number)).setScale(
	 * 			|	precision, BigDecimal.ROUND_HALF_EVEN )
	 */
	public static double convertPrecisionDouble(double number, int precision) {
		BigDecimal bd = new BigDecimal(number);
		bd = bd.setScale(precision,BigDecimal.ROUND_HALF_EVEN );
		return bd.doubleValue();
	}

	/**
	 * Checks whether a given number occurs in the Fibonacci sequence.
	 * 
	 * @param 	test
	 * 			The number to be checked.
	 * @return	The result is false when the given number is negative.
	 * @return	The result is true when the given number is 0. 
	 * @return	The result is true when the given number exist in
	 * 			the row of Fibannaci.
	 */
	public static boolean isFibonacciNumber(long test){
		if( test == 0)
			return true;
		long[] number = {0,1};
		while(number[1] <= test){
			if(number[1] == test) 
				return true;
			long sum = number[0] + number[1];
			number[0] = number[1];
			number[1] = sum;
		}
		return false;
	}
	
	/**
	 * Variable referencing a random generator.
	 */
	private static Random randomGenerator = new Random();
	
	/**
	 * Returns a random integer between 0 and a given integer (inclusive).
	 * 
	 * @param 	max
	 * 			The lowest value the random integer may not be.
	 */
	public static int randomInt(int max){
		return randomGenerator.nextInt(max + 1);
	}
}
