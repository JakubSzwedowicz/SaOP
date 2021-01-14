package Task_2;

import Functions.Utilities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MatrixToBinary {
    // MEMBERS
    private final static int s_bufferSize = 1024;
    private static String s_fileName = "Matrix.ser";
    private static String s_resourcesPath = "src/Task_2/Resources/";

    // PUBLIC
    public static boolean writeToFileByte(List<List<Double>> a_matrix) {
        return writeToFileByte(a_matrix, s_fileName);
    }

    public static boolean writeToFileByte(List<List<Double>> a_matrix, String a_fileName) {
        try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(s_resourcesPath.concat(a_fileName)))) {
            writer.writeInt(a_matrix.size());
            writer.writeInt(a_matrix.get(0).size());
            for (List row : a_matrix) {
                writer.writeObject(row);
            }
        } catch (IOException ex) {
            System.out.println("IOException caught " + a_fileName);
            return false;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return false;
        }
        return true;
    }

    public static List<List<Double>> readFromFileByte() {
        return readFromFileByte(s_fileName);
    }

    public static List<List<Double>> readFromFileByte(String a_fileName) {
        List<List<Double>> res = null;
        try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(s_resourcesPath.concat(a_fileName)))) {
            res = new ArrayList<>(reader.readInt());
            int length = reader.readInt();
            Object line;
            // available() can't be used here - especially with objects being deserialized
            while (true) {
                res.add((List) reader.readObject());
            }
        } catch (EOFException ex) {
            // java...
            return res;
        } catch(IOException ex){
            System.out.println("IOException caught " + ex.getMessage());
            return null;
        }catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return null;
        }
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
        List<List<Double>> matrix = Utilities.generateMatrix(2, 4, -2, 2);
        System.out.println("Creating the matrix: \n" + printMatrix(matrix));
        System.out.println("Saving matrix into the file " + s_resourcesPath.concat(s_fileName));
        writeToFileByte(matrix);
        System.out.println("Printing matrix from file:");
        System.out.println(printMatrix(readFromFileByte()));
    }
    // PRIVATE
}
