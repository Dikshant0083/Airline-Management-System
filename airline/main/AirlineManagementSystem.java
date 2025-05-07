package airline.main;

import airline.dao.BookingDAO;
import airline.dao.FlightDAO;
import airline.dao.PassengerDAO;
import airline.service.BookingService;
import airline.service.FlightService;
import airline.service.PassengerService;
import airline.service.PaymentService;
import airline.util.FlightDataLoader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AirlineManagementSystem {
    private static FlightDAO flightDAO = new FlightDAO();
    private static PassengerDAO passengerDAO = new PassengerDAO();
    private static BookingDAO bookingDAO = new BookingDAO();

    private static FlightService flightService = new FlightService(flightDAO);
    private static PassengerService passengerService = new PassengerService(passengerDAO);
    private static PaymentService paymentService = new PaymentService();
    private static BookingService bookingService = new BookingService(bookingDAO, flightDAO, passengerDAO, paymentService);

    private static Scanner scanner = new Scanner(System.in);

//    private static void preloadFlights() {
//        flightDAO.addFlight("Air India 101", "Delhi", "Mumbai", "06:00", "08:00", 3500, 100, "10-05-2025");
//        flightDAO.addFlight("IndiGo 202", "Mumbai", "Bangalore", "09:00", "11:00", 3000, 120, "11-05-2025");
//        flightDAO.addFlight("SpiceJet 303", "Chennai", "Hyderabad", "13:00", "14:30", 2500, 80, "10-05-2025");
//        flightDAO.addFlight("Vistara 404", "Delhi", "Goa", "17:00", "19:00", 4000, 90, "12-05-2025");
//        flightDAO.addFlight("GoAir 505", "Kolkata", "Delhi", "20:00", "22:30", 3700, 75, "10-05-2025");
//        System.out.println("✔ Flights loaded successfully.\n");
//    }
private static void preloadFlights() {
    System.out.println("✔ Flights loaded from file (if available).\n");
}


    public static void main(String[] args) {
        FlightDataLoader.preloadFlightsFromFile(flightDAO, "flights.txt");  // Load flights once at the start
        while (true) {
            try {
                System.out.println("\n--- Airline Management System ---");
                System.out.println("1. Admin Menu");
                System.out.println("2. Customer Menu");
                System.out.println("3. Exit");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        adminMenu();
                        break;
                    case 2:
                        customerMenu();
                        break;
                    case 3:
                        System.out.println("Exiting system. Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠ Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    private static void adminMenu() {
        while (true) {
            try {
                System.out.println("\n--- Admin Menu ---");
                System.out.println("1. Add Flight");
                System.out.println("2. View All Flights");
                System.out.println("3. Search Flights by Date");
                System.out.println("4. Update Seats");
                System.out.println("5. Delete Flight");
                System.out.println("6. Back to Main Menu");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Flight Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Source: ");
                        String src = scanner.nextLine();
                        System.out.print("Destination: ");
                        String dest = scanner.nextLine();

                        String dep, arr;
                        while (true) {
                            System.out.print("Departure Time (HH:mm): ");
                            dep = scanner.nextLine();
                            if (dep.matches("([01]?\\d|2[0-3]):[0-5]\\d")) break;
                            System.out.println("❌ Invalid time format.");
                        }

                        while (true) {
                            System.out.print("Arrival Time (HH:mm): ");
                            arr = scanner.nextLine();
                            if (arr.matches("([01]?\\d|2[0-3]):[0-5]\\d")) break;
                            System.out.println("❌ Invalid time format.");
                        }

                        int price;
                        while (true) {
                            System.out.print("Price: ");
                            price = scanner.nextInt();
                            if (price >= 0) break;
                            System.out.println("❌ Price cannot be negative.");
                        }

                        int seats;
                        while (true) {
                            System.out.print("Total Seats: ");
                            seats = scanner.nextInt();
                            if (seats >= 1) break;
                            System.out.println("❌ Seat count must be at least 1.");
                        }
                        scanner.nextLine(); // consume newline

                        String date;
                        while (true) {
                            System.out.print("Booking Date (DD-MM-YYYY): ");
                            date = scanner.nextLine();
                            if (isValidFutureDate(date)) break;
                            System.out.println("❌ Booking date must be today or a future date.");
                        }

                        flightDAO.addFlight(name, src, dest, dep, arr, price, seats, date);
                        break;

                    case 2:
                        flightDAO.printAllFlights();
                        break;

                    case 3:
                        System.out.print("Enter Date (DD-MM-YYYY): ");
                        String searchDate = scanner.nextLine();
                        flightService.searchFlightsByDate(searchDate);
                        break;

                    case 4:
                        System.out.print("Flight ID: ");
                        int fid = scanner.nextInt();
                        int newSeats;
                        while (true) {
                            System.out.print("New Seat Count: ");
                            newSeats = scanner.nextInt();
                            if (newSeats >= 1) break;
                            System.out.println("❌ Seat count must be at least 1.");
                        }
                        flightDAO.updateSeats(fid, newSeats);
                        System.out.println("✔ Seat count updated.");
                        break;

                    case 5:
                        System.out.print("Enter Flight ID to delete: ");
                        int id = scanner.nextInt();
                        flightDAO.deleteFlightById(id);
                        break;

                    case 6:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠ Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void customerMenu() {
        while (true) {
            try {
                System.out.println("\n--- Customer Menu ---");
                System.out.println("1. View Available Flights");
                System.out.println("2. Search Flights by Route");
                System.out.println("3. Sort Flights by Price");
                System.out.println("4. Register Passenger");
                System.out.println("5. Book Flight");
                System.out.println("6. Cancel Booking");
                System.out.println("7. View My Bookings");
                System.out.println("8. Back to Main Menu");
                System.out.print("Enter choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        flightDAO.printAllFlights();
                        break;

                    case 2:
                        System.out.print("Enter Source: ");
                        String src = scanner.nextLine();
                        System.out.print("Enter Destination: ");
                        String dest = scanner.nextLine();
                        flightService.searchFlightsByRoute(src, dest);
                        break;

                    case 3:
                        flightService.sortFlightsByPrice();
                        break;

                    case 4:
                        System.out.print("Enter Passenger Name: ");
                        String pname = scanner.nextLine();

                        String email;
                        while (true) {
                            System.out.print("Enter Passenger E-mail: ");
                            email = scanner.nextLine();
                            if (email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) break;
                            System.out.println("❌ Invalid email format. Try again.");
                        }

                        System.out.print("Enter Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine();

                        String gender;
                        while (true) {
                            System.out.print("Enter Gender (Male/Female/Other): ");
                            gender = scanner.nextLine();
                            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other")) break;
                            System.out.println("❌ Invalid gender.");
                        }

                        String phone;
                        while (true) {
                            System.out.print("Enter Passenger Phone Number: ");
                            phone = scanner.nextLine();
                            if (phone.matches("\\d{10}")) break;
                            System.out.println("❌ Phone number must be 10 digits.");
                        }

                        int pid = passengerService.registerPassenger(pname, email, age, gender, phone);
                        System.out.println("✔ Passenger registered successfully. Passenger ID: " + pid);
                        break;

                    case 5:
                        System.out.print("Enter Passenger ID: ");
                        int existingPid = scanner.nextInt();
                        System.out.print("Enter Flight ID: ");
                        int fid = scanner.nextInt();
                        System.out.print("Enter Number of Seats to Book: ");
                        int seatsToBook = scanner.nextInt();
                        bookingService.bookFlight(existingPid, fid, seatsToBook);
                        break;

                    case 6:
                        System.out.print("Enter Booking ID to cancel: ");
                        int bid = scanner.nextInt();
                        bookingService.cancelBooking(bid);
                        break;

                    case 7:
                        System.out.print("Enter your Passenger ID: ");
                        int Pid = scanner.nextInt();
                        bookingService.viewBookingsByPassenger(Pid);
                        break;

                    case 8:
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InputMismatchException e) {
                System.out.println("⚠ Invalid input. Please enter the correct data.");
                scanner.nextLine();
            }
        }
    }

    // Utility method for date validation
    private static boolean isValidFutureDate(String dateStr) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            sdf.setLenient(false);
            Date inputDate = sdf.parse(dateStr);
            return !inputDate.before(new Date());
        } catch (ParseException e) {
            return false;
        }
    }
}
