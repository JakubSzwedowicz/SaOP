import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Very ugly solution
// Should have grouped pieces of information about the points in some nested class and not in separate arrays
public class Scheme {
    // members
    private Point2D[] m_points;
    private double[] m_pointsRadius;
    private double[][] m_pointsDistances;
    private Point2D[] m_square;
    private int m_size;
    private double m_maxTriangleArea;
    private double m_maxDistance;
    private double m_maxRadius;
    private boolean m_isRadiusCalculated, m_isSquareCalculated, m_isDistanceCalculated, m_isTriangleCalculated, m_isSorted;
    Class m_dataType;
    // public
    public <T extends Number> Scheme(int a_numberOfPoints, Class<T> a_class) {
        clear(false);
        m_dataType = a_class;
        m_size = a_numberOfPoints;
        m_points = new Point2D[m_size];
//        m_square = new Point2D[4];
        if (m_size > 0) {
            fillPoints(a_numberOfPoints, -10, 10, -10, 10, m_dataType);
        }
    }

    public <T extends Number> void fillPoints(double a_minX, double a_maxX, double a_minY, double a_maxY, Class<T> a_class) {
        fillPoints(m_size, a_minX, a_maxX, a_minY, a_maxY, a_class);
    }

    public <T extends Number> void fillPoints(int a_numberOfPoints, double a_minX, double a_maxX, double a_minY, double a_maxY, Class<T> a_class) {
        if(m_points == null){
            clear(true);
        }
        if (a_numberOfPoints != m_size) {
            m_size = a_numberOfPoints;
            m_points = new Point2D[a_numberOfPoints];
            m_pointsRadius = new double[a_numberOfPoints];
        }
        m_dataType = a_class;
        double[] x = new double[m_size];
        double[] y = new double[m_size];
        Functions.fillArray(x, m_dataType, a_minX, a_maxX);
        Functions.fillArray(y, m_dataType, a_minY, a_maxY);
        for (int i = 0; i < m_size; i++) {
            m_points[i] = new Point2D.Double(x[i], y[i]);
        }
        // With the new points all the previous results are no longer valid
    }

    public void clear(boolean a_savePoints) {
        m_pointsRadius = null;
        m_pointsDistances = null;
        m_square = null;
        m_maxTriangleArea = 0;
        m_maxDistance = 0;
        m_maxRadius = 0;
        if (!a_savePoints) {
            m_points = null;
            m_size = 0;
        }
        resetFlags();
    }

    // algorithms
    public int[][] findFurtherPointsDistance() {
        if (m_size < 2) {
            System.out.println("There are no points to calculate distance between");
            return null;
        } else if (!m_isDistanceCalculated) {
            calculatePointsDistance();
        }
        List<Integer> maxIndexValue = new ArrayList<>(10);
        for (int i = 0; i < m_pointsDistances.length; i++) {
            if (m_pointsDistances[i][0] == m_maxDistance) {
                maxIndexValue.add(i);
            }
        }
//        else{
//            double maxValue = 0;
//            for (int i = 0; i < m_pointsDistances.length; i++) {
//                if (m_pointsDistances[i][0] > maxValue) {
//                    maxIndexValue.clear();
//                    maxIndexValue.add(i);
//                    maxValue = m_pointsDistances[i][0];
//                } else if (m_pointsDistances[i][0] == maxValue) {
//                    maxIndexValue.add(i);
//                }
//            }
//            m_maxDistance = maxValue;
//        }
        int[][] res = new int[maxIndexValue.size()][2];
        for (int i = 0; i < res.length; i++) {
            res[i][0] = (int) m_pointsDistances[maxIndexValue.get(i)][1];
            res[i][1] = (int) m_pointsDistances[maxIndexValue.get(i)][2];
        }
        return res;
    }

    public int[][] findBiggestTriangle() {
        if (m_size < 3) {
            System.out.println("There are not enough points to find any triangle");
            return null;
        }
        List<Integer> results = new ArrayList<>(m_points.length * (m_points.length - 1) * (m_points.length - 2) / 6);
        double maxArea = 0;
        if (m_isTriangleCalculated) {
            maxArea = m_maxTriangleArea;
        }
        double xA, yA;
        double xB, yB;
        double xC, yC;
        for (int i = 0; i < m_points.length - 2; i++) {
            xA = m_points[i].getX();
            yA = m_points[i].getY();
            for (int j = i + 1; j < m_points.length - 1; j++) {
                xB = m_points[j].getX();
                yB = m_points[j].getY();
                for (int k = j + 1; k < m_points.length; k++) {
                    xC = m_points[k].getX();
                    yC = m_points[k].getY();
                    double area = Math.abs(xA * (yB - yC) + xB * (yC - yA) + xC * (yA - yB)) / 2;
                    if (area == maxArea) {
                        results.addAll(Arrays.asList(i, j, k));
                    } else if (area > maxArea) {
                        maxArea = area;
                        results.clear();
                        results.addAll(Arrays.asList(i, j, k));
                    }
                }
            }
        }
        int size = results.size() / 3;
        int[][] res = new int[size][3];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 3; j++) {
                res[i][j] = results.get(i * 3 + j);
            }
        }
        m_maxTriangleArea = maxArea;
        m_isTriangleCalculated = true;
        return res;
    }

    public void sort(int a_option) {
        // a_option: 0 - by Radius, 1 - by distance
        // It's going to use QuickSort
        // It is needed to redo indexing in m_pointsDistances once the points in m_points switch places
        if(a_option == 0){
            int[] newIndex = new int[m_size];
            for (int i = 0; i < m_size; i++) {
                newIndex[i] = i;
            }
            sortQuickSort(0, m_size - 1, a_option, newIndex);
            updateAfterSorting(0, newIndex);
        } else{
            sortQuickSort(0, m_pointsDistances.length - 1, a_option);
        }
        m_isSorted = true;
    }

    // computations

    public void calculateEverything() {
        calculateSquare();
        calculatePointsRadius();
        calculatePointsDistance();
    }

    public void calculatePointsRadius() {
        if (m_size == 0) {
            System.out.println("There are no points to calculate theirs radius");
            return;
        } else if ((m_pointsRadius == null) || (m_pointsRadius.length != m_points.length)) {
            m_pointsRadius = new double[m_points.length];
        } else if (m_isRadiusCalculated) {
            return;
        }
        double maxRadius = 0;
        for (int i = 0; i < m_size; i++) {
            double radius = m_points[i].distance(0, 0);
            m_pointsRadius[i] = radius;
            if (radius > maxRadius) {
                maxRadius = radius;
            }
        }
        m_maxRadius = maxRadius;
        m_isRadiusCalculated = true;
    }

    public void calculateSquare() {
        if (m_size < 2) {
            System.out.println("There are no enough points");
            return;
        } else if (m_isSquareCalculated) {
            return;
        } else if (m_square == null) {
            m_square = new Point2D[4];
            for (int i = 0; i < m_square.length; i++) {
                m_square[i] = new Point2D.Double();
            }
        }
        double maxY = 0;
        double minY = 0;
        double maxX = 0;
        double minX = 0;
        for (Point2D p : m_points) {
            if (p.getY() > maxY) {
                maxY = p.getY();
            }
            if (p.getY() < minY) {
                minY = p.getY();
            }
            if (p.getX() > maxX) {
                maxX = p.getX();
            }
            if (p.getX() < minX) {
                minX = p.getX();
            }
        }
        if (maxY == minY || maxX == minX) {
            System.out.println("Can't compute square - linear points");
            return;
        }
        m_square[0].setLocation(maxX, maxY);
        m_square[1].setLocation(minX, maxY);
        m_square[2].setLocation(minX, minY);
        m_square[3].setLocation(maxX, minY);
        m_isSquareCalculated = true;
    }

    public void calculatePointsDistance() {
        if (m_size < 2) {
            System.out.println("There are no points to calculate distance between");
            return;
        } else if (m_isDistanceCalculated) {
            return;
        }
        int size = m_size * (m_size - 1) / 2;   // possible optimization with size /= 2
        if (m_pointsDistances == null || m_pointsDistances.length != size) {
            m_pointsDistances = new double[size][3];    // 0 - distance, 1 - first point index, 2 - second point index
        }
        int index = 0;
        int step = m_points.length - 1;
        for (int i = 0; i < m_size - 1; i++) {
            for (int j = i + 1; j < m_size; j++) {
                double distance = Point2D.distance(m_points[i].getX(), m_points[i].getY(), m_points[j].getX(), m_points[j].getY());
                m_pointsDistances[index + j - 1 - i][0] = distance;
                m_pointsDistances[index + j - 1 - i][1] = i;
                m_pointsDistances[index + j - 1 - i][2] = j;
                if (distance > m_maxDistance) {
                    m_maxDistance = distance;
                }
            }
            index += step;
            step--;
        }
        m_isDistanceCalculated = true;
    }


    // prints
    public void printFurthestPoint() {
        if (m_size == 0) {
            System.out.println("There are no points to look for!");
            return;
        } else if (!m_isRadiusCalculated) {
            calculatePointsRadius();
        }
        System.out.println("Printing the furthest points");
        int pointNumber = 0;
        for (int i = 0; i < m_size; i++) {
            if (m_pointsRadius[i] == m_maxRadius) {
                System.out.println("Point " + pointNumber + ": \n [" + printSinglePoint(i) + ", "
                        + ", r = " + m_pointsRadius[i]
                        + "]");
                pointNumber++;
            }
        }
        System.out.println();
    }

    public void printBiggestTriangle() {
        if (m_size < 3) {
            System.out.println("There are nto enough points to find any triangle");
        }
        int[][] triangles = findBiggestTriangle();
        System.out.println("Printing the points of the biggest triangle:");
        for (int i = 0; i < triangles.length; i++) {
            System.out.println("Triangle " + i + ": \n [" + printSinglePoint(triangles[i][0]) + ", "
                    + printSinglePoint(triangles[i][1]) + ", " + printSinglePoint(triangles[i][2]) + ", Area = "
                    + m_maxTriangleArea + "]");
        }
        System.out.println();
    }

    public void printMaxDistancesOfPoints() {
        if (m_size < 2) {
            System.out.println("There are no distances to print!");
            return;
        }
        int[][] points;
        points = findFurtherPointsDistance();
        System.out.println("Printing the furthest pairs of points:");
        for (int i = 0; i < points.length; i++) {
            System.out.println("Pair " + i + ": \n [" + printSinglePoint(points[i][0]) + ", "
                    + printSinglePoint(points[i][1])
                    + ", distance = " + m_pointsDistances[getDistanceIndexBasedOnPoints(points[i][0], points[i][1])][0]
                    + "]");
        }
        System.out.println();
    }

    public void printDistancesOfPoints() {
        if (m_size < 2) {
            System.out.println("There are no distances to print!");
            return;
        } else if (!m_isDistanceCalculated) {
            calculatePointsDistance();
        }
        System.out.println("Printing all the distances between the points: ");
        int first, second;
        for (int i = 0; i < m_pointsDistances.length; i++) {
            first = (int) m_pointsDistances[i][1];
            second = (int) m_pointsDistances[i][2];
            System.out.println("Pair " + i + ": \n [" + printSinglePoint(first) + ", "
                    + printSinglePoint(second)
                    + ", distance = " + m_pointsDistances[getDistanceIndexBasedOnPoints(first, second)][0]
                    + "]");
        }
        System.out.println();
    }

    public void printPoints() {
        if (m_size == 0) {
            System.out.println("There are no points to print");
            return;
        }
        System.out.println("Printing points:");
        for (int i = 0; i < m_points.length; i++) {
            System.out.println("Point " + i + ":\n" + printSinglePoint(i));
        }
        System.out.println();
    }

    public void printSquare() {
        if (m_size < 2) {
            System.out.println("There are no enough points to find any square");
            return;
        } else if (!m_isSquareCalculated) {
            calculateSquare();
        }
        System.out.println("Printing square: ");
        System.out.println("P1(" + m_square[1].getX() + ", " + m_square[1].getY() + ") \t P0("
                + m_square[0].getX() + ", " + m_square[0].getY() + ") \nP2("
                + m_square[2].getX() + ", " + m_square[2].getY() + ") \t P3("
                + m_square[3].getX() + ", " + m_square[3].getY() + ")\n");
    }

    public String printSinglePoint(int a_index) {
        if (a_index < 0 || a_index >= m_size) {
            System.out.println("Wrong index!");
            return null;
        }
        String stringPoint;
        stringPoint = "(" + m_points[a_index].getX() + ", " + m_points[a_index].getY() + ", r = " + m_pointsRadius[a_index] + ")";
        return stringPoint;
    }

    // private
    private void resetFlags() {
        m_isRadiusCalculated = false;
        m_isSquareCalculated = false;
        m_isDistanceCalculated = false;
        m_isTriangleCalculated = false;
        m_isSorted = false;
    }

    private int getDistanceIndexBasedOnPoints(int a_first, int a_second) {
        if (a_first < 0 || a_first >= m_size || a_second < 0 || a_second >= m_size) {
            System.out.println("Wrong indexes!");
            return -1;
        } else if (!m_isDistanceCalculated) {
            calculatePointsDistance();
        }
        int step = m_points.length - 1;
        int sum = 0;
        if (!m_isSorted) {      // It's actually much faster to look for the correct distance in unsorted array
            if (a_first > a_second) {
                int temp = a_first;
                a_first = a_second;
                a_second = temp;
            }
            for (int i = 0; i < a_first; i++) {
                sum += step;
                step--;
            }
            sum += (a_second - a_first - 1);
            return sum;
        } else {
            for (int i = 0; i < m_pointsDistances.length; i++) {
                if (m_pointsDistances[i][1] == a_first && m_pointsDistances[i][2] == a_second) {
                    return i;
                }
            }
        }
        return -1;
    }

    // Sorting
    private void sortQuickSort(int a_begin, int a_end, int a_option){
        sortQuickSort(a_begin, a_end, a_option, null);
    }
    private void sortQuickSort(int a_begin, int a_end, int a_option, int[] a_newIndex) {
        if (a_begin < a_end) {
            int pivot = sortQuickSortPartitioning(a_begin, a_end, a_option, a_newIndex);
            sortQuickSort(a_begin, pivot - 1, a_option, a_newIndex);
            sortQuickSort(pivot + 1, a_end, a_option, a_newIndex);
        }
    }

    private int sortQuickSortPartitioning(int a_begin, int a_end, int a_option, int[] a_newIndex) {
        double pivot;
        int i = a_begin;
        if (a_option == 0) {    // Do a single a_option check instead of putting it into the loop
            pivot = m_pointsRadius[a_end];
            // Starts from the a_begin because algorithm needs to check first element as well
            for (int j = a_begin; j < a_end; j++) {
                if (m_pointsRadius[j] < pivot) {
                    swapPoints(i, j, a_newIndex);
                    i++;
                }
            }
            swapPoints(i, a_end, a_newIndex);
        } else if (a_option == 1) {
            double[] temp;
            pivot = m_pointsDistances[a_end][0];
            for (int j = a_begin; j < a_end; j++) {
                if (m_pointsDistances[j][0] <= pivot) {
                    temp = m_pointsDistances[j];
                    m_pointsDistances[j] = m_pointsDistances[i];
                    m_pointsDistances[i] = temp;
                    i++;
                }
            }
            temp = m_pointsDistances[i];
            m_pointsDistances[i] = m_pointsDistances[a_end];
            m_pointsDistances[a_end] = temp;
        }
        return i;
    }

    private void updateAfterSorting(int a_option, int[] a_newIndexes) {
        double[] row;
        int[] correspondingIndexes = new int[a_newIndexes.length];
        // It finally works...
//        for(int i = 0; i < a_newIndexes.length; i++){
//            System.out.println(i + " " + a_newIndexes[i]);
//        }
//        System.out.println("");
        for(int i = 0; i < correspondingIndexes.length; i++){
            int j = 0;
            while(i != a_newIndexes[j]){
                j++;
            }
            correspondingIndexes[i] = j;
        }
//        for(int i = 0; i < a_newIndexes.length; i++){
//            System.out.println(i + " " + correspondingIndexes[i]);
//        }
        if (a_option == 0) {  // 0 - points are sorted by radius so distances require updating
            for (double[] m_pointsDistance : m_pointsDistances) {
                row = m_pointsDistance;
//                if(row[1] > row[2]){
//                    int temp = (int)row[1];
//                    row[1] = row[2];
//                    row[2] = temp;
//                }
                row[1] = correspondingIndexes[(int) row[1]];
                row[2] = correspondingIndexes[(int) row[2]];
            }
        }
    }

    // Swapping points
    private void swapPoints(int a_first, int a_second, int[] a_pointsIndexes) {
        // Not doing the if(a_first == a_second) return; bc its not worth it
        int temp = a_pointsIndexes[a_first];
        a_pointsIndexes[a_first] = a_pointsIndexes[a_second];
        a_pointsIndexes[a_second] = temp;
        swapPoints(a_first, a_second);
    }

    private void swapPoints(int a_first, int a_second) {
        // Temp values
        Point2D tempPoint = m_points[a_first];
        double tempRadius = m_pointsRadius[a_first];
        // Replacing first element
        m_points[a_first] = m_points[a_second];
        m_pointsRadius[a_first] = m_pointsRadius[a_second];
        // Replacing second element
        m_points[a_second] = tempPoint;
        m_pointsRadius[a_second] = tempRadius;
    }
}
