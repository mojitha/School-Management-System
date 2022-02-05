package com.subject;

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

public class SubjectviewController implements Initializable {
    // nonacademoic
    private NonAcademicStaff subjectManager;
    
    @FXML
    public BorderPane fxsubjectsborderpane;
    public Button fxhomebtn,fxsubjectsbtn,fxstatisticsbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
    public ImageView fxstatusimgview;
    public Label fxdisplaytimelbl,fxdisplayuserlbl;
    
    @FXML
    public void loadHome(ActionEvent event) throws IOException {
        System.out.println("Clicked HOME");
        fxsubjectsborderpane.setCenter(null);
        loadUI("SubjectviewHome");
    }
    
    @FXML
    public void loadSubjects(ActionEvent event) throws IOException {
        System.out.println("Clicked Subjects");
        SubjectTableUI subjecttableinstance = SubjectTableUI.getInstance();
        Stage subjecttablestage = new Stage();
        subjecttableinstance.display(subjecttablestage);

        SubjectUI.getStage().hide();
    }
    
    @FXML
    public void loadStatistics(ActionEvent event) {
        
    }
    
    @FXML
    public void loadSettings(ActionEvent event) {
        
    }
    
    @FXML
    public void loadNotifications(ActionEvent event) {
        
    }
    
    @FXML
    public void loadUser(ActionEvent event) {
        
    }
    
    public void loadUI(String uiname) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(uiname+".fxml"));
        fxsubjectsborderpane.setCenter(root);
        
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
            loadUI("SubjectviewHome");
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
        
        // set User
        subjectManager = NonAcademicStaff.getInstance();
        
        // display User
        fxdisplayuserlbl.setText(subjectManager.getName());
    }    
    
}
