public class T2 {
    // members

    //public
    public static void main(){
        runTask();
    }
    //private
    private static void runTask(){
        System.out.println("-------------------------------------------------\nSecond demo program T2");
        System.out.print("First matrix (for multiplication): \n \t");
        System.out.print("Input the size M of a matrix MxN \n \t");
        int fRow = Functions.validateInput();
        System.out.println("Input the size N of a matrix MxN");
        int fColumn = Functions.validateInput();

        System.out.print("Second matrix (for multiplication): \n \t");
        System.out.print("Input the size M of a matrix MxN \n \t");
        int sRow = Functions.validateInput();
        System.out.println("Input the size N of a matrix MxN");
        int sColumn = Functions.validateInput();

        System.out.print("Third matrix (for addiction with the 2nd one): \n \t");
        System.out.print("Input the size M of a matrix MxN \n \t");
        int rRow = Functions.validateInput();
        System.out.println("Input the size N of a matrix MxN");
        int rColumn = Functions.validateInput();

        Matrix first = new Matrix(fRow, fColumn);
        Matrix second = new Matrix(sRow, sColumn);
        Matrix third = new Matrix(rRow, rColumn);
        first.fillMatrix(Double.class, 10);
        second.fillMatrix(Double.class, 10);
        third.fillMatrix(Double.class, 10);

        System.out.println("First matrix:");
        first.printMatrix();
        System.out.println("Transposing first matrix:");
        first = first.transpose();
        first.printMatrix();
        System.out.println("Second matrix:");
        second.printMatrix();
        System.out.println("Transposing second matrix:");
        second = second.transpose();
        second.printMatrix();
        System.out.println("Third matrix:");
        third.printMatrix();
        System.out.println("Transposing third matrix:");
        third = third.transpose();
        third.printMatrix();

        Matrix fourth = second.addMatrix(third);
        if(fourth != null){
            System.out.println("After the addition second with third: ");
            fourth.printMatrix();
        }
        fourth = first.multiplyMatrix(second);
        if(fourth != null){
            System.out.println("After the multiplication first with second: ");
            fourth.printMatrix();
        }
        if(fourth != null){
            System.out.println("After the transposition of the multiplication result: ");
            fourth = third.transpose();
            fourth.printMatrix();
        }
    }
}
