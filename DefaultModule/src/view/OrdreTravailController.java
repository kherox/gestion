package view;

import com.rethinkdb.net.Cursor;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 17/11/2016.
 */
public class OrdreTravailController {

    @FXML
    private GridPane ordre;
    @FXML
    private GridPane besoin;
    @FXML
    private GridPane bon;
    @FXML
    private GridPane livraison;
    @FXML
    private GridPane facture;
    @FXML
    private CheckBox ochBox;
    @FXML
    private CheckBox bchBox;
    @FXML
    private CheckBox bochBox;
    @FXML
    private TextField livchBox;
    @FXML
    private CheckBox fchBox;

    @FXML
    private Label olabel;
    @FXML
    private Label blabel;
    @FXML
    private Label bolabel;
    @FXML
    private Label lilabel;
    @FXML
    private Label flabel;

    @FXML
    private TableView<Project> tableView;
    @FXML
    private TableColumn<Project,String> nameColumn;
    @FXML
    private TableColumn<Project,String> idColumn;

    private ObservableList<Project> observableList = FXCollections.observableArrayList();

    private MainApp mainApp;
    private Stage stage;
    private HashMap hashMap = new HashMap();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor = this.mainApp.manager.getAllData("projects");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            this.observableList.add(new Project(map.get("name").toString(),map.get("id").toString()));
            hashMap.put(map.get("name"),map);
        }
        tableView.setItems(this.observableList);

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
     @FXML
    private void  initialize(){
        nameColumn.setCellValueFactory(param -> param.getValue().labelProperty());
        idColumn.setCellValueFactory(param -> param.getValue().idProperty());
       // ordre; besoin; bon;livraison; facture;
        ordre.setVisible(false);
        besoin.setVisible(false);
        bon.setVisible(false);
        livraison.setVisible(false);
        facture.setVisible(false);

         tableView.setOnMousePressed(new EventHandler<MouseEvent>() {
             @Override
             public void handle(MouseEvent event) {
                 HashMap hashMap1 = (HashMap) hashMap.get(tableView.getSelectionModel().getSelectedItem().getLabel());
                 if (!hashMap1.containsKey("ordre_travail")){
                     setView(ordre,ochBox,olabel,false);

                 }else{
                     setView(ordre,ochBox,olabel,true);
                     if (!hashMap1.containsKey("besoin_liste")){
                         setView(besoin,bchBox,blabel,false);
                     }else {
                         setView(besoin,bchBox,blabel,true);
                         if (!hashMap1.containsKey("bon_comande")){
                             setView(bon,bochBox,bolabel,false);
                         }else {
                             setView(bon,bochBox,bolabel,true);
                             if (!hashMap1.containsKey("bon_livraison")){
                                 setView(livraison,livchBox,lilabel,false);
                             }else {
                                 setView(livraison,livchBox,lilabel,true);
                                 if (!hashMap1.containsKey("facture")){
                                     setView(facture,fchBox,flabel,false);
                                 }else {
                                     setView(facture,fchBox,flabel,true);

                                 }
                             }
                         }
                     }
                 }

             }
         });

         ochBox.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
              Alert alert =  alertedInformation("Fenetre de validation","Voulez-vous vraiment valider cette etape ? Elle est non reversible","Ordre de Travail");

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r
                             .hashMap("name",tableView.getSelectionModel().getSelectedItem().getLabel())
                             .with("ordre_travail",true);
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",tableView.getSelectionModel().getSelectedItem().getId());
                     besoin.setVisible(true);
                     ochBox.setVisible(false);
                     blabel.setVisible(false);
                 }
             }
         });


         bchBox.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Alert alert =  alertedInformation("Fenetre de validation","Voulez-vous vraiment valider cette etape ? Elle est non reversible","Ordre de Travail");

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r
                             .hashMap("name",tableView.getSelectionModel().getSelectedItem().getLabel())
                             .with("besoin_liste",true);
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",tableView.getSelectionModel().getSelectedItem().getId());
                     bon.setVisible(true);
                     bchBox.setVisible(false);
                     bolabel.setVisible(false);
                 }
             }
         });

         bochBox.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Alert alert =  alertedInformation("Fenetre de validation","Voulez-vous vraiment valider cette etape ? Elle est non reversible","Ordre de Travail");

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r
                             .hashMap("name",tableView.getSelectionModel().getSelectedItem().getLabel())
                             .with("bon_comande",true);
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",tableView.getSelectionModel().getSelectedItem().getId());
                     livraison.setVisible(true);
                     bochBox.setVisible(false);
                     bolabel.setVisible(true);
                     lilabel.setVisible(false);
                 }
             }
         });

         livchBox.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Alert alert =  alertedInformation("Fenetre de validation","Voulez-vous vraiment valider cette etape ? Elle est non reversible","Ordre de Travail");

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r
                             .hashMap("name",tableView.getSelectionModel().getSelectedItem().getLabel())
                             .with("bon_livraison",true);
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",tableView.getSelectionModel().getSelectedItem().getId());
                     facture.setVisible(true);
                     livchBox.setVisible(false);
                     flabel.setVisible(false);
                     lilabel.setVisible(true);
                 }
             }
         });

         fchBox.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent event) {
                 Alert alert =  alertedInformation("Fenetre de validation","Voulez-vous vraiment valider cette etape ? Elle est non reversible","Ordre de Travail");

                 if(alert.getResult().getButtonData().isDefaultButton()){
                     mainApp.mapObject = ConnectionManager.r
                             .hashMap("name",tableView.getSelectionModel().getSelectedItem().getLabel())
                             .with("facture",true);
                     mainApp.manager.UpdateData(mainApp.mapObject,"projects",tableView.getSelectionModel().getSelectedItem().getId());
                     fchBox.setVisible(false);
                     flabel.setVisible(true);

                 }
             }
         });
    }

    private Alert alertedInformation(String title,String content,String header){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initOwner(this.stage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
        return alert;
    }

    private void setView(GridPane gridPane,CheckBox checkBox, Label label,boolean b){

        if (b){
            gridPane.setVisible(true);
            label.setVisible(true);
            checkBox.setVisible(false);
        }else{
            gridPane.setVisible(true);
            label.setVisible(false);
            checkBox.setVisible(true);
        }

    }
    private void setView(GridPane gridPane,TextField checkBox, Label label,boolean b){

        if (b){
            gridPane.setVisible(true);
            label.setVisible(true);
            checkBox.setVisible(false);
        }else{
            gridPane.setVisible(true);
            label.setVisible(false);
            checkBox.setVisible(true);
        }

    }
}
