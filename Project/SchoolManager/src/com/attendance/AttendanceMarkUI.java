package com.attendance;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AttendanceMarkUI {
    private static AttendanceMarkUI attendanceMarkUISingleton;
    private static Stage stage;
    
    public static AttendanceMarkUI getInstance() {
        if(attendanceMarkUISingleton == null) {
            synchronized(AttendanceMarkUI.class) {
                attendanceMarkUISingleton = new AttendanceMarkUI();
                System.out.println("attendanceMarkUISingleton created");
            }
        }
        
        return attendanceMarkUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AttendanceMarkUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("AttendanceMark.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.attendanceIconURL));
        stage.setTitle(Strings.titles_markAttendance);
        
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(AttendanceTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
}
