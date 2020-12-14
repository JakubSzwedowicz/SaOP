import Int1.MyArray;
import Int2.SetOfMethods;
import Int3.Overseer;

public class Utilities {

    // MEMBERS

    // PUBLIC
    public static void runTasks() {
        Int1();
        Int2();
        Int3();
    }

    private static void Int1() {
        System.out.println("----------------------------\nRunning task I1\n----------------------------");
        MyArray.testClass();
    }

    private static void Int2() {
        System.out.println("----------------------------\nRunning task I2\n----------------------------");
        SetOfMethods.testClass();
    }

    private static void Int3(){
        System.out.println("----------------------------\nRunning task I3\n----------------------------");
        Overseer.testClass();
    }
}
