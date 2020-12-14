package H1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;

public abstract class Figure implements Serializable {
    // members
    private static final long serialVersionUID = 1L;

    // needless for serialization data
    protected transient double m_perimeter;
    protected transient double m_area;

    // public
    public final double getArea() {
        return m_area;
    }

    ;

    public final double getPerimeter() {
        return m_perimeter;
    }

    public final double getCost() {
        return m_perimeter + m_area;
    }

    protected abstract void calculateEverything();

    @Serial
    protected abstract void validateData();
}
