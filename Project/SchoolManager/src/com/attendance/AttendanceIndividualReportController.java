package com.attendance;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class AttendanceIndividualReportController implements Initializable {
    @FXML
    public Button fxcancelbtn,fxprintbtn;
    public Label fxdatelbl,fxnamelbl,fxstudentidlbl,fxgradelbl,fxclasslbl,fxmonthlylbl,fxmonthlypercentlbl,fxdatesheldlbl;
    
    @FXML
    public void closeReport(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Attendance currStu = AttendanceIndividualReportUI.getThisAttendance();
        String studentID = currStu.getUserID();
        String date = currStu.getDate();
        String attendance[] = AttendanceDao.getMonthlyPresent(studentID,date);
        
        fxdatelbl.setText(date);
        fxstudentidlbl.setText(studentID);
        fxnamelbl.setText(currStu.getName());
        fxgradelbl.setText(currStu.getGrade());
        fxclasslbl.setText(currStu.getStuClass());
        fxmonthlylbl.setText(attendance[1]);
        fxmonthlypercentlbl.setText(attendance[2]);
        fxdatesheldlbl.setText(attendance[0]);
    }
    
}
