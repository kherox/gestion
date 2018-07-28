package view;

import database.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import main.MainApp;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Univetech Sarl on 16/12/2016.
 */
public class RevisionListController {

    @FXML
    private Label projectname;
    @FXML
    private Label projectville;
    @FXML
    private Label projectdescription;
    @FXML
    private Label projectclient;
    @FXML
    private Label projectrepresentant;
    @FXML
    private Label projectclientcontact;
    @FXML
    private Button validate;

    private MainApp mainApp;
    private Stage stage;
    private HashMap hashMap = new HashMap();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setHashMap(HashMap hashMap) {
        this.hashMap = hashMap;

        ArrayList arrayList = (ArrayList)hashMap.get("devis_map");
        HashMap map = (HashMap) arrayList.get(0);
        projectclient.setText(map.get("clientname").toString());
        projectname.setText(this.hashMap.get("name").toString());
        projectclientcontact.setText(map.get("clienttelephone").toString());
        projectville.setText(this.hashMap.get("ville").toString());
    }

    @FXML
    private void initialize(){
         validate.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                 alert.initOwner(stage);
                 alert.setTitle("Revision");
                 alert.setHeaderText("Validation");
                 alert.setContentText("Voulez-vous vraiment activez une revision our ce devis ? Cette operation est irrevesible");
                 alert.showAndWait();

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r.hashMap("name",hashMap.get("name").toString())
                             .with("revision",true).with("devis",false).with("state",1
                             );
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",hashMap.get("id").toString());
                     mainApp.mapObject = ConnectionManager.getR().hashMap("name",hashMap.get("name").toString())
                             .with("data_map",hashMap).with("project_ref",hashMap.get("id"));
                     mainApp.manager.SaveData(mainApp.mapObject,"revisions");

                 }
             }
         });
    }
}
