package com.student;

import animatefx.animation.FadeIn;
import com.connection.IdentitySM;
import com.connection.ValidateSM;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddstudentController implements Initializable {
    @FXML
    public Label fxstudentIdlbl,fxwarninglbl,fxavailabilitylbl;
    public TextField fxnametxt,fxaddresstxt,fxemailtxt,fxphonetxt;
    public ComboBox fxgradecmbbx,fxreligioncmbbx,fxclasscmbbx;
    public DatePicker fxbirthdatedtpickr;
    public Button fxcancelbtn,fxOKbtn;
    
    @FXML
    public void closeAddStudent(ActionEvent event) {
        System.out.println(Strings.clicked_close);
        
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    public void okAddStudent(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_oK);
        
        String studentID = fxstudentIdlbl.getText().trim();
        String name = fxnametxt.getText().trim();
        SingleSelectionModel<String> grade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> stuClass = fxclasscmbbx.getSelectionModel();
        DatePicker date = fxbirthdatedtpickr;
        String address = fxaddresstxt.getText().trim();
        String email = fxemailtxt.getText().trim();
        String phone = fxphonetxt.getText().trim();
        SingleSelectionModel<String> religion = fxreligioncmbbx.getSelectionModel();
        
        if(ValidateSM.validateText(studentID, 10, Strings.invalidStudentID, fxwarninglbl)) {
            if(ValidateSM.validateText(name, 30, Strings.invalidName , fxwarninglbl)) {
                if(ValidateSM.isCombo(grade, Strings.invalidGrade, fxwarninglbl)) {
                    if(ValidateSM.isCombo(stuClass, Strings.notSelectedClass, fxwarninglbl)) {
                        if(ValidateSM.checkDate(date, Strings.invalidBirthDate, fxwarninglbl)) {
                            if(ValidateSM.validateText(address, 50, Strings.invalidAddress , fxwarninglbl)) {
                                if(ValidateSM.validateText(email, 50, Strings.invalidEmail , fxwarninglbl) && ValidateSM.isValidEmail(email, Strings.invalidEmail, fxwarninglbl)) {
                                    if(ValidateSM.validateNumber(phone, Strings.invalidPhone, fxwarninglbl) && ValidateSM.validateTextAbsolute(phone, 10, Strings.invalidPhone , fxwarninglbl)) {
                                        if(ValidateSM.isCombo(religion, Strings.invalidReligion, fxwarninglbl)) {
                                            String selectedGrade = grade.getSelectedItem();
                                            String selectedClass = stuClass.getSelectedItem();
                                            String selectedReligion = religion.getSelectedItem();
                                            
                                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                            String birthdate = dt.format(dt.parse(date.getValue().toString()));
                                            
                                            if(StudentDao.checkClassAvailability(selectedGrade, selectedClass)) {
                                                Student currStudent = Student.getInstance();
                                                
                                                    currStudent.setUserID(studentID);
                                                    currStudent.setGrade(selectedGrade);
                                                    currStudent.setStuClass(selectedClass);
                                                    currStudent.setReligion(selectedReligion);
                                                    currStudent.setUserID(studentID);
                                                    currStudent.setName(name);
                                                    currStudent.setUserName(null);
                                                    currStudent.setPassword(null);
                                                    currStudent.setBirthDate(birthdate);
                                                    currStudent.setRegisteredDate(getLocalDate().toString());
                                                    currStudent.setEmail(email);
                                                    currStudent.setPhone(phone);
                                                    currStudent.setAddress(address);
                                                    currStudent.setBio(null);
                                                    currStudent.setImg(null);
                                                    
                                                if(StudentDao.addNewStudent(currStudent)) {
                                                    Stage stage = AddStudentUI.getStage();
                                                    stage.close();
                                                    
                                                    StudentTableUI.hideStage();
                                                    StudentTableUI.setContext(Strings.context_insert);
                                                    StudentTableUI.setLoadFlagLast();
                                                    StudentTableUI.getInstance().display(StudentTableUI.getStage());
                                                }
                                                else {
                                                    Stage stage = AddStudentUI.getStage();
                                                    stage.close();
                                                }
                                                
                                            }
                                            else {
                                                fxwarninglbl.setText(Strings.invalidClass);
                                                new FadeIn(fxwarninglbl).play();
                                                
                                                fxavailabilitylbl.setText(Strings.notavailable);
                                                new FadeIn(fxavailabilitylbl).play();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set student id when load
        fxstudentIdlbl.setText(IdentitySM.getID(Strings.Student));
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set classes
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.class_A,Strings.class_B,Strings.class_C,Strings.class_D);
        fxclasscmbbx.setItems(oblistB);
        
        // set religions
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.religion_buddhism,Strings.religion_christianity,Strings.religion_hindu,Strings.religion_catholicism,Strings.religion_islam);
        fxreligioncmbbx.setItems(oblistC);
        
        // set warning as null
        fxwarninglbl.setText("");
        
        // set availability to null
        fxavailabilitylbl.setText("");
    }    
    
}
