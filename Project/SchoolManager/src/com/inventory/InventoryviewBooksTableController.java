package com.inventory;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeOut;
import com.book.Book;
import com.book.BookDao;
import com.connection.ValidateSM;
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
import javafx.stage.Stage;

public class InventoryviewBooksTableController implements Initializable {
    @FXML
    public Button fxaddbookbtn,fxundobtn,fxitemsbtn;
    public ComboBox fxcategorycmbbx,fxpricecmbbx,fxlangugecmbbx,fxcopiescmbbx;
    public Label fxwarninglbl;
    public TextField fxsearchbookstxt;
    public TableView fxbookstable;
    public TableColumn<Book, String> fxbookidtbcl;
    public TableColumn<Book, String> fxtitletbcl;
    public TableColumn<Book, String> fxlanguagetbcl;
    public TableColumn<Book, String> fxcategorytbcl;
    public TableColumn<Book, String> fxauthortbcl;
    public TableColumn<Book, String> fxpricetbcl;
    public TableColumn<Book, String> fxupdatedontbcl;
    public TableColumn<Book, String> fxcopiestbcl;
    
    ObservableList<Book> bookslist = FXCollections.observableArrayList();
    
    // loads add book window
    @FXML
    public void loadAddBook(ActionEvent event) throws IOException {
        System.out.println(Strings.clicked_addBook);
        
        AddBookUI addbookinstance = AddBookUI.getInstance();
        Stage addbookstage = new Stage();
        addbookinstance.display(addbookstage);
    }
    
    @FXML
    public void searchBooks(ActionEvent event) {
        fxbookstable.getItems().clear();
        String keyword = fxsearchbookstxt.getText().trim();
        if(!fxsearchbookstxt.getText().isEmpty()) {
            fxbookstable.setPlaceholder(new Label(Strings.tablePlaceholderDefault));
            
            bookslist = BookDao.getBookList(keyword);
            
            if(!bookslist.isEmpty()) {
                fxbookstable.setItems(bookslist);
                animateNode(fxbookstable,1);
            }
            else
                fxbookstable.setPlaceholder(new Label(Strings.tablePlaceholderNoBooksFound));
        }
        else {
            fxbookstable.setPlaceholder(new Label(Strings.tablePlaceholderNoBooksFound));
            fxwarninglbl.setText(Strings.notEnteredSearchBook);
            animateNode(fxwarninglbl, 1);
        }
    }
    
    // refreshes table when due
    public void refreshTable() {
        bookslist.clear();
        //itemslist = ItemDao.getItemList();
        fxbookstable.setItems(bookslist);
        animateNode(fxbookstable,1);
    }
    
    @FXML
    public void findBooks(ActionEvent event) {
        SingleSelectionModel<String> selCategory = fxcategorycmbbx.getSelectionModel();
        SingleSelectionModel<String> selPrice = fxpricecmbbx.getSelectionModel();
        SingleSelectionModel<String> selLanguage = fxlangugecmbbx.getSelectionModel();
        SingleSelectionModel<String> selCopies = fxcopiescmbbx.getSelectionModel();
        
            if(ValidateSM.isCombo(selCategory, Strings.invalidCategories, fxwarninglbl)) {
                if(ValidateSM.isCombo(selPrice, Strings.invalidPrice, fxwarninglbl)) {
                    if(ValidateSM.isCombo(selLanguage, Strings.invalidLanguage, fxwarninglbl)) {
                        if(ValidateSM.isCombo(selCopies, Strings.invalidCopies, fxwarninglbl)) {
                            String category = selCategory.getSelectedItem();
                            String price = selCategory.getSelectedItem();
                            String language = selCategory.getSelectedItem();
                            String copies = selCategory.getSelectedItem();
                            
                            if(!bookslist.isEmpty())
                                bookslist.clear();
                            
                            bookslist = BookDao.loadBooks(category,price,language,copies);
                            fxbookstable.setItems(bookslist);
                            
                            animateNode(fxwarninglbl, 0);
                            animateNode(fxbookstable, 1);
                        }
                    }
                }
            }
        
    }
    
    // undoes added book
    @FXML
    public void removelast(ActionEvent event) {
        String context = BookTableUI.getContext();
        
        switch (context) {
            case Strings.context_insert:
                if(BookDao.removeLast()) {
                    System.out.println(Strings.bookRemoved);
                    refreshTable();   // do not refresh the table
                    BookTableUI.setUndoFlagInsert();
                    fxundobtn.setDisable(true);
                    setLabelInTable(fxbookstable, Strings.bookRemoved);
                }
                else
                    System.out.println(Strings.bookNotRemoved);
                break;
        }
    }
    
    public ObservableList<String> getPrice() {
        ObservableList<String> list = FXCollections.observableArrayList();
        double price = 0;
        String sPrice = "";
        
        for(double i=100;i<=10000;i=i+100) {
            price += i;
            sPrice = i+"";
            list.add(sPrice);
        }
        
        return list;
    }
    
    public ObservableList<String> getCopies() {
        ObservableList<String> list = FXCollections.observableArrayList();
        int copies = 0;
        String sCopies = "";
        
        for(int i=0;i<=50;i++) {
            copies += i;
            sCopies = i+"";
            list.add(sCopies);
        }
        
        return list;
    }
    
    // label setter
    public void setLabelInTable(TableView fxTable, String labelText) {
        fxTable.setPlaceholder(new Label(labelText));
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
    
    // loads item window when called
    @FXML
    public void loadItems(ActionEvent event) throws IOException {
        ItemTableUI itemtableuiinstance = ItemTableUI.getInstance();
        Stage itemtableuistage = new Stage();
        itemtableuiinstance.display(itemtableuistage);
        
        Stage booksStage = BookTableUI.getStage();
        booksStage.hide();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set book table
        fxbookidtbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.resourceID));
        fxtitletbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.title));
        fxlanguagetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.language));
        fxcategorytbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.category));
        fxauthortbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.author));
        fxpricetbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.price));
        fxupdatedontbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.updatedOn));
        fxcopiestbcl.setCellValueFactory(new PropertyValueFactory<>(Strings.copies));
    
        // sets placeholder text of the books table
        if(BookTableUI.getContext() == null)
            setLabelInTable(fxbookstable, Strings.tablePlaceholderDefault);
        else {
            switch (BookTableUI.getContext()) {
                case Strings.context_insert:
                    setLabelInTable(fxbookstable, Strings.bookAdded);
                    break;
            }
        }
        
        // check Undo Buttons when loading
        if(BookTableUI.getUndoFlagInsert()== 1)
            fxundobtn.setDisable(false);
        
        // set categories
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(BookDao.getCategories());
        fxcategorycmbbx.setItems(oblistA);
        
        // set price
        ObservableList<String> oblistB = FXCollections.observableArrayList();
        oblistB.addAll(getPrice());
        fxpricecmbbx.setItems(oblistB);
        
        // set language
        ObservableList<String> oblistC = FXCollections.observableArrayList();
        oblistC.addAll(BookDao.getLanguage());
        fxlangugecmbbx.setItems(oblistC);
        
        // set copies
        ObservableList<String> oblistD = FXCollections.observableArrayList();
        oblistD.addAll(getCopies());
        fxcopiescmbbx.setItems(oblistD);
        
        // loads the last added book in the table
        if(BookTableUI.getLoadFlagLast() == 1) {
            ObservableList<Book> oblist = BookDao.getLastBook();
            fxbookstable.setItems(oblist);
            animateNode(fxbookstable, 1);
            BookTableUI.setLoadFlagLast();
        }
        
        // set warning as null
        fxwarninglbl.setText("");
    }    
    
    
}
