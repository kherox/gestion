package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.Project;
import model.Workforce;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 12/02/2017.
 */
public class AddComponentsController {


    @FXML
    private TextField label;
    @FXML
    private TextField nbre;
    @FXML
    private TextField coef;
    @FXML
    private TextField quantite;
    @FXML
    private TextField coeff_equipement;
    @FXML
    private TextField coeff_horaire;
    @FXML
    private TextField nombre_jour;
    @FXML
    private TextField taux_horaire;
    @FXML
    private TextField maindoeuvre;

    @FXML
    private TableView<Workforce> coefficientTableView;
    @FXML
    private TableColumn<Workforce,String> idColumn;
    @FXML
    private TableColumn<Workforce,String> labelColumn;
    @FXML
    private TableColumn<Workforce,String> montantColumn;
    @FXML
    private TableColumn<Workforce,String> nombreColumn;

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
    private ComboBox project_type;
    @FXML
    private Button validate;
    @FXML
    private Button quitter;
    @FXML
    private Button delete;
    @FXML
    private Button terminate;
    @FXML
    private ComboBox currentprojectbox;

    private ObservableList<String> typemap = FXCollections.observableArrayList();
    private ObservableList<String> projectmap = FXCollections.observableArrayList();
    private HashMap hashMap = new HashMap();
    private String libelle,qualification;


    private MainApp mainApp;
    private Stage stage;
    private Project project;
    private HashMap primaryHashMap = new HashMap();
    private HashMap projectid = new HashMap();

    private int counter = 0;
    private int val = 0;
    private String id;
    private String name;

    public void setProject() {
       // this.project = project;



        Cursor cursor = this.mainApp.manager.getAllData("components");
        Iterator iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            String s = map.get("name").toString();
            primaryHashMap.put(s,map);
            counter += 1;
            typemap.add(s);
        }
        project_type.setItems(typemap);

         cursor = this.mainApp.manager.getAllData("projects");
         iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            String s = map.get("name").toString();
            projectid.put(s,map);
            projectmap.add(s);
        }
        currentprojectbox.setItems(projectmap);



    }

    private ObservableList<Workforce> workforceObservableLis = FXCollections.observableArrayList();



    public void setMainApp(MainApp mainApp) { this.mainApp = mainApp; this.coefficientTableView.setItems(this.workforceObservableLis);}

    public void setStage(Stage stage) {this.stage = stage;}

    @FXML
    private void initialize(){

        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
        nombreColumn.setCellValueFactory(param -> param.getValue().nbreEquipeProperty());
        labelColumn.setCellValueFactory(param -> param.getValue().labelProperty());
        montantColumn.setCellValueFactory(param -> param.getValue().montantProperty());
        taux_horaire.setEditable(false);
//        maindoeuvre.setEditable(false);

        project_type.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HashMap map = (HashMap) primaryHashMap.get(newValue);
                taux_horaire.setText(map.get("montant").toString());

            }
        });

        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                mainApp.closeCurrentWindow(stage);
            }
        });



        currentprojectbox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                HashMap map = (HashMap) projectid.get(newValue.toString());

                ArrayList arrayList =  (ArrayList) map.get("client");
                HashMap client = (HashMap) arrayList.get(0);

                name = map.get("name").toString();

                  boolean pass = false;

                try {
                    String state = map.get("state").toString();
                    if (state.equals("5")) {
                        ///new WindowZone("Liste des besoins","Les prix ont été déja validés","Etape valider",false,stage);
                        pass = false;

                    }else{

                        pass = true;
                    }

                    if (state.equals("1")) {
                        pass = false;

                    }

                } catch (Exception as){
                    pass = false;
                    new WindowZone("Liste des besoins","Vous devez  renseigner la liste des besoins","Etape Manquant",false,stage);

                }

                delete.setVisible(pass);
                terminate.setVisible(pass);
                validate.setVisible(pass);

                projectclient.setText(client.get("raison_social").toString());
                projectdescription.setText(map.get("description").toString());
                projectname.setText(map.get("name").toString());
                projectville.setText(client.get("city").toString());
                projectrepresentant.setText(client.get("name").toString());
                projectclientcontact.setText(client.get("telephone").toString());

                id = map.get("id").toString();

            }
        });



        this.coefficientTableView.setItems(this.workforceObservableLis);


    }


    @FXML
    private void createWorkForce(){
        val +=  1;
        HashMap map = new HashMap();
        double qt = Double.parseDouble(quantite.getText());
        //double coefq = Double.parseDouble(coeff_equipement.getText());
        double coefh = Double.parseDouble(coeff_horaire.getText());
       // double nb  = Double.parseDouble(nombre_jour.getText());
        double th = Double.parseDouble(taux_horaire.getText());
        //double m = Double.parseDouble(maindoeuvre.getText());
        double montant = th * coefh * qt  ;
        /*String label, String nbreEquipe, String coefficient_horaire,
            String taux_horaire, String valeur_equipement, String coefficient_equipement,
            String nbre_jours, String id,String montant*/
        map.put("project_type",project_type.getValue().toString());
        map.put("quantite",quantite.getText());
        map.put("coefficient_horaire",coeff_horaire.getText());
        map.put("taux_horaire",taux_horaire.getText());
        map.put("name",project_type.getValue().toString());

        hashMap.put(project_type.getValue().toString(),map);

        Workforce workforce = new Workforce(
                project_type.getValue().toString(),quantite.getText(),
                coeff_horaire.getText(),taux_horaire.getText(),
                "","",
                "00","00",String.valueOf(montant)
        );
        this.workforceObservableLis.add(workforce);

        quantite.setText("");
//        coeff_equipement.setText("");
        coeff_horaire.setText("");
        taux_horaire.setText("");
        // maindoeuvre.setText("");
       // nombre_jour.setText("");

        if (counter == val){
            //new WindowZone("Panneau de control","Vous ne pourai plus enregistrer")
            this.validate.setVisible(false);
        }

    }

    private boolean IsValid(){

        String errorMessage ="";

        if (label.getText().isEmpty() || label.getText() == null)
            errorMessage = "Le nom ne peut etre vide";
        if (nbre.getText().isEmpty() || nbre.getText() == null || Float.parseFloat(nbre.getText())< 0 || Float.isNaN(Float.parseFloat(nbre.getText())))
            errorMessage = "Le nombre d'equipe doit etre un chiffre ou le nombre d'equipe ne doit pas etre vide";
        if (coef.getText().isEmpty() || Float.parseFloat(coef.getText())<0 || Float.isNaN(Float.parseFloat(coef.getText())))
            errorMessage ="Le coeffcient doit etre renseigner ou le coefficient doit etre un chiffre";
        if (!errorMessage.isEmpty())
        {
            new WindowZone("Donnée Incorrent",errorMessage,"Corriger Les informations",true,stage);
            return  false;
        }
        return true;
    }



    @FXML
    private void DeleteHandler(){
        int i = coefficientTableView.getSelectionModel().getSelectedIndex();
        if (i <0)
            new WindowZone("Suppression impossible","Le dernier element ne peut etre supprimer ou vous n'avez rien selectionner","Veuillez selectionner",false,stage);
        Workforce workforce = coefficientTableView.getSelectionModel().getSelectedItem();
        hashMap.remove(workforce.getLabel());
        this.coefficientTableView.getItems().remove(i);
    }

    @FXML
    private void TerminateHander() throws Exception {

        this.mainApp.mapObject = ConnectionManager.r
                .hashMap("name",name)
                .with("component_map",hashMap)
                .with("state","5")
                .with("components",true)
                .with("created", LocalDate.now().toString());
        this.mainApp.manager.UpdateData(this.mainApp.mapObject,"projects",this.id);
        this.mainApp.closeCurrentWindow(this.stage);
    }

	
}
