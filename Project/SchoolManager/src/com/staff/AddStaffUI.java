package com.staff;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddStaffUI {
    private static AddStaffUI addStaffUISingleton;
    private static Stage stage;
    
    private AddStaffUI() {}
    
    public static AddStaffUI getInstance() {
        if(addStaffUISingleton == null) {
            synchronized(AddStaffUI.class) {
                addStaffUISingleton = new AddStaffUI();
                System.out.println(Strings.created_addStaffUISingleton);
            }
        }
    
        return addStaffUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        AddStaffUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddStaff.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.staffIconURL));
        stage.setTitle("Add Staff");
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(StaffTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        //animate the stage
        new FadeIn(root).play();
    }
}
