import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.List;

// JUnit test for exercise D1
public class MainTest {
    Point2D[][] test_points = new Point2D.Double[3][3];
    String[][][] test_answers = new String[3][3][];
    @Test
    public void main(){
        MainTest M = new MainTest();
        Figure figure;
        Scheme[] schemes = new Scheme[3];
        for(int i = 0; i < 3; i++)
            schemes[i] = new Scheme();
        Main.load_schemes(schemes);
        test_run(schemes);
        System.out.println("Testing finished successfully");
    }
    public MainTest(){
        // First scheme
        load_answers0();
        load_answers1();
        load_answers2();
    }
    private void load_answers0(){
        test_answers[0][0] = new String[1];
        test_points[0][0] = new Point2D.Double(0,0);
        test_answers[0][0][0] = "yellow";

        test_answers[0][1] = new String[2];
        test_points[0][1] = new Point2D.Double(1,0);
        test_answers[0][1][0] = "light_orange";
        test_answers[0][1][1] = "dark_red";

        test_answers[0][2] = new String[1];
        test_points[0][2] = new Point2D.Double(1,0.5);
        test_answers[0][2][0] = "orange";
    }
    private void load_answers1(){
        test_answers[1][0] = new String[1];
        test_points[1][0] = new Point2D.Double(0,0);
        test_answers[1][0][0] = "dark_red";

        test_answers[1][1] = new String[2];
        test_points[1][1] = new Point2D.Double(1,0);
        test_answers[1][1][0] = "red";
        test_answers[1][1][1] = "dark_red";

        test_answers[1][2] = new String[1];
        test_points[1][2] = new Point2D.Double(2.5,0);
        test_answers[1][2][0] = "orange";
    }
    private void load_answers2(){

        test_answers[2][0] = new String[3];
        test_points[2][0] = new Point2D.Double(0,0);
        test_answers[2][0][0] = "red";
        test_answers[2][0][1] = "orange";
        test_answers[2][0][2] = "light_orange";

        test_answers[2][1] = new String[1];
        test_points[2][1] = new Point2D.Double(1,0);
        test_answers[2][1][0] = "red";

        test_answers[2][2] = new String[1];
        test_points[2][2] = new Point2D.Double(1,-1);
        test_answers[2][2][0] = "orange";
    }
    private boolean test_contain_check(List<Figure> a_tested_answers, String[] a_answers){
        if(a_tested_answers.size() != a_answers.length)
            return false;
        String string_tested = "";
        for(Figure fig : a_tested_answers)
            string_tested += fig.get_color();
        for(String answers : a_answers)
        {
            if(!string_tested.contains(answers))
                return false;
        }
        return true;
    }
    private void test_run(Scheme[] a_schemes){
        List<Figure> res;
        boolean test_res;
        System.out.println("Testing Main");
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++){
                res = a_schemes[i].check_point(test_points[i][j]);
                Assert.assertTrue("Test failed", test_contain_check(res, test_answers[i][j]));
            }
        }
    }
}