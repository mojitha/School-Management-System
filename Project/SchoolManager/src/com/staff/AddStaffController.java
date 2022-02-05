package com.staff;

import com.connection.IdentitySM;
import com.connection.ValidateSM;
import com.string.Strings;
import com.user.AcademicStaff;
import com.user.AcademicStaffDao;
import com.user.NonAcademicStaff;
import com.user.NonAcademicStaffDao;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class AddStaffController implements Initializable {
    @FXML
    public Label fxstaffIdlbl,fxwarninglbl;
    public Button fxcloseaddstaffbtn,fxOKbtn;
    public TextField fxnametxt,fxaddresstxt,fxphonetxt,fxemailtxt,fxdesignationtxt;
    public DatePicker fxbirthdatedtpickr;
    public RadioButton fxacademicradiobtn,fxnonacademicradiobtn;
    public ToggleGroup stafftype;
    
    @FXML
    public void closeAddStaff(ActionEvent event) {
        System.out.println(Strings.clicked_close);
        Stage thisstage = (Stage) fxcloseaddstaffbtn.getScene().getWindow();
        thisstage.close();
    }
    
    @FXML
    public void okAddStaff(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_oK);
        
        String staffID = fxstaffIdlbl.getText().trim();
        String name = fxnametxt.getText().trim();
        DatePicker date = fxbirthdatedtpickr;
        String address = fxaddresstxt.getText().trim();
        String phone = fxphonetxt.getText().trim();
        String email = fxemailtxt.getText().trim();
        String designation = fxdesignationtxt.getText().trim();
        
        if(ValidateSM.validateText(staffID, 10, Strings.invalidStaffID, fxwarninglbl)) {
            if(ValidateSM.validateText(name, 30, Strings.invalidName, fxwarninglbl)) {
                if(ValidateSM.checkDate(date, Strings.invalidBirthDate, fxwarninglbl)) {
                    if(ValidateSM.validateText(address, 50, Strings.invalidAddress, fxwarninglbl)) {
                        if(ValidateSM.validateNumber(phone, Strings.invalidPhone, fxwarninglbl) && ValidateSM.validateTextAbsolute(phone, 10, Strings.invalidPhone , fxwarninglbl)) {
                            if(ValidateSM.validateText(email, 50, Strings.invalidEmail, fxwarninglbl) && ValidateSM.isValidEmail(email, Strings.invalidEmail, fxwarninglbl)) {
                                if(ValidateSM.validateText(designation, 30, Strings.invalidDesignation, fxwarninglbl)) {
                                    RadioButton type = (RadioButton) stafftype.getSelectedToggle();
                                    String staffType = type.getText();

                                    SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                    String birthdate = dt.format(dt.parse(date.getValue().toString()));

                                    switch (staffType) {
                                        case Strings.NonAcademicStaff:
                                            NonAcademicStaff currNonac = NonAcademicStaff.getSharedUser();
                                                currNonac.setUserID(staffID);
                                                currNonac.setName(name);
                                                currNonac.setUserName(null);
                                                currNonac.setPassword(null);
                                                currNonac.setBirthDate(birthdate);
                                                currNonac.setRegisteredDate(getLocalDate().toString());
                                                currNonac.setEmail(email);
                                                currNonac.setPhone(phone);
                                                currNonac.setAddress(address);
                                                currNonac.setBio(null);
                                                currNonac.setImg(null);
                                                currNonac.setDesignation(designation);
                                                currNonac.setQualification(designation);

                                                if(NonAcademicStaffDao.addNonAcademicStaff(currNonac)) {
                                                    Stage stage = AddStaffUI.getStage();
                                                    stage.close();

                                                    StaffTableUI.hideStage();
                                                    StaffTableUI.setContext(Strings.context_insert);
                                                    StaffTableUI.getInstance().display(StaffTableUI.getStage());
                                                }
                                                else {
                                                    Stage stage = StaffTableUI.getStage();
                                                    stage.close();
                                                }
                                        break;
                                        case Strings.AcademicStaff:
                                            AcademicStaff currAc = AcademicStaff.getSharedUser();
                                                currAc.setUserID(staffID);
                                                currAc.setName(name);
                                                currAc.setUserName(null);
                                                currAc.setPassword(null);
                                                currAc.setBirthDate(birthdate);
                                                currAc.setRegisteredDate(getLocalDate().toString());
                                                currAc.setEmail(email);
                                                currAc.setPhone(phone);
                                                currAc.setAddress(address);
                                                currAc.setBio(null);
                                                currAc.setImg(null);
                                                currAc.setDesignation(designation);
                                                currAc.setQualification(designation);

                                                if(AcademicStaffDao.addNonAcademicStaff(currAc)) {
                                                    Stage stage = AddStaffUI.getStage();
                                                    stage.close();

                                                    StaffTableUI.hideStage();
                                                    StaffTableUI.setContext(Strings.context_insert);
                                                    StaffTableUI.getInstance().display(StaffTableUI.getStage());
                                                }
                                                else {
                                                    Stage stage = StaffTableUI.getStage();
                                                    stage.close();
                                                }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // sets staffID label
        fxstaffIdlbl.setText(IdentitySM.getID(Strings.Staff));
        
        // sets the staff type
        stafftype.selectToggle(fxacademicradiobtn);
        
        // sets warning label as null
        fxwarninglbl.setText("");
    }    
    
}
