package com.student;

import com.connection.ValidateSM;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStudentController implements Initializable {
    @FXML
    public Label fxwarninglbl,fxstudentIdlbl;
    public TextField fxnametxt,fxaddresstxt,fxemailtxt,fxphonetxt;
    public DatePicker fxbirthdatedtpickr;
    public Button fxOKbtn,fxcancelbtn;
    
    // student to be changed
    Student currStudent = UpdateStudentUI.getOldStudent();

    @FXML
    public void cancelUpdateStudent(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void okUpdateStudent(ActionEvent event) throws ParseException, IOException {
        String studentID = fxstudentIdlbl.getText();
        String name = fxnametxt.getText().trim();
        DatePicker date = fxbirthdatedtpickr;
        String address = fxaddresstxt.getText().trim();
        String email = fxemailtxt.getText().trim();
        String phone = fxphonetxt.getText().trim();
        
        // validates if all item details are given by student manager
        if(ValidateSM.validateText(studentID, 10, Strings.invalidStudentID, fxwarninglbl)) {
            if(ValidateSM.validateText(name, 30, Strings.invalidName, fxwarninglbl)) {
                if(ValidateSM.checkDate(date, Strings.invalidBirthDate, fxwarninglbl)) {
                    if(ValidateSM.validateText(address, 50, Strings.invalidAddress, fxwarninglbl)) {
                        if(ValidateSM.validateText(email, 50, Strings.invalidEmail, fxwarninglbl) && ValidateSM.isValidEmail(email, Strings.invalidEmail, fxwarninglbl)) {
                            if(ValidateSM.validateNumber(phone, Strings.invalidPhone, fxwarninglbl) && ValidateSM.validateTextAbsolute(phone, 10, Strings.invalidPhone , fxwarninglbl)) {
                                System.out.println("OKAY");
                                Student studentToUp = new Student();
                                
                                studentToUp.setUserID(studentID);
                                studentToUp.setName(name);
                                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                    String birthDate = dt.format(dt.parse(date.getValue().toString()));
                                studentToUp.setBirthDate(birthDate);
                                studentToUp.setAddress(address);
                                studentToUp.setEmail(email);
                                studentToUp.setPhone(phone);
                                
                                if(StudentDao.updateStudent(studentToUp)) {
                                    System.out.println(Strings.studentUpdated);
                                    Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                    stage.close();
                                    
                                    StudentTableUI.hideStage();
                                    System.out.println(Strings.studentUpdated);
                                    UpdateStudentUI.setUpdatedFlag();
                                    StudentTableUI.getInstance().display(StudentTableUI.getStage());
                                }
                                else {
                                    System.out.println(Strings.studentNotUpdated);
                                        Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                        stage.close();
                                }
                                
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets studentID
        fxstudentIdlbl.setText(currStudent.getUserID());
        
        // sets name
        fxnametxt.setText(currStudent.getName());
        
        // sets birthDate
        LocalDate ld = LocalDate.parse(currStudent.getBirthDate());
        fxbirthdatedtpickr.setValue(ld);
        
        // sets address
        fxaddresstxt.setText(currStudent.getAddress());
        
        // sets email
        fxemailtxt.setText(currStudent.getEmail());
        
        // sets phone
        fxphonetxt.setText(currStudent.getPhone());
        
        // sets clear warning
        fxwarninglbl.setText("");
    }    
    
}
