package com.inventory;

import animatefx.animation.FadeIn;
import com.item.Item;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ChangeItemUI {
    private static ChangeItemUI changeItemUISingleton;
    private static Stage thisStage;
    private static Item oldItem;
    
    public static void setOldItem(Item item) {
        oldItem = item;
    }

    public static Item getOldItem() {
        return oldItem;
    }
    
    private ChangeItemUI() {
        super();
    }
    
    public static ChangeItemUI getInstance() {
        if(changeItemUISingleton == null) {
            synchronized(ChangeItemUI.class) {
                changeItemUISingleton = new ChangeItemUI();
                System.out.println(Strings.created_changeItemUISingleton);
            }
        }
        
        return changeItemUISingleton;
    }

    public static Stage getThisStage() {
        return thisStage;
    }

    public static void setThisStage(Stage thisStage) {
        ChangeItemUI.thisStage = thisStage;
    }
    
    public void display(Stage stage) throws IOException {
        ChangeItemUI.setThisStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewChangeStatus.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.changeItemStatusIconURL));
        stage.setTitle(Strings.titles_changeItem);
        stage.setAlwaysOnTop(true);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        
        // set the parent stage blocked while this closed
        stage.initOwner(ItemTableUI.getStage());
        stage.initModality(Modality.WINDOW_MODAL);
        
        stage.setScene(scene);
        stage.show();
        
        // animate the stage
        new FadeIn(root).play();
    }
}
