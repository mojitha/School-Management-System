package com.staff;

import com.connection.ValidateSM;
import com.string.Strings;
import com.subject.SubjectDao;
import com.user.AcademicStaffDao;
import java.net.URL;
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

public class StaffReportController implements Initializable {
    @FXML
    public Button fxprintbtn,fxOKbtn;
    public Label fxtypelbl,fxgradelbl,fxsubjectlbl,fxtotalstafflbl,fxwarninglbl;
    public ComboBox<String> fxtypecmbbx,fxgradecmbbx,fxsubjectcmbbx;

    @FXML
    public void loadReport(ActionEvent event) {
        SingleSelectionModel<String> selType = fxtypecmbbx.getSelectionModel();
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxsubjectcmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selType, Strings.invalidType, fxwarninglbl)) {
            if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
                if(ValidateSM.isCombo(selSubject, Strings.invalidSubject, fxwarninglbl)) {
                    String type = selType.getSelectedItem();
                    String grade = selGrade.getSelectedItem();
                    String subject = selSubject.getSelectedItem();
                    
                    switch (type) {
                        case Strings.Academic:
                            String subjectID = SubjectDao.getSubjectID(grade, subject);
                            int staffCount = AcademicStaffDao.getStaffCount(subjectID);
                            fxtypelbl.setText(type);
                            fxgradelbl.setText(grade);
                            fxsubjectlbl.setText(subject);
                            fxtotalstafflbl.setText(staffCount+"");
                            break;
                        case Strings.NonAcademic:
                            
                            break;
                    }
                }
            }
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets type
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.Academic,Strings.NonAcademic);
        fxtypecmbbx.setItems(oblistA);
        
        // sets grade
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistB);
        
        // sets subjects
        fxsubjectcmbbx.setItems(SubjectDao.getSubjects());
    }    
    
}
