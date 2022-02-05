package com.result;

import com.subject.SubjectDao;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.connection.ValidateSM;
import com.string.Strings;
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

public class ResultTableController implements Initializable {
    @FXML
    public ComboBox<String> fxgradecmbbx,fxsubjectcmbbx,fxyearcmbbx,fxtermcmbbx;
    public Button fxgobtn,fxaddexambtn;
    public TableView<?> fxresulttable;
    public TableColumn<Result, ?> fxresultidtbcl;
    public TableColumn<Result, ?> fxstudentidtbcl;
    public TableColumn<Result, ?> fxstudentnametbcl;
    public TableColumn<Result, ?> fxresulttbcl;
    public Label fxwarninglbl;
    
    // table results list
    private ObservableList oblist = FXCollections.observableArrayList();

    @FXML
    public void loadResultTable(ActionEvent event) {
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxsubjectcmbbx.getSelectionModel();
        SingleSelectionModel<String> selYear = fxyearcmbbx.getSelectionModel();
        SingleSelectionModel<String> selTerm = fxtermcmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selSubject, Strings.invalidSubject, fxwarninglbl)) {
                if(ValidateSM.isCombo(selYear, Strings.invalidYear, fxwarninglbl)) {
                    if(ValidateSM.isCombo(selTerm, Strings.invalidTerm, fxwarninglbl)) {
                        String grade = selGrade.getSelectedItem();
                        String subject = selSubject.getSelectedItem();
                        String year = selYear.getSelectedItem();
                        String term = selTerm.getSelectedItem();

                        oblist = ResultDao.getResults(grade,subject,year,term);

                        fxresulttable.setItems(oblist);

                        // animate table
                        animateNode(fxresulttable, 1);
                    }
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
        // set values for the exam table
        fxresultidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.examID));
        fxstudentidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.studentID));
        fxstudentnametbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.name));
        fxresulttbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.result));
        
        // sets placeholder text of the result table
        if(ResultTableUI.getContext() == null)
            setLabelInTable(fxresulttable, Strings.tablePlaceholderDefault);
        else {
            switch (ResultTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxresulttable, Strings.resultAdded);
                    break;
                case Strings.context_update:
                    setLabelInTable(fxresulttable, Strings.resultChanged);
                    break;
            }
        }
        
        // sets subjects
        fxsubjectcmbbx.setItems(SubjectDao.getSubjects());
        
        // sets year
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.year_2018,Strings.year_2019,Strings.year_2020);
        fxyearcmbbx.setItems(oblistA);
        
        // sets term
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.term_1,Strings.term_2,Strings.term_3);
        fxtermcmbbx.setItems(oblistC);
        
        // sets grade
        ObservableList<String> oblistD = FXCollections.observableArrayList();
        oblistD.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistD);
        
        // clears warning
        fxwarninglbl.setText("");
        
    }    
    
}
