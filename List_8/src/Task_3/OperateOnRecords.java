package Task_3;

import java.io.*;
import java.time.LocalDateTime;

public class OperateOnRecords {
    // MEMBERS
    private static String s_resourcesPath = "src/Task_3/Resources/";
    private static String s_fileName = "Records.ser";
    private static String s_tempFileName = s_resourcesPath.concat("Records.ser.temp");
//    List<Task_3.Record> m_records;

    // PUBLIC
    public static boolean addObjectToFile(Object a_object) {
        return addObjectToFile(a_object, s_fileName);
    }

    public static boolean addObjectToFile(Object a_object, String a_fileName) {
        if (a_object == null || a_fileName == null || a_fileName.equals("")) {
            return false;
        }
        try (ObjectOutputStream writer = AppendingObjectOutputStream.generateObjectOutputStream(s_resourcesPath.concat(a_fileName), true)) {
            writer.writeObject(a_object);
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException caught " + ex.getMessage());
            return false;
        } catch (IOException ex) {
            System.out.println("IOException caught " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return false;
        }
        return true;
    }

    public static boolean insertRecord(Object a_object) {
        return insertRecord(a_object, s_fileName);
    }

    private static boolean insertRecord(Object a_object, String a_fileName) {
        if (a_object == null || a_fileName == null || a_fileName.equals("")) {
            return false;
        }
        ObjectOutputStream output = null;
        File oldFile = new File(s_resourcesPath.concat(a_fileName));
        File newFile = new File(s_tempFileName);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(oldFile))) {
            output = new ObjectOutputStream(new FileOutputStream(newFile));
            Record fileRecord;
            while (true) {
                fileRecord = (Record) input.readObject();
                if (fileRecord.compareTime(a_object) > 0) break;
                output.writeObject(fileRecord);
            }
            output.writeObject(a_object);
            a_object = null;
            output.writeObject(fileRecord);
            while (true) {
                fileRecord = (Record) input.readObject();
                output.writeObject(fileRecord);
            }
        } catch (FileNotFoundException ex) {
            System.out.println("FileNotFoundException caught " + ex.getMessage());
            return false;
        } catch (EOFException ex) {
            if (a_object != null) {
                output.writeObject(a_object);
            }
            output.close();
            // Streams are being closed here. Proceeding to "finally" block.
        } catch (IOException ex) {
            System.out.println("IOException caught " + ex.getMessage());
            return false;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.getMessage());
            return false;
        } finally {

            oldFile.delete();
            newFile.renameTo(oldFile);
            return true;
        }
    }

    // print
    public static String printFile(String a_fileName) {
        return printFile(a_fileName, 0, 0, false);
    }

    // negative numbers are illegal, 1 - 12 for concrete month, positive numbers for years.
    // 0 is a wildcard meaning all years or month
    public static String printFile(String a_fileName, int a_year, int a_month, boolean a_warmer) {
        if (a_month < 0 || a_month > 12 || a_year < 0) {
            return null;
        }
        StringBuilder output = new StringBuilder("Records within the file " + a_fileName);
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(s_resourcesPath.concat(a_fileName)))) {
            if (a_warmer == true) {
                printFileYearMonthWarmer(input, output, a_year, a_month);
            } else if (a_month == 0 && a_year == 0) {
                printFileWhole(input, output);
            } else {
                printFileYearMonth(input, output, a_year, a_month);
            }
        } catch (EOFException ex) {
            return output.toString();
        } catch (IOException ex) {
            System.out.println("IOException caught " + ex.getMessage());
            return null;
        } catch (Exception ex) {
            System.out.println("Exception caught " + ex.toString());
            return null;
        }
        return output.toString();
    }

    public static void testClass() {
        addObjectToFile(new Record(1));
        addObjectToFile(new Record(3));
        addObjectToFile(new Record(13));
        addObjectToFile(new Record(7));
        addObjectToFile(new Record(9));
        addObjectToFile(new Record(11));
        addObjectToFile(new Record(5));
        System.out.println("Printing file with 7 records:");
        System.out.println(printFile(s_fileName));
        LocalDateTime k = LocalDateTime.of(2020, 1, 1, 1,1);
        Record newRecord = new Record(k, 101);
        System.out.println("Adding the record " + newRecord);
        insertRecord(newRecord);
        System.out.println("Printing again:");
        System.out.println(printFile(s_fileName));
        int year = 2020, month = 12;
        boolean warmer = false;
        System.out.println("Printing all records from " + year + "." + month);
        System.out.println(printFile(s_fileName, year, month, warmer));
        System.out.println("Printing all records from " + year + "." + month + " which were warmer than the previous day");
        warmer = true;
        System.out.println(printFile(s_fileName, year, month, warmer));
    }

    // PRIVATE
    private static String printFileWhole(ObjectInputStream a_input, StringBuilder a_output)
            throws IOException, ClassNotFoundException {
        while (true) {
            a_output.append("\n" + a_input.readObject());
        }
    }

    private static String printFileYearMonthWarmer(ObjectInputStream a_input, StringBuilder a_output, int a_year, int a_month)
            throws IOException, ClassNotFoundException {
        Record previousRecord = null;
        while (true) {
            Record record = (Record) a_input.readObject();
            if (record.compareYear(a_year) == 0) {  // If year matches
                if (record.compareMonth(a_month) == 0) {    // if month matches
                    if (record.compareTemp(previousRecord) > 0) { // if previous day was colder
                        a_output.append("\n" + record);
                    }
                }
            }
            previousRecord = record;
        }
    }

    private static String printFileYearMonth(ObjectInputStream a_input, StringBuilder a_output, int a_year, int a_month)
            throws IOException, ClassNotFoundException {
        while (true) {
            Record record = (Record) a_input.readObject();
            if (record.compareYear(a_year) == 0) {
                if (record.compareMonth(a_month) == 0) {
                    a_output.append("\n" + record);
                }
            }
        }
    }

}

//

