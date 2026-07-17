/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rafahairport1;

/**
 *
 * @author SaQeR
 */
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
