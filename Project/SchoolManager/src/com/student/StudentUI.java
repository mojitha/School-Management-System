package com.student;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StudentUI {
    private static StudentUI studentUISingleton;
    private static Stage stage;
    
    private StudentUI() {}
    
    public static StudentUI getInstance() {
        if(studentUISingleton == null) {
            synchronized(StudentUI.class) {
                studentUISingleton = new StudentUI();
                System.out.println("studentUISingleton created");
            }
        }
        
        return studentUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StudentUI.stage = stage;
    }
    
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Studentview.fxml"));
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        
        //stage.getIcons().add(new Image(Strings.studentIconURL));
        stage.getIcons().add(new Image(Strings.studentIconURL));
        stage.setTitle("Student Management");
        stage.resizableProperty().setValue(Boolean.TRUE);
        stage.show();
        
        setStage(stage);
        
        // closes parent stage and its child stages when closed and loads the login back again
        stage.setOnCloseRequest(e-> {
            try {
                StudentUI.getStage().close();
                
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
