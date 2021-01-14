package MyGenericArrayListSetTest;

import MyGenericArrayListSet.MyArrayListSet;
import TestBuilder.TestBuilder;
import Utilities.Student;
import org.junit.jupiter.params.provider.Arguments;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class MyArrayListSetTest_Parameters {

    // METHODS SOURCES
    static Stream<Arguments> testAddSet() throws Throwable {
        return TestBuilder.buildParametersWithAnswers(
                MyArrayListSetTest_Parameters::testDataSupplier_Parameters,
                MyArrayListSetTest_Parameters::testAddSetSupplier_Answers);
    }

    static Stream<Arguments> testSubtractSet() throws Throwable {
        return TestBuilder.buildParametersWithAnswers(
                MyArrayListSetTest_Parameters::testDataSupplier_Parameters,
                MyArrayListSetTest_Parameters::testSubtractSetSupplier_Answers);
    }

    static Stream<Arguments> testIntersectSet() throws Throwable {
        return TestBuilder.buildParametersWithAnswers(
                MyArrayListSetTest_Parameters::testDataSupplier_Parameters,
                MyArrayListSetTest_Parameters::testIntersectSetSupplier_Answers);
    }

    static Stream<Arguments> testSymmetricDifference() throws Throwable {
        return TestBuilder.buildParametersWithAnswers(
                MyArrayListSetTest_Parameters::testDataSupplier_Parameters,
                MyArrayListSetTest_Parameters::testSymmetricDifferenceSetSupplier_Answers);
    }

    static Stream<Arguments> testAddElement() throws Throwable {
        return TestBuilder.buildParametersWithAnswers(
                MyArrayListSetTest_Parameters::testDataSupplier_Parameters,
                MyArrayListSetTest_Parameters::testAddElementSupplier_Answers);
    }


    // DATA
    // Arguments supplier for methods in MySetTest class
    private static List<List> testDataSupplier_Parameters() throws Throwable {

        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(1.0, 2.0, 3.0)),
                        new MyArrayListSet<Double>(Arrays.asList(2.0, 4.0, 6.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(-100.0, -50.0, -50.999, -51.0, 0.0)),
                        new MyArrayListSet<Double>(Arrays.asList(100.0, 50.0, 50.99999, 51.0, 0.0))),
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.001f, 0.0001f, 0.00001f)),
                        new MyArrayListSet<Float>(Arrays.asList(0.005f, 0.0005f, 0.00001f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Luke", "Skywalker", 243416),
                                new Student("Darth", "Vader", 241243),
                                new Student("Han", "Solo", 645123))),
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Darth", "Maul", 452341),
                                new Student("Boba", "Fet", 523412),
                                new Student("Darth", "Vader", 241243)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(1, 1),
                                new Point2D.Double(2, 1),
                                new Point2D.Double(3, -1))),
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(1, 1),
                                new Point2D.Double(-1, -1), // |length| repeated
                                new Point2D.Double(3, 4)))));
    }

    // Answers supplier for method AddSet in MySetTest class
    private static List<List> testAddSetSupplier_Answers() throws Throwable {
        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(1.0, 2.0, 3.0, 4.0, 6.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(-100.0, -50.0, -50.999, -51.0, 0.0, 100.0, 50.0, 50.99999, 51.0))),
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.001f, 0.0001f, 0.00001f, 0.005f, 0.0005f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Luke", "Skywalker", 243416),
                                new Student("Darth", "Vader", 241243),
                                new Student("Han", "Solo", 645123),
                                new Student("Darth", "Maul", 452341),
                                new Student("Boba", "Fet", 523412)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(1, 1),
                                new Point2D.Double(2, 1),
                                new Point2D.Double(3, -1),
                                new Point2D.Double(-1, -1),
                                new Point2D.Double(3, 4)))));
    }

    private static List<List> testSubtractSetSupplier_Answers() throws Throwable {
        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(1.0, 3.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(-100.0, -50.0, -50.999, -51.0))),
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.001f, 0.0001f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Luke", "Skywalker", 243416),
                                new Student("Han", "Solo", 645123)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(2, 1),
                                new Point2D.Double(3, -1)))));
    }

    private static List<List> testIntersectSetSupplier_Answers() throws Throwable {
        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(2.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(0.0))),
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.00001f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Darth", "Vader", 241243)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(1, 1)))));
    }

    private static List<List> testSymmetricDifferenceSetSupplier_Answers() throws Throwable {
        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(1.0, 3.0, 4.0, 6.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(-100.0, -50.0, -50.999, -51.0, 100.0, 50.0, 50.99999, 51.0))),
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.001f, 0.0001f, 0.005f, 0.0005f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Luke", "Skywalker", 243416),
                                new Student("Han", "Solo", 645123),
                                new Student("Darth", "Maul", 452341),
                                new Student("Boba", "Fet", 523412)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(2, 1),
                                new Point2D.Double(3, -1),
                                new Point2D.Double(3, 4),
                                new Point2D.Double(-1, -1))))); // |length| repeated
    }

    private static List<List> testAddElementSupplier_Answers() throws Throwable {
        return List.of(
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(1.0, 2.0, 3.0, 6.0))),
                List.of(
                        new MyArrayListSet<Double>(Arrays.asList(-100.0, -50.0, -50.999, -51.0, 0.0, 0.0))),   // 100.0 got added to the TreeSet as a last element
                List.of(
                        new MyArrayListSet<Float>(Arrays.asList(0.001f, 0.0001f, 0.00001f))),
                List.of(
                        new MyArrayListSet<Student>(Arrays.asList(
                                new Student("Luke", "Skywalker", 243416),
                                new Student("Darth", "Vader", 241243),
                                new Student("Han", "Solo", 645123)))),
                List.of(
                        new MyArrayListSet<Point2D>(Arrays.asList(
                                new Point2D.Double(1, 1),
                                new Point2D.Double(2, 1),
                                new Point2D.Double(3, -1), // |length| repeated
                                new Point2D.Double(3, 4))))); // |length| repeated
    }

}
