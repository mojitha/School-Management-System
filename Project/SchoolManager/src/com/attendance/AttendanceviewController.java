package com.attendance;

import animatefx.animation.FadeIn;
import com.string.Strings;
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

public class AttendanceviewController implements Initializable {
    // nonacademoic
    private NonAcademicStaff attendanceManager;
    
    @FXML
    public BorderPane fxattendanceborderpane;
    public Button fxhomebtn,fxattendancebtn,fxstatisticsbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
    public ImageView fxstatusimgview;
    public Label fxdisplaytimelbl,fxdisplayuserlbl;
    
    @FXML
    public void loadHome(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_home);
        fxattendanceborderpane.setCenter(null);
        loadUI("AttendanceHome");
    }
    
    @FXML
    public void loadAttendance(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_attendance);
        AttendanceTableUI attendancetableuiinstance = AttendanceTableUI.getInstance();
        Stage attendancetableuistage = new Stage();
        attendancetableuiinstance.display(attendancetableuistage);

        AttendanceUI.getStage().hide();
    }

    @FXML
    public void loadNotifications(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_notifications);
        fxattendanceborderpane.setCenter(null);
        loadUI("AttendanceNotifications");
    }

    @FXML
    public void loadSettings(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_settings);
        fxattendanceborderpane.setCenter(null);
        loadUI("AttendanceSettings");
    }

    @FXML
    public void loadStatictics(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_statistics);
        fxattendanceborderpane.setCenter(null);
        loadUI("AttendancePublicReport");
    }

    @FXML
    public void loadUser(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_user);
        fxattendanceborderpane.setCenter(null);
        loadUI("AttendanceUser");
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
        attendanceManager = NonAcademicStaff.getInstance();
        
        // display User
        fxdisplayuserlbl.setText(attendanceManager.getName());
        
        // load HOME when load
        try {
            loadUI("AttendanceHome");
        } 
        catch (IOException ioe) {
            System.out.println(ioe);
        }
    }    
    
    public void loadUI(String ui) throws IOException {
        fxattendanceborderpane.setCenter(null);
        
        Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        fxattendanceborderpane.setCenter(root);
        
        // set animation
        new FadeIn(root).play();
    }
}
