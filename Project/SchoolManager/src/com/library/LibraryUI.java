package com.library;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LibraryUI {
    private static LibraryUI libraryUISingleton;
    
    private LibraryUI() {}
    
    public static LibraryUI getInstance() {
        if(libraryUISingleton == null) {
            synchronized(LibraryUI.class) {
                libraryUISingleton = new LibraryUI();
                System.out.println("libraryUISingleton created");
            }
        }
        
        return libraryUISingleton;
    }
    
    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Libraryview.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.getIcons().add(new Image(Strings.libraryIconURL));
        stage.setTitle("Library Management");
        stage.setScene(scene);
        stage.show();
        
        // closes parent stage and its child stages when closed
        stage.setOnCloseRequest(e-> Platform.exit());
        
        //animate the stage
        new FadeIn(root).play();
    }
    
}
