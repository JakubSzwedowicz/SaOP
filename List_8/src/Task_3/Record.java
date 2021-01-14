package Task_3;

import java.io.*;
import java.time.LocalDateTime;

public class Record implements Serializable {
    // MEMBERS
    Time m_time;
    double m_temp; // temperature
    private static final long serialVersionUID = 1L;

    // PUBLIC
    Record(double a_temp) {
        this(new Time(), a_temp);
    }

    Record(LocalDateTime a_time, double a_temp) {
        this(new Time(a_time), a_temp);
    }

    Record(Time a_time, double a_temp) throws NullPointerException {
        if (a_time == null) {
            throw new NullPointerException("Given time cannot be null!");
        }
        m_time = a_time;
        m_temp = a_temp;
    }

    public int compareTime(Object a_object) {
        if (a_object == null || !(a_object instanceof Record)) {
            return Integer.MIN_VALUE;
        }
        Record record = (Record) a_object;
        return m_time.compare(record.m_time);
    }

    public int compareMonth(int a_month) {
        if (a_month == 0) { // in case of month == 0, everything should match
            return 0;
        }
        return m_time.compareMonth(a_month);
    }

    public int compareYear(int a_year) {
        if (a_year == 0) { // in case of year == 0, everything should match
            return 0;
        }
        return m_time.compareYear(a_year);
    }

    public double compareTemp(Object a_object) {
        if (a_object == null || !(a_object instanceof Record)) {
            return -Double.MAX_VALUE;
        }
        Record record = (Record) a_object;
        return (m_temp - record.m_temp);
    }

    // print
    public String toString() {
        return "temp = " + m_temp + ", " + m_time;
    }

    // PRIVATE
    @Serial
    private void writeObject(ObjectOutputStream a_out) throws IOException {
        a_out.defaultWriteObject();
    }

    @Serial
    private void readObject(ObjectInputStream a_in) throws IOException, ClassNotFoundException {
        a_in.defaultReadObject();
    }
}