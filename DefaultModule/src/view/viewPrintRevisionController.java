package view;

import com.rethinkdb.net.Cursor;
import controller.Asynchrone;
import controller.DefaultPdfModel;
import controller.PrintController;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.MainApp;
import model.Operations;
import model.Products;
import model.Project;
import model.Revision;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Univetech Sarl on 31/01/2017.
 */
public class viewPrintRevisionController {

    @FXML
    private WebView webView;
    @FXML
    private Button reload;
    @FXML
    private Button savePrint;
    private PrinterJob job;
    private PageLayout pageLayout;

    private WebEngine engine;

    private MainApp mainApp;
    private Stage stage;
    private HashMap datamap = new HashMap();
    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
    private ObservableList<Products> printingList = FXCollections.observableArrayList();
    private PrintController printController;
    private boolean setdevis = false;
    private boolean iscurent = false;

    private String hasvalue;
    private   byte[] h;

    private Revision revision;
    private boolean rva  = false;
    private int rvanumber = 1;

    private String content;

    private boolean isproject = false;

    public void setIsproject(boolean isproject) {
        this.isproject = isproject;
    }

    private Asynchrone asynchrone = new Asynchrone();

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        asynchrone.setMainApp(mainApp);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {
            //asynchrone.__get(h,mainApp.mapObject,"devis_options");
            if (iscurent){
               // asynchrone.__get(hasvalue,mainApp.mapObject,"devis_options");
            }

        });

    }

    public void setDatamap(HashMap datamap) {
        if (datamap != null){
            this.datamap = datamap;
        }

    }

    public void setProductsObservableList(ObservableList<Products> productsObservableList) {
        if (productsObservableList != null) {
            Iterator iterator = productsObservableList.iterator();
            boolean parent = false;
            boolean enfant = false;
            int parentcount = 0;
            while (iterator.hasNext()) {
                if (enfant)
                    break;

                Products products = (Products) iterator.next();
                if (!parent) {
                    if (products.getName().equals("FOURNITURES")) {
                        this.printingList.add(products);
                        this.productsObservableList.add(products);
                        parent = true;
                    }
                }

                if (parent && !enfant) {
                    if (products.getName().equals("MATERIELS ET ACCESSOIRES") || products.getName().equals("TRAVAUX")) {

                        String defaultNoeud = products.getName().toLowerCase();
                        this.printingList.add(products);
                        this.productsObservableList.add(products);
                        parentcount++;
                        if (parentcount == 2) {
                            enfant = true;
                        }
                        Iterator it = productsObservableList.iterator();

                        while (it.hasNext()) {
                            //Initialisation d'un element produit
                            Products p = (Products) it.next();
                            //tout d'abord on recherche le noeud racine

                             String childrenNoeud = p.getSousrubrique().toLowerCase();
                             if (defaultNoeud.equals(childrenNoeud)) {
                                 this.printingList.add(p);
                                 this.productsObservableList.add(p);
                             }
                        }

                    }

                }

            }
        }
    }

    public void setRevision(Revision revision) {
        if(revision != null){
            this.revision = revision;
            Cursor cursor = this.mainApp.manager.getDataById("revisions",revision.getId());
            rva = true;
            HashMap hashMap = (HashMap) cursor.next();
            ArrayList list = (ArrayList)  hashMap.get("datamap");
            HashMap mp = (HashMap) list.get(0);
            //HashMap map = (HashMap) hashMap.get("datamap");
            Collection collection = mp.values();
            Iterator iterator = collection.iterator();
            ArrayList lst = (ArrayList)  hashMap.get("clientmap");
            HashMap m1 = (HashMap) lst.get(0);
            rvanumber = Integer.parseInt(hashMap.get("revision_number").toString()) +1 ;

            this.datamap.put("clientraison",m1.get("clientraison").toString());
            this.datamap.put("clientname",m1.get("clientname").toString());
            this.datamap.put("currentdate", LocalDate.now().toString());
            this.datamap.put("clientfax",m1.get("clientfax"));
            this.datamap.put("clienttelephone",m1.get("clienttelephone"));
            this.datamap.put("defaultnumero",m1.get("proformaNumero").toString());
            this.datamap.put("proformaNumero",m1.get("proformaNumero").toString());
            this.datamap.put("proformaName",m1.get("proformaName").toString());
            this.datamap.put("clientcode",m1.get("clientcode").toString());
            this.datamap.put("avance",m1.get("avance").toString());
            this.datamap.put("delai_de_livraison",m1.get("delai_de_livraison").toString());
            this.datamap.put("date_demande",m1.get("date_demande").toString());
            this.datamap.put("date_fin_demande",m1.get("date_fin_demande").toString());
            this.datamap.put("revision_number",rvanumber);
            this.datamap.put("nombreJour",m1.get("nombreJour").toString());
       
                while (iterator.hasNext()){
                    HashMap h1 = (HashMap)  iterator.next();
                    String prix = h1.get("prix").toString();
                    String marge =  h1.get("marge").toString();
                    String quantite = h1.get("quantite").toString();
                    String remise   = h1.get("remise").toString();
                    double montant = 0;
                    try{
                        montant = Double.parseDouble(h1.get("montant").toString());
                    }catch (NumberFormatException e){
                        montant = 0;
                    }
                    
                    if (isproject){
                        this.productsObservableList.add(
                                new Products(h1.get("name").toString(),h1.get("reference").toString(),
                                        quantite,prix,marge,remise,String.valueOf(montant),"",h1.get("rubrique").toString(),h1.get("sousrubirque").toString()
                                )
                        );
                    }else{
                        this.productsObservableList.add(
                                new Products(h1.get("name").toString(),h1.get("reference").toString(),
                                        quantite,prix,marge,remise,String.valueOf(montant),""
                                ));
                    }





            }
        }

    }

    @FXML
    private void initialize(){
        this.engine = this.webView.getEngine();
        savePrint.setVisible(false);
        savePrint.setText("Generer le devis");

        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printController = new PrintController(datamap);
                printController.setStage(stage);
                printController.setIsproject(isproject);
                printController.setMainApp(mainApp);
                printController.entete();
                printController.setProductsObservableList(productsObservableList);
                printController.footer();
                content = printController.getContent();




                reload.setVisible(false);
                savePrint.setVisible(true);
                engine.loadContent(content);

                Printer printer = Printer.getDefaultPrinter();
                pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                job = PrinterJob.createPrinterJob(printer);
                HashMap hashMap = new HashMap();
                if (!setdevis){
                    Iterator iterator = productsObservableList.iterator();
                    int i =0;
                    while (iterator.hasNext()){
                        i++;
                        Products p = (Products) iterator.next();
                        HashMap map = new HashMap();
                        hasvalue =
                                datamap.get("proformaName").toString()
                                        + datamap.get("clientname").toString()
                                        + datamap.get("clienttelephone").toString();

                        map.put("name",p.getName());
                        map.put("reference",p.getRef());
                        map.put("quantite",p.getQte());
                        map.put("marge",p.getMarge());
                        map.put("remise",p.getRemise());
                        map.put("prix",p.getPrix());
                        map.put("montant",p.getMontantht());
                        try {
                            map.put("rubrique",p.getRubrique());
                            map.put("sousrubirque",p.getSousrubrique());
                        }catch (NullPointerException e){}

                        hashMap.put("productmap_"+i,map);
                    }


                        mainApp.mapObject = ConnectionManager.getR()
                                .hashMap("name",datamap.get("proformaName"))
                                .with("datamap",ConnectionManager.r.array(hashMap))
                                //.with("clientmap",ConnectionManager.r.array(datamap))
                                .with("revision_number",Integer.parseInt(datamap.get("revision_number").toString()));


                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            iscurent = true;
                            mainApp.mapObject = ConnectionManager.getR()
                                    .hashMap("name",datamap.get("proformaName"))
                                    .with("datamap",ConnectionManager.r.array(hashMap))
                                    .with("clientmap",ConnectionManager.r.array(datamap))
                                    .with("revision_number",Integer.parseInt(datamap.get("revision_number").toString()));
                            asynchrone.__save(mainApp.mapObject,"revision_temp_devis");
                        }
                    };
                    new Thread(task,"Service Thread").start();
                }


            }
        });

        savePrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new DefaultPdfModel(content,mainApp.getUserRole().getUsername());
                savePrint.setVisible(false);
            }
        });
    }


}
