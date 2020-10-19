import java.awt.geom.Point2D;

class Ring extends Figure{
    double outer_radius, inner_radius;
    Point2D center = new Point2D.Double();
    Ring(String a_color, double a_center_x, double a_center_y, double a_o_r, double a_i_r){
        this(a_color, new Point2D.Double(a_center_x, a_center_y), a_o_r, a_i_r);
    }
        Ring(String a_color, Point2D a_center, double a_i_r, double a_o_r){
        super("Ring", a_color);
        outer_radius = a_o_r;
        inner_radius = a_i_r;
        center.setLocation(a_center);
    }
    public boolean check_point(Point2D a_point){
        return check_point(a_point.getX(), a_point.getY());
    }
    public boolean check_point(double a_x, double a_y)
    {
        double distance = center.distance(a_x, a_y);
        if((outer_radius >=  distance) && (distance >= inner_radius))
            return true;
        return false;
    }
}