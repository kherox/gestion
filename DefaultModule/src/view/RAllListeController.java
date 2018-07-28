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
import model.Revision;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 18/12/2016.
 */
public class RAllListeController {

    @FXML
    private TableView<Revision> revisionListeView;
    @FXML
    private TableColumn<Revision,String> id;
    @FXML
    private TableColumn<Revision,String> name;
    @FXML
    private TableColumn<Revision,String> number;
    @FXML
    private TableColumn<Revision,String> state;
    @FXML
    private TableColumn<Revision,String> proformanumber;
    @FXML
    private TableColumn<Revision,String> client;

    private MainApp mainApp;
    private Stage stage;
    boolean isproject = false;
    private HashMap devistate = new HashMap();


     ObservableList<Revision> observableList = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        //String client, String name, String proforma, String type, String numero, String id
        Cursor cursor = this.mainApp.manager.getAllData("revisions");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){

            HashMap hash = (HashMap) iterator.next();
            ArrayList arrayList = (ArrayList) hash.get("clientmap");

            HashMap clientmap = (HashMap) arrayList.get(0);

            boolean object = false;
            try{
                 object = (Boolean) hash.get("project");
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
            devistate.put(hash.get("id").toString(),isproject);
            this.observableList.add(
            new Revision(
                    clientmap.get("clientname").toString(),hash.get("name").toString(),
                    hash.get("user").toString(),simple,
                    hash.get("default_devis_number").toString(),hash.get("id").toString()
                    )
            );
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        this.revisionListeView.setItems(this.observableList);
        id.setCellValueFactory(param -> param.getValue().idProperty());
        client.setCellValueFactory(param -> param.getValue().clientProperty());
        proformanumber.setCellValueFactory(param -> param.getValue().proformaProperty());
        number.setCellValueFactory(param -> param.getValue().numeroProperty());
        state.setCellValueFactory(param -> param.getValue().typeProperty());
        name.setCellValueFactory(param -> param.getValue().nameProperty());



        this.revisionListeView.setRowFactory(param -> {
            TableRow<Revision> p = new TableRow<Revision>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    Revision revision = p.getItem();
                    try {
                        boolean isproject = (boolean) devistate.get(revision.getId());
                        mainApp.viewPrintActionRevision(null,null,null,null,stage,revision, isproject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return p;
        });
    }

}
