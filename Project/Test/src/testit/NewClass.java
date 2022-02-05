package testit;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class NewClass {
    public static void main(String[] args) {
        switchQuantity(1000);
    }
    public static void switchQuantity(int n) {
        ObservableList<String> quantitylist = FXCollections.observableArrayList();
        for(int i=n;i<n*10;i=i+n)
            quantitylist.add(i+" AND "+(i+n));
        
        System.out.println(quantitylist);
    }
}
