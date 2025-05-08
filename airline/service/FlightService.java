package airline.service;

import airline.dao.FlightDAO;
import airline.model.Flight;
import airline.util.SortingUtil;
import airline.util.SearchingUtil;

public class FlightService {
    private FlightDAO flightDAO;

    public FlightService(FlightDAO dao) {
        this.flightDAO = dao;
    }

    public void addFlight(String name, String src, String dest, String dep, String arr, int price, int seats,String date) {
        flightDAO.addFlight(name, src, dest, dep, arr, price, seats,date);
    }

    public void viewAllFlights() {
        Flight[] allFlights = flightDAO.getAllFlights();
        for (Flight f : allFlights) {
            f.displayFlight();
        }
    }

    public void searchFlightsByRoute(String source, String destination) {
        Flight[] allFlights = flightDAO.getAllFlights();
        Flight[] results = SearchingUtil.linearSearchByRoute(allFlights, source, destination);
        if (results.length == 0) {
            System.out.println("No flights found for the route " + source + " to " + destination);
        } else {
            System.out.println("Available flights:");
            for (Flight f : results) f.displayFlight();
        }
    }
    public void searchFlightsByDate(String date) {
        Flight[] allFlights = flightDAO.getAllFlights();
        boolean found = false;
        for (Flight f : allFlights) {
            if (f.getDate().equals(date)) {  // Assuming you have getDate() in Flight
                f.displayFlight();
                found = true;
            }
        }
        if (!found) {
            System.out.println("No flights found on date: " + date);
        }
    }


    public void sortFlightsByPrice() {
        Flight[] allFlights = flightDAO.getAllFlights();
        SortingUtil.sortFlightsByPrice(allFlights);
        for (Flight f : allFlights) f.displayFlight();
    }

    public Flight getFlightById(int id) {
        return flightDAO.getFlightById(id);
    }

    public void updateSeats(int flightId, int newCount) {
        flightDAO.updateSeats(flightId, newCount);
    }
}
