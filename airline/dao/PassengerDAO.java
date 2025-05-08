package airline.dao;

import airline.model.Passenger;
import airline.util.LinkedList;

public class PassengerDAO {
    private LinkedList passengerList = new LinkedList();
    private int passengerCounter = 1;
    public int addPassenger(String name, String email, int age, String gender, String phone) {
        Passenger p = new Passenger(passengerCounter++, name, email, age, gender, phone);
        passengerList.add(p);
        System.out.println("Passenger registered successfully with ID: " + p.passengerId);
        return p.passengerId;
    }

    public Passenger getPassengerById(int id) {
        Object[] objs = passengerList.toArray();
        for (Object obj : objs) {
            Passenger p = (Passenger) obj;
            if (p.passengerId == id) {
                return p;
            }
        }
        return null;
    }

    public Passenger[] getAllPassengers() {
        Object[] objs = passengerList.toArray();
        Passenger[] passengers = new Passenger[objs.length];
        for (int i = 0; i < objs.length; i++) {
            passengers[i] = (Passenger) objs[i];
        }
        return passengers;
    }
}
