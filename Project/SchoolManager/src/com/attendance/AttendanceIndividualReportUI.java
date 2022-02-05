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

public class AttendanceIndividualReportUI {
    private static AttendanceIndividualReportUI attendanceIndividualReportUISingleton;
    private static Stage stage;
    private static Attendance thisAttendance;
    
    public static AttendanceIndividualReportUI getInstance() {
        if(attendanceIndividualReportUISingleton == null) {
            synchronized(AttendanceIndividualReportUI.class) {
                attendanceIndividualReportUISingleton = new AttendanceIndividualReportUI();
                System.out.println("attendanceIndividualReportUISingleton created");
            }
        }
        
        return attendanceIndividualReportUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AttendanceIndividualReportUI.stage = stage;
    }

    public static Attendance getThisAttendance() {
        return thisAttendance;
    }

    public static void setThisAttendance(Attendance thisAttendance) {
        AttendanceIndividualReportUI.thisAttendance = thisAttendance;
    }
    
    public void display(Stage stage) throws IOException {
        setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("AttendanceIndividualReport.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.attendanceIconURL));
        stage.setTitle(Strings.titles_individualAttendanceReport);
        
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
