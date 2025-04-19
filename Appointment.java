package application;

import java.sql.Time;
import java.util.Date;

public class Appointment {
	private int AppointmentID;
	private int DoctorID;
	private String Notes;
	private int PatientID;
	private Date AppointmentDate;
	private Time AppointmentTime;

	public Appointment(int AppointmentID, int DoctorID, String Notes, int PatientID, Date AppointmentDate,
			Time AppointmentTime) {

		this.PatientID = PatientID;
		this.AppointmentID = AppointmentID;
		this.DoctorID = DoctorID;
		this.Notes = Notes;
		this.AppointmentTime = AppointmentTime;
		this.AppointmentDate = AppointmentDate;

	}

	public int getAppointmentID() {
		return AppointmentID;
	}

	public void setAppointmentID(int appointmentID) {
		AppointmentID = appointmentID;
	}

	public int getDoctorID() {
		return DoctorID;
	}

	public void setDoctorID(int doctorID) {
		DoctorID = doctorID;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public int getPatientID() {
		return PatientID;
	}

	public void setPatientID(int patientID) {
		PatientID = patientID;
	}

	public Date getAppointmentDate() {
		return AppointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		AppointmentDate = appointmentDate;
	}

	public Time getAppointmentTime() {
		return AppointmentTime;
	}

	public void setAppointmentTime(Time appointmentTime) {
		AppointmentTime = appointmentTime;
	}

}
