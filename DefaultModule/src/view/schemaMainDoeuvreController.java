package view;

import com.rethinkdb.net.Cursor;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.effect.FloatMap;
import javafx.stage.Stage;
import main.MainApp;
import model.DetailsWorkforce;
import model.Project;
import model.Workforce;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 06/10/2016.
 */
public class schemaMainDoeuvreController {

    private MainApp mainApp;
    private Stage stage;
    private Project project;

    @FXML
    private TableView<DetailsWorkforce> detailsWorkforceTableView;
    @FXML
    private TableColumn<DetailsWorkforce,String> idColumn;
    @FXML
    private TableColumn<DetailsWorkforce,String> responsabiliteColumn;
    @FXML
    private TableColumn<DetailsWorkforce,String> nameColumn;
    @FXML
    private TableColumn<DetailsWorkforce,String> tauxColumn;
    @FXML
    private TableColumn<DetailsWorkforce,String> montantColumn;
    @FXML
    private TableColumn<DetailsWorkforce,String> coefficientColumn;

    @FXML
    private TableView<DetailsWorkforce> equipementView;
    @FXML
    private TableColumn<DetailsWorkforce,String> libelle;
    @FXML
    private TableColumn<DetailsWorkforce,String> coef_colum;
    @FXML
    private TableColumn<DetailsWorkforce,String> valeur;
    @FXML
    private TableColumn<DetailsWorkforce,String> montant;




    private ObservableList<DetailsWorkforce> detailsWorkforceObservableList = FXCollections.observableArrayList();
    private ObservableList<DetailsWorkforce> equiObservableList = FXCollections.observableArrayList();

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
    private ComboBox<String> name;
    @FXML
    private ComboBox<String> responsable;
    @FXML
    private ComboBox<String> label;
    @FXML
    private ComboBox<String> coefficient;
    @FXML
    private ComboBox<String> tauxhoraire;
    private HashMap hashMap = new HashMap();
    private HashMap idMap = new HashMap();

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp; }

    public void setStage(Stage stage) {this.stage = stage;}

    public void setProject(Project project) {
        this.project = project;

        projectclient.setText(this.project.getClient());
        projectdescription.setText(this.project.getDescription());
        projectname.setText(this.project.getLabel());
        projectville.setText(this.project.getVille());
        projectrepresentant.setText(this.project.getRepresentant());
        projectclientcontact.setText(this.project.getContact());


        Cursor cursor = this.mainApp.manager.getDataById("projects",project.getId());
        HashMap map = (HashMap) cursor.next();
        HashMap hashMap = (HashMap) map.get("maindoeuvre_map");
        Collection collection = hashMap.values();
        Iterator iterator = collection.iterator();
        //String responsabilité, String nom, String coefficient, String tauxhoraire, String montant, String id
        while (iterator.hasNext()){
            HashMap hashMap1 = (HashMap) iterator.next();
            double montant =
             Double.parseDouble(hashMap1.get("quantite").toString()) *  Double.parseDouble(hashMap1.get("taux_horaire").toString())
             * Double.parseDouble(hashMap1.get("coefficient_horaire").toString()) * Double.parseDouble(hashMap1.get("nombre_jour").toString()) ;
            this.detailsWorkforceObservableList.add(
                    new DetailsWorkforce(
                            hashMap1.get("project_type").toString(),hashMap1.get("quantite").toString(),
                            hashMap1.get("coefficient_horaire").toString(),hashMap1.get("taux_horaire").toString(),
                            String.valueOf(montant),hashMap1.get("nombre_jour").toString()
                            )
            );
            
        }


         hashMap = (HashMap) map.get("component_map");
         collection = hashMap.values();
         iterator = collection.iterator();


        while (iterator.hasNext()){
            HashMap hashMap1 = (HashMap) iterator.next();
            //la quantite represente le nom de personnes affecter l'equipement
            double montant = Double.parseDouble(hashMap1.get("quantite").toString()) *  Double.parseDouble(hashMap1.get("taux_horaire").toString()) * Double.parseDouble(hashMap1.get("coefficient_horaire").toString());
           
            this.equiObservableList.add(
                    new DetailsWorkforce(
                    hashMap1.get("name").toString(),hashMap1.get("quantite").toString(),
                    hashMap1.get("coefficient_horaire").toString(),hashMap1.get("taux_horaire").toString(),
                    String.valueOf(montant),"1"
            ));
        }





    }

    @FXML
    private void initialize(){

        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
        responsabiliteColumn.setCellValueFactory(param -> param.getValue().responsabilitéProperty());
        nameColumn.setCellValueFactory(param -> param.getValue().nomProperty());
        tauxColumn.setCellValueFactory(param -> param.getValue().tauxhoraireProperty());
        montantColumn.setCellValueFactory(param -> param.getValue().montantProperty());
        coefficientColumn.setCellValueFactory(param -> param.getValue().coefficientProperty());
        this.detailsWorkforceTableView.setItems(this.detailsWorkforceObservableList);
        this.equipementView.setItems(this.equiObservableList);
       // equipementView; libelle;coef_colum; valeur;montant;
        libelle.setCellValueFactory(param -> param.getValue().nomProperty());
        coef_colum.setCellValueFactory(param -> param.getValue().coefficientProperty());
        valeur.setCellValueFactory(param -> param.getValue().tauxhoraireProperty());
        montant.setCellValueFactory(param -> param.getValue().montantProperty());





    }

    @FXML
    private void CalculateHandler(){



    }
    @FXML
    public void TerminateHandler() throws Exception {
        this.mainApp.TransportCalculation(this.project,stage);
        this.mainApp.closeCurrentWindow(stage);
    }



}
