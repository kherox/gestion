package view;

import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import main.MainApp;
import model.Categories;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 20/01/2017.
 */
public class CategorisationEditController {

    private MainApp mainApp;
    private Stage stage;
    private Categories categories;

    @FXML
    TextArea zoneArea;
    @FXML
    Label libelle;
    @FXML
    Label abr;
    @FXML
    Button maj;
    private String content = "";

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setCategories(Categories categories) {
        this.categories = categories;

        com.rethinkdb.net.Cursor cursor =  this.mainApp.manager.getDataById("project_type",this.categories.getId());
        Iterator i = cursor.iterator();
        HashMap hasmap = (HashMap) cursor.next();
        libelle.setText(hasmap.get("name").toString());
        abr.setText(hasmap.get("abreviation").toString());
        //ArrayList hasArrayList = (ArrayList) (ArrayList) hasmap.get("datamap");
        zoneArea.setText(hasmap.get("datamap").toString());
        maj.setVisible(false);
    }

    @FXML
    private void initialize(){
        zoneArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                    maj.setVisible(true);
                content = newValue;
            }
        });

        maj.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.initOwner(stage);
                alert.setTitle("Validation de mise a jour");
                alert.setContentText("Voulez-vous vraiment sauvegarder cette information ??");
                alert.setHeaderText("Categorie Validation");
                alert.showAndWait();
                if (alert.getResult().getButtonData().isDefaultButton()){
                    mainApp.manager.deleteData("project_type",categories.getId());
                    mainApp.mapObject = ConnectionManager.getR().hashMap("name",libelle.getText())
                            .with("abreviation",abr.getText())
                            .with("datamap",ConnectionManager.r.array(content));
                    mainApp.manager.SaveData(mainApp.mapObject,"project_type");
                    mainApp.closeCurrentWindow(stage);
                }

            }
        });
    }
}
