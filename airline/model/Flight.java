package airline.model;

public class Flight {
    public int flightId;
    public String flightName;
    public String source;
    public String destination;
    public String departureTime;
    public String arrivalTime;
    public int price;
    public int seatsAvailable;
    private String date;

    public Flight(int id, String name, String src, String dest, String dep, String arr, int price, int seats,String date) {
        this.flightId = id;
        this.flightName = name;
        this.source = src;
        this.destination = dest;
        this.departureTime = dep;
        this.arrivalTime = arr;
        this.price = price;
        this.seatsAvailable = seats;
        this.date=date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    public void displayFlight() {
        System.out.println("Flight ID: " + flightId);
        System.out.println("Name: " + flightName);
        System.out.println("From: " + source + " To: " + destination);
        System.out.println("Date: "+date);
        System.out.println("Departure: " + departureTime + " Arrival: " + arrivalTime);
        System.out.println("Price: ₹" + price + " | Seats Available: " + seatsAvailable);
        System.out.println("----------------------------------");
    }
}
