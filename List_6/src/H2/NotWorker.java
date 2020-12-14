package H2;

public class NotWorker extends Employee{
    // members
    private static double s_basicIncome;
    private double m_bonusPercentage;

    // public
    NotWorker(String a_surname, double a_contract, double a_bonus){
        super(a_surname, a_contract);
        m_bonusPercentage = a_bonus;
        calculatePayment();
    }

    public String toString(){
        StringBuffer output = new StringBuffer("Not worker: \n" + super.toString());
        output.append("\n\tbasic income: " + s_basicIncome + "\n\tbonus percentage: " + m_bonusPercentage);
        return output.toString();
    }
    public static void setBasicIncome(double a_basicIncome) throws IllegalArgumentException{
        if(a_basicIncome < 0){
            throw new IllegalArgumentException("given basic income cannot be negative!");
        }
        s_basicIncome = a_basicIncome;
    }

    // private

    @Override
    protected void calculatePayment() {
        m_payment = s_basicIncome * m_contract + m_bonusPercentage * s_basicIncome;
    }
}
