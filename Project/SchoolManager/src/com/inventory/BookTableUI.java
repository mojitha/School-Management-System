package com.inventory;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class BookTableUI {
    private static BookTableUI bookTableUISingleton;
    private static Stage stage;
    private static int undoFlagInsert = 0;
    private static String context;
    private static int loadFlagLast = 0;
    
    private BookTableUI() {
        super();
    }
    
    public static BookTableUI getInstance() {
        if(bookTableUISingleton == null) {
            synchronized(BookTableUI.class) {
                bookTableUISingleton = new BookTableUI();
                System.out.println(Strings.created_bookTableUISingleton);
            }
        }
        
        return bookTableUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        BookTableUI.stage = stage;
    }
    
    public static void hideStage() {
        stage.hide();
    }

    public static int getUndoFlagInsert() {
        return undoFlagInsert;
    }

    public static void setUndoFlagInsert() {
        if(undoFlagInsert == 0)
            undoFlagInsert = 1;
        else
            undoFlagInsert = 0;
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        BookTableUI.context = context;
    }

    public static int getLoadFlagLast() {
        return loadFlagLast;
    }

    public static void setLoadFlagLast() {
        if(loadFlagLast == 0)
            loadFlagLast = 1;
        else
            loadFlagLast = 0;
    }
    
    // show books table
    public void display(Stage stage) throws IOException {
        BookTableUI.setStage(stage);
        
        Parent root = FXMLLoader.load(getClass().getResource("InventoryviewBooksTable.fxml"));
        root.setStyle(InventoryUI.getTheme());
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.libraryIconURL));
        stage.setTitle(Strings.titles_inventoryBooks);
        stage.setScene(scene);
        stage.show();
        
        // hides the inventoryview
        Stage inventoryStage = InventoryUI.getStage();
        getStage().setOnCloseRequest(e-> {
            inventoryStage.show();
        });
        
        //animate the stage
        new FadeIn(root).play();
    }
}
