package com.inventory;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ItemTableUI {
    private static ItemTableUI itemTableUISingleton;
    private static Stage stage;
    private static int undoFlagInsert = 0;
    private static int undoFlagUpdate = 0;
    private static int loadFlagLast = 0;
    private static int categoryFlagChange = 0;
    private static int updatedFlagLast = 0;
    private static String context;
    
    private ItemTableUI() {}
    
    public static ItemTableUI getInstance() {
        if(itemTableUISingleton == null)
           synchronized(ItemTableUI.class) {
               itemTableUISingleton = new ItemTableUI();
               System.out.println(Strings.created_itemTableUISingleton);
           }
        
        return itemTableUISingleton;
    }
    
    public static void setStage(Stage stage) {
        ItemTableUI.stage = stage;
    }
    
    public static Stage getStage() {
        return stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }
    
    public static void setUndoFlagInsert() {
        if(undoFlagInsert == 0)
            undoFlagInsert = 1;
        else
            undoFlagInsert = 0;
    }
    
    public static int getUndoFlagInsert() {
        return undoFlagInsert;
    }

    public static int getUndoFlagUpdate() {
        return undoFlagUpdate;
    }

    public static void setUndoFlagUpdate() {
        if(undoFlagUpdate == 0)
            undoFlagUpdate = 1;
        else
            undoFlagUpdate = 0;
    }
    
    // sets the load last flag
    public static void setLoadFlagLast() {
        if(loadFlagLast == 0)
            loadFlagLast = 1;
        else
            loadFlagLast = 0;
    }
    
    // gets the load last flag
    public static int getLoadFlagLast() {
        return loadFlagLast;
    }
    
    // gets the context of undoing
    public static String getContext() {
        return context;
    }
    
    // sets the context of undoing
    public static void setContext(String context) {
        ItemTableUI.context = context;
    }

    public static int getCategoryFlagChange() {
        return categoryFlagChange;
    }

    public static void setCategoryFlagChange() {
        if(categoryFlagChange == 0)
            categoryFlagChange = 1;
        else
            categoryFlagChange = 0;
    }

    public static int getUpdatedFlagLast() {
        return updatedFlagLast;
    }

    public static void setUpdatedFlagLast() {
        if(updatedFlagLast == 0)
            updatedFlagLast = 1;
        else if(updatedFlagLast == 1)
            updatedFlagLast = 0;            
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewItemsTable.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.inventoryItemsTableIconURL));
        stage.setTitle(Strings.titles_inventoryItems);
        stage.setScene(scene);
        stage.show();
        
        setStage(stage);
        
        // hides the inventoryview
        Stage parentStage = InventoryUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());
        
        //animate the stage
        new FadeIn(root).play();
    }
    
}
