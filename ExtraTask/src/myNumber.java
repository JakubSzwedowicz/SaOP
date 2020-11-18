import java.math.BigDecimal;

public class myNumber<T extends Number> {
    // members
    private BigDecimal m_number;
    private Class<? extends Number> m_type;

    // public
    myNumber(String a_number) {
        m_number = new BigDecimal(a_number);
        int maybeInteger = m_number.intValue();
        if (m_number.compareTo(new BigDecimal(maybeInteger)) == 0) {    // 1.0 is integer after all
            m_type = Integer.class;
        } else {
            m_type = Double.class;
        }
    }

    public int getSumOfDigits() {
        int sum = 0;
        if (m_type == Integer.class) {
            int a_number = m_number.intValue();
            while (a_number != 0) {
                sum += a_number % 10;
                a_number /= 10;
            }
            return sum;
        }
        String stringNumber = m_number.toString();   // there might be for example -242.29 so i guess it's the simplest option
        for (int i = 0; i < stringNumber.length(); i++) {
            int value = stringNumber.charAt(i) - '0';
            if (value >= 0 && value < 10) {
                sum += value;
            }
        }
        return sum;
    }

    public int getSignificantFigures() {
        return m_number.precision();
    }

    public int getBiggestDigit() {
        int max = 0, digit;
        if (m_type == Integer.class) {
            int intNumber = m_number.intValue();
            while (intNumber != 0) {
                digit = intNumber % 10;
                if (max < digit)
                    max = digit;
                intNumber /= 10;
            }
            return max;
        }
        String stringNumber = m_number.toString();
        for (int i = 0; i < stringNumber.length(); i++) {
            int value = stringNumber.charAt(i) - '0';
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public boolean isPrime() {
        if (m_type == Double.class) {
            return false;
        }
        // Otherwise it's integer and can be checked
        int value = m_number.intValue();
        if (value < 2)    // number is negative, 0 or 1
            return false;
        if (value == 2)   // the only even number that is prime
            return true;
        if ((value & 1) == 0) // Number is even
            return false;
        for (int i = 3; i <= Math.sqrt(value); i += 2) { // from this point we can iterate for 2
            if (value % i == 0)
                return false;
        }
        return true;

    }

    public static void testRun() {
        myNumber k = new myNumber("011.090");
        System.out.println("First test for k = " + k.m_number);
        System.out.println("Sum of digits: " + k.getSumOfDigits());
        System.out.println("Number of significant figures: " + k.getSignificantFigures());
        System.out.println("The biggest digit is: " + k.getBiggestDigit());
        System.out.println("Is number prime: " + k.isPrime());

        k = new myNumber("97.00");  // prime number
        System.out.println("\nSecond test for k = " + k.m_number);
        System.out.println("Sum of digits: " + k.getSumOfDigits());
        System.out.println("Number of significant figures: " + k.getSignificantFigures());
        System.out.println("The biggest digit is: " + k.getBiggestDigit());
        System.out.println("Is number prime: " + k.isPrime());
    }
    // private

    // Function checks if number is prime
    private boolean intCheck() {
        int maybeInteger = m_number.intValue();
        return m_number.equals(new BigDecimal(maybeInteger)) ? true : false;
    }
}

