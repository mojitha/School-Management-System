package com.user;

import com.string.Strings;
import java.io.File;

public class AcademicStaff extends User {
    private String designation,qualification,availability;
    private static AcademicStaff academicStaffSingleton;
    public static AcademicStaff shareduser = new AcademicStaff();
    private static File file;
    
    public AcademicStaff() {
        super();
    }
    
    public static AcademicStaff getSharedUser() {
        return shareduser;
    }
    
    public static AcademicStaff getInstance() {
        if(academicStaffSingleton == null) {
            synchronized(NonAcademicStaff.class) {
                academicStaffSingleton = new AcademicStaff();
                System.out.println(Strings.created_academicStaffSingleton);
            }
        }
        
        return academicStaffSingleton;
    }

    public AcademicStaff(String designation, String qualification, String availability, String userID, String name, String userName, String password, String birthDate, String registeredDate, String email, String phone, String address, String bio, String img, String status) {
        super(userID, name, userName, password, birthDate, registeredDate, email, phone, address, bio, img, status);
        this.designation = designation;
        this.qualification = qualification;
        this.availability = availability;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        AcademicStaff.file = file;
    }
    
    
}
