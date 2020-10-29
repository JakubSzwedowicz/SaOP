// BEGIN OF IMPORTANT SECTION
// you need kXChart from github /knowm/XChart

import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
// or just comment it
// END OF IMPORTANT SECTION

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// I KNOW NONE OF THIS TASKS HAS TO BE SOLVED ON WHOLE ARRAYS
// BUT I WANTED TO SAVE ALL THE RESULTS THE WAY YOU WOULD IN DATA SCIENCE

public class J2 {
    private static List<Integer> m_res = new ArrayList<Integer>(Arrays.asList(1, 1, 2, 6, 24, 120, 720));

    // public
    public static void main() {
        runTasks();
    }

    // BEGIN OF IMPORTANT SECTION
    // You need to have XChart to run those functions
    public static void plotCharts(double[] a_x, double[] a_y1, double[] a_y2) {
        plotCharts(a_x, a_y1, a_y2, null);
    }

    public static void plotCharts(double[] a_x, double[] a_y1, double[] a_y2, double[] a_y3) {
        XYChart chart = new XYChartBuilder().width(1200).height(800).title("some title").xAxisTitle("X").yAxisTitle("Y").build();
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        chart.getStyler().setPlotMargin(0);
        chart.getStyler().setPlotContentSize(.95);
//        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideSW);
        chart.addSeries("Function f(x)", a_x, a_y1);
        chart.addSeries("Function f'(x)", a_x, a_y2);
        if (a_y3 != null) {
            chart.addSeries("Function fc(x)", a_x, a_y3);
        }
        new SwingWrapper(chart).displayChart();
    }
    // or just comment it
    // END OF IMPORTANT SECTION

    public static void computeFunction(FunctionWrapper a_myFunction, double[] a_xArray, double[] a_yArray) {
        for (int i = 0; i < a_xArray.length; i++) {
            a_yArray[i] = a_myFunction.invoke(a_xArray[i]);
        }
    }

    public static void computeDerivative(FunctionWrapper a_myFunction,
                                         double[] a_xArray, double[] a_yArray) {
        computeDerivative(J2::df, a_myFunction, a_xArray, a_yArray);
    }

    public static void computeDerivative(DerivativeWrapper a_myDerivative, FunctionWrapper a_myFunction,
                                         double[] a_xArray, double[] a_yArray) {
        if (a_yArray.length < 2) {
            System.out.println("Compute function works only on a given range > 2!");
            return;
        }
        for (int i = 1; i < a_yArray.length; i++) {
            a_yArray[i] = a_myDerivative.invoke(a_myFunction, a_xArray[i], a_xArray[1] - a_xArray[0]);
        }
        a_yArray[0] = a_yArray[1];
    }

    public static int[] findMinMaxIndex(double[] a_yArray) {
        int[] minMax = new int[2];
        minMax[0] = -1;
        minMax[1] = -1;
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (int i = 0; i < a_yArray.length; i++) {
            if (min > a_yArray[i]) {
                minMax[0] = i;
            }
            if (max < a_yArray[i]) {
                minMax[1] = i;
            }
        }
        return minMax;
    }

    public static List<Integer> findAllZeros(double[] a_xArray, double[] a_yArray) {
        int step = 1;
        double xLast = a_xArray[a_xArray.length - 1];
        double xFirst = a_xArray[0];
        double h = a_xArray[1] - a_xArray[0];
        if (a_xArray.length > 1) {
            step = (int) (1 / (2 * h));  // multiplication 2*h to move for 0.5 on X axis instead of 1.0
        }
//        int divisions = (int) (2 * (xLast - xFirst)); // It's always at least 2
        int divisions = (int) Math.ceil(a_xArray.length / step);
        if (divisions * 0.5 + xFirst > xLast) { // voodoo trick
            divisions++;
        } else {
            divisions += 2;
        }
        int[] divisonIndexes = new int[divisions]; // number of splits
        Functions.arrange(divisonIndexes, 0, step);
        // last index of array is not guaranteed to be a multiplication of step
        divisonIndexes[divisions - 1] = a_xArray.length - 1;
        List<Integer> zeroSections = reduceDivisions(divisonIndexes, a_yArray);
        List<Integer> zeros = new ArrayList<Integer>(zeroSections.size());
        for (int i = 0; i < zeroSections.size() - 1; ) {
            zeros.add(findZero(a_yArray, zeroSections.get(i), zeroSections.get(i + 1)));
            i += 2;
        }
        return zeros;
    }

    public static int[] findExtremumIndexes(double[] a_xArray, int a_start, int a_end,
                                            FunctionWrapper a_function, DerivativeWrapper a_dFunction) {
        double[] dyArray = new double[a_xArray.length]; // Waste of resources but whatever
        double[] yArray = new double[a_xArray.length]; // Waste of resources but whatever
        computeFunction(a_function, a_xArray, dyArray);  // Don't want to overload it for sections [a, b]
        computeDerivative(a_dFunction, a_function, a_xArray, dyArray);  // Don't want to overload it for sections [a, b]
        return findExtremumIndexes(a_xArray, yArray, dyArray, a_start, a_end);

    }

    public static int[] findExtremumIndexes(double[] a_xArray, double[] a_yArray, double[] a_dyArray, int a_start, int a_end) {
        List<Integer> zeros = findAllZeros(a_xArray, a_dyArray);
        if (zeros.size() == 0) {
            System.out.println("Not a single extremum found");
            return null;
        }
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        int minIndex = -1;
        int maxIndex = -1;
        for (int i = 0; i < zeros.size(); i++) {
            int zeroIndex = zeros.get(i);
            if (zeroIndex < a_start)
                continue;
            if (zeroIndex >= a_end)   // a_end is past the end iterator
                break;
            if (min > a_yArray[zeroIndex]) {
                minIndex = zeroIndex;
                min = a_yArray[zeroIndex];
            }
            if (max < a_yArray[zeroIndex]) {
                maxIndex = zeroIndex;
                max = a_yArray[zeroIndex];
            }
        }
        int[] extremumIndexes = new int[2];
        extremumIndexes[0] = minIndex;
        extremumIndexes[1] = maxIndex;
        return extremumIndexes;
    }

    public static List<Integer> findInflectionPointOfFunction(double[] a_xArray, double[] a_d2yArray, int a_start, int a_end) {
        List<Integer> zeros = findAllZeros(a_xArray, a_d2yArray);
        List<Integer> inflectionIndexes = new ArrayList<Integer>(zeros.size());
        if (zeros.size() == 0) {
            System.out.println("Not a single inflection point found");
            return null;
        }
        for (int i = 0; i < zeros.size(); i++) {
            int zero = zeros.get(i);
            if (zero < a_start) {
                continue;
            }
            if (zero > a_end) {
                break;
            }
            inflectionIndexes.add(zeros.get(i));
        }
        return inflectionIndexes;
    }

    // private
    private static void runTasks() {
        double[] x = new double[10000];
        double h = 0.001;
        Functions.arrange(x, 0.1, h);
//        Functions.arrangeWithinRange(x, 0, 11.2);

        double[] y = new double[x.length];
        computeFunction(J2::f, x, y);

        double[] dy = new double[x.length];
        computeDerivative(J2::df, J2::f, x, dy);

        int[] minMax = findMinMaxIndex(y);
        if (minMax[0] != -1) {
            System.out.println("Min index = " + minMax[0] + " and has a value f(x) = " + y[minMax[1]]);
        }
        if (minMax[1] != -1) {
            System.out.println("Max index =  " + minMax[1] + " and has a value f(x) = " + y[minMax[1]]);
        }

        List<Integer> zeros = findAllZeros(x, dy);
        System.out.println("Looking for zeros in derivative f'(x)");
        for (int k : zeros) {
            System.out.println("Zero = " + dy[k] + " is on the " + k + " index");
        }

        int start = 0;
        int end = x.length;
        int[] extremums = findExtremumIndexes(x, y, dy, start, end);
        if (extremums != null) {
            System.out.println("In the range x = (" + x[start] + ", " + x[end - 1] + ") min and max values found are: ");
            for (int k : extremums) {
                if (k != -1) {
                    System.out.println("at index " + k + " for x = " + x[k] + ", f(x) = " + y[k] + " and it's derivative equals f'(x) = " + dy[k]);
                }
            }
        } else {
            System.out.println("There are no any extremums");
        }

        double[] d2y = new double[x.length];
        computeNDerivative(J2::f, x, d2y, 2);
        List<Integer> inflections = findInflectionPointOfFunction(x, d2y, 0, d2y.length);
        if (inflections != null) {
            System.out.println("In the range x = (" + x[start] + ", " + x[end - 1] + ") found inflection points are: ");
            for (int k : inflections) {
                System.out.println("at index " + k + " for x = " + x[k] + ", f(x) = " + y[k] + " and it's 2nd derivative equals f''(x) = " + d2y[k]);
            }
        } else {
            System.out.println("There are no any inflection points");
        }
        // BEGIN OF IMPORTANT SECTION
        // you need kXChart from github /knowm/XChart
        plotCharts(x, y, dy, d2y);
        // or just comment it
        // END OF IMPORTANT SECTION
    }

    private interface FunctionWrapper {


        double invoke(double a_x);

    }

    private interface DerivativeWrapper {
        // Takes 3 parameters,
        // a_f - function to derive,
        // a_x - argument X,
        // a_h - step

        double invoke(FunctionWrapper a_f, double a_x, double a_h);

    }

    private static double f(double a_x) {
        return (Math.exp(Math.sin(a_x)) + Math.sqrt(a_x));
//        return Math.pow(a_x, 3) - 5*Math.pow(a_x, 2) - a_x + 1;
    }

    private static double df(FunctionWrapper a_myFunction, double a_x, double a_h) {
        return ((a_myFunction.invoke(a_x + a_h) - a_myFunction.invoke(a_x)) / a_h);
    }

    private static void computeNDerivative(FunctionWrapper a_myFunction,
                                           double[] a_xArray, double[] a_yArray, int a_level) {
        if (a_level > 2) {
            System.out.println("This function only works for f''(x) AT MOST!"); // Waste of time and effort
            return;
        }
        double sum = 0;
        int sign = 1;
        double h = a_xArray[1] - a_xArray[0];
        double hToThePowerN = Math.pow(h, a_level);
        int[] binomialCoefficient = new int[a_level / 2 + 1];
        for (int i = 0; i < binomialCoefficient.length; i++) {
            binomialCoefficient[i] = factorial(a_level) / (factorial(i) * factorial(a_level - i));
        }
        for (int i = 0; i < a_yArray.length; i++) {   // voodoo loops
            sum = 0;
            sign = 1;
            for (int k = 0; k <= a_level / 2; k++) {
                sum += (sign * binomialCoefficient[k] * a_myFunction.invoke(a_xArray[i] + (a_level - k) * h));
                sign = -sign;
            }
            for (int k = 0; k <= (a_level - 1) / 2; k++) {
                sum += (sign * binomialCoefficient[k] * a_myFunction.invoke(a_xArray[i] + (a_level - a_level + k) * h));
                sign = -sign;
            }
            sum /= hToThePowerN;
            a_yArray[i] = sum;
        }
    }

    private static int factorial(int a_number) {
        if (a_number < m_res.size())
            return m_res.get(a_number);
        else {
            m_res.add(a_number * factorial(a_number - 1));
            return a_number * factorial(a_number - 1);
        }
    }

    private static List<Integer> reduceDivisions(int[] a_divisionIndexes, double[] a_yArray) {
        List<Integer> reducedIndexes = new ArrayList<Integer>(a_divisionIndexes.length);
        for (int i = 0; i < a_divisionIndexes.length - 1; i++) {
            if (a_yArray[a_divisionIndexes[i]] * a_yArray[a_divisionIndexes[i + 1]] < 0) {
                reducedIndexes.add(a_divisionIndexes[i]);
                reducedIndexes.add(a_divisionIndexes[i + 1]);
            }
        }
        return reducedIndexes;
    }

    private static int findZero(double[] a_yArray, int a_begin, int a_end) {
        if (a_end - a_begin < 2) {
            if (Math.abs(a_yArray[a_begin]) <= Math.abs(a_yArray[a_end])) {
                return a_begin;
            } else {
                return a_end;
            }
        }
        int pivot = (a_begin + a_end) / 2;
//        if (Math.abs(a_yArray[pivot] + a_yArray[a_begin]) > Math.abs(a_yArray[pivot] + a_yArray[a_end])) {
        if (a_yArray[a_begin] * a_yArray[pivot] > 0) {
            return findZero(a_yArray, pivot, a_end);
        } else {
            return findZero(a_yArray, a_begin, pivot);
        }
    }
}
