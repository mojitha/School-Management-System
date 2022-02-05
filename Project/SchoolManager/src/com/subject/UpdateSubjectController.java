package com.subject;

import animatefx.animation.FadeIn;
import com.connection.ValidateSM;
import com.string.Strings;
import com.user.AcademicStaffDao;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateSubjectController implements Initializable {
    @FXML
    public Label fxwarninglbl,fxsubjectIdlbl,fxavailabilitylbl;
    public TextField fxsubjecttxt;
    public ComboBox<String> fxgradecmbbx,fxteachercmbbx;
    public Button fxOKbtn,fxcancelbtn;

    // subject to be changed
    Subject currSubject = UpdateSubjectUI.getOldSubject();
    
    @FXML
    public void cancelUpdateSubject(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void okUpdateSubject(ActionEvent event) throws IOException {
        String subjectID = fxsubjectIdlbl.getText().trim();
        String subjectName = fxsubjecttxt.getText().trim();
        SingleSelectionModel<String> subjectGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> subjectTeacher = fxteachercmbbx.getSelectionModel();
        
        if(ValidateSM.validateText(subjectID, 10, Strings.invalidSubjectID, fxwarninglbl)) {
            if(ValidateSM.validateText(subjectName, 50, Strings.invalidSubjectName, fxwarninglbl)) {
                if(ValidateSM.isCombo(subjectGrade, Strings.invalidSubjectGrade, fxwarninglbl)) {
                    if(ValidateSM.isCombo(subjectTeacher, Strings.invalidSubjectTeacher, fxwarninglbl)) {
                        System.out.println("OKAY");
                        Subject subjectToUp = new Subject();

                        String grade = subjectGrade.getSelectedItem();
                        String teacher = subjectTeacher.getSelectedItem();
                        String teacherID = AcademicStaffDao.getTeacherID(teacher);
                        String todayDate = getLocalDate()+"";
                        
                        subjectToUp.setSubjectID(subjectID);
                        subjectToUp.setSubject(subjectName);
                        subjectToUp.setGrade(grade);
                        subjectToUp.setTeacher(teacher);
                        subjectToUp.setTeacherID(teacherID);
                        
                        if(ValidateSM.checkTeachability(subjectToUp, Strings.teacherNotAvailable, fxwarninglbl)) {
                            if(SubjectDao.updateSubject(subjectToUp)) {
                                if(AcademicStaffDao.getAvailability(subjectToUp.getTeacher()) == 0)
                                    SubjectDao.insertTeacherSubject(subjectID, teacherID, todayDate);
                                else
                                    SubjectDao.updateTeacherSubject(subjectID, teacherID, todayDate);
                                
                                System.out.println(Strings.subjectUpdated);
                                Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                stage.close();

                                SubjectTableUI.hideStage();
                                System.out.println(Strings.subjectUpdated);
                                UpdateSubjectUI.setUpdatedFlag();
                                SubjectTableUI.getInstance().display(SubjectTableUI.getStage());
                            }
                            else {
                                System.out.println(Strings.subjectNotUpdated);
                                    Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                    stage.close();
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
    
    @FXML
    public void reCheckAvailability(ActionEvent event) {
        SingleSelectionModel<String> selTeacher = fxteachercmbbx.getSelectionModel();
        String teacher = selTeacher.getSelectedItem();
        
        fxavailabilitylbl.setText("Teaching "+AcademicStaffDao.getAvailability(teacher)+" Subjects");
        new FadeIn(fxavailabilitylbl).play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets staffID
        fxsubjectIdlbl.setText(currSubject.getSubjectID());
        
        // sets subject name
        fxsubjecttxt.setText(currSubject.getSubject());
        
        // sets grade
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistB);
        String selGrade = currSubject.getGrade();
        fxgradecmbbx.setValue(selGrade);
        
        // sets teachers
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(AcademicStaffDao.getTeachers());
        fxteachercmbbx.setItems(oblistA);
        String selTeacher = currSubject.getTeacher();
        fxteachercmbbx.setValue(selTeacher);
        
        // clears availability
        fxavailabilitylbl.setText("Teaching "+AcademicStaffDao.getAvailability(currSubject.getTeacher())+" Subjects");
        
        // clears warning
        fxwarninglbl.setText("");
    }    
    
}
