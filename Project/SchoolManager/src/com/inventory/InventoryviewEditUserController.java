package com.inventory;

import animatefx.animation.FadeIn;
import com.connection.PictureSM;
import com.connection.ValidateSM;
import com.string.Strings;
import com.user.NonAcademicStaff;
import com.user.User;
import com.user.UserDao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

public class InventoryviewEditUserController implements Initializable {
    // current user 
    NonAcademicStaff currUser = NonAcademicStaff.getInstance();
    
    @FXML 
    public Label fxuseridlbl,fxwarninglbl;
    public Button fxchangepicturebtn,fxchangepasswordbtn,fxokbtn,fxcancelbtn;
    public TextField fxnametxt,fxusernametxt,fxpasswordtxt,fxchangephonetxt,fxchangeaddresstxt,fxchangebiotxt;
    public ImageView fxprofilepictureimgview;
    
    // File Chooser for picture
    private FileChooser fc;
    
    // verify user details and changes them
    public void changeUser(ActionEvent event) throws FileNotFoundException {
        System.out.println(Strings.clicked_oK);
        
        String uid = fxuseridlbl.getText();
        String name = fxnametxt.getText();
        String username = fxusernametxt.getText();
        String password = fxpasswordtxt.getText();
        String phone = fxchangephonetxt.getText();
        String address = fxchangeaddresstxt.getText();
        String bio = fxchangebiotxt.getText();
        
        if(ValidateSM.validateNumber(phone, Strings.invalidPhone, fxwarninglbl)) {
            User user = NonAcademicStaff.getSharedUser();

            user.setUserID(uid);
            user.setName(name);
            user.setUserName(username);
            user.setPassword(password);
            user.setPhone(phone);
            user.setAddress(address);
            user.setBio(bio);
            PictureSM.clearProfilePicture();
            
            UserDao.updateProfile(user);
            fxwarninglbl.setText(Strings.updateUserSuccessful);
            fxwarninglbl.setStyle(Strings.css_setTextColorPrimary);
            new FadeIn(fxwarninglbl).play();
        }
    }
    
    // change picture
    public void changePicture(ActionEvent event) throws IOException {
        fc = new FileChooser();
        fc.setInitialDirectory(new File(Strings.editUserInitialDirecory));
        fc.getExtensionFilters().addAll(new ExtensionFilter("PNG Files","*.png"));
        fc.setTitle(Strings.titles_choosePicture);
        File file = fc.showOpenDialog(null);
        
        if(file != null) {
            NonAcademicStaff.setFile(file);
            
            System.out.println(file.getAbsolutePath());
            System.out.println(file.getCanonicalPath());
            
            BufferedImage bufferedImage = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            fxprofilepictureimgview.setImage(image);
        }
        else
            System.out.println(Strings.invalidFileSelected);
    }
    
    // closes edit user
    @FXML
    public void closeEditUser(ActionEvent event) {
        System.out.println(Strings.clicked_cancel);
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
        
        InventoryUI.getStage().show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //sets warning to null
        fxwarninglbl.setText("");
        
        // set userID
        fxuseridlbl.setText(currUser.getUserID());
        
        // set Name
        fxnametxt.setText(currUser.getName());
        
        // set username
        fxusernametxt.setText(currUser.getUserName());
        
        // set password
        fxpasswordtxt.setText(currUser.getPassword());
        
        // set phone
        fxchangephonetxt.setText(currUser.getPhone());
        
        // set address
        fxchangeaddresstxt.setText(currUser.getAddress());
        
        // set bio
        fxchangebiotxt.setText(currUser.getBio());
        
        // save picture
        try {
            UserDao.saveProfilePicture(currUser.getUserID());
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
        
        // load current picture
        try {
            fxprofilepictureimgview.setImage(PictureSM.getProfilePicture(fxprofilepictureimgview, currUser));
        } 
        catch (SQLException | IOException ex) {
            System.out.println(ex);
        }
        
    }
    
}
