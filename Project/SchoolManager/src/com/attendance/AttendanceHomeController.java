package com.attendance;

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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AttendanceHomeController implements Initializable {
    @FXML
    public BorderPane fxhomeborderpane;
    public Label fxattendancelbl,fxstudentcountlbl,fxclasscountlbl;
    public Button fxgetnotyetmarkedclassesbtn;
    
    // array of which not yet marked classes
    ObservableList<String> stuClasses = FXCollections.observableArrayList();
    
    @FXML
    public void loadClassesThatMarked(ActionEvent event) throws IOException {
        AttendanceClassesMarkedUI attendanceclassesMarkeduiinstance = AttendanceClassesMarkedUI.getInstance();
        Stage attendanceclassesMarkeduistage = new Stage();
        attendanceclassesMarkeduiinstance.display(attendanceclassesMarkeduistage);
    }
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
    
     @FXML
    public void reloadView(MouseEvent event) {
        // checks updated flag of UI and reload if its value gets 1
        if(AttendanceUI.getUpdatedFlag() == 1) {
            fxattendancelbl.setText(AttendanceDao.getPercentageToday(getLocalDate())+"%");
            fxstudentcountlbl.setText(AttendanceDao.getStudentCount()+"");
            fxclasscountlbl.setText(AttendanceDao.getClassCount()+"");
            AttendanceUI.setUpdatedFlag();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // percentage of the students who came today
        fxattendancelbl.setText(AttendanceDao.getPercentageToday(getLocalDate())+"%");
        
        // set student
        fxstudentcountlbl.setText(AttendanceDao.getStudentCount()+"");
        
        // set classes count
        fxclasscountlbl.setText(AttendanceDao.getClassCount()+"");
        
    }    
    
}
