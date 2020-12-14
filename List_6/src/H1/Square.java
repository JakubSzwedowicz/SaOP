package H1;

public class Square extends Rectangle {
    // members
    private static final long serialVersionUID = 3L;
    // public
    public Square(int a_side){
        super(a_side, a_side);
    }

    public String toString(){
        return "Side = " + m_a;
    }
}
