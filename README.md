# Rafah International Airport Management System

---

## Overview

Rafah International Airport Management System is a Java-based desktop application developed using Object-Oriented Programming (OOP) principles. The system simulates the core operations of an airport, including flight scheduling, passenger registration, employee management, gate allocation, and ticket booking through both Console and JavaFX interfaces.

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

- Airport status overview
- Save data into text files
- Automatic data loading
- Flight booking using JavaFX

---

## OOP Concepts

| Concept | Description |
|---------|-------------|
| **Encapsulation** | Private fields with public getters and setters. |
| **Abstraction** | Abstract `Employee` class implemented by subclasses. |
| **Inheritance** | `Pilot` extends `Employee`; `InvalidFlightException` extends `Exception`. |
| **Polymorphism** | Employee references can point to Pilot objects. |
| **Interfaces** | `Comparable` implemented in multiple classes. |
| **Method Overriding** | `compareTo()` overridden where required. |
| **Enumeration** | `FlightStatus` enum defines flight states. |
| **Exception Handling** | Custom exceptions with try-catch blocks. |
| **Composition** | Flight owns Passenger and Pilot objects. |
| **Aggregation** | Airport contains Flights, Gates, Employees, and Passengers. |
| **JavaFX** | Booking interface for passengers. |

---

## Project Structure

```text
src
│
├── Airport.java
├── AirportSystem.java
├── BookingView.java
├── Employee.java
├── FileHandler.java
├── Flight.java
├── FlightStatus.java
├── Gate.java
├── InvalidFlightException.java
├── Passenger.java
└── Pilot.java
```

---

## Main Classes

| Class | Responsibility |
|------|----------------|
| `AirportSystem` | Application controller |
| `Airport` | Stores airport data |
| `Flight` | Flight information |
| `Passenger` | Passenger information |
| `Employee` | Abstract employee class |
| `Pilot` | Pilot implementation |
| `Gate` | Airport gates |
| `FileHandler` | File storage and retrieval |
| `InvalidFlightException` | Custom exception |
| `BookingView` | JavaFX booking window |

---
## UML Digram for the project
<img width="1902" height="2511" alt="RafahAirportUmL drawio" src="https://github.com/user-attachments/assets/77990030-8e4a-4a04-b9db-7d922c28e23d" />


## Technologies

| Technology | Purpose |
|------------|---------|
| Java | Core programming language |
| JavaFX | Graphical User Interface |
| OOP | Software design |
| Collections Framework | Data management |
| File Handling | Persistent storage |
| Exception Handling | Error management |

---

## Running the Project

### Console Mode

Run

```text
AirportSystem.java
```

The Console Interface provides full airport management functionality.
<img width="956" height="309" alt="image" src="https://github.com/user-attachments/assets/bce989fc-6ba4-4b4e-b654-b69a91010052" />

### JavaFX Mode

From the Console menu select

```
Booking View
```

The JavaFX window allows passengers to browse available flights and book tickets.

---
<img width="584" height="536" alt="لقطة شاشة 2026-07-16 163436" src="https://github.com/user-attachments/assets/fdac9c75-e396-42bd-9428-7ef0574d5869" />

## AI Usage Declaration

ChatGPT was used as a learning and guidance tool during the development of this project. It provided explanations of JavaFX concepts, assisted in understanding the GUI development process, and clarified the overall code structure and programming techniques and it also helped as to deal witj errors appered while doing the program.

All implementation, integration, testing, debugging, and final design decisions were completed independently by the students.

---

## Team Members

| Student | ID |
|---------|------------|
| Jannat Mohammed Abu Hatab | 2349011014 |
| Alaa Mohammed Afana | 2549011056 |
| Deema Ahmed Alnajjar | 2549011064 |
| Sara Ramiz Amer | 2549011042 |

---

## Course Project

Computer Systems Engineering

Object-Oriented Programming (Java)

Rafah International Airport Management System
