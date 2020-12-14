package Int1;

public interface Function {
    // PUBLIC
    // symmetric central formula is more precise
    default double df(double a_x) {
        double a_h = getDX();
        return ((f(a_x + a_h) - f(a_x - a_h)) / (2*a_h));
//        return ((f(a_x + a_h) - f(a_x - a_h)) / (2*a_h));
    }

    // central derivative
    // it appears symetric formula has the best precision, up to 1e-8 and no more
    default double d2f(double a_x) {
        double a_h = getDX();
//        return (f(a_x + a_h) - 2 * f(a_x) + f(a_x - a_h)) / (Math.pow(a_h, 2));   // mathlab central finite difference
//        return (f(a_x + 2 * a_h) + f(a_x) - 2 * f(a_x + a_h)) / (Math.pow(a_h, 2));   // from pure definition
        return (f(a_x + 2 * a_h) - 2 * f(a_x) + f(a_x - 2 * a_h)) / (4 * Math.pow(a_h, 2)); // from symmetric formula for df
//        return (df(a_x + a_h) - df(a_x)) / (a_h);
    }

    // central derivative
    // gives wrong results, but still can be used to find
    default double d3f(double a_x) {
        double a_h = getDX();
        return (f(a_x + 2 * a_h) - 2 * f(a_x + a_h) + 2 * f(a_x - a_h) - f(a_x - 2 * a_h)) / (2 * Math.pow(a_h, 3)); // matlab central finite difference
//        return (f(a_x + 3 * a_h) - 3 * f(a_x + a_h) + 3 * f(a_x - a_h) - f(a_x - 3 * a_h)) / (8 * Math.pow(a_h, 3));
    }
    // not adding the backward and forward derivative

    double f(double a_x);

    default boolean hasIntegral() {
        return false;
    }

    default double integral(double a_x) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Integral definition not calculated");
    }

    double getDX();

    boolean setDX(double a_newDX);

    String formula();

}
