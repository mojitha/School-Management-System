package com.inventory;

import com.string.Strings;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

public class InventoryviewSettingsController implements Initializable {
    @FXML
    public ToggleButton fxthemedefaultbtn,fxthemesecondarybtn,fxthemedarkbtn,fxthemesystembtn;

    @FXML
    public void changeUI(ActionEvent event) throws IOException {
        String themeUI = "";
        
            if(event.getSource().equals(fxthemedefaultbtn)) {
                System.out.println(Strings.themeDefault);
                themeUI = Strings.themeDefaultStyleInventory;
            }
            else if(event.getSource().equals(fxthemesecondarybtn)) {
                System.out.println(Strings.themeSecondary);
                themeUI = Strings.themeSecondaryStyleInventory;
            }
            else if(event.getSource().equals(fxthemesystembtn)) {
                System.out.println(Strings.themeSystem);
                themeUI = Strings.themeSystemStyleInventory;
            }
            else if(event.getSource().equals(fxthemedarkbtn)) {
                System.out.println(Strings.themeDark);
                themeUI = Strings.themeDarkStyleInventory;
            }
        
        InventoryUI.setTheme(themeUI);
        InventoryUI.setThemeChagngedFlag();
        InventoryUI.getStage().close();
        InventoryUI.getInstance().display(InventoryUI.getStage());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
