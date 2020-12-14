package Int1;

public class MyArray{
    // MEMBERS
    private Function m_function;
    private double m_begin;
    private double m_end;
    private int m_elementsNumber;
    private double m_step;
    // PUBLIC
    public MyArray(Function a_function, double a_begin, double a_end, int a_elementsNumber) throws NullPointerException, IllegalArgumentException{
        if(a_function == null){
            throw new NullPointerException("Given function cannot be null!");
        } else if (a_begin > a_end || a_elementsNumber < 0){
            throw new IllegalArgumentException("Given arguments are wrong!\n\tbegin = " + a_begin
                                                + "\n\tend = " + a_end + "\n\tNumber of elements = " + a_elementsNumber);
        }
        m_function = a_function;
        m_begin = a_begin;
        m_end = a_end;
        m_step = (a_end - a_begin) / a_elementsNumber;
        m_elementsNumber = a_elementsNumber;
    }

    // prints
    @Override
    public String toString(){
        return toString(2);
    }
    public String toString(int a_precision){
        StringBuilder output = new StringBuilder("Formula f(x) = " + m_function.formula() + "\n");
        output.append(String.format("\t%" + a_precision + "sx%" + (a_precision) + "s\t f(x)\n",
                " ", " "));
        double x = m_begin;
        for(int i = 0; i < m_elementsNumber; i++){
            output.append(String.format("\t%." + a_precision + "f \t %." + a_precision + "f\n", x, m_function.f(x)));
//            output.append(String.format("\t" + x + "\t" + m_function.f(x) + "\n"));
            x += m_step;
        }
        return output.toString();
    }

    //test
    public static void testClass(){
        MyArray k = new MyArray(new FunctionClass(0.1), 0, 10, 100);
        System.out.println(k);
    }
}
