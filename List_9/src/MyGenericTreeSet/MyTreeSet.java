package MyGenericTreeSet;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTreeSet<T> {
    // MEMBERS
    private TreeSet<T> m_treeSet;

    // PUBLIC
    // Constructors
    public MyTreeSet(Collection<? extends T> a_collection) {
        m_treeSet = new TreeSet<>(a_collection);
    }

    public MyTreeSet(SortedSet<T> a_collection) {
        m_treeSet = new TreeSet<>(a_collection);
    }

    public MyTreeSet(Collection<? extends T> a_collection, Comparator<? super T> a_comparator) {
        m_treeSet = new TreeSet<>(a_comparator);
        m_treeSet.addAll(a_collection);
    }

    public MyTreeSet(Comparator<? super T> a_comparator) {
        m_treeSet = new TreeSet<>(a_comparator);
    }

    // comparator doesn't have to be necessarly passed (T might implement it), but as a safety meassure
    // it has to be explicitly passed
    public MyTreeSet(T[] a_array, Comparator<? super T> a_comparator) {
        m_treeSet = new TreeSet<T>(a_comparator);
        m_treeSet.addAll(Arrays.asList(a_array));
    }

    // Java being stupid
    public MyTreeSet(double[] a_array) {
        m_treeSet = (TreeSet<T>) new TreeSet<>(Arrays.asList(Arrays.stream(a_array).boxed().toArray(Double[]::new)));
    }

    // Utilities
    // slower version according to JUnit
    public MyTreeSet<T> addSet2(MyTreeSet<T> a_set) {
        Collection<T> hashSet = new HashSet<>(m_treeSet);
        hashSet.addAll(a_set.m_treeSet);
        return new MyTreeSet<>(hashSet, a_set.m_treeSet.comparator());
    }

    public MyTreeSet<T> addSet(MyTreeSet<T> a_set) {
        Collection<T> hashSet = Stream
                .concat(m_treeSet.stream(), a_set.m_treeSet.stream())
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(a_set.m_treeSet.comparator())
                ));
        return new MyTreeSet<>(hashSet, a_set.m_treeSet.comparator());
    }

    // slower version according to JUnit
    public MyTreeSet<T> subtractSet2(MyTreeSet<T> a_set) {
        Collection<T> hashSet = new HashSet<>(m_treeSet);
        hashSet.removeAll(a_set.m_treeSet);
        return new MyTreeSet<>(hashSet, a_set.m_treeSet.comparator());
    }

    public MyTreeSet<T> subtractSet(MyTreeSet<T> a_set) {
        Collection<T> hashSet = m_treeSet
                .stream()
                .filter(e -> !a_set.m_treeSet.contains(e))
                .collect(Collectors.toCollection(HashSet::new));
        return new MyTreeSet<>(hashSet, a_set.m_treeSet.comparator());
    }

    // slower version according to JUnit
    public MyTreeSet<T> intersectSet2(MyTreeSet<T> a_set) {
        Collection<T> hashSet = new HashSet<>(m_treeSet);
        hashSet.retainAll(a_set.m_treeSet);
        return new MyTreeSet<>(hashSet, a_set.m_treeSet.comparator());
    }

    public MyTreeSet<T> intersectSet(MyTreeSet<T> a_set) {
        Collection<T> intersectionSet = m_treeSet
                .stream()
                .filter(e -> a_set.m_treeSet.contains(e))
                .collect(Collectors.toCollection(HashSet::new));
        return new MyTreeSet<>(intersectionSet, a_set.m_treeSet.comparator());
    }

    public MyTreeSet<T> symmetricDifference(MyTreeSet<T> a_set) {
        Collection<T> intersectionSet = m_treeSet
                .stream()
                .filter(e -> a_set.m_treeSet.contains(e))
                .collect(Collectors.toCollection(HashSet::new));
        Collection<T> symmetricDiffSet = Stream
                .concat(m_treeSet.stream(), a_set.m_treeSet.stream())
                .filter(e -> !(intersectionSet.contains(e)))
                .collect(Collectors.toCollection(HashSet::new));
        return new MyTreeSet<>(symmetricDiffSet, a_set.m_treeSet.comparator());
    }

    public boolean addElement(T a_element) {
        if (a_element == null) {
            return false;
        } else {
            // If element isn't in itself Comparable there mush be custom comparator present
            if (!(a_element instanceof Comparable) && m_treeSet.comparator() == null) {
                return false;
            }
            return m_treeSet.add(a_element);
        }
    }

    // getter
    public T getLastElement() {
        return m_treeSet.last();
    }

    // print
    public String toString() {
        return m_treeSet.toString();
    }

    // test
    public static void testClass() {
        try {
            MyTreeSet<Double> firstSet = new MyTreeSet<Double>(new double[]{1, 2, 3, 4, 4.5, 5, 6, 7});
            MyTreeSet<Double> secondSet = new MyTreeSet<Double>(new double[]{-3, -2, -1, 0, 4, 5});
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
                            new Point2D.Double(0, 0)));
            MyTreeSet<Point2D> pointsSet = new MyTreeSet<Point2D>(
                    points,
                    (Point2D p1, Point2D p2) -> {
                        double diff = p1.distance(0, 0) - p2.distance(0, 0);
                        if (diff > 0) {
                            return 1;
                        } else {
                            return diff == 0 ? 0 : -1;
                        }
                    });

            System.out.println(pointsSet);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public boolean equals(Object object) {
        return object instanceof MyTreeSet ? ((MyTreeSet<?>) object).m_treeSet.equals(m_treeSet) : false;
    }
    // PRIVATE
}
