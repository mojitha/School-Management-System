package com.subject;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SubjectTableUI {
    private static SubjectTableUI subjectTableUISingleton;
    private static Stage stage;
    private static String context;
    
    private SubjectTableUI() {
        super();
    }
    
    public static SubjectTableUI getInstance() {
        if(subjectTableUISingleton == null) {
            synchronized(SubjectTableUI.class) {
                subjectTableUISingleton = new SubjectTableUI();
                System.out.println(Strings.created_subjectTableUISingleton);
            }
        }
        
        return subjectTableUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        SubjectTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        SubjectTableUI.context = context;
    }
    
    public void display(Stage stage) throws IOException {    
        Parent root = FXMLLoader.load(getClass().getResource("SubjectTable.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.subjectIconURL));
        stage.setTitle("Subject Table");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // hides the subject 
        Stage parentStage = SubjectUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());
        
        //animate the stage
        new FadeIn(root).play();
    }
}
