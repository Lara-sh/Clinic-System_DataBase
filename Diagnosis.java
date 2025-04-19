package application;

import java.util.Date;

//Represents a diagnosis for a patient, including diagnosis details and related doctor and patient information.

public class Diagnosis {
    private int diagnosisId; // The ID of the diagnosis
    private String diagnosisName; // The name of the diagnosis
    private String description; // A description of the diagnosis
    private int patientId; // The ID of the patient associated with the diagnosis
    private int doctorId; // The ID of the doctor who made the diagnosis
    private Date date; // The date of the diagnosis

    public Diagnosis(int diagnosisId, String diagnosisName, String description, int patientId, int doctorId, Date date) {
        this.diagnosisId = diagnosisId;
        this.diagnosisName = diagnosisName;
        this.description = description;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
    }

    public int getDiagnosisId() {
        return diagnosisId;
    }

    public void setDiagnosisId(int diagnosisId) {
        this.diagnosisId = diagnosisId;
    }

    public String getDiagnosisName() {
        return diagnosisName;
    }

    public void setDiagnosisName(String diagnosisName) {
        this.diagnosisName = diagnosisName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
