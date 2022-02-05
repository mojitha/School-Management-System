package com.exam;

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

public class UpdateExamUI {
    private static UpdateExamUI updateExamUISingleton;
    private static Stage stage;
    private static Exam oldExam;

    public static Exam getOldExam() {
        return oldExam;
    }

    public static void setOldExam(Exam oldExam) {
        UpdateExamUI.oldExam = oldExam;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UpdateExamUI.stage = stage;
    }
    
    public static UpdateExamUI getInstance() {
        if(updateExamUISingleton == null) {
            synchronized(UpdateExamUI.class) {
                updateExamUISingleton = new UpdateExamUI();
                System.out.println(Strings.created_updateExamUISingleton);
            }
        }
        
        return updateExamUISingleton;
    }

    public void display(Stage stage) throws IOException {
        UpdateExamUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("UpdateExam.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.examIconURL));
        stage.setTitle(Strings.titles_updateExam);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // set the parent stage blocked while this closed
        stage.initOwner(ExamTableUI.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }

}
