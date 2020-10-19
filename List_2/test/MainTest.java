import org.junit.Assert;
import org.junit.Test;
import java.util.ArrayList;

import java.util.Arrays;

public class MainTest {

    @Test
    public void main() {
        ArrayList<Integer> numbers = new ArrayList<Integer>(Arrays.asList(1, 4, -8, 12, 10));
        Assert.assertEquals(Arrays.asList(19.0, 6.75, 2.0), Main.I1(numbers));
        Assert.assertEquals(23, Main.I2(19283));
        Assert.assertEquals(4, Main.I3(5311));
        Assert.assertEquals(9, Main.I4(125902));
        Assert.assertEquals(true, Main.I5(9833));
        Assert.assertEquals(false, Main.I5(9835));
        long start = System.nanoTime();
        Assert.assertEquals(1152, Main.I6(31104, 129024));
        long end = System.nanoTime() - start;
        System.out.println("Time meassured for I6(): " + end);

        start = System.nanoTime();
        Assert.assertEquals(1152, Main.I7(31104, 129024));
        end = System.nanoTime() - start;
        System.out.println("Time meassured for I7(): " + end);
        start = System.nanoTime();
        Assert.assertEquals(1152, Main.I8(31104, 129024));
        end = System.nanoTime() - start;
        System.out.println("Time meassured for I8(): " + end);

        start = System.nanoTime();
        Assert.assertEquals(1977326743, Main.I9(7, 11));
        end = System.nanoTime() - start;
        System.out.println("Time meassured for I9(): " + end);

        start = System.nanoTime();
        Main.I10(0.3, 10);
        end = System.nanoTime() - start;
        System.out.println("Main.I10() time: " + end);

        start = System.nanoTime();
        Main.I10_2(0.3, 10);
        end = System.nanoTime() - start;
        System.out.println("Main.I10_2() time: " + end);


        Assert.assertEquals(Arrays.asList(1.3498588075759574, 0.29552020666133955,0.955336489125606), Main.I10(0.3, 10));
        Assert.assertEquals(Arrays.asList(1.3498375, 0.29552025, 0.9553375), Main.I10(0.3, 1e-3));

        System.out.println("Testing finished successfully");
//        start = System.nanoTime();
//        Assert.assertEquals(Arrays.asList(), Main.I10(7, 11));
//        end = System.nanoTime() - start;
//        System.out.println("Time meassured for I9(): " + end);
    }
}