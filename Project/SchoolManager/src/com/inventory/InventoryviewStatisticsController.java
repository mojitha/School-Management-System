package com.inventory;

import animatefx.animation.FadeIn;
import com.book.BookDao;
import com.connection.ValidateSM;
import com.item.ItemDao;
import com.string.Strings;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;

public class InventoryviewStatisticsController implements Initializable {
    @FXML
    public ComboBox fxmonthcmbbx;
    public Label fxyearlbl,fxitemscountlbl,fxbookscountlbl,fxmonthlbl,fxmonthlyexpensesitemslbl,fxmonthlyexpensesbookslbl,fxwarninglbl;
    
    @FXML
    public void setMonth() {
        SingleSelectionModel<String> selMonth = fxmonthcmbbx.getSelectionModel();
        String month = selMonth.getSelectedItem();
        fxmonthlbl.setText(month);
        new FadeIn(fxmonthlbl).play();
        fxwarninglbl.setText("");
        getStats(null);
    }
    
    @FXML
    public void getStats(ActionEvent event) {
        SingleSelectionModel<String> selMonth = fxmonthcmbbx.getSelectionModel();
        String itemCost = "";
        String bookCost = "";
        String year = fxyearlbl.getText();
        
        if(ValidateSM.isCombo(selMonth, Strings.invalidMonth, fxwarninglbl)) {
            String month = selMonth.getSelectedItem();
            String monthInt = "";
            
            switch (month) {
                case Strings.month_jan:
                    monthInt = "01";
                    break;
                case Strings.month_feb:
                    monthInt = "02";
                    break;
                case Strings.month_mar:
                    monthInt = "03";
                    break;
                case Strings.month_apr:
                    monthInt = "04";
                    break;
                case Strings.month_may:
                    monthInt = "05";
                    break;
                case Strings.month_jun:
                    monthInt = "06";
                    break;
                case Strings.month_jul:
                    monthInt = "07";
                    break;
                case Strings.month_aug:
                    monthInt = "08";
                    break;
                case Strings.month_sep:
                    monthInt = "09";
                    break;
                case Strings.month_oct:
                    monthInt = "10";
                    break;
                case Strings.month_nov:
                    monthInt = "11";
                    break;
                case Strings.month_dec:
                    monthInt = "12";
                    break;
            }
            
            itemCost = ItemDao.getItemsCostPerMonth(monthInt,year);
            bookCost = BookDao.getBooksCostPerMonth(monthInt,year);
            
            fxmonthlyexpensesitemslbl.setText(itemCost);
            fxmonthlyexpensesbookslbl.setText(bookCost);
        }
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set year
        fxyearlbl.setText(LocalDate.now().getYear()+"");
        
        // set months
        ObservableList<String> oblistA = FXCollections.observableArrayList();
        oblistA.addAll(Strings.month_jan,Strings.month_feb,Strings.month_mar,Strings.month_apr,Strings.month_may,Strings.month_jun,Strings.month_aug,Strings.month_sep,Strings.month_oct,Strings.month_nov,Strings.month_dec);
        fxmonthcmbbx.setItems(oblistA);
        
        // set items count
        fxitemscountlbl.setText(ItemDao.getItemsCount());
        
        // set book count
        fxbookscountlbl.setText(BookDao.getBooksCount());
        
        // set warning to null
        fxwarninglbl.setText("");
    }    
    
}
