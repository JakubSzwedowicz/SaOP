import java.awt.geom.Point2D;

class Square extends Figure{
    double side;
    Point2D center = new Point2D.Double();
    Square(String a_color, double a_center_x, double a_center_y, double a_side){
    this(a_color, new Point2D.Double(a_center_x, a_center_y), a_side);
    }
    Square(String a_color, Point2D a_center, double a_side){
        super("square", a_color);
        side = a_side;
        center.setLocation(a_center);
    }
    public boolean check_point(Point2D a_point){
        return check_point(a_point.getX(), a_point.getY());
    }
    public boolean check_point(double a_x, double a_y)
    {
        if(Math.abs(a_x - center.getX()) <= side/2)
            if(Math.abs(a_y - center.getY()) <= side/2)
                return true;
        return false;
    }
}