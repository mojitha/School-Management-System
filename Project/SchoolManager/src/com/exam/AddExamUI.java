package com.exam;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class AddExamUI {
    private static AddExamUI AddExamUISingleton;
    private static Stage stage;
    
    private AddExamUI() {}
    
    public static AddExamUI getInstance() {
        if(AddExamUISingleton == null) {
            synchronized(AddExamUI.class) {
                AddExamUISingleton = new AddExamUI();
                System.out.println("AddExamUISingleton created");
            }
        }
        
        return AddExamUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AddExamUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddExam.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.examIconURL));
        stage.setTitle("Exam Management");
        
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(ExamTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        //animate the stage
        new FadeIn(root).play();
    }   
}
