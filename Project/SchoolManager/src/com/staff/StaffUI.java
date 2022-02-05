package com.staff;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StaffUI {
    private static StaffUI staffUISingleton;
    private static Stage stage;
    
    private StaffUI() {}
    
    public static StaffUI getInstance() {
        if(staffUISingleton == null) {
            synchronized(StaffUI.class) {
                staffUISingleton = new StaffUI();
                System.out.println(Strings.created_staffUISingleton);
            }
        }
        
        return staffUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StaffUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Staffview.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.staffIconURL));
        stage.setTitle("Staff Management");
        
        setStage(stage);
        
        stage.setScene(scene);
        stage.show();
        
        // closes parent stage and its child stages when closed and loads the login back again
        stage.setOnCloseRequest(e-> {
            try {
                StaffUI.getStage().close();
                
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
