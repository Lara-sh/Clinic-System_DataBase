package application;


import java.util.Date;

public class Test {

	private int testID;
	private int patientID;
	private int doctorID;
	private String testType;
	private String result;
	private Date testDate;
	private String Notes;

	public Test(int testID, int patientID, int doctorID, String testType, String result, Date testDate, String notes) {
		super();
		this.testID = testID;
		this.patientID = patientID;
		this.doctorID = doctorID;
		this.testType = testType;
		this.result = result;
		this.testDate = testDate;
		Notes = notes;
	}

	public int getTestID() {
		return testID;
	}

	public void setTestID(int testID) {
		this.testID = testID;
	}

	public int getPatientID() {
		return patientID;
	}

	public void setPatientID(int patientID) {
		this.patientID = patientID;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Date getTestDate() {
		return testDate;
	}

	public void setTestDate(Date testDate) {
		this.testDate = testDate;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

}