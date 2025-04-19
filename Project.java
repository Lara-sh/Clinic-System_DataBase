package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.util.converter.DateStringConverter;
import javafx.util.converter.DateTimeStringConverter;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewFocusModel;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class Project extends Application {
	private String dbURL;
	private String dbUsername = "root";
	private String dbPassword = "Lara.1034";
	private String URL = "127.0.0.1";
	private String port = "3306";
	private String dbName = "ClinicProject";

	private TableView<Bill> table;
	ObservableList<Bill> data = FXCollections.observableArrayList();
	private TableView<Patient> table2;
	ObservableList<Patient> data2 = FXCollections.observableArrayList();
	private TableView<Test> table3;
	ObservableList<Test> data3 = FXCollections.observableArrayList();
	private TableView<Diagnosis> table4;
	private ObservableList<Diagnosis> data4;

	Stage bill = new Stage();
	Stage test = new Stage();
	Stage patent = new Stage();
	Stage Main = new Stage();

	private TextField txFirst;
	private TextField txMid;
	private TextField txfLast;
	private TextField txfBir;

	private TextField txfGen;
	private TextField txfPhone;
	private TextField txfDiag;
	private TextField txfAge;
	private TextField textField;

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	DoctorInterface doctor;
	// DoctorInterface doctor;
	PrescriptionInterface prescrip;
	AppointmentInterFace appoint;
	DiagnosisInterface diagnosis;
	RoomInterface room;

	public static void main(String[] args) {
		launch(args);
	}

	public void table_load() {
		try {
			// Correcting the SQL query
			pst = con.prepareStatement("SELECT * FROM PatientsTable");
			rs = pst.executeQuery();

			// Clear existing items in the table
			table2.getItems().clear();
			data2.clear();

			while (rs.next()) {
				int patientID = rs.getInt("PatientID");
				String firstName = rs.getString("FirstName");
				String midName = rs.getString("MidName");
				String lastName = rs.getString("LastName");
				Date birthDate = rs.getDate("BirthDate");
				String gender = rs.getString("Gender");
				String phoneNumber = rs.getString("PhoneNumber");
				String diagnosis = rs.getString("Diagnosis");
				String ageGroup = rs.getString("AgeGroup");

				Patient patient = new Patient(patientID, firstName, midName, lastName, birthDate, gender, phoneNumber,
						diagnosis, ageGroup);
				data2.add(patient);
			}

			// Set the updated data to the table
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

	@Override
	public void start(Stage primaryStage) throws SQLException {
		connect();
		// Create a StackPane as the root pane
		StackPane rootPane = new StackPane();

		// Set the login scene as the only child of the StackPane
		Scene loginPane = getLogInScene();

		// Set the scene for the primary stage and show it
		primaryStage.setScene(loginPane);
		primaryStage.setTitle("Login");
		primaryStage.show();

	}

	private void handleLogin(String username, String password) {
		// Add your login logic here
		if (username.equals("root") && password.equals("Lara.1034")) {
			// Login successful, open the Bill and Test stages
			Stage patient = getMainPageScence();
			patient.show();
		} else {
			// Invalid credentials, show an error message
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Login Error");
			alert.setHeaderText(null);
			alert.setContentText("Invalid username or password. Please try again.");
			alert.showAndWait();
		}
	}

	public Scene getLogInScene() {
	    // Create a StackPane to layer background and content
	    StackPane root = new StackPane();

	    // Create a background image
	    BackgroundImage backgroundImage = new BackgroundImage(
	        new Image("file:C:\\Users\\ASUS\\Desktop\\Algo\\DataBase\\src\\application\\Clinic.jpg"), // Replace with your image path
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundPosition.CENTER,
	        new BackgroundSize(100, 100, true, true, true, true)
	    );
	    root.setBackground(new Background(backgroundImage));

	    // Create a GridPane for login components
	    GridPane gridPane = new GridPane();
	    gridPane.setAlignment(Pos.CENTER); // Center the content
	    gridPane.setPadding(new Insets(20)); // Add padding
	    gridPane.setHgap(10); // Set horizontal gap between elements
	    gridPane.setVgap(10); // Set vertical gap between elements

	    // Create a welcome message
	    Text welcomeText = new Text("Welcome to Clinic System");
	    welcomeText.setFill(new LinearGradient(
	            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
	            new Stop(0, Color.DARKBLUE), 
	            new Stop(1, Color.LIGHTBLUE)
	        ));
	    welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 36)); // Set font and size
	    GridPane.setColumnSpan(welcomeText, 2); // Span across two columns
	    gridPane.add(welcomeText, 0, 0);

	    // Create UI components
	    Label lblUsername = new Label("Username:");
	    lblUsername.setTextFill(new LinearGradient(
	            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
	            new Stop(0, Color.DARKBLUE), 
	            new Stop(1, Color.LIGHTBLUE)
	        ));
	    lblUsername.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Set font and size
	   
	    TextField txtUsername = new TextField();
	    Label lblPassword = new Label("Password:");
	    lblPassword.setTextFill(new LinearGradient(
	            0, 0, 1, 0, true, CycleMethod.NO_CYCLE,
	            new Stop(0, Color.DARKBLUE), 
	            new Stop(1, Color.LIGHTBLUE)
	        ));
	    lblPassword.setFont(Font.font("Arial", FontWeight.BOLD, 28)); // Set font and size
	    
	    PasswordField txtPassword = new PasswordField();
	    Button btnLogin = new Button("Login");

	    // Style the button
	    btnLogin.setMaxWidth(200);
	    btnLogin.setFont(Font.font("Arial", FontWeight.BOLD, 24)); // Set font and size
	    btnLogin.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");

	    // Configure the login button action
	    btnLogin.setOnAction(e -> {
	        String username = txtUsername.getText();
	        String password = txtPassword.getText();
	        handleLogin(username, password);
	    });

	    // Add components to the GridPane
	    gridPane.add(lblUsername, 0, 1);
	    gridPane.add(txtUsername, 1, 1);
	    gridPane.add(lblPassword, 0, 2);
	    gridPane.add(txtPassword, 1, 2);
	    gridPane.add(btnLogin, 1, 3);

	    // Add the GridPane to the StackPane
	    root.getChildren().add(gridPane);

	    // Create and return the Scene
	    return new Scene(root, 800, 600); // Adjust the width and height as needed
	}


	public Stage getMainPageScence() {
	    // Stage primaryStage = new Stage();
	    Main.setTitle("Clinic System");

	    // Root layout with a background
	    BorderPane rootPane = new BorderPane();
	    rootPane.setPadding(new Insets(20));

	    // Add background
	    BackgroundImage backgroundImage = new BackgroundImage(
	        new Image("file:C:\\Users\\ASUS\\Desktop\\Algo\\DataBase\\src\\application\\Clin.jpg"), // Update with your path
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundRepeat.NO_REPEAT,
	        BackgroundPosition.CENTER,
	        new BackgroundSize(100, 100, true, true, true, true)
	    );
	    rootPane.setBackground(new Background(backgroundImage));

	    // Header text
	    Text headerText = new Text("Select the process");
	    headerText.setFont(Font.font("Verdana", FontWeight.BOLD, 28));
	    headerText.setFill(Color.BLACK);
	    headerText.setStyle("-fx-effect: dropshadow(gaussian, grey, 3, 0.5, 2, 2);");
	    HBox headerBox = new HBox(headerText);
	    headerBox.setAlignment(Pos.CENTER);
	    headerBox.setPadding(new Insets(20, 0, 10, 0)); // Add padding to move the text down slightly
	    rootPane.setTop(headerBox);

	    // Create buttons
	    Button btnAppointmentTable = new Button("Appointment View");
	    Button btnRoomTable = new Button("DoctorRoom View");
	    Button btnPatientTable = new Button("Patient View");
	    Button btnBill = new Button("Bill View");
	    Button btnTest = new Button("Test View");
	    Button btnDoctor = new Button("Doctor View");
	    Button btnDiagnosis = new Button("Diagnosis View");
	    Button btnPrescription = new Button("Prescription View");
	    Button btnGoodbye = new Button("Goodbye System");

	    // Style all buttons
	    Button[] buttons = {
	        btnAppointmentTable, btnRoomTable, btnPatientTable, btnBill,
	        btnTest, btnDoctor, btnDiagnosis, btnPrescription, btnGoodbye
	    };
	    for (Button button : buttons) {
	        button.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 14));
	        button.setStyle("-fx-background-color: white; -fx-border-color: darkblue; -fx-border-width: 2;");
	        button.setMaxWidth(200);
	        button.setMinHeight(50);
	    }

	    // Layout for buttons
	    VBox leftBox = new VBox(10, btnPatientTable, btnBill, btnTest, btnDoctor);
	    VBox rightBox = new VBox(10, btnDiagnosis, btnPrescription, btnAppointmentTable, btnRoomTable);
	    leftBox.setAlignment(Pos.CENTER);
	    rightBox.setAlignment(Pos.CENTER);

	    // Add buttons to the center layout
	    HBox centerBox = new HBox(50, leftBox, rightBox);
	    centerBox.setAlignment(Pos.CENTER);
	    rootPane.setCenter(centerBox);

	    // "Goodbye" button at the bottom
	    HBox bottomBox = new HBox(btnGoodbye);
	    bottomBox.setAlignment(Pos.CENTER);
	    bottomBox.setPadding(new Insets(10, 0, 0, 0)); // Adjust padding to move the button up
	    rootPane.setBottom(bottomBox);

	    // Button actions
	    btnGoodbye.setOnAction(e -> System.exit(0));
	    btnPatientTable.setOnAction(e -> getPatientScence());
	    btnBill.setOnAction(e -> getBillScence());
	    btnTest.setOnAction(e -> getTestScence());
	    btnDoctor.setOnAction(e -> {
	    	DoctorInterface doctor = new DoctorInterface();
	        doctor.getDoctor();
	    });
	    btnDiagnosis.setOnAction(e -> {
	        diagnosis = new DiagnosisInterface();
	        diagnosis.getDiag();
	    });
	    btnPrescription.setOnAction(e -> {
	        prescrip = new PrescriptionInterface();
	        prescrip.getPrescription();
	    });
	    btnAppointmentTable.setOnAction(e -> {
	        appoint = new AppointmentInterFace();
	        appoint.getApo();
	    });
	    btnRoomTable.setOnAction(e -> {
	        room = new RoomInterface();
	        room.getRoom();
	    });

	    // Create and set the scene
	    Scene scene = new Scene(rootPane, 1200, 600);
	    Main.setScene(scene);

	    return Main;
	}

	private Button createButtonWithImage(String text, String imagePath) {
		Button button = new Button(text);

		// Load the image for the button
		Image image = new Image(imagePath);
		ImageView imageView = new ImageView(image);
		imageView.setFitWidth(60);
		imageView.setFitHeight(60);

		// Set the image as the graphic for the button
		button.setGraphic(imageView);

		return button;
	}

	public Stage getPatientScence() {
		// Get the controller instance
		table2 = new TableView<>();
		table2.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		TableColumn<Patient, Integer> idColumn = new TableColumn<>("Patient ID");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("PatientID"));

		TableColumn<Patient, String> firstNameColumn = new TableColumn<>("First Name");
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

		TableColumn<Patient, String> midNameColumn = new TableColumn<>("Middle Name");
		midNameColumn.setCellValueFactory(new PropertyValueFactory<>("midName"));

		TableColumn<Patient, String> lastNameColumn = new TableColumn<>("Last Name");
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

		TableColumn<Patient, Date> birthDateColumn = new TableColumn<>("Birth Date");
		birthDateColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
		birthDateColumn.setCellFactory(column -> new TableCell<Patient, Date>() {
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

		TableColumn<Patient, String> genderColumn = new TableColumn<>("Gender");
		genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

		TableColumn<Patient, String> phoneNumberColumn = new TableColumn<>("Phone Number");
		phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

		TableColumn<Patient, String> diagnosisColumn = new TableColumn<>("Diagnosis");
		diagnosisColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosis"));

		TableColumn<Patient, String> ageGroupColumn = new TableColumn<>("Age Group");
		ageGroupColumn.setCellValueFactory(new PropertyValueFactory<>("ageGroup"));

		table2.getColumns().addAll(idColumn, firstNameColumn, midNameColumn, lastNameColumn, birthDateColumn,
				genderColumn, phoneNumberColumn, diagnosisColumn, ageGroupColumn);

		data2 = FXCollections.observableArrayList(); // Initialize the data list
		// table2 = new TableView<>();
		// Initialize the table
		table_load();

		// table2.setVisible(false);

		// Create a form for registration
		GridPane formPane = new GridPane();
		formPane.setHgap(10);
		formPane.setVgap(10);
		formPane.setPadding(new Insets(10));

		Label labFirst = new Label("First Name:");
		txFirst = new TextField();
		Label labMid = new Label("Mid Name:");
		txMid = new TextField();
		Label labLast = new Label("Last Name:");
		txfLast = new TextField();
		Label labBirth = new Label("Birth Date:");
		txfBir = new TextField();
		Label labGender = new Label("Gender:");
		txfGen = new TextField();
		Label labPhone = new Label("Phone Number:");
		txfPhone = new TextField();
		Label labDiagnosis = new Label("Diagnosis:");
		txfDiag = new TextField();
		Label labAge = new Label("Age Group:");
		txfAge = new TextField();

		labFirst.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labMid.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labLast.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labBirth.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labGender.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labPhone.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labDiagnosis.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		labAge.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		Button btnSave = new Button("Save");
		btnSave.setOnAction(e -> {
			String firstname, midname, lastname, birthdat, gender, phone, diag, age;
			firstname = txFirst.getText();
			midname = txMid.getText();
			lastname = txfLast.getText();
			birthdat = txfBir.getText();
			gender = txfGen.getText();
			phone = txfPhone.getText();
			diag = txfDiag.getText();
			age = txfAge.getText();
			try {
				pst = con.prepareStatement(
						"INSERT INTO PatientsTable (FirstName, MidName, LastName, BirthDate, Gender, PhoneNumber, Diagnosis, AgeGroup)VALUES(?,?,?,?,?,?,?,?)");
				pst.setString(1, firstname);
				pst.setString(2, midname);
				pst.setString(3, lastname);
				pst.setString(4, birthdat);
				pst.setString(5, gender);
				pst.setString(6, phone);
				pst.setString(7, diag);
				pst.setString(8, age);

				// System.out.println(pst.toString());

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Addedddd!!!!!");

				table2.refresh();
				table_load();

				// formPane.add(table2, 2, 0, 1, 9);
				// table.setVisible(false);
				txFirst.setText("");
				txMid.setText("");
				;
				txfLast.setText("");
				txfBir.setText("");
				txfGen.setText("");
				txfPhone.setText("");
				txfDiag.setText("");
				;
				txfAge.setText("");
				txFirst.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Label labid = new Label("PatientID");
		textField = new TextField();
		Button btnSearch = new Button("Search");
		labid.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text

		btnSearch.setOnAction(e -> {
			String id = textField.getText();
			try {
				pst = con.prepareStatement(
						"SELECT FirstName, MidName, LastName, BirthDate, Gender, PhoneNumber, Diagnosis, AgeGroup FROM PatientsTable WHERE PatientID = ?");
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					String firstname = rs.getString(1);
					String midname = rs.getString(2);
					String lastname = rs.getString(3);
					String birthdat = rs.getString(4);
					String gender = rs.getString(5);
					String phone = rs.getString(6);
					String diag = rs.getString(7);
					String age = rs.getString(8);

					txFirst.setText(firstname);
					txMid.setText(midname);
					txfLast.setText(lastname);
					txfBir.setText(birthdat);
					txfGen.setText(gender);
					txfPhone.setText(phone);
					txfDiag.setText(diag);
					txfAge.setText(age);
				} else {
					txFirst.setText("");
					txMid.setText("");
					txfLast.setText("");
					txfBir.setText("");
					txfGen.setText("");
					txfPhone.setText("");
					txfDiag.setText("");
					txfAge.setText("");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});
		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction(e -> {
			String id = textField.getText();
			String firstname = txFirst.getText();
			String midname = txMid.getText();
			String lastname = txfLast.getText();
			String birthdat = txfBir.getText();
			String gender = txfGen.getText();
			String phone = txfPhone.getText();
			String diag = txfDiag.getText();
			String age = txfAge.getText();

			try {
				pst = con.prepareStatement(
						"UPDATE PatientsTable SET FirstName=?, MidName=?, LastName=?, BirthDate=?, Gender=?, PhoneNumber=?, Diagnosis=?, AgeGroup=? WHERE PatientID=?");
				pst.setString(1, firstname);
				pst.setString(2, midname);
				pst.setString(3, lastname);
				pst.setString(4, birthdat);
				pst.setString(5, gender);
				pst.setString(6, phone);
				pst.setString(7, diag);
				pst.setString(8, age);
				pst.setString(9, id);

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Updated!");
				table_load();
				table2.refresh();
				System.out.println("Doneeee");
				// formPane.add(table2, 2, 0, 1, 9);
				// table.setVisible(false);
				txFirst.setText("");
				txMid.setText("");
				txfLast.setText("");
				txfBir.setText("");
				txfGen.setText("");
				txfPhone.setText("");
				txfDiag.setText("");
				txfAge.setText("");
				textField.setText("");
				txFirst.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(e -> {
			String id;
			id = textField.getText();
			System.out.println("llllllllllllll");

			try {
				pst = con.prepareStatement("delete from PatientsTable  where PatientID =?");

				pst.setString(1, id);
				pst.executeUpdate();
				System.out.println("llllllllllllll");
				JOptionPane.showMessageDialog(null, "Record delete!!!!!");
				table2.refresh();
				table_load();
				System.out.println("Done");

				// table.setVisible(false);
				txFirst.setText("");
				txMid.setText("");
				txfLast.setText("");
				txfBir.setText("");
				txfGen.setText("");
				txfPhone.setText("");
				txfDiag.setText("");
				txfAge.setText("");
				txFirst.requestFocus();
			} catch (SQLException e1) {

				e1.printStackTrace();
			}

		});
		Button btnClear = new Button("Clear");
		btnClear.setOnAction(e -> {
			txFirst.setText("");
			txMid.setText("");
			txfLast.setText("");
			txfBir.setText("");
			txfGen.setText("");
			txfPhone.setText("");
			txfDiag.setText("");
			txfAge.setText("");
			txFirst.requestFocus();
			System.out.println("Done");
		});

		// patient.add(patient);
		Button btnExit = new Button("Exit");
		btnExit.setOnAction(e -> {
			System.exit(0);
		});

		formPane.add(table2, 2, 0, 1, 9);
		// formPane.add(table2, 2, 0, 1, 9);
		// Add form elements to the GridPane
		formPane.add(labFirst, 0, 0);
		formPane.add(txFirst, 1, 0);
		formPane.add(labMid, 0, 1);
		formPane.add(txMid, 1, 1);
		formPane.add(labLast, 0, 2);
		formPane.add(txfLast, 1, 2);
		formPane.add(labBirth, 0, 3);
		formPane.add(txfBir, 1, 3);
		formPane.add(labGender, 0, 4);
		formPane.add(txfGen, 1, 4);
		formPane.add(labPhone, 0, 5);
		formPane.add(txfPhone, 1, 5);
		formPane.add(labDiagnosis, 0, 6);
		formPane.add(txfDiag, 1, 6);
		formPane.add(labAge, 0, 7);
		formPane.add(txfAge, 1, 7);

		HBox h = new HBox(10);
		h.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);
		// Add buttons to the GridPane
		formPane.add(labid, 0, 11);
		formPane.add(textField, 1, 11);
		formPane.add(h, 2, 12);
		Button goBill = new Button("MAIN PAGE");
		goBill.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		goBill.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button
		formPane.add(goBill, 13, 16);
		goBill.setOnAction(e -> {
			patent.close();
		});
		// Close the statement and result set
		// rs.close();
		Label titleLabel = new Label("Patient Management System");
		titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
		btnSave.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnSave.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button

		btnSearch.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnSearch.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button

		btnUpdate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnUpdate.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button

		btnDelete.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnDelete.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button

		btnClear.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnClear.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button

		btnExit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12)); // Type of the text
		btnExit.setStyle("-fx-background-color: #FFC0CB;"); // Baby pink background for the button


		// Add the title label to the GridPane
		// formPane.add(titleLabel, 0, 0, 2, 1);
		formPane.setStyle("-fx-background-color: lightgray;");

		// Set column constraints for center alignment
		ColumnConstraints columnConstraints = new ColumnConstraints();
		columnConstraints.setHalignment(HPos.CENTER);
		formPane.getColumnConstraints().add(columnConstraints);
		patent.setTitle("Patient Management System");
		// Close the connection
		// con.close();
		Scene scene = new Scene(formPane, 1400, 600);
		patent.setScene(scene);
		patent.show();
		return patent;
	}



	public Stage getBillScence() {
		table = new TableView<>();
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table_load2();

		GridPane formPane2 = new GridPane();
		formPane2.setHgap(10);
		formPane2.setVgap(10);
		formPane2.setPadding(new Insets(10));

		TextField txbBillID;
		TextField txbPatientID;
		TextField txbLabCharges;
		TextField txbMedicineCharges;
		TextField txbDate;
		TextField txbPaymentMethod;

		Label labBillID = new Label("Bill ID:");
		txbBillID = new TextField();
		Label labPatientID = new Label("Patient ID:");
		txbPatientID = new TextField();
		Label labLabCharges = new Label("Lab Charges:");
		txbLabCharges = new TextField();
		Label labMedicineCharges = new Label("Medicine Charges:");
		txbMedicineCharges = new TextField();
		Label labDate = new Label("Date:");
		txbDate = new TextField();
		Label labPaymentMethod = new Label("Payment Method:");
		txbPaymentMethod = new TextField();

		Button btnSave = new Button("Save");
		btnSave.setOnAction(e -> {
			// String billID = txbBillID.getText();
			String patientID = txbPatientID.getText();
			String labCharges = txbLabCharges.getText();
			String medicineCharges = txbMedicineCharges.getText();
			String date = txbDate.getText();
			String paymentMethod = txbPaymentMethod.getText();

			try {
				pst = con.prepareStatement(
						"INSERT INTO Bill ( PatientID, LabCharges, MedicineCharges, Date, PaymentMethod) VALUES ( ?, ?, ?, ?, ?)");

				pst.setString(1, patientID);
				pst.setString(2, labCharges);
				pst.setString(3, medicineCharges);
				pst.setString(4, date);
				pst.setString(5, paymentMethod);

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Added!");

				table.refresh();
				table_load2();
				// txbBillID.setText("");
				txbPatientID.setText("");
				txbLabCharges.setText("");
				txbMedicineCharges.setText("");
				txbDate.setText("");
				txbPaymentMethod.setText("");
				txbPatientID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(e -> {
			String id = txbBillID.getText();
			try {
				pst = con.prepareStatement(
						"SELECT PatientID, LabCharges, MedicineCharges, Date, PaymentMethod FROM Bill WHERE BillID = ?");
				pst.setString(1, id);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					int patientID = rs.getInt("PatientID");
					double labCharges = rs.getDouble("LabCharges");
					double medicineCharges = rs.getDouble("MedicineCharges");
					Date date = rs.getDate("Date");
					String paymentMethod = rs.getString("PaymentMethod");

					txbPatientID.setText(String.valueOf(patientID));
					txbLabCharges.setText(String.valueOf(labCharges));
					txbMedicineCharges.setText(String.valueOf(medicineCharges));
					txbDate.setText(String.valueOf(date));
					txbPaymentMethod.setText(paymentMethod);
					JOptionPane.showMessageDialog(null, "Record Search!");

				} else {
					txbPatientID.setText("");
					txbLabCharges.setText("");
					txbMedicineCharges.setText("");
					txbDate.setText("");
					txbPaymentMethod.setText("");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});

		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction(e -> {
			String billID = txbBillID.getText();
			String patientID = txbPatientID.getText();
			String labCharges = txbLabCharges.getText();
			String medicineCharges = txbMedicineCharges.getText();
			String date = txbDate.getText();
			String paymentMethod = txbPaymentMethod.getText();

			try {
				pst = con.prepareStatement(
						"UPDATE Bill SET PatientID=?, LabCharges=?, MedicineCharges=?, Date=?, PaymentMethod=? WHERE BillID=?");
				pst.setString(1, patientID);
				pst.setString(2, labCharges);
				pst.setString(3, medicineCharges);
				pst.setString(4, date);
				pst.setString(5, paymentMethod);
				pst.setString(6, billID);

				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					table.refresh();
					table_load2();
					JOptionPane.showMessageDialog(null, "Record Updated!");

					System.out.println("Doneeee");
					txbPatientID.setText("");
					txbLabCharges.setText("");
					txbMedicineCharges.setText("");
					txbDate.setText("");
					txbPaymentMethod.setText("");
					txbPatientID.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Record not found or no changes made.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(e -> {
			String id = txbBillID.getText();
			try {
				pst = con.prepareStatement("DELETE FROM Bill WHERE BillID = ?");
				pst.setString(1, id);
				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					table.refresh();
					JOptionPane.showMessageDialog(null, "Record deleted!");
					table_load2();
					txbPatientID.setText("");
					txbLabCharges.setText("");
					txbMedicineCharges.setText("");
					txbDate.setText("");
					txbPaymentMethod.setText("");
					txbPatientID.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Record not found.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Button btnClear = new Button("Clear");
		btnClear.setOnAction(e -> {
			txbPatientID.setText("");
			txbLabCharges.setText("");
			txbMedicineCharges.setText("");
			txbDate.setText("");
			txbPaymentMethod.setText("");
			txbPatientID.requestFocus();
			System.out.println("Done");
		});

		// patient.add(patient);
		Button btnExit = new Button("Exit");
		btnExit.setOnAction(e -> {
			System.exit(0);
		});

		// goBill.setOnAction(e -> primaryStage.setScene(getBillScene()));

		Button main = new Button("MAIN PAGE");
		// goHome.setOnAction(e -> primaryStage.setScene(new Scene(new GridPane(), 400,
		// 400)));

		// Add table columns
		TableColumn<Bill, String> colBillID = new TableColumn<>("Bill ID");
		colBillID.setCellValueFactory(
				cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getBillID())));

		TableColumn<Bill, String> colPatientID = new TableColumn<>("Patient ID");
		colPatientID.setCellValueFactory(
				cellData -> new SimpleStringProperty(Integer.toString(cellData.getValue().getPatientID())));

		TableColumn<Bill, String> colLabCharges = new TableColumn<>("Lab Charges");
		colLabCharges.setCellValueFactory(
				cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getLabCharges())));

		TableColumn<Bill, String> colMedicineCharges = new TableColumn<>("Medicine Charges");
		colMedicineCharges.setCellValueFactory(
				cellData -> new SimpleStringProperty(Double.toString(cellData.getValue().getMedicineCharges())));

		TableColumn<Bill, String> colDate = new TableColumn<>("Date");
		colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDate().toString()));

		TableColumn<Bill, String> colPaymentMethod = new TableColumn<>("Payment Method");
		colPaymentMethod
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentMethod()));

		// Add columns to the table
		table.getColumns().addAll(colBillID, colPatientID, colLabCharges, colMedicineCharges, colDate,
				colPaymentMethod);
		colMedicineCharges.setPrefWidth(300);
		// Add table data
		// table_load();
		formPane2.add(table, 2, 0, 1, 9);
		// Add table to the formPane
		// formPane2.add(table, 0, 0, 2, 1);
		btnSave.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnSave.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		btnSearch.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnSearch.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		btnUpdate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnUpdate.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		btnDelete.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnDelete.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		btnExit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnExit.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		main.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 16));// type of The Text
		main.setStyle("-fx-background-color: #FFC0CB;");// Baby pink background for Button

		btnClear.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnClear.setStyle("-fx-background-color: #FFC0CB;");// Pink background for Button

		
		formPane2.add(labPatientID, 0, 0);
		formPane2.add(txbPatientID, 1, 0);
		formPane2.add(labLabCharges, 0, 1);
		formPane2.add(txbLabCharges, 1, 1);
		formPane2.add(labMedicineCharges, 0, 2);
		formPane2.add(txbMedicineCharges, 1, 2);
		formPane2.add(labDate, 0, 3);
		formPane2.add(txbDate, 1, 3);
		formPane2.add(labPaymentMethod, 0, 4);
		formPane2.add(txbPaymentMethod, 1, 4);

		labPatientID.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labLabCharges.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labMedicineCharges.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labDate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labPaymentMethod.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		labBillID.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnClear.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));// type of The Text
		btnClear.setStyle("-fx-background-color: white");// To determine the Color background for Button

		

		main.setOnAction(e -> {
			bill.close();
		});
		HBox h = new HBox(10);
		h.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);
		// Add buttons to the GridPane
		formPane2.add(labBillID, 0, 11);
		formPane2.add(txbBillID, 1, 11);
		formPane2.add(h, 2, 12);
		// HBox h2 = new HBox(10);
		// h2.getChildren().addAll(goHome,goBill);
		formPane2.add(main, 5, 16);
		formPane2.setStyle("-fx-background-color: lightgray;");
		bill.setTitle("Bill Management System");

		Scene scene2 = new Scene(formPane2, 1300, 600);
		bill.setScene(scene2);
		bill.show();
		return bill;
	}


	public void table_load2() {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = con.prepareStatement("SELECT * FROM Bill");
			rs = pst.executeQuery();
			table.getItems().clear();

			while (rs.next()) {
				int billID = rs.getInt("BillID");
				int patientID = rs.getInt("PatientID");
				double labCharges = rs.getDouble("LabCharges");
				double medicineCharges = rs.getDouble("MedicineCharges");
				java.sql.Date date = rs.getDate("Date");
				String paymentMethod = rs.getString("PaymentMethod");

				// Create a new Bill object with retrieved data
				Bill bill = new Bill(billID, patientID, labCharges, medicineCharges, date, paymentMethod);

				// Add the Bill object to the table
				table.getItems().add(bill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			// You can also log the exception to a file or display an error message to the
			// user
		} finally {
			// Properly close the resources
			try {
				if (rs != null)
					rs.close();
				if (pst != null)
					pst.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}
	}

	public Stage getTestScence() {
		table3 = new TableView<>();
		table3.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table_load3();

		GridPane formPane2 = new GridPane();
		formPane2.setHgap(10);
		formPane2.setVgap(10);
		formPane2.setPadding(new Insets(10));

		TextField txbTestCode;
		TextField txbPatientID;
		TextField txbDoctorID;
		TextField txbTestType;
		TextField txbTestResult;
		TextField txbTestDate;
		TextField txbNotes;

		Label labTestCode = new Label("Test Code:");
		txbTestCode = new TextField();
		Label labPatientID = new Label("Patient ID:");
		txbPatientID = new TextField();
		Label labDoctorID = new Label("Doctor ID:");
		txbDoctorID = new TextField();
		Label labTestType = new Label("Test Type:");
		txbTestType = new TextField();
		Label labTestResult = new Label("Test Result:");
		txbTestResult = new TextField();
		Label labTestDate = new Label("Test Date:");
		txbTestDate = new TextField();
		Label labNotes = new Label("Notes:");
		txbNotes = new TextField();

		Button btnSave = new Button("Save");
		btnSave.setOnAction(e -> {
			// String testCode = txbTestCode.getText();
			String patientID = txbPatientID.getText();
			String doctorID = txbDoctorID.getText();
			String testType = txbTestType.getText();
			String testResult = txbTestResult.getText();
			String testDate = txbTestDate.getText();
			String notes = txbNotes.getText();

			try {
				pst = con.prepareStatement(
						"INSERT INTO Test ( PatientID, DoctorID, TestType, TestResult, TestDate, Notes) VALUES ( ?, ?, ?, ?, ?, ?)");

				// pst.setString(1, testCode);
				pst.setString(1, patientID);
				pst.setString(2, doctorID);
				pst.setString(3, testType);
				pst.setString(4, testResult);
				pst.setString(5, testDate);
				pst.setString(6, notes);

				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "Record Added!");
				table_load3();
				// table.refresh();

				// txbTestCode.setText("");
				txbPatientID.setText("");
				txbDoctorID.setText("");
				txbTestType.setText("");
				txbTestResult.setText("");
				txbTestDate.setText("");
				txbNotes.setText("");
				txbPatientID.requestFocus();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(e -> {
			String testCode = txbTestCode.getText();
			try {
				pst = con.prepareStatement(
						"SELECT PatientID, DoctorID, TestType, TestResult, TestDate, Notes FROM Test WHERE TestCode = ?");
				pst.setString(1, testCode);
				ResultSet rs = pst.executeQuery();
				if (rs.next()) {
					int patientID = rs.getInt("PatientID");
					int doctorID = rs.getInt("DoctorID");
					String testType = rs.getString("TestType");
					String testResult = rs.getString("TestResult");
					Date testDate = rs.getDate("TestDate");
					String notes = rs.getString("Notes");

					txbPatientID.setText(String.valueOf(patientID));
					txbDoctorID.setText(String.valueOf(doctorID));
					txbTestType.setText(testType);
					txbTestResult.setText(testResult);
					txbTestDate.setText(String.valueOf(testDate));
					txbNotes.setText(notes);
					JOptionPane.showMessageDialog(null, "Record Found!");
				} else {
					txbPatientID.setText("");
					txbDoctorID.setText("");
					txbTestType.setText("");
					txbTestResult.setText("");
					txbTestDate.setText("");
					txbNotes.setText("");
					JOptionPane.showMessageDialog(null, "Record not found.");
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		});

		Button btnUpdate = new Button("Update");
		btnUpdate.setOnAction(e -> {
			String testCode = txbTestCode.getText();
			String patientID = txbPatientID.getText();
			String doctorID = txbDoctorID.getText();
			String testType = txbTestType.getText();
			String testResult = txbTestResult.getText();
			String testDate = txbTestDate.getText();
			String notes = txbNotes.getText();

			try {
				pst = con.prepareStatement(
						"UPDATE Test SET PatientID=?, DoctorID=?, TestType=?, TestResult=?, TestDate=?, Notes=? WHERE TestCode=?");
				pst.setString(1, patientID);
				pst.setString(2, doctorID);
				pst.setString(3, testType);
				pst.setString(4, testResult);
				pst.setString(5, testDate);
				pst.setString(6, notes);
				pst.setString(7, testCode);

				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					table_load3();
					// table.refresh();

					JOptionPane.showMessageDialog(null, "Record Updated!");
					txbPatientID.setText("");
					txbDoctorID.setText("");
					txbTestType.setText("");
					txbTestResult.setText("");
					txbTestDate.setText("");
					txbNotes.setText("");
					txbTestCode.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Record not found or no changes made.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});

		Button btnDelete = new Button("Delete");
		btnDelete.setOnAction(e -> {
			String testCode = txbTestCode.getText();
			try {
				pst = con.prepareStatement("DELETE FROM Test WHERE TestCode = ?");
				pst.setString(1, testCode);
				int rowsAffected = pst.executeUpdate();
				if (rowsAffected > 0) {
					table_load3();
					// table.refresh();
					JOptionPane.showMessageDialog(null, "Record deleted!");

					txbPatientID.setText("");
					txbDoctorID.setText("");
					txbTestType.setText("");
					txbTestResult.setText("");
					txbTestDate.setText("");
					txbNotes.setText("");
					txbTestCode.requestFocus();
				} else {
					JOptionPane.showMessageDialog(null, "Record not found.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		Button btnClear = new Button("Clear");
		btnClear.setOnAction(e -> {
			txbTestCode.setText("");
			txbPatientID.setText("");
			txbDoctorID.setText("");
			txbTestType.setText("");
			txbTestResult.setText("");
			txbTestDate.setText("");
			txbNotes.setText("");
			txbTestCode.requestFocus();

			System.out.println("Done");
		});

		// patient.add(patient);
		Button btnExit = new Button("Exit");
		btnExit.setOnAction(e -> {
			System.exit(0);
		});

		Button goBill = new Button("MAIN PAGE");

		goBill.setOnAction(e -> {
			test.close();
		});

		// Add table columns
		TableColumn<Test, Integer> testIDColumn = new TableColumn<>("Test ID");
		testIDColumn.setCellValueFactory(new PropertyValueFactory<>("testID"));

		TableColumn<Test, Integer> patientIDColumn = new TableColumn<>("Patient ID");
		patientIDColumn.setCellValueFactory(new PropertyValueFactory<>("patientID"));

		TableColumn<Test, Integer> doctorIDColumn = new TableColumn<>("Doctor ID");
		doctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));

		TableColumn<Test, String> testTypeColumn = new TableColumn<>("Test Type");
		testTypeColumn.setCellValueFactory(new PropertyValueFactory<>("testType"));

		TableColumn<Test, String> resultColumn = new TableColumn<>("Result");
		resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));

		TableColumn<Test, Date> testDateColumn = new TableColumn<>("Test Date");
		testDateColumn.setCellValueFactory(new PropertyValueFactory<>("testDate"));
		testDateColumn.setCellFactory(column -> new TableCell<Test, Date>() {
			private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			@Override
			protected void updateItem(Date item, boolean empty) {
				super.updateItem(item, empty);
				if (item == null || empty) {
					setText(null);
				} else {
					setText(dateFormat.format(item));
				}
			}
		});

		TableColumn<Test, String> notesColumn = new TableColumn<>("Notes");
		notesColumn.setCellValueFactory(new PropertyValueFactory<>("notes"));
		table3.getColumns().addAll(testIDColumn, patientIDColumn, doctorIDColumn, testTypeColumn, resultColumn,
				testDateColumn, notesColumn);
		notesColumn.setPrefWidth(800);
		formPane2.add(table3, 2, 0, 1, 9); // Add Test table to the formPane

		btnSave.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnSave.setStyle("-fx-background-color: #FFC0CB;");

		btnSearch.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnSearch.setStyle("-fx-background-color: #FFC0CB;");

		btnUpdate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnUpdate.setStyle("-fx-background-color: #FFC0CB;");

		btnDelete.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnDelete.setStyle("-fx-background-color: #FFC0CB;");

		btnClear.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnClear.setStyle("-fx-background-color: #FFC0CB;");

		btnExit.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		btnExit.setStyle("-fx-background-color: #FFC0CB;");

		goBill.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 16));
		goBill.setStyle("-fx-background-color: #FFC0CB;");


		formPane2.add(labTestCode, 0, 12);
		formPane2.add(txbTestCode, 1, 12);
		formPane2.add(labPatientID, 0, 1);
		formPane2.add(txbPatientID, 1, 1);
		formPane2.add(labDoctorID, 0, 2);
		formPane2.add(txbDoctorID, 1, 2);
		formPane2.add(labTestType, 0, 3);
		formPane2.add(txbTestType, 1, 3);
		formPane2.add(labTestResult, 0, 4);
		formPane2.add(txbTestResult, 1, 4);
		formPane2.add(labTestDate, 0, 5);
		formPane2.add(txbTestDate, 1, 5);
		formPane2.add(labNotes, 0, 6);
		formPane2.add(txbNotes, 1, 6);
		formPane2.add(goBill, 4, 14);

		HBox h = new HBox(10);
		h.getChildren().addAll(btnSearch, btnSave, btnUpdate, btnDelete, btnClear, btnExit);
		formPane2.add(h, 2, 13);
		labTestCode.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labPatientID.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labDoctorID.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labTestType.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labTestResult.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labTestDate.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
		labNotes.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));

		

		test.setTitle("Test Management System");

		// formPane2.setStyle("-fx-background-color: #00FF00;");
		formPane2.setStyle("-fx-background-color:lightgray;");

		Scene scene2 = new Scene(formPane2, 1700, 600);
		test.setScene(scene2);
		test.show();
		return test;
	}

	public void table_load3() {
		try {
			pst = con.prepareStatement("SELECT * FROM Test");
			ResultSet rs = pst.executeQuery();
			table3.getItems().clear();
			while (rs.next()) {
				int testID = rs.getInt("TestCode");
				int patientID = rs.getInt("PatientID");
				int doctorID = rs.getInt("DoctorID");
				String testType = rs.getString("TestType");
				String testResult = rs.getString("TestResult");
				Date testDate = rs.getDate("TestDate");
				String notes = rs.getString("Notes");

				// Create a new Test object with retrieved data
				Test test = new Test(testID, patientID, doctorID, testType, testResult, testDate, notes);

				// Add the Test object to the table
				table3.getItems().add(test);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void table_load4() {
		try {
			pst = con.prepareStatement("SELECT * FROM Diagnosis");
			rs = pst.executeQuery();

			// Define columns for the Diagnosis table
			TableColumn<Diagnosis, Integer> diagnosisIdColumn = new TableColumn<>("Diagnosis ID");
			diagnosisIdColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosisId"));

			TableColumn<Diagnosis, String> diagnosisNameColumn = new TableColumn<>("Diagnosis Name");
			diagnosisNameColumn.setCellValueFactory(new PropertyValueFactory<>("diagnosisName"));

			TableColumn<Diagnosis, String> descriptionColumn = new TableColumn<>("Description");
			descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

			TableColumn<Diagnosis, Integer> patientIdColumn = new TableColumn<>("Patient ID");
			patientIdColumn.setCellValueFactory(new PropertyValueFactory<>("patientId"));

			TableColumn<Diagnosis, Integer> doctorIdColumn = new TableColumn<>("Doctor ID");
			doctorIdColumn.setCellValueFactory(new PropertyValueFactory<>("doctorId"));

			TableColumn<Diagnosis, Date> dateColumn = new TableColumn<>("Date");
			dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));

			// Clear existing columns and add new columns
			table4.getColumns().clear();
			table4.getColumns().addAll(diagnosisIdColumn, diagnosisNameColumn, descriptionColumn, patientIdColumn,
					doctorIdColumn, dateColumn);

			// Initialize data list
			ObservableList<Diagnosis> data4 = FXCollections.observableArrayList();

			// Iterate through the result set and add Diagnosis objects to the data list
			while (rs.next()) {
				int diagnosisId = rs.getInt("DiagnosisID");
				String diagnosisName = rs.getString("DiagnosisName");
				String description = rs.getString("Description");
				int patientId = rs.getInt("PatientID");
				int doctorId = rs.getInt("DoctorID");
				Date date = rs.getDate("Date");

				Diagnosis diagnosis = new Diagnosis(diagnosisId, diagnosisName, description, patientId, doctorId, date);
				data4.add(diagnosis);
			}

			// Set items in the TableView
			table4.setItems(data4);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
