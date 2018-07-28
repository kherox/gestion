package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Transport;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 01/11/2016.
 */
public class TransportationController {
    @FXML
    private TextField label;
    @FXML
    private TextField ville;
    @FXML
    private TextField distance;
    @FXML
    private TextField montant;
    @FXML
    private TextField coefficient;
    @FXML
    private Button validate;

    @FXML
    TableView<Transport> transportTableView;
    @FXML
    TableColumn<Transport,String> nameColumn;
    @FXML
    TableColumn<Transport,String> montantColumn;

    private MainApp mainApp;
    private Stage stage;
    private ObservableList<Transport> contentList = FXCollections.observableArrayList();


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Cursor cursor = this.mainApp.manager.getAllData("transports_element_create");
        Iterator iterator = cursor.iterator();

        while (iterator.hasNext()){

            HashMap hashMap = (HashMap) iterator.next();

            contentList.add(new Transport(
                    hashMap.get("name").toString(),
                    hashMap.get("name").toString()
                    ,hashMap.get("name").toString()
                    ,hashMap.get("montant").toString(),
                    hashMap.get("id").toString()
            ));



        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

//    public void setProductses(ObservableList<Products> productses) {
//        this.productses = productses;
//    }

    @FXML
    private void initialize(){

        nameColumn.setCellValueFactory(param -> param.getValue().labelProperty());
        montantColumn.setCellValueFactory(param -> param.getValue().montantProperty());
        this.transportTableView.setItems(contentList);

        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                String name="";
                double m=0;
                try{
                     m     = Double.parseDouble(montant.getText());
                     name = label.getText();
                }catch (NumberFormatException ex) {m=0;}
                catch (NullPointerException ex){}


                if(name.isEmpty() || m <= 0){
                    new WindowZone("Sauvegarde Impossible","Vous ne poiuvez pas sauvergarder ces donnée","Erreur dans les données",true,stage);
                }else{
                    contentList.add(new Transport(name,name,"1",String.valueOf(m),"1"));

                    mainApp.mapObject = ConnectionManager.r.hashMap("name",name).with("montant",m);
                    mainApp.manager.__save(mainApp.mapObject,"transports_element_create");
                    label.setText("");
                    montant.setText("");
                }

            }
        });
    }


    private boolean IsInputValid(){
        String errorMessage = "";
        if (label.getText().isEmpty() || label.getText() == null)
            errorMessage += "Vous devez renseigner un label";
        if (distance.getText().isEmpty() || distance.getText() == null)
            errorMessage += "Renseigner la distance";
        if (Double.isNaN(Double.parseDouble(distance.getText())))
            errorMessage += "Le coefficient doit etre un nombre";
        if (montant.getText().isEmpty() || montant.getText() == null)
            errorMessage += "Le Montant doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(montant.getText())))
            errorMessage += "Le Montant doit etre un nombre";
        if (ville.getText().isEmpty() || ville.getText() == null)
            errorMessage += "La ville  doit etre renseigner";
        if (coefficient.getText().isEmpty() || coefficient.getText() == null)
            errorMessage += "La ville  doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(coefficient.getText())))
            errorMessage += "Le coefficient doit etre un nombre";
        return errorMessage.isEmpty();

    }

}
