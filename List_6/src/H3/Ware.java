package H3;

import java.math.BigDecimal;

public class Ware {
    // members
    private String m_name;
    private int m_amount;
    private BigDecimal m_price;
    private BigDecimal m_totalPrice;

    // public
    Ware(String a_name, int a_amount, BigDecimal a_price) throws IllegalArgumentException{
        if(a_name == null || a_name.equals("") || a_amount <= 0 || a_price.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Wrong ware details! name(" + a_name + "), amount("
                    + a_amount + "), price(" + a_price + ")");
        }
        m_name = a_name;
        m_amount = a_amount;
        m_price = a_price;
        calculateTotalPrice();
    }

    public String getName() {
        return m_name;
    }

    public int getAmount() {
        return m_amount;
    }

    public BigDecimal getPrice() {
        return m_price;
    }

    public BigDecimal getTotalPrice(){
        return m_totalPrice;
    }

    public void changeAmount(int a_amount){
        m_amount = a_amount;
        if(m_amount < 0){
            m_amount = a_amount;
        }
        calculateTotalPrice();
    }

    public void incrDecrAmount(int a_amount){
        m_amount += a_amount;
        if(m_amount < 0){
            m_amount = 0;
        }
        calculateTotalPrice();
    }

    // prints
    public String toString() {
        return "Ware: " + m_name + " | amount = " + m_amount + ", price = " + m_price + ", total price = " + m_totalPrice;
    }

    // private
    private void calculateTotalPrice(){
        m_totalPrice = new BigDecimal(m_amount);
        m_totalPrice = m_totalPrice.multiply(m_price);
    }
}
