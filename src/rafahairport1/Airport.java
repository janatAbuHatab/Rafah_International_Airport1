package rafahairport1;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Airport {

    private String name;
    private String code;
    private String city;
    private List<Flight> flights;
    private List<Gate> gates;
    private List<Employee> employees;
    private List<Passenger> passengers;
    private static int totalFlights = 0;

    public Airport(String name, String code, String city) {
        this.name = name;
        this.code = code;
        this.city = city;
        this.flights = new ArrayList<>();
        this.gates = new ArrayList<>();
        this.employees = new ArrayList<>();
        this.passengers = new ArrayList<>();
    }

    // Getters 
    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getCity() {
        return city;
    }

    public List<Flight> getFlights() {
        return flights;
    }

    public List<Gate> getGates() {
        return gates;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public static int getTotalFlights() {
        return totalFlights;
    }

    // Add Methods 
    public void addFlight(Flight flight) {
        flights.add(flight);
        totalFlights++;
    }

    public void addGate(Gate gate) {
        gates.add(gate);
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addPassenger(Passenger passenger) {
        passengers.add(passenger);
    }

    // Remove Methods 
    public boolean removeFlight(Flight flight) {
        if (flight != null && flights.remove(flight)) {
            totalFlights--;
            return true;
        }
        return false;
    }

    public boolean removeFlight(String flightNumber) {
        Flight flight = findFlight(flightNumber);
        if (flight != null) {
            return removeFlight(flight);
        }
        return false;
    }

    //  Search Methods 
    public Flight findFlight(String flightNumber) {
        for (Flight f : flights) {
            if (f.getFlightNumber().equals(flightNumber)) {
                return f;
            }
        }
        return null;
    }

    //  البجث عن بوابة متاحة دون شروط
    public Gate findAvailableGate() {
        for (Gate g : gates) {
            if (g.isAvailable()) {
                return g;
            }
        }
        return null;
    }

    // دالة البحث عن بوابة متاحة بشرط ان تكون سعتها ملائمة للرحلة المسندة
    public Gate findAvailableGate(int requiredCapacity) {
        for (Gate gate : gates) {
            if (gate.isAvailable() && gate.getCapacity() >= requiredCapacity) {
                return gate;
            }
        }
        return null;
    }

    public Employee findEmployee(String employeeId) {
        for (Employee e : employees) {
            if (e.getId().equals(employeeId)) {
                return e;
            }
        }
        return null;
    }

    public Passenger findPassenger(String passport) {
        for (Passenger p : passengers) {
            if (p.getPassportNumber().equals(passport)) {
                return p;
            }
        }
        return null;
    }

    //  دوال مساعدة
    public List<Flight> getFlightsByStatus(Flight.FlightStatus status) {
        List<Flight> result = new ArrayList<>();
        for (Flight f : flights) {
            if (f.getStatus() == status) {
                result.add(f);
            }
        }
        return result;
    }

    public boolean isPilotAvailable(Pilot pilot, LocalDateTime departure, LocalDateTime arrival) {
        for (Flight flight : flights) {
            if (flight.getStatus() == Flight.FlightStatus.CANCELLED) {
                continue;
            }
            if (flight.getPilot() != null && flight.getPilot().equals(pilot)) {
                boolean isOverlapping = !(arrival.isBefore(flight.getDeparture())
                        || departure.isAfter(flight.getArrival()));
                if (isOverlapping) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "Airport[" + name + ", " + city
                + ", Flights=" + flights.size()
                + ", Gates=" + gates.size() + "]";
    }
}
