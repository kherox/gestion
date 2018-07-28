package view;

import com.rethinkdb.net.Cursor;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import main.MainApp;
import model.Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 08/12/2016.
 */
public class RevisionController {


    @FXML
    private ComboBox projectlist;
    @FXML
    private Button validate;

    HashMap hashMap = new HashMap();
    HashMap idmap   = new HashMap();

    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor = this.mainApp.manager.FinishProject("projects");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            projectlist.getItems().add(map.get("name"));
            hashMap.put(map.get("name"),map);
            idmap.put(map.get("name"),map.get("id"));
        }


    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        validate.setVisible(false);
        projectlist.getItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                validate.setVisible(true);
            }
        });
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    mainApp.RevisionListAction((HashMap) hashMap.get(projectlist.getValue().toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        projectlist.valueProperty().addListener((observable, oldValue, newValue) -> {});
    }
}
