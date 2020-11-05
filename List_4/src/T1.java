public class T1 {
    // public
    public static void main(){
        runTasks();
    }
    // private
    private static void runTasks(){
        System.out.println("-------------------------------------------------\nFirst demo program T1");
        System.out.println("Type the size of set from 1 to " + Integer.MAX_VALUE);
        int size = Functions.validateInput(1, Integer.MAX_VALUE);
        int[] set = new int[size];
        System.out.println("Type the upper bound of set from 1 to " + Integer.MAX_VALUE);
        int upperBound = Functions.validateInput(1, Integer.MAX_VALUE);
        Functions.fillUniqueArray(set, upperBound);
        Functions.printArray(set, false);
        Functions.printArray(set, true);
        int[][] split = Functions.splitArrayOddEven(set);
        System.out.println("Printing array of only odd numbers");
        Functions.printArray(split[0], false);
        System.out.println("Printing array of only even numbers");
        Functions.printArray(split[1], false);
    }
}
