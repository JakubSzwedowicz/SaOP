package O2_O5;

import java.util.*;
import Utilities.Functions;

public class MyArray<T extends Number> implements RandomAccess {
    // members
    protected int m_size;
    protected int m_capacity;
    protected Number[] m_array;
    protected Class<T> m_dataType;

    // empty, unmodifiable placeholders
    private static final Number[] EMPTY_ARRAY = {};
    protected static final List<Integer> EMPTY_LIST = Collections.unmodifiableList(new ArrayList<>(0));

    // public
    MyArray(int a_capacity, Class<T> a_class) throws NegativeArraySizeException {
        if (a_capacity < 0) {
            throw new NegativeArraySizeException("Array with negative size cannot be initialised");
        }
        m_dataType = a_class;
        m_size = 0;
        m_capacity = a_capacity;
        if (a_capacity == 0) {
            m_array = EMPTY_ARRAY;
        } else {
            m_array = new Number[a_capacity];
        }
    }

    public void add(List<T> a_array) {
        int endIndex = m_size;
        incrDecrSize(a_array.size());
        for (int i = endIndex, j = 0; i < m_size; i++, j++) {
            m_array[i] = a_array.get(j);
        }
    }
    public void add(T a_number) {
        if (m_size == m_capacity) {
            reallocate(m_capacity * 2);
        }
        m_array[m_size++] = a_number;
    }

    public void changeNumber(T a_number, int a_index) throws IndexOutOfBoundsException {
        if (a_index >= m_size || a_index < 0) {
            throw new IndexOutOfBoundsException("Number from out of bound index cannot be changed!");
        }
        m_array[a_index] = a_number;
    }

    public void insert(List<T> a_array, int a_index) throws Exception {
        if(a_index > m_size || a_index < 0){
            throw new IndexOutOfBoundsException("Given index is wrong for inserted array!");
        } else if (a_array.size() == 0){
            throw new Exception("Cannot insert an empty array!");
        } else if(a_index == m_size){
            add(a_array);
            return;
        }
        int endIndex = m_size;
        moveRight(a_index, a_index + a_array.size(), a_array.size());
        for (int i = a_index, j = 0; i < m_size; i++, j++) {
            m_array[i] = a_array.get(j);
        }
    }

    public void insert(T a_number) throws Exception {
        if (m_size == m_capacity) {
            reallocate(m_capacity * 2);
        }
        m_array[m_size++] = a_number;
    }

    public void insert(T a_number, int a_index) throws Exception {
        if(a_index > m_size || a_index < 0){
            throw new IndexOutOfBoundsException("Wrong index was given for insert operation!");
        } else if(a_index == m_size){
            add(a_number);
            return;
        }
        if (m_size == m_capacity) {
            reallocate(m_capacity * 2);
        }
        shiftRight(a_index, m_size - 1, 1);
        m_array[a_index] = a_number;
        m_size++;
    }

    // rotates the elements in range [a_begin, a_end] so that a_middle element becomes the first one
    // note: actual implementation doesn't rotate anything. Algorithm just serves the idea of rotation.
    // Owing to this fact, algorithm below is O(n)
    public void rotate(int a_begin, int a_middle, int a_end) throws Exception {
        checkSize("Cannot rotate an empty array");
        int cycles = a_end - a_begin + 1;
        int step = a_middle - a_begin;
        int nextIndex = a_middle;
        if (nextIndex < a_begin) {
            int excess = nextIndex - a_begin;
            nextIndex = a_end + excess;
        }
        Number valueToMove = m_array[a_middle];
        for (int i = 0; i < cycles; i++) {
            Number temp = m_array[nextIndex];
            m_array[nextIndex] = valueToMove;
            valueToMove = temp;
            nextIndex -= step;
            if (nextIndex < a_begin) {
                int excess = nextIndex - a_begin;
                nextIndex = a_end + excess;
            }
        }
    }

    // discards elements [a_end - a_positions, a_end]
    public void shiftRight(int a_begin, int a_end, int a_positions) {
        int i = a_end;
        for (; i >= a_begin + a_positions; i--) {
            m_array[i] = m_array[i - a_positions];
        }
        for (; i >= a_begin; i--) {
            m_array[i] = null;
        }
        if (m_size < 0) {
            m_size = 0;
        }
    }

    // discards elements [a_begin, a_begin + a_positions]
    public void shiftLeft(int a_begin, int a_end, int a_positions) {
        int i = a_begin;
        for (i = a_begin; i <= a_end - a_positions; i++) {
            m_array[i] = m_array[i + a_positions];
        }
        for (; i <= a_end; i++) {
            m_array[i] = null;
        }
        if (m_size < 0) {
            m_size = 0;
        }
    }


    public T sum() throws Exception {
        checkSize("Cannot calculate sum of empty array");
        if (!wholeNumberTypeCheck()) {
            Double sum = 0.0;
            for (int i = 0; i < m_size; i++) {
                sum += m_array[i].doubleValue();
            }
            return (T) sum;
        } else {
            Long sum = 0L;
            for (int i = 0; i < m_size; i++) {
                sum += m_array[i].longValue();
            }
            return (T) sum;
        }
    }

    public T max() throws Exception {
        checkSize("Max value cannot be found in empty array");
        Double max = -Double.MAX_VALUE;
        for (Number element : m_array) {
            if (max < element.doubleValue()) {
                max = element.doubleValue();
            }
        }
        return (T) max;
    }

    public List<Integer> maxIndex() throws Exception {
        checkSize("Max index cannot be found in empty array");
        double max = -Double.MAX_VALUE;
        List<Integer> result = new ArrayList<>(m_size);
        for (int j = 0; j < m_size; j++) {
            double value = m_array[j].doubleValue();
            if (value > max) {
                result.clear();
                result.add(j);
                max = value;
            } else if (max == value) {
                result.add(j);
            }
        }
        return result;
    }

    public void fillRandom(double a_lowerBound, double a_upperBound) throws Exception {
        if (m_capacity == 0) {
            throw new Exception("Cannot fill an empty array");
        }
        if (wholeNumberTypeCheck()) {
            for (int i = 0; i < m_capacity; i++) {
                m_array[i] = (int) (Functions.getRandomInt((int) (a_upperBound - a_lowerBound)) + a_lowerBound);
            }
        } else {
            for (int i = 0; i < m_capacity; i++) {
                m_array[i] = Functions.getRandomDouble() * (a_upperBound - a_lowerBound) + a_lowerBound;
            }
        }
        m_size = m_capacity;
    }

    public int find(T a_number, int a_range) throws Exception {
        checkSize("There is nothing to find in an empty array!");
        for (int i = 0; i < a_range; i++) {
            if (m_array[i].equals(a_number)) {
                return i;
            }
        }
        return -1;
    }

    public List<Integer> findAll(T a_number, int a_range) throws Exception {
        checkSize("There is nothing to look for in empty array");
        List<Integer> result = EMPTY_LIST;
        for (int j = 0; j < a_range; j++) {
            if (m_array[j].equals(a_number)) {
                if (result.size() == 0) {
                    result = new ArrayList<>(a_range);
                }
                result.add(j);
            }
        }
        return result;
    }

    // Moves the given subarray [a_begin, a_end] a_distance places to the right increasing the size
    // might need an improvement
    public void moveRight(int a_begin, int a_end, int a_distance) throws IllegalArgumentException {
        if (a_begin < 0 || a_begin >= m_size || a_end < 0 || a_end < a_begin || a_distance < 0) {
            throw new IllegalArgumentException("Wrong indices were given!");
        }
//        int maxIndex = a_end + a_distance;
//        if (maxIndex >= m_size) {
//            m_size += (m_size - maxIndex + 1);
//        }
        incrDecrSize(a_distance);
        int lastIndex = a_begin + a_distance;
        if (lastIndex > a_end) {
            lastIndex = a_end + 1;
        }
        for (int i = a_begin; i < lastIndex; i++) {
            T moveValue = (T) m_array[i];
            m_array[i] = null;
            for (int index = i + a_distance; index < m_size; index += a_distance) {
                T temp = (T) m_array[index];
                m_array[index] = moveValue;
                moveValue = temp;
            }
        }
    }

    public boolean isDiverse1() throws Exception {
        checkSize("Empty array cannot be checked for its diversity!");
        for (int i = m_size - 1; i > 0; i--) {
            if (findAll((T) m_array[i], i).size() != 0) {
                return false;
            }
        }
        return true;
    }

    // Should be faster and more efficient
    public boolean isDiverse2() throws Exception {
        checkSize("Empty array cannot be checked for its diversity");
        for (int i = 0; i < m_size - 1; i++) {
            T number = (T) m_array[i];
            for (int j = i + 1; j < m_size; j++) {
                if (number.equals(m_array[j])) {
                    return false;
                }
            }
        }
        return true;
    }

    //
    public void removeAllValues(T a_number) throws Exception {
        checkSize("Values cannot be removed from empty array");
        int j = m_size - 1;
        for (int i = 0; i < m_size; i++) {
            T value = (T) m_array[i];
            if (value == a_number) {
                if (i != j) {
                    m_array[i--] = m_array[j--];    // in case j == a_number
                    m_size--;
                } else {
                    m_size--;
                    j--;
                }
            }
        }
    }

    // in place algorithm
    public void removeDuplicates() throws Exception {
        if (m_size < 2) {
            throw new Exception("Not enough elements in array to look for duplicates!");
        }
        int newEndIndex = 0;
        for (int i = 1; i < m_size; i++) {
            T checkValue = (T) m_array[i];
            int k;
            // Checking for the already approved values is generally faster than looking for duplicates in whole array
            for (k = 0; k <= newEndIndex; k++) {
                if (m_array[k] == checkValue) {
                    break;
                }
            }
            if (k > newEndIndex) {
                m_array[k] = checkValue;
                newEndIndex++;
            }
        }
        m_size = newEndIndex + 1;
    }

    // Shrinks capacity to match size
    public void shrinkToFit() throws Exception {
        checkSize("Empty array cannot be shrank");
        if (m_size < m_capacity) {
            reallocate(m_size);
        }
    }

    public void removeNulls() throws Exception {
        checkSize("Empty array cannot be shrank");
        int sizeToDecrease = 0;
        for (int i = 0; i < m_size; i++) {
            if (m_array[i] == null) {
                sizeToDecrease++;
                for (int j = i + 1; j < m_size; j++) {
                    if (m_array[j] != null) {
                        m_array[i] = m_array[j];
                        m_array[j] = null;
                    }
                }
            }
        }
        m_size -= sizeToDecrease;
    }

    public String toString() {
        String output;
        output = "\t type: " + m_dataType + "\n\t size: " + m_size + "\n\t capacity: " + m_capacity + "\n[";
        for (int i = 0; i < m_size - 1; i++) {
            output += m_array[i] + ", ";
        }
        if (m_size - 1 != -1) {
            output += m_array[m_size - 1];
        }
        output += "]";
        return output;
    }

    public int getSize() {
        return m_size;
    }

    public Number[] getArray() {
        Number[] result = Arrays.copyOfRange(m_array, 0, m_size);
        return result;
    }

    public T get(int a_index) {
        return (T) m_array[a_index];
    }

    public static boolean testClass() {
        try {
            System.out.println("---------------------------------------------\n" +
                                "Testing class O2_O5.MyArray for O2 task\n" +
                                "----------------------------------------------");
            MyArray<Integer> a = new MyArray<>(20, Integer.class);
            a.fillRandom(0, 10);
            System.out.println("size: " + a.getSize() + " sum: " + a.sum() + " max: " + a.max() + " max index: " + a.maxIndex());
            System.out.println(a);
            System.out.println("Finding all the zeros in array under indices: ");
            System.out.println(a.findAll(0, a.getSize()).toString());
            System.out.println("Removing all the zeros from array");
            a.removeAllValues(0);
            System.out.println(a);
            System.out.println("Checking for diversity:");
            System.out.println(a.isDiverse1());
            System.out.println(a.isDiverse2());
            System.out.println(a);
            System.out.println("Removing all the duplicates");
            a.removeDuplicates();
            System.out.println(a);
            int value = 5;
            int index = a.find(value, a.getSize());
            if (index != -1) {
                System.out.println("Under index: " + index + " value " + value + " was found as: " + a.get(index));
            }
            int b = 3, e = 5, d = 3;
            System.out.println("Testing moveRight. Moving set [begin = " + b + ", end = " + e + "] by " + d + " to the right");
            a.moveRight(b, e, d);
            System.out.println(a);
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage() + " happened");
            return false;
        }
    }

    // private
    protected void checkSize(String a_message) throws NegativeArraySizeException {
        if (m_size == 0) {
            throw new NegativeArraySizeException(a_message);
        }
    }

    protected boolean wholeNumberTypeCheck() {
        return m_dataType == Integer.class || m_dataType == Long.class || m_dataType == short.class ? true : false;
    }

    protected void incrDecrSize(int a_number) {
        int newSize = m_size + a_number;
        if (newSize > m_capacity) {
            reallocate(m_capacity *= 2);
        } else if (newSize < 0) {
            newSize = 0;
        }
        m_size = newSize;
    }

    protected void reallocate(int a_newCapacity) throws NegativeArraySizeException {
        if (a_newCapacity < 0) {
            throw new NegativeArraySizeException("Capacity cannot be negative!");
        }
        m_capacity = a_newCapacity;
        Number[] newArray = new Number[m_capacity];
        for (int i = 0; i < m_size; i++) {
            newArray[i] = m_array[i];
        }
        m_array = newArray;
    }

    protected void resize(int a_newSize) throws NegativeArraySizeException {
        if (a_newSize < 0) {
            throw new NegativeArraySizeException("New size cannot be negative!");
        }
        if (a_newSize > m_capacity) {
            reallocate(a_newSize * 2);
        }
        for (int i = m_size; i < a_newSize; i++) {
            m_array[i] = null;
        }
        m_size = a_newSize;
    }


}
