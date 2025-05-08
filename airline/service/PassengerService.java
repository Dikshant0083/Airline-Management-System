package airline.service;

import airline.dao.PassengerDAO;
import airline.model.Passenger;

public class PassengerService {
    private PassengerDAO passengerDAO;

    public PassengerService(PassengerDAO dao) {
        this.passengerDAO = dao;
    }


    public int registerPassenger(String name, String email, int age, String gender, String phone) {
        return passengerDAO.addPassenger(name, email, age, gender, phone);  // Must return int
    }

    public Passenger getPassengerById(int id) {
        return passengerDAO.getPassengerById(id);
    }

    public void displayAllPassengers() {
        Passenger[] passengers = passengerDAO.getAllPassengers();
        if (passengers.length == 0) {
            System.out.println("No passengers found.");
        } else {
            for (Passenger p : passengers) {
                p.displayPassenger();
            }
        }
    }
}
