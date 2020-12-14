package H3;

import java.util.ArrayList;
import java.util.List;

public class Buyer {
    // members
    private boolean m_naturalPerson; // [Pol]: Osoba fizyczna
    private String m_name;
    private String m_address;
    private String m_TIN;   // Taxpayer identification number
    private List<Invoice> m_invoices;

    // public
    Buyer(String a_name, String a_address) throws IllegalArgumentException {
        if (a_name == null || a_name.equals("") || a_address == null || a_address.equals("")) {
            throw new IllegalArgumentException("Illegal arguments given! name(" + a_name + "), adress(" + a_address + ")");
        }
        m_naturalPerson = true;
        m_name = a_name;
        m_address = a_address;
        m_invoices = new ArrayList<>(10);
    }

    Buyer(String a_name, String a_address, String a_TIN) throws IllegalArgumentException {
        if (a_name == null || a_name.equals("") || a_address == null || a_address.equals("") || a_TIN == null || a_TIN.equals("")) {
            throw new IllegalArgumentException("Illegal arguments given! name(" + a_name + "), adress(" + a_address + ")");
        }
        m_naturalPerson = false;
        m_name = a_name;
        m_address = a_address;
        m_TIN = a_TIN;
        m_invoices = new ArrayList<>(10);
    }

    // getters

    public List<Invoice> getInvoices() {
        return m_invoices;
    }

    public void linkInvoice(Invoice.LinkToInvoice a_link) {
        if(a_link == null){
            System.out.println("This method can only be called by invoice!");
            return;
        }
        if (findInvoice(a_link.m_invoice) == null) {
            m_invoices.add(a_link.m_invoice);
        }
    }

    public Invoice findInvoice(Invoice a_invoice) throws NullPointerException {
        if (a_invoice == null) {
            throw new NullPointerException("Cannot find null Invoice!");
        }
        for (Invoice e : m_invoices) {
            if (e == a_invoice) {
                return e;
            }
        }
        return null;
    }

    // prints
    public String toString() {
        StringBuffer output = new StringBuffer();
        if (!m_naturalPerson) {
            output.append("Not a natural person (a company): \n");
        } else {
            output.append("Natural person:\n");
        }
        output.append("\tName: " + m_name + ",\n\tAddress: " + m_address + "\n");
        if (!m_naturalPerson) {
            output.append("\tTIN: " + m_TIN + "\n");
        }
        return output.toString();
    }

    public String toStringInvoices() {
        StringBuffer output = new StringBuffer();
        for (Invoice e : m_invoices) {
            output.append(e);
        }
        return output.toString();
    }

    public void printInvoices() {
        System.out.println("Invoices of a buyer: " + this);
        for (int i = 0; i < m_invoices.size(); i++) {
            System.out.println("Invoice " + i + ":\n" + m_invoices.get(i));
        }
    }

    // private
}
