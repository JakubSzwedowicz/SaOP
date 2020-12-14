import H1.CompoundFigure;
import H2.EmployedStaff;
import H3.Invoice;

public class Main {
    public static void main(String[] args) {
        runTests();
    }

    private static void runTests() {
        // CompoundFigure is Serializable and it gets tested
        CompoundFigure.testClass();
        EmployedStaff.testClass();
        Invoice.testClass();

    }
}
