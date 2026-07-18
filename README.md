# Rafah International Airport Management System

---

## Overview

**Rafah International Airport Management System** is a Java-based desktop application developed using **Object-Oriented Programming (OOP)** principles. The system simulates the core operations of an airport, including flight scheduling, passenger registration, employee management, gate allocation, and ticket booking through both Console and JavaFX interfaces.

---

## Key Features

### Flight Management

- Add new flights
- View all flights
- Search for flights
- Update flight status
- Cancel flights
- Assign gates
- Assign pilots

### Passenger Management

- Register passengers
- View passengers
- Search passengers
- Delete passengers
- Flight check-in

### Gate Management

- Add gates
- Edit gate information
- View available gates

### Employee Management

- Add pilots
- Search employees
- View employees

### Additional Features

- View airport status
- Save data into text files
- Automatically load data at startup
- Book flights using the JavaFX interface

---

## OOP Concepts

| Concept | Description |
|---------|-------------|
| **Encapsulation** | All class fields are private and accessed through getters and setters. |
| **Abstraction** | The abstract `Employee` class is extended by subclasses such as `Pilot`. |
| **Inheritance** | `Pilot` extends `Employee`, and `InvalidFlightException` extends `Exception`. |
| **Polymorphism** | An `Employee` reference can refer to a `Pilot` object. |
| **Interfaces** | The `Comparable` interface is implemented by several classes. |
| **Method Overriding** | The `compareTo()` method is overridden to define custom object comparisons. |
| **Enumeration** | `FlightStatus` defines the available flight states. |
| **Exception Handling** | Custom exceptions and `try-catch` blocks are used to handle runtime errors. |
| **Composition** | Applied between `Flight` and `Passenger`, and between `Flight` and `Pilot`. |
| **Aggregation** | Applied between `Airport` and its Flights, Employees, Gates, and Passengers. |
| **JavaFX** | Provides a graphical booking interface for passengers. |

---

## Project Structure

```text
Rafah-International-Airport-Management-System
│
├── src
│   └── rafahairport
│       ├── Airport.java
│       ├── AirportSystem.java
│       ├── BookingView.java
│       ├── Employee.java
│       ├── FileHandler.java
│       ├── Flight.java
│       ├── FlightStatus.java
│       ├── Gate.java
│       ├── InvalidFlightException.java
│       ├── Passenger.java
│       └── Pilot.java
│
├── employees.txt
├── flights.txt
├── gates.txt
├── passengers.txt
└── README.md
```

---

## Main Classes

| Class | Responsibility |
|------|----------------|
| `AirportSystem` | Main application controller |
| `Airport` | Represents the airport and stores its data |
| `Flight` | Represents a flight |
| `Passenger` | Represents a passenger |
| `Employee` | Abstract superclass for employees |
| `Pilot` | Represents a pilot |
| `Gate` | Represents an airport gate |
| `FileHandler` | Handles file input/output operations |
| `InvalidFlightException` | Custom exception class |
| `BookingView` | JavaFX booking interface |

---

## UML Diagram

<img width="1902" height="2551" alt="RafahAirport drawio" src="https://github.com/user-attachments/assets/83d13579-369d-49d3-bdd1-af3c3266de8e" />


---

## Technologies Used

| Technology | Purpose |
|------------|---------|
| Java | Core programming language |
| JavaFX | Graphical User Interface |
| Object-Oriented Programming | Software Design |
| Collections Framework | Data Management |
| File Handling | Persistent Storage |
| Exception Handling | Error Management |

---

## Running the Project

### Console Mode

Run:

```text
AirportSystem.java
```

The Console Interface provides complete airport management functionality.

<p align="center">
<img width="900" alt="Console Interface" src="https://github.com/user-attachments/assets/bce989fc-6ba4-4b4e-b654-b69a91010052">
</p>

---

### JavaFX Mode

From the Console menu, select:

```text
Booking View
```

The JavaFX interface allows passengers to browse available flights and book tickets.

<p align="center">
<img width="550" alt="JavaFX Interface" src="https://github.com/user-attachments/assets/106c4354-5024-44e4-a848-e487593b1ca1">
</p>

---

## AI Usage Declaration

ChatGPT was used as a learning and guidance tool during the development of this project. It helped explain JavaFX concepts, clarify the application structure, and assist in understanding and resolving programming errors encountered during development.

All implementation, integration, testing, debugging, and final design decisions were completed independently by the students.

---

## Team Members

| Student | ID |
|---------|------------|
| Jannat Mohammed Abu Hatab | 2549011045 |
| Alaa Mohammed Afana | 2549011056 |
| Deema Ahmed Alnajjar | 2549011064 |
| Sara Ramiz Amer | 2549011042 |

---

## Course Information

**Course:** Object-Oriented Programming (Java)

**Department:** Computer Systems Engineering

**Project:** Rafah International Airport Management System
