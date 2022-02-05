package com.main;

import com.login.Login;
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
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainviewController implements Initializable,Runnable {
    static Login newLogin = new Login();
    
    @FXML
    public Label fxtimelabel;
    
    @FXML 
    public void openFunctionView(ActionEvent event) throws Exception {
        Main.getStage().hide();
        newLogin.start(new Stage());
    }
    
    @Override
    public void run() {
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
                
                fxtimelabel.setText(hour + ":" + minute + ampm);
            }), new KeyFrame(Duration.seconds(1)));
            
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Thread timethread = new Thread(this);
        timethread.start();
    }
}
