package Task_3;

import java.io.*;

public class AppendingObjectOutputStream extends ObjectOutputStream {

    // PUBLIC
    public static ObjectOutputStream generateObjectOutputStream(String a_name, boolean a_append)
            throws FileNotFoundException, IOException{
        File file = new File(a_name);
        if(file.isFile() & a_append){
            return new AppendingObjectOutputStream(new FileOutputStream(file, a_append));
        } else {
            return new ObjectOutputStream(new FileOutputStream(file, false));
        }
    }
    public AppendingObjectOutputStream(FileOutputStream a_out) throws IOException {
        super(a_out);
    }
    @Override
    public void writeStreamHeader() {
    }
}
