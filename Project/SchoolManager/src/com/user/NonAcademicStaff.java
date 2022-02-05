package com.user;

import com.string.Strings;
import java.io.File;

public class NonAcademicStaff extends User {
    private String designation,qualification;
    private static NonAcademicStaff nonAcademicStaffSingleton;
    public static NonAcademicStaff shareduser = new NonAcademicStaff();
    private static File file;
    
    public NonAcademicStaff() {
        super();
    }
    
    public static NonAcademicStaff getSharedUser() {
        return shareduser;
    }
    
    public static NonAcademicStaff getInstance() {
        if(nonAcademicStaffSingleton == null) {
            synchronized(NonAcademicStaff.class) {
                nonAcademicStaffSingleton = new NonAcademicStaff();
                System.out.println(Strings.created_nonAcademicStaffSingleton);
            }
        }
        
        return nonAcademicStaffSingleton;
    }
    
    public NonAcademicStaff(String userID, String name, String userName, String password, String birthDate, String registeredDate, String email, String phone, String address, String bio, String img, String status, String designation, String qualification) {
        super(userID, name, userName, password, birthDate, registeredDate, email, phone, address, bio, img, status);
        this.designation = designation;
        this.qualification = qualification;
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

    public static File getFile() {
        return file;
    }

    public static void setFile(File file) {
        NonAcademicStaff.file = file;
    }
    
    
}
