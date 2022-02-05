package com.student;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateStudentUI {
    private static UpdateStudentUI updateStudentUISingleton;
    private static Stage stage;
    private static int updatedFlag;
    private static Student oldStudent;

    public static UpdateStudentUI getInstance() {
        if(updateStudentUISingleton == null) {
            synchronized(UpdateStudentUI.class) {
                updateStudentUISingleton = new UpdateStudentUI();
                System.out.println(Strings.created_updateStudentUISingleton);
            }
        }
        
        return updateStudentUISingleton;
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UpdateStudentUI.stage = stage;
    }

    public static Student getOldStudent() {
        return oldStudent;
    }

    public static void setOldStudent(Student oldStudent) {
        UpdateStudentUI.oldStudent = oldStudent;
    }

    public static int getUpdatedFlag() {
        return updatedFlag;
    }

    public static void setUpdatedFlag() {
        if(updatedFlag == 1) 
            updatedFlag = 0;
        else if(updatedFlag == 0)
            updatedFlag = 1;
    }
    
    public void display(Stage stage) throws IOException {
        UpdateStudentUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("UpdateStudent.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.studentIconURL));
        stage.setTitle(Strings.titles_updateStudent);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // set the parent stage blocked while this closed
        stage.initOwner(StudentTableUI.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
    
    
    
}
