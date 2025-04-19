-- Drop and create the database
DROP DATABASE IF EXISTS ClinicProject;
CREATE DATABASE ClinicProject;
USE ClinicProject;

-- Creating PatientsTable
CREATE TABLE PatientsTable (
  PatientID INT PRIMARY KEY AUTO_INCREMENT,
  FirstName VARCHAR(50),
  MidName VARCHAR(50),
  LastName VARCHAR(50),
  BirthDate DATE,
  Gender VARCHAR(10),
  PhoneNumber VARCHAR(20),
  Diagnosis VARCHAR(100),
  AgeGroup VARCHAR(20)
);

-- Creating DoctorsTable
CREATE TABLE doctor (
  DoctorID INT PRIMARY KEY,
  FirstName VARCHAR(50),
  MidName VARCHAR(50),
  LastName VARCHAR(50),
  Specialty VARCHAR(50),
  Gender VARCHAR(10),
  phoneNumber VARCHAR(20),
  Email VARCHAR(100),
  Address VARCHAR(100),
  WorkHours VARCHAR(100)
);

-- Creating Appointment table
CREATE TABLE Appointment (
    AppointmentID INT PRIMARY KEY,
    DoctorID INT,
    Notes VARCHAR(255),
    PatientID INT,
    AppointmentDate DATE,
    AppointmentTime TIME,
    FOREIGN KEY (DoctorID) REFERENCES doctor(DoctorID),
    FOREIGN KEY (PatientID) REFERENCES PatientsTable(PatientID)
);

-- Creating Bill table
CREATE TABLE Bill (
  BillID INT AUTO_INCREMENT PRIMARY KEY,
  PatientID INT NOT NULL,
  LabCharges DECIMAL(10, 2) NOT NULL,
  MedicineCharges DECIMAL(10, 2) NOT NULL,
  Date DATE NOT NULL,
  PaymentMethod VARCHAR(20) NOT NULL,
  FOREIGN KEY (PatientID) REFERENCES PatientsTable(PatientID)
);

-- Creating Test table
CREATE TABLE Test (
    TestCode INT PRIMARY KEY AUTO_INCREMENT,
    PatientID INT,
    DoctorID INT,
    TestType VARCHAR(50),
    TestResult VARCHAR(50),
    TestDate DATE,
    Notes VARCHAR(100),
    FOREIGN KEY (DoctorID) REFERENCES doctor(DoctorID),
    FOREIGN KEY (PatientID) REFERENCES PatientsTable(PatientID)
);

-- Creating Prescription table
CREATE TABLE PRESCRIPTION (
    PrescriptionID INT PRIMARY KEY AUTO_INCREMENT,
    MedicationName VARCHAR(50),
    Dosage VARCHAR(50),
    Quantity INT,
    PatientID INT,
    DoctorID INT,
    Date DATE,
    FOREIGN KEY (PatientID) REFERENCES PatientsTable(PatientID),
    FOREIGN KEY (DoctorID) REFERENCES doctor(DoctorID) ON DELETE CASCADE
);

-- Creating DoctorRoom table
CREATE TABLE DoctorRoom (
  RoomNumber INT PRIMARY KEY AUTO_INCREMENT,
  DoctorID INT,
  NumberOfBeds INT,
  FOREIGN KEY (DoctorID) REFERENCES doctor(DoctorID)
);

-- Creating Diagnosis table
CREATE TABLE Diagnosis (
    DiagnosisID INT PRIMARY KEY AUTO_INCREMENT,
    DiagnosisName VARCHAR(50),
    Description VARCHAR(255),
    PatientID INT,
    DoctorID INT,
    Date DATE,
    FOREIGN KEY (PatientID) REFERENCES PatientsTable(PatientID) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (DoctorID) REFERENCES doctor(DoctorID) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Inserting records into PatientsTable
INSERT INTO PatientsTable (PatientID, FirstName, MidName, LastName, BirthDate, Gender, PhoneNumber, Diagnosis, AgeGroup)
VALUES 
(1, 'Mahammad', 'A', 'Ahmad', '1990-05-10', 'Male', '0568236589', 'Flu', 'Adult'),
(2, 'Tala', 'B', 'Salah', '2012-12-15', 'Female', '0598521478', 'Bronchitis', 'Young'),
(3, 'Emad', 'Gh', 'Kareem', '1994-05-16', 'Male', '09-2587412', 'Diabetes', 'Adult'),
(4, 'Alaa', 'E', 'Ali', '2003-07-08', 'Male', '0526321587', 'Diabetes', 'Adult'),
(5, 'Bana', 'J', 'Ali', '2015-01-19', 'Female', '0502369874', 'Flu', 'Young'),
(6, 'Israa', 'J', 'Ali', '2002-09-14', 'Female', '0599785841', 'Appendix', 'Adult'),
(7, 'Oday', 'M', 'Omar', '2007-05-20', 'Male', '0569321456', 'Flu', 'Young'),
(8, 'Omar', 'M', 'Ali', '2015-09-09', 'Male', '0596321547', 'Hypotension', 'Young'),
(9, 'Ahmed', 'R', 'Al-Sayed', '1985-02-20', 'Male', '0561234567', 'Hypertension', 'Adult'),
(10, 'Lina', 'M', 'Fathi', '2010-04-11', 'Female', '0591234578', 'Asthma', 'Young'),
(11, 'Samir', 'A', 'Khaled', '1992-07-05', 'Male', '0509887654', 'Cancer', 'Adult'),
(12, 'Fatima', 'N', 'Hassan', '2018-09-17', 'Female', '0505648903', 'Flu', 'Child'),
(13, 'Ali', 'S', 'Jaber', '1989-11-03', 'Male', '0567453567', 'Migraine', 'Adult'),
(14, 'Mona', 'T', 'Al-Sharif', '1995-01-29', 'Female', '0506348215', 'Flu', 'Young'),
(15, 'Zainab', 'B', 'Mohammed', '2007-03-19', 'Female', '0594567891', 'Cold', 'Young'),
(16, 'Tariq', 'Z', 'Baker', '1975-10-02', 'Male', '0567654321', 'Stroke', 'Senior');

-- Inserting records into doctor
INSERT INTO doctor (DoctorID, FirstName, MidName, LastName, Specialty, Gender, PhoneNumber, Email, Address, WorkHours)
VALUES 
(1, 'John', 'K', 'Doe', 'Internal Medicine Physician', 'Male', '0599874521', 'john.doe@example.com', '123 Main St', '9 AM - 5 PM'),
(2, 'Jane', 'B', 'Smith', 'Ear, noes and throat', 'Female', '0523698741', 'jane.smith@example.com', '456 Elm St', '10 AM - 6 PM'),
(3, 'Michael', 'M', 'Johnson', 'Cardiologist', 'Male', '0523654789', 'michael.johnson@example.com', '789 Oak St', '12 PM - 7 PM'),
(4, 'Sarah', 'F', 'Lee', 'Pediatrician', 'Female', '0501234567', 'sarah.lee@example.com', '123 Park Ave', '8 AM - 4 PM'),
(5, 'David', 'P', 'Miller', 'Orthopedic Surgeon', 'Male', '0527654321', 'david.miller@example.com', '456 Oak Ave', '9 AM - 5 PM'),
(6, 'Lina', 'V', 'Khan', 'Dentist', 'Female', '0593654789', 'lina.khan@example.com', '789 Pine St', '10 AM - 6 PM'),
(7, 'Mark', 'J', 'Taylor', 'Dermatologist', 'Male', '0568123456', 'mark.taylor@example.com', '123 Cedar Blvd', '8 AM - 5 PM'),
(8, 'Rebecca', 'C', 'Wang', 'Endocrinologist', 'Female', '0528479321', 'rebecca.wang@example.com', '101 Maple St', '7 AM - 3 PM');

-- Inserting records into Appointment
INSERT INTO Appointment (AppointmentID, DoctorID, Notes, PatientID, AppointmentDate, AppointmentTime)
VALUES
(1, 1, 'Routine checkup', 1, '2025-01-10', '10:00:00'),
(2, 2, 'Ear infection follow-up', 2, '2025-01-12', '11:00:00'),
(3, 3, 'Blood pressure monitoring', 3, '2025-01-15', '13:30:00'),
(4, 4, 'Vaccination', 4, '2025-01-16', '09:00:00'),
(5, 5, 'Fracture checkup', 5, '2025-01-17', '14:00:00'),
(6, 6, 'Routine dental checkup', 6, '2025-01-18', '10:30:00');

-- Inserting records into Bill
INSERT INTO Bill (PatientID, LabCharges, MedicineCharges, Date, PaymentMethod)
VALUES
(1, 100.50, 200.75, '2025-01-10', 'Credit Card'),
(2, 150.00, 50.00, '2025-01-12', 'Cash'),
(3, 120.00, 175.00, '2025-01-15', 'Debit Card'),
(4, 50.00, 75.00, '2025-01-16', 'Cash'),
(5, 80.00, 100.00, '2025-01-17', 'Insurance'),
(6, 100.00, 90.00, '2025-01-18', 'Debit Card');

-- Inserting records into Test
INSERT INTO Test (PatientID, DoctorID, TestType, TestResult, TestDate, Notes)
VALUES
(1, 1, 'Blood Test', 'Normal', '2025-01-10', 'Routine checkup'),
(2, 2, 'X-Ray', 'Infected', '2025-01-12', 'Follow-up for ear infection'),
(3, 3, 'Blood Pressure Test', 'High', '2025-01-15', 'Monitor health'),
(4, 4, 'Vaccine Test', 'Pending', '2025-01-16', 'For vaccination'),
(5, 5, 'Bone Density Test', 'Normal', '2025-01-17', 'Fracture checkup'),
(6, 6, 'Dental X-ray', 'Healthy', '2025-01-18', 'Routine dental checkup');

-- Inserting records into Prescription
INSERT INTO PRESCRIPTION (PrescriptionID, MedicationName, Dosage, Quantity, PatientID, DoctorID, Date)
VALUES
(1, 'Paracetamol', '500mg', 30, 1, 1, '2025-01-10'),
(2, 'Amoxicillin', '250mg', 20, 2, 2, '2025-01-12'),
(3, 'Aspirin', '75mg', 50, 3, 3, '2025-01-15'),
(4, 'Ibuprofen', '200mg', 25, 4, 4, '2025-01-16'),
(5, 'Calcium Tablets', '1000mg', 40, 5, 5, '2025-01-17'),
(6, 'Ciprofloxacin', '500mg', 15, 6, 6, '2025-01-18');

-- Inserting records into DoctorRoom
INSERT INTO DoctorRoom (RoomNumber, DoctorID, NumberOfBeds)
VALUES
(101, 1, 2),
(102, 2, 3),
(103, 3, 4),
(104, 4, 1),
(105, 5, 2),
(106, 6, 2);

-- Inserting records into Diagnosis
INSERT INTO Diagnosis (DiagnosisName, Description, PatientID, DoctorID, Date)
VALUES
('Flu', 'Common viral infection', 1, 1, '2025-01-10'),
('Asthma', 'Breathing difficulties', 2, 2, '2025-01-12'),
('Diabetes', 'Chronic high blood sugar levels', 3, 3, '2025-01-15'),
('Cold', 'Respiratory infection', 4, 4, '2025-01-16'),
('Fracture', 'Bone injury', 5, 5, '2025-01-17'),
('Dental Cavities', 'Tooth decay', 6, 6, '2025-01-18');







