package airline.util;

import airline.dao.FlightDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FlightDataLoader {
    public static void preloadFlightsFromFile(FlightDAO flightDAO, String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 8) {
                    String name = parts[0].trim();
                    String src = parts[1].trim();
                    String dest = parts[2].trim();
                    String dep = parts[3].trim();
                    String arr = parts[4].trim();
                    int price = Integer.parseInt(parts[5].trim());
                    int seats = Integer.parseInt(parts[6].trim());
                    String date = parts[7].trim();

                    flightDAO.addFlight(name, src, dest, dep, arr, price, seats, date);
                    count++;
                }
            }
            System.out.println("✔ " + count + " flights loaded from file.\n");
        } catch (IOException e) {
            System.out.println("❌ Error reading flight data file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number format in flight file: " + e.getMessage());
        }
    }
}