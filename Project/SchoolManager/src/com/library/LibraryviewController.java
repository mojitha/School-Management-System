package com.library;

import com.user.NonAcademicStaff;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

public class LibraryviewController implements Initializable {
    // nonacademoic
    private NonAcademicStaff libraryManager;
    
    @FXML
    public BorderPane fxlibraryborderpane;
    public Button fxhomebtn,fxlibrarybtn,fxstatisticsbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
    public ImageView fxstatusimgview;
    public Label fxdisplaytimelbl,fxdisplayuserlbl;
    
    @FXML
    public void loadHome(ActionEvent event) {
        System.out.println("Clicked HOME");
    }
    
    @FXML
    public void loadLibrary(ActionEvent event) {

    }

    @FXML
    public void loadNotifications(ActionEvent event) {

    }

    @FXML
    public void loadSettings(ActionEvent event) {

    }

    @FXML
    public void loadStatistics(ActionEvent event) {

    }

    @FXML
    public void loadUser(ActionEvent event) {

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
        
        // set User
        libraryManager = NonAcademicStaff.getInstance();
        
        // display User
        fxdisplayuserlbl.setText(libraryManager.getName());
    }    
    
}
