package com.example.demo;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeManagementApp extends Application {

    private List<Employee> employees = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");

        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(50));
        borderPane.setStyle("-fx-background-color: linear-gradient(to bottom, #800080, #0000FF);");

        Text heading = new Text("Employee Management System");
        Font font = Font.loadFont(getClass().getResourceAsStream("your-font-file.ttf"), 50);
        heading.setFont(font);
        heading.setFill(Color.WHITE);
        VBox headingBox = new VBox(10);
        headingBox.setAlignment(Pos.CENTER);
        headingBox.getChildren().add(heading);
        borderPane.setTop(headingBox);

        VBox buttonBox = new VBox(20);
        buttonBox.setAlignment(Pos.CENTER);

        Button btnAddEmployee = new Button("Add Employee Details");
        Button btnRecordAttendance = new Button("Record Attendance");
        Button btnProcessPayroll = new Button("Process Payroll");

        btnAddEmployee.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 24px;");
        btnRecordAttendance.setStyle("-fx-background-color: #008CBA; -fx-text-fill: white; -fx-font-size: 24px;");
        btnProcessPayroll.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; -fx-font-size: 24px;");

        btnAddEmployee.setOnAction(e -> showAddEmployeeDialog());
        btnRecordAttendance.setOnAction(e -> showRecordAttendanceDialog());
        btnProcessPayroll.setOnAction(e -> showProcessPayrollDialog());

        buttonBox.getChildren().addAll(btnAddEmployee, btnRecordAttendance, btnProcessPayroll);

        borderPane.setCenter(buttonBox);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);

        primaryStage.setMaximized(true);

        primaryStage.show();
    }

    private void showAddEmployeeDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Add Employee");
        dialog.setHeaderText("Add New Employee to the System");

        // Create labels and text fields
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        Label positionLabel = new Label("Position:");
        TextField positionField = new TextField();

        // Add labels and text fields to dialog layout
        VBox content = new VBox(10);
        content.getChildren().addAll(nameLabel, nameField, idLabel, idField, positionLabel, positionField);
        dialog.getDialogPane().setContent(content);

        // Add buttons
        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                String name = nameField.getText();
                String id = idField.getText();
                String position = positionField.getText();
                if (name.isEmpty() || id.isEmpty() || position.isEmpty()) {
                    showErrorAlert("Please fill in all fields.");
                } else {
                    employees.add(new Employee(name, id, position));
                    // For now, just display a message
                    showSuccessAlert("Employee added successfully.");
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showRecordAttendanceDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Record Attendance");
        dialog.setHeaderText("Record Employee Attendance");

        // Create labels and controls
        Label idLabel = new Label("Employee ID:");
        TextField idField = new TextField();
        Label dateLabel = new Label("Date:");
        DatePicker datePicker = new DatePicker();
        Label statusLabel = new Label("Attendance Status:");
        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll("Present", "Absent");

        // Add labels and controls to dialog layout
        VBox content = new VBox(10);
        content.getChildren().addAll(idLabel, idField, dateLabel, datePicker, statusLabel, statusComboBox);
        dialog.getDialogPane().setContent(content);

        // Add buttons
        ButtonType recordButton = new ButtonType("Record", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(recordButton, ButtonType.CANCEL);

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == recordButton) {
                String id = idField.getText();
                String date = datePicker.getValue().toString();
                String status = statusComboBox.getValue();
                if (id.isEmpty() || date.isEmpty() || status == null) {
                    showErrorAlert("Please fill in all fields.");
                } else {
                    Optional<Employee> employeeOptional = findEmployeeById(id);
                    if (employeeOptional.isPresent()) {
                        // Add logic to record attendance
                        // For now, just display a message
                        showSuccessAlert("Attendance recorded successfully.");
                    } else {
                        showErrorAlert("Employee ID not found.");
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private void showProcessPayrollDialog() {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Process Payroll");
        dialog.setHeaderText("Process Employee Payroll");

        // Create labels and text fields
        Label idLabel = new Label("Employee ID:");
        TextField idField = new TextField();
        Label salaryLabel = new Label("Salary Details:");
        TextField salaryField = new TextField();

        // Add labels and text fields to dialog layout
        VBox content = new VBox(10);
        content.getChildren().addAll(idLabel, idField, salaryLabel, salaryField);
        dialog.getDialogPane().setContent(content);

        // Add buttons
        ButtonType processButton = new ButtonType("Process", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(processButton, ButtonType.CANCEL);

        // Handle button actions
        dialog.setResultConverter(buttonType -> {
            if (buttonType == processButton) {
                String id = idField.getText();
                String salary = salaryField.getText();
                if (id.isEmpty() || salary.isEmpty()) {
                    showErrorAlert("Please fill in all fields.");
                } else {
                    Optional<Employee> employeeOptional = findEmployeeById(id);
                    if (employeeOptional.isPresent()) {
                        // Add logic to process payroll
                        // For now, just display a message
                        showSuccessAlert("Payroll processed successfully.");
                    } else {
                        showErrorAlert("Employee ID not found.");
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();
    }

    private Optional<Employee> findEmployeeById(String id) {
        return employees.stream()
                .filter(employee -> employee.getId().equals(id))
                .findFirst();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    static class Employee {
        private String name;
        private String id;
        private String position;

        public Employee(String name, String id, String position) {
            this.name = name;
            this.id = id;
            this.position = position;
        }

        public String getId() {
            return id;
        }
    }
}
