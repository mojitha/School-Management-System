package com.staff;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StaffTableUI {
    private static StaffTableUI staffTableUISingleton;
    private static Stage stage;
    private static String context;
    
    private StaffTableUI() {}
    
    public static StaffTableUI getInstance() {
        if(staffTableUISingleton == null) {
            synchronized(AddStaffUI.class) {
                staffTableUISingleton = new StaffTableUI();
                System.out.println(Strings.created_staffTableUISingleton);
            }
        }
    
        return staffTableUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        StaffTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        StaffTableUI.context = context;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("StaffTable.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.staffIconURL));
        stage.setTitle("Staff Table");
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // hides the staffview 
        Stage parentStage = StaffUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());
        
        //animate the stage
        new FadeIn(root).play();
    }
}
