package com.student;

import com.user.NonAcademicStaff;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class StudentUserController implements Initializable {
    private NonAcademicStaff currUser;
    
    @FXML
    public Label fxnamelbl,fxidlbl,fxusernamelbl,fxpasswordlbl,fxemaillbl,fxphonelbl,fxaddresslbl,fxbiolbl,fxdesignationlbl,fxqualificationlbl;
    public Button fxeditprofilebtn,fxlogoutbtn;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // get CurrentUser and assign
        currUser = NonAcademicStaff.getInstance();
        
        // set Details
        fxnamelbl.setText(currUser.getName());
        fxidlbl.setText(currUser.getUserID());
        fxusernamelbl.setText(currUser.getUserName());
        fxpasswordlbl.setText(currUser.getPassword());
        fxemaillbl.setText(currUser.getEmail());
        fxphonelbl.setText(currUser.getPhone());
        fxaddresslbl.setText(currUser.getAddress());
        fxbiolbl.setText(currUser.getBio());
        fxdesignationlbl.setText(currUser.getDesignation());
        fxqualificationlbl.setText(currUser.getQualification());
    }    
    
}
