package view;

import com.rethinkdb.net.Cursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MainApp;
import model.Operations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 03/11/2016.
 */
public class OperationsController {
    @FXML
    private TableView<Operations> operationTableView;
    @FXML
    private TableColumn<Operations,String> name;
    @FXML
    private TableColumn<Operations,String> created;
    @FXML
    private TableColumn<Operations,String> operatorname;
    @FXML
    private TableColumn<Operations,String> username;
    @FXML
    private TableColumn<Operations,String> client;
    @FXML
    private TableColumn<Operations,String> id;
    @FXML
    private TableColumn<Operations,String> code;

    private ObservableList<Operations> observableList = FXCollections.observableArrayList();

    private Stage stage;
    private MainApp mainApp;

    boolean isproject = false;
    private HashMap devistate = new HashMap();



    public void setStage(Stage stage) {
        this.stage = stage;

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Cursor cursor =  this.mainApp.manager.AllFinishProject("devis_options");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap obj = ( HashMap) iterator.next();
            ArrayList d = ( ArrayList) obj.get("clientmap");
            HashMap m = ( HashMap) d.get(0);


            this.observableList.add(
                    new Operations(m.get("proformaName").toString(),m.get("clientname").toString()
                            ,m.get("currentdate").toString(),obj.get("user").toString(),
                            obj.get("username").toString(),m.get("proformaNumero").toString(),
                            obj.get("id").toString()
                    )
            );

             boolean object = false;
            try{
                 object = (Boolean) obj.get("devis_project");
            }catch(NullPointerException as){}

            String simple;
            if (object) {
                isproject = true;
                simple = "Projet";
            }
            else{
                simple = "Devis";
                isproject = false;
            }


            devistate.put(obj.get("id").toString(),isproject);


        }

    }

    @FXML
    private void initialize(){

        name.setCellValueFactory(param -> param.getValue().nameProperty());
        operatorname.setCellValueFactory(param -> param.getValue().opertornameProperty());
        created.setCellValueFactory(param -> param.getValue().createdProperty());
        client.setCellValueFactory(param -> param.getValue().clientProperty());
        code.setCellValueFactory(param -> param.getValue().codeProperty());
        id.setCellValueFactory(param -> param.getValue().idProperty());

        this.operationTableView.setRowFactory(param -> {
            TableRow<Operations> p = new TableRow<Operations>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    Operations operations = p.getItem();
                    try {

                         boolean isproject = (boolean) devistate.get(operations.getId());
                        mainApp.viewPrintOperation(null,null,null,operations,stage,null, isproject);
                       // mainApp.ImpressionAction(null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return p;
        });
       this.operationTableView.setItems(this.observableList);
    }

}
