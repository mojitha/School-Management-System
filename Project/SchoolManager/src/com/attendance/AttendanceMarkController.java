package com.attendance;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.connection.ValidateSM;
import com.string.Strings;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class AttendanceMarkController implements Initializable {
    @FXML
    public Button fxcancelbtn,fxokbtn,fxgobtn,fxnobtn,fxyesbtn,fxaddattendancebtn;
    public Label fxdatelbl,fxwarninglbl,fxupdatedlbl;
    public ComboBox fxgradecmbbx,fxclasscmbbx;
    public HBox fxmarkclassnowhbox;
    public TextField fxsearchstudenttxt;
    public TableView<Attendance> fxmarkattendancetable;
    public TableColumn<Attendance, String> fxstudentidtbcl;
    public TableColumn<Attendance, String> fxstudentname;
    public TableColumn<Attendance, String> fxpresentabsenttbcl;
    
    private static int checkWarn = 0;
    
    ObservableList<Attendance> markAttendanceList = FXCollections.observableArrayList();
    
    @FXML
    public void closeMarkAttendance(ActionEvent event) {
        System.out.println(Strings.clicked_cancel);
        
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }
    
    // add new table for a class
    @FXML
    public void addAttendance(ActionEvent event) throws InterruptedException {
        System.out.println(Strings.clicked_update);
        
        new FadeOut(fxaddattendancebtn).play();
        fxaddattendancebtn.setVisible(false);
        new FadeIn(fxokbtn).play();
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidAttendanceGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selClass, Strings.invalidAttendanceClass, fxwarninglbl)) {
                ObservableList<Attendance> insertList = FXCollections.observableArrayList();
                String todayDate = getLocalDate()+"";
                
                insertList.addAll(markAttendanceList);
                
                insertList.stream().map((at) -> {
                    if(at.getSelect().isSelected())
                        at.setPresentAbsent("1");
                    else
                        at.setPresentAbsent("0");
                    return at;                    
                }).forEach((at) -> {
                    System.out.println(at.getUserID()+" "+at.getDate()+" "+at.getPresentAbsent());
                });
                
                if(AttendanceDao.saveAndUpdate(insertList,todayDate,Strings.context_insert)) {
                    fxupdatedlbl.setText(Strings.attendanceUpdatedSuccessfully); 
                    fxupdatedlbl.setVisible(true);
                    new FadeIn(fxupdatedlbl).play();
                    fxupdatedlbl.setVisible(true);
                    AttendanceUI.setUpdatedFlag();
                }
                else {
                    fxwarninglbl.setText(Strings.attendanceNotUpdated);
                    new FadeIn(fxwarninglbl).play();
                }
            }
        }
        
    }
    
    // update recent table for a class
    @FXML
    public void okUpdateAttendance(ActionEvent event) {
        System.out.println(Strings.clicked_oK);

        if(fxmarkclassnowhbox.isVisible())
            hideMarkNowHbox(null);
                
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidAttendanceGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selClass, Strings.invalidAttendanceClass, fxwarninglbl)) {

                ObservableList<Attendance> markedList = FXCollections.observableArrayList();
                String todayDate = getLocalDate()+"";
                
                markedList.addAll(markAttendanceList);
                
                markedList.stream().map((at) -> {
                    if(at.getSelect().isSelected())
                        at.setPresentAbsent("1");
                    else
                        at.setPresentAbsent("0");
                    return at;                    
                }).forEach((at) -> {
                    System.out.println(at.getUserID()+" "+at.getDate()+" "+at.getPresentAbsent());
                });

                if(AttendanceDao.saveAndUpdate(markedList,todayDate,Strings.context_update)) {
                    fxupdatedlbl.setText(Strings.attendanceUpdatedSuccessfully); 
                    fxupdatedlbl.setVisible(true);
                    new FadeIn(fxupdatedlbl).play();
                    fxupdatedlbl.setVisible(true);
                    AttendanceUI.setUpdatedFlag();
                }
                else {
                    fxwarninglbl.setText(Strings.attendanceNotUpdated);
                    new FadeIn(fxwarninglbl).play();
                }
                
            }
        }
        
    }
    
    @FXML
    public void loadTable(ActionEvent event) {
        if(fxmarkclassnowhbox.isVisible()) {
            hideMarkNowHbox(null);
            new FadeIn(fxokbtn).play();
        }
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidAttendanceGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selClass, Strings.invalidAttendanceClass, fxwarninglbl)) {
                checkWarn = 1;
                
                String grade = selGrade.getSelectedItem();
                String stuClass = selClass.getSelectedItem();
                String todayDate = getLocalDate()+"";
                
                markAttendanceList.clear();
                markAttendanceList = AttendanceDao.getAttendanceListMark(grade, stuClass, todayDate);
                
                if(!markAttendanceList.isEmpty()) {
                    fxmarkattendancetable.setItems(markAttendanceList);
                    setLabelInTable(fxmarkattendancetable, Strings.tablePlaceholderAttendance);
                }
                else {
                    setLabelInTable(fxmarkattendancetable, Strings.tablePlaceholderAttendanceNotYetMarked);
                    fxmarkclassnowhbox.setVisible(true);
                    new FadeIn(fxmarkclassnowhbox).play();
                }
                
                // animate the node
                new FadeIn(fxmarkattendancetable).play();
            }
        }
    }
    
    // load mark table to unmarked class
    @FXML
    public void loadMarkNowTable(ActionEvent event) {
        if(fxmarkclassnowhbox.isVisible())
            hideMarkNowHbox(null);
        
        fxaddattendancebtn.setVisible(true);
        new FadeIn(fxaddattendancebtn).play();
        new FadeOut(fxokbtn).play();
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidAttendanceGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selClass, Strings.invalidAttendanceClass, fxwarninglbl)) {
                checkWarn = 1;
                
                String grade = selGrade.getSelectedItem();
                String stuClass = selClass.getSelectedItem();
                String todayDate = getLocalDate()+"";
        
                markAttendanceList.clear();
                markAttendanceList = AttendanceDao.loadUnmarkedStudents(grade,stuClass,todayDate);
                fxmarkattendancetable.setItems(markAttendanceList);
                
                // animate the node
                new FadeIn(fxmarkattendancetable).play();
            }
        }
    }
    
    // hides mark now hbox
    @FXML
    public void hideMarkNowHbox(ActionEvent event) {
        new FadeIn(fxmarkclassnowhbox).play();
        fxmarkclassnowhbox.setVisible(false);
    }
    
    // clear warning
    @FXML
    public void clearWarning(MouseEvent event) {
        if(checkWarn == 1) {
            new FadeOut(fxwarninglbl).play();
            fxwarninglbl.setText("");
            checkWarn = 0;
        }
        
        if(fxupdatedlbl.isVisible()) {
            new FadeOut(fxupdatedlbl).play();
            fxupdatedlbl.setText("");
            fxupdatedlbl.setVisible(false);
        }
    }
    
    // label setter
    public void setLabelInTable(TableView fxTable, String labelText) {
        fxTable.setPlaceholder(new Label(labelText));
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
        // set table
        fxstudentidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.userID));
        fxstudentname.setCellValueFactory(new PropertyValueFactory<>(Strings.name));
        fxpresentabsenttbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.select));
        
        // set date
        String currDate = getLocalDate().toString();
        fxdatelbl.setText(currDate);
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set classes
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.class_A,Strings.class_B,Strings.class_C,Strings.class_D);
        fxclasscmbbx.setItems(oblistB);
        
        // set placeholder
        setLabelInTable(fxmarkattendancetable, Strings.tablePlaceholderAttendance);
        
        // hide updatebtn
        fxaddattendancebtn.setVisible(false);
        
        // hide hbox
        fxmarkclassnowhbox.setVisible(false);
        
        // sets updated to null
        fxupdatedlbl.setText("");
        
        // sets warning to null
        fxwarninglbl.setText("");
    }

}