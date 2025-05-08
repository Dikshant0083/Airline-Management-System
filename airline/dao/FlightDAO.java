package airline.dao;

import airline.model.Flight;
import airline.util.LinkedList;
import airline.util.FlightDataSaver;

public class FlightDAO {
    private LinkedList flightList = new LinkedList();
    private int flightCounter = 100;

    public void addFlight(String name, String src, String dest, String dep, String arr, int price, int seats, String date) {
        Flight f = new Flight(flightCounter++, name, src, dest, dep, arr, price, seats, date);
        flightList.add(f);
        System.out.println("Flight added successfully with ID: " + f.flightId);
        FlightDataSaver.saveFlightsToFile(this, "flights.txt"); // Save after adding
    }

    public Flight[] getAllFlights() {
        Object[] objs = flightList.toArray();
        Flight[] flights = new Flight[objs.length];
        for (int i = 0; i < objs.length; i++) {
            flights[i] = (Flight) objs[i];
        }
        return flights;
    }

    public Flight getFlightById(int id) {
        Flight[] flights = getAllFlights();
        for (Flight f : flights) {
            if (f.flightId == id)
                return f;
        }
        return null;
    }

    public void updateSeats(int flightId, int newCount) {
        Flight f = getFlightById(flightId);
        if (f != null) {
            f.seatsAvailable = newCount;
            FlightDataSaver.saveFlightsToFile(this, "flights.txt"); // Save after seat update
        }
    }

    public void deleteFlightById(int flightId) {
        Flight[] flights = getAllFlights();
        for (Flight f : flights) {
            if (f.flightId == flightId) {
                flightList.remove(f);
                FlightDataSaver.saveFlightsToFile(this, "flights.txt"); // Save after deleting
                break;
            }
        }
    }

    public void printAllFlights() {
        Flight[] flights = getAllFlights();
        for (Flight f : flights) {
            f.displayFlight();
        }
    }
}
