package com.result;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ResultUI {
    private static ResultUI resultUISingleton;
    private static Stage stage;
    
    private ResultUI() {}
    
    public static ResultUI getInstance() {
        if(resultUISingleton == null) {
            synchronized(ResultUI.class) {
                resultUISingleton = new ResultUI();
                System.out.println("resultUISingleton created");
            }
        }
        
        return resultUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        ResultUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Resultview.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.resultsIconURL));
        stage.setTitle("Result Management");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        stage.setOnCloseRequest(e-> {
            try {
                ResultUI.getStage().close();
                
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
