package view;

import com.rethinkdb.net.Cursor;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 16/11/2016.
 */
public class PreodreTravailController {

    @FXML
    private ComboBox projectList;
    @FXML
    private TextField client_bon;
    @FXML
    private Button validate;
    private MainApp mainApp;
    private Stage stage;
    private HashMap hashMap = new HashMap();
    private HashMap idmap = new HashMap();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Cursor cursor = this.mainApp.manager.FinishProject("projects");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            projectList.getItems().add(map.get("name"));
            hashMap.put(map.get("name"),map);
            idmap.put(map.get("name"),map.get("id"));
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){

        client_bon.setVisible(false);
        validate.setVisible(false);
        projectList.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HashMap map = (HashMap) hashMap.get(newValue.toString());
                if (map.containsKey("num_bon_client"))
                    try {
                        mainApp.OrderTravailAction();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                else
                    try {
                        validate.setVisible(true);
                        client_bon.setVisible(true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
        });
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.mapObject = ConnectionManager.getR().hashMap("name",projectList.getValue()).with("num_bon_client",client_bon.getText());
                mainApp.manager.UpdateData(mainApp.mapObject,"projects",idmap.get(projectList.getValue()).toString());
                try {
                    mainApp.OrderTravailAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mainApp.closeCurrentWindow(stage);
            }
        });
    }


}
