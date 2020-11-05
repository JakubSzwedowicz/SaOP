package start;

import java.lang.Math;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class Lab3 {
	
	public static final double PI = 3.141592;
	
	public static int[] concatArrays(int[] arr1, int[] arr2) {
		
		int[] resArr = new int[arr1.length + arr2.length];
		
		for (int i = 0; i < arr1.length; i++) {
			resArr[i] = arr1[i];
		}
		for (int i = 0; i < arr2.length; i++) {
			resArr[i + arr1.length] = arr2[i];
		}
		
		return resArr;
	}
	
	public static BigDecimal calculateFactorial (int number) {
        BigDecimal factorial = BigDecimal.ONE;
        for (int i = 1; i <= number; i++) {
            factorial = factorial.multiply(new BigDecimal(i));
        }
        System.out.println("Factorial of " + number + " = " + factorial);
        return factorial;
    }
	
	public static double round(double value, int places) {
		
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.DOWN);
	    
	    return bd.doubleValue();
	}
	
	public static BigDecimal middleAgesCalcPi(long accuracy) {
		
		BigDecimal pi = new BigDecimal("0");
		
		for (double i = 0; i < accuracy; i++) {
			pi = pi.add(BigDecimal.valueOf(Math.pow(-3, -i) / (2*i+1)));
		}
		
		return pi.multiply(BigDecimal.valueOf(Math.sqrt(12)));
	}
	
	public static BigDecimal betterCalcPi(long accuracy) {
		long k1 = 545140134;
		long k2 = 13591409;
		long k3 = 640320;
		long k4 = 100100025;
		long k5 = 327843840;
		long k6 = 53360;
		
		BigDecimal s = new BigDecimal("0");
		for (int i = 0; i < accuracy; i++) {

			BigDecimal numerator = BigDecimal.valueOf(k2 + (i*k1)).multiply(calculateFactorial(6*i));
			BigDecimal denominator = calculateFactorial(i).pow(3).multiply(calculateFactorial(3*i)).
					multiply(BigDecimal.valueOf(8*k4*k5).pow(i));
			s = s.add(BigDecimal.valueOf(-1).pow(i).multiply(numerator).divide(denominator, 1000, RoundingMode.HALF_EVEN));
			System.out.println(i + " -> " + (BigDecimal.valueOf(k6).multiply(BigDecimal.valueOf(k3).sqrt(new MathContext(1000))).divide(s, 1000, RoundingMode.HALF_EVEN)));
		}
		
		return BigDecimal.valueOf(k6).multiply(BigDecimal.valueOf(k3).sqrt(new MathContext(1000))).divide(s, 1000, RoundingMode.HALF_EVEN);
	}
	
	static double calcPi(long accuracy) {
	
		double pi = 1;
		boolean isCorrect = false;
		
		for (double i = 1; i <= accuracy; i++) {
			pi *= ((2*i)*(2*i)) / ((2*i-1)*(2*i+1));
			if (compareToPi(pi*2) && !isCorrect) {
				System.out.println("6 digits using Gregory's method are correct for n = " + (int)i);
				isCorrect = true;
			}
		}
		
		return pi*2;
	}
	
	static double calcPi2(long accuracy) {
		
		double pi = 0;
		boolean isCorrect = false;
		
		for (long i = 0; i <= accuracy; i++) {
			pi += Math.pow(-1, i) / (2*i+1);
			if (compareToPi(pi*4) && !isCorrect) {
				System.out.println("6 digits using Wallis' method are correct for n = " + i);
				isCorrect = true;
			}
		}
		
		return pi*4;
	}
	
	static boolean compareToPi(double pi) {
		
		pi = round(pi, 6);
		
		return PI == pi;
	}

	public static void main(String[] args) {
		
		long acc1 = 5000000;
		int acc2 = 5000000;
		int[] arr1 = {1, 2, 3, 4, 5};
		int[] arr2 = {6, 7, 8};
		
		
		//double pi1 = calcPi(acc1);
		//double pi2 = calcPi2(acc2);
		BigDecimal pi3 = betterCalcPi(3);
		//System.out.println("PI calculated using Wallis' method: " + pi1);
		//System.out.println("PI calculated using Gregory's method: " + pi1);
		//System.out.println("PI calculated using Middle Ages method:\n" + pi3);
		System.out.println("3.141592653589793238462643383279502884197169399375105820974944592307816406286");
	
		int[] arr3 = concatArrays(arr1, arr2);
		System.out.println("Concat of test arrays:");
		System.out.print("{");
		for (int i = 0; i < arr3.length; i++) {
			System.out.print(arr3[i]);
			if (i != arr3.length - 1) {
				System.out.print(", ");
			}
		}
		System.out.print("}");
	}

}