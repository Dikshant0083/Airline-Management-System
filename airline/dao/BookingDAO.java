package airline.dao;

import airline.model.Booking;
import airline.util.LinkedList;

public class BookingDAO {
    private LinkedList bookingList = new LinkedList();

    // ✅ Removed internal bookingCounter to prevent conflict

    public void addBooking(int bookingId, int passengerId, int flightId, int seatCount, int totalPrice, String bookingDate) {
        Booking b = new Booking(bookingId, flightId, passengerId, totalPrice, bookingDate, seatCount);
        bookingList.add(b);
        System.out.println("Booking created successfully with ID: " + b.bookingId);
    }

    public Booking[] getAllBookings() {
        Object[] objs = bookingList.toArray();
        Booking[] bookings = new Booking[objs.length];
        for (int i = 0; i < objs.length; i++) {
            bookings[i] = (Booking) objs[i];
        }
        return bookings;
    }

    public Booking getBookingById(int id) {
        Booking[] allBookings = getAllBookings();
        for (Booking b : allBookings) {
            if (b.bookingId == id)
                return b;
        }
        return null;
    }

    public Booking[] getBookingsByPassenger(int passengerId) {
        Booking[] all = getAllBookings();
        int count = 0;
        for (Booking b : all) {
            if (b.passengerId == passengerId)
                count++;
        }

        Booking[] result = new Booking[count];
        int idx = 0;
        for (Booking b : all) {
            if (b.passengerId == passengerId) {
                result[idx++] = b;
            }
        }
        return result;
    }

    public void deleteBooking(int bookingId) {
        Booking[] all = getAllBookings();
        for (int i = 0; i < all.length; i++) {
            if (all[i].bookingId == bookingId) {
                bookingList.remove(i);
                System.out.println("Booking with ID " + bookingId + " deleted.");
                return;
            }
        }
        System.out.println("Booking with ID " + bookingId + " not found.");
    }
}
