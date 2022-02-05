package com.result;

import animatefx.animation.FadeIn;
import com.string.Strings;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ResultTableUI {
    private static ResultTableUI resultTableUISingleton;
    private static Stage stage;
    private static String context;
    
    public static ResultTableUI getInstance() {
        if(resultTableUISingleton == null) {
            synchronized(ResultTableUI.class) {
                resultTableUISingleton = new ResultTableUI();
                System.out.println(Strings.created_resultTableUISingleton);
            }
        }
    
        return resultTableUISingleton;
    }

    public static Stage getStage() {
        return stage;
    }

    public static String getContext() {
        return context;
    }

    public static void setContext(String context) {
        ResultTableUI.context = context;
    }
    
    public static void setStage(Stage stage) {
        ResultTableUI.stage = stage;
    }

    public void display(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ResultTable.fxml"));
        Scene scene = new Scene(root);

        stage.getIcons().add(new Image(Strings.resultsIconURL));
        stage.setTitle(Strings.titles_resultTable);
        stage.setScene(scene);
        stage.show();

        setStage(stage);

        // hides the resultview 
        Stage parentStage = ResultUI.getStage();
        parentStage.hide();
        getStage().setOnCloseRequest(e->parentStage.show());

        // animate the stage
        new FadeIn(root).play();
    }
    
    
    
}
