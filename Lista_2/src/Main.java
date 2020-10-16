import java.util.List;

public class Main {
    public static void main(String[] a_args){

    }
    private static List<Double> I1(List<Double> a_series){
        double sum = 0, mean = 0, mean2 = 0;
        for(double number : a_series) {
            sum += number;
            if(number > 0)
                mean += number;

        }
    }
}
