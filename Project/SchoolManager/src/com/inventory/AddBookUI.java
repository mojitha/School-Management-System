package com.inventory;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class AddBookUI {
    public static AddBookUI addBookUISingleton;
    private static Stage primaryStage;
    private static Stage thisStage;
    
    public AddBookUI() {
        super();
    }
    
    public static AddBookUI getInstance() {
        if(addBookUISingleton == null) {
            synchronized(AddBookUI.class) {
                addBookUISingleton = new AddBookUI();
                System.out.println(Strings.created_addBookUISingleton);
            }
        }
        
        return addBookUISingleton;
    }
    
    // get the primary stage
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
    
    // set the primary stage -> AddBookUI Stage
    public static void setPrimaryStage(Stage primaryStage) {
        AddBookUI.primaryStage = primaryStage;
    }
    
    // get this stage
    public static Stage getThisStage() {
        return thisStage;
    }
    
    // set this stage
    public static void setThisStage(Stage thisStage) {
        AddBookUI.thisStage = thisStage;
    }
    
    // hide primary stage which is AddBookUI stage
    public static void hidePrimaryStage() {
        primaryStage.hide();
    }
    
    public void display(Stage stage) throws IOException {
        AddBookUI.setPrimaryStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewAddBook.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.addBookIconURL));
        stage.setTitle(Strings.titles_addBook);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        stage.initOwner(BookTableUI.getStage());
        
        stage.setScene(scene);
        stage.show();
        
        //animate the stage
        new FadeIn(root).play();
    }
    
    
}
