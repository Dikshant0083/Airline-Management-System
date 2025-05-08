package airline.model;

public class Passenger {
    public int passengerId;
    public String name;
    public String email;
    public int age;
    public String gender;
    public String phone;

    public Passenger(int id, String name, String email, int age, String gender, String phone) {
        this.passengerId = id;
        this.name = name;
        this.email = email;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }

    public void displayPassenger() {
        System.out.println("Passenger ID: " + passengerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Age: " + age);
        System.out.println("Gender(M/F): " + gender);
        System.out.println("Phone: " + phone);
        System.out.println("----------------------------------");
    }
}
