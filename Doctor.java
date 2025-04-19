package application;

public class Doctor {
    private int doctorID;
    private String firstName;
    private String midName;
    private String lastName;
    private String specialty;
    private String gender;
    private String phoneNumber;
    private String email;
    private String address;
    private String workHours;

    // Constructor
    public Doctor(int doctorID, String firstName, String midName, String lastName, String specialty, String gender,
                  String phoneNumber, String email, String address, String workHours) {
        this.doctorID = doctorID;
        this.firstName = firstName;
        this.midName = midName;
        this.lastName = lastName;
        this.specialty = specialty;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.workHours = workHours;
    }

    // Getter and Setter methods
    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMidName() {
        return midName;
    }

    public void setMidName(String midName) {
        this.midName = midName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumbers) {
        this.phoneNumber = phoneNumbers;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    @Override
    public String toString() {
        return "Doctor [doctorID=" + doctorID + ", firstName=" + firstName + ", midName=" + midName + ", lastName=" 
               + lastName + ", specialty=" + specialty + ", gender=" + gender + ", phoneNumbers=" + phoneNumber 
               + ", email=" + email + ", address=" + address + ", workHours=" + workHours + "]";
    }
}
