package Utilities;

import O2_O5.Matrix;
import O2_O5.MatrixArray;
import O2_O5.MyArray;
import O2_O5.MySortedArray;
import O6.Hotel;

import java.util.Random;

public class Functions {
    // members
    private static Random s_generator = new Random();

    private interface FunctionWrapper {
        default int intFunction() {/*body*/
            return 0;
        }

        double doubleFunction();
    }

    // public
    // Method to run all the tasks O2 - O5
    public static void runTasks() {
        O2();
        O3();
        O4();
        O5();
        O6();
    }

    public static <T> void fillRandom(Number[] a_array, Class<T> a_type, double a_lowerBound, double a_upperBound) {
        fillRandom(a_array, a_array.length, a_type, a_lowerBound, a_upperBound);
    }

    public static <T> void fillRandom(Number[] a_array, int a_size, Class<T> a_type, double a_lowerBound, double a_upperBound) {
        for (int i = 0; i < a_size; i++) {
            double randomDouble = s_generator.nextDouble();
        }
    }

    public static double getRandomDouble() {
        return s_generator.nextDouble();
    }

    public static int getRandomInt(int a_upperBound) {
        return s_generator.nextInt(a_upperBound);
    }

    public double getRandomInt() {
        return s_generator.nextInt();
    }

    // private
    private static void O2() {
        MyArray.testClass();
    }

    private static void O3() {
        MySortedArray.testClass();
    }

    private static void O4() {
        Matrix.testClass();
    }

    private static void O5() {
        MatrixArray.testClass();
    }

    private static void O6() {
        Hotel.testClass();
    }

}
