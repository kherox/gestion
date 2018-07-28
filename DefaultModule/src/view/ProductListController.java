package view;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;

import java.util.HashMap;

/**
 * Created by Univetech Sarl on 02/11/2016.
 */
public class ProductListController {

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
    private TableColumn<Products,String> remise; // fournisseurs
    @FXML
    private TableColumn<Products,String> marge; //type
    @FXML
    private TableColumn<Products,String> montantHt;
    @FXML
    private TableColumn<Products,String> id;
    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
    @FXML
    private Button deletebutton;

    private MainApp mainApp;
    private Stage stage;
    private  ContextMenu menu;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        for(Object obj : this.mainApp.manager.getAllData("products")){
            HashMap<Products,String> d = (HashMap<Products,String> ) obj;
                this.productsObservableList.add(
                        new Products(
                            d.get("name").toString(),
                            d.get("reference"),
                            String.valueOf(d.get("quantite")),
                            String.valueOf(d.get("prixu")),
                            String.valueOf(d.get("materiel_type")),
                            String.valueOf(d.get("fournisseur")),
                            String.valueOf(d.get("montant")),
                            d.get("id").toString()
                        )
                );




        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;

        menu = new ContextMenu();
        menu.getItems().add(new MenuItem("Supprimer"));

//        menu.setHeight(45);
//        menu.setWidth(140);
//        menu.requestFocus();

        menu.getItems().forEach(menuItem -> menuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Alert alerts = new Alert(Alert.AlertType.CONFIRMATION);
                alerts.initOwner(stage);
                alerts.setTitle("Confirmation");
                alerts.setHeaderText("Validation de la suppression");
                alerts.setContentText("Voulez-vous vraiment supprimer ce produits");
                alerts.showAndWait();
                if (alerts.getResult().getButtonData().isDefaultButton()){
                    if (!productsTableView.getSelectionModel().getSelectedItem().getId().isEmpty()){
                        mainApp.manager.deleteData("products",productsTableView.getSelectionModel().getSelectedItem().getId());
                        //mainApp.closeCurrentWindow(stage);
                    }
                }

            }
        }));



        this.stage.getScene().setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                try {
                    if (!productsTableView.getSelectionModel().getSelectedItem().getId().isEmpty()) {
                        menu.show(stage);
                    }
                } catch (NullPointerException e){}

            }
        });



    }

    @FXML
    private void initialize() {
        desig.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        prix.setCellValueFactory(param -> param.getValue().prixProperty());
        marge.setCellValueFactory(param -> param.getValue().margeProperty());
        remise.setCellValueFactory(param -> param.getValue().remiseProperty());
        montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());
        id.setCellValueFactory(param -> param.getValue().idProperty());


        this.deletebutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alerts = new Alert(Alert.AlertType.CONFIRMATION);
                alerts.initOwner(stage);
                alerts.setTitle("Confirmation");
                alerts.setHeaderText("Validation de la suppression");
                alerts.setContentText("Voulez-vous vraiment supprimer ce produits");
                alerts.showAndWait();
                if (alerts.getResult().getButtonData().isDefaultButton()){
                    if (!productsTableView.getSelectionModel().getSelectedItem().getId().isEmpty()){
                        mainApp.manager.deleteData("products",productsTableView.getSelectionModel().getSelectedItem().getId());
                        //mainApp.closeCurrentWindow(stage);
                    }
                }

            }
        });




        this.productsTableView.setItems(this.productsObservableList);





     this.productsTableView.setRowFactory(param -> {
         TableRow<Products> p = new TableRow<Products>();
         p.setOnMouseClicked(event -> {
             if (event.getClickCount() == 2 && !p.isEmpty()){
                 Products products = p.getItem();
                 try {
                     mainApp.AddProductAction(products);
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             }
         });
         return p;
     });
    }

}
