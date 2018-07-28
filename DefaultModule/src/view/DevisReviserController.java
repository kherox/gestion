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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.MainApp;
import model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 01/11/2016.
 */
public class DevisReviserController {


    @FXML
    private TableView<Products> productsTableView;
    @FXML
    private TableColumn<Products, String> ref;
    @FXML
    private TableColumn<Products, String> desig;
    @FXML
    private TableColumn<Products, String> qte;
    @FXML
    private TableColumn<Products, String> prix;
    @FXML
    private TableColumn<Products, String> remise;
    @FXML
    private TableColumn<Products, String> marge;
    @FXML
    private TableColumn<Products, String> montantHt;

    @FXML
    private Label client;
    @FXML
    private  Label proforma;
    @FXML
    private Label reference;
    @FXML
    private Label datedemande;
    @FXML
    private Label  delaidelivraison;


    private MainApp mainApp;
    private Stage stage;

    private Revision categories;

    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();

    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp;}

    public void setStage(Stage stage) {this.stage = stage;}


    public void setCategories(Revision categories) {

        this.categories = categories;

        Cursor cursor = this.mainApp.manager.getDataById("revisions",categories.getId());

        HashMap hashMap = (HashMap) cursor.next();
        ArrayList list = (ArrayList)  hashMap.get("datamap");
        HashMap mp = (HashMap) list.get(0);
        //HashMap map = (HashMap) hashMap.get("datamap");
        Collection collection = mp.values();
        Iterator iterator = collection.iterator();

        /*String name, String ref, String qte, String prix,String marge, String remise,String montantht, String id*/
        /*marge,montant,name,prix,quantite,reference,remise*/

       /* @FXML
        private Label client;
        @FXML
        private  Label proforma;
        @FXML
        private Label reference;
        @FXML
        private Label datedemande;
        @FXML
        private Label  delaidelivraison;*/

        ArrayList lst = (ArrayList)  hashMap.get("clientmap");
        HashMap m1 = (HashMap) lst.get(0);
        client.setText(m1.get("clientname").toString());
        proforma.setText(hashMap.get("default_devis_number").toString()+ "/"+m1.get("proformaNumero").toString());
        reference.setText(m1.get("proformaName").toString());
        datedemande.setText(m1.get("date_demande").toString());
        delaidelivraison.setText(m1.get("delai_de_livraison").toString());

        while (iterator.hasNext()){
            HashMap h1 = (HashMap)  iterator.next();
            this.productsObservableList.add(
                    new Products(
                            h1.get("name").toString(),
                            h1.get("reference").toString(),
                            h1.get("quantite").toString(),
                            h1.get("prix").toString(),
                            h1.get("marge").toString(),
                            h1.get("remise").toString(),
                            String.valueOf(
                                    (Double.parseDouble(h1.get("prix").toString()) * (Double.parseDouble(h1.get("marge").toString())) * Double.parseDouble(h1.get("quantite").toString()))
                                    -
                                    (Double.parseDouble(h1.get("prix").toString()) * (Double.parseDouble(h1.get("remise").toString())))
                            ),
                            ""
                    )
            );

            /*desig;qte;prix;remise;marge;montantHt;*/
        }




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
        this.productsTableView.setItems(this.productsObservableList);
    }




}