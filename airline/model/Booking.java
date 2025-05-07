package airline.model;

public class Booking {
    public int bookingId;
    public int flightId;
    public int passengerId;
    public int amount;
    public String bookingDate;
    public int seatsBooked;

    public Booking(int bookingId, int flightId, int passengerId, int amount, String bookingDate, int seatsBooked) {
        this.bookingId = bookingId;
        this.flightId = flightId;
        this.passengerId = passengerId;
        this.amount = amount;
        this.bookingDate = bookingDate;
        this.seatsBooked = seatsBooked;
    }

    public int getPassengerId() {
        return passengerId;
    }

    public void displayBooking() {
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Passenger ID: " + passengerId);
        System.out.println("Flight ID: " + flightId);
        System.out.println("Seats Booked: " + seatsBooked);
        System.out.println("Total Amount: ₹" + amount);
        System.out.println("Booking Date: " + bookingDate);
        System.out.println("-----------------------------");
    }
}
