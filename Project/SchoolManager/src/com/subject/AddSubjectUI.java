package com.subject;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddSubjectUI {
    private static AddSubjectUI addSubjectUISingleton;
    private static Stage stage;
    
    public static AddSubjectUI getInstance() {
        if(addSubjectUISingleton == null) {
            synchronized(AddSubjectUI.class) {
                addSubjectUISingleton = new AddSubjectUI();
                System.out.println(Strings.created_addSubjectUISingleton);
            }
        }
        
        return addSubjectUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AddSubjectUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddSubject.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.subjectIconURL));
        stage.setTitle(Strings.titles_addSubject);
        
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(SubjectTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // animate the stage
        new FadeIn(root).play();
    }
}
