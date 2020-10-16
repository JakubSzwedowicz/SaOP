import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class Logger extends PrintStream {
    private final PrintStream second;

    public Logger(OutputStream a_main, PrintStream a_second){
        super(a_main);
        second = a_second;
    }

    // it doesn't have to be implemented but whatever
    @Override
    public void close(){
        super.close();
    }
    @Override
    public void flush(){
        super.flush();
        second.flush();
    }
    @Override
    public void write(byte[] a_buf, int a_off, int a_len){
        super.write(a_buf, a_off, a_len);
        second.write(a_buf, a_off, a_len);
    }
    @Override
    public void write(int a_b){
        super.write(a_b);
        second.write(a_b);
    }
    @Override
    public void write(byte[] a_b) throws IOException {
        super.write(a_b);
        second.write(a_b);
    }
}
