package com.attendance;

import com.string.Strings;
import javafx.scene.control.CheckBox;

public class Attendance {
    private String userID,name,date,grade,stuClass,presentAbsent;
    private CheckBox select;
    private static Attendance attendanceSingleton;
    
    public Attendance() {
        super();
    }
    
    public static Attendance getInstance() {
        if(attendanceSingleton == null) {
            synchronized(Attendance.class) {
                attendanceSingleton = new Attendance();
                System.out.println(Strings.created_attendanceSingleton);
            }
        }
        
        return attendanceSingleton;
    }
    
    public Attendance(String userID, String name, String date, String grade, String stuClass, String presentAbsent) {
        this.userID = userID;
        this.name = name;
        this.date = date;
        this.grade = grade;
        this.stuClass = stuClass;
        this.presentAbsent = presentAbsent;
        this.select = new CheckBox();
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getPresentAbsent() {
        return presentAbsent;
    }

    public void setPresentAbsent(String presentAbsent) {
        this.presentAbsent = presentAbsent;
    }

    public CheckBox getSelect() {
        return select;
    }

    public void setSelect(CheckBox select) {
        this.select = select;
    }

}

class AttendanceIndividual extends Attendance {
    //
}
