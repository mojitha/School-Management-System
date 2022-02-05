package com.student;

import com.connection.ValidateSM;
import com.string.Strings;
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

public class TestReportController implements Initializable {
    @FXML
    public Button fxprintbtn,fxokbtn;
    public Label fxyearlbl,fxgradelbl,fxclasslbl,fxtotalstudentslbl,fxwarninglbl;
    public ComboBox fxyearcmbbx,fxgradecmbbx,fxclasscmbbx;

    @FXML
    public void generateReport(ActionEvent event) {
        System.out.println(Strings.clicked_oK);
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selYear = fxyearcmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selYear, Strings.invalidYear, fxwarninglbl)) {
            if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
                if(ValidateSM.isCombo(selClass, Strings.invalidClass, fxwarninglbl)) {
                        String grade = selGrade.getSelectedItem();
                        String year = selYear.getSelectedItem();
                        String stuClass = selClass.getSelectedItem();
                        
                        String studentCount = StudentDao.getStudentCount(grade,year,stuClass);
                        
                        fxgradelbl.setText(grade);
                        fxyearlbl.setText(year);
                        fxclasslbl.setText(stuClass);
                        fxtotalstudentslbl.setText(studentCount);
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set grade combo box
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set year
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.year_2018,Strings.year_2019,Strings.year_2020);
        fxyearcmbbx.setItems(oblistC);
        
        // set classes
        ObservableList<String> oblistE = FXCollections.observableArrayList();
        oblistE.addAll(Strings.class_A,Strings.class_B,Strings.class_C,Strings.class_D);
        fxclasscmbbx.setItems(oblistE);
        
        // sets warning to null
        fxwarninglbl.setText("");
    }    
    
}
