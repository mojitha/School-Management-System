package com.subject;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.connection.ValidateSM;
import com.string.Strings;
import com.user.AcademicStaff;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SubjectTableController implements Initializable {
    @FXML
    public Button fxaddsubjectbtn,fxupdatesubjectbtn,fxgobtn;
    public ComboBox fxgradecmbbx;
    public Label fxwarninglbl;
    public TableView fxsubjecttable;
    public TableColumn<Subject, String> fxsubjectidtbcl;
    public TableColumn<Subject, String> fxsubjectnametbcl;
    public TableColumn<Subject, String> fxsubjectgradetbcl;
    public TableColumn<AcademicStaff, String> fxteachertbcl;
    
    ObservableList<Subject> subjectList = FXCollections.observableArrayList();
    
    @FXML
    public void loadAddSubject(ActionEvent event) throws IOException {
        System.out.println("Clicked Add Subject");
        
        AddSubjectUI addnewsubjectinstance = AddSubjectUI.getInstance();
        Stage addnewsubjectstage = new Stage();
        addnewsubjectinstance.display(addnewsubjectstage);
    }
    
    @FXML
    public void loadUpdateSubject(ActionEvent event) throws IOException {
        TableView.TableViewSelectionModel<Subject> selRow = fxsubjecttable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            fxwarninglbl.setText(Strings.clear);
            animateNode(fxwarninglbl,0);
            
            // saves the selected item first
            Subject tableSubject = selRow.getSelectedItem();
            String subjectID = tableSubject.getSubjectID();
            SubjectDao.setCurrentSubject(subjectID);
            
            // set a backup of the current subject
            UpdateSubjectUI.setOldSubject(tableSubject);
            
            // loads change subject UI
            UpdateSubjectUI updatesubjectuiinstance = UpdateSubjectUI.getInstance();
            Stage updatesubjectuistage = new Stage();
            updatesubjectuiinstance.display(updatesubjectuistage);
        }
        else {
            fxwarninglbl.setText(Strings.notSelectedSubject);
            animateNode(fxwarninglbl,1);
        }
    }
    
    @FXML
    public void loadTable(ActionEvent event) {
        if(!subjectList.isEmpty())
            subjectList.clear();
        
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            new FadeOut(fxwarninglbl).play();
            String grade = selGrade.getSelectedItem();
            
            subjectList.addAll(SubjectDao.loadSubjectsTable(grade));
            
            fxsubjecttable.setItems(subjectList);
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
        // initialize columns of table
        fxsubjectidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.subjectID));
        fxsubjectnametbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.subject));
        fxsubjectgradetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.grade));
        fxteachertbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.teacher));
        
        // sets placeholder text of the student table
        if(SubjectTableUI.getContext() == null)
            setLabelInTable(fxsubjecttable, Strings.tablePlaceholderDefault);
        else {
            switch (SubjectTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxsubjecttable, Strings.subjectAdded);
                    break;
                case Strings.context_update:
                    setLabelInTable(fxsubjecttable, Strings.subjectChanged);
                    break;
            }
        }
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // check if updated
        if(UpdateSubjectUI.getUpdatedFlag() == 1) {
            setLabelInTable(fxsubjecttable, Strings.tablePlaceholderSubjectUpdatedSuccessfully);
            UpdateSubjectUI.setUpdatedFlag();
        }
        else
            setLabelInTable(fxsubjecttable, Strings.tablePlaceholderDefault);
        
        // set warning to null
        fxwarninglbl.setText("");
    }
    
}
