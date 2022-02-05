package com.inventory;

import animatefx.animation.FadeIn;
import com.login.Login;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class InventoryUI {
    private static InventoryUI inventoryUISingleton;
    private static Stage stage;
    private static Stage editUserStage;
    private static String theme;
    private static int themeChagngedFlag = 0;
    
    private InventoryUI() {}
    
    public static InventoryUI getInstance() {
        if(inventoryUISingleton == null) {
            synchronized(InventoryUI.class) {
                inventoryUISingleton = new InventoryUI();
                System.out.println("inventoryUISingleton created");
            }
        }
        
        return inventoryUISingleton;
    }
    
    // set the primary stage -> InventoryUI Stage
    public static Stage getStage() {
        return stage;
    }
    
    // get the primary stage
    public static void setStage(Stage stage) {
        InventoryUI.stage = stage;
    }

    public static String getTheme() {
        return theme;
    }

    public static void setTheme(String theme) {
        InventoryUI.theme = theme;
    }

    public static int getThemeChagngedFlag() {
        return themeChagngedFlag;
    }

    public static void setThemeChagngedFlag() {
        if(themeChagngedFlag == 0)
            themeChagngedFlag = 1;
        else if(themeChagngedFlag == 1)
            themeChagngedFlag = 0;
    }
    
    // display the inventory manager main ui
    public void display(Stage stage) throws IOException {
        InventoryUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("Inventoryview.fxml"));
        root.setStyle(getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.inventoryIconURL));
        stage.setTitle("Inventory Management");
        stage.setScene(scene);
        stage.show();
        
        // closes parent stage and its child stages when closed and loads the login back again
        stage.setOnCloseRequest(e-> {
            try {
                InventoryUI.getStage().close();
                
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
    
    // get the edituser stage
    public static Stage getEditUserStage() {
        return editUserStage;
    }
    
    // set the primary stage -> EditUser Stage
    public static void setEditUserStage(Stage editUserStage) {
        InventoryUI.editUserStage = editUserStage;
    }
    
    // displays the edit profile stage
    public void displayEditUser(Stage stage) throws IOException {
        InventoryUI.setEditUserStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewEditUser.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.editUserIconURL));
        stage.setTitle(Strings.titles_editUser);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        
        // loads the inventory view ui
        stage.setOnCloseRequest(e->InventoryUI.getStage().show());
        
        //animate the stage
        new FadeIn(root).play();
    }
}
