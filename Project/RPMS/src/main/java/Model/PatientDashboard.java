package Model;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PatientDashboard extends Application {
    private Patients currentPatient;
    private Object assignedDoctor; // Placeholder until Doctors class is implemented

    private Label patientName;
    private Label patientDetailsLabel;
    private Label contactInfo;
    private Label ageValue;
    private Label genderValue;
    private Label userIdValue;
    private Label heartRateValue;
    private Label bloodPressureValue;
    private Label oxygenLevelValue;
    private TextArea feedbackArea;
    private Label upcomingAppointmentValue;
    private TextArea prescriptionArea;
    private TextArea videoArea;

    public void setPatient(Patients patient, Object doctor) {
        this.currentPatient = patient;
        this.assignedDoctor = doctor;
        updateDisplay();
    }

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f0f4f8;");

        // Top Menu Bar
        HBox topMenu = new HBox(20);
        topMenu.setStyle("-fx-background-color: #0a3d62; -fx-padding: 10;");
        topMenu.setAlignment(javafx.geometry.Pos.CENTER);
        String[] menuItems = {"Dashboard", "My Doctors", "Appointments", "Messages", "Feedback", "Prescriptions", "Vitals", "History", "Add Vitals", "Video Consult", "Emergency", "Logout"};
        for (String item : menuItems) {
            Button button = new Button(item);
            button.setStyle("-fx-font-size: 12px; -fx-text-fill: #ffffff; -fx-background-color: transparent; -fx-padding: 5 10;");
            button.setOnAction(e -> handleMenuAction(item));
            topMenu.getChildren().add(button);
        }
        root.setTop(topMenu);

        // Center Content
        VBox centerContent = new VBox(20);
        centerContent.setPadding(new javafx.geometry.Insets(20));
        centerContent.setAlignment(javafx.geometry.Pos.CENTER);

        // Profile Section
        HBox profileSection = new HBox(20);
        profileSection.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
        ImageView profileImage = new ImageView();
        profileImage.setFitHeight(60);
        profileImage.setFitWidth(60);
        profileImage.setStyle("-fx-border-radius: 50; -fx-border-color: #0a3d62; -fx-border-width: 1;");
        VBox profileDetails = new VBox(5);
        patientName = new Label();
        patientName.setStyle("-fx-font-size: 18px; -fx-text-fill: #0a3d62;");
        patientDetailsLabel = new Label();
        patientDetailsLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        contactInfo = new Label();
        contactInfo.setStyle("-fx-font-size: 12px; -fx-text-fill: #666666;");
        profileDetails.getChildren().addAll(patientName, patientDetailsLabel, contactInfo);
        profileSection.getChildren().addAll(profileImage, profileDetails);

        // Overview and Vitals
        GridPane mainGrid = new GridPane();
        mainGrid.setHgap(20);
        mainGrid.setVgap(20);
        mainGrid.setAlignment(javafx.geometry.Pos.CENTER);

        // Overview Box
        VBox overviewBox = createInfoBox("Patient Overview");
        GridPane overviewGrid = new GridPane();
        overviewGrid.setHgap(10);
        overviewGrid.setVgap(5);
        overviewGrid.add(new Label("Age:"), 0, 0);
        ageValue = new Label();
        ageValue.setStyle("-fx-text-fill: #333333;");
        overviewGrid.add(ageValue, 1, 0);
        overviewGrid.add(new Label("Gender:"), 0, 1);
        genderValue = new Label();
        genderValue.setStyle("-fx-text-fill: #333333;");
        overviewGrid.add(genderValue, 1, 1);
        overviewGrid.add(new Label("User ID:"), 0, 2);
        userIdValue = new Label();
        userIdValue.setStyle("-fx-text-fill: #333333;");
        overviewGrid.add(userIdValue, 1, 2);
        overviewBox.getChildren().add(overviewGrid);

        // Vitals Box
        VBox vitalsBox = createInfoBox("Recent Vitals");
        GridPane vitalsGrid = new GridPane();
        vitalsGrid.setHgap(10);
        vitalsGrid.setVgap(5);
        vitalsGrid.add(new Label("Heart Rate:"), 0, 0);
        heartRateValue = new Label();
        heartRateValue.setStyle("-fx-text-fill: #333333;");
        vitalsGrid.add(heartRateValue, 1, 0);
        vitalsGrid.add(new Label("Blood Pressure:"), 0, 1);
        bloodPressureValue = new Label();
        bloodPressureValue.setStyle("-fx-text-fill: #333333;");
        vitalsGrid.add(bloodPressureValue, 1, 1);
        vitalsGrid.add(new Label("Oxygen Level:"), 0, 2);
        oxygenLevelValue = new Label();
        oxygenLevelValue.setStyle("-fx-text-fill: #333333;");
        vitalsGrid.add(oxygenLevelValue, 1, 2);
        vitalsBox.getChildren().add(vitalsGrid);

        // Feedback Box
        VBox feedbackBox = createInfoBox("Recent Feedback");
        feedbackArea = new TextArea();
        feedbackArea.setEditable(false);
        feedbackArea.setWrapText(true);
        feedbackArea.setPrefHeight(100);
        feedbackArea.setPrefWidth(300);
        feedbackArea.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333; -fx-background-color: #ffffff;");
        feedbackBox.getChildren().add(feedbackArea);

        // Appointments Box
        VBox appointmentBox = createInfoBox("Upcoming Appointments");
        upcomingAppointmentValue = new Label();
        upcomingAppointmentValue.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333;");
        Button requestAppointmentBtn = new Button("Request Appointment");
        requestAppointmentBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 5 15; -fx-font-size: 12px;");
        requestAppointmentBtn.setOnAction(e -> handleRequestAppointment());
        appointmentBox.getChildren().addAll(upcomingAppointmentValue, requestAppointmentBtn);

        // Prescriptions Box
        VBox prescriptionBox = createInfoBox("Recent Prescriptions");
        prescriptionArea = new TextArea();
        prescriptionArea.setEditable(false);
        prescriptionArea.setWrapText(true);
        prescriptionArea.setPrefHeight(100);
        prescriptionArea.setPrefWidth(300);
        prescriptionArea.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333; -fx-background-color: #ffffff;");
        prescriptionBox.getChildren().add(prescriptionArea);

        // Video Consult Box
        VBox videoBox = createInfoBox("Approved Video Consults");
        videoArea = new TextArea();
        videoArea.setEditable(false);
        videoArea.setWrapText(true);
        videoArea.setPrefHeight(100);
        videoArea.setPrefWidth(300);
        videoArea.setStyle("-fx-font-size: 12px; -fx-text-fill: #333333; -fx-background-color: #ffffff;");
        videoBox.getChildren().add(videoArea);

        mainGrid.add(overviewBox, 0, 0);
        mainGrid.add(vitalsBox, 1, 0);
        mainGrid.add(feedbackBox, 0, 1);
        mainGrid.add(appointmentBox, 1, 1);
        mainGrid.add(prescriptionBox, 0, 2);
        mainGrid.add(videoBox, 1, 2);

        // Action Buttons
        HBox actionButtons = new HBox(20);
        actionButtons.setAlignment(javafx.geometry.Pos.CENTER);
        Button addVitalsBtn = new Button("Add Vitals");
        addVitalsBtn.setStyle("-fx-background-color: #2980b9; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-size: 12px;");
        addVitalsBtn.setOnAction(e -> handleAddVitals());
        Button emergencyBtn = new Button("Emergency Alert");
        emergencyBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-size: 12px;");
        emergencyBtn.setOnAction(e -> handleEmergency());
        Button videoConsultBtn = new Button("Request Video Consult");
        videoConsultBtn.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-padding: 8 15; -fx-font-size: 12px;");
        videoConsultBtn.setOnAction(e -> handleVideoConsultation());
        actionButtons.getChildren().addAll(addVitalsBtn, emergencyBtn, videoConsultBtn);

        // Add components to center
        centerContent.getChildren().addAll(profileSection, mainGrid, actionButtons);
        root.setCenter(centerContent);

        // Set up the scene
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setTitle("Patient Dashboard - RPMS");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize with a sample patient (replace with actual login logic)
        currentPatient = new Patients("P001", "John Doe", "john@example.com", "+880123456789", "emergency@gmail.com", "password123", 40, "Male", false);
        assignedDoctor = new Object(); // Placeholder until Doctors class is implemented
        updateDisplay();
    }

    private VBox createInfoBox(String title) {
        VBox box = new VBox(10);
        box.setStyle("-fx-background-color: white; -fx-padding: 15; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 1);");
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #0a3d62;");
        Separator separator = new Separator();
        box.getChildren().addAll(titleLabel, separator);
        return box;
    }

    private void updateDisplay() {
        if (currentPatient != null) {
            patientName.setText(currentPatient.getName());
            patientDetailsLabel.setText(currentPatient.getGender() + " - " + currentPatient.getAge());
            contactInfo.setText(currentPatient.getEmail() + " | " + currentPatient.getContactNumber());
            ageValue.setText(String.valueOf(currentPatient.getAge()));
            genderValue.setText(currentPatient.getGender());
            userIdValue.setText(currentPatient.getUserId());

            // Vitals (Placeholder until Vitals class is implemented)
            List<Object> vitalsList = new ArrayList<>(); // Replace with currentPatient.getVitalsList()
            if (!vitalsList.isEmpty()) {
                Object recentVitals = vitalsList.get(vitalsList.size() - 1);
                heartRateValue.setText("N/A"); // Replace with recentVitals.getHeartRate()
                bloodPressureValue.setText("N/A"); // Replace with recentVitals.getBloodPressure()
                oxygenLevelValue.setText("N/A"); // Replace with recentVitals.getOxygenLevel()
            } else {
                heartRateValue.setText("N/A");
                bloodPressureValue.setText("N/A");
                oxygenLevelValue.setText("N/A");
            }

            // Feedback (Placeholder until FeedBack class is implemented)
            List<Object> feedbackList = new ArrayList<>(); // Replace with currentPatient.getDoctorFeedback()
            if (!feedbackList.isEmpty()) {
                feedbackArea.setText("Sample Feedback"); // Replace with feedbackList.get(feedbackList.size() - 1).toString()
            } else {
                feedbackArea.setText("No feedback available.");
            }

            // Appointments (Placeholder until Appointment class is implemented)
            List<Object> appointments = new ArrayList<>(); // Replace with currentPatient.getAppointments()
            upcomingAppointmentValue.setText(appointments.isEmpty() ? "None" : "Sample Appointment");

            // Prescriptions (Placeholder until Prescriptions class is implemented)
            List<Object> prescriptions = new ArrayList<>(); // Replace with currentPatient.getPrescriptions()
            if (!prescriptions.isEmpty()) {
                prescriptionArea.setText("Sample Prescription"); // Replace with prescriptions.get(prescriptions.size() - 1).toString()
            } else {
                prescriptionArea.setText("No prescriptions available.");
            }

            // Video Consultations (Placeholder until VideoConsultation class is implemented)
            List<Object> videoConsultations = new ArrayList<>(); // Replace with currentPatient.getVideoConsultations()
            if (!videoConsultations.isEmpty()) {
                videoArea.setText("Sample Video Consult: Link N/A, Time N/A"); // Replace with formatted video consultation data
            } else {
                videoArea.setText("No approved video consultations.");
            }
        }
    }

    private void handleMenuAction(String action) {
        switch (action) {
            case "Dashboard":
                updateDisplay();
                break;
            case "My Doctors":
                if (assignedDoctor != null) {
                    feedbackArea.setText("Assigned Doctor: N/A"); // Placeholder
                } else {
                    feedbackArea.setText("No doctor assigned.");
                }
                break;
            case "Appointments":
                currentPatient.ConfirmedAppointment();
                upcomingAppointmentValue.setText("Appointments confirmed. Check with doctor.");
                break;
            case "Messages":
                feedbackArea.setText("No messages available yet.");
                break;
            case "Feedback":
                currentPatient.viewDoctorFeedBack();
                feedbackArea.setText("Feedback viewed. No data available.");
                break;
            case "Prescriptions":
                currentPatient.medicalHistory();
                prescriptionArea.setText("Medical history viewed. No prescriptions available.");
                break;
            case "Vitals":
                feedbackArea.setText("View vitals in the Recent Vitals section.");
                break;
            case "History":
                currentPatient.medicalHistory();
                prescriptionArea.setText("History viewed. No data available.");
                break;
            case "Add Vitals":
                handleAddVitals();
                break;
            case "Video Consult":
                handleVideoConsultation();
                break;
            case "Emergency":
                handleEmergency();
                break;
            case "Logout":
                currentPatient.logOut();
                System.exit(0); // Simple exit; replace with navigation to login
                break;
        }
    }

    private void handleAddVitals() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Vitals CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        java.io.File file = fileChooser.showOpenDialog(null);
        if (file != null && currentPatient != null) {
            currentPatient.uploadVitalsFromFile(file.getAbsolutePath(), (Doctors) assignedDoctor); // Cast to Doctors
            feedbackArea.setText("Vitals added from " + file.getName());
        } else {
            feedbackArea.setText("No file selected or patient not set.");
        }
    }

    private void handleRequestAppointment() {
        if (currentPatient != null && assignedDoctor != null) {
            currentPatient.requestAppointment(((Doctors) assignedDoctor).getUserId(), "General Checkup", LocalDateTime.now().plusDays(1));
            upcomingAppointmentValue.setText("Request sent for General Checkup on " + LocalDateTime.now().plusDays(1));
        } else {
            upcomingAppointmentValue.setText("Error: No doctor or patient set.");
        }
    }

    private void handleVideoConsultation() {
        if (currentPatient != null && assignedDoctor != null) {
            currentPatient.requestVideoConsultation(((Doctors) assignedDoctor).getUserId(), LocalDateTime.now().plusHours(2));
            currentPatient.viewApprovedVideoConsultation();
            videoArea.setText("Video consult requested for " + LocalDateTime.now().plusHours(2) + ". Awaiting approval.");
        } else {
            videoArea.setText("Error: No doctor or patient set.");
        }
    }

    private void handleEmergency() {
        if (currentPatient != null && assignedDoctor != null) {
            currentPatient.panicButton((Doctors) assignedDoctor);
            feedbackArea.setText("Emergency alert sent to doctor and family.");
        } else {
            feedbackArea.setText("Error: No doctor or patient set.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}