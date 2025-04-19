package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class RoomInterface extends GridPane {

    private String dbURL;
    private String dbUsername = "root";
    private String dbPassword = "Lara.1034";
    private String URL = "127.0.0.1";
    private String port = "3306";
    private String dbName = "ClinicProject";
    private TableView<DoctorRoom> table;
    ObservableList<DoctorRoom> data = FXCollections.observableArrayList();

    private TextField txRoomNumber;
    private TextField txDoctorID;
    private TextField txNumOfBeds;

    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    public void table_load() {
        try {
            data.clear();

            pst = con.prepareStatement("SELECT * FROM DoctorRoom");
            rs = pst.executeQuery();

            if (table.getColumns().isEmpty()) {
                TableColumn<DoctorRoom, Integer> roomNumberColumn = new TableColumn<>("Room Number");
                roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

                TableColumn<DoctorRoom, Integer> doctorIDColumn = new TableColumn<>("Doctor ID");
                doctorIDColumn.setCellValueFactory(new PropertyValueFactory<>("doctorID"));

                TableColumn<DoctorRoom, Integer> numOfBedsColumn = new TableColumn<>("Number of Beds");
                numOfBedsColumn.setCellValueFactory(new PropertyValueFactory<>("numOfBeds"));

                table.getColumns().addAll(roomNumberColumn, doctorIDColumn, numOfBedsColumn);
            }

            while (rs.next()) {
                int roomNumber = rs.getInt("RoomNumber");
                int doctorID = rs.getInt("DoctorID");
                int numOfBeds = rs.getInt("NumberOfBeds");

                DoctorRoom doctorRoom = new DoctorRoom(roomNumber, doctorID, numOfBeds);
                data.add(doctorRoom);
            }

            table.setItems(data);

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

    public Stage getRoom() {
        connect();
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table_load();

        GridPane formPane = new GridPane();
        formPane.setHgap(10);
        formPane.setVgap(10);

        Label labRoomNumber = new Label("Room Number:");
        txRoomNumber = new TextField();

        Label labDoctorID = new Label("Doctor ID:");
        txDoctorID = new TextField();

        Label labNumOfBeds = new Label("Number of Beds:");
        txNumOfBeds = new TextField();

        Button btnSave = new Button("Save");
        btnSave.setOnAction(e -> {
            if (txRoomNumber.getText().isEmpty() || txDoctorID.getText().isEmpty() || txNumOfBeds.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all fields.");
                return;
            }

            int roomNumber = Integer.parseInt(txRoomNumber.getText());
            int doctorID = Integer.parseInt(txDoctorID.getText());
            int numOfBeds = Integer.parseInt(txNumOfBeds.getText());

            try {
                pst = con.prepareStatement("INSERT INTO DoctorRoom (RoomNumber, DoctorID, NumberOfBeds) VALUES (?, ?, ?)");
                pst.setInt(1, roomNumber);
                pst.setInt(2, doctorID);
                pst.setInt(3, numOfBeds);

                pst.executeUpdate();
                System.out.println("Record Added!");
                table_load();

                txRoomNumber.setText("");
                txDoctorID.setText("");
                txNumOfBeds.setText("");

                table.refresh();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        });

        formPane.add(labRoomNumber, 0, 0);
        formPane.add(txRoomNumber, 1, 0);

        formPane.add(labDoctorID, 0, 1);
        formPane.add(txDoctorID, 1, 1);

        formPane.add(labNumOfBeds, 0, 2);
        formPane.add(txNumOfBeds, 1, 2);

        formPane.add(btnSave, 1, 3);

        HBox hbox = new HBox(table);
        hbox.setSpacing(10);

        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);

        mainPane.add(hbox, 0, 0);
        mainPane.add(formPane, 1, 0);

        Scene scene = new Scene(mainPane, 800, 400);
        Stage primaryStage = new Stage();

        Button goBill = new Button("MAIN PAGE");
        goBill.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.ITALIC, 12));
        goBill.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        formPane.add(goBill, 1, 4);
        goBill.setOnAction(e -> primaryStage.close());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Doctor Room Management");
        primaryStage.show();
        return primaryStage;
    }
}