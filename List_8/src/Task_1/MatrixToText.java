package Task_1;

import Functions.Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class MatrixToText {
    // MEMBERS
    private static String s_fileName = "Matrix.txt";
    private static String s_resourcesPath = "src/Task_1/Resources/";

    // PUBLIC
    public static boolean writeToFileSign(List<List<Double>> a_matrix) {
        return writeToFileSign(a_matrix, s_fileName);
    }

    public static boolean writeToFileSign(List<List<Double>> a_matrix, String a_filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(new File(s_resourcesPath.concat(a_filename))))) {
            writer.write("Matrix\n");
            writer.write(a_matrix.size() + "\n");
            writer.write(a_matrix.get(0).size() + "\n");
            for (List row : a_matrix) {
                // wanted to necessary use Stream
                writer.write(IntStream.range(0, row.size()).mapToObj(i -> row.get(i).toString()).collect(Collectors.joining(" ")) + "\n");
            }
        } catch (IOException ex) {
            System.out.println("IOException caught " + a_filename);
            return false;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return false;
        }
        return true;
    }

    public static List<List<Double>> readFromFileSign() {
        return readFromFileSign(s_fileName);
    }

    public static List<List<Double>> readFromFileSign(String a_fileName) {
        List<List<Double>> res;
        try (BufferedReader reader = new BufferedReader(new FileReader(s_resourcesPath.concat(a_fileName)))) {
            String line = reader.readLine();
            if (!line.equals("Matrix")) {
                System.out.println("File doesn't contain any matrix");
                return null;
            }
            res = new ArrayList<>(Integer.parseInt(reader.readLine()));
            int length = Integer.parseInt(reader.readLine());
            while ((line = reader.readLine()) != null) {
                List<Double> row = new ArrayList<>(length);
                // Wanted to necessary use Stream
                row.addAll(Stream
                        .of(line.split(" "))
                        .map(string -> Double.parseDouble(string))
                        .collect(Collectors.toList()));
                res.add(row);
            }
        } catch (IOException ex) {
            System.out.println("IOException caught " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return null;
        }
        return res;
    }

    public static String printMatrix(List<? extends List<?>> a_matrix) {
        StringBuilder output = new StringBuilder();
        int rows = a_matrix.size();
//        int columns = a_matrix.get(0).size();
        for (int i = 0; i < rows; i++) {
            output.append(a_matrix.get(i) + "\n");
        }
        return output.toString();
    }

    public static void testClass() {
        try {
            List<List<Double>> matrix = Utilities.generateMatrix(2, 3, -1, 5);
            System.out.println("Creating the matrix \n" + printMatrix(matrix) + " and saving it into the file " + s_resourcesPath.concat(s_fileName));
            writeToFileSign(matrix, s_fileName);
            System.out.println("Printing the matrix from the file: ");
            System.out.println(printMatrix(readFromFileSign(s_fileName)));
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
        }
    }
    // PRIVATE


}
