package H2;

import java.math.BigDecimal;

public abstract class Employee {
    // members
    private String m_surname;
    protected double m_contract;

    protected double m_payment;

    // public
    Employee(String a_surname, double a_contract) {
        m_surname = a_surname;
        m_contract = a_contract;
        calculatePayment();
    }

    public final double getPayment() {
        return m_payment;
    }

    public final String getSurname() {
        return m_surname;
    }

    public final double getContract() {
        return m_contract;
    }

    public boolean isNotWorker(){
        return getClass() == NotWorker.class ? true : false;
    };
    public String toString(){
        return "Name: " + m_surname + "\nContract: " + m_contract + "\nPayment: " + m_payment;
    }
    // private
    protected abstract void calculatePayment();
}
