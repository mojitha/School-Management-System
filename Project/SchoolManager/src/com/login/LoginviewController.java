package com.login;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.attendance.AttendanceUI;
import com.exam.ExamUI;
import com.inventory.InventoryUI;
import com.library.LibraryUI;
import com.main.Main;
import com.result.ResultUI;
import com.staff.StaffUI;
import com.string.Strings;
import com.student.StudentUI;
import com.subject.SubjectUI;
import com.user.User;
import com.user.UserDao;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginviewController implements Initializable {
    
    @FXML
    private Button fxcancelbtn;
    public TextField fxusernametxt;
    public PasswordField fxpasswordtxt;
    public Label fxstatuslabel,fxloginlabel;
    
    public TextField getFxusernametxt() {
        return fxusernametxt;
    }

    public TextField getFxpasswordtxt() {
        return fxpasswordtxt;
    }
    
    @FXML
    public void checkLogin(ActionEvent event) {
        String username = getFxusernametxt().getText();
        String password = getFxpasswordtxt().getText();
        User userLoginInstance = new User(username, password);
        
        System.out.println("Login OK button clicked "+username+" "+password);
        
        if(username.equalsIgnoreCase("admin") && password.equals("0000")) {
            System.out.println("Access Granted - Admin");
            try {
                Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                stage.close();
                
                Main maininstance = Main.getInstance();
                Stage mainstage = new Stage();
                maininstance.display(mainstage);
            }
            catch(Exception e) {
                System.out.println(e);    
            }
        }
        else {
            if(UserDao.authenticateUser(userLoginInstance) == 'w') {
                System.out.println(Strings.accessGranted);
                //inventory
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Inventory_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();
                        
                        InventoryUI inventoryinstance = InventoryUI.getInstance();
                        Stage inventorystage = new Stage();
                        inventoryinstance.display(inventorystage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //attendance
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Attendance_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        AttendanceUI attendanceinstance = AttendanceUI.getInstance();
                        Stage attendancestage = new Stage();
                        attendanceinstance.display(attendancestage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //exam
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Exam_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        ExamUI examinstance = ExamUI.getInstance();
                        Stage examstage = new Stage();
                        examinstance.display(examstage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //library
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Library_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        LibraryUI libraryinstance = LibraryUI.getInstance();
                        Stage librarystage = new Stage();
                        libraryinstance.display(librarystage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //result
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Result_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        ResultUI resultinstance = ResultUI.getInstance();
                        Stage resultstage = new Stage();
                        resultinstance.display(resultstage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //staff
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Staff_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        StaffUI staffinstance = StaffUI.getInstance();
                        Stage staffstage = new Stage();
                        staffinstance.display(staffstage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //student
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Student_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        StudentUI studentinstance = StudentUI.getInstance();
                        Stage studentstage = new Stage();
                        studentinstance.display(studentstage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
                //subject
                if(UserDao.getDesignation(userLoginInstance).equals(Strings.Subject_Manager)) {
                    try {
                        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
                        stage.close();

                        SubjectUI subjectinstance = SubjectUI.getInstance();
                        Stage subjectstage = new Stage();
                        subjectinstance.display(subjectstage);
                    }
                    catch(Exception e) {
                        System.out.println(e);    
                    }
                }
            }
            else if(UserDao.authenticateUser(userLoginInstance) == 'u') {
                System.out.println(Strings.uNameWrong);
                fxstatuslabel.setText(Strings.uNameWrong);
                animateNode(fxstatuslabel, 1);
            }
            else if(UserDao.authenticateUser(userLoginInstance) == 'p') {
                System.out.println(Strings.pwdWrong);
                fxstatuslabel.setText(Strings.pwdWrong);
                animateNode(fxstatuslabel, 1);
            }
            else {
                System.out.println(Strings.accessDenied);
                fxstatuslabel.setText(Strings.accessDenied);
                animateNode(fxstatuslabel, 1);
            }
        }
        
    }
    
    @FXML
    public void closeLogin(ActionEvent event) {
        System.out.println("Login cancel button clicked");
        System.exit(1);
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set login status to null
        fxstatuslabel.setText("");
    }    
    
}
