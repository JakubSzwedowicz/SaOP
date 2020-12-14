package Int1;

public class FunctionClass implements Function {
    private double m_dx;

    public FunctionClass(double a_dx) {
        m_dx = a_dx;
    }

    @Override
    public double f(double a_x) {
        return Math.sin(a_x);
    }
//        public double f(double a_x) {
//            return Math.pow(a_x, 2) - 2 * a_x;
//        }

    @Override
    public double integral(double a_x) {
        return -Math.cos(a_x);
    }

    @Override
    public boolean hasIntegral() {
        return true;
    }

    @Override
    public double getDX() {
        return m_dx;
    }

    @Override
    public boolean setDX(double a_newDX) {
        m_dx = a_newDX;
        return true;
    }

    @Override
    public String formula() {
        return "sin(x)";
    }

    @Override
    public String toString() {
        return "sin(x), error = " + m_dx;
    }
}

