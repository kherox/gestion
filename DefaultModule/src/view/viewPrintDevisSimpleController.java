package view;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.rethinkdb.net.Cursor;
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
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.MainApp;
import model.Operations;
import model.Products;
import model.Project;
import model.Revision;
import org.apache.poi.poifs.storage.DocumentBlock;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Univetech Sarl on 31/01/2017.
 */
public class viewPrintDevisSimpleController {
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
    private boolean setdevis = false;
    private boolean isOperations = false;
    private boolean iscurent = false;
    private String id;
    private Operations operations;

    private Revision revision;
    private boolean rva  = false;
    private int rvanumber = 1;

    private String hasvalue;
    private   byte[] h;

    private String content;

    private Asynchrone asynchrone = new Asynchrone();
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        asynchrone.setMainApp(mainApp);
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stage.setOnCloseRequest(event -> {
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
                printController.setIsproject(false);
                printController.setStage(stage);
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
                        hashMap.put("productmap_"+i,map);
                    }


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
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String projectRef = null;
                    try{
                        projectRef = datamap.get("project_ref").toString();
                    }catch(NullPointerException e){}

                    boolean isRevision = false;
                    String auteur = "";
                    String username = (String) datamap.get("username");
                    if ( username == null){
                        isRevision = true;
                        auteur = mainApp.getUserRole().getUsername();
                    }


                    mainApp.mapObject = ConnectionManager.getR()
                                .hashMap("name",datamap.get("proformaName"))
                                .with("devis_project",false)
                                .with("project_ref", projectRef)
                                .with("datamap",ConnectionManager.r.array(hashMap))
                                .with("devis",true)
                                .with("clientmap",ConnectionManager.r.array(datamap))
                                .with("hash_code",h.toString())
                                .with("revision_number",0)
                                .with("isrevision",isRevision)
                                .with("revision_auteur",auteur)
                                .with("hash_code_numero",hasvalue.hashCode());

                    Runnable task = new Runnable() {
                        @Override
                        public void run() {
                            iscurent = true;
                            asynchrone.__save(mainApp.mapObject,"temp_devis");
                        }
                    };
                    new Thread(task,"Service Thread").start();

                    //mainApp.manager.SaveData(mainApp.mapObject,"devis_options");
                    // mainApp.closeCurrentWindow(stage);
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
