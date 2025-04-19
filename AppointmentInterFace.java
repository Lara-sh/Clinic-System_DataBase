package application;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DateTimeStringConverter;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class AppointmentInterFace extends GridPane {
	
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "Lara.1034";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "ClinicProject";


	private TableView<Appointment> table;
	ObservableList<Appointment> data = FXCollections.observableArrayList();
	private TableView<Appointment> table2;
	ObservableList<Appointment> data2 = FXCollections.observableArrayList();

	private TextField txAID;
	private TextField txDID;
	private TextField txNotes;
	private TextField txPID;

	private TextField txATime;
	private TextField txADate;

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	public void table_load() {
		try {
			pst = con.prepareStatement("SELECT * FROM Appointment");
			rs = pst.executeQuery();

// AppointmentID, DoctorID, Notes, PatientID, AppointmentDate, AppointmentTime

			TableColumn<Appointment, Integer> AppointmentIDColumn = new TableColumn<>("Appointment ID");
			AppointmentIDColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentID"));

			TableColumn<Appointment, Integer> idColumn = new TableColumn<>("Patient ID");
			idColumn.setCellValueFactory(new PropertyValueFactory<>("PatientID"));

			TableColumn<Appointment, Integer> AppointmentTimeColumn = new TableColumn<>("Appointment Time");
			AppointmentTimeColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentTime"));

			TableColumn<Appointment, String> NotesColumn = new TableColumn<>("Notes");
			NotesColumn.setCellValueFactory(new PropertyValueFactory<>("Notes"));

			TableColumn<Appointment, Integer> DoctorIDColumn = new TableColumn<>("Doctor ID");
			DoctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("DoctorID"));

			TableColumn<Appointment, Date> AppointmentDateColumn = new TableColumn<>("Appointment Date");
			AppointmentDateColumn.setCellValueFactory(new PropertyValueFactory<>("AppointmentDate"));
			AppointmentDateColumn.setCellFactory(column -> new TableCell<Appointment, Date>() {
				private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				@Override
				protected void updateItem(Date item, boolean empty) {
					super.updateItem(item, empty);
					if (item == null || empty) {
						setText(null);
					} else {
						LocalDate localDate = ((java.sql.Date) item).toLocalDate();
						String formattedDate = localDate.format(formatter);
						setText(formattedDate);
					}
				}
			});

// idColumn, AppointmentIDColumn, AppointmentTimeColumn, NotesColumn, DoctorIDColumn, AppointmentDateColumn 
			table2.getColumns().addAll(AppointmentIDColumn, DoctorIDColumn, NotesColumn, idColumn,
					AppointmentDateColumn, AppointmentTimeColumn);

			data2 = FXCollections.observableArrayList(); // Initialize the data list

			while (rs.next()) {
				int patientID = rs.getInt("PatientID");
				int AppointmentID = rs.getInt("AppointmentID");
				Time AppointmentTime = rs.getTime("AppointmentTime");
				String notes = rs.getString("Notes");
				int doctorID = rs.getInt("DoctorID");
				Date AppointmentDate = rs.getDate("AppointmentDate");

				Appointment appointment = new Appointment(AppointmentID, doctorID, notes, patientID, AppointmentDate,
						AppointmentTime);
				data2.add(appointment);
			}

			table2.setItems(data2);

		} catch (SQLException e) {
			e.printStackTrace();
		}
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
	public Stage getApo() {
		connect();
		// table_load();

		// Get the controller instance
		table2 = new TableView<>();
		table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		// table2 = new TableView<>();
		// Initialize the table
		table_load();

		// table2.setVisible(false);

		// Create a form for registration
		GridPane formPane = new GridPane();
		formPane.setHgap(10);
		formPane.setVgap(10);
		formPane.setPadding(new Insets(10));

		// AppointmentID, DoctorID, Notes, PatientID, AppointmentDate, AppointmentTime

		Label labAID = new Label("Appointment ID:");
		txAID = new TextField();

		Label labDID = new Label("Doctor ID:");
		txDID = new TextField();

		Label labNotes = new Label("Notes:");
		txNotes = new TextField();

		Label labPID = new Label("Patient ID:");
		txPID = new TextField();

		Label labADate = new Label("Appointment Date:");
		txADate = new TextField();

		Label labATime = new Label("Appointment Time:");
		txATime = new TextField();
////////////////////////////////////////////////////////////////

		Button btnSave = new Button("Save");
		btnSave.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button
		btnSave.setOnAction(e -> {
			String appointmentID, doctorID, notes, petientID, appointmentDate, appointmentTime;
			appointmentID = txAID.getText();
			doctorID = txDID.getText();
			notes = txNotes.getText();
			petientID = txPID.getText();
			appointmentDate = txADate.getText();
			appointmentTime = txATime.getText();

			try {
				pst = con.prepareStatement(
						"INSERT INTO Appointment (AppointmentID, DoctorID, Notes, PatientID, AppointmentDate, AppointmentTime)VALUES(?,?,?,?,?,?)");
				pst.setString(1, appointmentID);
				pst.setString(2, doctorID);
				pst.setString(3, notes);
				pst.setString(4, petientID);
				pst.setString(5, appointmentDate);
				pst.setString(6, appointmentTime);

				// System.out.println(pst.toString());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");

				table2.refresh();
				// table_load();

				// formPane.add(table2, 2, 0, 1, 9);
				// table.setVisible(false);
				txAID.setText("");
				txDID.setText("");
				;
				txNotes.setText("");
				txPID.setText("");
				txADate.setText("");
				txATime.setText("");
				txAID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Label labid = new Label("AppointmentID");
		TextField textField = new TextField();
		Button btnSearch = new Button("Search");
		btnSearch.setStyle("-fx-background-color: #FFC0CB;");
		btnSearch.setOnAction(e -> {
			String id = textField.getText();
			try {
				pst = con.prepareStatement(
						"SELECT AppointmentID, DoctorID, Notes, PatientID, AppointmentDate, AppointmentTime FROM Appointment  WHERE AppointmentID = ?");
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {

					String appointmentID = rs.getString(1);
					String doctorID = rs.getString(2);
					String notes = rs.getString(3);
					String petientID = rs.getString(4);
					String appointmentDate = rs.getString(5);
					String appointmentTime = rs.getString(6);

					txAID.setText(appointmentID);
					txDID.setText(doctorID);
					;
					txNotes.setText(notes);
					txPID.setText(petientID);
					txADate.setText(appointmentDate);
					txATime.setText(appointmentTime);

				} else {
					txAID.setText("");
					txDID.setText("");
					;
					txNotes.setText("");
					txPID.setText("");
					txADate.setText("");
					txATime.setText("");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
		Button btnUpdate = new Button("Update");
		btnUpdate.setStyle("-fx-background-color: #FFC0CB;");
		btnUpdate.setOnAction(e -> {
			String appointmentID = txAID.getText();
			String doctorID = txDID.getText();
			String notes = txNotes.getText();
			String petientID = txPID.getText();
			String appointmentDate = txADate.getText();
			String appointmentTime = txATime.getText();

			try {
				pst = con.prepareStatement(
						"UPDATE Appointment  SET  DoctorID=? , Notes=?, PatientID=?, AppointmentDate=?, AppointmentTime=? WHERE AppointmentID=?");
				pst.setString(1, doctorID);
				pst.setString(2, notes);
				pst.setString(3, petientID);
				pst.setString(4, appointmentDate);
				pst.setString(5, appointmentTime);
				pst.setString(6, appointmentID);

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Updated!");
				// table_load();
				table2.refresh();
				System.out.println("Doneeee");
				// formPane.add(table2, 2, 0, 1, 9);
				// table.setVisible(false);
				txAID.setText("");
				txDID.setText("");
				;
				txNotes.setText("");
				txPID.setText("");
				txADate.setText("");
				txATime.setText("");
				txAID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Button btnDelete = new Button("Delete");
		btnDelete.setStyle("-fx-background-color: #FFC0CB;");
		btnDelete.setOnAction(e -> {
			String appointmentID;
			appointmentID = textField.getText();
			System.out.println("llllllllllllll");

			try {
				pst = con.prepareStatement("delete from Appointment  where AppointmentID =?");

				pst.setString(1, appointmentID);
				pst.executeUpdate();
				System.out.println("llllllllllllll");
				JOptionPane.showMessageDialog(null, "Record delete!!!!!");
				// table_load();
				table2.refresh();

				// table.setVisible(false);
				txAID.setText("");
				txDID.setText("");
				;
				txNotes.setText("");
				txPID.setText("");
				txADate.setText("");
				txATime.setText("");
				txAID.requestFocus();

			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		});
		Button btnClear = new Button("Clear");
		btnClear.setStyle("-fx-background-color: #FFC0CB;");
		btnClear.setOnAction(e -> {
			txAID.setText("");
			txDID.setText("");
			;
			txNotes.setText("");
			txPID.setText("");
			txADate.setText("");
			txATime.setText("");
			txAID.requestFocus();
			System.out.println("Done");
		});

		// patient.add(patient);
		Button btnExit = new Button("Exit");
		btnClear.setStyle("-fx-background-color: #FFC0CB;");
		
		btnExit.setOnAction(e -> {
			System.exit(0);
		});
		btnExit.setStyle("-fx-background-color: #FFC0CB;");
		
		formPane.add(table2, 2, 0, 1, 9);
		// formPane.add(table2, 2, 0, 1, 9);
		// Add form elements to the GridPane
		formPane.add(labAID, 0, 0);
		formPane.add(txAID, 1, 0);
		formPane.add(labDID, 0, 1);
		formPane.add(txDID, 1, 1);
		formPane.add(labNotes, 0, 2);
		formPane.add(txNotes, 1, 2);
		formPane.add(labPID, 0, 3);
		formPane.add(txPID, 1, 3);
		formPane.add(labADate, 0, 4);
		formPane.add(txADate, 1, 4);
		formPane.add(labATime, 0, 5);
		formPane.add(txATime, 1, 5);
		// formPane.add(labDiagnosis, 0, 6);

		HBox h = new HBox(10);
		h.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);
		// Add buttons to the GridPane
		formPane.add(labid, 0, 11);
		formPane.add(textField, 1, 11);
		formPane.add(h, 2, 12);
		// Close the statement and result set

		Label titleLabel = new Label("Appointment Management System");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		// Add the title label to the GridPane
		// formPane.add(titleLabel, 0, 0, 2, 1);
		Stage primaryStage = new Stage();
		Button goBill = new Button("MAIN PAGE");
		goBill.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		goBill.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		formPane.add(goBill, 13, 16);
		goBill.setOnAction(e -> {
			primaryStage.close();
		});
		goBill.setStyle("-fx-background-color: #FFC0CB;");
		// Set column constraints for center alignment
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setHalignment(HPos.CENTER);
		formPane.getColumnConstraints().add(columnConstraints);
		primaryStage.setTitle("Appointment Management System");
		// Close the connection
		// con.close();
		formPane.setStyle("-fx-background-color: lightgray;"); // Light gray background for the root pane
		Scene scene = new Scene(formPane, 1100, 600);

		primaryStage.setScene(scene);
		primaryStage.show();
		return primaryStage;
	}

}
