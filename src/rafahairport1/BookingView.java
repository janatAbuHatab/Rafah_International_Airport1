package rafahairport1;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class BookingView {

    private static Stage primaryStage;
    private static boolean isJavaFXStarted = false;
    private static AirportSystem airportSystem;
    private static String bookingResult = "";
    private static CompletableFuture<String> futureResult;

    // متغيرات الواجهة
    private static ComboBox<String> flightComboBox;
    private static TextField passportField;
    private static TextField nameField;
    private static TextField nationalityField;
    private static TextField phoneField;
    private static TextField emailField;
    private static Label resultLabel;
    private static Stage currentStage;

    // بدء تشغيل JavaFX
    public static void initJavaFX() {
        if (!isJavaFXStarted) {
            new Thread(() -> {
                Application.launch(FXApplication.class);
            }).start();

            // انتظر حتى تبدأ JavaFX
            while (!isJavaFXStarted) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String showBookingWindow(AirportSystem system) {
        airportSystem = system;
        bookingResult = "";
        futureResult = new CompletableFuture<>();

        // تأكد من بدء JavaFX
        initJavaFX();

        // اعرض النافذة
        Platform.runLater(() -> {
            if (primaryStage != null) {
                // إذا كانت النافذة موجودة، أظهرها
                currentStage = new Stage();
                createBookingWindow(currentStage);
                currentStage.show();
            }
        });

        // انتظر النتيجة
        try {
            return futureResult.get(5, TimeUnit.MINUTES);
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    private static void createBookingWindow(Stage stage) {
        currentStage = stage;

        if (airportSystem == null) {
            showError(stage, "AirportSystem is not initialized!");
            return;
        }

        // عنوان
        Label title = new Label("Book a Flight");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        // حقل اختيار الرحلة
        Label flightLabel = new Label("Select Flight:");
        flightLabel.setStyle("-fx-font-weight: bold;");

        flightComboBox = new ComboBox<>();
        flightComboBox.setPromptText("Choose a flight");
        flightComboBox.setPrefWidth(250);
        loadFlights();

        // حقول الإدخال
        passportField = new TextField();
        passportField.setPromptText("Passport Number");

        nameField = new TextField();
        nameField.setPromptText("Full Name");

        nationalityField = new TextField();
        nationalityField.setPromptText("Nationality");

        phoneField = new TextField();
        phoneField.setPromptText("Phone Number");

        emailField = new TextField();
        emailField.setPromptText("Email");

        // زر الحجز
        Button bookBtn = new Button(" Book Flight");
        bookBtn.setStyle(
                "-fx-background-color: #2ecc71;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 10 25;"
                + "-fx-background-radius: 8;"
                + "-fx-cursor: hand;"
        );
        bookBtn.setOnAction(e -> bookFlight());

        // زر الإلغاء
        Button cancelBtn = new Button(" Cancel");
        cancelBtn.setStyle(
                "-fx-background-color: #e74c3c;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-weight: bold;"
                + "-fx-padding: 10 25;"
                + "-fx-background-radius: 8;"
                + "-fx-cursor: hand;"
        );
        cancelBtn.setOnAction(e -> {
            bookingResult = "Booking cancelled by user.";
            if (futureResult != null && !futureResult.isDone()) {
                futureResult.complete(bookingResult);
            }
            stage.close();
        });

        // نتيجة الحجز
        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 14px;");

        // تنسيق النموذج
        GridPane form = new GridPane();
        form.setHgap(15);
        form.setVgap(12);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: white; -fx-border-color: #bdc3c7; -fx-border-radius: 10; -fx-background-radius: 10;");

        form.add(flightLabel, 0, 0);
        form.add(flightComboBox, 1, 0);
        form.add(new Label("Passport:"), 0, 1);
        form.add(passportField, 1, 1);
        form.add(new Label("Name:"), 0, 2);
        form.add(nameField, 1, 2);
        form.add(new Label("Nationality:"), 0, 3);
        form.add(nationalityField, 1, 3);
        form.add(new Label("Phone:"), 0, 4);
        form.add(phoneField, 1, 4);
        form.add(new Label("Email:"), 0, 5);
        form.add(emailField, 1, 5);

        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(bookBtn, cancelBtn);
        form.add(buttonBox, 1, 6);

        VBox root = new VBox(20);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color: #f0f4f8;");
        root.getChildren().addAll(title, form, resultLabel);

        Scene scene = new Scene(root, 550, 500);
        stage.setTitle("Book a Flight");
        stage.setScene(scene);
        stage.setResizable(false);

        stage.setOnCloseRequest(e -> {
            if (futureResult != null && !futureResult.isDone()) {
                bookingResult = "Booking window closed.";
                futureResult.complete(bookingResult);
            }
        });
    }

    private static void loadFlights() {
        try {
            flightComboBox.getItems().clear();
            if (airportSystem == null) {
                System.err.println("airportSystem is null in loadFlights!");
                return;
            }

            List<Flight> flights = airportSystem.getAllFlights();
            if (flights == null || flights.isEmpty()) {
                flightComboBox.getItems().add("No flights available");
                return;
            }

            for (Flight flight : flights) {
                String display = flight.getFlightNumber() + " → " + flight.getDestination()
                        + " (" + flight.getAvailableSeats() + " seats available)";
                flightComboBox.getItems().add(display);
            }

            System.out.println("Loaded " + flights.size() + " flights in booking window");

        } catch (Exception e) {
            System.err.println("Error loading flights: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void bookFlight() {
        try {
            String selected = flightComboBox.getValue();
            if (selected == null || selected.equals("No flights available")) {
                resultLabel.setText(" Please select a flight.");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            String flightNumber = selected.split(" → ")[0];

            String passport = passportField.getText().trim().toUpperCase();
            String name = nameField.getText().trim();
            String nationality = nationalityField.getText().trim();
            String phone = phoneField.getText().trim();
            String email = emailField.getText().trim();

            if (passport.isEmpty() || name.isEmpty()) {
                resultLabel.setText(" Passport and Name are required.");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            Flight flight = airportSystem.findFlight(flightNumber);
            if (flight == null) {
                resultLabel.setText(" Flight not found.");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            if (flight.getAvailableSeats() <= 0) {
                resultLabel.setText(" Flight is full!");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
                return;
            }

            Passenger passenger = airportSystem.findPassenger(passport);
            if (passenger == null) {
                passenger = new Passenger(passport, name, nationality, phone, email);
                airportSystem.registerPassenger(passport, name, nationality, phone, email);
            }

            boolean success = flight.addPassenger(passenger);
            if (success) {
                String bpId = "BP" + System.currentTimeMillis();
                int seatNumber = flight.getPassengerCount() + 10;
                passenger.setBoardingPass(bpId + "-" + seatNumber);

                bookingResult = "Booking Successful!\n"
                        + "   Flight: " + flight.getFlightNumber() + "\n"
                        + "   Passenger: " + passenger.getName() + "\n"
                        + "   Seat: " + seatNumber + "\n"
                        + "   Boarding Pass: " + passenger.getBoardingPass();

                resultLabel.setText(" Booking Successful!");
                resultLabel.setStyle("-fx-text-fill: #27ae60;");

                airportSystem.saveData();
                clearFields();
                loadFlights();

                // إغلاق النافذة بعد 2 ثانية
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Platform.runLater(() -> {
                        if (futureResult != null && !futureResult.isDone()) {
                            futureResult.complete(bookingResult);
                        }
                        if (currentStage != null) {
                            currentStage.close();
                        }
                    });
                }).start();

            } else {
                resultLabel.setText(" Booking failed. Please try again.");
                resultLabel.setStyle("-fx-text-fill: #e74c3c;");
            }
        } catch (Exception e) {
            resultLabel.setText(" Error: " + e.getMessage());
            resultLabel.setStyle("-fx-text-fill: #e74c3c;");
            e.printStackTrace();
        }
    }

    private static void clearFields() {
        passportField.clear();
        nameField.clear();
        nationalityField.clear();
        phoneField.clear();
        emailField.clear();
        flightComboBox.setValue(null);
    }

    private static void showError(Stage stage, String message) {
        System.err.println("ERROR: " + message);
        bookingResult = "Error: " + message;
        if (futureResult != null && !futureResult.isDone()) {
            futureResult.complete(bookingResult);
        }
        if (stage != null) {
            stage.close();
        }
    }

    // تطبيق JavaFX الرئيسي
    public static class FXApplication extends Application {

        @Override
        public void start(Stage stage) {
            primaryStage = stage;
            isJavaFXStarted = true;

            // لا تظهر النافذة الرئيسية، فقط أنشئها
            stage.setTitle("Rafah Airport - Booking System");
            stage.setWidth(1);
            stage.setHeight(1);
            stage.setOpacity(0);
            stage.show();
        }

        @Override
        public void stop() {
            isJavaFXStarted = false;
        }
    }
}
