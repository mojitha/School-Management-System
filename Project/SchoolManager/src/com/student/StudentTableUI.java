package com.student;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StudentTableUI {
    private static StudentTableUI studentTableUISingleton;
    private static Stage stage;
    private static String context;
    private static int loadFlagLast = 0;
    
    public static StudentTableUI getInstance(){
        if(studentTableUISingleton == null)
            synchronized(StudentTableUI.class){
                studentTableUISingleton = new StudentTableUI();
                System.out.println(Strings.created_studentTableUISingleton);
            }
        
        return studentTableUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StudentTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }
    
    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        StudentTableUI.context = context;
    }

    public static int getLoadFlagLast() {
        return loadFlagLast;
    }

    public static void setLoadFlagLast() {
        if(loadFlagLast == 0)
            loadFlagLast = 1;
        else
            loadFlagLast = 0;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Studenttable.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.studentIconURL));
        stage.setTitle(Strings.titles_studentTable);
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // hides the studentview 
        Stage parentStage = StudentUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());
        
        // animate the stage
        new FadeIn(root).play();
    }
    
}
