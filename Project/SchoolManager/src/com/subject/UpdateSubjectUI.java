package com.subject;

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

public class UpdateSubjectUI {
    private static Stage stage;
    private static UpdateSubjectUI updateSubjectUISingleton;
    private static int updatedFlag;
    private static Subject oldSubject;

    public static UpdateSubjectUI getInstance() {
        if(updateSubjectUISingleton == null) {
            synchronized(UpdateSubjectUI.class) {
                updateSubjectUISingleton = new UpdateSubjectUI();
                System.out.println(Strings.created_updateSubjectUISingleton);
            }
        }
        
        return updateSubjectUISingleton;
    }
    
    public void display(Stage stage) throws IOException {
        UpdateSubjectUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("UpdateSubject.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.subjectIconURL));
        stage.setTitle(Strings.titles_updateSubject);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // set the parent stage blocked while this closed
        stage.initOwner(SubjectTableUI.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UpdateSubjectUI.stage = stage;
    }
    
    public static Subject getOldSubject() {
        return oldSubject;
    }

    public static void setOldSubject(Subject oldSubject) {
        UpdateSubjectUI.oldSubject = oldSubject;
    }

    public static int getUpdatedFlag() {
        return updatedFlag;
    }

    public static void setUpdatedFlag() {
        if(updatedFlag == 0)
            updatedFlag = 1;
        else if(updatedFlag == 1)
            updatedFlag = 0;
    }

    
    
    
}
