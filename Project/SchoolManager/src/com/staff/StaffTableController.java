package com.staff;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.connection.ValidateSM;
import com.string.Strings;
import com.subject.SubjectDao;
import com.user.AcademicStaffDao;
import com.user.NonAcademicStaff;
import com.user.NonAcademicStaffDao;
import com.user.User;
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

public class StaffTableController implements Initializable {
    @FXML
    public Button fxaddstaffbtn,fxgobtn;
    public Label fxwarninglbl;
    public ComboBox fxsubjectcmbbx,fxgradecmbbx,fxstafftypecmbbx;
    public TableView fxstafftable;
    public TextField fxsearchstafftxt;
    public TableColumn<?, String> fxstaffidtbcl;
    public TableColumn<?, String> fxnametbcl;
    public TableColumn<?, String> fxbirthdatetbcl;
    public TableColumn<?, String> fxentrydatetbcl;
    public TableColumn<?, String> fxaddresstbcl;
    public TableColumn<?, String> fxphonetbcl;
    public TableColumn<?, String> fxemailtbcl;
    public TableColumn<?, String> fxdesignationtbcl;
    public TableColumn<?, String> fxstatustbcl;
            
    ObservableList<?> staffList = FXCollections.observableArrayList();
    
    @FXML
    public void loadAddStaff(ActionEvent event) throws IOException {
        AddStaffUI addstaffuiinstance = AddStaffUI.getInstance();
        Stage addstaffuistage = new Stage();
        addstaffuiinstance.display(addstaffuistage);
    }
    
    @FXML
    public void loadStaff(ActionEvent event) {
        SingleSelectionModel<String> selGrade = fxgradecmbbx.getSelectionModel();
        SingleSelectionModel<String> selSubject = fxsubjectcmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selGrade, Strings.invalidExamGrade, fxwarninglbl)) {
            if(ValidateSM.isCombo(selSubject, Strings.invalidSubject, fxwarninglbl)) {
                String grade = selGrade.getSelectedItem();
                String subject = selSubject.getSelectedItem();
                
                ObservableList list = FXCollections.observableArrayList();
                list = AcademicStaffDao.getStaff(grade,subject);

                fxstafftable.setItems(list);
                
                // animate table
                animateNode(fxstafftable, 1);
            }
        }
    }
    
    @FXML
    public void checkStaffType(ActionEvent event) {
        SingleSelectionModel<String> selStaffType = fxstafftypecmbbx.getSelectionModel();
        
        if(ValidateSM.isCombo(selStaffType, Strings.invalidStaffType, fxwarninglbl)) {
            String staffType = selStaffType.getSelectedItem();
            
            switch (staffType) {
                case Strings.NonAcademic:
                    fxgradecmbbx.setVisible(false);
                    fxsubjectcmbbx.setVisible(false);
                    fxgobtn.setDisable(true);
                        ObservableList<NonAcademicStaff> oblistA = FXCollections.observableArrayList();
                        oblistA = NonAcademicStaffDao.loadNonAcadamic();
                        fxstafftable.setItems(oblistA);
                        new FadeIn(fxstafftable).play();
                    break;
                case Strings.Academic:
                    setLabelInTable(fxstafftable,Strings.tablePlaceholderDefault);
                    fxstafftable.getItems().clear();
                    new FadeIn(fxstafftable).play();
                    fxgradecmbbx.setVisible(true);
                    fxsubjectcmbbx.setVisible(true);
                    fxgobtn.setDisable(false);
                    break;
            }
        }
    }
    
    // load update staff
    @FXML
    public void updateStaff(ActionEvent event) throws IOException {
        TableView.TableViewSelectionModel<User> selRow = fxstafftable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            fxwarninglbl.setText(Strings.clear);
            animateNode(fxwarninglbl,0);
            
            // saves the selected item first
            User tableMember = selRow.getSelectedItem();
            String userID = tableMember.getUserID();
            StaffDao.setCurrentStaffMember(userID);
            
            // set a backup of the current member
            UpdateStaffUI.setOldUser(tableMember);
            
            // loads update Staff UI
            UpdateStaffUI updatestaffuiinstance = UpdateStaffUI.getInstance();
            Stage updatestaffuistage = new Stage();
            updatestaffuiinstance.display(updatestaffuistage);
        }
        else {
            fxwarninglbl.setText(Strings.notSelectedStaffMember);
            animateNode(fxwarninglbl,1);
        }
    }
    
    // search staff
    @FXML
    public void searchStaff(ActionEvent event) {
        // clears table
        fxstafftable.getItems().clear();
        
        // gets the keyword
        String keyword = fxsearchstafftxt.getText().trim();
        
        // gets selected type
        SingleSelectionModel selStaffType = fxstafftypecmbbx.getSelectionModel();
        
        // searches for the staff member
        if(ValidateSM.isCombo(selStaffType, Strings.invalidStaffType, fxwarninglbl)) {
            if(!fxsearchstafftxt.getText().isEmpty()) {
                fxstafftable.setPlaceholder(new Label(Strings.tablePlaceholderDefault));
                fxwarninglbl.setText(Strings.clear);
                animateNode(fxwarninglbl, 0);
                
                String staffType = selStaffType.getSelectedItem().toString();
                ObservableList<?> acList = FXCollections.observableArrayList();
                ObservableList<?> nonAcList = FXCollections.observableArrayList();

                switch (staffType) {
                    case Strings.Academic:
                        acList = AcademicStaffDao.getAcademicList(keyword); // customized academic
                            if(!acList.isEmpty()) {
                                fxstafftable.setItems(acList);
                                animateNode(fxstafftable,1);
                            }
                            else
                                fxstafftable.setPlaceholder(new Label(Strings.tablePlaceholderNoStaffFound));
                        break;
                    case Strings.NonAcademic:
                        nonAcList = NonAcademicStaffDao.getNonAcademicList(keyword); // customized nonacademic
                            if(!nonAcList.isEmpty()) {
                                fxstafftable.setItems(nonAcList);
                                animateNode(fxstafftable,1);
                            }
                            else
                                fxstafftable.setPlaceholder(new Label(Strings.tablePlaceholderNoStaffFound));
                        break;
                }
                
                acList = null;
                nonAcList = null;
            }
            else {
                fxstafftable.setPlaceholder(new Label(Strings.tablePlaceholderNoStaffFound));
                fxwarninglbl.setText(Strings.notEnteredStaffSearch);
                animateNode(fxwarninglbl, 1);
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
        // set table
        fxstaffidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.userId));
        fxnametbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.name));
        fxbirthdatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.birthDate));
        fxentrydatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.registeredDate));
        fxaddresstbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.address));
        fxphonetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.phone));
        fxemailtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.email));
        fxdesignationtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.designation));
        fxstatustbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.status));
        
        // sets placeholder text of the staff table
        if(StaffTableUI.getContext() == null)
            setLabelInTable(fxstafftable, Strings.tablePlaceholderDefault);
        else {
            switch (StaffTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxstafftable, Strings.staffAdded);
                    break;
                case Strings.context_update:
                    setLabelInTable(fxstafftable, Strings.staffChanged);
                    break;
            }
        }
        
        // set grades
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.grade_6,Strings.grade_7,Strings.grade_8,Strings.grade_9,Strings.grade_10,Strings.grade_11,Strings.grade_12,Strings.grade_13);
        fxgradecmbbx.setItems(oblistA);
        
        // set subjects
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(SubjectDao.getSubjects());
        fxsubjectcmbbx.setItems(oblistB);
        
        // set type
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(Strings.Academic,Strings.NonAcademic);
        fxstafftypecmbbx.setItems(oblistC);
        
        // check if updated
        if(UpdateStaffUI.getUpdatedFlag() == 1) {
            setLabelInTable(fxstafftable, Strings.tablePlaceholderStaffUpdatedSuccessfully);
            UpdateStaffUI.setUpdatedFlag();
        }
        else
            setLabelInTable(fxstafftable, Strings.tablePlaceholderDefault);
        
        // clear warning
        fxwarninglbl.setText("");
                
    }    
    
}
