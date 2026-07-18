package rafahairport1;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

public class FileHandler {

    private static final String DATA_DIR = "data/";
    private static final String SEPARATOR = ",";

    static {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
            System.out.println("Data directory created");
        }
    }
    // Save Flights and related gates and passengers

    public static void saveFlights(List<Flight> flights) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_DIR + "flights.txt"))) {
            for (Flight f : flights) {
                String gateNumber = f.getGate() != null ? f.getGate().getGateNumber() : "None";

                // حفظ أرقام جوازات المسافرين المسجلين بالرحلة مفصولين بفاصلة منقوطة 
                StringBuilder passengersBuilder = new StringBuilder();
                for (Passenger p : f.getPassengers()) {
                    if (passengersBuilder.length() > 0) {
                        passengersBuilder.append(";");
                    }
                    passengersBuilder.append(p.getPassportNumber());
                }
                String passengerIds = passengersBuilder.toString();

                out.println(f.getFlightNumber() + SEPARATOR
                        + f.getDestination() + SEPARATOR
                        + f.getOrigin() + SEPARATOR
                        + f.getDeparture() + SEPARATOR
                        + f.getArrival() + SEPARATOR
                        + f.getStatus() + SEPARATOR
                        + f.getCapacity() + SEPARATOR
                        + gateNumber + SEPARATOR
                        + passengerIds);
            }
            System.out.println("Saved " + flights.size() + " flights");
        } catch (IOException e) {
            System.err.println("Error saving flights: " + e.getMessage());
        }
    }

    //  Load Flights and related gates and passengers
    public static List<Flight> loadFlights(List<Gate> gates, List<Passenger> allPassengers) {
        List<Flight> flights = new ArrayList<>();
        File file = new File(DATA_DIR + "flights.txt");

        if (!file.exists()) {
            System.out.println("No flights file found");
            return flights;
        }

        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEPARATOR, -1);
                if (data.length >= 7) {
                    // creat flight
                    Flight f = new Flight(
                            data[0], // flightNumber
                            data[1], // destination
                            data[2], // origin
                            LocalDateTime.parse(data[3]), // departure
                            LocalDateTime.parse(data[4]), // arrival
                            Integer.parseInt(data[6]) // capacity
                    );
                    f.setStatus(Flight.FlightStatus.valueOf(data[5]));

                    // إعادة ربط البوابة 
                    if (data.length >= 8 && !data[7].equals("None")) {
                        String gateNumber = data[7];
                        for (Gate gate : gates) {
                            if (gate.getGateNumber().equals(gateNumber) && gate.isAvailable()) {
                                gate.assignFlight(f);
                                break;
                            }
                        }
                    }

                    // إعادة ربط المسافرين
                    if (data.length >= 9 && !data[8].isEmpty()) {
                        String[] passengerIds = data[8].split(";");
                        for (String passport : passengerIds) {
                            for (Passenger p : allPassengers) {
                                if (p.getPassportNumber().equals(passport)) {
                                    f.addPassenger(p);
                                    break;
                                }
                            }
                        }
                    }

                    flights.add(f);
                }
            }
            System.out.println("Loaded " + flights.size() + " flights");
        } catch (Exception e) {
            System.err.println("Error loading flights: " + e.getMessage());
        }
        return flights;
    }

    //  Save Passengers
    public static void savePassengers(List<Passenger> passengers) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_DIR + "passengers.txt"))) {
            for (Passenger p : passengers) {
                out.println(p.getPassportNumber() + SEPARATOR
                        + p.getName() + SEPARATOR
                        + p.getNationality() + SEPARATOR
                        + p.getPhone() + SEPARATOR
                        + p.getEmail());
            }
            System.out.println("Saved " + passengers.size() + " passengers");
        } catch (IOException e) {
            System.err.println("Error saving passengers: " + e.getMessage());
        }
    }

    //  Load Passengers
    public static List<Passenger> loadPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        File file = new File(DATA_DIR + "passengers.txt");

        if (!file.exists()) {
            System.out.println("No passengers file found");
            return passengers;
        }

        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEPARATOR, -1);
                if (data.length >= 4) {
                    Passenger p = new Passenger(
                            data[0],
                            data[1],
                            data[2],
                            data[3],
                            data.length > 4 ? data[4] : ""
                    );
                    passengers.add(p);
                }
            }
            System.out.println("Loaded " + passengers.size() + " passengers");
        } catch (Exception e) {
            System.err.println("Error loading passengers: " + e.getMessage());
        }
        return passengers;
    }

    //  Save Gates
    public static void saveGates(List<Gate> gates) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_DIR + "gates.txt"))) {
            for (Gate g : gates) {
                out.println(g.getGateNumber() + SEPARATOR
                        + g.getTerminal() + SEPARATOR
                        + g.getCapacity());
            }
            System.out.println("Saved " + gates.size() + " gates");
        } catch (IOException e) {
            System.err.println("Error saving gates: " + e.getMessage());
        }
    }

    //  Load Gates
    public static List<Gate> loadGates() {
        List<Gate> gates = new ArrayList<>();
        File file = new File(DATA_DIR + "gates.txt");

        if (!file.exists()) {
            System.out.println("No gates file found");
            return gates;
        }

        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEPARATOR, -1);
                if (data.length == 3) {
                    gates.add(new Gate(data[0], data[1], Integer.parseInt(data[2])));
                }
            }
            System.out.println("Loaded " + gates.size() + " gates");
        } catch (Exception e) {
            System.err.println("Error loading gates: " + e.getMessage());
        }
        return gates;
    }

    //  Save Employees
    public static void saveEmployees(List<Employee> employees) {
        try (PrintWriter out = new PrintWriter(new FileWriter(DATA_DIR + "employees.txt"))) {
            for (Employee e : employees) {
                if (e instanceof Pilot) {
                    Pilot p = (Pilot) e;
                    out.println("PILOT" + SEPARATOR
                            + p.getId() + SEPARATOR
                            + p.getName() + SEPARATOR
                            + p.getPhone() + SEPARATOR
                            + p.getSalary() + SEPARATOR
                            + p.getLicenseNumber() + SEPARATOR
                            + p.getFlightHours());
                }
            }
            System.out.println("Saved " + employees.size() + " employees");
        } catch (IOException e) {
            System.err.println("Error saving employees: " + e.getMessage());
        }
    }

    //  Load Employees
    public static List<Employee> loadEmployees() {
        List<Employee> employees = new ArrayList<>();
        File file = new File(DATA_DIR + "employees.txt");

        if (!file.exists()) {
            System.out.println("No employees file found");
            return employees;
        }

        try (Scanner in = new Scanner(file)) {
            while (in.hasNextLine()) {
                String line = in.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }

                String[] data = line.split(SEPARATOR, -1);
                if (data[0].equals("PILOT") && data.length == 7) {
                    Pilot p = new Pilot(
                            data[1], data[2], data[3],
                            Double.parseDouble(data[4]),
                            data[5], Integer.parseInt(data[6])
                    );
                    employees.add(p);
                }
            }
            System.out.println("Loaded " + employees.size() + " employees");
        } catch (Exception e) {
            System.err.println("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    //  Save All Data
    public static void saveAll(Airport airport) {
        System.out.println("Saving data...");
        saveFlights(airport.getFlights());
        savePassengers(airport.getPassengers());
        saveGates(airport.getGates());
        saveEmployees(airport.getEmployees());
        System.out.println("All data saved!");
    }

    //  Load All Data
    public static void loadAll(Airport airport) {
        System.out.println("Loading data...");

        //  تحميل البواباتً
        List<Gate> gates = loadGates();
        for (Gate gate : gates) {
            airport.addGate(gate);
        }

        //  تحميل المسافرين
        List<Passenger> passengers = loadPassengers();
        for (Passenger p : passengers) {
            airport.addPassenger(p);
        }

        //  تحميل الرحلات وإعادة ربط المسافرين والبوابات
        List<Flight> flights = loadFlights(gates, passengers);
        for (Flight f : flights) {
            airport.addFlight(f);
        }

        //  تحميل الموظفين
        List<Employee> employees = loadEmployees();
        for (Employee e : employees) {
            airport.addEmployee(e);
        }

        System.out.println("All data loaded!");
    }
}
