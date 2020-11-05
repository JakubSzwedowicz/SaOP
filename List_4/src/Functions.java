import java.util.*;

public final class Functions {
    // members
    private static Random s_generator = new Random();
    private static Scanner s_userInput = new Scanner(System.in);
    private static List<Integer> s_factorial = new ArrayList<Integer>(Arrays.asList(1, 1));
    // Public
    public static void fillArray(double[] a_array) {
        fillArray(a_array, Double.class, Double.MIN_VALUE, Double.MAX_VALUE);
    }
    public static <T extends Number> void fillArray(double[] a_array, Class<T> a_class,
                                                    double a_lowerBound, double a_upperBound) {
        if (a_array.length == 0) {
            System.out.println("Array size equals 0!");
            return;
        }
        double ratio = 1/(a_upperBound - a_lowerBound);
        for (int i = 0; i < a_array.length; i++) {
            if(a_class == Double.class){
                a_array[i] = s_generator.nextDouble() / ratio + a_lowerBound;
            } else if(a_class == Integer.class){
                a_array[i] = s_generator.nextInt((int)(a_upperBound - a_lowerBound)) + a_lowerBound;
            }
        }
    }
//    public static void fillArray(int[] a_array, int a_upperBound) {
//        if (a_array.length == 0) {
//            System.out.println("Array size equals 0!");
//            return;
//        }
//        for (int element : a_array) {
//            element = s_generator.nextInt(a_upperBound);
//        }
//    }
    public static int factorial(int a_number){
        if(s_factorial.size() <= a_number){
            s_factorial.add(factorial(a_number - 1) * a_number);
            return s_factorial.get(a_number);
        } else{
            return s_factorial.get(a_number);
        }
    }
    // Fills the given array with numbers from range [1, a_upperBound];
    public static void fillUniqueArray(int[] a_array, int a_upperBound) {
        if (a_array.length == 0) {
            System.out.println("Array size equals 0!");
            return;
        } else if(a_array.length > a_upperBound){
            System.out.println("Array size cannot be greater than upper bound!");
            return;
        }
        int[] uniqueArray = new int[a_upperBound];
        for (int i = 0; i < uniqueArray.length; ) {
            uniqueArray[i] = ++i;
        }
        int number = 0;
        for (int i = 0; i < a_array.length; i++) {
            number = s_generator.nextInt(a_upperBound);
            a_array[i] = number + 1;
            uniqueArray[number] = a_upperBound;
            a_upperBound--;
        }
    }
    public static int validateInput(){
        return validateInput(Double.MIN_VALUE, Double.MAX_VALUE);
    }
    public static int validateInput(double a_lowerBound, double a_upperBound) {
        int returnValue = 0;
        System.out.println("Input a integer value in range [" + a_lowerBound + ", " + a_upperBound + "]");
        while (true) {
            while (!s_userInput.hasNextInt()) {
                System.out.println("You should input integer!");
                s_userInput.next();
            }
            returnValue = s_userInput.nextInt();
            if (returnValue > a_lowerBound && returnValue <= a_upperBound) {
                return returnValue;
            } else {
                System.out.println("You should stick to the boundaries!");
            }
        }
    }

    public static void printArray(int[] a_array, boolean a_reverse) {
        int begin = 0;
        int step = 1;
        System.out.print("Printing array in ");
        if (a_reverse) {
            begin = a_array.length - 1;
            step = -1;
            System.out.println("reverse order:");
        } else {
            System.out.println("normal order:");
        }

        for (int i = begin; i >= 0 && i < a_array.length; i += step) {
            System.out.print(a_array[i] + " ");
        }
        System.out.println();
    }

    public static int[][] splitArrayOddEven(int[] a_array) {
        int[][] res = new int[2][]; // 2D arrays in java are not contiguous
        int oddSize = 0;
        for (int element : a_array) {
            if ((element & 1) == 1) { // Odd
                oddSize++;
            }
        }
        res[0] = new int[oddSize];
        res[1] = new int[a_array.length - oddSize];
        int i = 0;
        int j = 0;
        for(int element : a_array){ // Just for the sake of memory optimization and easier iterating later on. O(2n) ~ O(n)
            if((element & 1) == 1){
                res[0][i++] = element;
            } else{
                res[1][j++] = element;
            }
        }
        return res;
    }
    // Private
}
