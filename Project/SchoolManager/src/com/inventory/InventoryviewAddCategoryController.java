package com.inventory;

import animatefx.animation.FadeIn;
import com.connection.IdentitySM;
import com.item.ItemDao;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class InventoryviewAddCategoryController implements Initializable {
    ObservableList<String> availablecategorieslist = FXCollections.observableArrayList();
    
    @FXML
    public TextField fxaddcategorytxt,fxchangecategorytxt,fxsearchcategorytxt;
    public Button fxaddcategorybtn,fxchangecategorybtn;
    public ListView<String> fxavailablecategorieslstview;
    public Label fxwarninglbl;
    
    // saves a new category on the database
    @FXML
    public void saveCategory(ActionEvent event) throws IOException {
        String newCategory = fxaddcategorytxt.getText();
        if(validateCategory(newCategory, 30)) {
            System.out.println(newCategory);
            String newCategoryID = IdentitySM.getID(Strings.ItemCategories);
            if(ItemDao.saveCategory(newCategoryID,newCategory)) {
                ItemTableUI.setCategoryFlagChange();
                fxwarninglbl.setText(Strings.categoryAdded);
                new FadeIn(fxwarninglbl).play();
                refreshCategories();
            }
            else {
                fxwarninglbl.setText(Strings.categoryNotAdded);
                new FadeIn(fxwarninglbl).play();
            }
        }
    }
    
    // change categories
    @FXML
    public void changeCategory(ActionEvent event) throws IOException {
        String changedCategory = fxavailablecategorieslstview.getSelectionModel().getSelectedItem();
        String newCategory = fxchangecategorytxt.getText();
        if(validateCategory(newCategory, 30)) {
            System.out.println(newCategory);
            if(ItemDao.updateCategory(changedCategory,newCategory)) {
                fxwarninglbl.setText(Strings.categoryUpdated);
                new FadeIn(fxwarninglbl).play();
                refreshCategories();
            }
            else {
                fxwarninglbl.setText(Strings.categoryNotUpdated);
                new FadeIn(fxwarninglbl).play();
            }
        }
    }
    
    // loads category to edit
    @FXML
    public void editCategory(MouseEvent event) {
        if(fxchangecategorytxt.getText() != null) {
            fxchangecategorytxt.setDisable(false);
            fxchangecategorybtn.setDisable(false);
            String selected = fxavailablecategorieslstview.getSelectionModel().getSelectedItem();
            fxchangecategorytxt.setText(selected);
        }
        else
            fxchangecategorytxt.setDisable(true);
    }
    
    // searches category
    @FXML
    public void searchCategories(KeyEvent event) throws IOException {
        System.out.println(Strings.clicked_searchCategory);
        String keyword = fxsearchcategorytxt.getText();
        System.out.println(keyword);
        if(keyword.isEmpty())
            refreshCategories();
        else
            fxavailablecategorieslstview.setItems(ItemDao.searchCategories(keyword));
    }
    
    // validates the category
    public boolean validateCategory(String textInput, int charCount) {
        if(textInput.length() > charCount ||  textInput.isEmpty()) {
            fxwarninglbl.setText(Strings.invalidCategory);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else {
            fxwarninglbl.setText("");
            return true;
        }
    }
    
    // refreshes available categories
    public void refreshCategories() throws IOException {
        availablecategorieslist.clear();
        availablecategorieslist.addAll(ItemDao.getCategories());
        fxavailablecategorieslstview.setItems(availablecategorieslist);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set categories in listview
        availablecategorieslist.addAll(ItemDao.getCategories());
        fxavailablecategorieslstview.setItems(availablecategorieslist);
        
        // set warning to null
        fxwarninglbl.setText("");
    }    
    
}
