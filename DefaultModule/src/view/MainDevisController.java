package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Univetech Sarl on 16/11/2016.
 */
public class MainDevisController {
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
    private TableColumn<Products, String> id;
    @FXML
    private Button generate;
    @FXML
    private Button delete;

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
    private Label datecurent;
    @FXML
    private Label proforma;
    @FXML
    private DatePicker demande_date;
    @FXML
    private DatePicker date_livraison;
    @FXML
    private TextField avance;
    @FXML
    private TextField nombrejour; 
    @FXML
    private TextField  destinataire;
    @FXML
    private Label d1;
    @FXML
    private Label d2;

    private HashMap primarymap = new HashMap();
    private HashMap printmap = new HashMap();


    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
    private ObservableList<Products> printProductListe = FXCollections.observableArrayList();

    private ObservableList<Products> selectedProduct;
    private ObservableList<Products> AllProductses;

    private MainApp mainApp;
    private Stage stage;
    private Project project;

    double AllMontant = 0.0;
    double selectedMontant = 0.0;
    private boolean revision = false;


    public void setSelectedProduct(ObservableList<Products> selectedProduct) {
        if (selectedProduct != null){
            this.selectedProduct = selectedProduct;
            for (Products products : selectedProduct){
                selectedMontant += Double.parseDouble(products.getMontantht());
                primarymap.put(products.getName(),products);
            }
        }else{
            this.selectedProduct = FXCollections.observableArrayList();
        }


    }

    public void setAllProductses(ObservableList<Products> allProductses) {
        if (allProductses != null){
            this.AllProductses = allProductses;
            for (Products products : this.AllProductses)
                try {
                    AllMontant += Double.parseDouble(products.getMontantht());
                } catch (NullPointerException e) {
                    //e.printStackTrace();
                    }

            double montant = AllMontant-selectedMontant;
            double newv = montant - (montant*(0.05));
            if (montant > 0)
            this.selectedProduct.add(new Products("Support et accessoires","SPA","1",java.lang.String.valueOf(montant),"1",
                    "5",java.lang.String.valueOf(newv),"2","Fournitures","Materiels et Accessoires"));
        }else{
            this.AllProductses = FXCollections.observableArrayList();
        }

    }

    private double setQuantity(String quantite){
        double newv;
        if (quantite.contains("%")){
            String[] d = quantite.split("%");
            newv = Double.parseDouble(d[0]);
        }else
            newv = Double.parseDouble(quantite);
        return newv;
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProject(Project project) {
        if (project != null) {


            this.project = project;
            if (project.getState().equals("true")){
                this.revision = true;
            }



            Products products = new Products();
            products = new Products("Travaux".toUpperCase(), "", "", "", "", "", "", "","Fournitures","Travaux");
            this.selectedProduct.add(products);
            Cursor cursor = this.mainApp.manager.getDataById("projects", this.project.getId());
            HashMap hashMap = (HashMap) cursor.next();

           // System.out.println(hashMap);

            nombrejour.setText(hashMap.get("nombreJour").toString());
            destinataire.setText(hashMap.get("personneabout").toString());


            ArrayList ar = (ArrayList) hashMap.get("client");
            HashMap client = (HashMap) ar.get(0);
            String resp = client.get("name").toString();

            if (revision){
                projectclient.setText(client.get("raison_social").toString());
                //projectdescription.setText(this.project.getDescription());
                projectname.setText(hashMap.get("name").toString());
                projectville.setText(hashMap.get("ville").toString());
                projectrepresentant.setText(client.get("name").toString());
                projectclientcontact.setText(client.get("telephone").toString());

                
            }else{
                projectclient.setText(this.project.getClient());
                projectdescription.setText(this.project.getDescription());
                projectname.setText(this.project.getLabel());
                projectville.setText(this.project.getVille());
                projectrepresentant.setText(this.project.getRepresentant());
                projectclientcontact.setText(this.project.getContact());
                //nombrejour.setText(this.project.getState());

            }



//             ar = (ArrayList) hashMap.get("devis_map");
//            HashMap devismap = (HashMap) ar.get(0);
//
//            avance.setText(devismap.get("avance").toString());




            HashMap map1 = (HashMap) hashMap.get("maindoeuvre_map");
            Collection collection = map1.values();
            Iterator i1 = collection.iterator();
            int i = 0;
            double vetement_valeur = 0.0;
            double montant = 0.0;
            while (i1.hasNext()) {
                HashMap d = (HashMap) i1.next();
                 montant += 
                Double.parseDouble(d.get("taux_horaire").toString()) * Double.parseDouble(d.get("coefficient_horaire").toString()) *
                Double.parseDouble(d.get("nombre_jour").toString()) * Double.parseDouble(d.get("quantite").toString());

            }
            String name = "Main d'oeuvre ";
            products = new Products(name, "MAO" , "1", String.valueOf(montant), "0", "0", String.valueOf(montant), "","Fournitures","Travaux");
            this.selectedProduct.add(products);


            //equipement
             map1 = (HashMap) hashMap.get("component_map");
             collection = map1.values();
             i1 = collection.iterator();
           
            while (i1.hasNext()) {
                HashMap d = (HashMap) i1.next();
                 montant += 
                Double.parseDouble(d.get("taux_horaire").toString()) * Double.parseDouble(d.get("coefficient_horaire").toString()) *
                 Double.parseDouble(d.get("quantite").toString());

            }
             name = "Mise à la disposition des outils ";
            products = new Products(name, "MDO" , "1", String.valueOf(montant), "0", "0", String.valueOf(montant), "","Fournitures","Travaux");
            this.selectedProduct.add(products);

           try{
               map1 = (HashMap) hashMap.get("transport_map");
               String s = map1.get("name").toString();
               double vl = Double.parseDouble(map1.get("valeur_logistique").toString());
                montant = vl + (Double.parseDouble(map1.get("distance").toString()) * Double.parseDouble(map1.get("cout_kilometre").toString()));
               products = new Products(s, "Trans", "1", String.valueOf(montant), "0", "0", String.valueOf(montant), "","Fournitures","Travaux");
               this.selectedProduct.add(products);
           } catch (NullPointerException as ){}

            // products = new Products("Mise à la disposition des outils ", "Out", "1", String.valueOf(vetement_valeur), "0", "0", String.valueOf(vetement_valeur), "","Fournitures","Travaux");
            // this.selectedProduct.add(products);


            Long number = mainApp.manager.countAll("devis_options");
            if (revision){
                cursor = mainApp.manager.getDataByName("customers", client.get("name").toString());
                printmap.put("clientraison",client.get("name").toString());
            }else{
                project.setRepresentant(resp);
                cursor = mainApp.manager.getDataByName("customers", resp);
                printmap.put("clientraison",resp);
            }

            hashMap = (HashMap) cursor.next();
            String abrv_client = hashMap.get("type_client_abreger").toString();
            String abrv_activite = hashMap.get("type_client").toString();
            String numeroPro = "FPO";
            if (number <= 9)
                numeroPro += "00" + (number + 1);
            else
                numeroPro += (number + 1);

            numeroPro += "/" + LocalDate.now().getMonth().getValue() + "/" + LocalDate.now().getYear() + "/" + abrv_activite + "_" + abrv_client;
            proforma.setText(numeroPro);
            this.productsTableView.setItems(this.selectedProduct);
        }

    }

    @FXML
    private void initialize() {
        d1.setVisible(false);
        d2.setVisible(false);
        desig.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        prix.setCellValueFactory(param -> param.getValue().prixProperty());
        marge.setCellValueFactory(param -> param.getValue().margeProperty());
        remise.setCellValueFactory(param -> param.getValue().remiseProperty());
        montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());

        desig.setCellFactory(TextFieldTableCell.forTableColumn());
        remise.setCellFactory(TextFieldTableCell.forTableColumn());
        qte.setCellFactory(TextFieldTableCell.forTableColumn());
        Calendar now = new GregorianCalendar(Locale.CANADA_FRENCH);
        datecurent.setText(LocalDate.now().getDayOfMonth()+"/"+LocalDate.now().getMonthValue()+"/"+LocalDate.now().getYear());

        desig.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue()))
                    event.getRowValue().setName(event.getNewValue());
            }
        });

        remise.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {

                if (!event.getOldValue().equals(event.getNewValue())){


                    if (event.getNewValue().contains("%")){System.exit(0);}
                    double parseRemise = Double.parseDouble(event.getNewValue());
                    double qte;


                    int margeSimple = 0;
                    double price;
                    try {
                        margeSimple = (int) Double.parseDouble(event.getRowValue().getMarge());
                        price = Double.valueOf(Math.round(Double.parseDouble(event.getRowValue().getPrix())));
                         qte = Double.parseDouble(event.getRowValue().getQte());
                    } catch (NumberFormatException e) {
                        margeSimple = 1;
                        price = Double.valueOf(Math.round(Double.parseDouble(event.getRowValue().getMontantht())));
                         qte = 1;
                    }
                    if(margeSimple == 0)
                        margeSimple = 1;




                        Double remiseValue =  (price*parseRemise)/100;
                        //calcul du montant
                        double montant = (price*qte*margeSimple) -remiseValue;
                        event.getRowValue().setMontantht(String.valueOf(montant));
                        event.getRowValue().setMarge(String.valueOf(margeSimple));
                        event.getRowValue().setRemise(String.valueOf(event.getNewValue()));


                }

            }
        });

        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override

            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){

                    double qte = Double.parseDouble(event.getNewValue());
                    double parseRemise = Double.parseDouble(event.getRowValue().getRemise());

                    int  margeSimple = 0;
                    try {
                        margeSimple = (int) Double.parseDouble(event.getRowValue().getMarge());
                    } catch (NumberFormatException e) {
                        margeSimple = 1;
                    }

                    Double price = Double.valueOf(Math.round(Double.parseDouble(event.getRowValue().getPrix())));
                    Double remiseValue =  (price*parseRemise)/100;
                    //calcul du montant
                    double montant = (price*qte*margeSimple) -remiseValue;
                    event.getRowValue().setMontantht(String.valueOf(montant));
                    event.getRowValue().setQte(String.valueOf(qte));


                }





            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int selected = productsTableView.selectionModelProperty().getValue().getSelectedIndex();
                if (selected > 0 ){
                    Products products = productsTableView.selectionModelProperty().getValue().getSelectedItem();
                    int children = selected +1;
                    int taille = productsTableView.getItems().size();
                    System.out.println(children);
                    System.out.println(taille);
                    if (taille < children || taille == children ){
                        new WindowZone("Zone de suppression","Impossible de supprimer","Vous ne pouvez pas supprimer le dernier element",true,stage);

                    }else{
                        Products p= productsTableView.getItems().get(children);
                        double marge = 0;
                        try {
                            marge = Double.parseDouble(products.getMarge());
                        } catch (NumberFormatException e) {
                            marge = 0;
                        }
                        if (marge >= 1 ){
                            new WindowZone("Zone de suppression","Impossible de supprimer","Permission non accordée",true,stage);
                        }else{

                                double m = 0 ;double price = 0;
                       try{
                             m = Double.parseDouble(p.getMontantht()) + Double.parseDouble(products.getMontantht());
                             price = Double.parseDouble(p.getPrix()) + Double.parseDouble(products.getPrix());
                            p.setMontantht(String.valueOf(m));
                            p.setPrix(String.valueOf(price));
                            productsTableView.getItems().remove(selected);

                       } catch(NumberFormatException as){
                            new WindowZone("Zone de suppression","Impossible de supprimer","Vous ne pouvez pas supprimer un element parent",true,stage);

                       }
                            
                           
                    }

                    }

                }
            }
        });

        generate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                Iterator iterator = productsTableView.getItems().iterator();
                while (iterator.hasNext()){
                    Products products = (Products) iterator.next();
                    printProductListe.add(products);
                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                printmap.put("currentdate",datecurent.getText().toString());
                printmap.put("proformanumero",proforma.getText());
                printmap.put("proformaName",project.getLabel());

                String ddd = demande_date.getValue().toString();
                String[] dv = ddd.split("-");
                String newdate = dv[2]+"-"+dv[1]+"-"+dv[0];
                printmap.put("date_demande",newdate);
                 ddd = date_livraison.getValue().toString();
                 dv = ddd.split("-");
                 newdate = dv[2]+"-"+dv[1]+"-"+dv[0];
                 printmap.put("date_fin_demande",newdate);
               // printmap.put("date_demande",sdf.format(demande_date.getValue()));
               // printmap.put("date_fin_demande",demande_date.getValue().toString());
               // printmap.put("delai_de_livraison",date_livraison.getValue().toString());
                printmap.put("avance",avance.getText());
                printmap.put("devis_project",true);
                printmap.put("project_ref",project.getId());
                printmap.put("revision_number",0);


                try {
                    mainApp.viewPrintAction(project,printProductListe,printmap, null, stage,null, false);
                } catch (Exception e) {
                    new WindowZone("Previsualition Impossible","Affichage Errone","Vous ne pouvez pas affiché cet element",true,stage);

                }


            }
        });



        this.productsTableView.setItems(this.productsObservableList);

    }


}