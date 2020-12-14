package H1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Container classes should use hashtable (or at least follow its rules) when Serializable
// as opposed to a large hash table data structure
public class CompoundFigure implements Serializable {
    // members
    private static final long serialVersionUID = 10L;
    private List<Figure> m_figures;
    private double m_cost;
    private double m_material;

    // public
    public CompoundFigure(List<Figure> a_figures) {
        m_figures = new ArrayList<>(a_figures);
        calculateEverything();
    }

    public List<Figure> getFigures() {
        return m_figures;
    }

    public double getCost() {
        return m_cost;
    }

    public double getMaterial() {
        return m_material;
    }

    public void addFigure(Figure a_figure) {
        if (a_figure != null) {
            m_figures.add(a_figure);
            m_cost += a_figure.getCost();
            m_material += a_figure.getArea();
        }
    }

    public static void testClass(){
        testSerialization();
    }
    // Serialization test
    private static void testSerialization() {

        // Serialization
        List<Figure> figures = new ArrayList<Figure>(3);
        figures.add(new Circle(2));
        figures.add(new Square(5));
        figures.add(new Rectangle(1, 4));

        CompoundFigure test = new CompoundFigure(figures);
        String fileName = "test.ser";
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            System.out.println("Object when serialized :");
            System.out.println("cost = " + test.getCost() + " and material = " + test.getMaterial());
            System.out.println("Figures: \n" + test.getFigures());
            System.out.println();

            oos.writeObject(test);
            oos.close();
            fos.close();

        } catch (IOException e) {
            System.out.println("IOException is caught " + e.getMessage());
        }

        // Deserialization
        CompoundFigure test2 = null;

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            test2 = (CompoundFigure) ois.readObject();

            ois.close();
            fis.close();

            System.out.println("Object deserialized :");
            System.out.println("cost = " + test2.getCost() + " and material = " + test2.getMaterial());
            System.out.println("Figures: \n" + test2.getFigures());

        } catch (IOException e) {
            System.out.println("IOException is caught " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("CLassNotFoundException is caught " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException is caught " + e.getMessage());
        }
    }

    // private
    private void calculateEverything() {
        m_cost = 0;
        m_material = 0;
        for (Figure figure : m_figures) {
            m_cost += figure.getCost();
            m_material += figure.getArea();
        }
    }

    // Serialization
    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();    // toy implementation actually needless
        validateData();
//        calculateEverything();    // not needed because members are not transient
    }

    @Serial
    private void validateData() throws IllegalArgumentException {
        if (m_figures == null) {
            throw new IllegalArgumentException("List<Figures> cannot be null!");
        }
    }
}
