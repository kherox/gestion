package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;

import java.util.*;

/**
 * Created by Univetech Sarl on 18/10/2016.
 */
public class preDevisController {

    @FXML
    private ComboBox<String> productBox;
    @FXML
    private TableView<Products> productsTableView;
    @FXML
    private TableColumn<Products,String> ref;
    @FXML
    private TableColumn<Products,String> desig;
    @FXML
    private TableColumn<Products,String> qte;
    @FXML
    private TableColumn<Products,String> prix;
    @FXML
    private TableColumn<Products,String> remise;
    @FXML
    private TableColumn<Products,String> marge;
    @FXML
    private TableColumn<Products,String> montantHt;
    @FXML
    private TableColumn<Products,String> id;
    @FXML
    private TextField rubriqueTextField;
    @FXML
    private CheckBox sousrubrique;

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
    private Button createdevis;
    // @FXML
    // private Button createdevis;

    @FXML
    private ComboBox projetbox;

    private  HashMap projectmap = new HashMap();

    private String ids;

    private MainApp mainApp;
    private Stage stage;
    private Project project;
    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
    private ObservableList<Products> selectedArrayListe = FXCollections.observableArrayList();

    private boolean mainconfig = false;
    private Iterator mainIterator;
    private double nombreJour  = 0;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp; this.productsTableView.setItems(this.productsObservableList);



       Cursor cursor = this.mainApp.manager.getAllData("projects");
       Iterator iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            String s = map.get("name").toString();
            projectmap.put(s,map);
            projetbox.getItems().add(s);
        }
        //projetbox.setItems(projectmap);

    }
    public void setStage(Stage stage) {this.stage = stage;}
    private double setQuantity(String quantite){
        double newv;
        if (quantite.contains("%")){
            String[] d = quantite.split("%");
            newv = Double.parseDouble(d[0]);
        }else
            newv = Double.parseDouble(quantite);
        return newv;
    }



    public void setProject(Iterator iterator) {
        
            try {
               
                    while (iterator.hasNext()){
                        HashMap mapkey = (HashMap) iterator.next();
                        double qt = setQuantity(mapkey.get("quantite").toString());
                        double price = setQuantity(mapkey.get("prixu").toString());
                        double remise = setQuantity(mapkey.get("remise").toString());
                        double mg = setQuantity(mapkey.get("marge").toString());
                        double montant = (price*mg) -(price*remise/100);
                        double pricemarge = (price*mg) ;
                     Products   products =  new Products(
                                mapkey.get("designation").toString(),
                                mapkey.get("reference").toString(),
                                mapkey.get("quantite").toString(),
                                String.valueOf(Math.round(price)),
                                mapkey.get("marge").toString(),
                                mapkey.get("remise").toString(),
                                String.valueOf(Math.round(montant)),
                                "000","Fournitures","Materiels et Accessoires");
                        this.productsObservableList.add(products);

                     }
             
             } catch (Exception e) {}

    }


    @FXML
    private void initialize(){
        desig.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        prix.setCellValueFactory(param -> param.getValue().prixProperty());
        marge.setCellValueFactory(param -> param.getValue().margeProperty());
        remise.setCellValueFactory(param -> param.getValue().remiseProperty());
        montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());

        productsObservableList = FXCollections.observableArrayList();
        selectedArrayListe = FXCollections.observableArrayList();

        Products product = new Products("Fournitures".toUpperCase(),"","","","","","0","","Fournitures","Fournitures");
        if (selectedArrayListe.add(product)){
            Products products  = new Products("Materiels et Accessoires".toUpperCase(),"","","","","","0","","Fournitures","Materiels et Accessoires");
            if (!selectedArrayListe.add(products)){
                new WindowZone("Probleme de configuration", "Nous ne parvenons pas a charger votre configuration de base. Cela peut etre du" +
                        "a une erreur systeme", "Veuillez patienter un peu",true,stage);
            }else{
                mainconfig = true;
            }
        }else{
            new WindowZone("Probleme de configuration", "Nous ne parvenons pas a charger votre configuration de base. Cela peut etre du" +
                    "a une erreur systeme", "Veuillez patienter un peu",true,stage);
        }


        /*desig.setCellFactory(TextFieldTableCell.forTableColumn());
        ref.setCellFactory(TextFieldTableCell.forTableColumn());
        prix.setCellFactory(TextFieldTableCell.forTableColumn());
        remise.setCellFactory(TextFieldTableCell.forTableColumn());
        qte.setCellFactory(TextFieldTableCell.forTableColumn());
*/
        this.productsTableView.setItems(this.productsObservableList);


        this.productsTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        this.productsTableView.getSelectionModel().setCellSelectionEnabled(false);

        this.productsTableView.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
               Products p =  productsTableView.getSelectionModel().selectedItemProperty().getValue();
                selectedArrayListe.add(p);

            }
        });




        prix.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())) {}
            }
        });



        marge.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){}
            }
        });

        remise.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override

            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){}
            }
        });


        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override

            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){}
            }
        });


        desig.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override

            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){}
            }
        });

        projetbox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                HashMap map = (HashMap) projectmap.get(newValue.toString());

                

                 boolean state = false;
                 boolean enter = false;

                try {
                     state = (boolean) map.get("maindoeuvre");
                } catch (Exception as){
                    enter = true;
                    new WindowZone("Etape Main d'oeuvre ","Vous devez valider des etapes precedentes","Main d'oeuvre en attente",false,stage);
                    try{ mainApp.CoefficientWindow();}catch(Exception ex){}
                    mainApp.closeCurrentWindow(stage);
                }


                if(!enter){
                     try {
                         state = (boolean) map.get("components");
                    
                    } catch (Exception as){
                        enter = true;
                        new WindowZone("Etape Equipement","Vous devez valider des etapes precedentes","Equipement en attente",false,stage);
                       try{mainApp.AddComponent();}catch(Exception sd){} 
                       mainApp.closeCurrentWindow(stage);
                    }
                }
                if (!enter) {
                        try {
                         state = (boolean) map.get("transport");
                    } catch (Exception as){
                        enter = true;
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.initOwner(stage);
                            alert.setTitle("Calcul du transport");
                            alert.setContentText("Voulez-vous vraiment ajout un model de transport ??");
                            alert.setHeaderText("Etape Transport");
                            alert.showAndWait();
                            if (alert.getResult().getButtonData().isDefaultButton()){
                                new WindowZone("Etape Transport","Vous devez valider le calcul du Transport","Transport en attente",false,stage);
                                try{mainApp.TransportCalculation(null,stage);}catch(Exception ls){}
                                mainApp.closeCurrentWindow(stage);
                            }

                    }
                }
                


                ArrayList arrayList;
                try{
                     arrayList = (ArrayList) map.get("datamap");
                    HashMap hashMap = (HashMap) arrayList.get(0);
                    Collection collection = hashMap.values();
                    mainIterator = collection.iterator();
                    productsObservableList.remove(0,productsObservableList.size());
                    setProject(mainIterator);
                } catch(Exception as){
                    new WindowZone("Affichage impossible","Impossible d'etablir la liste des produits","Vous devez d'abord renseigner des produits",true,stage);

                }


                 arrayList =  (ArrayList) map.get("client");
                HashMap client = (HashMap) arrayList.get(0);
//String label, String client, String ville, String description, String id, String state
                 try{
                    nombreJour = Double.parseDouble(map.get("nombreJour").toString());

            }catch(Exception as){}
                project = new Project(client.get("raison_social").toString(),client.get("name").toString(),
                                     client.get("city").toString(),map.get("description").toString(),
                                     map.get("id").toString(),String.valueOf(nombreJour));

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
    private void AddNewLigneHandler(){
        this.productsObservableList.add(new Products("Modifier","Modifier","1","0.0","...","...","...","0","","")) ;
    }

    @FXML
    private void preview() throws Exception {
        if (this.selectedArrayListe.size()<= 2){
             new WindowZone("Devis impossible","Impossible d'elaborer le devis","Aucun produit selectionner",true,stage);
        }else{
            //this.mainApp.PrintDetails(this.project,this.productsObservableList,this.selectedArrayListe);
            try
            {
                if (mainconfig){
                    this.mainApp.MainDevisAction(this.project,this.productsObservableList,this.selectedArrayListe,stage);
                }

                //this.mainApp.closeCurrentWindow(this.stage);
            } catch (Exception es){
                 new WindowZone("Devis impossible","Impossible d'elaborer le devis","Nous avons detecter un probleme dans votre selection",true,stage);
                es.printStackTrace();

            }

        }
    }

    @FXML
    private void DeleteHandler(){
        int selected = this.productsTableView.selectionModelProperty().getValue().getSelectedIndex();
        if (selected > 0 ){
            this.productsTableView.getItems().remove(selected);
        }
    }
}
