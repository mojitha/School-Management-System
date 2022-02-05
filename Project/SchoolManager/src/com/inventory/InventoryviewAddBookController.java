package com.inventory;

import animatefx.animation.FadeIn;
import com.book.Book;
import com.book.BookDao;
import com.connection.IdentitySM;
import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InventoryviewAddBookController implements Initializable {
    @FXML
    public TextField fxtitletxt,fxlanguagetxt,fxauthortxt,fxpricetxt,fxcopiestxt;
    public DatePicker fxdatereceiveddtpickr;
    public ComboBox fxbookcategoriescmbbx;
    public Label fxbookIdlbl,fxwarninglbl;
    
    // buttons for cancel and adding book
    @FXML
    public Button fxaddbookOKbtn,fxaddbookcancelbtn;
    
    // cancels adding a book
    @FXML
    public void closeAddBook(ActionEvent event) {
        System.out.println(Strings.clicked_cancel);
        
        Stage addbookstage = (Stage) fxaddbookcancelbtn.getScene().getWindow();
        addbookstage.close();
    }

    // adding a book
    @FXML
    public void okAddBook(ActionEvent event) throws ParseException, IOException {
        System.out.println(Strings.clicked_addBook);
        
        String bookID = fxbookIdlbl.getText();
        String title = fxtitletxt.getText().trim();
        String language = fxlanguagetxt.getText().trim();
        String author = fxauthortxt.getText().trim();
        String price = fxpricetxt.getText().trim();
        String copies = fxcopiestxt.getText().trim();
        SingleSelectionModel<String> category = fxbookcategoriescmbbx.getSelectionModel();
        DatePicker date = fxdatereceiveddtpickr;
        
        // validates if all item details are given by inventory manager correctly
        if(validateText(bookID, 10, Strings.invalidBookID)) {
            if(validateText(title, 50, Strings.invalidTitle)) {
                if(validateText(language, 30, Strings.invalidLanguage)) {
                    if(isCombo(category,Strings.notSelectedCategory)) {
                        if(validateText(author, 100, Strings.invalidAuthor)) {
                            if(validateNumber(price, Strings.invalidPrice)) {
                                if(checkDate(date, Strings.notSelectedDate)) { 
                                    if(validateNumber(copies, Strings.invalidCopies)) {
                                        String bookCategory = category.getSelectedItem();
                                            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                                            String supplyDate = dt.format(dt.parse(date.getValue().toString()));
                                        
                                        Book currBook = Book.getInstance();
                                        
                                        currBook.setResourceID(bookID);
                                        currBook.setTitle(title);
                                        currBook.setLanguage(language);
                                        currBook.setCategory(bookCategory);
                                        currBook.setAuthor(author);
                                        currBook.setPrice(price);
                                        currBook.setUpdatedOn(supplyDate);
                                        currBook.setCopies(copies);
                                        
                                        if(BookDao.addBook(currBook)) {
                                            System.out.println(Strings.bookAdded);
                                                Stage stage = (Stage) fxaddbookOKbtn.getScene().getWindow();
                                                stage.close();
                                                
                                                // loads tableview with updated values
                                                BookTableUI.hideStage();
                                                BookTableUI.setUndoFlagInsert();
                                                BookTableUI.setContext(Strings.context_insert);
                                                BookTableUI.setLoadFlagLast();
                                                BookTableUI.getInstance().display(BookTableUI.getStage());
                                        }
                                        else {
                                            System.out.println(Strings.bookNotAdded);
                                                Stage stage = (Stage) fxaddbookOKbtn.getScene().getWindow();
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
    
    // check category selected
    public boolean isCombo(SingleSelectionModel<String> option, String message) {
        if(option.isEmpty()) {
            fxwarninglbl.setText(Strings.notSelectedCategory);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // checks the validity of the date 
    public boolean checkDate(DatePicker date, String message) {
        if(date.getValue() == null) {
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
        else
            return true;
    }
    
    // validates for a numeric non negative value
    public boolean validateNumber(String number, String message) {
        try {
            if(number.isEmpty()) {
                fxwarninglbl.setText(message);
                new FadeIn(fxwarninglbl).play();
                return false;
            }
            else return Double.parseDouble(number) >= 0;
        }
        catch(NumberFormatException nfe) {
            System.out.println(nfe);
            fxwarninglbl.setText(message);
            new FadeIn(fxwarninglbl).play();
            return false;
        }
    }
    
    // validates a text value
    public boolean validateText(String textInput, int charCount, String message) {
        if(textInput.length() > charCount ||  textInput.isEmpty()) {
            fxwarninglbl.setText(message);
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
        fxbookIdlbl.setText(IdentitySM.getID(Strings.Book));
        
        // set categories in category combobox
        fxbookcategoriescmbbx.setItems(BookDao.getCategories());
        
        // set today as the received date
        fxdatereceiveddtpickr.setValue(getLocalDate());
        
        // clears the warning
        fxwarninglbl.setText("");
    }    
    
}
