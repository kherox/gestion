package view;

import com.rethinkdb.gen.ast.Add;
import com.rethinkdb.gen.ast.Json;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Cursor;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import jdk.nashorn.internal.parser.JSONParser;
import main.MainApp;
import model.Customer;

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Univetech Sarl on 03/10/2016.
 */
public class customerEditDialogController {

    @FXML
    private TextField name;
    @FXML
    private TextField raison;
   
    @FXML
    private TextField id;
    @FXML
    private TextField country;
    @FXML
    private TextField city;
    @FXML
    private TextField mobile;
    @FXML
    private TextField telephone;
    @FXML
    private TextField reglement;
    @FXML
    private TextField fax;
    @FXML
    private TextField code_client;
    @FXML
    private ComboBox type_client;
     @FXML
    private TextField type_activite_abreviation;
     @FXML
     private ComboBox type_activite;
    @FXML
    private TextField type_client_abreger;
    @FXML
    private Button quitter;

    private Stage dialogStage;
    private MainApp mainApp;
    private Customer customer;
    private boolean okClicked = false;

    private HashMap activitytypemap = new HashMap();
    private HashMap activitymap = new HashMap();
    @FXML
    private  void initialize(){
        quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainApp.closeCurrentWindow(dialogStage);
            }
        });

        type_client.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                type_client_abreger.setText(activitymap.get(newValue.toString()).toString());
            }
        });
        type_activite.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                type_activite_abreviation.setText((String) activitytypemap.get(newValue.toString()).toString());
            }
        });

        code_client.setEditable(true);
    }

    public void setDialogStage(Stage stage){
        this.dialogStage = stage;
    }

    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

       Cursor cursor =  mainApp.manager.getAllData("clients_type");
        Iterator iterator = cursor.iterator();

        while (iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            type_client.getItems().add(map.get("name").toString());
            activitymap.put(map.get("name").toString(),map.get("abreviation").toString());
           
        }
        cursor = mainApp.manager.getAllData("project_type");
        iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap map = (HashMap) iterator.next();
            type_activite.getItems().add(map.get("name").toString());
            activitytypemap.put(map.get("name").toString(),map.get("abreviation").toString());
        }

    }

    public void setCustomer(Customer customer){
        if (customer != null) {
            this.customer = customer;
            this.name.setText(customer.getName());
            this.raison.setText(customer.getLabel());
            this.fax.setText(customer.getFax());
            this.country.setText(customer.getCountry());
            this.city.setText(customer.getCity());
            this.telephone.setText(customer.getTelephone());
            this.reglement.setText(customer.getReglement());
            this.mobile.setText(customer.getMobile());
            this.id.setText(customer.getId());
            this.code_client.setText(customer.getCode_client());
            //this.type_client.setText(customer.getType_client());
            this.type_client_abreger.setText(customer.getType_client_abreger());
           // this.type_activite.setText(customer.getActivity_abreger());
        }

    }



    public boolean isOkClicked(){
        return this.okClicked;
    }

    @FXML
    private void handleOk() throws UnsupportedEncodingException {
        if (isInputValid()) {
            long nombre = this.mainApp.manager.countAll("customers");

            String code = code_client.getText();
            if (code == null){
                code = " ";
                code +=LocalDate.now().getYear();
                if (nombre >= 8) code += "00"+(nombre+1);
                if (nombre <= 9 )code += "0" +(nombre+1);
                if (nombre <= 9 && nombre > 99 ) code += "0"+(nombre+1);
                if (nombre <= 99 ) code += (nombre+1);

                code+= "_"+type_client_abreger.getText();
            }
            if (code.isEmpty()){
                code = " ";
                code +=LocalDate.now().getYear();
                if (nombre >= 8) code += "00"+(nombre+1);
                if (nombre <= 9 )code += "0" +(nombre+1);
                if (nombre <= 9 && nombre > 99 ) code += "0"+(nombre+1);
                if (nombre <= 99 ) code += (nombre+1);

                code+= "_"+type_client_abreger.getText();

            }

          this.mainApp.mapObject =  ConnectionManager.r
                    .hashMap("name", new String(name.getText().getBytes("UTF-8"),"UTF-8"))
//                    .with("activity", new String(type_activite.getValue().toString().getBytes("UTF-8"),"UTF-8"))
  //                  .with("type_activity",type_activite_abreviation.getText())
                    .with("type_client",type_client.getValue())
                    .with("type_client_abreger",type_client_abreger.getText())
                    .with("city",city.getText())
                    .with("country",country.getText())
                    .with("fax",fax.getText())
                    .with("raison_social",raison.getText())
                    .with("mobile",mobile.getText())
                    .with("telephone",telephone.getText())
                    .with("reglement",reglement.getText())
                    .with("code_client",code)
                    .with("created", LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE));
           boolean state = this.mainApp.manager.SaveData(this.mainApp.mapObject,"customers");

            if (state){
               // customer.setActivity(type_activite.getValue().toString());
                customer.setCity(city.getText());
                customer.setCountry(country.getText());
                customer.setFax(fax.getText());
                customer.setLabel(raison.getText());
                customer.setMobile(mobile.getText());
                customer.setTelephone(telephone.getText());
                customer.setName(name.getText());
                customer.setReglement(reglement.getText());
                customer.setType_client(type_client.getValue().toString());
             //   customer.setActivity_abreger(type_activite_abreviation.getText());
                customer.setType_client_abreger(type_client_abreger.getText());
                customer.setCode_client(code_client.getText());

            }

            okClicked = true;
            this.dialogStage.close();

        }
    }

   @FXML
    private void handleCancel(){
            this.dialogStage.close();
        }

    private boolean isInputValid(){
        String errorMessage = "";
        if (name.getText() == null || name.getText().length() == 0)
             errorMessage += "Vous devrez renseigner un nom pour le responsable \n";
        if (raison.getText() == null || raison.getText().length() == 0)
            errorMessage += "Vous devrez renseigner le nom de l'entreprise \n";
        if (country.getText() == null || country.getText().length() == 0)
            errorMessage += "Vous devrez renseigner un pays \n";
        if (city.getText() == null || city.getText().length() == 0)
            errorMessage += "Vous devrez renseigner une ville  \n";
        if (telephone.getText() == null || telephone.getText().length() == 0)
            errorMessage += "Vous devrez renseigner un numero de telephone fixe \n";
        if (mobile.getText() == null || mobile.getText().length() == 0)
            errorMessage += "Vous devrez renseigner un numero mobile \n";
//        if (activity.getText() == null || activity.getText().length() == 0)
//            errorMessage += "Vous devrez renseigner l'activte de la société \n";
        if (reglement.getText() == null || reglement.getText().length() == 0)
            errorMessage += "Vous devrez renseigner un mode de reglement \n";

        if (errorMessage.length() ==0){
            return true;
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Champs Invalid");
            alert.setHeaderText("Corriger les erreurs svp");
            alert.setContentText(errorMessage);
            alert.showAndWait();
        }
            return false;
    }





}
