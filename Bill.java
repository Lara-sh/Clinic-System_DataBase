package application;

import java.sql.Date;

//Represents a bill for a patient, including lab charges, medicine charges, and payment information.
public class Bill {
    private int billID; // The ID of the bill
    private int patientID; // The ID of the patient
    private double labCharges; // The charges for lab services
    private double medicineCharges; // The charges for medicines
    private Date date; // The date of the bill
    private String paymentMethod; // The payment method used for the bill

    public Bill(int billID, int patientID, double labCharges, double medicineCharges, Date date, String paymentMethod) {
        this.billID = billID;
        this.patientID = patientID;
        this.labCharges = labCharges;
        this.medicineCharges = medicineCharges;
        this.date = date;
        this.paymentMethod = paymentMethod;
    }
 
    //setters and getters
    public int getBillID() {
        return billID;
    }

    public void setBillID(int billID) {
        this.billID = billID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public double getLabCharges() {
        return labCharges;
    }

    public void setLabCharges(double labCharges) {
        this.labCharges = labCharges;
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(double medicineCharges) {
        this.medicineCharges = medicineCharges;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
