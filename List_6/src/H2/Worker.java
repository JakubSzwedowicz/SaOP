package H2;

public class Worker extends Employee {
    // members
    private static double s_limit;
    private double m_workingTime;
    private double m_hourlyRate;

    // public
    Worker(String a_surname, double a_contract, double a_workingTime, double a_hourlyRate) {
        super(a_surname, a_contract);
        m_workingTime = a_workingTime;
        m_hourlyRate = a_hourlyRate;
        calculatePayment();
    }

    public String toString(){
        StringBuffer output = new StringBuffer("Worker: \n" + super.toString());
        output.append("\n\tworking time: " + m_workingTime + "\n\tlimit: " + s_limit + "\n\thourly rate: " + m_hourlyRate);
        return output.toString();
    }

    public static void setLimit(double a_limit) throws IllegalArgumentException{
        if(a_limit < 0){
            throw new IllegalArgumentException("given limit cannot be negative!");
        }
        s_limit = a_limit;
    }
    // private
    @Override
    protected void calculatePayment() {
        m_payment = m_hourlyRate * (m_workingTime + 1.5 * calculateOvertime());
    }

    private double calculateOvertime() {
        double overtime = m_workingTime - s_limit;
        if (overtime > 0) {
            return overtime;
        } else {
            return 0;
        }
    }
}
