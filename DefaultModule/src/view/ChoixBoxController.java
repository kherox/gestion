package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.MainApp;

/**
 * Created by Univetech Sarl on 07/11/2016.
 */
public class ChoixBoxController {
    @FXML
    private Button devis_simple;
    @FXML
    private Button devis_projet;
    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        devis_projet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainApp.InitProjectAction(stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        devis_simple.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainApp.DevisAction(null,stage, false, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
