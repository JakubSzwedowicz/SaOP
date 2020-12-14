package H2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

// Huge Design mistakes
// ad. 1. Everything should be sorted within the class all the time - would have worked much faster
public class EmployedStaff {
    // members
    private Employee[] m_staff;
    private int m_size;
    private static final int MAX_SIZE = 100;

    // public
    public static boolean testClass() {
        return Test.testClass();
    }

    EmployedStaff(int a_staffSize, double a_basicIncome, double a_limit) throws IllegalArgumentException {
        if (a_staffSize < 0 || a_staffSize > MAX_SIZE || a_basicIncome < 0 || a_limit < 0) {
            throw new IllegalArgumentException("Wrong arguments given to constructor");
        }
        m_size = a_staffSize;
        m_staff = new Employee[m_size];
        Worker.setLimit(a_limit);
        NotWorker.setBasicIncome(a_basicIncome);
    }

    // Functions
    public void loadStaff(String a_filename) throws FileNotFoundException {
        try {
            File file = new File(a_filename);
            Scanner fileInput = new Scanner(file);
            Employee[] temp = new Employee[MAX_SIZE];
            int newSize = 0;
            while (fileInput.hasNextLine()) {
                String line = fileInput.nextLine();
                if (line.charAt(0) == '#') {
                    continue;
                } else if (line.startsWith("[W]")) {
                    temp[newSize++] = workerFromFile(line);
                } else if (line.startsWith("[NW]")) {
                    temp[newSize++] = notWorkerFromFile(line);
                }
            }
            copyArray(temp, newSize);
        } catch (Exception e) {
            System.out.println("Exception caught " + e.getMessage());
        }
    }

    public void addNotWorker(String a_surname, double a_contract, double a_bonusPercentage) throws NullPointerException, IllegalArgumentException {
        if (a_surname == null || a_surname.equals("")) {
            throw new NullPointerException("Surname cannot be null or empty!");
        } else if (a_contract <= 0 || a_bonusPercentage < 0) {
            throw new IllegalArgumentException("Contract (" + a_contract + ") cannot be non positive or bonus percentage ("
                    + a_bonusPercentage + "), cannot be negative!");
        }
        Employee newNotWorker = new NotWorker(a_surname, a_contract, a_bonusPercentage);
        addEmployee(newNotWorker);
    }

    public void addWorker(String a_surname, double a_contract, double a_workingTime, double a_hourlyRate) throws NullPointerException, IllegalArgumentException {
        if (a_surname == null || a_surname.equals("")) {
            throw new NullPointerException("Surname cannot be null or empty!");
        } else if (a_contract <= 0 || a_hourlyRate < 0) {
            throw new IllegalArgumentException("Contract cannot be non positive (" + a_contract +
                    ") or hour rate (" + a_hourlyRate + ") cannot be negative");
        }
        Employee newWorker = new Worker(a_surname, a_contract, a_workingTime, a_hourlyRate);
        addEmployee(newWorker);
    }

    public void removeEmployee(String a_surname) throws NullPointerException {
        if (a_surname == null || a_surname.equals("") || m_size == 0) {
            throw new NullPointerException("Given surname (" + a_surname + ") is null or size (" + m_size + ") of staff equals zero!");
        }
        int index = getEmployeeIndex(a_surname);
        Employee[] newStaff = new Employee[--m_size];
        for (int i = 0, j = 0; i < m_size; i++, j++) {
            if (j == index) {
                i--;
                continue;
            }
            newStaff[i] = m_staff[j];
        }
        m_staff = newStaff;
    }

    // [0] number of Not Workers, [1] number of Workers
    public int[] calculateEmployeeTypes() throws NullPointerException {
        if (m_size == 0) {
            throw new NullPointerException("Size cannot be 0 to get number of types of employees!");
        }
        int[] result = new int[2];
        for (Employee e : m_staff) {
            if (e.isNotWorker()) {
                result[0]++;
            } else {
                result[1]++;
            }
        }
        return result;
    }

    // [0] payment for Not Workers, [1] payment for Workers
    public double[] calculatePayments() throws NullPointerException {
        if (m_staff == null) {
            throw new NullPointerException("Array of employees cannot be null when counting the payments!");
        }
        double[] res = new double[2];
        for (Employee e : m_staff) {
            double payment = e.getPayment();
            if (e.isNotWorker()) {
                res[0] += payment;
            } else {
                res[1] += payment;
            }
        }
        return res;
    }

    // accessors
    public double getEmployeePayment(int a_index) throws IndexOutOfBoundsException {
        if (a_index < 0 || a_index >= m_size) {
            throw new IndexOutOfBoundsException("Out of bound index!");
        }
        return m_staff[a_index].getPayment();
    }

    public int getEmployeeIndex(String a_surname) throws NullPointerException {
        if (a_surname == null || a_surname.equals("")) {
            throw new NullPointerException("Given surname cannot be null or empty!");
        }
        for (int i = 0; i < m_size; i++) {
            if (m_staff[i].getSurname().equals(a_surname)) {
                return i;
            }
        }
        return 0;
    }

    public Employee getEmployee(String a_surname) throws NullPointerException {
        if (a_surname == null || a_surname.equals("")) {
            throw new NullPointerException("Given surname cannot be null or empty!");
        }
        for (Employee employee : m_staff) {
            if (employee.getSurname().equals(a_surname)) {
                return employee;
            }
        }
        return null;
    }

    public Employee getEmployee(int a_index) throws IndexOutOfBoundsException {
        if (a_index < 0 || a_index >= m_size) {
            throw new IndexOutOfBoundsException("Looked for index is out of bound!");
        } else {
            return m_staff[a_index];
        }
    }

    // prints
    public String toString() {
        StringBuffer output = new StringBuffer("Employed staff: " + m_size + "\n");
        for (int i = 0; i < m_size; i++) {
            output.append("Employee " + i + ": " + m_staff[i] + "\n");
        }
        return output.toString();
    }

    // String.compareTo() doesn't work as needed when there are strings such as "aag", "aaz" and "baa"
    public String printSortedPaymentsBySurname() throws NullPointerException {
        if (m_staff == null) {
            throw new NullPointerException("Staff array size cannot be null to print it alphabetically!");
        }
        Employee[] sortedEmployees = new Employee[m_size];
        sortedEmployees = m_staff;
        boolean notSorted = true;
        for(int i = 0; i < m_size && notSorted == true; i++) {
            notSorted = false;
            for(int j = 0; j < m_size - 1; j++){
                if(sortedEmployees[j+1].getSurname().compareTo(sortedEmployees[j].getSurname()) < 0){
                    Employee temp = sortedEmployees[j+1];
                    sortedEmployees[j+1] = sortedEmployees[j];
                    sortedEmployees[j] = temp;
                        notSorted = true;
                }
            }
        }
        System.out.println(Arrays.toString(sortedEmployees));
        // not working
//        int currentIndex = findNextAlphabetically("!", -1);
//        for(; currentIndex != -1;){
//            System.out.println(m_staff[currentIndex]);
//            currentIndex = findNextAlphabetically(m_staff[currentIndex].getSurname(), currentIndex);
//        }
        return null;
    }

    // private
    private int calculateLinesFile(FileInputStream a_file) {
        return 0;
    }

    private Worker workerFromFile(String a_line) throws IOException {
        String[] lines = a_line.split(" ");
        if (lines.length != 5) {
            throw new IOException("Wrong file format! \nline = " + Arrays.deepToString(lines));
        }
        String[] numbers = lines[2].split("/");
        double contract = Integer.parseInt(numbers[0]);
        if (numbers.length != 1) {
            contract /= Integer.parseInt(numbers[1]);
        }
        double workingTime = Double.parseDouble(lines[3]);
        double hourlyRate = Double.parseDouble(lines[4]);
        Worker newWorker = new Worker(lines[1], contract, workingTime, hourlyRate);
        return newWorker;
    }

    private NotWorker notWorkerFromFile(String a_line) throws IOException {
        String[] lines = a_line.split(" ");
        if (lines.length != 4) {
            throw new IOException("Wrong file format! \nline = " + Arrays.deepToString(lines));
        }
        String[] contractLines = lines[2].split("/");
        double contract = Double.parseDouble(contractLines[0]);
        if (contractLines.length != 1) {
            contract /= Double.parseDouble(contractLines[1]);
        }
        double bonus = Double.parseDouble(lines[3]);
        NotWorker newNotWorker = new NotWorker(lines[1], contract, bonus);
        return newNotWorker;
    }

    private void addEmployee(Employee a_newEmployee) {
        Employee[] newStaff = new Employee[m_size + 1];
        for (int i = 0; i < m_size; i++) {
            newStaff[i] = m_staff[i];
        }
        newStaff[m_size++] = a_newEmployee;
        m_staff = newStaff;
    }

    private void copyArray(Employee[] a_source, int a_size) {
        m_staff = new Employee[a_size];
        for (int i = 0; i < a_size; i++) {
            m_staff[i] = (a_source[i]);
        }
        m_size = a_size;
    }

//     String.compareTo() doesn't work as needed when there are strings such as "aag", "aaz" and "baa"
//    private int findNextAlphabetically(String a_currName, int currIndex){
//        int tempValue = 0;
//        int smallestBiggerValue = Integer.MAX_VALUE;
//        int result = -1;
//        for(int i = 0; i < m_size; i++){
//            if(i == currIndex){
//                continue;
//            }
//            tempValue = m_staff[i].getSurname().compareToIgnoreCase(a_currName);
//            if(tempValue > 0 && tempValue < smallestBiggerValue){
//                result = i;
//                smallestBiggerValue = tempValue;
//            }
//        }
//        return result;
//    }
    private void sortSelectionSort(Employee[] a_array) throws NullPointerException{
        if(a_array == null || a_array.length == 0){
            throw new NullPointerException("Cannot sort an empty array!");
        }
        int size = a_array.length;
        for(int i = 0; i < size - 1; i++){
            int min = i;
            for(int j = i + 1; j < size; j++){
                if(a_array[min].getSurname().compareTo(a_array[j].getSurname()) > 0){
                    min = j;
                }
            }
        }
    }
    // private test!
    private static class Test {
        private static boolean testClass() {
            try {
                System.out.println("Running all the tests for the class EmployedStaff!\n\n");
                EmployedStaff test = new EmployedStaff(MAX_SIZE, 1000, 160);
                testLoading(test);
                testSearching(test);
                testIsNotWorker(test);
                testAddingWorkerAndNotWorker(test);
                testRemovingEmployee(test);
                testCalculatingEmployeeTypes(test);
                testPaymentsForEmployees(test);
                testPrintingSorted(test);
                return true;
            } catch (Exception e) {
                System.out.println("Exception caught " + e.getMessage());
                return false;
            }
        }

        private static boolean testLoading(EmployedStaff a_test) throws FileNotFoundException {
            String fileName = "employees.txt";
            System.out.println("Loading data from the file " + fileName);
            a_test.loadStaff(fileName);
            System.out.println(a_test);
            return true;
        }

        private static boolean testSearching(EmployedStaff a_test) throws NullPointerException {
            String searchSurname = "Stallman";
            System.out.println("\nTesting finding an employee by surname = " + searchSurname);
            System.out.println(a_test.getEmployee(searchSurname));
            return true;
        }

        private static boolean testIsNotWorker(EmployedStaff a_test) throws Exception {
            System.out.println("\nTesting if two employees are not workers");
            int workerIndex = 32;
            int notWorkerIndex = 36;
            System.out.println("Is \n" + a_test.m_staff[workerIndex] +
                    "\n not a worker? = " + a_test.m_staff[workerIndex].isNotWorker());
            System.out.println("Is \n" + a_test.m_staff[notWorkerIndex] +
                    "\n not a worker? = " + a_test.m_staff[notWorkerIndex].isNotWorker());
            return true;
        }

        private static boolean testAddingWorkerAndNotWorker(EmployedStaff a_test) throws NullPointerException, IllegalArgumentException {
            System.out.println("\nTesting adding worker and not worker");
            a_test.addNotWorker("Sauron", 1.0 / 2, 100);
            a_test.addWorker("Saruman", 1, 160, 10);
            System.out.println(a_test);
            return true;
        }

        private static boolean testRemovingEmployee(EmployedStaff a_test) {
            String surname = "Sauron";
            System.out.println("\nTesting removing the employee with surname: " + surname);
            a_test.removeEmployee(surname);
            System.out.println("After removing " + surname + ": \n" + a_test);
            return true;
        }

        private static boolean testCalculatingEmployeeTypes(EmployedStaff a_test) throws NullPointerException {
            System.out.println("\nTesting calculating the number of Workers and Not Workers");
            int[] res = a_test.calculateEmployeeTypes();
            System.out.println(Arrays.toString(res));
            return true;
        }

        private static boolean testPaymentsForEmployees(EmployedStaff a_test) throws NullPointerException {
            System.out.println("\nTesting the calculation of the payments for: \n\t[0] - Not Workers, \n\t[1] - Workers");
            double[] payments = a_test.calculatePayments();
            System.out.println("Payments equal: \n" + Arrays.toString(payments));
            return true;
        }

        private static boolean testPrintingSorted(EmployedStaff a_test) throws NullPointerException {
            System.out.println("\nTesting printing sorted payments by name");
            a_test.printSortedPaymentsBySurname();
            return true;
        }
    }
}

