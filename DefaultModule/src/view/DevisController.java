package view;

import com.rethinkdb.net.Cursor;
import controller.BaseController;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Binding;
import javafx.beans.binding.ListBinding;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import main.MainApp;
import model.Customer;
import model.Operations;
import model.Products;
import model.Project;

import javax.xml.soap.Text;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Created by Univetech Sarl on 01/11/2016.
 */
public class DevisController  {

    @FXML
    private ComboBox listClient;
    @FXML
    private ComboBox<String> listProduit;
//    @FXML
//    private Label proformaNumero;
    @FXML
    private TextField proformaNumero;
    @FXML
    private Label proformaDate;
    @FXML
    private Label montantglobal;
    @FXML
    private DatePicker dateFinValidite;
    @FXML
    private DatePicker dateDeDemande;
    @FXML
    private TextField delaiDeLivraison;
    @FXML
    private TextField proformaName;
    @FXML
    private TextField avance;
    @FXML
    private TextField defaultclientname;
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

    //button
    @FXML
    private Button workforce;
    @FXML
    private Button transport;
    @FXML
    private Button printable;
    @FXML
    private Button save;
    @FXML
    private Button revision;

    @FXML
    private Label date_fin1;
    @FXML
    private Label date_demande2;
    @FXML
    private TextField partnersref;


    // private String numeroPro = "FPO";
    private long countnumber;
    private Boolean setRevision = false;
    private boolean setproformnumber = false;

    private String firstProformaNumber;

    private MainApp mainApp;
    private Stage stage;

    //project
    private Project project;

    //Products
    private ObservableList<String> productsObservableList = FXCollections.observableArrayList();
    private ObservableList<Products> productTableViewObservableList = FXCollections.observableArrayList();
    private HashMap<String, Products> productsMap = new HashMap<>();
    private HashMap<String, HashMap> productSaveMap = new HashMap<>();
    private HashMap<String, HashMap> saveMap = new HashMap<>();
    //client
    private ObservableList<String> clientStringObservableList = FXCollections.observableArrayList();
    private ObservableList<Customer> clientCustomerObservableList = FXCollections.observableArrayList();
    private HashMap<String, Customer> clientMap = new HashMap<>();
    //pour rgreouper les elments
    private HashMap<String, String> dataMap = new HashMap<>();
    //operation
    private Operations operations;
    //boolean to detect new or old devis
    private boolean newdevis = false;
    // deja enregistrer
    private boolean autosave = false;
    //devis modify
    private boolean modify = false;

    private SplitPane splitPane;
    private long  globalnumber = 0;
    private String id;
    private String has_code;
    private String hash_code_numero;
    private boolean isRevision = false;
    private boolean isproject  = false;


    //numero d'initialisation system pour les devis
    private int initcount = 88;


    public void isRevision(boolean state){this.isRevision = state;}
    public void isProject(boolean state){this.isproject = state; }


    public void setOperations(Operations operations) {

        this.operations = operations;
        if (this.operations != null) {
            //nouveau devis
            newdevis = true;
            save.setVisible(false);
            transport.setVisible(false);
            workforce.setVisible(false);
            printable.setVisible(false);
            dateFinValidite.setVisible(false);
            dateDeDemande.setVisible(false);
            avance.setEditable(false);
            delaiDeLivraison.setEditable(false);
            proformaName.setEditable(false);

            if (this.isRevision){
                revision.setVisible(true);
                setRevision = true;
                this.init();
                listProduit.setVisible(true);
            }

            setObservableList();



        } else {
            this.init();
        }
    }

    private void setObservableList() {

        Cursor cursor = this.mainApp.manager.getDataById("devis_options", this.operations.getId());
        Iterator iterator = cursor.iterator();
        HashMap obj = (HashMap) iterator.next();
        ArrayList map = (ArrayList) obj.get("clientmap");
        ArrayList d = (ArrayList) obj.get("datamap");
        this.has_code = obj.get("hash_code").toString();
        this.hash_code_numero = obj.get("hash_code_numero").toString();

         this.globalnumber = ((Long) obj.get("revision_number")) + 1;
        this.id = this.operations.getId();
        setAllData(map);
        HashMap m = (HashMap) d.get(0);
        Collection collection = m.values();
       
         boolean parent = false;boolean enfant = false;int parentcount = 0;

         if (this.isproject) {
              //recuperation de l'entente
                if (!parent) {
                        String rubrique = null;
                        String sousrubrique = null;

                            Iterator it = collection.iterator();
                            while(it.hasNext()){
                                 HashMap sol = (HashMap) it.next();
                                    if (sol.get("name").toString().equals("FOURNITURES")) {
                                        parent = true;
                                        try{
                                           rubrique     =  sol.get("rubrique").toString();
                                           sousrubrique  = sol.get("sousrubirque").toString();
                                        }catch (Exception e){}


                                        this.productTableViewObservableList.add(
                                            new Products(
                                                    sol.get("name").toString(), sol.get("reference").toString(),
                                                    sol.get("quantite").toString(), sol.get("prix").toString(),
                                                    sol.get("marge").toString(), sol.get("remise").toString(),
                                                    sol.get("montant").toString(), "000",rubrique,sousrubrique
                                            ));
                                    break;

                                    }
                                }
                }

                Iterator iterator2 = collection.iterator();
                HashMap contener = new HashMap();
                while (iterator2.hasNext()) {
                    if (enfant)  break;
                    HashMap ds = (HashMap) iterator2.next();
                    String remise = ds.get("remise").toString();
                    String rubrique = null;
                    String sousrubrique = null;

                    try{
                       rubrique     =  ds.get("rubrique").toString();
                       sousrubrique  = ds.get("sousrubirque").toString();
                    }catch (Exception e){}


                    if (parent && !enfant) {

                        if (ds.get("name").toString().equals("MATERIELS ET ACCESSOIRES") || ds.get("name").toString().equals("TRAVAUX")) {
                            if (!contener.containsKey(ds.get("name").toString())){
                                contener.put(ds.get("name").toString(),ds.get("name").toString());
                                String defaultNoeud = ds.get("name").toString().toLowerCase();
                                this.productTableViewObservableList.add(
                                new Products(
                                        ds.get("name").toString(), ds.get("reference").toString(),
                                        ds.get("quantite").toString(), ds.get("prix").toString(),
                                        ds.get("marge").toString(), ds.get("remise").toString(),
                                        ds.get("montant").toString(), "000",rubrique,sousrubrique
                                ));

                                parentcount++;
                                if (parentcount == 2) enfant = true;

                            Collection clc = m.values();
                            Iterator it = clc.iterator();

                            while (it.hasNext()) {
                                //Initialisation d'un element produit
                                HashMap dsv = (HashMap) it.next();
                                //tout d'abord on recherche le noeud racine
                                String sbr = null;
                                String rub = null;
                                 try{
                                       sbr  = dsv.get("sousrubirque").toString();
                                       rub  = dsv.get("rubrique").toString();
                                    String childrenNoeud = sbr.toLowerCase();
                                    if (!dsv.get("name").toString().equals("FOURNITURES") &&
                                       !dsv.get("name").toString().equals("MATERIELS ET ACCESSOIRES") 
                                       && !dsv.get("name").toString().equals("TRAVAUX") && defaultNoeud.equals(childrenNoeud)) {
                                        this.productTableViewObservableList.add(
                                        new Products(
                                                dsv.get("name").toString(), dsv.get("reference").toString(),
                                                dsv.get("quantite").toString(), dsv.get("prix").toString(),
                                                dsv.get("marge").toString(), dsv.get("remise").toString(),
                                                dsv.get("montant").toString(), "000",rub,sbr
                                        ));
                                    }

                                    }catch (Exception e){sbr="";rub="";}

                                
                            }
                        }
                               
                               
                               

                            }

                        }

                    
                       
                    try {
                        double v = Double.parseDouble(ds.get("marge").toString());
                    } catch (NumberFormatException e){
                        this.productSaveMap.put(ds.get("name").toString(),ds);
                    }
                }
        }else{
            
            Iterator iterator2 =  collection.iterator();
                while (iterator2.hasNext()){
                    HashMap ds = ( HashMap) iterator2.next();
                    //System.out.println(ds);
                    this.productTableViewObservableList.add(
                                new Products(
                                        ds.get("name").toString(), ds.get("reference").toString(),
                                        ds.get("quantite").toString(), ds.get("prix").toString(),
                                        ds.get("marge").toString(), ds.get("remise").toString(),
                                        ds.get("montant").toString(), "000","",""
                    ));
                }

        }

    }

    private void setAllData(ArrayList ar) {
        Iterator iterator = ar.iterator();


        HashMap m = (HashMap) ar.get(0);
        if (setRevision){
            firstProformaNumber = m.get("defaultnumero").toString();
            proformaNumero.setText(""); proformaNumero.setText(firstProformaNumber+"/Rev_"+this.globalnumber);
            dataMap.put("proformaNumero", firstProformaNumber+"/Rev_"+this.globalnumber);
            dataMap.put("defaultnumero", firstProformaNumber);
        }else {
            proformaNumero.setText(String.valueOf(m.get("defaultnumero")));
            dataMap.put("proformaNumero", m.get("defaultnumero").toString());
            dataMap.put("defaultnumero", m.get("defaultnumero").toString());
        }

        proformaDate.setText(String.valueOf(m.get("currentdate")));
        proformaName.setText(String.valueOf(m.get("proformaName")));
        avance.setText(String.valueOf(m.get("avance")));
        delaiDeLivraison.setText(String.valueOf(m.get("delai_de_livraison")));
        date_fin1.setText(m.get("date_fin_demande").toString());
        date_demande2.setText(m.get("date_demande").toString());
        date_demande2.setVisible(true);
        date_fin1.setVisible(true);
        listClient.setVisible(false);
        defaultclientname.setVisible(true);
        defaultclientname.setText((String) m.get("clientname"));
        defaultclientname.setEditable(false);
        dataMap.put("clientraison",(String) m.get("clientname"));
        if (isproject && isRevision){
            delaiDeLivraison.setText(m.get("nombreJour").toString()+" Jour (s) ");
        }

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        dataMap.put("currentdate",proformaDate.getText());
        dataMap.put("date_fin_demande",m.get("date_fin_demande").toString());
        dataMap.put("date_demande", m.get("date_demande").toString());
        dataMap.put("delai_de_livraison", String.valueOf(m.get("delai_de_livraison")));
        dataMap.put("proformaName", String.valueOf(m.get("proformaName")));

        dataMap.put("clientname",(String) m.get("clientname") );
        dataMap.put("clientraison",(String) m.get("clientraison") );
        dataMap.put("clientcode", m.get("clientcode").toString());
        dataMap.put("clienttelephone", m.get("clienttelephone").toString());
        String fax = null;
        try {
             fax = m.get("clientfax").toString();
        } catch (NullPointerException e){
            //e.printStackTrace();
        }
        if (fax == null)
            fax =  m.get("clienttelephone").toString();
        dataMap.put("clientfax", fax);
        dataMap.put("avance", String.valueOf(m.get("avance")));


    }

    
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor = this.mainApp.manager.getAllData("products");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()) {
            HashMap<Products, String> d = (HashMap<Products, String>) iterator.next();
            productsMap.put(
                    d.get("name").toString()
                    ,
                    new Products(
                            d.get("name").toString(),
                            d.get("reference"),
                            String.valueOf(d.get("quantite")),
                            String.valueOf(d.get("prixu")),
                            String.valueOf(d.get("marge")),
                            String.valueOf(d.get("remise")),
                            String.valueOf(d.get("montant")),
                            d.get("id").toString(),"",""
                    )
            );


            this.productsObservableList.add(d.get("name").toString());
        }
        cursor = this.mainApp.manager.getAllData("customers");
        iterator = cursor.iterator();
        while (iterator.hasNext()) {
            HashMap<Customer, String> d = (HashMap<Customer, String>) iterator.next();
            clientStringObservableList.add(d.get("raison_social"));
            clientMap.put(
                    d.get("raison_social"),
                    new Customer(
                            d.get("name"),
                            d.get("raison_social"),
                            d.get("telephone"),
                            d.get("fax"),
                            d.get("country"),
                            d.get("city"),
                            d.get("activity"),
                            d.get("mobile"),
                            d.get("id"),
                            d.get("reglement"),
                            d.get("type_activity"),
                            d.get("type_client"),
                            d.get("type_client_abreger"),
                            d.get("code_client")
                    )
            );
        }


    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void init() {
        desig.setCellFactory(TextFieldTableCell.forTableColumn());
        qte.setCellFactory(TextFieldTableCell.forTableColumn());
        prix.setCellFactory(TextFieldTableCell.forTableColumn());
        marge.setCellFactory(TextFieldTableCell.forTableColumn());
        remise.setCellFactory(TextFieldTableCell.forTableColumn());
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
        defaultclientname.setVisible(false);
        date_demande2.setVisible(false);
        date_fin1.setVisible(false);
        revision.setVisible(false);
        //printable.setText("Apercu");
        printable.setVisible(false);
        save.setVisible(false);
        workforce.setVisible(false);
        transport.setVisible(false);

        listClient.setEditable(false);
       // listProduit.setEditable(false);


        listProduit.setOnKeyTyped(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                System.out.print(event.getText());
            }
        });
        listProduit.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                String s = (String) listProduit.getSelectionModel().getSelectedItem();
                System.out.print(s);
//                Predicate predicate = new Predicate() {
//                    @Override
//                    public boolean test(Object o) {
//                        if (s.equals(o))
//                            return true;
//                        return false;
//                    }
//                };
                Comparator<String> comparator = Comparator.comparing(s1 -> s1.matches(s));

                listProduit.getItems().sorted(comparator);
            }
        });


        revision.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Alert alerts = new Alert(Alert.AlertType.CONFIRMATION);
                alerts.initOwner(stage);
                alerts.setTitle("Revision du devis");
                alerts.setHeaderText("Valider la revision");
                alerts.setContentText("Voulez-vous vraiment revisez ce devis ? ");
                alerts.showAndWait();
                if (alerts.getResult().getButtonData().isDefaultButton()) {
                   
                    dataMap.put("revision_number", String.valueOf(globalnumber));
                    try {
                         setDataMap();
                         SaveHandler();
                        if (isproject) {
                            //revision project
                           mainApp.viewPrintActionRevision(null, productTableViewObservableList, dataMap, null, stage,null,isproject);

                        }else{
                            //revision devis simple

                           mainApp.viewPrintActionDevisSimple(null, productTableViewObservableList, dataMap, null, stage,null);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new WindowZone("Creation du devis","Nous ne pouvons pas creer ce devis","Erreur detecter",true,stage);

                    }
                }
            }
        });


        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (event.getNewValue() != event.getOldValue()) {
                    long quantite = Math.round(Double.parseDouble(event.getNewValue()));

                    double marge = Double.parseDouble(event.getRowValue().getMarge())/100;
                    double prixu = Double.parseDouble(event.getRowValue().getPrix()) * (1+marge);

                    double montant =(prixu * quantite * (1- (Double.parseDouble(event.getRowValue().getRemise()) / 100)));


                    event.getRowValue().setMontantht(String.valueOf(Math.round(montant)));
                    event.getRowValue().setQte(String.valueOf(quantite));

                }
            }
        });

        prix.setEditable(true);

        prix.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {

                if (event.getNewValue() != event.getOldValue()) {
                    long quantite = Math.round(Double.parseDouble(event.getRowValue().getQte()));

                    double marge = Double.parseDouble(event.getRowValue().getMarge())/100;
                    double prixu = Double.parseDouble(event.getNewValue()) * (1+marge);

                    double montant =(prixu * quantite * (1- (Double.parseDouble(event.getRowValue().getRemise()) / 100)));


                    event.getRowValue().setMontantht(String.valueOf(Math.round(montant)));
                    event.getRowValue().setPrix(String.valueOf(event.getNewValue()));

                }

            }
        });

        marge.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (event.getNewValue() != event.getOldValue()) {
                    long quantite = Math.round(Double.parseDouble(event.getRowValue().getQte()));
                    //formule
                    /*
                    montant = pU*marge*qte*(1-remise/100)
                     */
                    double marge = Double.parseDouble(event.getNewValue())/100;
                    double prixu = Double.parseDouble(event.getRowValue().getPrix()) * (1+marge);

                    double montant =(prixu * quantite * (1- (Double.parseDouble(event.getRowValue().getRemise()) / 100)));
                    event.getRowValue().setMontantht(String.valueOf(Math.round(montant)));
                    event.getRowValue().setMarge(event.getNewValue());

                }

            }
        });


        remise.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (event.getNewValue() != event.getOldValue()) {
                    //formule
                    // qte * pU * (1- %) * marge

                    /**
                     * Pirx u = pu * (1+ marge/100)
                     * remise = (1- remise/100))
                     */

                    long quantite = Math.round(Double.parseDouble(event.getRowValue().getQte()));

                    double marge = Double.parseDouble(event.getRowValue().getMarge())/100;
                    double prixu = Double.parseDouble(event.getRowValue().getPrix()) * (1+marge);

                    double remise = (1- (Double.parseDouble(event.getNewValue()) / 100));

                    double montant =(prixu * quantite * remise);

                    event.getRowValue().setMontantht(String.valueOf(Math.round(montant)));
                    event.getRowValue().setRemise(event.getNewValue());

                }

            }
        });


        this.productsTableView.setItems(this.productTableViewObservableList);
        this.listClient.setItems(this.clientStringObservableList);
        listProduit.setItems(this.productsObservableList);

        listProduit.valueProperty().addListener((observable, oldValue, newValue) -> {
            //montantglobal.setText();
            Products s = productsMap.get(newValue);
            if (s != null) {
                    if (Double.isNaN(Double.parseDouble(montantglobal.getText())))
                        montantglobal.setText(productsMap.get(newValue).getMontantht());
                    else {
                        montantglobal.setText(String.valueOf(Double.parseDouble(montantglobal.getText()) + (Double.parseDouble(productsMap.get(newValue).getMontantht()))));
                    }


                    if (productTableViewObservableList.size() <= 0) {
                        productTableViewObservableList.add(productsMap.get(newValue));
                    } else {
                        Iterator<Products> iterator = productTableViewObservableList.iterator();
                        boolean isExist = false;

                        while (iterator.hasNext()) {
                            Products p = iterator.next();
                            if (p.getName().equals(newValue)) {
                                isExist = true;
                                break;
                            }
                        }

                        if(!isExist){
                            Products products = (Products) productsMap.get(newValue);
                            HashMap cmap = new HashMap();
                            cmap.put("name", products.getName());
                            cmap.put("reference", products.getRef());
                            cmap.put("quantite", products.getQte());
                            cmap.put("prix", products.getPrix());
                            cmap.put("marge", products.getMarge());
                            cmap.put("remise", products.getRemise());
                            cmap.put("montant", products.getMontantht());
                            if (isproject) {
                                cmap.put("rubrique", "FOURNITURES");
                                cmap.put("sousrubirque", "MATERIELS ET ACCESSOIRES");
                            }
                            products.setRubrique("FOURNITURES");
                            products.setSousrubrique("MATERIELS ET ACCESSOIRES");

                            productTableViewObservableList.add(productsMap.get(newValue));
                            productSaveMap.put(newValue, cmap);
                          //  break;
                        }
                    }
                }

                if (!setRevision)
                    printable.setVisible(true);
                if (this.autosave)
                    this.modify = true;
        });

        listClient.valueProperty().addListener((observable, oldValue, newValue) -> {
            Customer customer = clientMap.get(newValue);

            dataMap.put("clientname", customer.getLabel());
            dataMap.put("clientraison", customer.getName());
            dataMap.put("clientcode", customer.getCode_client());
            dataMap.put("clienttelephone", customer.getTelephone());
            dataMap.put("clientfax", customer.getFax());
            dataMap.put("username",this.mainApp.getUserRole().getUsername());

             if (!setproformnumber && !setRevision){

                 countnumber = this.mainApp.manager.countAll("devis_options");
                 countnumber = countnumber + initcount;
                 String numeroPro = "FPO";
                 numeroPro += (countnumber );
                 numeroPro += "/" + LocalDate.now().getMonth().getValue() + "/" + LocalDate.now().getYear() + "/" + customer.getActivity_abreger() + "_" + customer.getType_client_abreger();
                 proformaNumero.setText(numeroPro);
                 setproformnumber = true;
             }else{
                 //chercher si le proforma a realiser une revision deja
                 this.globalnumber = 0;
             }

        });


        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        proformaDate.setText(sdf.format(date));

        workforce.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainApp.MaindoeuvreAction(productTableViewObservableList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        transport.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    mainApp.TransportationAction(productTableViewObservableList);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        printable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               setDataMap();
                SaveHandler();
               try {
                   dataMap.put("revision_number", String.valueOf(globalnumber));
                   dataMap.put("partneref",partnersref.getText());
                    mainApp.viewPrintActionDevisSimple(null, productTableViewObservableList, dataMap, null, stage,null);
                } catch (Exception e) {
                    new WindowZone("Creation du devis","Nous ne pouvons pas creer ce devis","Erreur detecter",true,stage);

                }

            }
        });

        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new WindowZone("System corrompu","Nous avons des raison de croire que votre system est corrompu","Veuillez rapidement contactez vous administrateur",true,stage);
                System.exit(1);
            }
        });

    }

    private void SaveHandler() {
        Iterator iterator = productTableViewObservableList.iterator();
        while (iterator.hasNext()){
            Products products = (Products) iterator.next();
            HashMap cmap = new HashMap();
            cmap.put("name", products.getName());
            cmap.put("reference", products.getRef());
            cmap.put("quantite", products.getQte());
            cmap.put("prix", products.getPrix());
            cmap.put("marge", products.getMarge());
            if (products.getRemise() == "0")
                cmap.put("remise", " ");
            else
                cmap.put("remise", products.getRemise());

            cmap.put("montant", products.getMontantht());
            try{
                cmap.put("rubrique",products.getRubrique());
                cmap.put("sousrubirque",products.getSousrubrique());
            } catch (NullPointerException as){}
           
            saveMap.put(products.getName(),cmap);
        }

        if (!newdevis) {
            if (mainApp.manager.getDevisOptions(proformaNumero.getText()) > 0) {
                mainApp.manager.deleteDataDevis("devis_options", proformaNumero.getText());
            }else{
                if (!autosave || modify){
                    if (modify)
                        mainApp.manager.deleteDataDevis("devis_options", proformaNumero.getText());

                      String  hasvalue =
                              dataMap.get("proformaName").toString()
                                    + dataMap.get("clientname").toString()
                                    + dataMap.get("clienttelephone").toString();
                       byte[] h = null;
                    try {
                        MessageDigest md = MessageDigest.getInstance("MD5");
                        md.reset();
                        md.update(hasvalue.getBytes("UTF-8"));
                        h = md.digest();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    mainApp.mapObject =
                            ConnectionManager.r.hashMap("name", proformaName.getText())
                            .with("clientmap", ConnectionManager.r.array(dataMap))
                            .with("datamap", ConnectionManager.r.array(saveMap))
                            .with("devis", true)
                            .with("hash_code",h.toString())
                            .with("hash_code_numero",hasvalue.hashCode())
                            .with("devis_project", false)
                            .with("revison", false).with("revision_number",this.globalnumber).with("project_ref", null);
                    mainApp.manager.SaveData(mainApp.mapObject, "devis_options");
                    this.autosave = true;
                }

            }




        }else{


            if (isRevision)
            {

                mainApp.mapObject = ConnectionManager.r.hashMap("name", proformaName.getText())
                        .with("clientmap", ConnectionManager.r.array(dataMap))
                        .with("datamap", ConnectionManager.r.array(saveMap))
                        .with("devis", true).with("revison", true)
                        .with("project",isproject)
                        .with("client",dataMap.get("clientcode"))
                        .with("clientcode",dataMap.get("clientcode"))
                        .with("default_devis_number",firstProformaNumber)
                        .with("revision_number",this.globalnumber)
                        .with("devis_options", this.id);
                mainApp.manager.SaveData(mainApp.mapObject, "revisions");
                mainApp.mapObject = null;
                mainApp.mapObject =ConnectionManager.r
                        .hashMap("name", proformaName.getText())
                        .with("datamap", ConnectionManager.r.array(saveMap))
                        .with("revison", true)
                        .with("revision_number",this.globalnumber);
                mainApp.manager.UpdateData(mainApp.mapObject,"devis_options",this.id);

            }else{
                new WindowZone("System corrompu","Nous avons des raison de croire que votre system est corrompu","Veuillez rapidement contactez vous administrateur",true,stage);
                System.exit(1);
            }
            

        }


    }

    private void setDataMap(){

        if (!setRevision)
        {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String ddd = dateDeDemande.getValue().toString();
            String[] dv = ddd.split("-");
            String newdate = dv[2]+"-"+dv[1]+"-"+dv[0];
            dataMap.put("date_demande",newdate);
            ddd = dateFinValidite.getValue().toString();
             dv = ddd.split("-");
             newdate = dv[2]+"-"+dv[1]+"-"+dv[0];
            dataMap.put("date_fin_demande",newdate);


            dataMap.put("proformaNumero", proformaNumero.getText());
            dataMap.put("defaultnumero", proformaNumero.getText());
            dataMap.put("currentdate", proformaDate.getText());
            //dataMap.put("date_demande",sdf.format(dateDeDemande.getValue()));
            dataMap.put("delai_de_livraison", delaiDeLivraison.getText());
            dataMap.put("nombreJour", delaiDeLivraison.getText());
            dataMap.put("proformaName", proformaName.getText());
            dataMap.put("avance", avance.getText());

        }else{
            dataMap.put("nombreJour", delaiDeLivraison.getText());
        }
    }


    private void autosave(HashMap hashMap){


        HashMap map = new HashMap();
        map.put("proformaNumero",proformaNumero.getText());
        map.put("defaultnumero",proformaNumero.getText());
        map.put("proformaDate",hashMap.get("proformaDate").toString());
        map.put("currentdate", proformaDate.getText());
        map.put("delai_de_livraison", delaiDeLivraison.getText());

        map.put("nombreJour", delaiDeLivraison.getText());
        map.put("proformaName", proformaName.getText());
        map.put("avance", avance.getText());

//       map.put("montantglobal",hashMap.get("montantglobal").toString());
//       map.put("datefinvalidite",hashMap.get("datefinvalidite").toString());
//       map.put("datededemande",hashMap.get("datededemande").toString());
//       map.put("delaidelivraison",hashMap.get("delaidelivraision").toString());
//       map.put("proformaName",hashMap.get("proformaName").toString());
//       map.put("avance",hashMap.get("avance").toString());
//       map.put("defaultclientname",hashMap.get("defaultclientname").toString());

    }
}