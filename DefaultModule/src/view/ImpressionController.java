package view;

import com.rethinkdb.net.Cursor;
import controller.PrintController;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.print.*;
import javafx.scene.control.Button;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import netscape.javascript.JSObject;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 02/11/2016.
 */
public class ImpressionController {
    @FXML
    private WebView webView = new WebView();
    @FXML
    private Button reload;

    private MainApp mainApp;
    private Stage stage;
    private WebEngine engine;
    private HashMap<String,String> dataMap;
    private ObservableList<Products> productsObservableList;

    private String datefindevalidite;
    private String avance;
    private String delaidelivraion;
    private String solde;
    private String escompte;
    private String acompte;
    private String proformaNumber;
    private String proformaName;
    private String clientname;
    private String clientcode;
    private String clienttelephone;
    private String clientfax;
    private String datededemande;
    private double valeurGlobal = 0;


    String content = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">\n" +
            "\n" +
            "<html>\n" +
            "<head>\n" +
            "\t\n" +
            "\t<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\"/>\n" +
            "\t<title></title>\n" +
            "\t<meta name=\"generator\" content=\"Kherox\"/>\n" +
            "\t<meta name=\"author\" content=\"Univetech Sarl\"/>\n" +
            "\t<meta name=\"created\" content=\"2016-10-12T00:42:04\"/>\n" +
            "\t<meta name=\"changedby\" content=\"Univetech Sarl\"/>\n" +
            "\t<meta name=\"changed\" content=\"2016-10-12T00:42:25\"/>\n" +
            "\t<meta name=\"AppVersion\" content=\"12.0000\"/>\n" +
            "\t<meta name=\"DocSecurity\" content=\"0\"/>\n" +
            "\t<meta name=\"HyperlinksChanged\" content=\"false\"/>\n" +
            "\t<meta name=\"LinksUpToDate\" content=\"false\"/>\n" +
            "\t<meta name=\"ScaleCrop\" content=\"false\"/>\n" +
            "\t<meta name=\"ShareDoc\" content=\"false\"/>\n\n" +
            "\t\n" +
            "\t<style type=\"text/css\">\n" +
            "\t\tbody,div,table,thead,tbody,tfoot,tr,th,td,p { font-family:\"Calibri\"; font-size:12px }\t\ttable {padding-top: 10% !important; padding-bottom: 10% !important;} body{margin-left : 5%;}\n\n" +
            "\t</style>\n" +
            "\t\n" +
            "</head>\n" +
            "\n" +
            "<body class=\"container\">\n" +
            "<div class=\"col-md-2\"></div>\n" +
            "<div class=\"col-md-8\"><table cellspacing=\"0\" border=\"0\"  class=\"table\">\n" +
            "\t<colgroup width=\"40\"></colgroup>\n" +
            "\t<colgroup width=\"43\"></colgroup>\n" +
            "\t<colgroup width=\"65\"></colgroup>\n" +
            "\t<colgroup width=\"42\"></colgroup>\n" +
            "\t<colgroup width=\"50\"></colgroup>\n" +
            "\t<colgroup width=\"42\"></colgroup>\n" +
            "\t<colgroup width=\"6\"></colgroup>\n" +
            "\t<colgroup width=\"36\"></colgroup>\n" +
            "\t<colgroup width=\"54\"></colgroup>\n" +
            "\t<colgroup width=\"4\"></colgroup>\n" +
            "\t<colgroup width=\"33\"></colgroup>\n" +
            "\t<colgroup width=\"10\"></colgroup>\n" +
            "\t<colgroup width=\"7\"></colgroup>\n" +
            "\t<colgroup width=\"24\"></colgroup>\n" +
            "\t<colgroup width=\"56\"></colgroup>\n" +
            "\t<colgroup width=\"49\"></colgroup>\n" +
            "\t<colgroup width=\"10\"></colgroup>\n" +
            "\t<colgroup width=\"54\"></colgroup>\n" +
            "\t<colgroup width=\"103\"></colgroup>\n" +
            "\t<colgroup width=\"12\"></colgroup>\n" +
            "\t<colgroup width=\"108\"></colgroup>\n" +
            "\t<colgroup width=\"101\"></colgroup>\n" +
            "\t<colgroup width=\"85\"></colgroup>\n" +
            "\t<tr></tr><tr></tr><tr></tr><tr>";

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setDataMap(HashMap<String, String> dataMap) {
        this.dataMap = dataMap;
        datefindevalidite = dataMap.get("date_fin_demande");
        avance = dataMap.get("avance");
        delaidelivraion = dataMap.get("delai_de_livraison");

        if (!Double.isNaN(Double.parseDouble(avance)) && Double.parseDouble(avance) > 0)
        {
            solde = String.valueOf(100 - Double.parseDouble(avance));
            escompte = "";
            acompte = "";
        }else{
            avance = "0";solde = "100"; escompte="0.0"; acompte="0.0";
        }
        proformaNumber = dataMap.get("proformaNumero");
        proformaName = dataMap.get("proformaName");
        clientname = dataMap.get("clientname");
        clientfax = dataMap.get("clientfax");
        clienttelephone = dataMap.get("clienttelephone");
        clientcode = dataMap.get("clientcode");
        datededemande = dataMap.get("date_de_demande");



       /*
        private String proformaName;private String clientname;private String clientcode;private String clienttelephone;
        private String clientfax;private String datededemande;
        dataMap.put("clientname",customer.getName());
            dataMap.put("clientcode",customer.getCode_client());
            dataMap.put("clienttelephone",customer.getTelephone());
            dataMap.put("clientfax",customer.getFax());
            dataMap.put("proformaNumero",proformaNumero.getText());
               dataMap.put("proformaDate",proformaDate.getText());
               dataMap.put("date_fin_demande",dateFinValidite.getValue().toString());
               dataMap.put("date_de_demande",dateDeDemande.getValue().toString());
               dataMap.put("delai_de_livraison",delaiDeLivraison.getText());
               dataMap.put("proformaName",proformaName.getText());
               dataMap.put("avance",avance.getText());

        */


    }

    public void setProductsObservableList(ObservableList<Products> productsObservableList) {
        this.productsObservableList = productsObservableList;
    }

    @FXML

    private void initialize(){
        engine = this.webView.getEngine();
        engine.getLoadWorker().stateProperty().addListener((obs,oldValue,newValue) ->{
            if(newValue == Worker.State.SUCCEEDED)
            {
                JSObject jsObject = (JSObject) engine.executeScript("window");
                //jsObject.setMember("jsObject",new PrintController());


            }
        });

        reload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                reload.setVisible(false);
                entete();
                recursive();
                footer();
                engine.loadContent(content);
                Printer printer = Printer.getDefaultPrinter();
                PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, Printer.MarginType.DEFAULT);
                PrinterJob job = PrinterJob.createPrinterJob(printer);

                if (job != null) {
                    job.showPrintDialog(stage);
                    engine.print(job);
                    job.endJob();
                }
            }
        });
    }


    private boolean recursive(){
        String allchildren = "";
        Iterator iterator =  productsObservableList.iterator();
        while (iterator.hasNext()){
            Products products = (Products) iterator.next();
            allchildren = "<tr id=\"DMMM\">\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"22\" align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\">"+ products.getRef()+"</font></td>\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=8 align=\"left\" valign=middle><font color=\"#000000\">"+ products.getName()+"</font></td>\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\">"+ products.getQte()+"</font></td>\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"right\" valign=middle sdval=\"131040\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"> "+String.valueOf(Math.round((Double.parseDouble(products.getPrix())*Double.parseDouble(products.getMarge()))))+"</font></td>\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"0.05\" sdnum=\"1033;0;0%\"><font color=\"#000000\">"+products.getRemise()+" %</font></td>\n" +
                    "\t\t<td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"124488\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"> "+products.getMontantht()+"</font></td>\n" +
                    "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                    "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                    "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                    "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                    "\t</tr>";
            content += allchildren;
            valeurGlobal += Double.parseDouble(products.getMontantht());
        }

        return true;
    }

    private void entete(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String entente =
                "<td colspan=3 height=\"23\" align=\"left\" valign=middle><b><font size=2 color=\"#000000\">Proforma N°:</font></b></td>\n" +
                        "\t\t<td colspan=6 align=\"center\" valign=middle><b><font size=2 color=\"#000000\">"+proformaNumber+"</font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td style=\"border-top: 2px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">CLIENT :</font></td>\n" +
                        "\t\t<td style=\"border-top: 2px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=5 align=\"center\" id=\"nameClient\" valign=middle ><b><font color=\"#000000\">"+clientname+"</font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr><tr>\n" +
                        "\t\t<td height=\"23\" align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">CODE CLIENT :</font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=5 align=\"center\" valign=middle id=\"codeClient\"><font color=\"#000000\">"+clientcode+"</font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>\n" +
                        "\t<tr>\n" +
                        "\t\t<td height=\"23\" align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">TEL :</font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=5 align=\"center\" valign=middle id=\"telClient\"><font color=\"#000000\">"+clienttelephone+"</font></td>\n" +
                        "\t\t<td align=\"left\" valign=middle><font color=\"#000000\"></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>\n" +
                        "\t<tr>\n" +
                        "\t\t<td height=\"23\" align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">FAX :</font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=5 align=\"center\" valign=middle id=\"faxClient\"><font color=\"#000000\">"+clientfax+"</font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr><tr>\n" +
                        "\t\t<td height=\"10\" align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"24\" align=\"center\" valign=middle><b><font color=\"#000000\">DATE</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle><b><font color=\"#000000\">Votre demande</font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"24\" align=\"center\" valign=middle sdval=\"42655\" sdnum=\"1033;1033;M/D/YYYY\"><font color=\"#000000\">"+sdf.format(date)+"</font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle><font color=\"#000000\">"+datededemande+"</font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>"+
                        "\t<tr>\n" +
                        "\t\t<td height=\"9\" align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=19 height=\"26\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\">"+proformaName+"</font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr><tr>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" height=\"9\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br></font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>\n" +
                        "\t<tr>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"26\" align=\"center\" valign=middle><b><font color=\"#000000\">Réf</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=8 align=\"center\" valign=middle><b><font color=\"#000000\">Désignations</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Qté</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle><b><font color=\"#000000\">Prix Unitaire</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">R%</font></b></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Prix Net HT</font></b></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                        "\t</tr>";
        content += entente;
    }

    private void footer(){
        String html = "<tr>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" height=\"24\" align=\"center\" valign=middle><b><font color=\"#000000\">Code</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Base</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" align=\"center\" valign=middle><b><font color=\"#000000\">Taux</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Montant</font></b></td>\n" +
                "\t\t<td align=\"center\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle><b><font color=\"#000000\">Total HT</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle><b><font color=\"#000000\">Escompte</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Total TTC</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Acompte</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" align=\"center\" valign=middle><b><font color=\"#000000\">NET A PAYER</font></b></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" rowspan=2 height=\"40\" align=\"center\" valign=top><b><font color=\"#000000\">TVA</font></b></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000\" colspan=2 rowspan=2 align=\"center\" valign=top sdval=\"1600933.533\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(this.valeurGlobal)+"</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000\" rowspan=2 align=\"center\" valign=top sdval=\"0.18\" sdnum=\"1033;0;0%\"><font color=\"#000000\">18%</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 rowspan=2 align=\"center\" valign=top sdval=\"288168.03594\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf((this.valeurGlobal*0.18))+"</font></td>\n" +
                "\t\t<td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle sdval=\"1600933.533\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(this.valeurGlobal)+"</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle sdval=\"0\" sdnum=\"1033;\"><font color=\"#000000\">"+acompte+"</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"1889101.56894\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(this.valeurGlobal+(this.valeurGlobal*0.18))+"</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"0\" sdnum=\"1033;\"><font color=\"#000000\">"+escompte+"</font></td>\n" +
                "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" align=\"center\" valign=middle sdval=\"1889101.56894\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(this.valeurGlobal+(this.valeurGlobal*0.18))+"</font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t</tr><tr>\n" +
                "\t\t<td height=\"15\" align=\"center\" valign=middle><b><font color=\"#000000\"><br></font></b></td>\n" +
                "\t\t<td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"center\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"center\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"center\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t</tr>\n" +
                "\t<tr><tr>\n" +
                "\t\t<td height=\"21\" colspan=15 align=\"left\" valign=middle><b><u><font color=\"#000000\">Condition de vente</font></u></b></td>\n" +

                "\t\t<td align=\"left\" colspan=8 valign=middle><b><u><font color=\"#000000\">Condition de paiement</font></u></b></td>\n" +

                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td height=\"21\"  colspan=2 align=\"left\" valign=middle><font color=\"#000000\">Fin de validite :</font></td>\n" +
                "\t\t <td align=\"left\" valign=middle><font color=\"#000000\"></font></td>\n" +
                "\t\t<td align=\"left\" colspan=3 valign=middle><font color=\"#000000\">"+datefindevalidite+"</font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"></font></td> \n" +
                "\t\t<td colspan=8 align=\"center\" valign=middle sdval=\"42685\" sdnum=\"1033;1033;M/D/YYYY\"><font color=\"#000000\"></font></td>\n" +
                "\t\t<td align=\"left\" valign=middle><font color=\"#000000\">Avance: </font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td  align=\"left\" colspan=8 valign=middle sdnum=\"1033;0;0%\"><font size=2 color=\"#000000\">"+avance+" % à la command</font></td>\n" +
                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td height=\"21\" colspan=3 align=\"left\" valign=middle><font color=\"#000000\">Délai de livraison :</font></td>\n" +
                "\t\t <td align=\"left\" valign=middle><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td colspan=3 align=\"left\" valign=middle><font color=\"#000000\">"+delaidelivraion+"</font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"></font></td> \n" +
                "\t\t<td colspan=7 align=\"left\" valign=middle><font color=\"#000000\"></font></td>\n" +

                "\t\t<td align=\"left\" valign=middle><font color=\"#000000\">Solde: </font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td colspan=8 align=\"left\" valign=middle><font size=2 color=\"#000000\">"+solde+" % à la reception</font></td>\n" +

                "\t</tr>\n" +
                "\t<tr>\n" +
                "\t\t<td height=\"21\" align=\"left\" valign=middle><b><u><font color=\"#000000\">Commercial</font></u></b></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=middle><b><u><font color=\"#000000\">Gérant</font></u></b></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br></font></td>\n" +
                "\t</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "<div class=\"col-md-2\"></div>\n" +
                "<!-- ************************************************************************** -->\n" +
                "</body>\n" +
                "\n" +
                "</html>\n\n" ;

        content += html;
    }






}
