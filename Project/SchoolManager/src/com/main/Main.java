package com.main;

import com.login.Login;
import com.string.Strings;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main {
    private static Main mainSingleton;
    private static Stage stage;
    
    private Main() {}
    
    public static Main getInstance() {
        if(mainSingleton == null) {
            synchronized(Main.class) {
                mainSingleton = new Main();
                System.out.println("mainSingleton created");
            }
        }
        
        return mainSingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        Main.stage = stage;
    }
    
    public void display(Stage stage) throws Exception {
        setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("Mainview.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.adminIconURL));
        stage.setTitle(Strings.titles_schoolManagerAdministrator);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.setScene(scene);
        stage.show();
    
        // closes parent stage and its child stages when closed
        stage.setOnCloseRequest(e-> {
            try {
                Main.getStage().close();
                
                Login newLogin = new Login();
                newLogin.start(new Stage());
            } 
            catch (Exception ex) {
                System.out.println(ex);
            }
        });
    }
    
}
