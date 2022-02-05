package com.exam;

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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


public class ExamTableController implements Initializable {
    @FXML
    public Button fxaddexambtn,fxupdatebtn,fxgobtn,fxundobtn;
    public ComboBox fxgradecmbbx,fxyearcmbbx,fxtermcmbbx;
    public Label fxwarninglbl;
    public TableView fxexamtable;
    public TableColumn<Exam, String> fxexamidtbcl;
    public TableColumn<Exam, String> fxsubjecttbcl;
    public TableColumn<Exam, String> fxgradetbcl;
    public TableColumn<Exam, String> fxyeartbcl;
    public TableColumn<Exam, String> fxtermtbcl;
    public TableColumn<Exam, String> fxstarttimetbcl;
    public TableColumn<Exam, String> fxendtimetbcl;
    public TableColumn<Exam, String> fxdatetbcl;
    
    // table exams list
    private ObservableList<Exam> oblist = FXCollections.observableArrayList();
    
    @FXML
    public void loadAddExam(ActionEvent event) throws IOException{
        AddExamUI addexaminstance = AddExamUI.getInstance();
        Stage addexamstage = new Stage();
        addexaminstance.display(addexamstage);
    }
    
    // loads customized exams
    public void loadExamTable(ActionEvent event) {
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selYear = fxyearcmbbx.getSelectionModel();
        SingleSelectionModel<String> selTerm = fxtermcmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selYear, Strings.invalidYear, fxwarninglbl)) {
                if(ValidateSM.isCombo(selTerm, Strings.invalidTerm, fxwarninglbl)) {
                    String grade = selGrade.getSelectedItem();
                    String year = selYear.getSelectedItem();
                    String term = selTerm.getSelectedItem();
                    
                    oblist = ExamDao.getExams(grade,year,term);
                    
                    fxexamtable.setItems(oblist);
                    
                    // animate table
                    new FadeIn(fxexamtable).play();
                }
            }
        }
        
    }
    
    @FXML
    public void undoLast(ActionEvent event) {
        if(ExamDao.removeLast()) {
            System.out.println(Strings.examRemoved);
            refreshTable();   // do not refresh the table
            ExamTableUI.setUndoFlagInsert();
            fxundobtn.setDisable(true);
            setLabelInTable(fxexamtable, Strings.examRemoved);
        }
    }
    
    // refreshes table when due
    public void refreshTable() {
        oblist.clear();
        fxexamtable.setItems(oblist);
        animateNode(fxexamtable,1);
    }
    
    @FXML
    public void updateExam(ActionEvent event) throws IOException {
        TableView.TableViewSelectionModel<Exam> selRow = fxexamtable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            fxwarninglbl.setText(Strings.clear);
            animateNode(fxwarninglbl,0);
            
            // saves the selected exam first
            Exam tableExam = selRow.getSelectedItem();
            String examID = tableExam.getExamID();
            ExamDao.setCurrentExam(examID);
            
            // set a backup of the current exam
            UpdateExamUI.setOldExam(tableExam);
            
            // loads update exam UI
            if(ExamDao.getExamStatus(examID).equals(Strings.status_exam_ahead)) {
                UpdateExamUI updateexamuiinstance = UpdateExamUI.getInstance();
                Stage updateexamuistage = new Stage();
                updateexamuiinstance.display(updateexamuistage);
            }
            else {
                fxwarninglbl.setText(Strings.invalidExamStatusWarning);
                animateNode(fxwarninglbl,1);
            }
        }
        else {
            fxwarninglbl.setText(Strings.notSelectedExam);
            animateNode(fxwarninglbl,1);
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
        // set values for the exam table
        fxexamidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.examID));
        fxsubjecttbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.subjectID));
        fxgradetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.grade));
        fxyeartbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.year));
        fxtermtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.term));
        fxstarttimetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.startTime));
        fxendtimetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.endTime));
        fxdatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.examDate));
        
        // sets placeholder text of the exam table
        if(ExamTableUI.getContext() == null)
            setLabelInTable(fxexamtable, Strings.tablePlaceholderDefault);
        else {
            switch (ExamTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxexamtable, Strings.examAdded);
                    break;
                case Strings.context_update:
                    setLabelInTable(fxexamtable, Strings.examChanged);
                    break;
            }
        }
        
        // check Undo Buttons when loading
        if(ExamTableUI.getUndoFlagInsert()== 1)
            fxundobtn.setDisable(false);
        if(ExamTableUI.getUndoFlagUpdate()== 1)
            fxundobtn.setDisable(false);
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set year
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.year_2018,Strings.year_2019,Strings.year_2020);
        fxyearcmbbx.setItems(oblistB);
        
        // set terms
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.term_1,Strings.term_2,Strings.term_3);
        fxtermcmbbx.setItems(oblistC);
        
        // clear warning
        fxwarninglbl.setText("");
    }    
    
}
