package application;

import javafx.scene.layout.GridPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import javax.swing.JOptionPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class PrescriptionInterface extends GridPane {
    
    private String dbURL;
    private String dbUsername = "root";
    private String dbPassword = "Lara.1034";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "ClinicProject";

    private TextField resultTextField;
    private TableView<Prescription> table;
    ObservableList<Prescription> data = FXCollections.observableArrayList();
    private TableView<Prescription> table2;
    ObservableList<Prescription> data2 = FXCollections.observableArrayList();

    private TextField txfMedication;
    private TextField txfDosage;
    private TextField txfQuantity;
    private TextField txfPatientID;
    private TextField txfDoctorID;
    private TextField txfDate;
    private TextField textField;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    // Style for buttons
    String buttonStyle = "-fx-background-color: #FFC0CB; -fx-font-size: 14px; -fx-padding: 10px; -fx-font-weight: bold;";

    public void table_load() {
        try {
            pst = con.prepareStatement("SELECT * FROM prescription");
            rs = pst.executeQuery();

            TableColumn<Prescription, Integer> prescriptionIdColumn = new TableColumn<>("Prescription ID");
            prescriptionIdColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionId"));

            TableColumn<Prescription, String> medicationNameColumn = new TableColumn<>("Medication Name");
            medicationNameColumn.setCellValueFactory(new PropertyValueFactory<>("medicationName"));

            TableColumn<Prescription, String> dosageColumn = new TableColumn<>("Dosage");
            dosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));

            TableColumn<Prescription, Integer> quantityColumn = new TableColumn<>("Quantity");
            quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

            TableColumn<Prescription, Integer> patientIdColumn = new TableColumn<>("Patient ID");
            patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

            TableColumn<Prescription, Integer> doctorIdColumn = new TableColumn<>("Doctor ID");
            doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));

            TableColumn<Prescription, LocalDate> dateColumn = new TableColumn<>("Date");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

            table2.getColumns().clear(); // Clear existing columns
            table2.getColumns().addAll(prescriptionIdColumn, medicationNameColumn, dosageColumn, quantityColumn,
                    patientIdColumn, doctorIdColumn, dateColumn);

            data2 = FXCollections.observableArrayList(); // Initialize the data list

            while (rs.next()) {
                int prescriptionId = rs.getInt("PrescriptionID");
                String medicationName = rs.getString("MedicationName");
                String dosage = rs.getString("Dosage");
                int quantity = rs.getInt("Quantity");
                int patientId = rs.getInt("PatientID");
                int doctorId = rs.getInt("DoctorID");
                Date date = rs.getDate("Date");

                Prescription prescription = new Prescription(prescriptionId, medicationName, dosage, quantity,
                        patientId, doctorId, date);
                data2.add(prescription);
            }

            table2.setItems(data2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void findMedicationName(int patientId) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/ClinicProject", "root", "khatib2532004");
            PreparedStatement pst = con.prepareStatement("SELECT MedicationName FROM Prescription WHERE PatientID = ?");
            pst.setInt(1, patientId);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                String medicationName = rs.getString("MedicationName");
                System.out.println("Medication Name: " + medicationName);
            } else {
                System.out.println("No medication found for patient with ID: " + patientId);
            }

            rs.close();
            pst.close();
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public String getMinDate() {
        String minDate = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MIN(Date) AS MinDate FROM PRESCRIPTION");

            if (rs.next()) {
                minDate = rs.getString("MinDate");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minDate;
    }

    public String getMaxDate() {
        String maxDate = null;
        try {
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT MAX(Date) AS MaxDate FROM PRESCRIPTION");

            if (rs.next()) {
                maxDate = rs.getString("MaxDate");
            }

            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return maxDate;
    }

    public void connect() {
        try {
            String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?useSSL=false";
            con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
            System.out.println("Correct connection");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Stage getPrescription() {
        connect();
        // table_load();
        resultTextField = new TextField();

        // Get the controller instance
        table2 = new TableView<>();
        table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        // Initialize the table
        table_load();

        // Create a form for registration
        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);
        formPane.setPadding(new Insets(10));

        Label labMedication = new Label("Medication Name:");
        txfMedication = new TextField();
        Label labDosage = new Label("Dosage:");
        txfDosage = new TextField();
        Label labQuantity = new Label("Quantity:");
        txfQuantity = new TextField();
        Label labPatientID = new Label("Patient ID:");
        txfPatientID = new TextField();
        Label labDoctorID = new Label("Doctor ID:");
        txfDoctorID = new TextField();
        Label labDate = new Label("Date:");
        txfDate = new TextField();
        
        Button btnSave = new Button("Save");
        btnSave.setStyle(buttonStyle);
        btnSave.setOnAction(e -> {
            String medicationName, dosage, patientId, doctorId, date;
            int quantity;
            medicationName = txfMedication.getText();
            dosage = txfDosage.getText();
            patientId = txfPatientID.getText();
            doctorId = txfDoctorID.getText();
            date = txfDate.getText();

            // Check if any of the text fields are empty
            if (medicationName.isEmpty() || dosage.isEmpty() || patientId.isEmpty() || doctorId.isEmpty() || date.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return; // Exit the event handler
            }

            try {
                // Parse the quantity as an integer
                quantity = Integer.parseInt(txfQuantity.getText());

                // Parse the patient ID as an integer
                int parsedPatientId = Integer.parseInt(patientId);

                // Parse the doctor ID as an integer
                int parsedDoctorId = Integer.parseInt(doctorId);

                pst = con.prepareStatement(
                        "INSERT INTO Prescription (MedicationName, Dosage, Quantity, PatientID, DoctorID, Date) VALUES (?,?,?,?,?,?)");
                pst.setString(1, medicationName);
                pst.setString(2, dosage);
                pst.setInt(3, quantity);
                pst.setInt(4, parsedPatientId);
                pst.setInt(5, parsedDoctorId);
                pst.setString(6, date);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Prescription added successfully!");

                table2.refresh();
                txfMedication.setText("");
                txfDosage.setText("");
                txfQuantity.setText("");
                txfPatientID.setText("");
                txfDoctorID.setText("");
                txfDate.setText("");
                txfMedication.requestFocus();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Quantity, Patient ID, and Doctor ID must be valid integers.");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        Label labPrescriptionID = new Label("Prescription ID:");
        textField = new TextField();
        Button btnSearch = new Button("Search");
        btnSearch.setStyle(buttonStyle);
        btnSearch.setOnAction(e -> {
            String id = textField.getText();
            try {
                pst = con.prepareStatement(
                        "SELECT MedicationName, Dosage, Quantity, PatientID, DoctorID, Date FROM Prescription WHERE PrescriptionID = ?");
                pst.setString(1, id);
                ResultSet rs = pst.executeQuery();
                if (rs.next()) {
                    String medicationName = rs.getString(1);
                    String dosage = rs.getString(2);
                    int quantity = rs.getInt(3);
                    int patientId = rs.getInt(4);
                    int doctorId = rs.getInt(5);
                    Date date = rs.getDate(6);

                    txfMedication.setText(medicationName);
                    txfDosage.setText(dosage);
                    txfQuantity.setText(String.valueOf(quantity));
                    txfPatientID.setText(String.valueOf(patientId));
                    txfDoctorID.setText(String.valueOf(doctorId));
                    txfDate.setText(date.toString());
                } else {
                    txfMedication.setText("");
                    txfDosage.setText("");
                    txfQuantity.setText("");
                    txfPatientID.setText("");
                    txfDoctorID.setText("");
                    txfDate.setText("");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        Label minDateLabel = new Label("Minimum Date: ");
        Label maxDateLabel = new Label("Maximum Date: ");

        Button minDateButton = new Button("Get Minimum Date");
        minDateButton.setStyle(buttonStyle);
        minDateButton.setOnAction(e -> {
            String minDate = getMinDate();
            minDateLabel.setText("Minimum Date: " + minDate);
        });

        Button maxDateButton = new Button("Get Maximum Date");
        maxDateButton.setStyle(buttonStyle);
        maxDateButton.setOnAction(e -> {
            String maxDate = getMaxDate();
            maxDateLabel.setText("Maximum Date: " + maxDate);
        });
        Button btnUpdate = new Button("Update");
        btnUpdate.setStyle(buttonStyle);
        btnUpdate.setOnAction(e -> {
            String id = textField.getText();
            String medicationName = txfMedication.getText();
            String dosage = txfDosage.getText();
            int quantity, patientId, doctorId;
            Date date = null;

            // Check if Quantity field is empty
            if (txfQuantity.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a quantity value.");
                return;
            } else {
                quantity = Integer.parseInt(txfQuantity.getText());
            }

            // Check if PatientID field is empty
            if (txfPatientID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a patient ID value.");
                return;
            } else {
                patientId = Integer.parseInt(txfPatientID.getText());
            }

            // Check if DoctorID field is empty
            if (txfDoctorID.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please enter a doctor ID value.");
                return;
            } else {
                doctorId = Integer.parseInt(txfDoctorID.getText());
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            try {
                date = dateFormat.parse(txfDate.getText());
            } catch (ParseException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Invalid date format. Please enter the date in yyyy-MM-dd format.");
                return;
            }

            try {
                PreparedStatement pst = con.prepareStatement(
                        "UPDATE Prescription SET MedicationName=?, Dosage=?, Quantity=?, PatientID=?, DoctorID=?, Date=? WHERE PrescriptionID=?");
                pst.setString(1, medicationName);
                pst.setString(2, dosage);
                pst.setInt(3, quantity);
                pst.setInt(4, patientId);
                pst.setInt(5, doctorId);
                pst.setDate(6, new java.sql.Date(date.getTime()));
                pst.setString(7, id);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Prescription Updated!");
                    table2.refresh();

                    txfMedication.setText("");
                    txfDosage.setText("");
                    txfQuantity.setText("");
                    txfPatientID.setText("");
                    txfDoctorID.setText("");
                    txfDate.setText("");
                    textField.setText("");
                    txfMedication.requestFocus();
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to update the prescription.");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
                JOptionPane.showMessageDialog(null, "An error occurred while updating the prescription.");
            }
        });

        Button btnDelete = new Button("Delete");
        btnDelete.setStyle(buttonStyle);
        btnDelete.setOnAction(e -> {
            String id = textField.getText();

            try {
                pst = con.prepareStatement("DELETE FROM Prescription WHERE PrescriptionID = ?");
                pst.setString(1, id);
                pst.executeUpdate();

                JOptionPane.showMessageDialog(null, "Prescription Deleted!");
                table2.refresh();

                txfMedication.setText("");
                txfDosage.setText("");
                txfQuantity.setText("");
                txfPatientID.setText("");
                txfDoctorID.setText("");
                txfDate.setText("");
                textField.setText("");
                txfMedication.requestFocus();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });
        Button btnClear = new Button("Clear");
        btnClear.setStyle(buttonStyle);
        btnClear.setOnAction(e -> {
            txfMedication.setText("");
            txfDosage.setText("");
            txfQuantity.setText("");
            txfPatientID.setText("");
            txfDoctorID.setText("");
            txfDate.setText("");
            txfMedication.requestFocus();
            System.out.println("Done");
        });
        Button btnExit = new Button("Exit");
        btnExit.setStyle(buttonStyle);
        btnExit.setOnAction(e -> {
            System.exit(0);
        });

        TextField resultTextField = new TextField();

        // Load the image
        Image image = new Image("file:C:\\Users\\ASUS\\Desktop\\Algo\\DataBase\\src\\application\\hh.jpg");
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(200);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);

        // Add the table, labels, text fields, and other elements to the formPane
        formPane.add(table2, 2, 0, 1, 9);
        formPane.add(labMedication, 0, 0);
        formPane.add(txfMedication, 1, 0);
        formPane.add(labDosage, 0, 1);
        formPane.add(txfDosage, 1, 1);
        formPane.add(labQuantity, 0, 2);
        formPane.add(txfQuantity, 1, 2);
        formPane.add(labPatientID, 0, 3);
        formPane.add(txfPatientID, 1, 3);
        formPane.add(labDoctorID, 0, 4);
        formPane.add(txfDoctorID, 1, 4);
        formPane.add(labDate, 0, 5);
        formPane.add(txfDate, 1, 5);

        formPane.add(minDateButton, 0, 6);
        formPane.add(minDateLabel, 1, 6);
        formPane.add(maxDateButton, 0, 7);
        formPane.add(maxDateLabel, 1, 7);
        HBox h = new HBox(10);
        h.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);
        formPane.add(labPrescriptionID, 0, 8);
        formPane.add(textField, 1, 8);
        formPane.add(h, 2, 9);
        HBox hPrescription = new HBox(10);
        hPrescription.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);

        formPane.add(hPrescription, 2, 15);

        // Set column constraints for center alignment
        ColumnConstraints columnConstraints = new ColumnConstraints();
        columnConstraints.setHalignment(HPos.CENTER);
        formPane.getColumnConstraints().add(columnConstraints);

        // Create a VBox for the image and add it to the formPane
        HBox imageBox = new HBox(imageView);
        imageBox.setAlignment(Pos.BOTTOM_LEFT);
        formPane.add(imageBox, 0,15);

        Stage primaryStage = new Stage();
        Button goBill = new Button("MAIN PAGE");
        goBill.setStyle(buttonStyle);
        goBill.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
        formPane.add(goBill, 12, 16);
        goBill.setOnAction(e -> {
            primaryStage.close();
        });
        primaryStage.setTitle("Prescription Management System");
        Scene scene = new Scene(formPane, 1200, 600, Color.LIGHTGRAY); // Set background color to light gray
		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}

}
