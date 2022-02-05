package com.exam;

import com.connection.IdentitySM;
import com.connection.ValidateSM;
import com.string.Strings;
import com.subject.Subject;
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

public class AddExamController implements Initializable {    
    @FXML
    public ComboBox<String> fxselectgradecmbbx,fxselectsubjectcmbbx,fxselecttermcmbbx,fxselectstarttimecmbbx,fxselectendtimecmbbx;
    public TextField fxexamyeartxt,fxstarttimetxt,fxendtimetxt;
    public Label fxexamidlbl,fxwarninglbl;
    public DatePicker fxexamdatedtpickr;
    public Button fxokbtn,fxcancelbtn;
    
    @FXML
    public void closeAddExam(ActionEvent event) {
        System.out.println(Strings.clicked_cancel);
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }
    
    // adds new exam
    public void okAddNewExam(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_oK);
        
        String examID = fxexamidlbl.getText().trim();
        SingleSelectionModel<String> selGrade = fxselectgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxselectsubjectcmbbx.getSelectionModel();
        String year = fxexamyeartxt.getText().trim();
        SingleSelectionModel<String> selTerm = fxselecttermcmbbx.getSelectionModel();
        String startTime = fxstarttimetxt.getText().trim();
        String endTime = fxendtimetxt.getText().trim();
        DatePicker date = fxexamdatedtpickr;
        
        if(ValidateSM.validateText(examID, 10, Strings.invalidExamID, fxwarninglbl)) {
            if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
                if(ValidateSM.isCombo(selSubject, Strings.invalidExamSubject, fxwarninglbl)) {
                    if(ValidateSM.validateText(year, 4, Strings.invalidExamYearA, fxwarninglbl) || ValidateSM.validateTextAbsolute(year, 4, Strings.invalidExamYearB, fxwarninglbl)) {
                        if(ValidateSM.isCombo(selTerm, Strings.invalidTerm, fxwarninglbl)) {
                            if(ValidateSM.validateText(startTime, 5, Strings.invalidStartTime, fxwarninglbl)) {
                                if(ValidateSM.validateText(endTime, 5, Strings.invalidEndTime, fxwarninglbl)) {
                                    if(ValidateSM.checkDate(date, Strings.invalidExamDate, fxwarninglbl)) {
                                        String grade = selGrade.getSelectedItem();
                                        String subject = selSubject.getSelectedItem();
                                        String term = selTerm.getSelectedItem();
                                        String subjectID = SubjectDao.getSubjectID(grade,subject);
                                        
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
                                        
                                        if(ExamDao.addExam(currExam)) {
                                            Stage stage = AddExamUI.getStage();
                                            stage.close();

                                            ExamTableUI.hideStage();
                                            ExamTableUI.setContext(Strings.context_insert);
                                            ExamTableUI.setUndoFlagInsert();
                                            ExamTableUI.getInstance().display(ExamTableUI.getStage());
                                        }
                                        else {
                                            Stage stage = AddExamUI.getStage();
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
    
    // set subjects
    @FXML
    public void setSubjects(ActionEvent event) {
        String selGrade = fxselectgradecmbbx.getSelectionModel().getSelectedItem();
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        ObservableList<Subject> oblistB = SubjectDao.loadSubjects(selGrade);
            oblistB.stream().forEach((sub) -> {
                oblistA.add(sub.getSubject());
            });
        fxselectsubjectcmbbx.setItems(oblistA);
    }
    
    // sets start time in textfield
    @FXML
    public void setStartTime(ActionEvent event) {
        SingleSelectionModel<String> selStTime = fxselectstarttimecmbbx.getSelectionModel();
        String stTime = selStTime.getSelectedItem();
        fxstarttimetxt.setText(stTime);
        
        ObservableList<String> list = FXCollections.observableArrayList();
        
        int x = selStTime.getSelectedIndex();
        int size = fxselectstarttimecmbbx.getItems().size();
        
        for(int i=x;i<size;i++) {
            list.add(fxselectstarttimecmbbx.getItems().get(i));
        }
        
        fxselectendtimecmbbx.setItems(list);
    }
    
    // sets end time in textfie
    @FXML
    public void setEndTime(ActionEvent event) {
        SingleSelectionModel<String> selEnTime = fxselectendtimecmbbx.getSelectionModel();
        String enTime = selEnTime.getSelectedItem();
        fxendtimetxt.setText(enTime);
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
    
    // changes year on date change
    @FXML
    public void changeYear(ActionEvent event) {
        int year = fxexamdatedtpickr.getValue().getYear();
        fxexamyeartxt.setText(year+"");
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set exam ID
        fxexamidlbl.setText(IdentitySM.getID(Strings.Exam));
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxselectgradecmbbx.setItems(oblistA);
        
        // set terms
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.term_1,Strings.term_2,Strings.term_3);
        fxselecttermcmbbx.setItems(oblistB);
        
        // set start times and end times in combo boxes
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(getTime());
        fxselectstarttimecmbbx.setItems(oblistC);
        fxselectendtimecmbbx.setItems(oblistC);
        
        // sets the year
        String year = LocalDate.now().getYear()+"";
        fxexamyeartxt.setText(year);
        
        // sets the exam date
        fxexamdatedtpickr.setValue(getLocalDate());
        
        // set warning to null
        fxwarninglbl.setText("");
    }    
    
}
