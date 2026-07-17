# Rafah International Airport Management System

# project description
This project designs and implements an integrated management system for Rafah International Airport using ava and Object-Oriented Programming (OOP) principles.
It manages core airport operations, including flights, passengers, gates, employees, airport status, and data storage/retrieval using text files.
The system applies key OOP concepts such as Encapsulation, Inheritance, Polymorphism, Abstraction, Interfaces, and includes a simple JavaFX-based user interface.

# Main Features
The project enables the programmatic transformation of real-world relationships by creating objects that interact with each other like in real life , 
and allows us to modify by consol or by using a booking view interface which created by JavaFX so you can apply operations on them, as follows:
flight Management (Add new flight / View all flights /Search for a flight /Update flight status / Cancel flight / Assign gate to flight / Assign pilot to flight).
Passenger Management (Register new passenger / View all passengers / Search for a passenger / Flight check-in).
Gate Management (View all gates / Add new gate /Edit gate).
Employee Management (View all employees / Add new pilot / Search for an employee).
Other (View airport status / Save data /Auto-load data on startup/booking a ticket in a flight).

#OOP concept Used
1-Encapsulation : In our project We used encapsulation in all classes by making the fields private and accessing them through getters and setters.
2-Abstraction :  We used abstraction by creating the abstract class Employee. We cannot create an object from Employee using the new keyword.
Instead, subclasses such as Pilot inherit from it and implement its abstract methods
3-Inheritance : : We used inheritance because a Pilot is an Employee, so the Pilot class extends Employee and inherits its common fields and methods.
 We also used inheritance when InvalidFlightException extends the Exception class.
4-Polymorphism : An Employee reference can refer to a Pilot object For Example : Employee employee = new Pilot(..);
5-Interfaces and Method Overriding : We implemented the Comparable interface in the Pilot, Flight, Passenger, and Gate classes.
We also overrode the compareTo() method to define how objects are compared
6-Enumeration : We used FlightStatus to define the available flight statuses.
7-Exception Handling : We created a custom exception called InvalidFlightException and used try-catch blocks to handle runtime errors.
8- Has-a Relationship (composition &Aggregation) : Composition is applied between Flight and Passenger, and between Flight and Pilot.
Aggregation is applied between Airport and Employee, Airport and Gate, Airport and Passenger, and Airport and Flight ,also between Flight and gate.
9-JavaFX : We used JavaFX to create a simple interface that allows passengers to book a flight. The interface displays the available flights and the number of available seats.

#main classes
AirportSystem : Main controller class 
Airport : Represents the airport 
Flight : Represents flights 
Passenger : Represents passengers 
Employee : Abstract superclass for employees 
Pilot : Subclass of Employee 
Gate : Represents airport gates 
FileHandler : Handles file I/O 
InvalidFlightException : Custom exception 
BookingView : JavaFX booking window 

#How to run the project
the project run normally fro console if you press Booking View you will run it by JavaFX
so the project use both Console & JavaFX as follow :
- Console: Main menu for airport management
- JavaFX: Booking window for passengers

#AL used decleration
ChatGPT was used as a learning and guidance tool during the development of this project.
 It provided explanations of JavaFX concepts, assisted in understanding the GUI development process, and clarified the overall code structure and programming techniques.
 All implementation, integration, testing, debugging, and final design decisions were carried out independently by the students.

#Student names and IDs
Jannat Mohammed Abu Hatab _ NO :2349011014
Alaa Mohammed Afana _ NO 2549011056
Deema Ahmed Alnajjar _ NO 2549011064
Sara Ramiz Amer _ NO 2549011042
