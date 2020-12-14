package Int2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleFunction;
import Int1.Function;
import Int1.FunctionClass;

public class SetOfMethods {
    // MEMBERS

    // PUBLIC
    public static double df(Function a_function, double a_x, double a_h) {
        return ((a_function.f(a_x + a_h) - a_function.f(a_x - a_h)) / (2*a_h));
    }

    public static double findSingleZero(Function a_function, double a_begin, double a_end, double a_precision, boolean a_useDX) throws IllegalArgumentException {
        if (a_end < a_begin || a_precision <= 0) {
            throw new IllegalArgumentException("Illegal arguments! \n\tbegin = " + a_begin + "\n\tend = " + a_end
                    + "\n\tprecision = " + a_precision);
        }
        if (a_useDX) {
            return actualFindSingleZero(a_function::df, a_begin, a_end, a_precision);
        } else {
            return actualFindSingleZero(a_function::f, a_begin, a_end, a_precision);
        }
    }

    public static List<Double> findAllZeros(Function a_function, double a_begin, double a_end, double a_precision, int a_fDerivative) throws IllegalArgumentException {
        if (a_fDerivative < 0 || a_fDerivative > 3) {
            throw new IllegalArgumentException("Illegal arguments!\n\tfDerivative = " + a_fDerivative);
        }
        double range = a_end - a_begin;
        int divisions = 100;
        double step = range / divisions;  // it will be possible to find at best 100 zeros.
        double[] divisonArguments = new double[divisions]; // number of splits
        double argument = a_begin;
        for (int i = 0; i < divisions; i++, argument += step) {
            divisonArguments[i] = argument;
        }
        DoubleFunction<Double> functionWrapper;
        switch (a_fDerivative) {
            case 0 -> functionWrapper = a_function::f;
            case 1 -> functionWrapper = a_function::df;
            case 2 -> functionWrapper = a_function::d2f;
            default -> functionWrapper = a_function::d3f;
        }
        List<Double> zeroSections = reduceDivisions(functionWrapper, divisonArguments);
        List<Double> zeros = new ArrayList<>(zeroSections.size());
        for (int i = 0; i < zeroSections.size() - 1; ) {
            zeros.add(actualFindSingleZero(functionWrapper, zeroSections.get(i), zeroSections.get(i + 1), a_precision));
            i += 2;
        }
        return zeros;
    }

    // finds the global extremes of f(x)
    public static double[] findExtremumArguments(Function a_function, double a_start, double a_end, double a_precision) {
        return findExtremumArguments(a_function, a_start, a_end, a_precision, 0);
    }

    //    finds the global extremes of a given a_derivative, i.e. if a_derivative == 0, then function returns max values of f(x),
    // for a_derivative == 1, function returns max value of f'(x)
    public static double[] findExtremumArguments(Function a_function, double a_start, double a_end, double a_precision, int a_derivative) {
        a_function.setDX(a_precision);
        // we have to find zeros of one higher derivative to find max value of a given function
        List<Double> zeros = findAllZeros(a_function, a_start, a_end, a_precision, a_derivative + 1);
        if (zeros.size() == 0) {
            System.out.println("Not a single extremum found");
            return null;
        }
        zeros.addAll(Arrays.asList(a_start, a_end));    // to check values on borders
        double min = Double.MAX_VALUE;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < zeros.size(); i++) {
            double zeroArgument = zeros.get(i);
            double value = a_function.f(zeroArgument);
            if (min > value) {
                min = value;
            }
            if (max < value) {
                max = value;
            }
        }
        return new double[]{min, max};
    }

    public static List<Double> findInflectionPointOfFunction(Function a_function, double a_start, double a_end, double a_precision) {
        if(a_precision > 1e-8){
            System.out.println("Can't computer with higher than 1e-8 precision = " + a_precision);
            return null;
        }
        a_function.setDX(a_precision);
        List<Double> zeros = findAllZeros(a_function, a_start, a_end, a_precision, 2);
        List<Double> inflectionArguments = new ArrayList<>(zeros.size());
        if (zeros.size() == 0) {
            System.out.println("Not a single inflection point found");
            return null;
        }
        for (int i = 0; i < zeros.size(); i++) {
            double zero = zeros.get(i);
            inflectionArguments.add(zeros.get(i));
        }
        return inflectionArguments;
    }

    public static double countIntegral(Function a_function, double a_start, double a_end, double a_error) {
        int n = getNumberOfSteps(a_function, a_start, a_end, a_error);
        double h = (a_end - a_start) / n;
        double sum = 0.5 * (a_function.f(a_start) + a_function.f(a_end));
        double argument = a_start;
        for (int i = 1; i < n; i++) {
            argument += h;
            sum += a_function.f(argument);
        }
        sum *= h;
        return sum;
    }


//    private static double getRightHeight(Int1.Function a_function, double a_start, double a_height, double a_percentage) {
//        final double first = a_start;
//        final double firstRes = a_function.f(first);
//        double middle = first + a_height;
//        double last = middle + a_height;
//        // criteria formula comes from equations on paper: (P2 - P1)
//        double criteria = (firstRes + a_function.f(last)) / (a_function.f(middle) + a_function.f(last));
//        while (Math.abs(criteria) < a_percentage) {
//            a_height /= 2;
//            middle = first + a_height;
//            last = middle + a_height;
//            criteria = (firstRes + a_function.f(last)) / (a_function.f(middle) + a_function.f(last));
//        }
//        return a_height;
//    }

    // test
    public static void testClass() {
        Function functionObject = new FunctionClass(0.1);
        testClass(functionObject);
    }
    public static void testClass(Function a_functionObject) {
        try {
            System.out.println("----------------------------\nTesting class Int2.SetOfMethods\n----------------------------");
            System.out.println("Given function object: " + a_functionObject);
            double begin = -4;
            double end = 4;
            double error = 1e-9;
            double argument = Math.PI;
            System.out.println("Printing value of derivative at point x0 = " + argument + " with error = " + error);
            System.out.println(df(a_functionObject, argument, error));
            error = 1e-3;
            System.out.println("Printing all square roots on range [" + begin + ", " + end + "] with error = " + error);
            System.out.println(findAllZeros(a_functionObject, begin, end, error, 0));
            error = 1e-10;
            System.out.println("Printing min and max values on range [" + begin + ", " + end + "] with error = " + error);
            System.out.println(Arrays.toString(findExtremumArguments(a_functionObject, begin, end, error)));
            begin = -1;
            end = Math.PI;
            error = 1e-8;
            System.out.println("Printing inflection points on range [" + begin + ", " + end + "] with error = " + error);
            System.out.println(findInflectionPointOfFunction(a_functionObject, begin, end, error));

            begin = 0;
            end = Math.PI;
            error = 1e-3;
            System.out.println("Counting the integral from " + a_functionObject.formula() + " on range ["
                    + begin + ", " + end + "], with error = " + error);
            System.out.println(countIntegral(a_functionObject, begin, end, error));
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
        }
    }

    // PRIVATE
    private static List<Double> reduceDivisions(DoubleFunction<Double> a_function, double[] a_divisionArguments) {
        List<Double> reducedArguments = new ArrayList<Double>(a_divisionArguments.length);
        for (int i = 0; i < a_divisionArguments.length - 1; i++) {
            if (a_function.apply(a_divisionArguments[i]) * a_function.apply(a_divisionArguments[i + 1]) < 0) {
//                System.out.println("for " + a_divisionArguments[i] + " f(x) = " + a_function.apply(a_divisionArguments[i]));
//                System.out.println("for " + a_divisionArguments[i+1] + " f(x) = " + a_function.apply(a_divisionArguments[i+1]));
                reducedArguments.add(a_divisionArguments[i]);
                reducedArguments.add(a_divisionArguments[i + 1]);
            }
        }
        return reducedArguments;
    }

    private static double actualFindSingleZero(DoubleFunction<Double> a_function, double a_begin, double a_end, double a_precision) {
        if (Math.abs(Math.abs(a_end) - Math.abs(a_begin)) < a_precision) {
            if (Math.abs(a_end) > Math.abs(a_begin)) {
                return a_begin;
            } else {
                return a_end;
            }
        }
        double pivot = (a_begin + a_end) / 2;
        if (a_function.apply(a_begin) * a_function.apply(pivot) > 0) {
            return actualFindSingleZero(a_function, pivot, a_end, a_precision);
        } else {
            return actualFindSingleZero(a_function, a_begin, pivot, a_precision);
        }
    }

    private static int getNumberOfSteps(Function a_function, double a_start, double a_end, double a_error) {
        double[] minMax = findExtremumArguments(a_function, a_start, a_end, Math.pow(a_error, 2), 2);
        double K = Math.abs(minMax[1]); // it's max value of f''(x) on range [a_start, a_end]
        // |ErrorTrap| <= equation, so if we want |ErrorTrap| <= a_error, then by solving equation <= a_error
        // we get res <= numberOfElements, so the minimal number of elements maintaining given error is equal res
        double res = Math.sqrt((K * Math.pow(a_end - a_start, 3)) / (12 * a_error));
        return (int) Math.ceil(res);
    }

}
