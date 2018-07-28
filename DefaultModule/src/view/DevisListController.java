package view;

import com.rethinkdb.net.Cursor;
import controller.BaseController;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Dubai on 9/22/2017.
 */
public class DevisListController  {

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
    @FXML
    TableColumn<Devis,String> name;

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

            String simple = "Simple";
//String client, String devis, String type, String created, String numero, String id,boolean state
            devisliste.add(
                    new Devis(
                            map.get("clientraison").toString(),
                            map.get("proformaName").toString(),
                            obj.get("username").toString(),
                            map.get("date_demande").toString(),
                            map.get("proformaNumero").toString(),
                            obj.get("id").toString(),
                            (Boolean) obj.get("devis_project")

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
        name.setCellValueFactory(param -> param.getValue().typeProperty());
         client.setCellValueFactory(param -> param.getValue().clientProperty());
       // state.setCellValueFactory(param -> param.getValue().stateProperty());
      //  id.setCellValueFactory(param -> param.getValue().idProperty());
      type.setCellValueFactory(param -> param.getValue().typeProperty());

    }
}
