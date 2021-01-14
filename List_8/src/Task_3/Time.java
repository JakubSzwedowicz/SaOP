package Task_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Time implements Serializable {
    // MEMBERS
    private int m_Y; // Year
    private int m_M; // Month
    private int m_D; // Day
    private int m_h; // hour
    private int m_m; // minute
    private static final long serialVersionUID = 2L;

    // PUBLIC
    Time() {
        this(LocalDateTime.now());
    }

    Time(LocalDateTime a_time) throws NullPointerException {
        if (a_time == null) {
            throw new NullPointerException("Cant create Time from null timer!");
        }
        m_Y = a_time.getYear();
        m_M = a_time.getMonthValue();
        m_D = a_time.getDayOfMonth();
        m_h = a_time.getHour();
        m_m = a_time.getMinute();
    }

    Time(int a_year, int a_month, int a_day, int a_hour, int a_minute) throws IllegalArgumentException {
        if (a_year < 1 || a_month < 1 || a_day < 1 || a_hour < 0 || a_minute < 0) {
            throw new IllegalArgumentException("Given arguments are not valid:"
                    + "\n\t year = " + a_year
                    + "\n\t month = " + a_month
                    + "\n\t day = " + a_day
                    + "\n\t hour = " + a_hour
                    + "\n\t minute = " + a_minute);
        }
        m_Y = a_year;
        m_M = a_month;
        m_D = a_day;
        m_h = a_hour;
        m_m = a_minute;
    }

    public int[] getDateTime() {
        return new int[]{m_Y, m_M, m_D, m_h, m_m};
    }

    public int compare(Object a_object) {
        if (a_object == null || !(a_object instanceof Time)) {
            return -1;
        }
        Time timer = (Time) a_object;
        int[] dateTime = getDateTime();
        int[] compareDateTime = timer.getDateTime();
        int diff = 0;
        for (int i = 0; i < dateTime.length; i++) {
            diff = dateTime[i] - compareDateTime[i];
            if (diff != 0) return diff;
        }
        return diff;
    }

    public int compareMonth(int a_month){
        return m_M - a_month;
    }
    public int compareYear(int a_year){
        return m_Y - a_year;
    }
    // print
    public String toString() {
        return "date = " + m_Y + "." + m_M + "." + m_D + ", " + m_h + ":" + m_m;
    }

    // PRIVATE
    private void writeObject(ObjectOutputStream a_out) throws IOException {
        a_out.defaultWriteObject();
    }

    private void readObject(ObjectInputStream a_in) throws IOException, ClassNotFoundException {
        a_in.defaultReadObject();
    }
}
