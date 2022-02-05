package com.connection;

import animatefx.animation.FadeIn;
import com.string.Strings;
import com.subject.Subject;
import com.user.AcademicStaffDao;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;

public class ValidateSM {
    // validates a text value
    public static boolean validateText(String textInput, int charCount, String message, Label fxwarninglbl) {
        if(textInput.length() > charCount ||  textInput.isEmpty()) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // validates for a numeric non negative value
    public static boolean validateNumber(String number, String message, Label fxwarninglbl) {
        try {
            if(number.isEmpty()) {
                fxwarninglbl.setText(message);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
            else return Double.parseDouble(number) >= 0;
        }
        catch(NumberFormatException nfe) {
            System.out.println(nfe);
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
    }
    
    // validates a combo box
    public static boolean isCombo(SingleSelectionModel<String> option, String message, Label fxwarninglbl) {
        if(option.isEmpty()) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // checks the validity of the date 
    public static boolean checkDate(DatePicker date, String message, Label fxwarninglbl) {
        if(date.getValue() == null) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // checks a limited lengthed text
    public static boolean validateTextAbsolute(String textInput, int charCount, String message, Label fxwarninglbl) {
        if(textInput.length() != charCount ||  textInput.isEmpty()) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // checks and validate an email address
    public static boolean isValidEmail(String email,String message, Label fxwarninglbl) {
        String emailRegex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
      
        if(!email.matches(emailRegex)) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }

    // checks the validity of the designation
    public static boolean validateDesignation(String designation, String message, Label fxwarninglbl) {
        String des[] = {Strings.Inventory_Manager,Strings.Attendance_Manager,Strings.Exam_Manager,Strings.Student_Manager,Strings.Result_Manager,Strings.Subject_Manager,Strings.Library_Manager,Strings.Staff_Manager};
        
        for(String desig:des) {
            if(desig.equals(designation)) {
                fxwarninglbl.setText(message);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
        }
        
        return true;
    }

    // check the availability of a teacher to assign for a subject
    public static boolean checkTeachability(Subject subjectToUp, String message, Label fxwarninglbl) {
        if(AcademicStaffDao.getAvailability(subjectToUp.getTeacher()) >= 3) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
}
