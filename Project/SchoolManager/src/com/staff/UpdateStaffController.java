package com.staff;

import com.connection.ValidateSM;
import com.string.Strings;
import com.user.User;
import com.user.UserDao;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UpdateStaffController implements Initializable {
    @FXML
    public Label fxwarninglbl,fxstaffIdlbl;
    public TextField fxnametxt,fxaddresstxt,fxphonetxt,fxemailtxt,fxdesignationtxt;
    public DatePicker fxbirthdatedtpickr;
    public Button fxOKbtn,fxcloseupdatestaffbtn;

    // user to be changed
    User currUser = UpdateStaffUI.getOldUser();
    
    @FXML
    public void closeUpdateStaff(ActionEvent event) {
        Stage stage = (Stage) fxcloseupdatestaffbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void okUpdateStaff(ActionEvent event) throws ParseException, IOException {
        String staffID = fxstaffIdlbl.getText();
        String name = fxnametxt.getText().trim();
        DatePicker date = fxbirthdatedtpickr;
        String address = fxaddresstxt.getText().trim();
        String phone = fxphonetxt.getText().trim();
        String email = fxemailtxt.getText().trim();
        String designation = fxdesignationtxt.getText().trim();
        
        // validates if all item details are given by staff manager
        if(ValidateSM.validateText(staffID, 10, Strings.invalidStaffID, fxwarninglbl)) {
            if(ValidateSM.validateText(name, 30, Strings.invalidName, fxwarninglbl)) {
                if(ValidateSM.checkDate(date, Strings.invalidBirthDate, fxwarninglbl)) {
                    if(ValidateSM.validateText(address, 50, Strings.invalidAddress, fxwarninglbl)) {
                        if(ValidateSM.validateNumber(phone, Strings.invalidPhone, fxwarninglbl) && ValidateSM.validateTextAbsolute(phone, 10, Strings.invalidPhone , fxwarninglbl)) {
                            if(ValidateSM.validateText(email, 50, Strings.invalidEmail, fxwarninglbl) && ValidateSM.isValidEmail(email, Strings.invalidEmail, fxwarninglbl)) {
                                if(ValidateSM.validateText(designation, 30, Strings.invalidDesignation, fxwarninglbl) && ValidateSM.validateDesignation(designation, Strings.notUsableDesignation, fxwarninglbl)) {
                                    System.out.println("OKAY");
                                    User staffToUp = new User();
                                    
                                    staffToUp.setUserID(staffID);
                                    staffToUp.setName(name);
                                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                        String birthDate = dt.format(dt.parse(date.getValue().toString()));
                                    staffToUp.setBirthDate(birthDate);
                                    staffToUp.setAddress(address);
                                    staffToUp.setPhone(phone);
                                    staffToUp.setEmail(email);
                                    
                                    if(UserDao.updateUser(staffToUp) && UserDao.updateDesignation(staffID, designation)) {
                                        System.out.println(Strings.staffUpdated);
                                        Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                        stage.close();

                                        StaffTableUI.hideStage();
                                        System.out.println(Strings.staffUpdated);
                                        UpdateStaffUI.setUpdatedFlag();
                                        StaffTableUI.getInstance().display(StaffTableUI.getStage());
                                    }
                                    else {
                                        System.out.println(Strings.itemNotChanged);
                                        Stage stage = (Stage) fxOKbtn.getScene().getWindow();
                                        stage.close();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
         
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets staffID
        fxstaffIdlbl.setText(currUser.getUserID());
        
        // sets name
        fxnametxt.setText(currUser.getName());
        
        // sets birthDate
        LocalDate ld = LocalDate.parse(currUser.getBirthDate());
        fxbirthdatedtpickr.setValue(ld);
        
        // set address
        fxaddresstxt.setText(currUser.getAddress());
        
        // set phone
        fxphonetxt.setText(currUser.getPhone());
        
        // set email
        fxemailtxt.setText(currUser.getEmail());
        
        // set designation
        fxdesignationtxt.setText(UserDao.getDesignation(currUser));
        
        // clears warning
        fxwarninglbl.setText("");
    }    
    
}
