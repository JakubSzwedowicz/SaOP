package Int3.Vehicles;

public class Vehicle implements Identification{
    // MEMBER
    protected String m_licensePlate;

    // PUBLIC
    Vehicle(String a_licensePlate) throws NullPointerException{
        if(a_licensePlate == null || a_licensePlate.equals("")){
            throw new NullPointerException("Given license plate cannot be null ro empty!");
        }
        m_licensePlate = a_licensePlate;
    }

    @Override
    public String licensePlate() {
        return m_licensePlate;
    }

    @Override
    public String toString(){ return licensePlate();}

    public boolean equals(Object a_object){
        if(a_object == null || !(a_object instanceof Vehicle)){
            return false;
        }
        Vehicle vehicle = (Vehicle)a_object;
        return m_licensePlate.equals(vehicle.m_licensePlate);
    }
}
