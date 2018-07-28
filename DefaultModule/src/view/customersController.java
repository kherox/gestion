package view;

import com.rethinkdb.net.Cursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.Customer;

import java.util.HashMap;

/**
 * Created by Univetech Sarl on 03/10/2016.
 */
public class customersController {

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer,String> nameCustomerStringTableColumn;
    @FXML
    private TableColumn<Customer,String> contactCustomerStringTableColumn;

    @FXML
    private Label name;
    @FXML
    private Label raison;
    @FXML
    private Label activity;
    @FXML
    private Label id;
    @FXML
    private Label country;
    @FXML
    private Label city;
    @FXML
    private Label mobile;
    @FXML
    private Label telephone;
    @FXML
    private Label reglement;
    @FXML
    private Label fax;

    @FXML
    private Button edit;
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button quitter;

    private MainApp mainApp;
    private Stage stage;
    private String doc;
    private ObservableList<Customer> customerObservableList = FXCollections.observableArrayList();

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    private void initialize(){

           quitter.setOnAction(new EventHandler<ActionEvent>() {
               @Override
               public void handle(ActionEvent event) {
                   mainApp.closeCurrentWindow(stage);
               }
           });
           
        nameCustomerStringTableColumn.setCellValueFactory(param -> param.getValue().labelProperty());
        contactCustomerStringTableColumn.setCellValueFactory(param -> param.getValue().telephoneProperty());
        this.showCustomerDetail(null);
        customerTableView.refresh();
        customerTableView.getSelectionModel().selectedItemProperty().addListener((observable,oldvalue,newvalue) -> showCustomerDetail(newvalue));

    }

/*
    private ObservableList<Customer> getCustomers(){
        try (Cursor cursor = this.mainApp.manager.getAllData("customers")) {
            for (Object doc: cursor) {
                HashMap<Customer,String> d = (HashMap<Customer,String>) doc;
                 this.customerObservableList.add(
                         new Customer(
                                 d.get("name"),d.get("raison_social"),
                                 d.get("telephone"),d.get("fax"),
                                 d.get("country"),d.get("city"),
                                 d.get("activity"),d.get("mobile"),
                                 d.get("id"),d.get("reglement"),
                                 d.get("activity_abreger"),
                                 d.get("type_client"),
                                 d.get("type_client_abreger"),
                                 d.get("code_client")
                         )
                 );
            }
            return this.customerObservableList;
        }
    }*/

    //suppression de person
    @FXML
    private void HandlerDeleteCustomer(){
        Alert alerts = new Alert(Alert.AlertType.CONFIRMATION);
        alerts.initOwner(this.stage);
        alerts.setTitle("Confirmation");
        alerts.setHeaderText("Validation de la suppression");
        alerts.setContentText("Voulez-vous vraiment supprimer ce client");
        alerts.showAndWait();
        if (alerts.getResult().getButtonData().isDefaultButton()){
            Customer customer = null;
            int selectedId = customerTableView.getSelectionModel().getSelectedIndex();
            if (selectedId >=0){
                customer =       customerTableView.getItems().get(customerTableView.getSelectionModel().getSelectedIndex());
                this.mainApp.manager.deleteData("customers",customer.getId());
                customerTableView.getItems().remove(customerTableView.getSelectionModel().getSelectedIndex());
            }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initOwner(this.stage);
                alert.setTitle("Suppression impossible");
                alert.setHeaderText("Aucun client");
                alert.setContentText("Veuillez selectionner un client avant la suppression");
                alert.showAndWait();
            }
        }




    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        try (Cursor cursor = this.mainApp.manager.getAllData("customers")) {
            for (Object doc : cursor) {
                HashMap<Customer, String> d = (HashMap<Customer, String>) doc;
                this.customerObservableList.add(
                        new Customer(
                                d.get("name"), d.get("raison_social"),
                                d.get("telephone"), d.get("fax"),
                                d.get("country"), d.get("city"),
                                d.get("type_client"), d.get("mobile"),
                                d.get("id"), d.get("reglement"),
                                d.get("activity_abreger"),
                                d.get("type_client"),
                                d.get("type_client_abreger"),
                                d.get("code_client")
                        )
                );
            }

        }
//        if(mainApp.getUserRole().getRole().equals("admin")){
//            edit.setVisible(true);
//            add.setVisible(true);
//            delete.setVisible(true);
//        }else{
//            edit.setVisible(false);
//            add.setVisible(false);
//            delete.setVisible(false);
//        }
        customerTableView.setItems(this.customerObservableList);
    }

    public void showCustomerDetail(Customer customer){

        if (customer != null){
            this.name.setText(customer.getName());
            this.raison.setText(customer.getLabel());
            this.activity.setText(customer.getActivity());
            this.fax.setText(customer.getFax());
            this.country.setText(customer.getCountry());
            this.city.setText(customer.getCity());
            this.telephone.setText(customer.getTelephone());
            this.reglement.setText(customer.getReglement());
            this.mobile.setText(customer.getMobile());
            this.id.setText(customer.getCode_client());
        }else {

            this.name.setText("");
            this.raison.setText("");
            this.activity.setText("");
            this.fax.setText("");
            this.country.setText("");
            this.city.setText("");
            this.telephone.setText("");
            this.reglement.setText("");
            this.mobile.setText("");
            this.id.setText("");
        }

    }

    @FXML
    private void handleNewCustomer(){
        Customer customer = new Customer();
        boolean okClicked = mainApp.showPersonEditDialog(customer);
        if (okClicked)
            this.customerObservableList.add(customer);
    }

    @FXML
    private void handleEditCustomer(){
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        if (customer != null){
            boolean okClicked = mainApp.showPersonEditDialog(customer);
            if (okClicked)
                this.showCustomerDetail(customer);

        }else{
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(this.stage);
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }
}
