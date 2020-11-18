package O6;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Hotel {
    //members
    private class HotelInfo {
        private int m_floorsNumber;
        private int m_guestsNumber;
        private int m_roomsPerFloorNumber;
        private int m_roomsFree;
        private Floor.FloorInfo[] m_floorsInfo;
        private Room.RoomInfo[][] m_roomsInfo;

        HotelInfo(int a_floor, int a_guests, int a_rooms) {
            m_floorsNumber = a_floor;
            m_guestsNumber = a_guests;
            m_roomsPerFloorNumber = a_rooms;
            m_roomsFree = a_rooms * a_floor;
            m_floorsInfo = new Floor.FloorInfo[a_floor];
            m_roomsInfo = new Room.RoomInfo[a_floor][];
        }

        public String toString() {
            return "Number of floors: " + m_hotelInfo.m_floorsNumber +
                    "\nNumber of guests: " + m_hotelInfo.m_guestsNumber +
                    "\nRooms per floor: " + m_hotelInfo.m_roomsPerFloorNumber +
                    "\nNumber of free rooms: " + m_hotelInfo.m_roomsFree;
        }
    }

    private Floor[] m_floors;
    private List<Person> m_guests;
    private HotelInfo m_hotelInfo;

    //public
    Hotel(int a_floors, int a_roomsPerFloor) throws IllegalArgumentException {
        if (a_floors < 0 || a_roomsPerFloor < 1) {
            throw new IllegalArgumentException("There cannot be a hotel like that floors = " + a_floors +
                    " and roomsPerFloor = " + a_roomsPerFloor);
        }
        m_hotelInfo = new HotelInfo(a_floors, 0, a_roomsPerFloor);
        m_floors = new Floor[a_floors];
        m_guests = new ArrayList<>(a_floors * a_roomsPerFloor);
        for (int i = 0; i < a_floors; i++) {
            m_floors[i] = new Floor(this, i, a_roomsPerFloor);
            m_hotelInfo.m_floorsInfo[i] = m_floors[i].getFloorInfo();
            m_hotelInfo.m_roomsInfo[i] = m_floors[i].getRoomInfo();
        }
    }

    public boolean checkIn(Person a_newGuest, int a_roomNumber) {
        if (addGuest(a_newGuest, a_roomNumber)) {
            addIfNotPresent(a_newGuest);
            return true;
        } else {
            return false;
        }
    }

    public static void testClass() {
        System.out.println("---------------------------------------------\n" +
                "Testing class 06.Hotel for O6 task\n" +
                "----------------------------------------------");
        Hotel hotel = new Hotel(3, 5);
        hotel.printGuests();
        Person a = new Person("Jakub", "Szwedowicz", hotel);
        hotel.checkIn(a, 101);
        hotel.checkIn(a, 102);
        hotel.checkIn(a, 2);
        System.out.println();
        hotel.printGuests();
        int adjacentRooms = 5;
        System.out.println("\n" + adjacentRooms + " adjacent rooms start from the room "
                + hotel.checkAdjacentFreeRooms(adjacentRooms));
        Person b = new Person("Darth", "Vader", hotel);
        System.out.println("Is guest " + a.getPersonalInfo() + " present = " + hotel.isGuestPresent(a));
        System.out.println("Is guest " + b.getPersonalInfo() + " present = " + hotel.isGuestPresent(b));
        int[] rooms = hotel.getOccupiedRooms(a.getPersonalInfo().m_secondName);
        System.out.println("Guest " + a.getPersonalInfo() + " occupies rooms = " + Arrays.toString(rooms));
        System.out.println("Checking in " + b.getPersonalInfo() + "\n\n");
        hotel.checkIn(b, 4);
        hotel.printGuests();
        System.out.println("Checking out " + a.getPersonalInfo() + "\n\n");
        hotel.checkOut(a.getPersonalInfo().m_secondName);
        hotel.printGuests();
    }

    public Integer checkAdjacentFreeRooms(int a_adjacentRooms) {
        for (int i = 0; i < m_hotelInfo.m_floorsNumber; i++) {
            int[] tempResult = m_floors[i].getMaxFreeAdjacentRooms();
            if (tempResult[1] >= a_adjacentRooms) {
                return tempResult[0];
            }
        }
        return null;
    }

    public boolean isAnyRoomFree() {
        return m_hotelInfo.m_roomsFree != 0 ? true : false;
    }

    public int[] getOccupiedRooms(String a_surname) {
        Person guest = getGuest(a_surname);
        int[] result = null;
        if (guest != null) {
            List<Room> rooms = guest.getRooms();
            result = new int[rooms.size()];
            for (int i = 0; i < rooms.size(); i++) {
                result[i] = rooms.get(i).getRoomInfo().getRoomNumber();
            }
        }
        return result;
    }

    public Person getGuest(String a_surname) {
        int place = findGuestIndex(new Person("empty", a_surname, this));
        if (place >= 0) {
            return m_guests.get(place);
        } else {
            return null;
        }
    }

    public boolean checkOut(int[] a_roomsToFree) {
        for (int i = 0; i < a_roomsToFree.length; i++) {
            if (!checkOut(a_roomsToFree[i])) {
                return false;
            }
        }
        return true;
    }

    public boolean checkOut(int a_roomNumber) {
        int floor = getFloorBasedOnRoom(a_roomNumber);
        int room = getRoomBasedOnNumber(a_roomNumber);
        m_hotelInfo.m_roomsFree++;
        return m_floors[floor].checkOut(room);
    }

    public boolean checkOut(String a_surname) {
        Person guest = getGuest(a_surname);
        if (guest != null) {
            int[] roomsToFree = guest.getRoomsNumbers();
            return checkOut(roomsToFree);
        } else {
            return false;
        }
    }

    public void removeGuest(Person a_guest) {
        m_guests.remove(a_guest);
        m_hotelInfo.m_guestsNumber--;
    }

    // accessors
    public int getNumberOfFreeRooms() {
        return m_hotelInfo.m_roomsFree;
    }

    public boolean isGuestPresent(Person a_guest) {
        int place = findClosestGuestIndex(0, m_hotelInfo.m_guestsNumber - 1, a_guest);
        if (m_guests.get(place).getPersonalInfo().m_secondName.compareTo(a_guest.getPersonalInfo().m_secondName) == 0) {
            return true;
        } else {
            return false;
        }

    }

    // print methods
    public void printGuests() {
        System.out.println(m_hotelInfo);
        for (int i = 0; i < m_hotelInfo.m_guestsNumber; i++) {
            System.out.println("GUEST " + i + ":\n" + m_guests.get(i).toString());
        }
    }

    public String toString() {
        return m_hotelInfo.toString();
    }

    // private
    private boolean addGuest(Person a_newGuest, int a_roomNumber) {
        int floor = getFloorBasedOnRoom(a_roomNumber);
        int room = getRoomBasedOnNumber(a_roomNumber);
        if (m_hotelInfo.m_roomsFree != 0 && validateRoomNumber(floor, room) && m_floors[floor].checkIn(a_newGuest, room)) {
            m_hotelInfo.m_roomsFree--;
            return true;
        } else {
            return false;
        }
    }

    private boolean validateRoomNumber(int a_roomNumber) {
        int floor = getFloorBasedOnRoom(a_roomNumber);
        int room = getRoomBasedOnNumber(a_roomNumber);
        return validateRoomNumber(floor, room);

    }

    private boolean validateRoomNumber(int a_floor, int a_room) {
        if (a_floor < m_hotelInfo.m_floorsNumber && a_room < m_hotelInfo.m_roomsPerFloorNumber) {
            return true;
        } else {
            return false;
        }
    }

    private int getFloorBasedOnRoom(int a_roomNumber) {
        return a_roomNumber / 100;
    }

    private int getRoomBasedOnNumber(int a_roomNumber) {
        int number = a_roomNumber % 100;
        return number;
    }

    private void addIfNotPresent(Person a_newGuest) {
        if (m_hotelInfo.m_guestsNumber == 0) {
            m_guests.add(a_newGuest);
            m_hotelInfo.m_guestsNumber++;
        }
        int place = findClosestGuestIndex(0, m_hotelInfo.m_guestsNumber - 1, a_newGuest);
        if (m_guests.get(place).getPersonalInfo().m_secondName.compareTo(a_newGuest.getPersonalInfo().m_secondName) == 0) {
            return;
        } else {
            m_guests.add(place, a_newGuest);
            m_hotelInfo.m_guestsNumber++;
        }
    }

    private int findGuestIndex(Person a_person) {
        int index = findClosestGuestIndex(0, m_hotelInfo.m_guestsNumber - 1, a_person);
        if (m_guests.get(index).getPersonalInfo().m_secondName.compareTo(a_person.getPersonalInfo().m_secondName) == 0) {
            return index;
        } else {
            return -1;
        }
    }

    private int findClosestGuestIndex(int a_begin, int a_end, Person a_person) {
        if (a_end == a_begin) {
            int result = m_guests.get(a_begin).getPersonalInfo().m_secondName.compareTo(a_person.getPersonalInfo().m_secondName);
            if (result >= 0) {
                return a_begin;
            } else {
                return a_begin--;
            }
        } else if (a_end < a_begin) { // Something went wrong or m_guest is empty
            return -1;
        }
        int middle = (a_begin + a_end) / 2;
        int result = m_guests.get(middle).getPersonalInfo().m_secondName.compareTo(a_person.getPersonalInfo().m_secondName);
        if (result < 0) {
            return findClosestGuestIndex(a_begin, middle - 1, a_person);
        } else if (result > 0) {
            return findClosestGuestIndex(middle + 1, a_end, a_person);
        } else {
            return middle;
        }
    }

}