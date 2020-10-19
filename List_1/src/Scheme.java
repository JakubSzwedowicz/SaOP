import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class Scheme {
    private String name;
    private int size;
    private List<Figure> figures;
    private Figure background;

    // Constructors
    Scheme(String a_name) {
        name = a_name;
        figures = new ArrayList<Figure>();
        background = new Square("Yellow", 0, 0, 2);
        size = 0;
    }

    Scheme() {
        this("Cartesian System");
    }

    // Methods
    public void add_figure(boolean a_background, String a_name, String a_color, double a_ceter_x, double a_center_y, double a_arg1) {
        add_figure(a_background, a_name, a_color, new Point2D.Double(a_ceter_x, a_center_y), a_arg1, 0);
    }

    public void add_figure(boolean a_background, String a_name, String a_color, Point2D a_point, double a_arg1) {
        add_figure(a_background, a_name, a_color, a_point, a_arg1, 0);
    }

    public void add_figure(boolean a_background, String a_name, String a_color, double a_ceter_x, double a_center_y, double a_arg1, double a_arg2) {
        add_figure(a_background, a_name, a_color, new Point2D.Double(a_ceter_x, a_center_y), a_arg1, a_arg2);
    }

    public void add_figure(boolean a_background, String a_name, String a_color, Point2D a_point, double a_arg1, double a_arg2) {
        switch (a_name) {
            case "Square" -> {
                if (a_background)
                    background = new Square(a_color, a_point, a_arg1);
                else
                    figures.add(new Square(a_color, a_point, a_arg1));
            }
            case "Circle" -> {
                if (a_background)
                    background = new Circle(a_color, a_point, a_arg1);
                else
                    figures.add(new Circle(a_color, a_point, a_arg1));
            }
            case "Ring" -> {
                if (a_background)
                    background = new Ring(a_color, a_point, a_arg1, a_arg2);
                else
                    figures.add(new Ring(a_color, a_point, a_arg1, a_arg2));
            }
            case "Triangle" -> {
                if (a_background)
                    background = new Triangle(a_color, a_point, a_arg1);
                else
                    figures.add(new Triangle(a_color, a_point, a_arg1));
            }
        }
    }

    public void print_figures() {
        for (Figure ptr : figures) {
            System.out.println(ptr.get_name());
        }
    }

    // Accessors
    public String get_name() {
        return name;
    }

    public List<Figure> check_point(Point2D a_point) {
        return check_point(a_point.getX(), a_point.getY());
    }

    public List<Figure> check_point(double a_x, double a_y) {
        List<Figure> res = new ArrayList<Figure>();
        for (Figure figure : figures) {
            if (figure.check_point(a_x, a_y))
                res.add(figure);
        }
        if (res.size() == 0 && background.check_point(a_x, a_y)) {
            res.add(background);
        }
        System.out.println("Found " + res.size() + " corresponding figures");
        return res;
    }
}



