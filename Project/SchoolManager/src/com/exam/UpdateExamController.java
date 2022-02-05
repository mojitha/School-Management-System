package com.exam;

import com.connection.ValidateSM;
import com.string.Strings;
import com.subject.SubjectDao;
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

public class UpdateExamController implements Initializable {
    @FXML
    public Label fxwarninglbl,fxexamidlbl;
    public ComboBox<String> fxsubjectcmbbx,fxgradecmbbx,fxtermcmbbx,fxstarttimecmbbx,fxendtimecmbbx;
    public TextField fxyeartxt;
    public DatePicker fxexamdtpickr;
    public Button fxOKbtn,fxcancelbtn;

    // exam to be changed
    Exam currExam = UpdateExamUI.getOldExam();
    
    @FXML
    public void cancelUpdateExam(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void updateExam(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_oK);
        
        String examID = fxexamidlbl.getText().trim();
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxsubjectcmbbx.getSelectionModel();
        String year = fxyeartxt.getText().trim();
        SingleSelectionModel<String> selTerm = fxtermcmbbx.getSelectionModel();
        SingleSelectionModel<String> selStartTime = fxstarttimecmbbx.getSelectionModel();
        SingleSelectionModel<String> selEndTime = fxendtimecmbbx.getSelectionModel();
        DatePicker date = fxexamdtpickr;
        
        if(ValidateSM.validateText(examID, 10, Strings.invalidExamID, fxwarninglbl)) {
            if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
                if(ValidateSM.isCombo(selSubject, Strings.invalidExamSubject, fxwarninglbl)) {
                    if(ValidateSM.validateText(year, 4, Strings.invalidExamYearA, fxwarninglbl) || ValidateSM.validateTextAbsolute(year, 4, Strings.invalidExamYearB, fxwarninglbl)) {
                        if(ValidateSM.isCombo(selTerm, Strings.invalidTerm, fxwarninglbl)) {
                            if(ValidateSM.isCombo(selStartTime, Strings.invalidStartTime, fxwarninglbl)) {
                                if(ValidateSM.isCombo(selEndTime, Strings.invalidEndTime, fxwarninglbl)) {
                                    if(ValidateSM.checkDate(date, Strings.invalidExamDate, fxwarninglbl)) {
                                        String grade = selGrade.getSelectedItem();
                                        String subject = selSubject.getSelectedItem();
                                        String term = selTerm.getSelectedItem();
                                        String subjectID = SubjectDao.getSubjectID(grade,subject);
                                        String startTime = selStartTime.getSelectedItem();
                                        String endTime = selEndTime.getSelectedItem();
                                        
                                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                        String examDate = dt.format(dt.parse(date.getValue().toString()));
                                        
                                        Exam currExam = Exam.getInstance();
                                        
                                        currExam.setExamID(examID);
                                        currExam.setSubjectID(subjectID);
                                        currExam.setGrade(grade);
                                        currExam.setYear(year);
                                        currExam.setTerm(term);
                                        currExam.setStartTime(startTime+":000");
                                        currExam.setEndTime(endTime+":000");
                                        currExam.setExamDate(examDate);
                                        currExam.setStatus(Strings.status_exam_ahead);
                                        
                                        if(ExamDao.updateExam(currExam)) {
                                            Stage stage = UpdateExamUI.getStage();
                                            stage.close();

                                            ExamTableUI.hideStage();
                                            ExamTableUI.setContext(Strings.context_update);
                                            ExamTableUI.getInstance().display(ExamTableUI.getStage());
                                        }
                                        else {
                                            Stage stage = UpdateExamUI.getStage();
                                            stage.close();
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
    
    // gets time
    public ObservableList<String> getTime() {
        ObservableList<String> list = FXCollections.observableArrayList();
        String timeX = "";
        String timeY = "";
        
        for(int i=8;i<14;i++) {
            timeX = i+":00";
            timeY = i+":30";
            list.addAll(timeX,timeY);
        }
        
        return list;
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
        // sets examID
        fxexamidlbl.setText(currExam.getExamID());

        // sets subject
        fxsubjectcmbbx.setItems(SubjectDao.getSubjects());
        fxsubjectcmbbx.setValue(SubjectDao.getSubjectName(currExam.getGrade(), currExam.getSubjectID()));
        
        // sets grade
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        fxgradecmbbx.setValue(currExam.getGrade());
        
        // sets year
        fxyeartxt.setText(currExam.getYear());
        
        // sets term
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.term_1,Strings.term_2,Strings.term_3);
        fxtermcmbbx.setItems(oblistB);
        fxtermcmbbx.setValue(currExam.getTerm());
        
        // sets starttime and endtime
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(getTime());
        fxstarttimecmbbx.setItems(oblistC);
        fxendtimecmbbx.setItems(oblistC);
        fxstarttimecmbbx.setValue(currExam.getStartTime());
        fxendtimecmbbx.setValue(currExam.getEndTime());
        
        // sets the exam date
        fxexamdtpickr.setValue(getLocalDate());
        
        // sets warning
        fxwarninglbl.setText("");
    }    
    
}
