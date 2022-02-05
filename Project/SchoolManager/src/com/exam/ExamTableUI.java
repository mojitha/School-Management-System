package com.exam;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ExamTableUI {
    private static ExamTableUI examTableUISingleton;
    private static Stage stage;
    private static String context;
    private static int undoFlagInsert,undoFlagUpdate;
    
    public static ExamTableUI getInstance(){
        if(examTableUISingleton == null)
            synchronized(ExamTableUI.class){
                examTableUISingleton = new ExamTableUI();
                System.out.println(Strings.created_examTableUISingleton);
            }
        
        return examTableUISingleton;
    }

    public static int getUndoFlagInsert() {
        return undoFlagInsert;
    }

    public static void setUndoFlagInsert() {
        if(undoFlagInsert == 0)
            undoFlagInsert = 1;
        else if(undoFlagInsert == 1)
            undoFlagInsert = 0;
    }
    
    public static int getUndoFlagUpdate() {
        return undoFlagUpdate;
    }

    public static void setUndoFlagUpdate() {
        if(undoFlagUpdate == 0)
            undoFlagUpdate = 1;
        else if(undoFlagUpdate == 1)
            undoFlagUpdate = 0;
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ExamTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        ExamTableUI.context = context;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ExamTable.fxml"));
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(Strings.examIconURL));
        stage.setTitle(Strings.titles_examTable);
        stage.setScene(scene);
        stage.show();

        setStage(stage);

        // hides the studentview 
        Stage parentStage = ExamUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());

        // animate the stage
        new FadeIn(root).play();
    }
}
