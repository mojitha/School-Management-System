package com.login;

import animatefx.animation.Pulse;
import com.string.Strings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Login extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Loginview.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.schoolManagerIconURL));
        stage.setTitle("SchoolManager");
        stage.resizableProperty().setValue(Boolean.FALSE);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        //animate the stage
        new Pulse(root).play();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
