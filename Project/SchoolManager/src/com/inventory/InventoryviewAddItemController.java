package com.inventory;

import animatefx.animation.FadeIn;
import com.connection.IdentitySM;
import com.item.Item;
import com.item.ItemDao;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InventoryviewAddItemController implements Initializable {
    // item to be added
    Item itemToAdd = Item.getCurrentItem();
    
    // item details for a new item
    @FXML
    public Label fxitemIdlbl,fxwarninglbl;
    public TextField fxdescriptiontxt,fxquantitytxt,fxcosttxt,fxexpiredatetxt;
    public DatePicker fxdatereceiveddtpickr;
    public ComboBox<String> fxstatuscmbox,fxunitcmbox,fxcategorycmbox;
    public CheckBox fxexpiredatechckbx;
    
    // button for manage categories
    @FXML
    public Button fxaddcategorybtn;
    
    // buttons for cancel and adding item 
    @FXML
    public Button fxadditemcancelbtn,fxadditemOKbtn;
    
    // item category manager opens
    @FXML
    public void loadManageCategory(ActionEvent event) throws IOException {
        AddItemUI additemuiinstance = AddItemUI.getInstance();
        Stage stage = new Stage();
        additemuiinstance.displayAddCategory(stage);
    }
    
    // cancels adding an item
    @FXML
    public void closeAddItem(ActionEvent event) {
        Stage additemstage = (Stage) fxadditemcancelbtn.getScene().getWindow();
        additemstage.close();
    }
    
    @FXML
    public void checkExpireDate(ActionEvent event) {
        if(fxexpiredatechckbx.isSelected()) {
            fxexpiredatetxt.setVisible(false);
            fxexpiredatetxt.setText(Strings.notSpecified);
        }
        else {
            fxexpiredatetxt.setVisible(true);
            fxexpiredatetxt.setText("");
        }
    }
    
    // adding an item
    @FXML
    public void okAddItem(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_addItem);
        
        String itemID = fxitemIdlbl.getText();
        String description = fxdescriptiontxt.getText().trim();
        String quantity = fxquantitytxt.getText().trim();
        String cost = fxcosttxt.getText().trim();
        String expireDate = fxexpiredatetxt.getText().trim();
        DatePicker date = fxdatereceiveddtpickr;
        SingleSelectionModel<String> category = fxcategorycmbox.getSelectionModel();
        SingleSelectionModel<String> status = fxstatuscmbox.getSelectionModel();
        SingleSelectionModel<String> unit = fxunitcmbox.getSelectionModel();
        
        // validates if all item details are given by inventory manager correctly
        if(validateDescription(description, 50)) {
            if(isCombo(category,Strings.category)) {
                if(validateQuantity(quantity)) {
                    if(isCombo(unit,Strings.unit)) {
                        if(isCombo(status,Strings.status)) {
                            if(validateCost(cost)) {
                                if(checkDate(date)) {
                                    if(validateExpireDate(expireDate)) {
                                        System.out.println("OKAY");
                                        
                                        String catVal = category.getSelectedItem();
                                        String unitVal = unit.getSelectedItem();
                                        String statusVal = status.getSelectedItem();
                                        
                                        itemToAdd.setResourceID(itemID);
                                        itemToAdd.setDescription(description);
                                        itemToAdd.setCategory(catVal);
                                        itemToAdd.setQuantity(quantity);
                                        itemToAdd.setUnit(unitVal);
                                        itemToAdd.setCost(cost);
                                        itemToAdd.setStatus(statusVal);
                                        itemToAdd.setExpireDate(expireDate);
                                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                            String supplyDate = dt.format(dt.parse(date.getValue().toString()));
                                        itemToAdd.setSupplyDate(supplyDate);

                                        System.out.println(itemID+" "+description+" "+category+" "+quantity+" "+cost+" "+supplyDate+" "+status+" "+expireDate);

                                        if(ItemDao.saveItem(itemToAdd)) {
                                            System.out.println(Strings.itemAdded);
                                                Stage stage = (Stage) fxadditemOKbtn.getScene().getWindow();
                                                stage.close();

                                            // loads tableview with updated values
                                            ItemTableUI.hideStage();
                                            ItemTableUI.setUndoFlagInsert();
                                            ItemTableUI.setContext(Strings.context_insert);
                                            System.out.println(Strings.itemAdded);
                                            ItemTableUI.setLoadFlagLast();
                                            ItemTableUI.getInstance().display(ItemTableUI.getStage());
                                            
                                        }
                                        else {
                                            System.out.println(Strings.itemNotAdded);
                                                Stage stage = (Stage) fxadditemOKbtn.getScene().getWindow();
                                                stage.close();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    public boolean validateDescription(String textInput, int charCount) {
        if(textInput.length() > charCount ||  textInput.isEmpty()) {
            fxwarninglbl.setText(Strings.invalidDescription);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    public boolean validateExpireDate(String textInput) {
        if(fxexpiredatechckbx.isSelected())
            return true;
        else {
            if (textInput.isEmpty()) {
                fxwarninglbl.setText(Strings.invalidExpireDate);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
            else {
                SimpleDateFormat simpledtformat = new SimpleDateFormat("yyyy-MM-dd");
                simpledtformat.setLenient(false);

                try {
                    Date dt = simpledtformat.parse(textInput);
                }
                catch (ParseException pe) {
                    System.out.println(pe);
                    fxwarninglbl.setText(Strings.invalidExpireDate);
                    new FadeIn(fxwarninglbl).play();
                    return false;
                }

                return true;
            }
        }
    }
    
    public boolean validateCost(String number) {
        try {
            if(number.isEmpty()) {
                fxwarninglbl.setText(Strings.invalidCost);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
            else return Double.parseDouble(number) >= 0;
        }
        catch(NumberFormatException nfe) {
            System.out.println(nfe);
            fxwarninglbl.setText(Strings.invalidCost);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
    }
    
    public boolean validateQuantity(String textInput) {
        if(textInput.isEmpty()) {
            fxwarninglbl.setText(Strings.invalidQuantity);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else {
            try {
                Double.parseDouble(textInput);
                fxwarninglbl.setText(Strings.invalidQuantity);
                new FadeIn(fxwarninglbl).play();
            } 
            catch(NumberFormatException nfe) {
                System.out.println(nfe);
                fxwarninglbl.setText(Strings.invalidQuantity);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
        }
        
        return true;
    }
    
    public boolean isCombo(SingleSelectionModel<String> option, String type) {
        if(option.isEmpty()) {
            switch (type) {
                case Strings.status:
                    fxwarninglbl.setText(Strings.notSelectedStatus);
                    new FadeIn(fxwarninglbl).play();
                    break;
                case Strings.unit:
                    fxwarninglbl.setText(Strings.notSelectedUnit);
                    new FadeIn(fxwarninglbl).play();
                    break;
                case Strings.category:
                    fxwarninglbl.setText(Strings.notSelectedCategory);
                    new FadeIn(fxwarninglbl).play();
                    break;
            }
            
            return false;
        }
        else
            return true;
    }
    
    // checks the validity of the date 
    public boolean checkDate(DatePicker date) {
        if(date.getValue() == null) {
            fxwarninglbl.setText(Strings.notSelectedDate);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set itemID which is auto generated
        fxitemIdlbl.setText(IdentitySM.getID(Strings.Item));
        
        // set categories in combobox
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(ItemDao.getCategories());
        fxcategorycmbox.setItems(oblistA);
        
        // set status in combobox
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(Strings.status_NEW);
        fxstatuscmbox.setItems(oblistB);
        
        // set units in combo box
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(ItemDao.getUnits());
        fxunitcmbox.setItems(oblistC);
        
        // set default date
        fxdatereceiveddtpickr.setValue(getLocalDate());
        
        // selects status as NEW
        fxstatuscmbox.getSelectionModel().select(0);
        
        // set warning
        fxwarninglbl.setText("");
        
    }    
    
    
}
