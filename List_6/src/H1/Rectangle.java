package H1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;

public class Rectangle extends Figure {
    // members
    private static final long serialVersionUID = 2L;
    protected double m_a, m_b;

    // public
    public Rectangle(double a_a, double a_b){
        if(a_a < 0 || a_b < 0){
            a_a = 0;
            a_b = 0;
        } else {
            m_a = a_a;
            m_b = a_b;
        }
        calculateEverything();
    }

    public String toString(){
        return "Sides = " + m_a + "x" + m_b + " [mm]";
    }

    // protected
    @Override
    protected void calculateEverything(){
        m_area = m_a * m_b;
        m_perimeter = 2 * m_a + 2 * m_b;
    }

    // private
    @Serial
    private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException{
        ois.defaultReadObject();
        validateData();
        calculateEverything();
    }

    @Serial
    protected void validateData() throws IllegalArgumentException{
        if(m_b <= 0 || m_a <= 0){
            throw new IllegalArgumentException("Sides of a rectangle/square cannot be 0 or smaller!");
        }
    }
}
