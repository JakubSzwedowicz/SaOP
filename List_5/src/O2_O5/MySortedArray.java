package O2_O5;

import java.util.ArrayList;
import java.util.List;

public class MySortedArray<T extends Number> extends MyArray<T> {

    MySortedArray(int a_capacity, Class<T> a_dataType) {
        super(a_capacity, a_dataType);
    }

    @Override
    public void add(List<T> a_array) {
        // poor implementation and it actually might add a whole set.
        System.out.println("adding set of numbers to sorted array is forbidden!");
        return;
    }

    @Override
    public void add(T a_number) {
        if (m_size == m_capacity) {
            reallocate(m_capacity * 2);
        }
        int begin = bisectionFind(0, m_size - 1, a_number);
        Number temp = m_array[m_size - 1];
        shiftRight(begin, m_size - 1, 1);
        // or
//        moveRight(begin, m_size - 1, 1);
        m_array[begin] = a_number;
        m_array[m_size] = temp;
        m_size++;
    }

    @Override
    public void insert(T a_number, int a_index) throws Exception {
        throw new UnsupportedOperationException("Insert operation in sorted array is forbidden!");
    }

    @Override
    public void insert(List<T> a_array, int a_index) throws Exception {
        throw new UnsupportedOperationException("Insert operation in sorted array is forbidden!");
    }

    @Override
    public void changeNumber(T a_number, int a_index) throws IndexOutOfBoundsException {
        // it actually might work but would need an implementation
        throw new UnsupportedOperationException("Changing whatever number is forbidden in sorted array!");
    }

    // returns index of a_value or -1 if not found
    @Override
    public int find(T a_value, int a_range) throws Exception {
        checkSize("There is nothing to look for in empty array!");
        int index = bisectionFind(0, a_range - 1, a_value);
        if (m_array[index] != a_value) {
            return -1;
        } else {
            return index;
        }
    }

    public void removeIndex(int a_index) throws Exception {
        checkSize("Nothing can be removed from an empty array!");
        if (a_index >= m_size) {
            throw new ArrayIndexOutOfBoundsException("Element cannot be removed from given index exceeding the array size");
        }
        shiftLeft(a_index, m_size - 1, 1);
        m_size--;
    }

    public void removeValue(T a_value) throws Exception {
        checkSize("There is nothing to remove in empty array");
        List<Integer> indices = findAll(a_value, m_size);
        if (indices.size() != 0) {
            shiftLeft(indices.get(indices.size() - 1), m_size - 1, 1);
            m_size--;
        }

    }

    @Override
    public void removeAllValues(T a_value) throws Exception {
        checkSize("There is nothing to remove in empty array");
        List<Integer> indices = findAll(a_value, m_size);
        if (indices.size() != 0) {
            shiftLeft(indices.get(0), m_size - 1, indices.size());
            m_size -= indices.size();
        }
    }

    @Override
    public List<Integer> findAll(T a_value, int a_range) throws Exception {
        int i = find(a_value, a_range);
        List<Integer> result = EMPTY_LIST;
        if (i != -1) {
            result = new ArrayList<>(a_range);
            for (int j = i; m_array[j] == a_value && j < m_size; j++) {
                result.add(j);
            }
        }
        return result;
    }

    @Override
    public void fillRandom(double a_lowerBound, double a_upperBound) throws Exception {
        super.fillRandom(a_lowerBound, a_upperBound);
        sort();
    }

    public void sort() throws Exception {
        checkSize("You cannot sort an empty array");
        sortQuickSort(0, m_size - 1);
    }

    // just hides testClass() method of base class. Doesn't override it.
    public static boolean testClass() {
        try {
            System.out.println("---------------------------------------------\n" +
                    "Testing class O2_O5.MySortedArray for O3 task\n" +
                    "----------------------------------------------");
            MySortedArray<Integer> a = new MySortedArray<>(20, Integer.class);
            a.fillRandom(-2, 5);
            System.out.println(a.toString());
            int value = 2;
            int index = a.find(value, a.m_size);
            if (index != -1) {
                System.out.println("value " + value + " was found on index " + index + " and its value is " + a.get(index));
                List<Integer> res = a.findAll(value, a.m_size);
                System.out.println("value " + value + " was found " + res.size() + " times, on indices " + res.toString());
                System.out.println("Removing value under index " + index);
                a.removeIndex(index);
                System.out.println(a.toString());
                System.out.println("Removing last value equal to " + value);
                a.removeValue(value);
                System.out.println(a.toString());
                System.out.println("Removing all the values equal to " + value);
                a.removeAllValues(value);
                System.out.println(a.toString());
            }
            System.out.println("Removing all the duplicates");
            a.removeDuplicates();
            System.out.println(a);
            return true;
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage() + " happened");
            return false;
        }
    }

    // Overriden just for the sake of optimization. Base class method would do fine as well cause it's in-place
    @Override
    public void removeDuplicates() throws Exception {
        if (m_size < 2) {
            throw new Exception("Array is to small to look for any duplicates!");
        }
        T previousValue = (T) m_array[0];
        int newEndIndex = 0;
        for (int i = 1; i < m_size; i++) {
            Number temp = m_array[i];
            if (!temp.equals(previousValue)) {
                newEndIndex++;
                m_array[newEndIndex] = temp;
                previousValue = (T) temp;
            }
        }
        m_size = newEndIndex + 1;
    }
    // private

    private void sortQuickSort(int a_lowerBound, int a_upperBound) {
        if (a_lowerBound < a_upperBound) {
            int pivot = sortPartition(a_lowerBound, a_upperBound);
            sortQuickSort(a_lowerBound, pivot - 1);
            sortQuickSort(pivot + 1, a_upperBound);
        }
    }

    private int sortPartition(int a_lowerBound, int a_upperBound) {
        T pivotValue = (T) m_array[a_upperBound];
        int i = a_lowerBound;
        for (int j = a_lowerBound; j < a_upperBound; j++) {
            if (m_array[j].doubleValue() < pivotValue.doubleValue()) {
                T temp = (T) m_array[i];
                m_array[i] = m_array[j];
                m_array[j] = temp;
                i++;
            }
        }
        T temp = (T) m_array[i];
        m_array[i] = m_array[a_upperBound];
        m_array[a_upperBound] = temp;
        return i;
    }

//    // rotates the elements in range [a_begin, a_end] so that a_middle element becomes the first one
//    // note: actual implementation doesn't rotate anything. Algorithm just serves the idea of rotation.
//    // Owing to this fact, algorithm below is O(n)
//    private void rotate(int a_begin, int a_middle, int a_end) throws Exception {
//        checkSize("Cannot rotate an empty array");
//        int cycles = a_end - a_begin + 1;
//        int step = a_middle - a_begin;
//        int nextIndex = a_middle;
//        if (nextIndex < a_begin) {
//            int excess = nextIndex - a_begin;
//            nextIndex = a_end + excess;
//        }
//        Number valueToMove = m_array[a_middle];
//        for (int i = 0; i < cycles; i++) {
//            Number temp = m_array[nextIndex];
//            m_array[nextIndex] = valueToMove;
//            valueToMove = temp;
//            nextIndex -= step;
//            if (nextIndex < a_begin) {
//                int excess = nextIndex - a_begin;
//                nextIndex = a_end + excess;
//            }
//        }
//    }
//
//    private void shiftRight(int a_begin, int a_end, int a_positions) {
//        int i = a_end;
//        for (; i >= a_begin + a_positions; i--) {
//            m_array[i] = m_array[i - a_positions];
//        }
//        for (; i >= a_begin; i--) {
//            m_array[i] = null;
//        }
//        m_size -= a_positions;
//        if (m_size < 0) {
//            m_size = 0;
//        }
//    }
//
//    private void shiftLeft(int a_begin, int a_end, int a_positions) {
//        int i = a_begin;
//        for (i = a_begin; i <= a_end - a_positions; i++) {
//            m_array[i] = m_array[i + a_positions];
//        }
//        for (; i <= a_end; i++) {
//            m_array[i] = null;
//        }
//        m_size -= a_positions;
//        if (m_size < 0) {
//            m_size = 0;
//        }
//    }

    // returns the smallest index of equal number to a_value or returns very first index of bigger number than a_value
    private int bisectionFind(int a_begin, int a_end, T a_value) {
        if (a_begin == a_end) {
            return a_begin;
        }
        int middle = (a_end + a_begin) / 2;
        double pivotValue = m_array[middle].doubleValue();
        if (pivotValue < a_value.doubleValue()) {
            return bisectionFind(middle + 1, a_end, a_value);
        } else if (pivotValue > a_value.doubleValue()) {
            return bisectionFind(a_begin, middle, a_value);
        } else { // If equal beginning of possible repetitions needs to be find
            for (int i = middle; i >= 0; i--) {
                if (m_array[i].doubleValue() < a_value.doubleValue()) {
                    return ++i;
                }
            }
            return 0;
        }
    }
}
