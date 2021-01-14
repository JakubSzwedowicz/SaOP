package MyGenericArrayListSetTest;

import MyGenericArrayListSet.MyArrayListSet;
import Utilities.Stopwatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

// Turn off optimization first:
// -Djava.compiler=NONE

// Class testing MyArrayListSet
public class MyArrayListSetTest {
    private static Stopwatch timer = new Stopwatch();

    @BeforeEach
    public void prepareForTest() {
        timer.reset();
        timer.start();
    }

    @AfterEach
    public void finishAfterTest(TestInfo testInfo) {
        timer.stop();
        System.out.println("TEST LOGGER: execution time of " + testInfo.getDisplayName() + " took " + timer.getElapsedTime() / 1000000 + "[ms]");
    }

    @ParameterizedTest(name = "testAddSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testAddSet")
    public <T> void testAddSet(MyArrayListSet<T> a_first, MyArrayListSet<T> a_second, MyArrayListSet<T> a_answer) {
        MyArrayListSet<T> sumSet = a_first.addSet(a_second);
        Assertions.assertEquals(a_answer, sumSet);
    }

    @ParameterizedTest(name = "testAddSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testAddSet")
    public <T> void testAddSet2(MyArrayListSet<T> a_first, MyArrayListSet<T> a_second, MyArrayListSet<T> a_answer) {
        MyArrayListSet<T> sumSet = a_first.addSet2(a_second);
        Assertions.assertEquals(a_answer, sumSet);
    }

    @ParameterizedTest(name = "testSubtractSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testSubtractSet")
    public <T> void testSubtractSet2(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        MyArrayListSet<T> subtractionSet = a_firstSet.subtractSet2(a_secondSet);
        Assertions.assertEquals(a_answerSet, subtractionSet);
    }

    @ParameterizedTest(name = "testSubtractSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testSubtractSet")
    public <T> void testSubtractSet(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        MyArrayListSet<T> subtractionSet = a_firstSet.subtractSet(a_secondSet);
        Assertions.assertEquals(a_answerSet, subtractionSet);
    }

    @ParameterizedTest(name = "testIntersectSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testIntersectSet")
    public <T> void testIntersectSet(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.intersectSet(a_secondSet));
    }

    @ParameterizedTest(name = "testIntersectSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testIntersectSet")
    public <T> void testIntersectSet2(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.intersectSet2(a_secondSet));
    }

    @ParameterizedTest(name = "testSymmetricDifference (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testSymmetricDifference")
    public <T> void testSymmetricDifference(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.symmetricDifference(a_secondSet));
    }

    @ParameterizedTest(name = "testAddElement (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericArrayListSetTest.MyArrayListSetTest_Parameters#testAddElement")
    public <T> void testAddElement(MyArrayListSet<T> a_firstSet, MyArrayListSet<T> a_secondSet, MyArrayListSet<T> a_answerSet) {
        a_firstSet.addElement(a_secondSet.getLastElement());
        Assertions.assertEquals(a_answerSet, a_firstSet);
    }
}