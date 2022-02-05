package com.inventory;

import animatefx.animation.FadeIn;
import com.user.NonAcademicStaff;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class InventoryviewController implements Initializable {
    // nonacademic
    private NonAcademicStaff inventoryManager;
    
    // side pane buttons
    @FXML
    public Button fxhomebtn,fxinventorybtn,fxstatisticsbtn,fxsettingsbtn,fxnotificationsbtn,fxuserbtn;
            
    @FXML
    public ImageView fxstatusimgview;
    public BorderPane fxinventoryborderpane;
    public Label fxdisplayuserlbl,fxdisplaytimelbl;
    public HBox fxtableoptionhbox;
    
    @FXML 
    public void loadHome(ActionEvent event) throws IOException {
        System.out.println("Clicked Home");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewHome");
    }
    
    @FXML 
    public void loadInventory(ActionEvent event) throws IOException {
        System.out.println("Clicked Inventory");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewInventory");
    }
    
    @FXML
    public void loadNotifications(ActionEvent event) throws IOException {
        System.out.println("Clicked Notifications");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewNotifications");
    }

    @FXML
    public void loadSettings(ActionEvent event) throws IOException {
        System.out.println("Clicked Settings");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewSettings");
    }

    @FXML
    public void loadStatistics(ActionEvent event) throws IOException {
        System.out.println("Clicked Statistics");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewStatistics");
    }
    
    @FXML
    public void loadUser(ActionEvent event) throws IOException {
        System.out.println("Clicked User");
        fxinventoryborderpane.setCenter(null);
        loadUI("InventoryviewUser");
    }
    
    public void loadUI(String ui) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        fxinventoryborderpane.setCenter(root);
        
        //animate the stage
        new FadeIn(root).play();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set time
        try {
            Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e-> {
                int hour;
                String minute,ampm;
                
                DateTimeFormatter minutestamp = DateTimeFormatter.ofPattern("mm");
                hour = LocalDateTime.now().getHour();
                minute = LocalDateTime.now().format(minutestamp);
               
                if(hour > 12) {
                    hour -= 12; 
                    ampm = " PM";
                }
                else if(hour == 12)
                    ampm = " PM";
                else
                    ampm = " AM";
                
                fxdisplaytimelbl.setText(hour + ":" + minute + ampm);
            }), new KeyFrame(Duration.seconds(1)));
            
            clock.setCycleCount(Animation.INDEFINITE);
            clock.play();
        }
        catch (Exception e) {
            System.out.println(e);
        }
        
        // set User
        inventoryManager = NonAcademicStaff.getInstance();
        
        // load main view as home
        try {
            if(InventoryUI.getThemeChagngedFlag() == 1) {
                loadUI("InventoryviewSettings");
                InventoryUI.setThemeChagngedFlag();
            }
            else
                loadUI("InventoryviewHome");
        } 
        catch (IOException ex) {
            System.out.println(ex);
        }
        
        // display User
        fxdisplayuserlbl.setText(inventoryManager.getName());
        
    }
    
}
