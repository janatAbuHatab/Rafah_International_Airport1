package rafahairport1;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Flight implements Comparable<Flight> {

    // Enum لتفادي مشكلة الادخال الخاطئ وحصر الخيارات 
    public enum FlightStatus {
        SCHEDULED, BOARDING, DEPARTED, ARRIVED, CANCELLED, DELAYED
    }

    private String flightNumber;
    private String destination;
    private String origin;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private FlightStatus status;
    private int capacity;
    private Gate gate;          // Association 
    private Pilot pilot;        // Composition
    private List<Passenger> passengers;  // Composition

    public Flight(String flightNumber, String destination, String origin, LocalDateTime departure, LocalDateTime arrival, int capacity) {
        this.flightNumber = flightNumber;
        this.destination = destination;
        this.origin = origin;
        this.departure = departure;
        this.arrival = arrival;
        this.capacity = capacity;
        this.status = FlightStatus.SCHEDULED;
        this.passengers = new ArrayList<>();
    }

    //  Getters 
    public String getFlightNumber() {
        return flightNumber;
    }

    public String getDestination() {
        return destination;
    }

    public String getOrigin() {
        return origin;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

    public Gate getGate() {
        return gate;
    }

    public Pilot getPilot() {
        return pilot;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public int getPassengerCount() {
        return passengers.size();
    }

    public int getAvailableSeats() {
        return capacity - passengers.size();
    }

    // Setters
    public void setStatus(FlightStatus status) {
        if (status != null) {
            this.status = status;
        }
    }

    public void setGate(Gate gate) {
        this.gate = gate;
        if (gate != null) {
            gate.assignFlight(this);
        }
    }

    public void setPilot(Pilot pilot) {
        this.pilot = pilot;
    }

    // Passenger Management 
    public boolean addPassenger(Passenger passenger) {
        if (passenger != null && passengers.size() < capacity) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }

    public void clearPassengers() {

        List<Passenger> passengersToRemove = new ArrayList<>(passengers);

        for (Passenger p : passengersToRemove) {
            p.setBoardingPass(null); // إلغاء بطاقة الصعود
            removePassenger(p);
        }
    }

    //Implement Comparable interface
    @Override
    public int compareTo(Flight other) {
        return this.departure.compareTo(other.departure);
    }

    @Override
    public String toString() {
        return flightNumber + ": " + origin + " → " + destination
                + " [" + status + "] " + passengers.size() + "/" + capacity + " seats";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Flight) {
            return this.flightNumber.equals(((Flight) obj).flightNumber);
        }
        return false;
    }
}
