import java.util.*;
import java.text.SimpleDateFormat;

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private Date departureTime;
    private Date arrivalTime;
    private int totalSeats;
    private int availableSeats;
    private double price;

    public Flight(String flightNumber, String source, String destination,
                  Date departureTime, Date arrivalTime, int totalSeats, double price) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.totalSeats = totalSeats;
        this.availableSeats = totalSeats;
        this.price = price;
    }

    // Getters and setters
    public String getFlightNumber() { return flightNumber; }
    public String getSource() { return source; }
    public String getDestination() { return destination; }
    public Date getDepartureTime() { return departureTime; }
    public Date getArrivalTime() { return arrivalTime; }
    public int getAvailableSeats() { return availableSeats; }
    public double getPrice() { return price; }

    public boolean bookSeats(int numSeats) {
        if (availableSeats >= numSeats) {
            availableSeats -= numSeats;
            return true;
        }
        return false;
    }

    public void cancelBooking(int numSeats) {
        availableSeats += numSeats;
        if (availableSeats > totalSeats) {
            availableSeats = totalSeats;
        }
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return String.format("Flight %s: %s to %s | Departure: %s | Arrival: %s | Available Seats: %d | Price: ₹%.2f",
                flightNumber, source, destination, sdf.format(departureTime),
                sdf.format(arrivalTime), availableSeats, price);
    }
}

class Passenger {
    private String name;
    private String email;
    private String contactNumber;
    private String gender;
    private List<Booking> bookings;

    public Passenger(String name, String email, String contactNumber, String gender) {
        this.name = name;
        this.email = email;
        this.contactNumber = contactNumber;
        this.gender = gender;
        this.bookings = new ArrayList<>();
    }

    // Getters
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getContactNumber() { return contactNumber; }
    public String getGender() { return gender; }
    public List<Booking> getBookings() { return bookings; }

    public void addBooking(Booking booking) {
        bookings.add(booking);
    }

    public boolean cancelBooking(String bookingId) {
        for (Booking booking : bookings) {
            if (booking.getBookingId().equals(bookingId)) {
                booking.getFlight().cancelBooking(booking.getNumSeats());
                bookings.remove(booking);
                return true;
            }
        }
        return false;
    }
}

class Booking {
    private static int bookingCounter = 1;
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private int numSeats;
    private String paymentMethod;
    private String paymentDetails;
    private Date bookingDate;

    public Booking(Flight flight, Passenger passenger, int numSeats,
                   String paymentMethod, String paymentDetails) {
        this.bookingId = "BK" + String.format("%04d", bookingCounter++);
        this.flight = flight;
        this.passenger = passenger;
        this.numSeats = numSeats;
        this.paymentMethod = paymentMethod;
        this.paymentDetails = paymentDetails;
        this.bookingDate = new Date();
    }

    // Getters
    public String getBookingId() { return bookingId; }
    public Flight getFlight() { return flight; }
    public Passenger getPassenger() { return passenger; }
    public int getNumSeats() { return numSeats; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getPaymentDetails() { return paymentDetails; }
    public Date getBookingDate() { return bookingDate; }

    public double getTotalPrice() {
        return flight.getPrice() * numSeats;
    }

    public void printBill() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        System.out.println("\n========== BOOKING RECEIPT ==========");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Booking Date: " + sdf.format(bookingDate));
        System.out.println("\nPassenger Details:");
        System.out.println("Name: " + passenger.getName());
        System.out.println("Email: " + passenger.getEmail());
        System.out.println("Contact: " + passenger.getContactNumber());
        System.out.println("Gender: " + passenger.getGender());

        System.out.println("\nFlight Details:");
        System.out.println(flight);
        System.out.println("Number of Seats: " + numSeats);

        System.out.println("\nPayment Details:");
        System.out.println("Payment Method: " + paymentMethod);
        if (!paymentMethod.equalsIgnoreCase("cash")) {
            System.out.println("Payment Reference: " + paymentDetails);
        }
        System.out.println("Total Amount: ₹" + String.format("%.2f", getTotalPrice()));
        System.out.println("====================================\n");
    }
}

public class AirlineManagementSystem {
    private static List<Flight> flights = new ArrayList<>();
    private static List<Passenger> passengers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeFlights();

        while (true) {
            System.out.println("\n===== AIRLINE MANAGEMENT SYSTEM =====");
            System.out.println("1. Search and Book Flights");
            System.out.println("2. View/Cancel Bookings");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // consume newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the invalid input
                continue;
            }

            switch (choice) {
                case 1:
                    searchAndBookFlights();
                    break;
                case 2:
                    viewOrCancelBookings();
                    break;
                case 3:
                    System.out.println("Thank you for using our Airline Management System. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeFlights() {
        // Initialize with some sample flights
        Calendar cal = Calendar.getInstance();

        // Flight 1
        cal.set(2023, Calendar.DECEMBER, 15, 8, 0);
        Date dep1 = cal.getTime();
        cal.set(2023, Calendar.DECEMBER, 15, 10, 30);
        Date arr1 = cal.getTime();
        flights.add(new Flight("AI101", "Delhi", "Mumbai", dep1, arr1, 180, 4500.0));

        // Flight 2
        cal.set(2023, Calendar.DECEMBER, 15, 14, 30);
        Date dep2 = cal.getTime();
        cal.set(2023, Calendar.DECEMBER, 15, 17, 0);
        Date arr2 = cal.getTime();
        flights.add(new Flight("AI202", "Delhi", "Bangalore", dep2, arr2, 160, 6500.0));

        // Flight 3
        cal.set(2023, Calendar.DECEMBER, 16, 9, 15);
        Date dep3 = cal.getTime();
        cal.set(2023, Calendar.DECEMBER, 16, 11, 45);
        Date arr3 = cal.getTime();
        flights.add(new Flight("AI303", "Mumbai", "Delhi", dep3, arr3, 200, 4500.0));

        // Flight 4
        cal.set(2023, Calendar.DECEMBER, 16, 12, 0);
        Date dep4 = cal.getTime();
        cal.set(2023, Calendar.DECEMBER, 16, 14, 30);
        Date arr4 = cal.getTime();
        flights.add(new Flight("SG404", "Bangalore", "Chennai", dep4, arr4, 150, 3500.0));
    }

    private static void searchAndBookFlights() {
        System.out.println("\n===== SEARCH FLIGHTS =====");
        System.out.print("Enter source city: ");
        String source = scanner.nextLine();
        System.out.print("Enter destination city: ");
        String destination = scanner.nextLine();

        List<Flight> availableFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getSource().equalsIgnoreCase(source) &&
                    flight.getDestination().equalsIgnoreCase(destination) &&
                    flight.getAvailableSeats() > 0) {
                availableFlights.add(flight);
            }
        }

        if (availableFlights.isEmpty()) {
            System.out.println("No available flights found for the given route.");
            return;
        }

        System.out.println("\nAvailable Flights:");
        for (int i = 0; i < availableFlights.size(); i++) {
            System.out.println((i+1) + ". " + availableFlights.get(i));
        }

        System.out.print("\nEnter flight number to book (or 0 to cancel): ");
        int flightChoice;
        try {
            flightChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // clear the invalid input
            return;
        }

        if (flightChoice == 0) {
            return;
        }

        if (flightChoice < 1 || flightChoice > availableFlights.size()) {
            System.out.println("Invalid flight selection.");
            return;
        }

        Flight selectedFlight = availableFlights.get(flightChoice - 1);

        System.out.print("Enter number of seats to book: ");
        int numSeats;
        try {
            numSeats = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // clear the invalid input
            return;
        }

        if (numSeats <= 0) {
            System.out.println("Number of seats must be positive.");
            return;
        }

        if (selectedFlight.getAvailableSeats() < numSeats) {
            System.out.println("Not enough seats available. Only " + selectedFlight.getAvailableSeats() + " seats left.");
            return;
        }

        // Collect passenger details
        System.out.println("\n===== PASSENGER DETAILS =====");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your contact number: ");
        String contact = scanner.nextLine();
        System.out.print("Enter your gender (M/F/O): ");
        String gender = scanner.nextLine();

        Passenger passenger = new Passenger(name, email, contact, gender);
        passengers.add(passenger);

        // Payment details
        System.out.println("\nTotal Amount: ₹" + String.format("%.2f", selectedFlight.getPrice() * numSeats));
        System.out.println("\n===== PAYMENT OPTIONS =====");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Net Banking");
        System.out.print("Select payment method: ");

        int paymentChoice;
        try {
            paymentChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // clear the invalid input
            return;
        }

        String paymentMethod = "";
        String paymentDetails = "";

        switch (paymentChoice) {
            case 1:
                paymentMethod = "Cash";
                break;
            case 2:
                paymentMethod = "UPI";
                System.out.print("Enter UPI ID: ");
                paymentDetails = scanner.nextLine();
                break;
            case 3:
                paymentMethod = "Net Banking";
                System.out.print("Enter Account Number: ");
                paymentDetails = scanner.nextLine();
                break;
            default:
                System.out.println("Invalid payment method selection.");
                return;
        }

        // Book the flight
        if (selectedFlight.bookSeats(numSeats)) {
            Booking booking = new Booking(selectedFlight, passenger, numSeats, paymentMethod, paymentDetails);
            passenger.addBooking(booking);
            System.out.println("\nBooking successful!");
            booking.printBill();
        } else {
            System.out.println("Booking failed. Please try again.");
        }
    }

    private static void viewOrCancelBookings() {
        System.out.println("\n===== VIEW/CANCEL BOOKINGS =====");
        System.out.print("Enter your email to search bookings: ");
        String email = scanner.nextLine();

        Passenger passenger = null;
        for (Passenger p : passengers) {
            if (p.getEmail().equalsIgnoreCase(email)) {
                passenger = p;
                break;
            }
        }

        if (passenger == null) {
            System.out.println("No bookings found for this email.");
            return;
        }

        List<Booking> bookings = passenger.getBookings();
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this passenger.");
            return;
        }

        System.out.println("\nYour Bookings:");
        for (int i = 0; i < bookings.size(); i++) {
            System.out.println((i+1) + ". " + bookings.get(i).getFlight());
            System.out.println("   Booking ID: " + bookings.get(i).getBookingId());
            System.out.println("   Seats: " + bookings.get(i).getNumSeats());
            System.out.println("   Total: ₹" + String.format("%.2f", bookings.get(i).getTotalPrice()));
            System.out.println();
        }

        System.out.print("Enter booking number to cancel (or 0 to go back): ");
        int bookingChoice;
        try {
            bookingChoice = scanner.nextInt();
            scanner.nextLine(); // consume newline
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // clear the invalid input
            return;
        }

        if (bookingChoice == 0) {
            return;
        }

        if (bookingChoice < 1 || bookingChoice > bookings.size()) {
            System.out.println("Invalid booking selection.");
            return;
        }

        Booking bookingToCancel = bookings.get(bookingChoice - 1);
        if (passenger.cancelBooking(bookingToCancel.getBookingId())) {
            System.out.println("Booking " + bookingToCancel.getBookingId() + " has been cancelled successfully.");
            System.out.println("Amount ₹" + String.format("%.2f", bookingToCancel.getTotalPrice()) + " will be refunded.");
        } else {
            System.out.println("Failed to cancel booking.");
        }
    }
}