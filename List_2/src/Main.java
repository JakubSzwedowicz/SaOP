import java.lang.reflect.Array;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    static ArrayList<Long>  mem_fact = new ArrayList<Long>(Arrays.asList(1L, 1L, 2L));
    public static void main(String[] a_args) {
        System.out.println("I1() = counting for [1, 2, 3, 4, 5, 12] = " + I1(Arrays.asList(1, 2, 3, 4, 5, 12)).toString());
        System.out.println("I2() = Sum of digits of the number 12903 is: " + I2(12903));
        System.out.println("I3() = The number 24310 has " + I3(24310) + " significant figures");
        System.out.println("I4() = The biggest digit in a number 24310 is " + I4(24310));
        System.out.println("I5() = Is number 341231 prime? answer = " + I5(341231));
        System.out.println("I6() = Trivial algorithm: GCD of 31 104 and 129 024 is " + I6(31104, 129024));
        System.out.println("I7() = Euclidean algorithm %: GCD of 31 104 and 129 024 is " + I7(31104, 129024));
        System.out.println("I8() = Euclidean algorithm without %: GCD of 31 104 and 129 024 is " + I8(31104, 129024));
        System.out.println("I9() = 3 to the power of 19 is " +I9(3, 19));
        System.out.println("I10() = Counting series' for x = 0.25 and k = 10: " + I10(0.25, 10));
        System.out.println("I10() = Counting series' for x = 0.25 and E = 1e-5: " + I10(0.25, 1e-5));
    }

    public static <T extends Number> List<Double> I1(List<T> a_series) {
        double sum = 0, mean = 0, mean2 = 0, temp;
        int b = 0;
        for (int i = 0; i < a_series.size() - 1; i++) {
            temp = a_series.get(i).doubleValue();
            sum += temp;
            if (temp > 0) {
                b++;
                mean += temp;
                mean2 += a_series.get(i + 1).doubleValue();
            }
        }
        mean2 /= b;
        temp = a_series.get(a_series.size() - 1).doubleValue();
        sum += temp;
        if (temp > 0) {
            mean += temp;
            b++;
        }
        mean /= b;
        ArrayList<Double> res = new ArrayList<Double>(Arrays.asList(sum, mean, mean2));
        return res;
    }

    public static int I2(int a_number) {
        int sum = 0;
        while (a_number != 0) {
            sum += a_number % 10;
            a_number /= 10;
        }
        return sum;
    }
    public static int I3(int a_number){
        return (int)Math.log10(a_number) + 1;
    }
    public static int I4(int a_number){
        int max = 0, digit;
        while(a_number != 0){
            digit = a_number % 10;
            if(max < digit)
                max = digit;
            a_number /= 10;
        }
        return max;
    }
    public static boolean I5(int a_number){
        if(a_number < 2)    // number is negative, 0 or 1
            return false;
        if(a_number == 2)   // the only even number that is prime
            return true;
        if((a_number & 1) == 0) // Number is even
            return false;
        for(int i = 3; i <= Math.sqrt(a_number); i+=2){ // from this point we can iterate for 2
            if(a_number % i == 0)
                return false;
        }
            return true;
    }
    public static int I6(int a_M, int a_N){
        ArrayList<Integer> m_factors = new ArrayList<Integer>(), n_factors = new ArrayList<Integer>();
        boolean flag = false;
        for(int j = 2; a_M != 1 || a_N != 1; j++){
                if(a_M % j == 0){
                    m_factors.add(j);
                    a_M /= j;
                    flag = true;
                }
                if(a_N % j == 0){
                    n_factors.add(j);
                    a_N /= j;
                    flag = true;
                }
                if(flag){
                    flag = false;
                    j--;
                }
        }
        int GCF = 1;
        int temp;
        for(int i = 0, j = 0; i < m_factors.size() && j < n_factors.size();i++){
            temp = m_factors.get(i);
            for(int k = j; k < n_factors.size(); k++){
                if(temp == n_factors.get(k))
                {
                    GCF *= m_factors.get(i);
                    j = k + 1;
                    break;
                }
            }
        }
        return GCF;
    }
    public static int I7(int a_M, int a_N){
        int res;
        while(a_N != 0){
            res = a_M % a_N;
            a_M = a_N;
            a_N = res;
        }
        return a_M;
    }
    public static int I8(int a_M, int a_N){
        while(a_M != a_N){
            if(a_M < a_N)
                a_N -= a_M;
            else
                a_M -= a_N;
        }
        return a_M;
    }
    public static int I9(int a_base, int a_power){
        if(a_power == 0)
            return 1;
        if(a_power == 1)
            return a_base;
        if(a_power % 2 == 0)
            return I9(a_base*a_base, a_power/2);
        else
            return a_base*I9(a_base*a_base, (a_power - 1)/2);
    }
    public static long factorial(int a_fact){
        if(mem_fact.size() - 1  >= a_fact)  // memoization
            return mem_fact.get(a_fact);
        mem_fact.ensureCapacity(a_fact);
        for(int i = mem_fact.size(); i <= a_fact; i++)
            mem_fact.add(mem_fact.get(i-1) * i);
        return mem_fact.get(a_fact);
    }
    public static ArrayList<Double> I10(double a_x, int a_bound){
        double e = 1, sin = a_x, cos = 1;
        double e_num = 1, sin_num = a_x, cos_num = 1;
        double square = Math.pow(a_x, 2);
        ArrayList<Double> res = new ArrayList<Double>();
        for(int i = 1; i < a_bound + 1; i++){
            e_num *= a_x / i;
            e += e_num;
            sin_num = -(sin_num * square / (2*i * (2*i + 1)));
            sin += sin_num;
            cos_num = -(cos_num * square / ((2*i -1) * 2*i));
            cos += cos_num;
        }
        res.addAll(Arrays.asList(e, sin, cos));
        return res;
    }
    public static ArrayList<Double> I10(double a_x, double a_precision){
        double e = 1, sin = a_x, cos = 1;
        double e_prec = 1, sin_prec = a_x, cos_prec = 1;
        boolean flag = true;
        double square = Math.pow(a_x, 2);
        ArrayList<Double> res = new ArrayList<Double>();
        for(int i = 1; flag; i++){
            flag = false;
            if(Math.abs(e_prec) > a_precision){
                e_prec *= a_x / i;
                e += e_prec;
                flag = true;
            }
            if(Math.abs(sin_prec) > a_precision){
                sin_prec = -(sin_prec * square / (2*i * (2*i + 1)));
                sin += sin_prec;
                flag = true;
            }
            if(Math.abs(cos_prec) > a_precision){
                cos_prec = -(cos_prec * square / ((2*i -1) * 2*i));
                cos += cos_prec;
                flag = true;
            }
        }
        res.addAll(Arrays.asList(e, sin, cos));
        return res;
    }
    public static ArrayList<Double> I10_2(double a_x, int a_bound){
        double e = 0, sin = 0, cos = 0;
//        double e_num, sin_num, cos_num;
        ArrayList<Double> res = new ArrayList<Double>();
        for(int i = 0; i < a_bound + 1; i++){
            e += Math.pow(a_x, i) / factorial(i);
            sin += Math.pow(-1, i) * Math.pow(a_x, 2*i + 1) / factorial(2*i + 1);
            cos += Math.pow(-1, i) * Math.pow(a_x, 2*i) / factorial(2*i);
        }
        res.addAll(Arrays.asList(e, sin, cos));
        return res;
    }

}
