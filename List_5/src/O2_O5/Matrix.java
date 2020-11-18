package O2_O5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import Utilities.Functions;

public class Matrix<T extends Number> {
    // members
    private Number[] m_data;
    private int m_rowWidth;
    private int m_rows;
    private int m_size;
    private Class<T> m_dataType;

    private static final Number[] EMPTY_ARRAY = {};
    private static final List<Integer> EMPTY_LIST = Collections.unmodifiableList(new ArrayList<>(0));

    // public
    Matrix(int a_row, int a_column, Class<T> a_dataType) throws IllegalArgumentException {
        if (a_row < 0 || a_column < 0) {
            throw new IllegalArgumentException("Dimensions of a matrix cannot be negative!");
        }
        m_size = a_row * a_column;
        m_rowWidth = a_column;
        m_rows = a_row;
        m_dataType = a_dataType;
        m_data = new Number[m_size];
    }

    public T max() {
        Double max = Double.MIN_VALUE;
        for (Number element : m_data) {
            if (element.doubleValue() > max.doubleValue()) {
                max = element.doubleValue();
            }
        }
        return (T) max;
    }

    public List<Integer[]> findMaxIndices() {
        List<Integer> result = new ArrayList<>(m_size);
        Double max = Double.MIN_VALUE;
        for (int i = 0; i < m_size; i++) {
            if (m_data[i].doubleValue() > max.doubleValue()) {
                max = m_data[i].doubleValue();
                result.clear();
                result.add(i);
            } else if (m_data[i].doubleValue() == max.doubleValue()) {
                result.add(i);
            }
        }
        return convert1DArrayTo2D(result);
    }

    public int maxRow() throws Exception {
        checkSize("There isn't any max row in empty matrix");
        Double max = Double.MIN_VALUE;
        Double rowSum = 0.0;
        int maxRowIndex = -1;
        for (int i = 0; i < m_size; i++) {
            rowSum += m_data[i].doubleValue();
            if ((i + 1) % m_rowWidth == 0) {
                if (rowSum > max) {
                    maxRowIndex = i / m_rowWidth;
                    max = rowSum;
                    rowSum = 0.0;
                }
            }
        }
        return maxRowIndex;
    }

    public List<Integer[]> findNumber(T a_number) throws Exception {
        checkSize("There is nothing to look for in an empty array!");
        List<Integer> result = EMPTY_LIST;
        for (int i = 0; i < m_size; i++) {
            if (a_number.doubleValue() == m_data[i].doubleValue()) {
                if (result.size() == 0) {
                    result = new ArrayList<Integer>(m_size);
                }
                result.add(i);
            }
        }
        return convert1DArrayTo2D(result);
    }

    public boolean isDiverse() throws Exception {
        checkSize("Can't check if matrix is diverse when it's empty!");
        for (int i = 0; i < m_size - 1; i++) {
            T number = (T) m_data[i];
            for (int j = i + 1; j < m_size; j++) {
                if (number.equals(m_data[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    public Matrix<T> transpose() throws Exception {
        checkSize("Cannot transpose empty matrix!");
        Matrix<T> transposedMatrix = new Matrix<T>(m_rowWidth, m_rows, m_dataType);
        for (int i = 0; i < m_size; i++) {
            transposedMatrix.m_data[(i / m_rowWidth) + (i % m_rowWidth) * (m_rowWidth - 1)] = m_data[i];
        }
        return transposedMatrix;
    }

    public void fillRandom(double a_lowerBound, double a_upperBound) throws Exception {
        checkSize("Empty array cannot be filled");
        if (checkWholeNumbers()) {
            for (int i = 0; i < m_size; i++) {
                m_data[i] = Functions.getRandomInt((int) (a_upperBound - a_lowerBound)) + a_lowerBound;
            }
        } else {
            for (int i = 0; i < m_size; i++) {
                m_data[i] = Functions.getRandomDouble() * (a_upperBound - a_lowerBound) + a_lowerBound;
            }
        }
    }

    public String toString() {
        StringBuilder output;
        output = new StringBuilder("data type: " + m_dataType);
        output.append("\nDimensions: ").append(m_rows).append("x").append(m_rowWidth);
        output.append("\n[ ");
        for (int i = 0; i < m_rows; i++) {
            output.append("[");
            for (int j = 0; j < m_rowWidth; j++) {
                output.append(m_data[i * m_rowWidth + j]);
                if (j != m_rowWidth - 1) {
                    output.append(", ");
                }
            }
            output.append("]");
            if (i != m_rows - 1) {
                output.append(",\n ");
            }
        }
        output.append(" ]");
        return output.toString();
    }

    public static void testClass() {
        try {
            System.out.println("---------------------------------------------\n" +
                    "Testing class O2_O5.Matrix for O4 task\n" +
                    "----------------------------------------------");
            Matrix<Integer> a = new Matrix<Integer>(2, 3, Integer.class);
            a.fillRandom(0, 5);
            System.out.println(a);
            System.out.println("Printing max element " + a.max());
            System.out.println("Printing max row " + a.maxRow());
            int value = 3;
            System.out.println("Looking for number " + value + ":");
            System.out.println("Results " + Arrays.deepToString(a.findNumber(value).toArray()));
            System.out.println("Is matrix diverse? " + a.isDiverse());
            System.out.println("Looking for indices of max elements " + Arrays.deepToString(a.findMaxIndices().toArray()));
            System.out.println("Transposing:");
            Matrix b = a.transpose();
            System.out.println(b);
        } catch (Exception e) {
            System.out.println("Caught exception: " + e.getMessage());
        }
    }

    // private
    private void checkSize(String a_message) throws Exception {
        if (m_size == 0) {
            throw new Exception(a_message);
        }
    }

    private boolean checkWholeNumbers() {
        return m_dataType == Integer.class || m_dataType == Long.class || m_dataType == short.class ? true : false;
    }

    private List<Integer[]> convert1DArrayTo2D(List<Integer> a_array) {
        List<Integer[]> result = new ArrayList<Integer[]>(a_array.size());
        for (int i = 0; i < a_array.size(); i++) {
            result.add(new Integer[2]);
            result.get(i)[0] = a_array.get(i) / m_rowWidth;    // row
            result.get(i)[1] = a_array.get(i) % m_rowWidth;    // column
        }
        return result;
    }
}
