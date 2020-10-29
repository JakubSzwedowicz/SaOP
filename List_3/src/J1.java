import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.InputMismatchException;
import java.util.Scanner;

public class J1 {
    private static Scanner m_userInput = new Scanner(System.in);
    private static DateTimeFormatter m_dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main() {
        runTask();
    }
    private static void runTask(){
        System.out.println("Running the first task");
        System.out.println("Type first date in a format dd/mm/yyyy");
        LocalDate date1= null, date2= null;
        try {
            date1 = setDates();
            date2 = setDates();
            System.out.println("Date1 equals " + date1.toString());
            System.out.println("Date2 equals " + date2.toString());

            System.out.println("Is date1 before date2? \n answer = " +  date1.isBefore(date2));
            System.out.println("Is date1 a leap year? \n answer = " + date1.isLeapYear());
            System.out.println("How many days do month of date1 have? \n answer = " + date1.lengthOfMonth());
            System.out.println("100 days after the date1 is: \n answer = " + date1.plusDays(100));
            System.out.println("100 days before the date1 is: \n answer = " + date1.minusDays(100));
            System.out.println("The day of date1 is: \n answer = " + date1.getDayOfWeek());
            System.out.println("How many days are between date1 and date2? \n answer = " + ChronoUnit.DAYS.between(date1, date2));

        } catch (Exception e) {
            System.out.println("Caught exception " + e.getMessage());

        }
    }
    private static LocalDate setDates() {
        String dateString;
        LocalDate date = null;
        boolean flag = true;
        while (flag) {
            try {
                dateString = Functions.validateInput(String.class);
                date = LocalDate.parse(dateString, m_dateFormat);
                System.out.println("Inserted date " + date.toString());
                flag = false;
            } catch (ParseException e) {
                System.out.println("Error when setting date: " + e.getMessage());
            } catch(InputMismatchException e){
                System.out.println("Wrong input by the user: " + e.getMessage());
            } catch(Exception e){
                System.out.println("Something unexpected happened: " + e.getMessage());
            }
        }
        return date;
    }
    }
