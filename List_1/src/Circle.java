import java.awt.*;
import java.awt.geom.Point2D;

class Circle extends Figure{
    double radius;
    Point2D center = new Point2D.Double();
    Circle(String a_color, double a_center_x, double a_center_y, double a_radius){
        this(a_color, new Point2D.Double(a_center_x, a_center_y), a_radius);
    }
        Circle(String a_color, Point2D a_center, double a_radius){
        super("Circle", a_color);
        radius = a_radius;
        center.setLocation(a_center);
    }
    public boolean check_point(Point2D a_point){
        return check_point(a_point.getX(), a_point.getY());
    };
    public boolean check_point(double a_x, double a_y)
    {
        if(Math.sqrt( Math.pow((a_x - center.getX()), 2) + Math.pow((a_y - center.getY()), 2) ) <= radius)
            return true;
        return false;
    }
}