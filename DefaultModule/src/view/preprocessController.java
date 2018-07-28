package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApp;
import model.Customer;
import model.Project;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Univetech Sarl on 06/10/2016.
 */
public class preprocessController {

   @FXML
    private VBox container;
    @FXML
    private ComboBox<String> client;
    @FXML
    private TextField projetName;
    @FXML
    private TextField projetVille;
    @FXML
    private TextField personneabout;
    @FXML
    private TextArea projetDescription;
    @FXML
    private ComboBox projectType;
    @FXML
    private ComboBox villeList;
    @FXML
    private Button quitter;

    private MainApp mainApp;
    private Stage stage;
    private Customer currentCustomer;
    private boolean state = false;
    private ObservableList<String> clientliste = FXCollections.observableArrayList();
    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();
    private ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();
    private HashMap clientmap = new HashMap();


    public void setStage(Stage stage) {this.stage = stage;}



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor = this.mainApp.manager.getAllData("customers");


        for (Object obj : cursor)
        {
            HashMap<Customer,String>  hashMap = (HashMap<Customer, String>) obj;
            client.getItems().add(hashMap.get("raison_social"));
            Customer customer = new Customer(
                    hashMap.get("name"),
                    hashMap.get("raison_social"),
                    hashMap.get("telephone"),
                    hashMap.get("fax"),
                    hashMap.get("country"),
                    hashMap.get("city"),
                    hashMap.get("mobile"),
                    hashMap.get("activity"),
                    hashMap.get("id"),
                    hashMap.get("reglement"),
                    hashMap.get("type_activity"),
                    hashMap.get("type_client"),
                    hashMap.get("type_client_abreger"),
                    hashMap.get("code_client")
            );
            clientmap.put(hashMap.get("raison_social"),customer);

        }

         cursor = this.mainApp.manager.getAllData("clients_type"); //clients_type  project_type
        for (Object obj : cursor){
            HashMap  hashMap = (HashMap) obj;
            projectType.getItems().add(hashMap.get("name"));
        }

    }

    @FXML
    private void initialize(){

        villeList.getItems().add("Abidjan");
        villeList.getItems().add("Autre");

        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.closeCurrentWindow(stage);
            }
        });
    }


    private HashMap getProjectType(String n){

     Cursor cursor =  this.mainApp.manager.getDataByName("clients_type",n);

       return  (HashMap) cursor.next();
    }



    @FXML
    private void SaveNewProjetButton() throws Exception {
        if (!this.IsInputValid()){
             this.currentCustomer = (Customer) clientmap.get(client.selectionModelProperty().getValue().selectedItemProperty().getValue());
            HashMap map =  getProjectType(projectType.getValue().toString());
            this.mainApp.mapObject = ConnectionManager.r.
                    hashMap("name",projetName.getText())
                    .with("ville",villeList.getValue())
                    .with("categorie",projectType.getValue())
                    .with("client",ConnectionManager.r.array(
                            ConnectionManager.r.
                                    hashMap("name",this.currentCustomer.getName())
                                    .with("raison_social",this.currentCustomer.getLabel())
                                    .with("telephone",this.currentCustomer.getTelephone())
                                    .with("fax",this.currentCustomer.getFax())
                                    .with("country",this.currentCustomer.getCountry())
                                    .with("city",this.currentCustomer.getCity())
                                    .with("activitty",this.currentCustomer.getActivity())
                                    .with("mobile",this.currentCustomer.getMobile())
                                    .with("id",this.currentCustomer.getId())
                                    .with("reglement",this.currentCustomer.getReglement())
                            )
                    )
                    .with("description",projetDescription.getText())
                    .with("state","1")
                    .with("isproject",true)
                    .with("personneabout",personneabout.getText().toString())
                    .with("created", LocalDate.now().toString());

            if(this.mainApp.manager.SaveData(this.mainApp.mapObject,"projects"))
            {
               new WindowZone("Nouveau Process","Vous venez d'initialisé un nouveau process avec success","Nouveau Process Initialisé",false,stage);
                //this.mainApp.DefaultAction(this.stage);
                this.mainApp.closeCurrentWindow(stage);
            }else{
                this.state = true;
               new WindowZone("Sauvegarde Impossible","Nous ne pouvons pas enregistrer vos donnée","Erreur dans les données",true,stage);

            }
        }
    }

    private boolean IsInputValid(){
        String errorMessage = "";
        if (this.projetDescription.getText().length() == 0 || this.projetDescription.getText() == null)
              errorMessage += " La description du projet ne doit pas etre vide \n ";
        if (this.projetName.getText().length() == 0 || this.projetName.getText() == null)
            errorMessage += "Le nom  du projet ne doit pas etre vide \n ";
       /* if (this.projetVille.getText().length() == 0 || this.projetVille.getText() == null)
            errorMessage += "La ville du projet ne doit pas etre vide  \n";*/

        try {
            if(client.selectionModelProperty().getValue().selectedItemProperty().getValue().toString().length()>0)
            {
                if(!client.getItems().contains(client.getValue().toString())){
                    errorMessage +=" Ce client n'existe pas !! Veuillez svp le creer d abord \n";
                }
            }
        } catch (NullPointerException e) {
            this.state = true;
            new WindowZone("Erreur Client","Corriger les erreurs"," Ce client n'existe pas !! Veuillez svp le creer d abord "+e.getMessage(),true,stage);
        }
        if (errorMessage.length() >0)
        {
            this.state = true;
            new WindowZone("Erreur Client","Corriger les erreurs",errorMessage,true,stage);

            return true;
        }else{
            return  false;
        }
    }






}
