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
import model.Devis;
import model.Operations;
import model.Project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 18/12/2016.
 */
public class RListeController {

    @FXML
    TableView<Devis> devisTableView;
    @FXML
    TableColumn<Devis,String> id;
    @FXML
    TableColumn<Devis,String> type;
    @FXML
    TableColumn<Devis,String> devis_name;
    @FXML
    TableColumn<Devis,String> client;
    @FXML
    TableColumn<Devis,String> created;
    @FXML
    TableColumn<Devis,Boolean> state;
    @FXML
    TableColumn<Devis,String> numero;

    HashMap<String ,Operations> mapOperations = new HashMap<>();

    ObservableList<Devis> devisliste = FXCollections.observableArrayList();

    private MainApp mainApp;
    private Stage stage;
    private boolean isproject = false;

    private HashMap devistate = new HashMap();




    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor =  this.mainApp.manager.AllFinishProject("devis_options");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap obj = ( HashMap) iterator.next();
            ArrayList d = ( ArrayList) obj.get("clientmap");
            HashMap map = ( HashMap) d.get(0);
            String projectRef =  "null";

            try{
                projectRef = obj.get("project_ref").toString();
            }catch(NullPointerException as){}
            boolean object = (Boolean) obj.get("devis_project");
            String simple;
            if (object) {
                isproject = true;
                simple = "Projet";
            }else {
                isproject = false;
                simple = "Simple";
            }
            devistate.put(obj.get("id").toString(),isproject);
            devisliste.add(
                    new Devis(
                            projectRef,
                            map.get("proformaName").toString(),
                            simple,
                            map.get("date_demande").toString(),
                            map.get("proformaNumero").toString(),
                            obj.get("id").toString(),
                            (Boolean) obj.get("devis_project")

                    )
            );

            mapOperations.put(obj.get("id").toString(),
                    new Operations(map.get("proformaName").toString(),map.get("clientname").toString()
                            ,map.get("currentdate").toString(),obj.get("name").toString(),
                            "edit",map.get("proformaNumero").toString(),
                            obj.get("id").toString()
                    )
            );
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        this.devisTableView.setItems(devisliste);
        devis_name.setCellValueFactory(param -> param.getValue().devisProperty());
        numero.setCellValueFactory(param -> param.getValue().numeroProperty());
        created.setCellValueFactory(param -> param.getValue().createdProperty());
       // client.setCellValueFactory(param -> param.getValue().clientProperty());
        state.setCellValueFactory(param -> param.getValue().stateProperty());
        id.setCellValueFactory(param -> param.getValue().idProperty());
        type.setCellValueFactory(param -> param.getValue().typeProperty());


        this.devisTableView.setRowFactory(param -> {
            TableRow<Devis> p = new TableRow<Devis>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    Devis devis = p.getItem();
                    try {
                            Operations operations = mapOperations.get(devis.getId());
                            boolean isproject = (boolean) devistate.get(devis.getId());
                            mainApp.DevisAction(operations,stage,true,isproject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return p;
        });

    }
}
