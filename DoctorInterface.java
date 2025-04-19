package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.DateTimeStringConverter;
import javax.swing.JOptionPane;

public class DoctorInterface extends GridPane {

	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "Lara.1034";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "ClinicProject";

	private TableView<Doctor> table;
	ObservableList<Doctor> data = FXCollections.observableArrayList();

	private TextField txDoctorID;
	private TextField txFirstName;
	private TextField txMidName;
	private TextField txLastName;
	private TextField txSpecialty;
	private TextField txGender;
	private TextField txPhoneNumber;
	private TextField txEmail;
	private TextField txAddress;
	private TextField txWorkHours;

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	public void table_load() {
		try {
			table.getColumns().clear(); // Clear previous columns
			pst = con.prepareStatement("SELECT * FROM Doctor");
			rs = pst.executeQuery();

			// Define table columns
			TableColumn<Doctor, Integer> doctorIDColumn = new TableColumn<>("Doctor ID");
			doctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));

			TableColumn<Doctor, String> firstNameColumn = new TableColumn<>("First Name");
			firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

			TableColumn<Doctor, String> midNameColumn = new TableColumn<>("Middle Name");
			midNameColumn.setCellValueFactory(new PropertyValueFactory<>("midName"));

			TableColumn<Doctor, String> lastNameColumn = new TableColumn<>("Last Name");
			lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

			TableColumn<Doctor, String> specialtyColumn = new TableColumn<>("Specialty");
			specialtyColumn.setCellValueFactory(new PropertyValueFactory<>("specialty"));

			TableColumn<Doctor, String> genderColumn = new TableColumn<>("Gender");
			genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

			TableColumn<Doctor, String> PhoneNumberColumn = new TableColumn<>("Phone Numbers");
			PhoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

			TableColumn<Doctor, String> emailColumn = new TableColumn<>("Email");
			emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

			TableColumn<Doctor, String> addressColumn = new TableColumn<>("Address");
			addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));

			TableColumn<Doctor, String> workHoursColumn = new TableColumn<>("Work Hours");
			workHoursColumn.setCellValueFactory(new PropertyValueFactory<>("workHours"));

			table.getColumns().addAll(doctorIDColumn, firstNameColumn, midNameColumn, lastNameColumn, specialtyColumn,
					genderColumn, PhoneNumberColumn, emailColumn, addressColumn, workHoursColumn);

			// Load data
			data = FXCollections.observableArrayList();

			while (rs.next()) {
				int doctorID = rs.getInt("doctorID");
				String firstName = rs.getString("firstName");
				String midName = rs.getString("midName");
				String lastName = rs.getString("lastName");
				String specialty = rs.getString("specialty");
				String gender = rs.getString("gender");
				String PhoneNumber = rs.getString("phoneNumber");
				String email = rs.getString("email");
				String address = rs.getString("address");
				String workHours = rs.getString("workHours");

				Doctor doctor = new Doctor(doctorID, firstName, midName, lastName, specialty, gender, PhoneNumber,
						email, address, workHours);
				data.add(doctor);
			}

			table.setItems(data);
			table.refresh(); 

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void connect() {
		try {
			String dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?useSSL=false";
			con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
			System.out.println("Connected to the database successfully.");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	public Stage getDoctor() {
		connect();
		table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table_load();

		GridPane formPane = new GridPane();
		formPane.setHgap(10);
		formPane.setVgap(10);
		formPane.setPadding(new Insets(10));

		Label labDoctorID = new Label("Doctor ID:");
		txDoctorID = new TextField();

		Label labFirstName = new Label("First Name:");
		txFirstName = new TextField();

		Label labMidName = new Label("Middle Name:");
		txMidName = new TextField();

		Label labLastName = new Label("Last Name:");
		txLastName = new TextField();

		Label labSpecialty = new Label("Specialty:");
		txSpecialty = new TextField();

		Label labGender = new Label("Gender:");
		txGender = new TextField();

		Label labPhoneNumber = new Label("Phone Numbers:");
		txPhoneNumber = new TextField();

		Label labEmail = new Label("Email:");
		txEmail = new TextField();

		Label labAddress = new Label("Address:");
		txAddress = new TextField();

		Label labWorkHours = new Label("Work Hours:");
		txWorkHours = new TextField();

		Button btnSave = new Button("Save");
		btnSave.setOnAction(e -> {
			String doctorID, firstName, midName, lastName, specialty, gender, PhoneNumber, email, address, workHours;
			doctorID = txDoctorID.getText();
			firstName = txFirstName.getText();
			midName = txMidName.getText();
			lastName = txLastName.getText();
			specialty = txSpecialty.getText();
			gender = txGender.getText();
			PhoneNumber = txPhoneNumber.getText();
			email = txEmail.getText();
			address = txAddress.getText();
			workHours = txWorkHours.getText();

			try {
				pst = con.prepareStatement(
						"INSERT INTO Doctor (doctorID, firstName, midName, lastName, specialty, gender, PhoneNumber, email, address, workHours) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				pst.setString(1, doctorID);
				pst.setString(2, firstName);
				pst.setString(3, midName);
				pst.setString(4, lastName);
				pst.setString(5, specialty);
				pst.setString(6, gender);
				pst.setString(7, PhoneNumber);
				pst.setString(8, email);
				pst.setString(9, address);
				pst.setString(10, workHours);

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Added!");

				table.refresh();
				txDoctorID.setText("");
				txFirstName.setText("");
				txMidName.setText("");
				txLastName.setText("");
				txSpecialty.setText("");
				txGender.setText("");
				txPhoneNumber.setText("");
				txEmail.setText("");
				txAddress.setText("");
				txWorkHours.setText("");
				txDoctorID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Label labSearchID = new Label("Doctor ID:");
		TextField textField = new TextField();
		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(e -> {
			String id = textField.getText();
			try {
				pst = con.prepareStatement("SELECT * FROM Doctor WHERE doctorID = ?");
				pst.setString(1, id);
				rs = pst.executeQuery();
				if (rs.next()) {
					String doctorID = rs.getString(1);
					String firstName = rs.getString(2);
					String midName = rs.getString(3);
					String lastName = rs.getString(4);
					String specialty = rs.getString(5);
					String gender = rs.getString(6);
					String phoneNumber = rs.getString(7);
					String email = rs.getString(8);
					String address = rs.getString(9);
					String workHours = rs.getString(10);

					txDoctorID.setText(doctorID);
					txFirstName.setText(firstName);
					txMidName.setText(midName);
					txLastName.setText(lastName);
					txSpecialty.setText(specialty);
					txGender.setText(gender);
					txPhoneNumber.setText(phoneNumber);
					txEmail.setText(email);
					txAddress.setText(address);
					txWorkHours.setText(workHours);

				} else {
					txDoctorID.setText("");
					txFirstName.setText("");
					txMidName.setText("");
					txLastName.setText("");
					txSpecialty.setText("");
					txGender.setText("");
					txPhoneNumber.setText("");
					txEmail.setText("");
					txAddress.setText("");
					txWorkHours.setText("");

					JOptionPane.showMessageDialog(null, "Invalid Doctor ID!");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});

		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction(e -> {
			String doctorID, firstName, midName, lastName, specialty, gender, PhoneNumber, email, address, workHours;
			doctorID = txDoctorID.getText();
			firstName = txFirstName.getText();
			midName = txMidName.getText();
			lastName = txLastName.getText();
			specialty = txSpecialty.getText();
			gender = txGender.getText();
			PhoneNumber = txPhoneNumber.getText();
			email = txEmail.getText();
			address = txAddress.getText();
			workHours = txWorkHours.getText();
			try {
				pst = con.prepareStatement(
						"UPDATE Doctor SET firstName = ?, midName = ?, lastName = ?, specialty = ?, gender = ?, PhoneNumber = ?, email = ?, address = ?, workHours = ? WHERE doctorID = ?");
				pst.setString(1, firstName);
				pst.setString(2, midName);
				pst.setString(3, lastName);
				pst.setString(4, specialty);
				pst.setString(5, gender);
				pst.setString(6, PhoneNumber);
				pst.setString(7, email);
				pst.setString(8, address);
				pst.setString(9, workHours);
				pst.setString(10, doctorID);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Updated!");
				table_load();
				table.refresh();
				txDoctorID.setText("");
				txFirstName.setText("");
				txMidName.setText("");
				txLastName.setText("");
				txSpecialty.setText("");
				txGender.setText("");
				txPhoneNumber.setText("");
				txEmail.setText("");
				txAddress.setText("");
				txWorkHours.setText("");
				txDoctorID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(e -> {
			String doctorID;
			doctorID = txDoctorID.getText();
			try {
				pst = con.prepareStatement("DELETE FROM Doctor WHERE doctorID = ?");
				pst.setString(1, doctorID);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Deleted!");
				table_load();
				table.refresh();
				txDoctorID.setText("");
				txFirstName.setText("");
				txMidName.setText("");
				txLastName.setText("");
				txSpecialty.setText("");
				txGender.setText("");
				txPhoneNumber.setText("");
				txEmail.setText("");
				txAddress.setText("");
				txWorkHours.setText("");
				txDoctorID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		formPane.add(labDoctorID, 0, 0);
		formPane.add(txDoctorID, 1, 0);

		formPane.add(labFirstName, 0, 1);
		formPane.add(txFirstName, 1, 1);

		formPane.add(labMidName, 0, 2);
		formPane.add(txMidName, 1, 2);

		formPane.add(labLastName, 0, 3);
		formPane.add(txLastName, 1, 3);

		formPane.add(labSpecialty, 0, 4);
		formPane.add(txSpecialty, 1, 4);

		formPane.add(labGender, 0, 5);
		formPane.add(txGender, 1, 5);

		formPane.add(labPhoneNumber, 0, 6);
		formPane.add(txPhoneNumber, 1, 6);

		formPane.add(labEmail, 0, 7);
		formPane.add(txEmail, 1, 7);

		formPane.add(labAddress, 0, 8);
		formPane.add(txAddress, 1, 8);

		formPane.add(labWorkHours, 0, 9);
		formPane.add(txWorkHours, 1, 9);

		formPane.add(btnSave, 1, 10);
		formPane.add(btnUpdate, 1, 11);
		formPane.add(btnDelete, 1, 12);

		HBox searchBox = new HBox(10);
		searchBox.getChildren().addAll(labSearchID, textField, btnSearch);
		formPane.add(searchBox, 1, 13);

		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth(30);
		ColumnConstraints column2 = new ColumnConstraints();
		column2.setPercentWidth(70);
		formPane.getColumnConstraints().addAll(column1, column2);

		GridPane.setHalignment(btnSave, HPos.RIGHT);
		GridPane.setHalignment(btnUpdate, HPos.RIGHT);
		GridPane.setHalignment(btnDelete, HPos.RIGHT);

		this.add(table, 0, 0);
		this.add(formPane, 1, 0);

		Scene scene = new Scene(this, 1100, 600);
		Stage stage = new Stage();
		stage.setTitle("Doctor Management");
		stage.setScene(scene);
		stage.show();

		return stage;
	}
}
