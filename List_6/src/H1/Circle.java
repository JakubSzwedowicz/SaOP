package H1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

public class Circle extends Figure{
    // members
    private static final long serialVersionUID = 4L;
    private double m_radius;
    // public
    public Circle(double a_radius){
        if(a_radius < 0){
            m_radius = 0;
        } else{
            m_radius = a_radius;
        }
        calculateEverything();
    }

    public String toString(){
        return "Radius = " + m_radius + " [mm]";
    }

    // private
    @Override
    protected void calculateEverything(){
        m_perimeter = 2 * Math.PI * m_radius;
        m_area = 2 * Math.PI * Math.pow(m_radius, 2);
    }

    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException {
        ois.defaultReadObject();
        validateData();
        calculateEverything();
    }

    @Serial
    protected void validateData() throws IllegalArgumentException{
        if(m_radius <= 0){
            throw new IllegalArgumentException("There cannot be a circle with 0 or negative radius!");
        }
    }
}
