package com.inventory;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.item.Item;
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
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class InventoryviewItemsTableController implements Initializable {
    // items list
    ObservableList<Item> itemslist = FXCollections.observableArrayList();
    
    // a warning flag (change item)
    int warningFlagCI = 0;
    
    // a warning flag (search item)
    int warningFlagSI = 0;
    
    // add item button
    @FXML 
    public Button fxadditembtn,fxgobtn,fxundobtn,fxbooksbtn,fxchangestatus;
    public ComboBox fxquantitycmbbx,fxunitcmbbx,fxcategorycmbbx,fxstatuscmbbx;
    public ComboBox<Integer> fxnthcmbox;
    public TextField fxsearchitemstxt;
    public Label fxwarninglbl;
    
    // inventoryviewItemTable
    @FXML
    public TableView<Item> fxitemstable;
    public TableColumn<Item, String> fxitemidtbcl;
    public TableColumn<Item, String> fxdescriptiontbcl;
    public TableColumn<Item, String> fxsupplydatetbcl;
    public TableColumn<Item, String> fxupdatedontbcl;
    public TableColumn<Item, String> fxstatustbcl;
    public TableColumn<Item, String> fxquantitytbcl;
    public TableColumn<Item, String> fxcosttbcl;
    public TableColumn<Item, String> fxexpiredatetbcl;
    
    // adds item to the system
    @FXML
    public void addItem(ActionEvent event) throws IOException {
        AddItemUI additeminstance = AddItemUI.getInstance();
        Stage additemstage = new Stage();
        additeminstance.display(additemstage);
    }
    
    // load items on status
    @FXML
    public void loadItemsOnStatus(ActionEvent event) {
        SingleSelectionModel<String> sel = fxstatuscmbbx.getSelectionModel();
        ObservableList<Item> oblist = ItemDao.getItemListOnStatus(sel.getSelectedItem());
        fxitemstable.setItems(oblist);
        animateNode(fxitemstable,1);
    }
    
    // load items on units
    @FXML
    public void loadItemsOnUnits(ActionEvent event) {
        SingleSelectionModel<String> sel = fxunitcmbbx.getSelectionModel();
        ObservableList<Item> oblist = ItemDao.getItemListOnUnits(sel.getSelectedItem());
        fxitemstable.setItems(oblist);
        animateNode(fxitemstable, 1);
    }
    
    // load items on categories
    @FXML
    public void loadItemsOnCategories(ActionEvent event) throws IOException {
        SingleSelectionModel<String> sel = fxcategorycmbbx.getSelectionModel();
        ObservableList<Item> oblist = ItemDao.getItemListOnCategories(sel.getSelectedItem());
        fxitemstable.setItems(oblist);
        animateNode(fxitemstable,1);
    }
    
    // loads quantity range
    @FXML 
    public void loadItemsOnQuantities(ActionEvent event) {
        SingleSelectionModel<String> sel = fxquantitycmbbx.getSelectionModel();
        int quanIndRange = sel.getSelectedIndex();
        System.out.println(sel.getSelectedItem());
        for(int i=0;i<9;i++) {
            if(i == quanIndRange) {
                int n = 0;
                if(fxnthcmbox.getSelectionModel().isEmpty())
                    n = 10;
                else {
                    SingleSelectionModel<Integer> N = fxnthcmbox.getSelectionModel();
                    n = N.getSelectedItem();
                }
                
                ObservableList<Item> oblist = ItemDao.getItemListOnQuantities( (++i*n),(++i*n));
                fxitemstable.setItems(oblist);
                animateNode(fxitemstable,1);
                break;
            }
        }
    }
    
    // loads change item ui
    public void loadChangeItem(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_changeItemStatus);
        
        TableView.TableViewSelectionModel<Item> selRow = fxitemstable.getSelectionModel();
        
        // checks if a table row is selected
        if(!selRow.isEmpty()) {
            // saves the selected item first
            Item tableItem = selRow.getSelectedItem();
            String itemID = tableItem.getResourceID();
            ItemDao.setCurrentItem(itemID);
            
            // set a backup of the current item
            ChangeItemUI.setOldItem(tableItem);
            System.out.println(Item.getCurrentItem().getStatus());
            
            // loads change item UI
            ChangeItemUI changeitemuiinstance = ChangeItemUI.getInstance();
            Stage changeitemuistage = new Stage();
            changeitemuiinstance.display(changeitemuistage);
        }
        else {
            warningFlagCI = 1;
            fxwarninglbl.setText(Strings.notSelectedItem);
            animateNode(fxwarninglbl,1);
        }
    }
    
    // clear warning
    @FXML
    public void clearWarning(MouseEvent event) {
        if(warningFlagCI == 1) {
            animateNode(fxwarninglbl,0);
            warningFlagCI = 0;
        }
    }
    
    // clear warning search
    @FXML
    public void clearWarningSearch(KeyEvent event) {
        if(warningFlagSI == 1) {
            animateNode(fxwarninglbl,0);
            warningFlagSI = 0;
        }
    }
    
    // search for item(s)
    @FXML
    public void searchItem(ActionEvent event) {
        // clears table
        fxitemstable.getItems().clear();
        
        // gets the keyword
        String keyword = fxsearchitemstxt.getText().trim();
        
        // searches for the user sorted items
        if(!fxsearchitemstxt.getText().isEmpty()) {
            fxitemstable.setPlaceholder(new Label(Strings.tablePlaceholderDefault));
            
            itemslist = ItemDao.getItemList(keyword); // customized view
            
            if(!itemslist.isEmpty()) {
                fxitemstable.setItems(itemslist);
                animateNode(fxitemstable,1);
            }
            else
                fxitemstable.setPlaceholder(new Label(Strings.tablePlaceholderNoItemsFound));
        }
        else {
            fxitemstable.setPlaceholder(new Label(Strings.tablePlaceholderNoItemsFound));
            fxwarninglbl.setText(Strings.notEnteredSearch);
            animateNode(fxwarninglbl, 1);
            warningFlagSI = 1;
        }
    }
    
    // undo changed or added item
    @FXML
    public void undoLast(ActionEvent event) throws IOException {
        String context = ItemTableUI.getContext();
        
        switch (context) {
            case Strings.context_insert:
                if(ItemDao.removeLast()) {
                    // <-message that showed to the inventory manager is here->
                    System.out.println(Strings.itemRemoved);
                    refreshTable();   // do not refresh the table
                    ItemTableUI.setUndoFlagInsert();
                    fxundobtn.setDisable(true);
                    setLabelInTable(fxitemstable, Strings.itemRemoved);
                }
                else
                    System.out.println(Strings.itemNotRemoved);
                break;
                
            case Strings.context_update:
                Item olItem = ChangeItemUI.getOldItem();
                String olItemID = olItem.getResourceID();
                
                System.out.println(olItem.getResourceID()+" "+olItem.getStatus());
                
                if(ItemDao.updateItem(olItem)) {
                    System.out.println(Strings.itemResetDone);
                    refreshTable();   // do not refresh the table
                    
                    if(!fxundobtn.isDisable()) {
                        ItemTableUI.setUndoFlagUpdate();
                        fxundobtn.setDisable(true);
                    }
                        
                    setLabelInTable(fxitemstable, Strings.itemResetDone);
                    
                    ObservableList<Item> oblist = ItemDao.getItem(olItemID);
                    fxitemstable.setItems(oblist);
                    animateNode(fxitemstable, 1);
                }
                else
                    System.out.println(Strings.itemNotReset);
                break;
        }
    }
    
    // refreshes table when due
    public void refreshTable() {
        itemslist.clear();
        //itemslist = ItemDao.getItemList();
        fxitemstable.setItems(itemslist);
        animateNode(fxitemstable,1);
    }
    
    // loads books table
    @FXML
    public void loadBooks(ActionEvent event) throws IOException {
        BookTableUI booktableuiinstance = BookTableUI.getInstance();
        Stage booktableuistage = new Stage();
        booktableuiinstance.display(booktableuistage);
        
        Stage itemsStage = ItemTableUI.getStage();
        itemsStage.hide();
    }
    
    // clicked to change quantity group
    @FXML
    public void clickedNth(ActionEvent event) {
        SingleSelectionModel<Integer> N = fxnthcmbox.getSelectionModel();
        int n = N.getSelectedItem();
        fxquantitycmbbx.setItems(switchQuantity(n));
    }
        
    // generates a range for quantities
    public static ObservableList<String> switchQuantity(int n) {
        ObservableList<String> quantitylist = FXCollections.observableArrayList();
        for(int i=n;i<n*10;i=i+n)
            quantitylist.add(i+" AND "+(i+n));
        
        System.out.println(quantitylist);
        
        return quantitylist;
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
    
    // checks if category added or changed and loads the combo box again
    public void changeCategories() {
        if(ItemTableUI.getCategoryFlagChange() == 1) {
            ObservableList<String> oblist = ItemDao.getCategories();
            fxcategorycmbbx.setItems(oblist);
            animateNode(fxcategorycmbbx, 1);
            ItemTableUI.setCategoryFlagChange();
        }
    }
    
    // label setter
    public void setLabelInTable(TableView fxTable, String labelText) {
        fxTable.setPlaceholder(new Label(labelText));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // initialize the items table
        try {
            fxitemidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.resourceID));
            fxdescriptiontbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.description));
            fxsupplydatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.supplyDate));
            fxupdatedontbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.updatedOn));
            fxstatustbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.status));
            fxquantitytbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.quantity));
            fxcosttbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.cost));
            fxexpiredatetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.expireDate));
            
            //refreshTable();
        } 
        catch (Exception e) {
            System.out.println(e);
        }
        
        // check Undo Buttons when loading
        if(ItemTableUI.getUndoFlagInsert()== 1)
            fxundobtn.setDisable(false);
        if(ItemTableUI.getUndoFlagUpdate()== 1)
            fxundobtn.setDisable(false);
        
        // sets sortable by status combobox
        ObservableList<String> statuslist = FXCollections.observableArrayList();
        statuslist.addAll(Strings.status_NEW,Strings.status_Using,Strings.status_Expired,Strings.status_Repairable,Strings.status_Replacable);
        fxstatuscmbbx.setItems(statuslist);
        
        // set units in combo box
        ObservableList<String> unitlist = FXCollections.observableArrayList();
        unitlist.addAll(ItemDao.getUnits());
        fxunitcmbbx.setItems(unitlist);
        
        // set units in nth box
        ObservableList<Integer> nthlist = FXCollections.observableArrayList();
        nthlist.addAll(1,10,100,1000,10000);
        fxnthcmbox.setItems(nthlist);
        
        // set quantities initially in combo box
        fxquantitycmbbx.setItems(switchQuantity(10));
        
        // set categories in combo box
        ObservableList<String> categorylist = FXCollections.observableArrayList();
        categorylist.addAll(ItemDao.getCategories());
        fxcategorycmbbx.setItems(categorylist);
        
        // sets placeholder text of the items table
        if(ItemTableUI.getContext() == null)
            setLabelInTable(fxitemstable, Strings.tablePlaceholderDefault);
        else {
            switch (ItemTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxitemstable, Strings.itemAdded);
                    changeCategories();
                    break;
                case Strings.context_update:
                    setLabelInTable(fxitemstable, Strings.itemChanged);
                    break;
                case Strings.context_remove:
                    setLabelInTable(fxitemstable, Strings.itemRemoved);
                    break;
            }
        }
        
        // loads the last added item in the table
        if(ItemTableUI.getLoadFlagLast() == 1) {
            ObservableList<Item> oblist = ItemDao.getLastItem();
            fxitemstable.setItems(oblist);
            animateNode(fxitemstable, 1);
            ItemTableUI.setLoadFlagLast();
        }
            
        // loads the updated item in the table
        if(ItemTableUI.getUpdatedFlagLast() == 1) {
            String itemID = ChangeItemUI.getOldItem().getResourceID();
            ObservableList<Item> oblist = ItemDao.getItem(itemID);
            fxitemstable.setItems(oblist);
            animateNode(fxitemstable, 1);
            ItemTableUI.setUpdatedFlagLast();
        }
        
        // sets warning label empty
        fxwarninglbl.setText("");
    }
}
