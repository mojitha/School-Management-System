package com.attendance;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

public class AttendanceClassesMarkedController implements Initializable {
    @FXML
    public ListView fxclasseslstview;
    
    // gets local date as default
    public static final LocalDate getLocalDate(){
        String date = new SimpleDateFormat("dd-MM-yyyy").format(Calendar.getInstance().getTime());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate localDate = LocalDate.parse(date,formatter);
        
        return localDate;
    }
            
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // set classes list
        fxclasseslstview.setItems(AttendanceDao.getClassesThatNotYetMarked(getLocalDate()));
        
    }    
    
}
