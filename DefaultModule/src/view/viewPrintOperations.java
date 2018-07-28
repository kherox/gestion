package view;

import com.rethinkdb.net.Cursor;
import controller.Asynchrone;
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
import model.Revision;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 08/02/2017.
 */
public class viewPrintOperations {
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

    private Operations operations;



    private String hasvalue;
    private   byte[] h;

    private Revision revision;
    private boolean rva  = false;
    private int rvanumber = 1;

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
                //asynchrone.__get(hasvalue,mainApp.mapObject,"devis_options");
            }

        });

    }

   

    

    public void setOperations(Operations operations) {
        if(operations != null){
            this.operations = operations;
            Cursor cursor = this.mainApp.manager.getDataById("devis_options",operations.getId());
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

            // while (iterator.hasNext()){
            //     HashMap h1 = (HashMap)  iterator.next();
            //     try{
            //         this.productsObservableList.add(
            //                 new Products(
            //                         h1.get("name").toString(),
            //                         h1.get("reference").toString(),
            //                         h1.get("quantite").toString(),
            //                         h1.get("prix").toString(),
            //                         h1.get("marge").toString(),
            //                         h1.get("remise").toString(),
            //                         String.valueOf(
            //                                 (Double.parseDouble(h1.get("prix").toString()) * (Double.parseDouble(h1.get("marge").toString())) * Double.parseDouble(h1.get("quantite").toString()))
            //                                         -
            //                                         (Double.parseDouble(h1.get("prix").toString()) * (Double.parseDouble(h1.get("remise").toString())))
            //                         ),
            //                         "",h1.get("rubrique").toString(),h1.get("sousrubirque").toString()
            //                 )
            //         );
            //     }catch (NumberFormatException e){}


            // }

            while (iterator.hasNext()){

                
                    HashMap h1 = (HashMap)  iterator.next();
                    String prix = h1.get("prix").toString();
                    String marge =  h1.get("marge").toString();
                    String quantite = h1.get("quantite").toString();
                    String remise   = h1.get("remise").toString();

                    // double mrg; double rms ;

                    // if(remise.isEmpty()) rms = 1; else rms  =  Double.parseDouble(h1.get("remise").toString());
                    // if (marge.isEmpty()) mrg = 1; else  mrg  =  Double.parseDouble(h1.get("marge").toString());
                  
                   

                     
                    //  if (mrg == 0) mrg = 1;
                    //  if (rms == 0) rms = 1;


                   double montant = 0;
                    try{
                        montant = Double.parseDouble(h1.get("montant").toString());
                    }catch (NumberFormatException e){
                        montant = 0;
                    }



                     if (isproject) {
                          this.productsObservableList.add(
                                new Products(h1.get("name").toString(),h1.get("reference").toString(),
                                        quantite,prix,marge,remise,String.valueOf(montant),"",h1.get("rubrique").toString(),h1.get("sousrubirque").toString()
                                )
                        );
                     }else{
                        this.productsObservableList.add(
                                new Products(h1.get("name").toString(),h1.get("reference").toString(),
                                        quantite,prix,marge,remise,String.valueOf(montant),""
                                )
                        );
                     }
                
                   //  HashMap h1 = (HashMap)  iterator.next();

                   //  double marge;double quantite;double remise; double prix;
                   //  try{
                   //         prix = Double.parseDouble(h1.get("prix").toString());

                   //  }catch(NumberFormatException as ){
                   //      prix = 1;
                   //  }

                   //  try{
                   //       marge =  Double.parseDouble(h1.get("marge").toString());
                   //  }catch(NumberFormatException as){
                   //    marge = 1;
                   //  }

                   //  try{
                   //       quantite = Double.parseDouble(h1.get("quantite").toString());
                   //  }catch(NumberFormatException as){
                   //    quantite = 1;
                   //  }

                   //   try{
                   //   remise   = Double.parseDouble(h1.get("remise").toString());
                   //  }catch(NumberFormatException as){
                   //    remise = 1;
                   //  }

                   //  if (remise == 0 ) remise = 1;
                   //  if (marge == 0)  marge   = 1;
                   //  if (quantite == 0) quantite = 1 ;
              

                   // double montant = 0;
                   //  try{
                   //      montant =  (prix * marge * quantite) - ((prix * remise)/100);
                   //  }catch (NumberFormatException e){
                   //      montant = 0;
                   //  }


                   //   if (isproject) {
                   //        this.productsObservableList.add(
                   //              new Products(h1.get("name").toString(),h1.get("reference").toString(),
                   //                      String.valueOf(quantite),String.valueOf(prix),String.valueOf(marge),String.valueOf(remise),String.valueOf(montant),"",h1.get("rubrique").toString(),h1.get("sousrubirque").toString()
                   //              )
                   //      );
                   //   }else{
                   //      this.productsObservableList.add(
                   //              new Products(h1.get("name").toString(),h1.get("reference").toString(),
                   //                      String.valueOf(quantite),String.valueOf(prix),String.valueOf(marge),String.valueOf(remise),String.valueOf(montant),""
                   //              )
                   //      );
                   //   }
                   


            }

        }

    }

    @FXML
    private void initialize(){
        this.engine = this.webView.getEngine();
        savePrint.setVisible(false);

        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printController = new PrintController(datamap);
                printController.setIsproject(isproject);
                printController.setStage(stage);
                printController.setMainApp(mainApp);
                printController.entete();
                printController.setProductsObservableList(productsObservableList);
                printController.footer();
                String content = printController.getContent();
                reload.setVisible(false);
                savePrint.setVisible(true);
                engine.loadContent(content);
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
                       

                        map.put("name",p.getName());
                        map.put("reference",p.getRef());
                        map.put("quantite",p.getQte());
                        map.put("marge",p.getMarge());
                        map.put("remise",p.getRemise());
                        map.put("prix",p.getPrix());
                        if (isproject) {
                             map.put("rubrique",p.getRubrique());
                            map.put("sousrubirque",p.getSousrubrique());
                        }
                        map.put("montant",p.getMontantht());
                       
                        hashMap.put("productmap_"+i,map);
                    }

                     hasvalue =
                                datamap.get("proformaName").toString()
                                        + datamap.get("clientname").toString()
                                        + datamap.get("clienttelephone").toString();


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
                if (job != null) {
                    // savePrint.setVisible(false);
                    job.showPrintDialog(stage);
                    engine.print(job);

                    job.endJob();
                    job.cancelJob();
                }
            }
        });
    }




}
