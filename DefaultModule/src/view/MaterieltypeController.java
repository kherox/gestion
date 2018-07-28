package view;

import database.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;

/**
 * Created by Dubai on 9/21/2017.
 */
public class MaterieltypeController {

    @FXML
    private TextField name;
    @FXML
    private Button valider;

    private MainApp mainApp;
    private Stage stage;


    public void setMainApp(MainApp mainApp){
        this.mainApp = mainApp;
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!name.getText().isEmpty()){
                    mainApp.mapObject = ConnectionManager.getR().hashMap("name",name.getText());
                    mainApp.manager.__save(mainApp.mapObject,"materiel_types");
                    mainApp.closeCurrentWindow(stage);
                }

            }
        });
    }

    public void setName(String name) {
        this.name.setText(name);;
    }
}
