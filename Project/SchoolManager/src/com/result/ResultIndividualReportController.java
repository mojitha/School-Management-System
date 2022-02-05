package com.result;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
/*


import com.connection.ValidateSM;
import com.string.Strings;
import com.subject.SubjectDao;
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
*/

public class ResultIndividualReportController implements Initializable {
 /*
    @FXML
    public Button fxprintbtn,fxokbtn;
    public ComboBox fxgradecmbbx,fxsubjectcmbbx,fxyearcmbbx,fxtermcmbbx;
    public Label fxwarninglbl,fxsubjectandnamelbl,fxgradelbl,fxyearlbl,fxtermlbl,fxtotalstulbl,fxpassstucountlbl,fxmeritslbl,fxavgmarkslbl;
            
    @FXML
    public void generateReport(ActionEvent event) {
        System.out.println(Strings.clicked_oK);
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxsubjectcmbbx.getSelectionModel();
        SingleSelectionModel<String> selYear = fxyearcmbbx.getSelectionModel();
        SingleSelectionModel<String> selTerm = fxtermcmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selSubject, Strings.invalidSubject, fxwarninglbl)) {
                if(ValidateSM.isCombo(selYear, Strings.invalidYear, fxwarninglbl)) {
                    if(ValidateSM.isCombo(selTerm, Strings.invalidTerm, fxwarninglbl)) {
                        String grade = selGrade.getSelectedItem();
                        String subject = selSubject.getSelectedItem();
                        String year = selYear.getSelectedItem();
                        String term = selTerm.getSelectedItem();
                        
                        String examID = ExamDao.getExam(grade,subject,year,term);
                        String subjectID = SubjectDao.getSubjectID(grade, subject);
                        String examTookCount = ExamDao.getExamTookCount(examID,subjectID);
                        String examPassCount = ExamDao.getExamPassCount(examID,subjectID);
                        String examMeritCount = ExamDao.getExamMeritCount(examID,subjectID);
                        String totalMarks = ExamDao.getTotalMarks(examID,subjectID);
                        double examAverage = Double.parseDouble(totalMarks) / Double.parseDouble(examTookCount);
                        
                        fxsubjectandnamelbl.setText(subject+" "+subjectID);
                        fxgradelbl.setText(grade);
                        fxyearlbl.setText(year);
                        fxtermlbl.setText(term);
                        fxtotalstulbl.setText(examTookCount);
                        fxpassstucountlbl.setText(examPassCount);
                        fxmeritslbl.setText(examMeritCount);
                        fxavgmarkslbl.setText(examAverage+"");
                    }
                }
            }
        }
    }

    @FXML
    public void printReport(ActionEvent event) {

    }

    */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        // set grade combo box
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set subjects
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(SubjectDao.getSubjects());
        fxsubjectcmbbx.setItems(oblistB);
        
        // set year
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.year_2018,Strings.year_2019,Strings.year_2020);
        fxyearcmbbx.setItems(oblistC);
        
        // set term
        ObservableList<String> oblistD = FXCollections.observableArrayList();
        oblistD.addAll(Strings.term_1,Strings.term_2,Strings.term_3);
        fxtermcmbbx.setItems(oblistD);
        
        // sets warning to null
        fxwarninglbl.setText("");
    */    
    }    
    
}
