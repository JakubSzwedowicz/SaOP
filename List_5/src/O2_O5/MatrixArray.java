package O2_O5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import Utilities.Functions;

public class MatrixArray<T extends Number> {
    // members
    private MyArray<T> m_data;
    private int m_rows;
    private int m_rowWidth;
    private int m_size;
    private Class<T> m_dataType;

    // public
    MatrixArray(int a_rows, int a_columns, Class<T> a_dataType) throws IllegalArgumentException {
        if (a_rows < 0 || a_columns < 0) {
            throw new IllegalArgumentException("Cannot create a matrix with negative dimensions");
        }
        m_rows = a_rows;
        m_rowWidth = a_columns;
        m_size = m_rows * m_rowWidth;
        m_dataType = a_dataType;
        m_data = new MyArray<T>(m_size, m_dataType);
    }

    public boolean isDiverse() throws Exception {
        m_data.checkSize("Cannot check for diversity in empty matrix");
        return m_data.isDiverse2();
    }

    public void fillRandom(double a_lowerBound, double a_upperBound) throws Exception {
        if (m_size == 0) {
            throw new Exception("Cannot fill matrix with size set to 0");
        }
        m_data.fillRandom(a_lowerBound, a_upperBound);
    }

    public void resizeMatrix(int a_newRow, int a_newColumn) throws Exception {
        if (a_newRow < 0 || a_newColumn < 0) {
            throw new NegativeArraySizeException("Resized O2_O5.Matrix cannot have negative dimensions");
        }
        m_size = a_newRow * a_newColumn;
        m_rowWidth = a_newColumn;
        m_rows = a_newRow;
        m_data.resize(m_size);
        // Requires improvements!
        m_data.fillRandom(-5, 5);
    }

    public T sum() throws Exception {
        return m_data.sum();    // Can throw an exception
    }

    public T maxValue() throws Exception {
        return m_data.max();    // throws exception
    }

    public List<Integer[]> maxIndices() throws Exception {
        return convert1DArrayTo2D(m_data.maxIndex());   // already throws exception
    }

    public List<Integer> maxRow() throws Exception {
        m_data.checkSize("Max row cannot be found in empty array!");
        List<Integer> result = new ArrayList<>(m_rows);
        double sum = 0;
        double max = -Double.MAX_VALUE;
        for (int i = 0; i < m_size; i++) {
            sum += m_data.get(i).doubleValue();
            if ((i + 1) % m_rowWidth == 0) { // when done calculating sum of row
                if (sum > max) {
                    max = sum;
                    result.clear();
                    result.add(i / m_rowWidth);
                } else if (sum == max) {
                    result.add(i / m_rowWidth);
                }
                sum = 0;
            }
        }
        return result;
    }

    public List<Integer[]> findValue(T a_value) throws Exception {
        return convert1DArrayTo2D(m_data.findAll(a_value, m_size)); // throws exception
    }

    public T getNumber(int a_row, int a_column) throws Exception {
        m_data.checkSize("Nothing can be returned from empty array!");
        if (a_row < 0 || a_row >= m_rows || a_column < 0 || a_column >= m_rowWidth) {
            throw new ArrayIndexOutOfBoundsException("Out of bound indices!");
        }
        return m_data.get(a_row * m_rowWidth + a_column);
    }

    public T getNumber(int a_position) throws Exception {
        m_data.checkSize("Nothing can be returned from empty array!");
        if (a_position < 0 || a_position >= m_size) {
            throw new ArrayIndexOutOfBoundsException("Out of bound position");
        }
        return m_data.get(a_position);
    }

    public String toString() {
        StringBuilder output;
        output = new StringBuilder("data type: " + m_dataType);
        output.append("\nDimensions: ").append(m_rows).append("x").append(m_rowWidth);
        output.append("\n[ ");
        Number[] dataHandler = m_data.getArray();
        for (int i = 0; i < m_rows; i++) {
            output.append("[");
            for (int j = 0; j < m_rowWidth; j++) {
                output.append(dataHandler[i * m_rowWidth + j]);
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

    public void addColumn(List<T> a_column) throws Exception {
        insertColumn(a_column, m_rowWidth);
    }

    public void insertColumn(List<T> a_column, int a_place) throws Exception {
        if (a_place < 0 || a_place > m_rowWidth) {
            throw new IndexOutOfBoundsException("Wrong position of column was given!");
        } else if (a_column.size() != m_rows) {
            throw new IllegalArgumentException("Column size (" + a_column.size()
                    + ") does not match matrix dimension (" + m_rows + ")");
        }
        int cycles = a_column.size();
        m_size += cycles;
        boolean addLast = false;
        if (a_place == m_rowWidth) {
            cycles--;
            addLast = true;
        }
        for (int i = a_place, j = 0; j < cycles; i += (m_rowWidth + 1), j++) {
            m_data.moveRight(i, m_data.m_size - 1, 1);
            m_data.changeNumber(a_column.get(j), i);
        }
        if (addLast) {
            m_data.add(a_column.get(cycles));
        }
        m_rowWidth++;
    }

    public void addRow(List<T> a_row) throws Exception {
        insertRow(a_row, m_rows);
    }

    public void insertRow(List<T> a_row, int a_place) throws Exception {
        if (a_place > m_rows || a_place < 0) {
            throw new IndexOutOfBoundsException("Position for a new row was given wrong!");
        } else if (a_row.size() != m_rowWidth) {
            throw new Exception("Inserted row has wrong length!");
        }
        // works fine for 0 <= a_place <= m_rows
        m_data.insert(a_row, a_place * m_rowWidth);
        m_rows++;
        m_size+=m_rowWidth;
    }

    public static void testClass() {
        try {
            System.out.println("---------------------------------------------\n" +
                    "Testing class O2_O5.MatrixArray for O5 task\n" +
                    "----------------------------------------------");
            MatrixArray<Integer> a = new MatrixArray<>(3, 2, Integer.class);
            a.fillRandom(-5, 5);
            System.out.println(a);
            List<Integer> newColumn = new ArrayList<Integer>(Arrays.asList(10, 10, 10));
            System.out.println("Adding 2 columns " + newColumn);
            a.addColumn(newColumn);
            a.addColumn(newColumn);
            System.out.println(a);
            newColumn.clear();
            newColumn.addAll(Arrays.asList(-20, -20, -20, -20));
            System.out.println("Adding 3 rows " + newColumn);
            a.addRow(newColumn);
            a.addRow(newColumn);
            a.addRow(newColumn);
            System.out.println(a);
            System.out.println("Is diverse? = " + a.isDiverse());
            System.out.println("Max row? = " + a.maxRow());
            List<Integer[]> indicesHandler = a.maxIndices();
            System.out.println("Max values lay under indices? = " + Arrays.deepToString(indicesHandler.toArray()));
            System.out.println("Max value? = " + a.maxValue());
            int value = 3;
            System.out.println("Looking for the value " + value + " that was found on indices" + Arrays.deepToString(a.findValue(value).toArray()));

        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
    // private

    // creates slight overhead but worth for simplicity in overall
    private List<Integer[]> convert1DArrayTo2D(List<Integer> a_array) {
        List<Integer[]> result = new ArrayList<Integer[]>(a_array.size());
        for (int i = 0; i < a_array.size(); i++) {
            result.add(new Integer[2]);
            result.get(i)[0] = a_array.get(i) / m_rowWidth;    // row
            result.get(i)[1] = a_array.get(i) % m_rowWidth;    // column
        }
        return result;
    }

    private List<Integer> convert2DArrayTo1D(List<Integer[]> a_array) {
        List<Integer> result = new ArrayList<Integer>(a_array.size() * 2);
        for (int i = 0; i < result.size(); i++) {
            result.add(a_array.get(i)[0] * m_rowWidth + a_array.get(i)[1]);
        }
        return result;
    }

}
