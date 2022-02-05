package com.student;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddStudentUI {
    private static AddStudentUI addStudentUISingleton;
    private static Stage stage;
    
    public static AddStudentUI getInstance() {
        if(addStudentUISingleton == null) {
            synchronized(AddStudentUI.class) {
                addStudentUISingleton = new AddStudentUI();
                System.out.println("addStudentUISingleton created");
            }
        }
        
        return addStudentUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AddStudentUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("addstudent.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.studentIconURL));
        stage.setTitle(Strings.titles_addStudent);
        
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(StudentTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
}
