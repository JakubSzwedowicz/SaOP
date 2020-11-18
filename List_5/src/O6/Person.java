package O6;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Person {
    public class PersonInfo {
        String m_firstName;
        String m_secondName;

        PersonInfo(String a_firstName, String a_secondName) {
            m_firstName = a_firstName;
            m_secondName = a_secondName;
        }

        public String toString() {
            return m_firstName + " " + m_secondName;
        }
    }

    private static final List<Room> NO_ROOM = Collections.unmodifiableList(new ArrayList<>());

    // members
    private PersonInfo m_personInfo;
    private List<Room> m_rooms;
    private Hotel m_hotel;

    // public
    Person(String a_firstName, String a_secondName, Hotel a_hotel) {
        m_personInfo = new PersonInfo(a_firstName, a_secondName);
        m_hotel = a_hotel;
        m_rooms = NO_ROOM;
    }

    public void checkIn(Room a_newRoom) {
        if (m_rooms.size() == 0) {
            m_rooms = new ArrayList<>(1);
        }
        m_rooms.add(a_newRoom);
    }


    public void checkOut(Room a_roomToFree) {
        m_rooms.remove(a_roomToFree);
        if (m_rooms.size() == 0) {
            m_hotel.removeGuest(this);
        }
    }

    public List<Room> getRooms() {
        return m_rooms;
    }

    public int[] getRoomsNumbers() {
        int[] rooms = null;
        if (m_rooms.size() != 0) {
            rooms = new int[m_rooms.size()];
            for (int i = 0; i < rooms.length; i++) {
                rooms[i] = m_rooms.get(i).getRoomInfo().getRoomNumber();
            }
        }
        return rooms;
    }

    // print methods
    public String toString() {
        StringBuffer output = new StringBuffer(m_personInfo.toString());
        for (int i = 0; i < m_rooms.size(); i++) {
            output.append("\n\t Room " + i + ": \n" + m_rooms.get(i));
        }
        return output.toString();
    }

    // accessors
    public PersonInfo getPersonalInfo() {
        return m_personInfo;
    }
    // private

}
