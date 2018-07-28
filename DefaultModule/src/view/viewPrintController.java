package view;

import com.rethinkdb.net.Cursor;
import com.sun.webkit.WebPage;
import controller.Asynchrone;
import controller.DefaultPdfModel;
import controller.PrintController;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.Node;
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
 * Created by Univetech Sarl on 18/11/2016.
 */
public class viewPrintController {

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
    private Project project;
    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();
    private PrintController printController;
    private boolean iscurent = false;
    private String hasvalue;
    private   byte[] h;

    private Asynchrone asynchrone = new Asynchrone();

    private boolean isproject = false;

    private String content;

    public void setIsproject(boolean isproject) {
        this.isproject = isproject;
    }

    public void setProject(Project project) {
        this.project = project;

        if (this.project != null){
            long  countnumber =   mainApp.manager.countAll("projects") +1;
            Cursor cursor =  mainApp.manager.getDataByName("customers",project.getRepresentant());
            HashMap hashMap = (HashMap) cursor.next();
            String abrv_client = hashMap.get("type_client_abreger").toString();
            String abrv_activite = hashMap.get("type_client").toString();
            this.datamap.put("clientcode",hashMap.get("code_client").toString());
            String numeroPro = "FPO";
            if(countnumber <=9)
                numeroPro+="00"+(countnumber+1);
            else
                numeroPro += (countnumber+1);
            numeroPro+="/"+ LocalDate.now().getMonth().getValue()+"/"+LocalDate.now().getYear()+"/"+abrv_activite+"_"+abrv_client;


            cursor = this.mainApp.manager.getDataById("projects",this.project.getId());
            hashMap = (HashMap) cursor.next();
            ArrayList arrayList = (ArrayList) hashMap.get("client");
            HashMap  client = (HashMap) arrayList.get(0);


            this.datamap.put("clientname",client.get("raison_social"));
            this.datamap.put("clientfax",client.get("fax"));
            this.datamap.put("clienttelephone",client.get("telephone"));
            this.datamap.put("proformaNumero",numeroPro);
            this.datamap.put("defaultnumero",numeroPro);
            this.datamap.put("nombreJour",hashMap.get("nombreJour").toString());
            this.datamap.put("personneabout",hashMap.get("personneabout").toString());
            this.datamap.put("username",hashMap.get("username").toString());

 
        }

    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        asynchrone.setMainApp(mainApp);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {
            //asynchrone.__get(h,mainApp.mapObject,"devis_options");
            if (iscurent){
                asynchrone.__get(hasvalue,mainApp.mapObject,"devis_options");
            }

        });

    }

    public void setDatamap(HashMap datamap) {
        if (datamap != null){
            this.datamap = datamap;

        }

    }

    public void setProductsObservableList(ObservableList<Products> productsObservableList) {
        if (productsObservableList !=null){
            this.productsObservableList = productsObservableList;
        }

    }

    @FXML
    private void initialize(){
        engine = this.webView.getEngine();
        savePrint.setVisible(false);

        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                printController = new PrintController(datamap);
                printController.setStage(stage);
                //printController.setIsproject(isproject);
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
                        map.put("montant",p.getMontantht());
                        map.put("rubrique",p.getRubrique());
                        map.put("sousrubirque",p.getSousrubrique());
                        hashMap.put("productmap_"+i,map);

                    }

                hasvalue =  datamap.get("proformaName").toString() + datamap.get("clientname").toString() + datamap.get("clienttelephone").toString();




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



                    mainApp.mapObject = ConnectionManager.getR()
                                    .hashMap("name",datamap.get("proformaName"))
                                    .with("devis_project",true)
                                    .with("project_ref", project.getId())
                                    .with("datamap",ConnectionManager.r.array(hashMap))
                                    .with("devis",true)
                                    .with("clientmap",ConnectionManager.r.array(datamap))
                                    .with("hash_code",h.toString())
                                    .with("revision_number",0)
                                    .with("hash_code_numero",hasvalue.hashCode());

                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            iscurent = true;
                         asynchrone.__save(mainApp.mapObject,"project_temp_devis");
                        }
                    };
                    new Thread(task,"Service Thread").start();

                    //mainApp.manager.SaveData(mainApp.mapObject,"devis_options");
                   // mainApp.closeCurrentWindow(stage);
                }

        });

        savePrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                if (job != null) {
//                    // savePrint.setVisible(false);
//                    job.showPrintDialog(stage);
//                    engine.print(job);
//
//                    job.endJob();
//                    job.cancelJob();
//                }

                new DefaultPdfModel(content,mainApp.getUserRole().getUsername());
                savePrint.setVisible(false);
            }
        });
    }
}
