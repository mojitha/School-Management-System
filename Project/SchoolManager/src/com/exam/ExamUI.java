package com.exam;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ExamUI {
    private static ExamUI examUISingleton;
    private static Stage stage;
    
    private ExamUI() {}
    
    public static ExamUI getInstance() {
        if(examUISingleton == null) {
            synchronized(ExamUI.class) {
                examUISingleton = new ExamUI();
                System.out.println("examUISingleton created");
            }
        }
        
        return examUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ExamUI.stage = stage;
    }
    
    public void hideStage() {
        stage.hide();
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Examview.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.examIconURL));
        stage.setTitle("Exam Management");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // closes parent stage and its child stages when closed
        stage.setOnCloseRequest(e-> {
            try {
                ExamUI.getStage().close();
                
                Login newLogin = new Login();
                newLogin.start(new Stage());
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        });
        
        //animate the stage
        new FadeIn(root).play();
    }
    
}
