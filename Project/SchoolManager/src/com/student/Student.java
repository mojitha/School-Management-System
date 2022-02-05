package com.student;

import com.user.User;

public class Student extends User {
    private String grade,stuClass,religion;
    private static Student studentSingleton;
    
    public Student() {
        super();
    }

    public static Student getInstance() {
        if(studentSingleton == null) {
            synchronized(Student.class) {
                studentSingleton = new Student();
                System.out.println("studentSingleton created");
            }
        }
        
        return studentSingleton;
    }
    
    public Student(String grade, String stuClass, String religion, String userID, String name, String userName, String password, String birthDate, String registeredDate, String email, String phone, String address, String bio, String img, String status) {
        super(userID, name, userName, password, birthDate, registeredDate, email, phone, address, bio, img, status);
        this.grade = grade;
        this.stuClass = stuClass;
        this.religion = religion;
    }
    
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStuClass() {
        return stuClass;
    }

    public void setStuClass(String stuClass) {
        this.stuClass = stuClass;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    
    
    
}
