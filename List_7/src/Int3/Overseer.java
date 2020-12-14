package Int3;

import Int3.Vehicles.Car;
import Int3.Vehicles.Identification;
import Int3.Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Overseer implements Observer {

    // MEMBERS
    private int m_allFreeSpaces;
    private int m_allCapacity;
    private List<ParkingSpace> m_parkingSpaces;

    // PUBLIC
    public Overseer(){
        m_parkingSpaces = new ArrayList<>(5);
    }
    @Override
    public boolean update(int a_changeFreeSpace, int a_changeCapacity) {
        m_allFreeSpaces += a_changeFreeSpace;
        m_allCapacity += a_changeCapacity;
        if (m_allFreeSpaces < 0 || m_allCapacity < 0) {
            recalculateData();
        }
        return true;
    }

    public boolean removeAcceptedVehicle(Vehicle a_newVehicle, int a_parkingNumber)
            throws NullPointerException, IllegalArgumentException, Exception {
        if (a_newVehicle == null) {
            throw new NullPointerException("Can't remove null vehicle from the parking space!");
        } else if (a_parkingNumber < 0 || a_parkingNumber >= m_parkingSpaces.size()) {
            throw new IllegalArgumentException("Given parking number is wrong!" +
                    "\n\tparking number = " + a_parkingNumber + ", when there are = " + m_parkingSpaces.size());
        } else if (m_allFreeSpaces == 0) {
            throw new Exception("There are no more free places!");
        }
        return m_parkingSpaces.get(a_parkingNumber).removeAcceptedVehicle(a_newVehicle);
    }

    public boolean addNewAcceptedVehicle(Vehicle a_newVehicle, int a_parkingNumber)
            throws NullPointerException, IllegalArgumentException, Exception {
        if (a_newVehicle == null) {
            throw new NullPointerException("Can't add null vehicle to the accepted parking space!");
        } else if (a_parkingNumber < 0 || a_parkingNumber >= m_parkingSpaces.size()) {
            throw new IllegalArgumentException("Given parking number is wrong!" +
                    "\n\tparking number = " + a_parkingNumber + ", when there are = " + m_parkingSpaces.size());
        } else if (m_allFreeSpaces == 0) {
            throw new Exception("There are no more free places!");
        }
        return m_parkingSpaces.get(a_parkingNumber).addAcceptedVehicle(a_newVehicle);
    }

    public boolean registerNewVehicle(Vehicle a_newVehicle, int a_parkingNumber)
            throws NullPointerException, IllegalArgumentException {
        if (a_newVehicle == null) {
            throw new NullPointerException("Can't register null vehicle to the parking space!");
        } else if (a_parkingNumber < 0 || a_parkingNumber >= m_parkingSpaces.size()) {
            throw new IllegalArgumentException("Given parking number is wrong!" +
                    "\n\tparking number = " + a_parkingNumber + ", when there are = " + m_parkingSpaces.size());
        }
        for(ParkingSpace parkingSpace : m_parkingSpaces){
            if(parkingSpace.isPresent(a_newVehicle)){
                return false;
            }
        }
        return m_parkingSpaces.get(a_parkingNumber).registerVehicle(a_newVehicle.licensePlate());
    }

    public boolean unregisterNewVehicle(Vehicle a_newVehicle, int a_parkingNumber)
            throws NullPointerException, IllegalArgumentException {
        if (a_newVehicle == null) {
            throw new NullPointerException("Can't unregister null vehicle from the parking space!");
        } else if (a_parkingNumber < 0 || a_parkingNumber >= m_parkingSpaces.size()) {
            throw new IllegalArgumentException("Given parking number is wrong!" +
                    "\n\tparking number = " + a_parkingNumber + ", when there are = " + m_parkingSpaces.size());
        }
        return m_parkingSpaces.get(a_parkingNumber).unregisterVehicle(a_newVehicle.licensePlate());
    }

    public boolean addNewParkingSpace(int a_capacity) throws IllegalArgumentException {
        if (a_capacity < 0) {
            throw new IllegalArgumentException("New parking cannot have negative capacity!" +
                    "\n\tcapacity = " + a_capacity);
        }
        m_parkingSpaces.add(new ParkingSpace(a_capacity, this));
        m_allCapacity += a_capacity;
        m_allFreeSpaces += a_capacity;
        return true;
    }

    //test
    public static void testClass(){
        try{
            System.out.println("----------------------------\nTesting class Int3.Overseer\n----------------------------");
            Overseer overseer = new Overseer();

            overseer.addNewParkingSpace(100);
            Car car = new Car("DPL1");
            overseer.registerNewVehicle(car, 0);
            overseer.addNewAcceptedVehicle(car, 0);
            car = new Car("DPL2");
            overseer.registerNewVehicle(car, 0);
            overseer.addNewAcceptedVehicle(car, 0);
            car = new Car("DPL2");
            overseer.registerNewVehicle(car, 0);
            overseer.addNewAcceptedVehicle(car, 0);

            overseer.addNewParkingSpace(50);
            car = new Car("DPL2");
            overseer.registerNewVehicle(car, 1);
            overseer.addNewAcceptedVehicle(car, 1);
            car = new Car("DPL5");
            overseer.registerNewVehicle(car, 1);
            overseer.addNewAcceptedVehicle(car, 1);
            car = new Car("DPL6");
            overseer.addNewAcceptedVehicle(car, 1);

            System.out.println(overseer);
        } catch (Exception ex){
            System.out.println("Exception caught " + ex.getMessage());
        }
    }
    public String toString(){
        StringBuilder output = new StringBuilder("There are " + m_parkingSpaces.size() + " parking spaces." +
                "\n\tWhole free space = " + m_allFreeSpaces + "\n\tWhole capacity = " + m_allCapacity + "\n");
        for(int i = 0; i < m_parkingSpaces.size(); i++){
            output.append("Parking " + i + ":\n" + m_parkingSpaces.get(i));
        }
        return output.toString();
    }
    // PRIVATE
    private void recalculateData() {
        m_allFreeSpaces = 0;
        m_allCapacity = 0;
        for (ParkingSpace parkingSpace : m_parkingSpaces) {
            m_allFreeSpaces += parkingSpace.getParkingData().getFreePlaces();
            m_allCapacity += parkingSpace.getParkingData().getCapacity();
        }
    }

}
