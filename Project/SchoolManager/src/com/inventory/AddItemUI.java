package com.inventory;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddItemUI {
    public static AddItemUI addItemUISingleton;
    private static Stage primaryStage;
    private static Stage thisStage;
    
    private AddItemUI() {}
    
    public static AddItemUI getInstance() {
        if(addItemUISingleton == null) {
            synchronized(AddItemUI.class) {
                addItemUISingleton = new AddItemUI();
                System.out.println(Strings.created_addItemUISingleton);
            }
        }
        
        return addItemUISingleton;
    }
    
    // get the primary stage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    // set the primary stage -> AddItemUI Stage
    public static void setPrimaryStage(Stage primaryStage) {
        AddItemUI.primaryStage = primaryStage;
    }
    
    // get this stage
    public static Stage getThisStage() {
        return thisStage;
    }
    
    // set this stage
    public static void setThisStage(Stage thisStage) {
        AddItemUI.thisStage = thisStage;
    }
    
    // hide primary stage which is AddItemUI stage
    public static void hidePrimaryStage() {
        primaryStage.hide();
    }
    
    public void display(Stage stage) throws IOException {
        AddItemUI.setPrimaryStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewAddItem.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.addItemIconURL));
        stage.setTitle(Strings.titles_addItem);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(ItemTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        //animate the stage
        new FadeIn(root).play();
    }
    
    public void displayAddCategory(Stage stage) throws IOException {
        AddItemUI.setThisStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewAddCategory.fxml"));
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.addCategoryIconURL));
        stage.setTitle(Strings.titles_addCategory);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        
        // set the parent stage blocked while this closed
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(getPrimaryStage());
        
        stage.setOnCloseRequest(e-> {
            primaryStage.close();
            try {
                AddItemUI.getInstance().display(new Stage());
            } 
            catch (IOException ioe) {
                System.out.println(ioe);
            }
        });
        
        stage.setScene(scene);
        stage.show();
        
        //animate the stage
        new FadeIn(root).play();
    }
}
