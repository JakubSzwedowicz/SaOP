import java.util.InputMismatchException;
import java.util.Scanner;

public class Functions {
    // public
    public static <T> T validateInput(Class<T> a_type) throws Exception { // In case of string
        return validateInput(a_type, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static <T> T validateInput(Class<T> a_type, double a_min, double a_max) throws Exception {
        Scanner userInput = new Scanner(System.in);
        boolean flag = true;
        T returnInput;
        try {

            if (a_type == Number.class) {     // In case of numbers
                while (flag) {
                    while (!(userInput.hasNextDouble() || userInput.hasNextInt() || userInput.hasNextFloat())) {
                        System.out.println("You didn't insert any " + a_type.getName());
                        userInput.next();
                    }
                    returnInput = a_type.cast(userInput.next());
                    if ((double) returnInput < a_min || (double) returnInput > a_max) {
                        System.out.println("Out of bound input (min, max) = (" + a_min + ", " + a_max + ") and user input = " + returnInput.toString());
                        System.out.println("Type another value");
                    } else {
                        flag = false;
                        return returnInput;
                    }
                }
            } else if (a_type == String.class) {    // In case of date
                while (!userInput.hasNext("\\d{2}/\\d{2}/\\d{4}")) {
                    System.out.println("You used illegal signs for date, \n accepted signs are '0123456789/'"
                            + " in order to type dd/mm/yyyy");
                    userInput.next();
                }
                returnInput = a_type.cast(userInput.next());
                return returnInput;
            }
        } catch (InputMismatchException e) {
            throw new Exception(e);
        }
        return null;
    }

    public static void arrange(int[] a_array, int a_start, int a_step) {
        // Generics with primitives in java...
        int next = a_start;
        for (int i = 0; i < a_array.length; i++) {
            a_array[i] = next;
            next += a_step;
        }
    }

    public static void arrangeWithinRange(double[] a_array, double a_start, double a_end) {
        a_array[0] = a_start;
        double next = a_start;
        double step = (a_end - a_start) / (a_array.length - 1);
//        step = removeTrailingZeros(step);   // Because I dont like them and because I can
        // But it doesn't work anyway when doing the addiction below
        for (int i = 0; i < a_array.length - 1; i++) {
            a_array[i] = next;
            next += step;
        }
        // owing to precision last element is never going to be equal to a_end, so:
        a_array[a_array.length - 1] = a_end;
    }

    public static void arrange(double[] a_array, double a_start, double a_step) {
        double next = a_start;
        for (int i = 0; i < a_array.length; i++) {
            a_array[i] = next;
            next += a_step;
        }
    }

    // private
    private static int giveLengthOfSignificantFigures(double a_number) {
        int res = (int) Math.floor(Math.log10(Math.abs(a_number)));
        return Math.abs(res);
    }

    private static double removeTrailingZeros(double a_number) {
        int numberOfDigits = giveLengthOfSignificantFigures(a_number);
        int precision = (int) Math.pow(10, numberOfDigits);
        a_number = Math.floor(a_number * precision + 0.5) / precision;
        return a_number;
    }
}
