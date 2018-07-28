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
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainApp;
import model.DetailsWorkforce;
import model.Project;
import model.Transport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 16/10/2016.
 */
public class transportController {

    @FXML
    private TextField label;
    @FXML
    private TextField ville;
    @FXML
    private TextField distance;
    @FXML
    private TextField montant;
    @FXML
    private ComboBox logistique;
    @FXML
    private TextField valeur_logistique;
    @FXML
    private TextField dsl;
    @FXML
    private TextField kilometre;
    @FXML
    private GridPane transportPane;
    @FXML
    private Label informations;
    @FXML
    private Button show;
    @FXML
    private Button calculate_button;
    @FXML
    private Button terminate_button;
    @FXML
    private Button next_button;


    @FXML
    private TableView<Transport> transportTableView;
    @FXML
    private TableColumn<Transport,String> labelColumn;
    @FXML
    private TableColumn<Transport,String> villeColumn;
    @FXML
    private TableColumn<Transport,String> distanceColumn;
    @FXML
    private TableColumn<Transport,String> montantColumn;
    @FXML
    private TableColumn<Transport,String> idColumn;

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
    private ComboBox projetbox;

    private HashMap pricemap = new HashMap();


    private MainApp mainApp;
    private Stage stage;
    private Project project;
    private ObservableList<Transport> transportObservableList = FXCollections.observableArrayList();
    private HashMap transportMap = new HashMap();
    private HashMap projectmap = new HashMap();
    private String ids;
    private String name;

    public void setProject(Project project) {


        Cursor cursor = this.mainApp.manager.getAllData("projects");
        Iterator iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            String s = map.get("name").toString();
            projectmap.put(s,map);
            projetbox.getItems().add(s);
        }
        // this.project = project;

        // projectclient.setText(this.project.getClient());
        // projectdescription.setText(this.project.getDescription());
        // projectname.setText(this.project.getLabel());
        // projectville.setText(this.project.getVille());
        // projectrepresentant.setText(this.project.getRepresentant());
        // projectclientcontact.setText(this.project.getContact());

        // Cursor cursor = this.mainApp.manager.getDataById("projects",project.getId());
        // HashMap map = (HashMap) cursor.next();

        // if (map.containsKey("transport_map")){
        //     HashMap hashMap1 = (HashMap) map.get("transport_map");
        //     try{
        //         this.transportObservableList.add(
        //                 new Transport(
        //                         hashMap1.get("name").toString(),
        //                         "",
        //                         hashMap1.get("distance").toString(),
        //                         hashMap1.get("montant").toString(),
        //                         "")
        //         );
        //     } catch (NullPointerException as){}

            show.setVisible(false);
        //     terminate_button.setVisible(false);
        //     next_button.setVisible(true);
        //     next_button.setDefaultButton(true);
        //     informations.setVisible(false);
        // }




//        logistique.getItems().add("PTAC <=3.5T : Pickup,Fourgon");
//        logistique.getItems().add("PTAC <=3.5T & <=6T : Kia,Camoinnette");
//        logistique.getItems().add("PTAC >6T : Camoin Telescopique type 10 roue");
//        logistique.getItems().add("PTAC >6T : Camoin plateau type remorque");
//




        cursor = this.mainApp.manager.getAllData("transports_element_create");

        for (Object obj : cursor){
            HashMap map = (HashMap) obj;
            logistique.getItems().add(map.get("name"));
            pricemap.put(map.get("name").toString(),map.get("montant").toString());
        }


        logistique.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                //ici la valeur logistique est represente le coefficient
               // valeur_logistique.setText(pricemap.get(newValue.toString()).toString());
                valeur_logistique.setEditable(true);
                kilometre.setText(pricemap.get(newValue.toString()).toString());
                kilometre.setEditable(false);
            }
        });

        valeur_logistique.setEditable(false);



    }

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp; this.transportTableView.setItems(this.transportObservableList);}


    public void setStage(Stage stage) {this.stage = stage;}


    @FXML
    private void initialize(){
        labelColumn.setCellValueFactory(param -> param.getValue().labelProperty());
        distanceColumn.setCellValueFactory(param -> param.getValue().distanceProperty());
        villeColumn.setCellValueFactory(param -> param.getValue().villeProperty());
        montantColumn.setCellValueFactory(param -> param.getValue().montantProperty());
        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
        valeur_logistique.setEditable(true);
        next_button.setVisible(false);
        informations.setVisible(false);
        transportPane.setVisible(true);

        next_button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainApp.Predevis(project,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                show.setVisible(false);
                transportPane.setVisible(true);
                informations.setVisible(false);
            }
        });

        // transportPane.setVisible(false);
        // informations.setMinHeight(25);



        projetbox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                HashMap map = (HashMap) projectmap.get(newValue.toString());

                boolean pass = false;

                try {
                    String state = map.get("state").toString();
                    if (state.equals("3")) {
                        new WindowZone("Etape insuffisants","Vous devez valider des etapes precedentes","Retour",false,stage);
                        mainApp.closeCurrentWindow(stage);
                        mainApp.CoefficientWindow();


                    }

                    if (state.equals("4")) {
                        new WindowZone("Etape insuffisants","Vous devez valider des etapes precedentes","Retour",false,stage);
                        mainApp.closeCurrentWindow(stage);
                        mainApp.AddComponent();


                    }

                } catch (Exception as){
                    pass = false;
                    new WindowZone("Liste des besoins","Vous devez  renseigner la liste des besoins","Etape Manquant",false,stage);

                }


                ArrayList arrayList;
//                try{
//                    arrayList = (ArrayList) map.get("datamap");
//                    HashMap hashMap = (HashMap) arrayList.get(0);
//                    Collection collection = hashMap.values();
//                    mainIterator = collection.iterator();
//                    productsObservableList.remove(0,productsObservableList.size());
//                    setProject(mainIterator);
//                } catch(Exception as){
//                    new WindowZone("Affichage impossible","Impossible d'etablir la liste des produits","Vous devez d'abord renseigner des produits",true,stage);
//
//                }

                name = map.get("name").toString();
                arrayList =  (ArrayList) map.get("client");
                HashMap client = (HashMap) arrayList.get(0);
//String label, String client, String ville, String description, String id, String state
                project = new Project(client.get("raison_social").toString(),client.get("name").toString(),
                        client.get("city").toString(),map.get("description").toString(),
                        map.get("id").toString(),"false");

                projectclient.setText(client.get("raison_social").toString());
                projectdescription.setText(map.get("description").toString());
                projectname.setText(map.get("name").toString());
                projectville.setText(client.get("city").toString());
                projectrepresentant.setText(client.get("name").toString());
                projectclientcontact.setText(client.get("telephone").toString());

                ids = map.get("id").toString();


            }
        });
    }

    @FXML
    private void calculateTransportPrice(){


        //logistique;valeur_logistique;dsl;kilometre;

      String l  =   logistique.getSelectionModel().getSelectedItem().toString();
      String vl =  valeur_logistique.getText();
      String dl = dsl.getText();
      String kl = kilometre.getText();



       double coeff = Double.parseDouble(vl) / 100;

        double montant = Double.parseDouble(dl) * Double.parseDouble(kl) * coeff;

        transportMap.put("moyen_logistique",l);
        transportMap.put("coefficient",vl);
        transportMap.put("distance",dl);
        transportMap.put("cout_kilometre",kl);
        transportMap.put("montant",montant);
        transportMap.put("name","Transport et Installation chantier");

        this.transportObservableList.add(
                new Transport(
                        "Transport et Installation chantier",
                        "",
                        dsl.getText(),
                        String.valueOf(montant),
                        "")
        );

        kilometre.setText("");
        dsl.setText("");
        valeur_logistique.setText("");

        //calculate_button.setVisible(false);
    }

    private boolean IsValid(){
        String errorMessage ="";
       if (label.getText().isEmpty() || label.getText() == null)
           errorMessage += "Le libéllé de peut etre vide";
        if (ville.getText().isEmpty() || ville.getText() == null)
            errorMessage += "La ville ne peut etre vide";
        if (distance.getText().isEmpty() || distance.getText() == null)
            errorMessage += "la distance ne peut etre vide";
        if (montant.getText().isEmpty() || montant.getText() == null)
            errorMessage +="Le montant ne peut etre vide";
        if (errorMessage.isEmpty()){
            if (!Float.isNaN(Float.parseFloat(distance.getText())))
                errorMessage += "La distance doit etre un nombre";
            if (!Float.isNaN(Float.parseFloat(montant.getText())))
                errorMessage += "Le montant doit etre un nombre";
            if (errorMessage.isEmpty())
            {
                return true;
            }

        }else{
            new WindowZone("Erreur",errorMessage,"Probleme dans les données",true,stage);
            return  false;
        }
        new WindowZone("Erreur",errorMessage,"Probleme dans les données",false,stage);
        return false;
    }


    @FXML
    private boolean TerminateHandler() throws Exception {
        this.mainApp.mapObject = ConnectionManager.r.hashMap("name",name)
        .with("transport_map",transportMap)
        .with("state","6")
        .with("transport",true);
        this.mainApp.manager.UpdateData(this.mainApp.mapObject,"projects",this.project.getId());
        this.mainApp.closeCurrentWindow(this.stage);
        return true;
    }


}
