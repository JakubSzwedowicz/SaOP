package H3;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Invoice {
    // members
    public class LinkToInvoice {
        public Invoice m_invoice;

        private LinkToInvoice(Invoice a) {
            m_invoice = a;
        }

        ;
    }

    ;
    private static long s_nextInvoiceNumber = 1;
    private long m_invoiceNumber;
    private Buyer m_buyer;
    private List<Ware> m_wares;
    private int m_size;
    private BigDecimal m_totalPayment;
    private int m_transactionType; // [Pol]: 1 - gotówka, 2 -  przelew, 3 - przedpłata, 4 - czek, 5 - karta kredytowa, 6 - zapłacono
    private LocalDate m_issueDate; // [Pol]: data wystawienia
    private LocalDate m_supplyDate; // [Pol]: data sprzedaży/dostarczenia produktu
    private LocalDate m_paymentDate; // [Pol]: data zapłaty

    // public
    Invoice(Buyer a_buyer, int a_transactionType, LocalDate a_issueDate,
            LocalDate a_supplyDate, LocalDate a_paymentDate, List<Ware> a_newWare) throws NullPointerException, IllegalArgumentException {
        if (a_buyer == null || a_issueDate == null || a_transactionType < 0 || a_transactionType > 5 || a_newWare == null) {
            throw new NullPointerException("There must be a buyer (" + a_buyer + ") on the Invoice,\nIssue date ("
                    + a_issueDate + ") cannot be null, \nTransaction type (" + a_transactionType
                    + ") has to be in range [0, 5], \nNew ware (" + a_newWare + ") cannot be null!");
        }
        if (!validateDates(a_issueDate, a_supplyDate, a_paymentDate)) {
            throw new IllegalArgumentException("Given dates are wrong!\n\tIssue date: " + a_issueDate + "\n\tSupply date: "
                    + a_supplyDate + "\n\tPayment date: " + a_paymentDate);
        }
        constructInvoice(a_buyer, a_transactionType, a_issueDate, a_supplyDate, a_paymentDate);
        addWare(a_newWare);
        calculateTotalPayment();
        linkInvoice();
    }

    Invoice(Buyer a_buyer, int a_transactionType, LocalDate a_issueDate,
            LocalDate a_supplyDate, LocalDate a_paymentDate, Ware a_newWare) throws NullPointerException, IllegalArgumentException {
        if (a_buyer == null || a_issueDate == null || a_transactionType < 0 || a_transactionType > 5 || a_newWare == null) {
            throw new NullPointerException("There must be a buyer (" + a_buyer + ") on the Invoice,\nIssue date ("
                    + a_issueDate + ") cannot be null, \nTransaction type (" + a_transactionType
                    + ") has to be in range [0, 5], \nNew ware (" + a_newWare + ") cannot be null!");
        }
        if (!validateDates(a_issueDate, a_supplyDate, a_paymentDate)) {
            throw new IllegalArgumentException("Given dates are wrong!\n\tIssue date: " + a_issueDate + "\n\tSupply date: "
                    + a_supplyDate + "\n\tPayment date: " + a_paymentDate);
        }
        constructInvoice(a_buyer, a_transactionType, a_issueDate, a_supplyDate, a_paymentDate);
        addWare(a_newWare);
        calculateTotalPayment();
        linkInvoice();
    }

    private void constructInvoice(Buyer a_buyer, int a_transactionType, LocalDate a_issueDate,
                                  LocalDate a_supplyDate, LocalDate a_paymentDate) {
        m_transactionType = a_transactionType;
        m_buyer = a_buyer;
        m_issueDate = a_issueDate;
        m_supplyDate = a_supplyDate;
        m_paymentDate = a_paymentDate;
        m_invoiceNumber = s_nextInvoiceNumber++;
    }

    public void addWare(Ware a_newWare) throws NullPointerException {
        if (a_newWare == null) {
            throw new NullPointerException("Ware cannot be null!");
        } else if (m_wares == null) {
            m_totalPayment = BigDecimal.ZERO;
            m_wares = new ArrayList<>(5);
        }
        m_wares.add(a_newWare);
        m_size++;
        m_totalPayment = m_totalPayment.add(a_newWare.getTotalPrice());
    }

    public void addWare(List<Ware> a_newWares) throws NullPointerException {
        if (a_newWares == null) {
            throw new NullPointerException("Wares cannot be null!");
        } else if (m_wares == null) {
            m_wares = new ArrayList<>(a_newWares.size() + 5);
            m_totalPayment = BigDecimal.ZERO;
        }
        m_wares.addAll(a_newWares);
        m_size += a_newWares.size();
        addTotalPayment(a_newWares);
    }

    public Buyer getBuyer() {
        return m_buyer;
    }

    public static boolean testClass() {
        return Test.testClass();
    }

    // print
    public String printWares() {
        StringBuffer output = new StringBuffer();
        for (int i = 0; i < m_size; i++) {
            output.append(i + " " + m_wares.get(i) + "\n");
        }
        return output.toString();
    }

    public String toString() {
        StringBuffer output = new StringBuffer("Invoice number " + m_invoiceNumber + ", \tIssue date: " + m_issueDate + "\n\n");
        output.append("Supply date: " + m_supplyDate + ", Payment day: " + m_paymentDate + "\n");
        output.append("Buyer: \n " + m_buyer + "\n\n");
        output.append("Wares of transaction: \n" + printWares() + "\n");
        output.append("Total amount to pay: " + m_totalPayment + "\n");
        return output.toString();
    }

    public void printInvoicesOfBuyer() {
        m_buyer.printInvoices();
    }

    // private

    private void linkInvoice() {
        m_buyer.linkInvoice(new LinkToInvoice(this));
    }

    private void addTotalPayment(List<Ware> a_wares) {
        for (Ware element : a_wares) {
            m_totalPayment = m_totalPayment.add(element.getTotalPrice());
        }
    }

    private void calculateTotalPayment() {
        m_totalPayment = BigDecimal.ZERO;
        for (Ware element : m_wares) {
            m_totalPayment = m_totalPayment.add(element.getTotalPrice());
        }
    }

    // According to the polish rules!
    private boolean validateDates(LocalDate a_issueDate, LocalDate a_supplyDate, LocalDate a_paymentDate) {
        if (a_supplyDate == null) { // if issue and supply daters are the same then it is possible to use only issue date
            a_supplyDate = a_issueDate;
        }
        long days = a_issueDate.until(a_supplyDate, ChronoUnit.DAYS);
        if (a_issueDate.isBefore(a_supplyDate)) {
            if (days > 30) {
                return false;
            } else {
                return true;
            }
        } else {
            LocalDate upperDate = a_supplyDate.plusMonths(1).withDayOfMonth(16); // needs to be done this way in case of the Months: 12 + 1 = 1
            return a_issueDate.isBefore(upperDate); // last possible date is BEFORE 16th of the next month
        }
    }

    private static class Test {
        private static boolean testClass() {
            try {
                System.out.println("Running all the tests for the class Invoice!\n\n");
                System.out.println("Creating first invoice for first buyer:");
                Invoice invoice1 = createInvoice1();
                System.out.println(invoice1 + "\n\n");

                System.out.println("Creating the second invoice for the second buyer:");
                Invoice invoice2 = createInvoice2();
                System.out.println(invoice2 + "\n\n");

                System.out.println("Adding the third invoice to the first buyer:");
                Invoice invoice3 = createInvoice3(invoice1.getBuyer());
                System.out.println(invoice3 + "\n\n");

                System.out.println("Printing all the invoices of the first buyer:");
                invoice3.printInvoicesOfBuyer();
                return true;
            } catch (Exception e) {
                System.out.println("Exception caught " + e.getMessage());
                return false;
            }
        }

        private static Invoice createInvoice1() {
            Buyer buyer = new Buyer("Trump", "Ulica 12/23");
            Ware ware = new Ware("Tables", 2, new BigDecimal("100.99"));
            Ware ware2 = new Ware("Chairs", 8, new BigDecimal("37.99"));
            Ware ware3 = new Ware("Plates", 8, new BigDecimal("20.99"));
            Invoice invoice = new Invoice(buyer, 1, LocalDate.of(2020, 12, 10),
                    LocalDate.of(2020, 11, 10), LocalDate.of(2020, 12, 30), ware);
            invoice.addWare(ware2);
            invoice.addWare(ware3);
            List<Ware> wares = new ArrayList<>(3);
            wares.add(new Ware("Vase", 4, new BigDecimal("99.99")));
            wares.add(new Ware("Forks", 16, new BigDecimal("12.99")));
            wares.add(new Ware("Spoons", 16, new BigDecimal("10.99")));
            invoice.addWare(wares);
            return invoice;
        }

        private static Invoice createInvoice2() {
            Buyer buyer = new Buyer("Putin", "Ulica 11/23");
            List<Ware> wares = new ArrayList<>(3);
            wares.add(new Ware("CPU", 120, new BigDecimal("1000")));
            wares.add(new Ware("GPU", 900, new BigDecimal("3500")));
            wares.add(new Ware("SSD", 12000, new BigDecimal("500.99")));
            Invoice invoice = new Invoice(buyer, 1, LocalDate.of(2020, 11, 10),
                    LocalDate.of(2020, 12, 10), LocalDate.of(2020, 12, 30), wares);
            return invoice;
        }

        private static Invoice createInvoice3(Buyer a_buyer) {
            List<Ware> wares = new ArrayList<>(3);
            wares.add(new Ware("TV", 10, new BigDecimal("5000.99")));
            wares.add(new Ware("Car", 6, new BigDecimal("12000.99")));
            wares.add(new Ware("House", 3, new BigDecimal("7800000")));
            Invoice invoice = new Invoice(a_buyer, 1, LocalDate.of(2010, 12, 10),
                    LocalDate.of(2011, 1, 5), LocalDate.of(2020, 12, 30), wares);
            return invoice;
        }

    }
}

