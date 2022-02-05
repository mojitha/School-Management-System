package com.inventory;

import com.connection.ValidateSM;
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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InventoryviewChangeStatusController implements Initializable {
    // item to be changed
    Item currItem = Item.getCurrentItem();
    
    @FXML
    public Button fxcancelbtn,fxchangeitemOKbtn,fxsetoutbtn,fxfineremovebtn; 
    public Label fxitemidlbl,fxwarninglbl;
    public VBox fxhiddenvbox;
    public ComboBox fxstatuscmbbx;
    public TextField fxdescriptiontxt,fxquantitytxt,fxcosttxt;
    public DatePicker fxdateupdateddtpickr;
    
    // closes change status window
    @FXML 
    public void closeChangeStatus(ActionEvent event) {
        Stage stage = (Stage) fxcancelbtn.getScene().getWindow();
        stage.close();
    }
    
    // ok function change status of an item
    @FXML
    public void changeStatus(ActionEvent event) throws IOException, ParseException {
        String itemID = fxitemidlbl.getText();
        String description = fxdescriptiontxt.getText().trim();
        String quantity = fxquantitytxt.getText().trim();
        String cost = fxcosttxt.getText().trim();
        DatePicker date = fxdateupdateddtpickr;
        SingleSelectionModel<String> selStatus = fxstatuscmbbx.getSelectionModel();
        
        // validates if all item details are given by inventory manager correctly
        if(ValidateSM.validateText(description, 50, Strings.invalidDescription, fxwarninglbl)) {
            if(ValidateSM.validateNumber(quantity, Strings.invalidQuantity, fxwarninglbl)) {
                if(ValidateSM.validateNumber(cost, Strings.invalidCost, fxwarninglbl)) {
                    if(ValidateSM.checkDate(date, Strings.invalidUpdatedOn, fxwarninglbl)) {
                        if(ValidateSM.isCombo(selStatus, Strings.notSelectedStatus, fxwarninglbl)) {
                            System.out.println("OKAY");
                            String status = selStatus.getSelectedItem();
                            Item itemToUp = Item.getCurrentItem();
                                        
                            itemToUp.setResourceID(itemID);
                            itemToUp.setDescription(description);
                            itemToUp.setQuantity(quantity);
                            itemToUp.setCost(cost);
                            itemToUp.setStatus(status);
                                SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                String updatedDate = dt.format(dt.parse(date.getValue().toString()));
                            itemToUp.setUpdatedOn(updatedDate);

                            if(ItemDao.updateItem(itemToUp)) {
                                System.out.println(Strings.itemUpdated);
                                    Stage stage = (Stage) fxchangeitemOKbtn.getScene().getWindow();
                                    stage.close();

                                ItemTableUI.hideStage();
                                ItemTableUI.setUndoFlagUpdate();
                                ItemTableUI.setContext(Strings.context_update);
                                System.out.println(Strings.itemChanged);
                                ItemTableUI.setUpdatedFlagLast();
                                ItemTableUI.getInstance().display(ItemTableUI.getStage());
                            }
                            else {
                                System.out.println(Strings.itemNotChanged);
                                    Stage stage = (Stage) fxchangeitemOKbtn.getScene().getWindow();
                                    stage.close();
                            }
                        }
                    }
                }
            }
        }
        
    }
    
    @FXML
    public void loadRemoveItem(ActionEvent event) {
        fxhiddenvbox.setVisible(true);
    }
    
    @FXML
    public void changeItem(ActionEvent event) throws IOException {
        String itemID = fxitemidlbl.getText().trim();
        
        if(ItemDao.removeItem(itemID)) {
            System.out.println(Strings.itemUpdated);
                Stage stage = (Stage) fxchangeitemOKbtn.getScene().getWindow();
                stage.close();

            ItemTableUI.hideStage();
            ItemTableUI.setUndoFlagUpdate();
            ItemTableUI.setContext(Strings.context_remove);
            System.out.println(Strings.itemRemoved);
            ItemTableUI.getInstance().display(ItemTableUI.getStage());
        }
        else {
            System.out.println(Strings.itemNotChanged);
                Stage stage = (Stage) fxchangeitemOKbtn.getScene().getWindow();
                stage.close();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set ItemID
        fxitemidlbl.setText(currItem.getResourceID());
        
        // set item description
        fxdescriptiontxt.setText(currItem.getDescription());
        
        // set item quantity
        fxquantitytxt.setText(currItem.getQuantity());
        
        // get current date
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        // set current date as updated Date
        fxdateupdateddtpickr.setValue(localDate);
        
        // set cost for the item
        fxcosttxt.setText(currItem.getCost());
        
        // set statuses
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.status_Expired,Strings.status_NEW,Strings.status_Repairable,Strings.status_Replacable,Strings.status_Using);
        fxstatuscmbbx.setItems(oblistA);
        
        // set curr status
        String status = currItem.getStatus();
        fxstatuscmbbx.setValue(status);
        
        // set warning clear
        fxwarninglbl.setText("");
        
        // hide vbox
        fxhiddenvbox.setVisible(false);
    }    
    
}
