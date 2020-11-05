public class T3 {
    // public
    public static void main(){
        runTask();
    }

    private static void runTask(){
        System.out.println("-------------------------------------------------\nThird demo program T3");
        Scheme scheme = new Scheme(5, Double.class);
        scheme.fillPoints(5, -10, 10, -10, 10, Double.class);
        scheme.calculateEverything();
        scheme.printPoints();
        scheme.printSquare();
        scheme.printFurthestPoint();
        scheme.printDistancesOfPoints();
        scheme.printMaxDistancesOfPoints();
        scheme.printBiggestTriangle();

        System.out.println("After sorting by radius");
        scheme.sort(0);
        scheme.printPoints();
        scheme.printSquare();
        scheme.printFurthestPoint();
        scheme.printDistancesOfPoints();
        scheme.printMaxDistancesOfPoints();
        scheme.printBiggestTriangle();

        System.out.println("After sorting by distance");
        scheme.sort(1);
        scheme.printPoints();
        scheme.printSquare();
        scheme.printFurthestPoint();
        scheme.printDistancesOfPoints();
        scheme.printMaxDistancesOfPoints();
        scheme.printBiggestTriangle();

    }
    // private
}
