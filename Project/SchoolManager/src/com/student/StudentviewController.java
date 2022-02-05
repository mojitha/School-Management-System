package com.student;

import animatefx.animation.FadeIn;
import com.user.NonAcademicStaff;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class StudentviewController implements Initializable {
    // nonacademoic
    private NonAcademicStaff studentManager;
    
    @FXML
    public BorderPane fxstudentborderpane;
    public Button fxhomebtn,fxstudentbtn,fxreportbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
    public ImageView fxstatusimgview;
    public Label fxdisplaytimelbl,fxdisplayuserlbl;

    @FXML
    public void loadHome(ActionEvent event) throws IOException {
        System.out.println("Clicked HOME");
        fxstudentborderpane.setCenter(null);
        loadUI("StudentviewHome");
    }
    
    @FXML
    public void loadStudent(ActionEvent event) throws IOException {
        System.out.println("Clicked Student");
        StudentTableUI studenttableinstance = StudentTableUI.getInstance();
        Stage studenttablestage = new Stage();
        studenttableinstance.display(studenttablestage);

        StudentUI.getStage().hide();
    }

    @FXML
    public void loadNotifications(ActionEvent event) {
        System.out.println("Clicked Notifications");
        fxstudentborderpane.setCenter(null);
    }

    @FXML
    public void loadSettings(ActionEvent event) {
        System.out.println("Clicked Settings");
        fxstudentborderpane.setCenter(null);
    }

    @FXML
    public void loadReport(ActionEvent event) throws IOException {
        System.out.println("Clicked Report");
        fxstudentborderpane.setCenter(null);
        loadUI("TestReport");
    }

    @FXML
    public void loadUser(ActionEvent event) throws IOException {
        System.out.println("Clicked User");
        fxstudentborderpane.setCenter(null);
        loadUI("StudentUser");
    }
    
    public void loadUI(String uiname) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(uiname+".fxml"));
        fxstudentborderpane.setCenter(root);
        
        //animate the stage
        new FadeIn(root).play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set time
        try {
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e-> {
                int hour;
                String minute,ampm;
                
                DateTimeFormatter minutestamp = DateTimeFormatter.ofPattern("mm");
                hour = LocalDateTime.now().getHour();
                minute = LocalDateTime.now().format(minutestamp);
               
                if(hour > 12) {
                    hour -= 12; 
                    ampm = " PM";
                }
                else if(hour == 12)
                    ampm = " PM";
                else
                    ampm = " AM";
                
                fxdisplaytimelbl.setText(hour + ":" + minute + ampm);
            }), new KeyFrame(Duration.seconds(1)));
            
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        // loads home
        try {
            loadUI("StudentviewHome");
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
        
        // set User
        studentManager = NonAcademicStaff.getInstance();
        
        // display User
        fxdisplayuserlbl.setText(studentManager.getName());
    }    
    
}
