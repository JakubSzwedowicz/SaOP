package MyGenericArrayListSet;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyArrayListSet<T> {
    // MEMBERS
    private List<T> m_list;

    // PUBLIC
    // Constructors
    // this constructor can be used when sure that passed Collection has no repetitions
    public MyArrayListSet(Set<T> a_set) {
        m_list = new ArrayList<>(a_set);
    }

    // It has te be ensured that no repeated element gets into the array, distinct() it used for that matter
    public MyArrayListSet(Collection<? extends T> a_collection) {
        m_list = new ArrayList<>(a_collection
                .stream()
                .distinct()
                .collect(Collectors.toList()));
    }

    public MyArrayListSet(int a_capacity) throws IllegalArgumentException {
        if (a_capacity < 0) {
            throw new IllegalArgumentException("Capacity of ArrayList cannot be negative!");
        }
        m_list = new ArrayList<>(a_capacity);
    }

    public MyArrayListSet() {
        m_list = new ArrayList<>();
    }

    // It has te be ensured that no repeated element gets into the array, distinct() it used for that matter
    public MyArrayListSet(T[] a_array) {
        m_list = new ArrayList<>(Arrays
                .stream(a_array)
                .distinct()
                .collect(Collectors.toList()));
    }

    // Java being stupid
    public MyArrayListSet(double[] a_array) {
        m_list = (List<T>) new ArrayList<Double>((Arrays
                .stream(a_array)
                .distinct()
                .boxed()
                .collect(Collectors.toList())));
    }

    // Utilities
    // slower version according to JUnit
    public MyArrayListSet<T> addSet2(MyArrayListSet<T> a_set) {
        return new MyArrayListSet<T>(Stream
                .concat(m_list.stream(), a_set.m_list.stream())
                .distinct()
                .collect(Collectors.toList()));
    }

    public MyArrayListSet<T> addSet(MyArrayListSet<T> a_set) {
        Set<T> addedSets = Stream
                .concat(m_list.stream(), a_set.m_list.stream())
                .collect(Collectors.toCollection(HashSet::new));
        return new MyArrayListSet<T>(addedSets);
    }

    // slower version according to JUnit
    public MyArrayListSet<T> subtractSet2(MyArrayListSet<T> a_set) {
        Collection<T> hashSet = new HashSet<>(m_list);
        hashSet.removeAll(a_set.m_list);
        return new MyArrayListSet<>(hashSet);
    }

    public MyArrayListSet<T> subtractSet(MyArrayListSet<T> a_set) {
        Set<T> subtractionSet = m_list
                .stream()
                .filter(e -> !a_set.m_list.contains(e))
                .collect(Collectors.toSet());
        return new MyArrayListSet<>(subtractionSet);
    }

    // slower version according to JUnit
    public MyArrayListSet<T> intersectSet2(MyArrayListSet<T> a_set) {
        List<T> newList = new ArrayList<>(m_list);
        newList.retainAll(a_set.m_list);
        return new MyArrayListSet<>(newList);
    }

    public MyArrayListSet<T> intersectSet(MyArrayListSet<T> a_set) {
        Set<T> intersectionSet = m_list
                .stream()
                .filter(e -> a_set.m_list.contains(e))
                .collect(Collectors.toSet());
        return new MyArrayListSet<>(intersectionSet);
    }

    public MyArrayListSet<T> symmetricDifference(MyArrayListSet<T> a_set) {
        List<T> intersectionSet = m_list
                .stream()
                .filter(e -> a_set.m_list.contains(e))
                .collect(Collectors.toList());
        Set<T> symmetricDiffSet = Stream
                .concat(m_list.stream(), a_set.m_list.stream())
                .filter(e -> !(intersectionSet.contains(e)))
                .collect(Collectors.toSet());
        return new MyArrayListSet<>(symmetricDiffSet);
    }

    public boolean addElement(T a_element) {
        if (a_element == null || m_list.contains(a_element)) {
            return false;
        } else {
            return m_list.add(a_element);
        }
    }

    // getter
    public T getLastElement() {
        return m_list.get(m_list.size() - 1);
    }

    // print
    public String toString() {
        return m_list.toString();
    }

    // test
    public static void testClass() {
        try {
            MyArrayListSet<Double> firstSet = new MyArrayListSet<Double>(new double[]{1, 2, 3, 4, 4.5, 5, 6, 7});
            MyArrayListSet<Double> secondSet = new MyArrayListSet<Double>(new double[]{-3, -2, -1, 0, 4, 5});
            System.out.println("First set:\n\t" + firstSet
                    + "\nSecond set:\n\t" + secondSet + "\n");
            System.out.println("SUM:\n\t" + firstSet.addSet(secondSet));
            System.out.println("SUBTRACTION:\n\t" + firstSet.subtractSet(secondSet));
            System.out.println("INTERSECTION:\n\t" + firstSet.intersectSet(secondSet));
            System.out.println("SYMMETRIC DIFFERENCE:\n\t" + firstSet.symmetricDifference(secondSet));

            System.out.println("===========================================================");
            System.out.println("Second part of testing:");
            List<Point2D> points = new ArrayList<>(Arrays
                    .asList(
                            new Point2D.Double(0, 0),
                            new Point2D.Double(1, 1),
                            new Point2D.Double(-1, -1),
                            new Point2D.Double(0, 0)
                    ));
            System.out.println(points);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // This is a tricky one
    // sorting O(nlog(n)), creating TreeSet O(nlog(n)), etc. can't be used here because elements
    // don't have to be Comparable.
    // Comparing Lists O(n) also can't be used because order in Sets doesn't matter whereas in Lists it does.
    // What we are left with is that Sets are distinct and if they have equal sizes, then it is safe to use contains.all()
    public boolean equals(Object object) {
        if (!(object instanceof MyArrayListSet)) {
            return false;
        }
        MyArrayListSet set = (MyArrayListSet) object;
        if (set.m_list.size() != m_list.size()) {
            return false;
        }
        return m_list.containsAll(set.m_list);
    }

    // PRIVATE
}
