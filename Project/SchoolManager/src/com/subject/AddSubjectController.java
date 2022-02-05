package com.subject;

import com.connection.IdentitySM;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddSubjectController implements Initializable {
    @FXML
    public Label fxsubjectIdlbl,fxwarninglbl;
    public TextField fxsubjectnametxt;
    public ComboBox fxsubjectgradecmbbx;
    public Button fxcancelbtn,fxokbtn;
    
    @FXML
    public void closeAddSubject(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void okAddSubject(ActionEvent event) throws IOException {
        String subjectID = fxsubjectIdlbl.getText().trim();
        String subjectName = fxsubjectnametxt.getText().trim();
        SingleSelectionModel<String> subjectGrade = fxsubjectgradecmbbx.getSelectionModel();
        
        if(ValidateSM.validateText(subjectID, 10, Strings.invalidSubjectID, fxwarninglbl)) {
            if(ValidateSM.validateText(subjectName, 50, Strings.invalidSubjectName, fxwarninglbl)) {
                if(ValidateSM.isCombo(subjectGrade, Strings.invalidSubjectGrade, fxwarninglbl)) {
                    String grade = subjectGrade.getSelectedItem();
                    
                    Subject currSubject = Subject.getInstance();
                    
                    currSubject.setSubjectID(subjectID);
                    currSubject.setSubject(subjectName);
                    currSubject.setGrade(grade);
                    
                    if(SubjectDao.addSubject(currSubject)) {
                        Stage stage = AddSubjectUI.getStage();
                        stage.close();

                        SubjectTableUI.hideStage();
                        SubjectTableUI.setContext(Strings.context_insert);
                        SubjectTableUI.getInstance().display(SubjectTableUI.getStage());
                    }
                    else {
                        Stage stage = AddSubjectUI.getStage();
                        stage.close();
                    }
                        
                }
            }
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set subject id
        fxsubjectIdlbl.setText(IdentitySM.getID(Strings.Subject));
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxsubjectgradecmbbx.setItems(oblistA);
        
        // set warning as null
        fxwarninglbl.setText("");
    }    
    
}
