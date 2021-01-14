package MyGenericSetTest;

import MyGenericTreeSet.MyTreeSet;
import Utilities.Stopwatch;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

// Turn off optimization first:
// -Djava.compiler=NONE

// Class testing MyTreeSet
public class MyTreeSetTest {
    private static Stopwatch timer = new Stopwatch();

    @BeforeEach
    public void prepareForTest() {
        timer.reset();
        timer.start();
    }

    @AfterEach
    public void finishAfterTest(TestInfo testInfo) {
        timer.stop();
        System.out.println("LOGGER: execution time of " + testInfo.getDisplayName() + " took " + timer.getElapsedTime() / 1000000 + "[ms]");
    }

    @ParameterizedTest(name = "testAddSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testAddSet")
    public <T> void testAddSet(MyTreeSet<T> a_first, MyTreeSet<T> a_second, MyTreeSet<T> a_answer) {
        MyTreeSet<T> sumSet = a_first.addSet(a_second);
        Assertions.assertEquals(a_answer, sumSet);
    }

    @ParameterizedTest(name = "testAddSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testAddSet")
    public <T> void testAddSet2(MyTreeSet<T> a_first, MyTreeSet<T> a_second, MyTreeSet<T> a_answer) {
        MyTreeSet<T> sumSet = a_first.addSet2(a_second);
        Assertions.assertEquals(a_answer, sumSet);
    }

    @ParameterizedTest(name = "testSubtractSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testSubtractSet")
    public <T> void testSubtractSet2(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        MyTreeSet<T> subtractionSet = a_firstSet.subtractSet2(a_secondSet);
        Assertions.assertEquals(a_answerSet, subtractionSet);
    }

    @ParameterizedTest(name = "testSubtractSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testSubtractSet")
    public <T> void testSubtractSet(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        MyTreeSet<T> subtractionSet = a_firstSet.subtractSet(a_secondSet);
        Assertions.assertEquals(a_answerSet, subtractionSet);
    }

    @ParameterizedTest(name = "testIntersectSet (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testIntersectSet")
    public <T> void testIntersectSet(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.intersectSet(a_secondSet));
    }

    @ParameterizedTest(name = "testIntersectSet2 (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testIntersectSet")
    public <T> void testIntersectSet2(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.intersectSet2(a_secondSet));
    }

    @ParameterizedTest(name = "testSymmetricDifference (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testSymmetricDifference")
    public <T> void testSymmetricDifference(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        Assertions.assertEquals(a_answerSet, a_firstSet.symmetricDifference(a_secondSet));
    }

    @ParameterizedTest(name = "testAddElement (Run: {index}): " +
            "a_first={0}, a_second={1}, a_answer={2}")
    @MethodSource("MyGenericSetTest.MySetTest_Parameters#testAddElement")
    public <T> void testAddElement(MyTreeSet<T> a_firstSet, MyTreeSet<T> a_secondSet, MyTreeSet<T> a_answerSet) {
        a_firstSet.addElement(a_secondSet.getLastElement());
        Assertions.assertEquals(a_answerSet, a_firstSet);
    }
}