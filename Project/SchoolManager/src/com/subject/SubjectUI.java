package com.subject;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SubjectUI {
    private static SubjectUI subjectUISingleton;
    private static Stage stage;
    
    private SubjectUI() {
        super();
    }
    
    public static SubjectUI getInstance() {
        if(subjectUISingleton == null) {
            synchronized(SubjectUI.class) {
                subjectUISingleton = new SubjectUI();
                System.out.println(Strings.created_subjectUISingleton);
            }
        }
        
        return subjectUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        SubjectUI.stage = stage;
    }
    
    public static void hideStage(Stage stage) {
        stage.hide();
    }
    
    public void display(Stage stage) throws IOException {    
        Parent root = FXMLLoader.load(getClass().getResource("Subjectview.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.subjectIconURL));
        stage.setTitle("Subject Management");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // closes parent stage and its child stages when closed
        stage.setOnCloseRequest(e-> {
            try {
                SubjectUI.getStage().close();
                
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
