package Int3;

import Int3.Vehicles.Identification;

import java.util.ArrayList;
import java.util.List;

//public class ParkingSpace implements Parking {
public class ParkingSpace {
    // MEMBERS
    public class ParkingData {
        private final ParkingSpace m_owner;
        private int m_capacity;
        private int m_freePlaces;

        private ParkingData(ParkingSpace a_owner, int a_capacity, int a_freePlaces) {
            m_owner = a_owner;
            m_capacity = a_capacity;
            m_freePlaces = a_freePlaces;
        }

        public int getFreePlaces() {
            return m_freePlaces;
        }

        public int getCapacity() {
            return m_capacity;
        }
    }

    private List<Identification> m_acceptedVehicles;
    private List<String> m_registeredVehicles;
    private ParkingData m_parkingData;
    private List<Observer> m_watchers;

    // PUBLIC
    ParkingSpace(int a_capacity, Observer a_watcher) throws IllegalArgumentException {
        if (a_capacity < 0) {
            throw new IllegalArgumentException("Capacity of a parking space cannot be negative!");
        }
        m_parkingData = new ParkingData(this, a_capacity, a_capacity);
        m_acceptedVehicles = new ArrayList<>(a_capacity);
        m_registeredVehicles = new ArrayList<>(a_capacity);
        m_watchers = new ArrayList<>(5);
        m_watchers.add(a_watcher);
    }

    public boolean registerVehicle(Identification a_newLicensePlate) throws NullPointerException {
        if (a_newLicensePlate == null) {
            throw new NullPointerException("Can't register null license plate!");
        }
        return registerVehicle(a_newLicensePlate.licensePlate());
    }

    public boolean registerVehicle(String a_newLicensePlate) throws NullPointerException {
        if (a_newLicensePlate == null || a_newLicensePlate.equals("")) {
            throw new NullPointerException("Can't add null license plate to accepted vehicles list!" +
                    "\n\tnew license plate = " + a_newLicensePlate);
        }
        boolean present = m_registeredVehicles.contains(a_newLicensePlate);
        if (present) {
            return false;
        }
        return m_registeredVehicles.add(a_newLicensePlate);
    }

    public boolean unregisterVehicle(Identification a_newLicensePlate) throws NullPointerException {
        if (a_newLicensePlate == null) {
            throw new NullPointerException("Can't unregister null license plate!");
        }
        return unregisterVehicle(a_newLicensePlate.licensePlate());
    }

    public boolean unregisterVehicle(String a_removedLicensePlate) throws NullPointerException {
        if (a_removedLicensePlate == null || a_removedLicensePlate.equals("")) {
            throw new NullPointerException("Can't remove null license plate from accepted vehicles list!" +
                    "\n\tremoved license plate = " + a_removedLicensePlate);
        }
        return m_registeredVehicles.remove(a_removedLicensePlate);
    }

    public boolean addAcceptedVehicle(Identification a_newLicensePlate) throws NullPointerException {
        if (a_newLicensePlate == null) {
            throw new NullPointerException("Can't assign null license plate!");
        } else if (isPresent(a_newLicensePlate) || !isRegistered(a_newLicensePlate)) {
            return false;
        }
        boolean added = m_acceptedVehicles.add(a_newLicensePlate);
        if (added) {
            notifyObservers(-1, 0);
        }
        return added;
    }

    public boolean removeAcceptedVehicle(Identification a_deleteLicensePlate) throws NullPointerException {
        if (a_deleteLicensePlate == null) {
            throw new NullPointerException("Can't remove null license plate!");
        }
        boolean removed = m_acceptedVehicles.remove(a_deleteLicensePlate);
        if (removed) {
            notifyObservers(1, 0);
        }
        return removed;
    }

    public boolean isPresent(Identification a_licensePlate) throws NullPointerException {
        return m_acceptedVehicles.contains(a_licensePlate);
    }

    public boolean isRegistered(Identification a_licensePlate) {
        return isRegistered(a_licensePlate.licensePlate());
    }

    public boolean isRegistered(String a_licensePlate) {
        if (a_licensePlate == null || a_licensePlate.equals("")) {
            return false;
        }
        for (String plate : m_registeredVehicles) {
            if (plate.equals(a_licensePlate)) {
                return true;
            }
        }
        return false;
    }

    public boolean notifyObservers(int a_changeFreeSpaces, int a_changeCapacity) {
        m_parkingData.m_capacity += a_changeCapacity;
        m_parkingData.m_freePlaces += a_changeFreeSpaces;
        for (Observer watcher : m_watchers) {
            watcher.update(a_changeFreeSpaces, a_changeCapacity);
        }
        return true;
    }

    // getters
    public ParkingData getParkingData() {
        return m_parkingData;
    }

    public String toString(){
        StringBuilder output = new StringBuilder("Parking space:" +
                "\n\tfree space = " + getParkingData().m_freePlaces +
                "\n\tcapacity = " + m_parkingData.m_capacity + "\n");
        for(int i = 0; i < m_acceptedVehicles.size(); i++){
            output.append("license plate " + i + ": " + m_acceptedVehicles.get(i).licensePlate() + "\n");
        }
        return output.toString();
    }
    // PRIVATE

}
