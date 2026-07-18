package rafahairport1;

public class Gate implements Comparable<Gate> {

    private String gateNumber;
    private String terminal;
    private boolean available;
    private Flight currentFlight;  // Association with Flight class
    private int capacity;

    public Gate(String gateNumber, String terminal, int capacity) {
        this.gateNumber = gateNumber;
        this.terminal = terminal;
        this.capacity = capacity;
        this.available = true;
    }

    // Getters
    public String getGateNumber() {
        return gateNumber;
    }

    public String getTerminal() {
        return terminal;
    }

    public boolean isAvailable() {
        return available;
    }

    public Flight getCurrentFlight() {
        return currentFlight;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean assignFlight(Flight flight) {
        if (available && flight != null) {
            this.currentFlight = flight;
            this.available = false;
            flight.setGate(this);
            return true;
        }
        return false;
    }

    // جعلها متاحة
    public void release() {
        if (currentFlight != null) {
            currentFlight.setGate(null);
        }
        this.currentFlight = null;
        this.available = true;
    }

    // Implement Comparable interface
    @Override
    public int compareTo(Gate other) {
        return this.gateNumber.compareTo(other.gateNumber); // يرتب ارقام البوابات لعرضهم مرتبين 
    }

    @Override
    public String toString() {
        return gateNumber + " (Terminal " + terminal + ") - "
                + (available ? "Available" : "Occupied by " + currentFlight.getFlightNumber());
    }
}
