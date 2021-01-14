package Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utilities {
    // MEMBERS
    private static Random s_generator = new Random();

    // PUBLIC
    public static List<List<Double>> generateMatrix(int a_rows, int a_columns, double a_lowerBound, double a_upperBound)
            throws IllegalArgumentException{
        if(a_rows < 0 || a_columns < 0){
            throw new IllegalArgumentException("Matrix cannot have negative dimensions!"
                    + "\n\trows = " + a_rows
                    + "\n\tcolumns = " + a_columns);
        }
        List<List<Double>> res = new ArrayList<>(a_rows);
        for(int i = 0; i < a_rows; i++){
            List<Double> row = new ArrayList<>(a_columns);
            for(int j = 0; j < a_columns; j++){
                row.add(s_generator.nextDouble() * (a_upperBound - a_lowerBound) + a_lowerBound);
            }
            res.add(row);
        }
        return res;
    }
    // PRIVATE
}
