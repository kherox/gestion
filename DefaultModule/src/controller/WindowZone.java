package controller;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class WindowZone{


    public  WindowZone(String title, String content, String header, boolean state,Stage stage){
        Alert alert;
        if (state)
            alert = new Alert(Alert.AlertType.ERROR);
        else
            alert = new Alert(Alert.AlertType.INFORMATION);

        alert.initOwner(stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

}