package com.attendance;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AttendanceClassesMarkedUI {
    private static AttendanceClassesMarkedUI attendanceClassesMarkedUISingleton;
    private static Stage stage;
    
    public static AttendanceClassesMarkedUI getInstance(){
        if(attendanceClassesMarkedUISingleton == null)
            synchronized(AttendanceClassesMarkedUI.class){
                attendanceClassesMarkedUISingleton = new AttendanceClassesMarkedUI();
                System.out.println(Strings.created_attendanceClassesMarkedUISingleton);
            }
        
        return attendanceClassesMarkedUISingleton;
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AttendanceClassesMarkedUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AttendanceClassesMarked.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.attendanceIconURL));
        stage.setTitle(Strings.titles_attendanceTable);
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // hides the studentview 
        Stage parentStage = AttendanceUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());
        
        // animate the stage
        new FadeIn(root).play();
    }

    
}
