package com.student;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.connection.ValidateSM;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class StudenttableController implements Initializable {
    @FXML
    public Button fxupdatebtn,fxaddstudentbtn,fxgobtn;
    public Label fxwarninglbl;
    public ComboBox fxyearcmbbx,fxgradecmbbx,fxclasscmbbx;
    public TextField fxsearchstudenttxt;
    public TableView fxstudenttable;
    public TableColumn<Student, String> fxstudentidtbcl;
    public TableColumn<Student, String> fxnametbcl;
    public TableColumn<Student, String> fxgradetbcl;
    public TableColumn<Student, String> fxbirthdatetbcl;
    public TableColumn<Student, String> fxentrydatetbcl;
    public TableColumn<Student, String> fxphonetbcl;
    public TableColumn<Student, String> fxaddresstbcl;
    public TableColumn<Student, String> fxemailtbcl;
    public TableColumn<Student, String> fxstatustbcl;
    
    ObservableList<Student> studentlist = FXCollections.observableArrayList();
    
    @FXML
    public void loadUpdate(ActionEvent event) throws IOException {
        TableView.TableViewSelectionModel<Student> selRow = fxstudenttable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            fxwarninglbl.setText(Strings.clear);
            animateNode(fxwarninglbl,0);
            
            // saves the selected item first
            Student tableStudent = selRow.getSelectedItem();
            String studentID = tableStudent.getUserID();
            StudentDao.setCurrentStudent(studentID);
            
            // set a backup of the current student
            UpdateStudentUI.setOldStudent(tableStudent);
            
            // loads change subject UI
            UpdateStudentUI updatestudentuiinstance = UpdateStudentUI.getInstance();
            Stage updatestudentuistage = new Stage();
            updatestudentuiinstance.display(updatestudentuistage);
        }
        else {
            fxwarninglbl.setText(Strings.notSelectedStudent);
            animateNode(fxwarninglbl,1);
        }
    }
    
    @FXML
    public void searchStudents(ActionEvent event) {
        fxstudenttable.getItems().clear();
        String keyword = fxsearchstudenttxt.getText().trim();
        if(!fxsearchstudenttxt.getText().isEmpty()) {
            fxstudenttable.setPlaceholder(new Label(Strings.tablePlaceholderDefault));
            
            studentlist = StudentDao.getStudentList(keyword);
            
            if(!studentlist.isEmpty()) {
                fxstudenttable.setItems(studentlist);
                animateNode(fxstudenttable,1);
            }
            else
                fxstudenttable.setPlaceholder(new Label(Strings.tablePlaceholderNoStudentsFound));
        }
        else {
            fxstudenttable.setPlaceholder(new Label(Strings.tablePlaceholderNoBooksFound));
            fxwarninglbl.setText(Strings.notEnteredSearchStudent);
            animateNode(fxwarninglbl, 1);
        }
    }
    
    @FXML
    public void addStudent(ActionEvent event) throws IOException {
        System.out.println("Clicked Add Student");
        
        AddStudentUI addstudentinstance = AddStudentUI.getInstance();
        Stage addnewstudentstage = new Stage();
        addstudentinstance.display(addnewstudentstage);
    }
    
    @FXML
    public void findStudents(ActionEvent event) {
        SingleSelectionModel<String> selYear = fxyearcmbbx.getSelectionModel();
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selClass = fxclasscmbbx.getSelectionModel();
        
            if(ValidateSM.isCombo(selYear, Strings.invalidYear, fxwarninglbl)) {
                if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
                    if(ValidateSM.isCombo(selClass, Strings.invalidStudentClass, fxwarninglbl)) {
                            String year = selYear.getSelectedItem();
                            String grade = selGrade.getSelectedItem();
                            String stuClass = selClass.getSelectedItem();
                            
                            studentlist.clear();
                            studentlist = StudentDao.loadStudents(year,grade,stuClass);
                            fxstudenttable.setItems(studentlist);
                            
                            animateNode(fxwarninglbl, 0);
                            animateNode(fxstudenttable, 1);
                        }
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
    
    // label setter
    public void setLabelInTable(TableView fxTable, String labelText) {
        fxTable.setPlaceholder(new Label(labelText));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fxstudentidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.userId));
        fxnametbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.name));
        fxgradetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.grade));
        fxbirthdatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.birthDate));
        fxentrydatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.registeredDate));
        fxphonetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.phone));
        fxaddresstbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.address));
        fxemailtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.email));
        fxstatustbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.status));

        // set year
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.year_2018,Strings.year_2019,Strings.year_2020);
        fxyearcmbbx.setItems(oblistA);
        
        // set grades
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistB);
        
        // set classes
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.class_A,Strings.class_B,Strings.class_C,Strings.class_D);
        fxclasscmbbx.setItems(oblistC);
        
        // sets placeholder text of the student table
        if(StudentTableUI.getContext() == null)
            setLabelInTable(fxstudenttable, Strings.tablePlaceholderDefault);
        else {
            switch (StudentTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxstudenttable, Strings.studentAdded);
                    break;
                case Strings.context_update:
                    setLabelInTable(fxstudenttable, Strings.studentChanged);
                    break;
            }
        }
        
        // loads the last added student in the table
        if(StudentTableUI.getLoadFlagLast() == 1) {
            ObservableList<Student> oblist = StudentDao.getLastStudent();
            fxstudenttable.setItems(oblist);
            animateNode(fxstudenttable, 1);
            StudentTableUI.setLoadFlagLast();
        }
        
        // check if updated
        if(UpdateStudentUI.getUpdatedFlag() == 1) {
            setLabelInTable(fxstudenttable, Strings.tablePlaceholderStudentUpdatedSuccessfully);
            UpdateStudentUI.setUpdatedFlag();
        }
        else
            setLabelInTable(fxstudenttable, Strings.tablePlaceholderDefault);
        
        // sets warning to null
        fxwarninglbl.setText("");
    }    
    
}
