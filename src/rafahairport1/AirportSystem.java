package rafahairport1;

import java.time.LocalDateTime;
import java.util.*;

public class AirportSystem {

    private Airport airport;
    private Scanner scanner;

    //Constructor 
    public AirportSystem() {
        this.airport = new Airport("Rafah International Airport", "RAF", "Rafah");
        this.scanner = new Scanner(System.in);

        // Load data from files
        FileHandler.loadAll(airport);

        // Initialize sample data if no data created
        if (airport.getGates().isEmpty()) {
            initializeSampleData();
        }
    }

    // Initialization the  initial data
    private void initializeSampleData() {
        System.out.println("Initializing sample data");

        // Sample Gates
        airport.addGate(new Gate("A1", "Terminal 1", 200));
        airport.addGate(new Gate("A2", "Terminal 1", 150));
        airport.addGate(new Gate("B1", "Terminal 2", 180));
        airport.addGate(new Gate("B2", "Terminal 2", 120));

        // Sample Pilots
        airport.addEmployee(new Pilot("P001", "Captain Ahmed", "+970599123456",
                15000, "PIL-001", 1500));
        airport.addEmployee(new Pilot("P002", "Captain Mariam", "+970599123457",
                14000, "PIL-002", 1200));

        // Sample Flights
        Flight f1 = new Flight("RAF101", "Cairo", "Rafah",
                LocalDateTime.now().plusHours(2),
                LocalDateTime.now().plusHours(4), 200);

        Flight f2 = new Flight("RAF102", "Dubai", "Rafah",
                LocalDateTime.now().plusHours(6),
                LocalDateTime.now().plusHours(10), 150);

        airport.addFlight(f1);
        airport.addFlight(f2);

        // Assign gate to first flight
        Gate gate = airport.findAvailableGate();
        if (gate != null) {
            gate.assignFlight(f1);
        }

        // Save sample data
        FileHandler.saveAll(airport);
    }

    // Main Loop 
    public void start() {
        int choice;
        do {
            showMainMenu();
            choice = getIntInput("Enter your choice: ");

            try {
                switch (choice) {
                    case 1:
                        manageFlights();
                        break;
                    case 2:
                        managePassengers();
                        break;
                    case 3:
                        manageGates();
                        break;
                    case 4:
                        manageEmployees();
                        break;
                    case 5:
                        viewAirportStatus();
                        break;
                    case 6:
                        saveData();
                        break;
                    case 7:
                        bookFlight();
                        break;
                    case 0:
                        saveData();
                        System.out.println("Exiting system.");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (choice != 0);
    }

    // Menu Display
    private void showMainMenu() {
        System.out.println("\n======================================================");
        System.out.println("    RAFAH International Airport Management System");
        System.out.println("=======================================================");
        System.out.println("1- Manage Flights");
        System.out.println("2- Manage Passengers");
        System.out.println("3- Manage Gates");
        System.out.println("4- Manage Employees");
        System.out.println("5- View Airport Status");
        System.out.println("6- Save Data");
        System.out.println("7- Book a Flight (JavaFX)");
        System.out.println("0- Exit");
        System.out.println("=========================================================");
    }

    private void bookFlight() {
        System.out.println("\n Opening booking window");
        System.out.println("Please fill in the details in the window.");
        System.out.println("if you want to return to consol Close the window.\n");

        String result = BookingView.showBookingWindow(this);

        System.out.println("\n" + result);
        System.out.println("\n Booking ticket completed");
    }

    // Flight Management 
    private void manageFlights() {
        int choice;
        do {
            System.out.println("\n======= Flight Management =======");
            System.out.println("1- Add Flight");
            System.out.println("2- View All Flights");
            System.out.println("3- Search Flight");
            System.out.println("4- Update Flight Status");
            System.out.println("5- Cancel Flight");
            System.out.println("6- Assign Gate to Flight");
            System.out.println("7- Assign Pilot to Flight");
            System.out.println("0- Back to Main Menu");

            choice = getIntInput("Enter your Choice: ");

            switch (choice) {
                case 1:
                    addFlight();
                    break;
                case 2:
                    viewAllFlights();
                    break;
                case 3:
                    try {
                        searchFlight();
                    } catch (InvalidFlightException e) {
                        System.out.println(e.getMessage());
                        System.out.println("   Flight: " + e.getFlightNumber());
                    }
                    break;
                case 4:
                    try {
                        updateFlightStatus();
                    } catch (InvalidFlightException e) {
                        System.out.println(e.getMessage());
                        System.out.println("   Flight: " + e.getFlightNumber());
                    }
                    break;
                case 5:
                    try {
                        cancelFlight();
                    } catch (InvalidFlightException e) {
                        System.out.println(e.getMessage());
                        System.out.println("   Flight: " + e.getFlightNumber());
                    }
                    break;
                case 6:
                    try {
                        assignGateToFlight();
                    } catch (InvalidFlightException e) {
                        System.out.println(e.getMessage());
                        System.out.println("   Flight: " + e.getFlightNumber());
                    }
                    break;
                case 7:
                    try {
                        assignPilotToFlight();
                    } catch (InvalidFlightException e) {
                        System.out.println(e.getMessage());
                        System.out.println("   Flight: " + e.getFlightNumber());
                    }
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void addFlight() {
        try {
            System.out.print("Flight Number: ");
            String flightNumber = scanner.nextLine().trim().toUpperCase();

            if (airport.findFlight(flightNumber) != null) {
                System.out.println("Flight already exists!");
                return;
            }

            System.out.print("Destination: ");
            String destination = scanner.nextLine().trim();
            System.out.print("Origin: ");
            String origin = scanner.nextLine().trim();

            System.out.print("Departure (format: YYYY-MM-DDTHH:MM, e.g., 2026-07-15T22:00): ");
            LocalDateTime departure = LocalDateTime.parse(scanner.nextLine().trim());
            System.out.print("Arrival (format: YYYY-MM-DDTHH:MM, e.g., 2026-07-16T02:00): ");
            LocalDateTime arrival = LocalDateTime.parse(scanner.nextLine().trim());

            if (departure.isBefore(LocalDateTime.now())) {
                System.out.println("Departure time cannot be in the past!");
                System.out.println("   Current time: " + LocalDateTime.now());
                System.out.println("   Departure time: " + departure);
                return;
            }

            if (arrival.isBefore(LocalDateTime.now())) {
                System.out.println("Arrival time cannot be in the past!");
                System.out.println("   Current time: " + LocalDateTime.now());
                System.out.println("   Arrival time: " + arrival);
                return;
            }

            if (!arrival.isAfter(departure)) {
                if (arrival.equals(departure)) {
                    System.out.println(" Arrival time cannot be the same as departure time!");
                    System.out.println("   Both times are: " + departure);
                } else {
                    System.out.println(" Arrival time must be after departure time!");
                    System.out.println("   Departure: " + departure);
                    System.out.println("   Arrival: " + arrival);
                }
                return;
            }
            System.out.print("Max Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());

            Flight flight = new Flight(flightNumber, destination, origin, departure, arrival, capacity);

            // البحث عن بوابة متاحة بسعة كافية للرحلة
            Gate gate = airport.findAvailableGate(capacity);
            if (gate != null) {
                gate.assignFlight(flight);
                System.out.println("Assigned Gate: " + gate.getGateNumber() + " (Gate Capacity: " + gate.getCapacity() + ")");
            } else {
                // إذا لم توجد بوابة بسعة كافية، حاول إيجاد أي بوابة متاحة
                Gate anyGate = airport.findAvailableGate();
                if (anyGate != null) {
                    System.out.println("Warning: No gate with sufficient capacity found!");
                    System.out.println("   Required capacity: " + capacity);
                    System.out.println("   Available gate: " + anyGate.getGateNumber() + " (Capacity: " + anyGate.getCapacity() + ")");
                    System.out.print("   Do you want to assign this gate anyway? (y/n): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("y")) {
                        anyGate.assignFlight(flight);
                        System.out.println("Assigned Gate: " + anyGate.getGateNumber() + " (Capacity: " + anyGate.getCapacity() + ")");
                    } else {
                        System.out.println("No suitable gate assigned.");
                    }
                } else {
                    System.out.println("No available gates.");
                }
            }

            airport.addFlight(flight);
            System.out.println("Flight added successfully");

        } catch (Exception e) {
            System.out.println("Error adding flight: " + e.getMessage());
        }
    }

    private void viewAllFlights() {
        System.out.println("\n======= All Flights =======");
        List<Flight> flights = airport.getFlights();

        if (flights.isEmpty()) {
            System.out.println("No flights available.");
            return;
        }

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-12s %-12s %-15s %-10s %-10s %-10s%n",
                "Flight", "Origin", "Destination", "Departure", "Status", "Passengers", "Gate");
        System.out.println("----------------------------------------------------------------------------------------------------");

        for (Flight f : flights) {
            String gateInfo = f.getGate() != null ? f.getGate().getGateNumber() : "None";

            System.out.printf("%-12s %-12s %-12s %-15s %-10s %-10d %-10s%n",
                    f.getFlightNumber(),
                    f.getOrigin(),
                    f.getDestination(),
                    f.getDeparture().toLocalDate(),
                    f.getStatus(),
                    f.getPassengerCount(),
                    gateInfo);
        }
        System.out.println("----------------------------------------------------------------------------------------------------");
    }

    private void searchFlight() throws InvalidFlightException {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight != null) {
            System.out.println("\n==== Flight Details ====");
            System.out.println("Flight Number: " + flight.getFlightNumber());
            System.out.println("Origin: " + flight.getOrigin());
            System.out.println("Destination: " + flight.getDestination());
            System.out.println("Departure: " + flight.getDeparture());
            System.out.println("Arrival: " + flight.getArrival());
            System.out.println("Status: " + flight.getStatus());
            System.out.println("Passengers: " + flight.getPassengerCount() + "/" + flight.getCapacity());
            System.out.println("Gate: " + (flight.getGate() != null ? flight.getGate().getGateNumber() : "Not assigned"));
            System.out.println("Pilot: " + (flight.getPilot() != null ? flight.getPilot().getName() : "Not assigned"));
        } else {
            throw new InvalidFlightException("Flight not found!", flightNumber);
        }
    }

    private void updateFlightStatus() throws InvalidFlightException {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight == null) {
            throw new InvalidFlightException("Flight not found!", flightNumber);
        }

        System.out.println("Current Status: " + flight.getStatus());
        System.out.println("Select new status:");
        Flight.FlightStatus[] statuses = Flight.FlightStatus.values();
        for (int i = 0; i < statuses.length; i++) {
            System.out.println((i + 1) + ". " + statuses[i]);
        }

        int choice = getIntInput("Choice: ");
        if (choice >= 1 && choice <= statuses.length) {
            Flight.FlightStatus newStatus = statuses[choice - 1];

            if (newStatus == Flight.FlightStatus.CANCELLED) {
                int passengerCount = flight.getPassengerCount();
                if (passengerCount > 0) {
                    System.out.print("This flight has " + passengerCount + " passengers. ");
                    System.out.print("Are you sure you want to cancel and remove all passengers? (y/n): ");
                    String confirm = scanner.nextLine().trim().toLowerCase();
                    if (confirm.equals("y")) {
                        flight.clearPassengers();
                        System.out.println(" All " + passengerCount + " passengers have been removed.");
                    } else {
                        System.out.println("Cancellation aborted.");
                        return;
                    }
                }

                if (flight.getGate() != null) {
                    flight.getGate().release();
                    System.out.println("Gate " + flight.getGate().getGateNumber() + " has been released.");
                }
            }

            flight.setStatus(newStatus);
            System.out.println(" Status updated successfully to: " + newStatus);
        } else {
            System.out.println(" Invalid choice.");
        }
    }

    private void cancelFlight() throws InvalidFlightException {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight == null) {
            throw new InvalidFlightException("Flight not found!", flightNumber);
        }

        if (flight.getStatus() == Flight.FlightStatus.CANCELLED) {
            System.out.println("This flight is already cancelled.");
            return;
        }

        int passengerCount = flight.getPassengerCount();
        if (passengerCount > 0) {
            System.out.println("This flight has " + passengerCount + " passengers.");
            System.out.print("Are you sure you want to cancel and remove all passengers? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println(" Cancellation aborted.");
                return;
            }
        } else {
            System.out.print("Are you sure you want to cancel this flight? (y/n): ");
            String confirm = scanner.nextLine().trim().toLowerCase();
            if (!confirm.equals("y")) {
                System.out.println(" Cancellation aborted.");
                return;
            }
        }

        if (passengerCount > 0) {
            flight.clearPassengers();
            System.out.println(" All " + passengerCount + " passengers have been removed.");
        }

        if (flight.getGate() != null) {
            flight.getGate().release();
            System.out.println(" Gate " + flight.getGate().getGateNumber() + " has been released.");
        }

        flight.setStatus(Flight.FlightStatus.CANCELLED);
        System.out.println(" Flight " + flightNumber + " cancelled successfully!");
    }

    private void assignGateToFlight() throws InvalidFlightException {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight == null) {
            throw new InvalidFlightException("Flight not found!", flightNumber);
        }

        if (flight.getGate() != null) {
            System.out.println("Flight already has gate: " + flight.getGate().getGateNumber());
            return;
        }

        // البحث عن بوابة متاحة بسعة كافية
        Gate gate = airport.findAvailableGate(flight.getCapacity());

        if (gate != null) {
            gate.assignFlight(flight);
            System.out.println("Gate " + gate.getGateNumber() + " assigned to flight " + flightNumber);
            System.out.println("   Gate capacity: " + gate.getCapacity() + ", Flight capacity: " + flight.getCapacity());
        } else {
            // إذا لم توجد بوابة بسعة كافية
            Gate anyGate = airport.findAvailableGate();
            if (anyGate != null) {
                System.out.println("Warning: No gate with sufficient capacity!");
                System.out.println("   Required: " + flight.getCapacity() + ", Available: " + anyGate.getCapacity());
                System.out.print("   Assign this gate anyway? (y/n): ");
                String confirm = scanner.nextLine().trim().toLowerCase();
                if (confirm.equals("y")) {
                    anyGate.assignFlight(flight);
                    System.out.println("Assigned Gate: " + anyGate.getGateNumber());
                } else {
                    System.out.println("Assignment cancelled.");
                }
            } else {
                System.out.println("No available gates.");
            }
        }
    }

    private void assignPilotToFlight() throws InvalidFlightException {
        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight == null) {
            throw new InvalidFlightException("Flight not found!", flightNumber);
        }

        System.out.print("Enter pilot ID: ");
        String pilotId = scanner.nextLine().trim().toUpperCase();
        Employee employee = airport.findEmployee(pilotId);

        if (!(employee instanceof Pilot)) {
            if (employee == null) {
                System.out.println(" Employee not found.");
            } else {
                System.out.println(" Employee is not a pilot.");
            }
            return;
        }

        Pilot pilot = (Pilot) employee;

        if (flight.getPilot() != null && flight.getPilot().equals(pilot)) {
            System.out.println("This pilot is already assigned to this flight.");
            return;
        }

        if (!airport.isPilotAvailable(pilot, flight.getDeparture(), flight.getArrival())) {
            System.out.println("Pilot " + pilot.getName() + " is already assigned to another flight at this time!");
            System.out.println("   Departure: " + flight.getDeparture());
            System.out.println("   Arrival: " + flight.getArrival());
            return;
        }

        flight.setPilot(pilot);
        System.out.println(" Pilot " + pilot.getName() + " assigned to flight " + flightNumber + " successfully!");
    }

    // Passenger Management 
    private void managePassengers() {
        int choice;
        do {
            System.out.println("\n======= Passenger Management =======");
            System.out.println("1- Register Passenger");
            System.out.println("2- View All Passengers");
            System.out.println("3- Search Passenger");
            System.out.println("4- Check-in Passenger");
            System.out.println("5- Delete Passenger");
            System.out.println("0- Back to Main Menu");

            choice = getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    registerPassenger();
                    break;
                case 2:
                    viewAllPassengers();
                    break;
                case 3:
                    searchPassenger();
                    break;
                case 4:

                case 5:
                    deletePassenger();
                    checkInPassenger();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void registerPassenger() {
        try {
            System.out.print("Passport Number: ");
            String passport = scanner.nextLine().trim().toUpperCase();

            if (airport.findPassenger(passport) != null) {
                System.out.println("Passenger already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Nationality: ");
            String nationality = scanner.nextLine().trim();
            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            Passenger passenger = new Passenger(passport, name, nationality, phone, email);
            airport.addPassenger(passenger);
            System.out.println("Passenger registered successfully!");

        } catch (Exception e) {
            System.out.println("Error registering passenger: " + e.getMessage());
        }
    }

    private void viewAllPassengers() {
        System.out.println("\n=== All Passengers ===");
        List<Passenger> passengers = airport.getPassengers();

        if (passengers.isEmpty()) {
            System.out.println("No passengers registered.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-20s %-12s %-15s %-15s %-12s%n",
                "Passport", "Name", "Nationality", "Phone", "Flight", "Status");
        System.out.println("------------------------------------------------------------------------------------------------------------------");

        for (Passenger p : passengers) {
            String flightNumber = getPassengerFlight(p);
            String status = p.getBoardingPass() != null ? "Checked-in" : "Registered";

            System.out.printf("%-12s %-20s %-12s %-15s %-15s %-12s%n",
                    p.getPassportNumber(),
                    p.getName().length() > 20 ? p.getName().substring(0, 17) + "..." : p.getName(),
                    p.getNationality(),
                    p.getPhone(),
                    flightNumber,
                    status);
        }
        System.out.println("------------------------------------------------------------------------------------------------------------------");
    }

    private String getPassengerFlight(Passenger passenger) {
        for (Flight flight : airport.getFlights()) {

            if (flight.getStatus() == Flight.FlightStatus.CANCELLED) {
                continue;
            }
            if (flight.getPassengers().contains(passenger)) {
                return flight.getFlightNumber();
            }
        }
        return "Not booked";
    }

    private void searchPassenger() {
        System.out.print("Enter passport number: ");
        String passport = scanner.nextLine().trim().toUpperCase();
        Passenger passenger = airport.findPassenger(passport);

        if (passenger != null) {
            System.out.println("\n==== Passenger Details ====");
            System.out.println("Name: " + passenger.getName());
            System.out.println("Passport: " + passenger.getPassportNumber());
            System.out.println("Nationality: " + passenger.getNationality());
            System.out.println("Phone: " + passenger.getPhone());
            System.out.println("Email: " + passenger.getEmail());
            if (passenger.getBoardingPass() != null) {
                System.out.println("Boarding Pass: " + passenger.getBoardingPass());
            }
        } else {
            System.out.println("Passenger not found.");
        }
    }

    private void deletePassenger() {
        System.out.print("Enter passenger passport number to delete: ");
        String passport = scanner.nextLine().trim().toUpperCase();

        Passenger passenger = airport.findPassenger(passport);
        if (passenger == null) {
            System.out.println("Passenger not found!");
            return;
        }

        System.out.println("Passenger details:");
        System.out.println("   Name: " + passenger.getName());
        System.out.println("   Passport: " + passenger.getPassportNumber());
        System.out.println("   Nationality: " + passenger.getNationality());
        System.out.println("   Phone: " + passenger.getPhone());
        System.out.println("   Email: " + passenger.getEmail());

        boolean isBooked = false;
        for (Flight flight : airport.getFlights()) {
            if (flight.getPassengers().contains(passenger)) {
                isBooked = true;
                System.out.println("  Booked on flight: " + flight.getFlightNumber());
            }
        }

        if (isBooked) {
            System.out.println("Cannot delete passenger. Passenger is booked on one or more flights.");
            System.out.print("   Do you want to remove the passenger from the flight first? (y/n): ");
            String removeConfirm = scanner.nextLine().trim().toLowerCase();
            if (removeConfirm.equals("y")) {
                for (Flight flight : airport.getFlights()) {
                    if (flight.getPassengers().contains(passenger)) {
                        flight.removePassenger(passenger);
                        System.out.println("   Removed from flight: " + flight.getFlightNumber());
                    }
                }
                boolean deleted = airport.removePassenger(passenger);
                if (deleted) {
                    System.out.println("Passenger " + passenger.getName() + " deleted successfully!");
                    saveData();
                }
            } else {
                System.out.println("Deletion cancelled.");
            }
            return;
        }

        System.out.print("Are you sure you want to delete this passenger? (y/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("y")) {
            boolean deleted = airport.removePassenger(passenger);
            if (deleted) {
                System.out.println("Passenger " + passenger.getName() + " deleted successfully!");
                saveData();
            } else {
                System.out.println("Failed to delete passenger.");
            }
        } else {
            System.out.println("Deletion cancelled.");
        }
    }

    private void checkInPassenger() {
        System.out.print("Enter passenger passport: ");
        String passport = scanner.nextLine().trim().toUpperCase();
        Passenger passenger = airport.findPassenger(passport);

        if (passenger == null) {
            System.out.println("Passenger not found.");
            return;
        }

        System.out.print("Enter flight number: ");
        String flightNumber = scanner.nextLine().trim().toUpperCase();
        Flight flight = airport.findFlight(flightNumber);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        if (flight.getStatus() == Flight.FlightStatus.DEPARTED
                || flight.getStatus() == Flight.FlightStatus.ARRIVED) {
            System.out.println("Cannot check-in. Flight has already departed or arrived.");
            return;
        }

        if (flight.addPassenger(passenger)) {
            String bpId = "BP" + System.currentTimeMillis();
            int seatNumber = flight.getPassengerCount() + 10;
            passenger.setBoardingPass(bpId + "-" + seatNumber);

            System.out.println("Check-in successful!");
            System.out.println("Boarding Pass: " + passenger.getBoardingPass());
            System.out.println("Flight: " + flight.getFlightNumber());
            System.out.println("Seat: " + seatNumber);
        } else {
            System.out.println("Flight is full. Cannot check-in.");
        }
    }

    // Gate Management 
    private void manageGates() {
        int choice;
        do {
            System.out.println("\n======= Gate Management =======");
            System.out.println("1- View All Gates");
            System.out.println("2- Add Gate");
            System.out.println("3- Release Gate");
            System.out.println("0- Back to Main Menu");

            choice = getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    viewAllGates();
                    break;
                case 2:
                    addGate();
                    break;
                case 3:
                    releaseGate();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void viewAllGates() {
        System.out.println("\n======= All Gates =======");
        List<Gate> gates = airport.getGates();

        if (gates.isEmpty()) {
            System.out.println("No gates available.");
            return;
        }

        System.out.println("------------------------------------------------------------------------------------------");
        System.out.printf("%-12s %-15s %-12s %-15s %-12s%n",
                "Gate", "Terminal", "Available", "Current Flight", "Capacity");
        System.out.println("------------------------------------------------------------------------------------------");

        int availableCount = 0;
        int occupiedCount = 0;

        for (Gate g : gates) {
            String availability = g.isAvailable() ? " Yes" : " No";
            String flightInfo = g.getCurrentFlight() != null ? g.getCurrentFlight().getFlightNumber() : "None";

            System.out.printf("%-12s %-15s %-12s %-15s %-12d%n",
                    g.getGateNumber(),
                    g.getTerminal(),
                    availability,
                    flightInfo,
                    g.getCapacity());

            if (g.isAvailable()) {
                availableCount++;
            } else {
                occupiedCount++;
            }
        }
        System.out.println("------------------------------------------------------------------------------------------");
        System.out.println("Summary: " + availableCount + " available, " + occupiedCount + " occupied");
        System.out.println("   Total gates: " + gates.size());
        System.out.println("------------------------------------------------------------------------------------------");
    }

    private void addGate() {
        try {
            System.out.print("Gate Number: ");
            String gateNumber = scanner.nextLine().trim().toUpperCase();

            //  التحقق من وجود بوابة بنفس الرقم
            for (Gate g : airport.getGates()) {
                if (g.getGateNumber().equals(gateNumber)) {
                    System.out.println(" Gate " + gateNumber + " already exists!");
                    return;
                }
            }

            System.out.print("Terminal: ");
            String terminal = scanner.nextLine().trim();
            System.out.print("Capacity: ");
            int capacity = Integer.parseInt(scanner.nextLine().trim());

            airport.addGate(new Gate(gateNumber, terminal, capacity));
            System.out.println(" Gate added successfully!");

        } catch (Exception e) {
            System.out.println("Error adding gate: " + e.getMessage());
        }
    }

    private void releaseGate() {
        System.out.print("Enter gate number: ");
        String gateNumber = scanner.nextLine().trim().toUpperCase();

        for (Gate gate : airport.getGates()) {
            if (gate.getGateNumber().equals(gateNumber)) {
                if (!gate.isAvailable()) {
                    gate.release();
                    System.out.println("Gate released successfully.");
                } else {
                    System.out.println("Gate is already available.");
                }
                return;
            }
        }
        System.out.println("Gate not found.");
    }

    //  Employee Management 
    private void manageEmployees() {
        int choice;
        do {
            System.out.println("\n======= Employee Management =======");
            System.out.println("1- View All Employees");
            System.out.println("2- Add Pilot");
            System.out.println("3- Search Employee");
            System.out.println("0- Back to Main Menu");

            choice = getIntInput("Choice: ");

            switch (choice) {
                case 1:
                    viewAllEmployees();
                    break;
                case 2:
                    addPilot();
                    break;
                case 3:
                    searchEmployee();
                    break;
                case 0:
                    System.out.println("Returning to Main Menu...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }

            if (choice != 0) {
                System.out.print("\nPress Enter to continue...");
                scanner.nextLine();
            }

        } while (choice != 0);
    }

    private void viewAllEmployees() {
        System.out.println("\n==== All Employees ====");
        List<Employee> employees = airport.getEmployees();

        if (employees.isEmpty()) {
            System.out.println("No employees registered.");
            return;
        }

        System.out.println("--------------------------------------------------------------------------");
        System.out.printf("%-10s %-20s %-12s %-15s %-15s%n",
                "ID", "Name", "Role", "Base Salary", "Total Salary");
        System.out.println("--------------------------------------------------------------------------");

        for (Employee e : employees) {
            double totalSalary = e.getSalary();

            if (e instanceof Pilot) {
                Pilot pilot = (Pilot) e;
                totalSalary += pilot.calculateBonus();
            }

            System.out.printf("%-10s %-20s %-12s %-15.2f %-15.2f%n",
                    e.getId(),
                    e.getName().length() > 20 ? e.getName().substring(0, 17) + "..." : e.getName(),
                    e.getRole(),
                    e.getSalary(),
                    totalSalary);
        }
        System.out.println("--------------------------------------------------------------------------");
    }

    private void addPilot() {
        try {
            System.out.print("Pilot ID: ");
            String id = scanner.nextLine().trim().toUpperCase();

            if (airport.findEmployee(id) != null) {
                System.out.println(" Employee ID already exists!");
                return;
            }

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            System.out.print("Phone: ");
            String phone = scanner.nextLine().trim();
            System.out.print("Salary: ");
            double salary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("License Number: ");
            String license = scanner.nextLine().trim().toUpperCase();

            if (isLicenseNumberExists(license)) {
                System.out.println(" License number '" + license + "' is already assigned to another pilot!");
                return;
            }

            System.out.print("Flight Hours: ");
            int hours = Integer.parseInt(scanner.nextLine().trim());

            Pilot pilot = new Pilot(id, name, phone, salary, license, hours);
            airport.addEmployee(pilot);
            System.out.println(" Pilot added successfully!");

        } catch (NumberFormatException e) {
            System.out.println(" Invalid number format. Please enter a valid number.");
        } catch (Exception e) {
            System.out.println(" Error adding pilot: " + e.getMessage());
        }
    }

    private boolean isLicenseNumberExists(String licenseNumber) {
        for (Employee e : airport.getEmployees()) {
            if (e instanceof Pilot) {
                Pilot pilot = (Pilot) e;
                if (pilot.getLicenseNumber().equalsIgnoreCase(licenseNumber)) {
                    System.out.println("   This license belongs to: " + pilot.getName() + " (ID: " + pilot.getId() + ")");
                    return true;
                }
            }
        }
        return false;
    }

    private void searchEmployee() {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine().trim().toUpperCase();
        Employee employee = airport.findEmployee(id);

        if (employee != null) {
            System.out.println("\n===== Employee Details =====");
            System.out.println("ID: " + employee.getId());
            System.out.println("Name: " + employee.getName());
            System.out.println("Role: " + employee.getRole());
            System.out.println("Phone: " + employee.getPhone());
            System.out.println("Base Salary: " + employee.getSalary());
            System.out.println("Hire Date: " + employee.getHireDate());

            if (employee instanceof Pilot) {
                Pilot pilot = (Pilot) employee;
                System.out.println("License: " + pilot.getLicenseNumber());
                System.out.println("Flight Hours: " + pilot.getFlightHours());
                double bonus = pilot.calculateBonus();
                System.out.println("Bonus: " + bonus);
                System.out.println("Total Salary (with bonus): " + (employee.getSalary() + bonus));
            }
        } else {
            System.out.println("Employee not found.");
        }
    }

    // Airport Status 
    private void viewAirportStatus() {
        System.out.println("\n===== Airport Status =====");
        System.out.println("Airport: " + airport.getName() + " (" + airport.getCode() + ")");
        System.out.println("City: " + airport.getCity());
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Total Flights: " + airport.getFlights().size());
        System.out.println("  - Scheduled: " + airport.getFlightsByStatus(Flight.FlightStatus.SCHEDULED).size());
        System.out.println("  - Boarding: " + airport.getFlightsByStatus(Flight.FlightStatus.BOARDING).size());
        System.out.println("  - Departed: " + airport.getFlightsByStatus(Flight.FlightStatus.DEPARTED).size());
        System.out.println("  - Arrived: " + airport.getFlightsByStatus(Flight.FlightStatus.ARRIVED).size());
        System.out.println("  - Cancelled: " + airport.getFlightsByStatus(Flight.FlightStatus.CANCELLED).size());
        System.out.println("  - Delayed: " + airport.getFlightsByStatus(Flight.FlightStatus.DELAYED).size());
        System.out.println("--------------------------------------------------------------------------");
        System.out.println("Total Passengers: " + airport.getPassengers().size());
        System.out.println("Total Gates: " + airport.getGates().size());

        long availableGates = airport.getGates().stream().filter(Gate::isAvailable).count();
        System.out.println("Available Gates: " + availableGates);

        System.out.println("Total Employees: " + airport.getEmployees().size());
        int activeFlights = 0;
        for (Flight f : airport.getFlights()) {
            if (f.getStatus() != Flight.FlightStatus.CANCELLED) {
                activeFlights++;
            }
        }
        System.out.println("Total Active Flights: " + activeFlights);
        System.out.println("Total Flights Scheduled (All Flights in the system without consider ststues ): " + Airport.getTotalFlights());
        System.out.println("--------------------------------------------------------------------------");
    }

    // Data Management 
    public void saveData() {
        FileHandler.saveAll(airport);
    }

    // Helper Methods 
    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    //  GUI Helper Methods (BookingView)
    public List<Flight> getAllFlights() {
        return airport.getFlights();
    }

    public List<Passenger> getAllPassengers() {
        return airport.getPassengers();
    }

    public List<Gate> getAllGates() {
        return airport.getGates();
    }

    public List<Employee> getAllEmployees() {
        return airport.getEmployees();
    }

    public List<String> getAllFlightNumbers() {
        List<String> numbers = new ArrayList<>();
        for (Flight flight : airport.getFlights()) {
            numbers.add(flight.getFlightNumber());
        }
        return numbers;
    }

    public Flight findFlight(String flightNumber) {
        return airport.findFlight(flightNumber);
    }

    public Passenger findPassenger(String passport) {
        return airport.findPassenger(passport);
    }

    public Employee findEmployee(String employeeId) {
        return airport.findEmployee(employeeId);
    }

    public void registerPassenger(String passport, String name, String nationality,
            String phone, String email) {
        Passenger passenger = new Passenger(passport, name, nationality, phone, email);
        airport.addPassenger(passenger);
    }

    public boolean checkInPassenger(String passport, String flightNumber) {
        Passenger passenger = airport.findPassenger(passport);
        Flight flight = airport.findFlight(flightNumber);

        if (passenger == null || flight == null) {
            return false;
        }

        return flight.addPassenger(passenger);
    }

    public void addFlight(Flight flight) {
        airport.addFlight(flight);
    }

    public void addGate(Gate gate) {
        airport.addGate(gate);
    }

    public void addEmployee(Employee employee) {
        airport.addEmployee(employee);
    }

    public void updateFlightStatus(String flightNumber, Flight.FlightStatus status) {
        Flight flight = airport.findFlight(flightNumber);
        if (flight != null) {
            flight.setStatus(status);
        }
    }

    public void cancelFlight(String flightNumber) {
        Flight flight = airport.findFlight(flightNumber);
        if (flight != null) {
            flight.setStatus(Flight.FlightStatus.CANCELLED);
            if (flight.getGate() != null) {
                flight.getGate().release();
            }
        }
    }

    public void deleteFlight(String flightNumber) {
        Flight flight = airport.findFlight(flightNumber);
        if (flight != null) {
            if (flight.getGate() != null) {
                flight.getGate().release();
            }
            airport.removeFlight(flight);
        }
    }

    public boolean releaseGate(String gateNumber) {
        for (Gate gate : airport.getGates()) {
            if (gate.getGateNumber().equals(gateNumber) && !gate.isAvailable()) {
                gate.release();
                return true;
            }
        }
        return false;
    }

    public String getAirportStatus() {
        StringBuilder sb = new StringBuilder();
        sb.append("Airport: ").append(airport.getName()).append("\n");
        sb.append("City: ").append(airport.getCity()).append("\n");
        sb.append("---\n");
        sb.append("Total Flights: ").append(airport.getFlights().size()).append("\n");
        sb.append("Total Passengers: ").append(airport.getPassengers().size()).append("\n");
        sb.append("Total Gates: ").append(airport.getGates().size()).append("\n");
        sb.append("Total Employees: ").append(airport.getEmployees().size()).append("\n");
        return sb.toString();
    }

    public List<Gate> getAvailableGates() {
        List<Gate> availableGates = new ArrayList<>();
        for (Gate gate : airport.getGates()) {
            if (gate.isAvailable()) {
                availableGates.add(gate);
            }
        }
        return availableGates;
    }
}
