package O6;

public class Floor {
    // members
    public class FloorInfo {
        int m_floorNumber;
        int m_guestsNumber;
        int m_roomsNumber;
        Room.RoomInfo[] m_roomsInfo;
        boolean m_free;

        FloorInfo(int a_floorNumber, int a_roomsNumber) {
            m_floorNumber = a_floorNumber;
            m_roomsNumber = a_roomsNumber;
            m_guestsNumber = 0;
            m_free = false;
            m_roomsInfo = new Room.RoomInfo[a_roomsNumber];
        }
    }

    private Room[] m_rooms;
    private Hotel m_hotel;
    private FloorInfo m_floorInfo;

    // public
    Floor(Hotel a_hotel, int a_floorNumber, int a_roomsNumber) {
        m_hotel = a_hotel;
        m_floorInfo = new FloorInfo(a_floorNumber, a_roomsNumber);
        m_rooms = new Room[a_roomsNumber];
        for (int i = 0; i < a_roomsNumber; i++) {
            m_rooms[i] = new Room(this, m_floorInfo.m_floorNumber * 100 + i);
            m_floorInfo.m_roomsInfo[i] = m_rooms[i].getRoomInfo();
        }
    }

    public boolean checkIn(Person a_newGuest, int a_roomIndex) {
        if (a_newGuest != null && m_rooms[a_roomIndex].isFree()) {
            Room room = m_rooms[a_roomIndex];
            m_floorInfo.m_guestsNumber++;
            room.checkIn(a_newGuest);
            return true;
        } else {
            return false;
        }
    }

    public boolean checkOut(int a_roomIndex) {
        if (!m_rooms[a_roomIndex].isFree()) {
            m_rooms[a_roomIndex].checkOut();
            m_floorInfo.m_guestsNumber--;
            return true;
        } else {
            return false;
        }
    }

    // return 2 numbers. [0] = room, [1] = number of free adjacent
    public int[] getMaxFreeAdjacentRooms() {
        int adjacentRooms = 0;
        int firstRoom = m_floorInfo.m_floorNumber * 100;
        int currentBegin = firstRoom;
        int[] result = new int[]{firstRoom, 0};
        for (int i = 0; i < m_floorInfo.m_roomsNumber; i++) {
            if (m_rooms[i].isFree() == true) {
                adjacentRooms++;
            } else {
                if (result[1] < adjacentRooms) {
                    result[1] = adjacentRooms;
                    result[0] = currentBegin;
                }
                currentBegin = firstRoom + i + 1;
                adjacentRooms = 0;
            }
        }
        if (adjacentRooms > result[1]) {
            result[0] = currentBegin;
            result[1] = adjacentRooms;
        }
        return result;
    }

    // accessors
    public Room[] getAllRooms() {
        return m_rooms;
    }

    public int getFloorNumber() {
        return m_floorInfo.m_floorNumber;
    }

    public FloorInfo getFloorInfo() {
        return m_floorInfo;
    }

    public Room.RoomInfo[] getRoomInfo() {
        return m_floorInfo.m_roomsInfo;
    }
    // private
}
