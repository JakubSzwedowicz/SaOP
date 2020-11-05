public final class Matrix {
    // members
    private int m_row;
    private int m_rowWidth;
    private int m_size;
    private double[] m_data;    // Optimization and simplification, it lacks pointers though

    // public
    // constructors
    public Matrix(int a_size) {
        this(a_size, a_size);
    }

    public Matrix(int a_row, int a_column) {
        m_rowWidth = a_column;
        m_row = a_row;
        m_size = m_rowWidth * m_row;
        m_data = new double[m_size];
    }

    public void printMatrix() {
        System.out.print("[");
        for (int i = 0; i < m_size; i += m_rowWidth) {
            if(i == 0){
                System.out.print("[");
            } else{
                System.out.print("\n[");
            }
            for (int j = 0; j < m_rowWidth; j++) {
                System.out.print(" " + m_data[i + j]);
            }
            System.out.print(" ]");
        }
        System.out.println("]\n");
    }

    public Matrix addMatrix(Matrix a_second) {
        if (m_row != a_second.m_row || m_rowWidth != a_second.m_rowWidth) {
            System.out.println("Matrixes with different dimensions can't be added!");
            return null;
        }
        Matrix res = new Matrix(m_row, m_rowWidth);
        for (int i = 0; i < m_size; i++) {
            res.m_data[i] = m_data[i] + a_second.m_data[i];
        }

        return res;
    }

    public Matrix multiplyMatrix(Matrix a_second) {
        if (m_rowWidth != a_second.m_row) {
            System.out.println("First matrix must have number of rows equal to " +
                    "the number of columns of the second matrix");
            return null;
        }
        Matrix res = new Matrix(m_row, a_second.m_rowWidth);
        for (int i = 0; i < res.m_size; i++) {
            res.m_data[i] = multiplyArray(a_second, i / a_second.m_rowWidth, i % a_second.m_rowWidth);
        }
        return res;
    }

    public Matrix transpose() {
        if (m_data.length == 0) {
            System.out.println("Matrix without elements cannot be transposed");
            return this;
        }
        Matrix res = new Matrix(m_rowWidth, m_row);
        for (int i = 0; i < res.m_size; i++) {
            res.m_data[i / m_rowWidth + (i % m_rowWidth) * m_row] = m_data[i];  // Formula found out on a sheet of paper
        }
        return res;
    }

    public <T extends Number> void fillMatrix(Class<T> a_class, double a_bound) {
        Functions.fillArray(m_data, a_class, 1, a_bound);
    }

    // private
    private double multiplyArray(Matrix a_second, int a_row, int a_column) {
        double res = 0;
        int rowStep = a_row * m_rowWidth;
        int columnStep = a_column + a_second.m_rowWidth;
        for (int i = 0; i < m_rowWidth; i++) {
            res += m_data[i + rowStep] * a_second.m_data[a_column + i * a_second.m_rowWidth];
        }
        return res;
    }
}
