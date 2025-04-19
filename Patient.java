package application;

import java.sql.SQLException;
import java.util.Date;

// Class representing a Patient entity
public class Patient {
    // Attributes of the Patient class
    private int PatientID; // Unique identifier for the patient
    private String firstName; // First name of the patient
    private String midName; // Middle name of the patient
    private String lastName; // Last name of the patient
    private Date birthDate; // Birth date of the patient
    private String gender; // Gender of the patient
    private String phoneNumber; // Phone number of the patient
    private String diagnosis; // Diagnosis of the patient
    private String ageGroup; // Age group of the patient

    // Constructor to initialize attributes of the Patient class
    public Patient(int PatientID, String firstName, String midName, String lastName, Date birthDate, String gender, String phoneNumber,  String diagnosis, String ageGroup) throws SQLException {
        this.PatientID = PatientID;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.diagnosis = diagnosis;
        this.ageGroup = ageGroup;
    }

    // Getter method for PatientID
    public int getPatientID() {
        return PatientID;
    }

    // Setter method for PatientID
    public void setPatientID(int patientID) {
        PatientID = patientID;
    }

    // Getter method for firstName
    public String getFirstName() {
        return firstName;
    }

    // Setter method for firstName
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    // Getter method for midName
    public String getMidName() {
        return midName;
    }

    // Setter method for midName
    public void setMidName(String midName) {
        this.midName = midName;
    }

    // Getter method for lastName
    public String getLastName() {
        return lastName;
    }

    // Setter method for lastName
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter method for birthDate
    public Date getBirthDate() {
        return birthDate;
    }

    // Setter method for birthDate
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    // Getter method for gender
    public String getGender() {
        return gender;
    }

    // Setter method for gender
    public void setGender(String gender) {
        this.gender = gender;
    }

    // Getter method for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Setter method for phoneNumber
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Getter method for diagnosis
    public String getDiagnosis() {
        return diagnosis;
    }

    // Setter method for diagnosis
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    // Getter method for ageGroup
    public String getAgeGroup() {
        return ageGroup;
    }

    // Setter method for ageGroup
    public void setAgeGroup(String ageGroup) {
        this.ageGroup = ageGroup;
    }
}
