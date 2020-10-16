import java.awt.geom.Point2D;   // Used a lot in D1
import java.io.*;
import java.time.LocalDate; // LocalDate type
import java.util.List;  // List<>
import java.util.Scanner;   // Scanner user_input = new Scanner(System.in)
//import java.util.logging.ConsoleHandler;
//import java.util.logging.Handler;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class Main {
    public static void main(String[] args){

        Scanner user_input = new Scanner(System.in);
        boolean running = true;
        int option;

        while(running){
            try{
                System.out.println("Which task '1-6' would you like to run? \n In order to stop the program type '0' ");
                while(!user_input.hasNextInt())
                {
                    System.out.println("It's not a number!");
                    user_input.next();
                    System.out.println("Which task '1-6' would you like to run? \n In order to stop program type '0' ");
                }
                switch(option = user_input.nextInt()){
                    case 0 -> {
                        running = false;
                    }
                    case 1 -> {
                        D1();
                    }
                    case 2 -> {
                        D2();
                    }
                    case 3 -> {
                        D3();
                    }
                    case 4 -> {
                        D4();
                    }
                    case 5 -> {
                        D5();
                    }
                    default -> {
                        D6();
                    }
                }
            }
            catch(Exception e){
                System.out.println("Something went wrong. \n" +
                        "Error message: " + e.getMessage() +
                        "\n\n Caused by: " + e.toString());
            }
            finally{
                System.out.println("Repeating the program.");
            }
        }

    }
    public static void load_schemes(Scheme[] a_schemes){
        a_schemes[0].add_figure(false, "Circle", "light_orange", new Point2D.Double(1,1), 1);
        a_schemes[0].add_figure(false, "Circle", "orange", new Point2D.Double(-1,1), 1);
        a_schemes[0].add_figure(false, "Circle", "red", -1, -1, 1);
        a_schemes[0].add_figure(false, "Circle", "dark_red", 1, -1, 1);
        a_schemes[0].add_figure(true, "Square", "yellow", 0, 0, 4);

        a_schemes[1].add_figure(false, "Circle", "dark_red", 0, 0, 1);
        a_schemes[1].add_figure(false, "Ring", "red", 0, 0, 1, 2);
        a_schemes[1].add_figure(false, "Ring", "orange", 0, 0, 2, 3);
        a_schemes[1].add_figure(false, "Ring", "light_orange", 0, 0, 3, 4);
        a_schemes[1].add_figure(true, "Square", "yellow", 0, 0, 4);

        a_schemes[2].add_figure(false, "Triangle", "red", 0,0,1);
        a_schemes[2].add_figure(false, "Triangle", "light_orange", -1,0,1);
        a_schemes[2].add_figure(false, "Triangle", "dark_red", -1,-1,1);
        a_schemes[2].add_figure(false, "Triangle", "orange", 0,-1,1);
        a_schemes[2].add_figure(true,"Square", "Yellow", 0, 0, 4);
    }
    public static void D1(){
        boolean running;
        List<Figure> figure;
        Scanner input = new Scanner(System.in);
        Scheme[] schemes = new Scheme[3];
        for(int i = 0; i < 3; i++)
            schemes[i] = new Scheme();
        load_schemes(schemes);
        System.out.println("3 Schemes are loaded. Type the scheme '0-2' and point 'X Y' you want to validate.");
        int input_scheme = input.nextInt();
        Point2D input_point = new Point2D.Double(input.nextDouble(), input.nextDouble());
        figure = schemes[input_scheme].check_point(input_point);
        System.out.println("Colors: ");
        for(Figure fig : figure)
            System.out.println(fig.get_color() + " ");
    }
    public static void D2(){
        Scanner user_input = new Scanner(System.in);
        System.out.println("Type 3 numbers so that program could print it sorted");
        double a,b,c;
        a = user_input.nextDouble();
        b = user_input.nextDouble();
        c = user_input.nextDouble();
        if(a > b)
        {
            if(b > c)
                System.out.println(c + " " + b + " " + a);
            else
            if(a > c)
                System.out.println(b + " " + c + " " + a);
            else
                System.out.println(b + " " + a + " " + c);
        }
        else
        {
            if(a > c)
                System.out.println(c + " " + a + " " + b);
            else
            if(b > c)
                System.out.println(a + " " + c + " " + b);
            else
                System.out.println(a + " " + b + " " + c);
        }
    }
    public static void D3(){
        Scanner user_input = new Scanner(System.in);
        double[] numbers = new double[3];
        System.out.println("Type 3 numbers so that program could sort and print it");
        numbers[0] = user_input.nextDouble();
        numbers[1] = user_input.nextDouble();
        numbers[2] = user_input.nextDouble();
        bubble_sort(numbers);
        print_array(numbers);
    }
    public static void D4(){
        Scanner user_input = new Scanner(System.in);
        double[] numbers = new double[3];
        System.out.println("Type 3 sides of a triangle: ");
        numbers[0] = user_input.nextDouble();
        numbers[1] = user_input.nextDouble();
        numbers[2] = user_input.nextDouble();
        bubble_sort(numbers);
        if(numbers[0] + numbers[1] > numbers[2])
        {
            print_array(numbers);
            System.out.println("A triangle can be built from those sides");
            System.out.println("The triangle is: \n\t" + triangle_sides_type(numbers) + " \n\t" + triangle_angle_type(numbers));
        }
        else
        {
            System.out.println("Triangle can't be built from the sides " + numbers[0] + " " + numbers[1] + " " + numbers[2]);
        }
    }
    public static void D5(){
        Scanner user_input = new Scanner(System.in);
        double[] points = new double[6];
        System.out.println("Type 3 points in a format: 'X Y'");
        for(int i = 0; i < points.length; i++)
        {
            points[i] = user_input.nextDouble();
        }
        Triangle triangle = new Triangle("red", points[0], points[1], points[2], points[3], points[4], points[5]);
        System.out.println("Now type the point 'X Y' to check if it lays on the triangle");
        double px = user_input.nextDouble();
        double py = user_input.nextDouble();
        if(triangle.check_point(px, py))
            System.out.println("The point: (" + px + " " + py + ") belongs to the triangle");
        else
            System.out.println("The point: (" + px + " " + py + ") doesn't belong to the triangle");
    }
    public static void D6(){
        LocalDate local_date = LocalDate.now();
        System.out.println("Today is" + local_date.toString());
        System.out.println("Yesterday was " + local_date.minusDays(1) + " and tomorrow is going to be " + local_date.plusDays(1));
    }
    public static void clear_console() {
        // Poor implementation - just shifts
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
    private static void bubble_sort(double[] a_numbers){
        boolean unsorted = true;
        double temp;
        for(int i = 0; unsorted == true; i++)
        {
            unsorted = false;
            for(int j = 0; j < a_numbers.length - 1; j++)
                if(a_numbers[j] > a_numbers[j+1])
                {
                    temp = a_numbers[j];
                    a_numbers[j] = a_numbers[j+1];
                    a_numbers[j + 1] = temp;
                    unsorted = true;
                }
        }
    }
    private static void print_array(double[] a_array){
        for(double number : a_array){
            System.out.print(number + " ");
        }
        System.out.println();
    }
    private static String triangle_sides_type(double[] a_numbers){
        int duplicates = 0;
        for(int i = 0; i < a_numbers.length - 1; i++)
            if(a_numbers[i] == a_numbers[i+1])
                duplicates++;
        switch(duplicates){
            case 0 -> {
                return "scalene(POL: różnoboczny)";
            }
            case 1 -> {
                return "isosceles(POL: równoramienny)";
            }
            default -> {
                return "equilateral(POL: równoboczny)";
            }
        }
    }
    private static String triangle_angle_type(double[] a_numbers){
        double ab2 = a_numbers[1] * a_numbers[1] + a_numbers[0] * a_numbers[0];
        double c2 = a_numbers[2] * a_numbers[2];
        if(ab2 == c2)
            return "Right(POL: prostokątny)";
        else if(ab2 > c2)
            return "acute(POL: ostrokątny)";
        else
            return "obtuse(POL: rozwartokątny)";
    }
}
