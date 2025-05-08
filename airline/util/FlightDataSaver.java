package airline.util;

import airline.dao.FlightDAO;
import airline.model.Flight;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FlightDataSaver {
    public static void saveFlightsToFile(FlightDAO flightDAO, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            Flight[] flights = flightDAO.getAllFlights();
            for (Flight f : flights) {
                String line = f.flightName + "," +
                        f.source + "," +
                        f.destination + "," +
                        f.departureTime + "," +
                        f.arrivalTime + "," +
                        f.price + "," +
                        f.seatsAvailable + "," +
                        f.getDate();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("✔ Flights saved to file.");
        } catch (IOException e) {
            System.out.println("❌ Error saving flights: " + e.getMessage());
        }
    }
}
