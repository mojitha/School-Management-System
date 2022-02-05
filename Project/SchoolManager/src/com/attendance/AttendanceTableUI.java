package com.attendance;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AttendanceTableUI {
    private static AttendanceTableUI attendanceTableUISingleton;
    private static Stage stage;
    private static String context;
    
    public static AttendanceTableUI getInstance(){
        if(attendanceTableUISingleton == null)
            synchronized(AttendanceTableUI.class){
                attendanceTableUISingleton = new AttendanceTableUI();
                System.out.println(Strings.created_attendanceTableUISingleton);
            }
        
        return attendanceTableUISingleton;
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AttendanceTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }
    
    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        AttendanceTableUI.context = context;
    }

    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AttendanceTable.fxml"));
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
