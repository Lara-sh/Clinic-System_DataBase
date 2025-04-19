package application;

public class DoctorRoom {
	private int roomNumber;
	private int doctorID;
	private int numOfBeds;

	public DoctorRoom(int roomNumber, int doctorID, int numOfBeds) {
		this.roomNumber = roomNumber;
		this.doctorID = doctorID;
		this.numOfBeds = numOfBeds;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public int getDoctorID() {
		return doctorID;
	}

	public void setDoctorID(int doctorID) {
		this.doctorID = doctorID;
	}

	public int getNumOfBeds() {
		return numOfBeds;
	}

	public void setNumOfBeds(int numOfBeds) {
		this.numOfBeds = numOfBeds;
	}
}