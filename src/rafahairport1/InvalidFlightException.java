package rafahairport1;


public class InvalidFlightException extends Exception {

    private String flightNumber;

    public InvalidFlightException(String message) {
        super(message);
    }

    public InvalidFlightException(String message, String flightNumber) {
        super(message);
        this.flightNumber = flightNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }
}
