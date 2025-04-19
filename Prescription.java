package application;

import java.util.Date;

// Class representing a Prescription entity
public class Prescription {
    // Attributes of the Prescription class
    private int prescriptionId; // Unique identifier for the prescription
    private String medicationName; // Name of the medication prescribed
    private String dosage; // Dosage of the medication
    private int quantity; // Quantity of the medication prescribed
    private int patientId; // ID of the patient for whom the prescription is issued
    private int doctorId; // ID of the doctor who issued the prescription
    private Date date; // Date when the prescription was issued

    // Constructor to initialize attributes of the Prescription class
    public Prescription(int prescriptionId, String medicationName, String dosage, int quantity, int patientId,
            int doctorId, Date date) {
        // Initializing attributes with provided values
        this.prescriptionId = prescriptionId;
        this.medicationName = medicationName;
        this.dosage = dosage;
        this.quantity = quantity;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    // Getter method for prescriptionId
    public int getPrescriptionId() {
        return prescriptionId;
    }

    // Setter method for prescriptionId
    public void setPrescriptionId(int prescriptionId) {
        this.prescriptionId = prescriptionId;
    }

    // Getter method for medicationName
    public String getMedicationName() {
        return medicationName;
    }

    // Setter method for medicationName
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    // Getter method for dosage
    public String getDosage() {
        return dosage;
    }

    // Setter method for dosage
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }

    // Getter method for quantity
    public int getQuantity() {
        return quantity;
    }

    // Setter method for quantity
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Getter method for patientId
    public int getPatientId() {
        return patientId;
    }

    // Setter method for patientId
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    // Getter method for doctorId
    public int getDoctorId() {
        return doctorId;
    }

    // Setter method for doctorId
    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    // Getter method for date
    public Date getDate() {
        return date;
    }

    // Setter method for date
    public void setDate(Date date) {
        this.date = date;
    }

    // toString method to represent the Prescription object as a string
    @Override
    public String toString() {
        return "Prescription [prescriptionId=" + prescriptionId + ", medicationName=" + medicationName + ", dosage="
                + dosage + ", quantity=" + quantity + ", patientId=" + patientId + ", doctorId=" + doctorId + ", date="
                + date + "]";
    }
}
