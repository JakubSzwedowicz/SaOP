import java.awt.geom.Point2D;

class Triangle extends Figure {
    Point2D[] vertexes;

    // Barycentric coordinates
    double area, s, t, diff;

    Triangle(String a_color, double a_vertex_x, double a_vertex_y, double a_height) {
        this(a_color, a_vertex_x, a_vertex_y,
                a_vertex_x + a_height, a_vertex_y,
                a_vertex_x, a_vertex_y + a_height);
    }

    Triangle(String a_color, Point2D a_vertex, double a_height) {
        this(a_color, a_vertex.getX(), a_vertex.getY(),
                a_vertex.getX() + a_height, a_vertex.getY(),
                a_vertex.getX(), a_vertex.getY() + a_height);
    }
    Triangle(String a_color, Point2D a_vertex1, Point2D a_vertex2, Point2D a_vertex3) {
        this(a_color, a_vertex1.getX(), a_vertex1.getY(),
                a_vertex2.getX(), a_vertex2.getY(),
                a_vertex3.getX(), a_vertex3.getY());
    }

    Triangle(String a_color, double a_point1_x, double a_point1_y,
             double a_point2_x, double a_point2_y,
             double a_point3_x, double a_point3_y) {
        super("Triangle", a_color);
        vertexes = new Point2D[3];
        vertexes[0] = new Point2D.Double(a_point1_x, a_point1_y);
        vertexes[1] = new Point2D.Double(a_point2_x, a_point2_y);
        vertexes[2] = new Point2D.Double(a_point3_x, a_point3_y);
    }

    private void compute_barycentric(double a_x, double a_y) {
        double point1_x = vertexes[0].getX(), point1_y = vertexes[0].getY();
        double point2_x = vertexes[1].getX(), point2_y = vertexes[1].getY();
        double point3_x = vertexes[2].getX(), point3_y = vertexes[2].getY();
        area = 0.5 * Math.abs((point2_x - point1_x) * (point3_y - point1_y) - (point2_y - point1_y) * (point3_x - point1_x));
        s = 1 / (2 * area) * (point1_y * point3_x - point1_x * point3_y + a_x * (point3_y - point1_y) + a_y * (point1_x - point3_x));
        t = 1 / (2 * area) * (point1_x * point2_y - point1_y * point2_x + a_x * (point1_y - point2_y) + a_y * (point2_x - point1_x));
        diff = 1 - s - t;
    }
    public boolean check_point(Point2D a_point) {
        return check_point(a_point.getX(), a_point.getY());
    }
    public boolean check_point(double a_x, double a_y) {
        compute_barycentric(a_x, a_y);
        return s >= 0 && t >= 0 && diff >= 0;
    }
}