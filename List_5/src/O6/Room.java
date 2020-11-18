package O6;

public class Room {
    public class RoomInfo {
        private Floor m_floor;
        private Room m_room;
        private int m_number;

        private RoomInfo(Floor a_floor, int a_number, Room a_room) {
            m_floor = a_floor;
            m_room = a_room;
            m_number = a_number;
        }

        public String toString() {
            return "Room number: " + m_number + "\nFloor number: " + m_floor.getFloorNumber();
        }

        public int getRoomNumber() {
            return m_number;
        }
    }

    // members
    private RoomInfo m_roomInfo;
    private Person m_locator;
    private boolean m_free;

    // public
    Room(Floor a_floor, int a_number) {
        m_roomInfo = new RoomInfo(a_floor, a_number, this);
        m_free = true;
    }

    public boolean isFree() {
        return m_free;
    }

    public void checkIn(Person a_locator) {
        m_free = false;
        m_locator = a_locator;
        m_locator.checkIn(this);
    }

    public void checkOut() {
        m_locator.checkOut(this);
        m_locator = null;
        m_free = true;
    }

    public String toString() {
//        StringBuffer output = new StringBuffer(m_roomInfo.toString() + "\nis free: " + m_occupied);
        return m_roomInfo.toString() + "\nis free: " + m_free;
    }

    public String getLocator() {
        return m_locator.toString();
    }

    public RoomInfo getRoomInfo() {
        return m_roomInfo;
    }
    // private
}
