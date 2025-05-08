package airline.service;

import airline.model.Flight;

import java.util.Scanner;

public class PaymentService {
    private Scanner sc = new Scanner(System.in);

    public String selectPaymentMode() {
        System.out.println("Select Payment Mode:");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Net Banking");
        int choice = sc.nextInt();
        sc.nextLine();  // Consume leftover newline
        switch (choice) {
            case 1: return "Cash";
            case 2: return "UPI";
            case 3: return "Net Banking";
            default: return "Cash";
        }
    }

    public void processTicketPayment(Flight flight, int bookingId, int amount) {
        String mode = selectPaymentMode();
        switch (mode) {
            case "Cash":
                System.out.println("Please pay ₹" + amount + " in cash at the counter.");
                break;
            case "UPI":
                System.out.print("Enter UPI ID: ");
                String upiId = sc.nextLine();
                System.out.print("Enter UPI PIN: ");
                String upiPin = sc.nextLine();
                System.out.println("Processing UPI payment...");
                break;
            case "Net Banking":
                System.out.print("Enter Bank Account Number: ");
                String accNo = sc.nextLine();
                System.out.print("Enter Net Banking PIN: ");
                String netPin = sc.nextLine();
                System.out.println("Processing Net Banking payment...");
                break;
            default:
                System.out.println("Invalid payment method. Defaulting to Cash.");
        }

        System.out.println("\n🎟️  Ticket Payment Successful!");
        System.out.println("-------------------------------------------------");
        System.out.println(" Booking ID     : " + bookingId);
        System.out.println(" Flight Name    : " + flight.flightName);
        System.out.println(" Source         : " + flight.source);
        System.out.println(" Destination    : " + flight.destination);
        System.out.println(" Departure Time : " + flight.departureTime);
        System.out.println(" Arrival Time   : " + flight.arrivalTime);
        System.out.println(" Journey Date   : " + flight.getDate());
        System.out.println(" Amount Paid    : ₹" + amount);
        System.out.println(" Payment Mode   : " + mode);
        System.out.println("-------------------------------------------------");
    }
}
