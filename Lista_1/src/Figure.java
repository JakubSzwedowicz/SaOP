import java.awt.geom.Point2D;

abstract class Figure{
    String name;
    String color;
    public Figure(String a_name, String a_color)
    {
        name = a_name;
        color = a_color;
    }
    public Figure(){};
    public String get_name(){
        return name;
    }
    public String get_color(){
        return color;
    }
    public abstract boolean check_point(Point2D a_point);
    public abstract boolean check_point(double a_x, double a_y);
}