package com.attendance;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AttendanceTableController implements Initializable {
    @FXML
    public DatePicker fxattendancedtpickr;
    public Button fxmarkattendancebtn,fxgobtn;
    public Label fxpresentnolbl,fxabsentnolbl,fxwarninglbl;
    public ComboBox fxgradecmbbx,fxclasscmbbx;
    public TableView fxattendancetable;
    public TableColumn<Attendance, String> fxstudentidtbcl;
    public TableColumn<Attendance, String> fxstudentnametbcl;
    public TableColumn<Attendance, String> fxdatetbcl;
    
    ObservableList<Attendance> attendanceList = FXCollections.observableArrayList();
    
    @FXML
    public void loadMarkAttendance(ActionEvent event) throws IOException {
        System.out.println("Clicked Mark Attendance");
        
        AttendanceMarkUI attendancemarkuiinstance = AttendanceMarkUI.getInstance();
        Stage attendancemarkuistage = new Stage();
        attendancemarkuiinstance.display(attendancemarkuistage);
    }
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
    
    @FXML
    public void setDate(ActionEvent event) throws ParseException {
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selClass, Strings.notSelectedAttendanceClass, fxwarninglbl)) {
                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                String todayDate = dt.format(dt.parse(fxattendancedtpickr.getValue().toString()));
                fxdatetbcl.setText(todayDate);
                
                String grade = selGrade.getSelectedItem();
                String stuClass = selClass.getSelectedItem();
                        
                if(!attendanceList.isEmpty())
                    attendanceList.clear();
                    
                attendanceList.addAll(AttendanceDao.getAttendanceList(grade, stuClass, todayDate));
                
                if(!attendanceList.isEmpty()) {
                    fxpresentnolbl.setText(AttendanceDao.getPresent(grade, stuClass, todayDate)+"");
                    fxabsentnolbl.setText(AttendanceDao.getAbsent(grade, stuClass, todayDate)+"");
                    fxattendancetable.setItems(attendanceList);
                    setLabelInTable(fxattendancetable, Strings.tablePlaceholderBrowse);
                    fxwarninglbl.setText("");
                }
                else {
                    setLabelInTable(fxattendancetable, Strings.noRecordsFoundForThisSelection);
                    fxpresentnolbl.setText("");
                    fxabsentnolbl.setText("");
                }
                
                // animate node
                new FadeIn(fxattendancetable).play();
            }
        }
    }
    
    // animator
    public void animateNode(Node node, int animation) {
        switch(animation) {
            case 0:
                new FadeOut(node).play();
            break;
            case 1:
                new FadeIn(node).play();
            break;
        }
    }
    
    // loads report
    @FXML
    public void loadReport(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_individualReport);
         
        TableView.TableViewSelectionModel<Attendance> selRow = fxattendancetable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            fxwarninglbl.setText(Strings.clear);
            animateNode(fxwarninglbl,0);
            
            // saves the selected attendance first
            Attendance tableMember = selRow.getSelectedItem();
            
            // set a backup of the current member
            AttendanceIndividualReportUI.setThisAttendance(tableMember);
            
            // loads update attendnace UI
            AttendanceIndividualReportUI attendanceindividualreportuiinstance = AttendanceIndividualReportUI.getInstance();
            Stage attendanceindividualreportuistage = new Stage();
            attendanceindividualreportuiinstance.display(attendanceindividualreportuistage);
        }
        else {
            fxwarninglbl.setText(Strings.notSelectedAttendanceMember);
            animateNode(fxwarninglbl,1);
        }
        
    }
    
    // label setter
    public void setLabelInTable(TableView fxTable, String labelText) {
        fxTable.setPlaceholder(new Label(labelText));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize table
        fxstudentidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.userID));
        fxstudentnametbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.name));
        fxdatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.presentAbsent));
        
        // set date to today
        fxattendancedtpickr.setValue(getLocalDate());
        
        // convert date
        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            try {
                String todayDate = dt.format(dt.parse(fxattendancedtpickr.getValue().toString()));
                fxdatetbcl.setText(todayDate);
            }
            catch (ParseException ex) {
                System.out.println(ex);
            }
        
        // set placeholder
        setLabelInTable(fxattendancetable, Strings.tablePlaceholderBrowse);
            
        // set grade
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set present and absent to null
        fxpresentnolbl.setText("");
        fxabsentnolbl.setText("");
        
        // set class
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.class_A,Strings.class_B,Strings.class_C,Strings.class_D);
        fxclasscmbbx.setItems(oblistB);
        
        // clears warning
        fxwarninglbl.setText("");
        
    }    
    
}
