package com.exam;

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

public class ExamviewController implements Initializable {
    // nonacademoic
    private NonAcademicStaff examManager;
    
    @FXML
    public BorderPane fxexamborderpane;
    public Button fxhomebtn,fxexambtn,fxstatisticsbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
    public ImageView fxstatusimgview;
    public Label fxdisplaytimelbl,fxdisplayuserlbl;

    @FXML
    public void loadHome(ActionEvent event) throws IOException {
        System.out.println("Clicked HOME");
        fxexamborderpane.setCenter(null);
        loadUI("ExamviewHome");
    }
    
    @FXML
    public void loadExam(ActionEvent event) throws IOException {
        System.out.println("Clicked exam");
        ExamTableUI examtableinstance = ExamTableUI.getInstance();
        Stage examtablestage = new Stage();
        examtableinstance.display(examtablestage);

        ExamUI.getStage().hide();
    }

    @FXML
    public void loadNotifications(ActionEvent event) {

    }

    @FXML
    public void loadSettings(ActionEvent event) {
        
    }

    @FXML
    public void loadStatistics(ActionEvent event) throws IOException {
        System.out.println("Clicked statictics");
        loadUI("ExamResults");
    }

    @FXML
    public void loadUser(ActionEvent event) {

    }
    
    public void loadUI(String ui) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        fxexamborderpane.setCenter(root);
        
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
            loadUI("ExamviewHome");
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
        
        // set User
        examManager = NonAcademicStaff.getInstance();
        
        // display User
        fxdisplayuserlbl.setText(examManager.getName());
    }    
    
}
