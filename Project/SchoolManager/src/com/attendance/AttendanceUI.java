package com.attendance;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AttendanceUI {
    private static AttendanceUI attendanceUISingleton;
    private static Stage stage;
    private static int updatedFlag = 0;
    
    private AttendanceUI() {}
    
    public static AttendanceUI getInstance() {
        if(attendanceUISingleton == null) {
            synchronized(AttendanceUI.class) {
                attendanceUISingleton = new AttendanceUI();
                System.out.println("attendanceUISingleton created");
            }
        }
        
        return attendanceUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AttendanceUI.stage = stage;
    }

    public static int getUpdatedFlag() {
        return updatedFlag;
    }

    public static void setUpdatedFlag() {
        if(updatedFlag == 0)
            updatedFlag = 1;
        else if(updatedFlag == 1)
            updatedFlag = 0;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Attendanceview.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.attendanceIconURL));
        stage.setTitle("Attendance Management");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // closes parent stage and its child stages when closed
        stage.setOnCloseRequest(e-> {
            try {
                AttendanceUI.getStage().close();
                
                Login newLogin = new Login();
                newLogin.start(new Stage());
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        });
        
        //animate the stage
        new FadeIn(root).play();
    }

}
