package airline.service;

import airline.dao.BookingDAO;
import airline.dao.FlightDAO;
import airline.dao.PassengerDAO;
import airline.model.Booking;
import airline.model.Flight;
import airline.model.Passenger;

import java.time.LocalDate;

public class BookingService {
    private BookingDAO bookingDAO;
    private FlightDAO flightDAO;
    private PassengerDAO passengerDAO;
    private PaymentService paymentService;
    private int bookingCounter = 1000;

    public BookingService(BookingDAO bookingDAO, FlightDAO flightDAO, PassengerDAO passengerDAO, PaymentService paymentService) {
        this.bookingDAO = bookingDAO;
        this.flightDAO = flightDAO;
        this.passengerDAO = passengerDAO;
        this.paymentService = paymentService;
    }

    public void bookFlight(int passengerId, int flightId, int seatsToBook) {
        Passenger passenger = passengerDAO.getPassengerById(passengerId);
        Flight flight = flightDAO.getFlightById(flightId);

        if (passenger == null) {
            System.out.println("Invalid passenger ID.");
            return;
        }

        if (flight == null) {
            System.out.println("Invalid flight ID.");
            return;
        }

        if (seatsToBook <= 0) {
            System.out.println("Seat count must be at least 1.");
            return;
        }

        if (flight.seatsAvailable < seatsToBook) {
            System.out.println("Only " + flight.seatsAvailable + " seats available. Booking failed.");
            return;
        }

        // Update seat count
        flight.seatsAvailable -= seatsToBook;
        flightDAO.updateSeats(flightId, flight.seatsAvailable);

        int bookingId = bookingCounter++;
        int amount = flight.price * seatsToBook;
        String bookingDate = LocalDate.now().toString();

        bookingDAO.addBooking(bookingId, passengerId, flightId, seatsToBook, amount, bookingDate);
        System.out.println("✔ Booking successful! Booking ID: " + bookingId + ", Seats Booked: " + seatsToBook);

        // ✅ Process full ticket payment
        paymentService.processTicketPayment(flight, bookingId, amount);
    }

    public void cancelBooking(int bookingId) {
        Booking booking = bookingDAO.getBookingById(bookingId);

        if (booking == null) {
            System.out.println("Invalid booking ID.");
            return;
        }

        // Increase seat count
        Flight flight = flightDAO.getFlightById(booking.flightId);
        if (flight != null) {
            flight.seatsAvailable += booking.seatsBooked;
            flightDAO.updateSeats(flight.flightId, flight.seatsAvailable);
        }

        // Refund amount
        System.out.println("Processing refund of ₹" + booking.amount + " for Booking ID: " + bookingId + "...");
        System.out.println("✔ Refund successful.");

        // Delete booking
        bookingDAO.deleteBooking(bookingId);
        System.out.println("Booking canceled successfully.");
    }

    public void viewBookingsByPassenger(int passengerId) {
        Booking[] bookings = bookingDAO.getBookingsByPassenger(passengerId);
        if (bookings.length == 0) {
            System.out.println("No bookings found for passenger ID: " + passengerId);
        } else {
            for (Booking b : bookings) {
                b.displayBooking();
            }
        }
    }
}
