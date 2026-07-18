package rafahairport1;

public class Pilot extends Employee implements Comparable<Pilot> {

    private String licenseNumber;
    private int flightHours;

    public Pilot(String id, String name, String phone, double salary, String licenseNumber, int flightHours) {
        super(id, name, phone, salary);
        this.licenseNumber = licenseNumber;
        this.flightHours = flightHours;
    }

    // Getters
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public int getFlightHours() {
        return flightHours;
    }

    // Setters
    public void setFlightHours(int hours) {
        if (hours >= 0) {
            this.flightHours = hours;
        }
    }
 
    // Override Abstract Methods
    @Override
    public String getRole() {
        return "Pilot";
    }

    @Override
    public double calculateBonus() {
        // 5% bonus per 100 hours, max 50%
        // اذا كانت قيمة الزيادة اكثر من قيمة نص الراتب لا يطبقها بل يطبق النصف
        double rate = Math.min((flightHours / 100.0) * 0.05, 0.50);
        return getSalary() * rate;
    }

    // Implement Comparable interface
    @Override
    public int compareTo(Pilot other) {
        return Integer.compare(this.flightHours, other.flightHours);
    }

    @Override
    public String toString() {
        return super.toString() + " - License: " + licenseNumber
                + ", Hours: " + flightHours;
    }
}
