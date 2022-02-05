package com.inventory;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryviewInventoryController implements Initializable {
    @FXML
    public Button fxinventoryitemsbtn,fxinventorybooksbtn;
    public ImageView fxitemsimgview,fxbooksimgview;
    
    @FXML
    public void loadItemsTable(ActionEvent event) throws IOException {
        ItemTableUI itemtableinstance = ItemTableUI.getInstance();
        Stage itemtablestage = new Stage();
        itemtableinstance.display(itemtablestage);
        
        InventoryUI.getStage().hide();
    }
    
    @FXML
    public void loadBooksTable(ActionEvent event) throws IOException {
        BookTableUI booktableinstance = BookTableUI.getInstance();
        Stage booktablestage = new Stage();
        booktableinstance.display(booktablestage);
       
        InventoryUI.getStage().hide();
    }
    
    @FXML
    public void hoverItems(MouseEvent event) {
        fxitemsimgview.setImage(new Image(Strings.hoverItemIconSURL));
        fxbooksimgview.setImage(new Image(Strings.hoverBookIconPURL));
        animateNode(fxinventoryitemsbtn,1);
    }
    
    @FXML
    public void hoverBooks(MouseEvent event) {
        fxbooksimgview.setImage(new Image(Strings.hoverBookIconSURL));
        fxitemsimgview.setImage(new Image(Strings.hoverItemIconPURL));
        animateNode(fxinventorybooksbtn,1);
    }
    
    // animator
    public void animateNode(Node node, int animation) {
        switch(animation) {
            case 0:
                new FadeOut(node).play();
            break;
            case 1:
                new FadeIn(node).play();
            break;
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
    }    
    
}
