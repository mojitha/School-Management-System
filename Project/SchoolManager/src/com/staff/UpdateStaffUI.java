package com.staff;

import animatefx.animation.FadeIn;
import com.string.Strings;
import com.user.User;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class UpdateStaffUI {
    private static Stage stage;
    private static User oldUser;
    private static int updatedFlag;
    private static UpdateStaffUI updateStaffUISingleton;
    
    public static UpdateStaffUI getInstance() {
        if(updateStaffUISingleton == null) {
            synchronized(UpdateStaffUI.class) {
                updateStaffUISingleton = new UpdateStaffUI();
                System.out.println(Strings.created_updateStaffUISingleton);
            }
        }
        
        return updateStaffUISingleton;
    }

    public static int getUpdatedFlag() {
        return updatedFlag;
    }

    public static void setUpdatedFlag() {
        if(updatedFlag == 0)
            updatedFlag = 1;
        else if(updatedFlag == 1)
            updatedFlag = 0;
    }
    
    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        UpdateStaffUI.stage = stage;
    }
    
    public void display(Stage stage) throws IOException {
        UpdateStaffUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("UpdateStaff.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.staffIconURL));
        stage.setTitle(Strings.titles_updateStaff);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // set the parent stage blocked while this closed
        stage.initOwner(StaffTableUI.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
    
    public static User getOldUser() {
        return oldUser;
    }

    public static void setOldUser(User oldUser) {
        UpdateStaffUI.oldUser = oldUser;
    }
    
    
}
