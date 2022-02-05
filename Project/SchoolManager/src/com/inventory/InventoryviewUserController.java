package com.inventory;

import com.connection.PictureSM;
import com.login.Login;
import com.string.Strings;
import com.user.NonAcademicStaff;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class InventoryviewUserController implements Initializable {
    private NonAcademicStaff currUser;
    
    @FXML
    public Label fxnamelbl,fxidlbl,fxusernamelbl,fxpasswordlbl,fxemaillbl,fxphonelbl,fxaddresslbl,fxbiolbl,fxdesignationlbl,fxqualificationlbl;
    public Button fxeditprofilebtn,fxlogoutbtn;
    public ImageView fxprofilepictureimgview;
    
    @FXML
    public void loadEditProfile(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_editProfile);
        InventoryUI.getStage().hide();
        
        InventoryUI inventoryuiinstance = InventoryUI.getInstance();
        Stage edituserstage = new Stage();
        inventoryuiinstance.displayEditUser(edituserstage);
    }
    
    @FXML 
    public void loadLogin(ActionEvent event) throws Exception {
        System.out.println(Strings.clicked_logOut);
        InventoryUI.getStage().close();
        
        Login newLogin = new Login();
        newLogin.start(new Stage());
    }
    
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
        
        try {
            fxprofilepictureimgview.setImage(PictureSM.getProfilePicture(fxprofilepictureimgview, currUser));
        } 
        catch (SQLException | IOException ex) {
            System.out.println(ex);
        }
        
    }    
    
}
