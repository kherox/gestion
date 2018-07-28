package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;

import javax.xml.crypto.dom.DOMCryptoContext;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import static java.lang.System.exit;

/**
 * Created by Univetech Sarl on 12/10/2016.
 */
public class PrintController {

    private String solde;
    private String avance;
    private String datefindevalidite;
    private String delaidelivraion;
    private String escompte;
    private String acompte;
    private String proformaNumber;
    private String proformaName;
    private String clientname;
    private String clientcode;
    private String clienttelephone;
    private String clientfax;
    private String datededemande;
    private String personneabout;
    private String nombreJour;
    private String chargerdaffaire;
    private double valeurGlobal;
    private boolean isRevision = false;
    Stage stage = new Stage();
    private MainApp mainApp;
    boolean parent = false; boolean enfant = false;
    boolean isproject = true;

    public void setIsproject(boolean isproject) {
        this.isproject = isproject;
    }

    public boolean isParent() {
        return parent;
    }

    public boolean isEnfant() {
        return enfant;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        if (chargerdaffaire == null){
            isRevision = true;
            chargerdaffaire =  mainApp.getUserRole().getUsername();
        }

    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private ObservableList<Products> productsObservableList = FXCollections.observableArrayList();


    private void setRubriqueAndSubRubrique(ObservableList<Products> productsObservableList){
        //declaration
         //initialisation
            String allchildren;
        //mise en place du premier element
         if (!parent) {
                Iterator it1 = productsObservableList.iterator();
                while(it1.hasNext()){
                    Products p    = (Products) it1.next(); 
                    if(p.getName().equals("FOURNITURES")){
                     allchildren = "<tr class=\"classmain\">\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"22\" align=\"center\" valign=middle sdval=\"5\" sdnum=\"1033;\"><font color=\"#000000\">"+ p.getRef()+"</font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"left\" valign=middle><font color=\"#000000\">"+ p.getName()+"</font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"0.1\" sdnum=\"1033;0;0%\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle sdval=\"114243.75\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "  </tr>";
                    parent = true;
                    content += allchildren;
                    break;
                    }
                }
                
            }


        //recuperation de l'iterator d'origine
        Iterator iterator = productsObservableList.iterator();
        //on initialize le boolean de traitement des parent
        int parentcount = 0;
        HashMap contener = new HashMap();
        //on commence la boucle
        while(iterator.hasNext()){
            //On commence 
            if (enfant) 
                break;

            //Initialisation d'un element produit
            Products products    = (Products) iterator.next();



            //System.out.println(products.getName());
 
            if (parent && !enfant) {
                if(products.getName().equals("MATERIELS ET ACCESSOIRES") || products.getName().equals("TRAVAUX") || 
                    products.getName().equals("Materiels et Accessoires") || products.getName().equals("Travaux")){
                    
                 if (!contener.containsKey(products.getName())) {
                     contener.put(products.getName(),products.getName());
                     String defaultNoeud = products.getName().toLowerCase();
                     allchildren = "<tr class=\"classmain\">\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"22\" align=\"center\" valign=middle sdval=\"5\" sdnum=\"1033;\"><font color=\"#000000\">"+ products.getRef().toUpperCase()+"</font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"left\" valign=middle><font color=\"#000000\">"+ products.getName()+"</font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"0.1\" sdnum=\"1033;0;0%\"><font color=\"#000000\"></font></td>\n" +
                           "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle sdval=\"114243.75\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                           "  </tr>";
                        content += allchildren;
                        parentcount++;
                        if (parentcount == 2) {
                             enfant = true;
                         }
                          Iterator it       = productsObservableList.iterator();
                           while (it.hasNext()) {
                                //Initialisation d'un element produit
                                Products p    = (Products) it.next();
                                //tout d'abord on recherche le noeud racine
                                String parentNoeud   = p.getRubrique().toLowerCase();
                                String childrenNoeud = p.getSousrubrique().toLowerCase();

                                double montant = 0;
                                double qte ;
                               try{
                                    qte = Double.parseDouble(p.getQte());
                               }catch (Exception e){qte = 1;}

                               double prixu;
                               try{
                                    prixu = Double.parseDouble(p.getPrix());
                               } catch (Exception e){prixu = 1;}
                               double remise;
                               try{
                                    remise = Double.parseDouble(p.getRemise());
                               } catch (Exception e) {remise = 1;}




                                double marge = 0;
                                try {

                                    if (Double.parseDouble(p.getMarge()) <= 0  )
                                        marge = 1;
                                    else
                                        marge =  Double.parseDouble(p.getMarge());

                                    double prixU = prixu*(1+(marge/100));

                                    montant = (qte*prixU) * (1-(remise/100)) ;

//                                    System.out.println("**********marge*********");
//                                    System.out.println(products.getMarge());
//                                    System.out.println(p.getMarge());
//                                    System.out.println("------------------------");
//                                    System.out.println("**************price 1***********");
//                                    System.out.println(products.getPrix());
//                                    System.out.println(p.getPrix());
//                                    System.out.println("*********************************");



                                    if (defaultNoeud.equals(childrenNoeud)) {
                                         allchildren = "<tr class=\"classmain\">\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"22\" align=\"left\" valign=middle sdval=\"5\" sdnum=\"1033;\"><font color=\"#000000\">" + p.getRef().toUpperCase() + "</font></td>\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"left\" valign=middle><font color=\"#000000\">" + p.getName() + "</font></td>\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\">" + p.getQte() + "</font></td>\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">" + String.valueOf((Math.round(prixU))) + "</font></td>\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"0.1\" sdnum=\"1033;0;0%\"><font color=\"#000000\">" + p.getRemise() + " </font></td>\n" +
                                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+ Math.round(montant) +"</font></td>\n" +
                                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                                            "  </tr>";
                                    marge = 0;
                                    content += allchildren;
                                    valeurGlobal += montant;
                                    montant = 0;

                                    }
                                } catch (NumberFormatException e) {}
                        }
                       } 
                    }
                }
            }
        }
                
     

    public void setProductsObservableList(ObservableList<Products> productsObservableList) {
        this.productsObservableList = productsObservableList;
        Iterator iterator = this.productsObservableList.iterator();
        String allchildren = "";

        if (isproject){
            setRubriqueAndSubRubrique(productsObservableList);
        }else {
            while (iterator.hasNext()) {
                Products products = (Products) iterator.next();

                double marge = 0;
                double montant = 0;
                double qte = Double.parseDouble(products.getQte());
                double prixu = Double.parseDouble(products.getPrix());
                double remise = Double.parseDouble(products.getRemise());
                marge =  Double.parseDouble(products.getMarge());
                try {

                    double prixU = prixu*(1+(marge/100));

                    montant = (qte*prixU) * (1-(remise/100)) ;


                    allchildren = "<tr class=\"classmain\">\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"22\" align=\"left\" valign=middle sdval=\"5\" sdnum=\"1033;\"><font color=\"#000000\">" + products.getRef().toUpperCase() + "</font></td>\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"left\" valign=middle><font color=\"#000000\">" + products.getName() + "</font></td>\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"1\" sdnum=\"1033;\"><font color=\"#000000\">" + products.getQte() + "</font></td>\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">" + String.valueOf(Math.round(prixU)) + "</font></td>\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdval=\"0.1\" sdnum=\"1033;0;0%\"><font color=\"#000000\">" + products.getRemise() + " </font></td>\n" +
                            "    <td style=\"border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"right\" valign=middle sdval=\"126937.5\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">" + Math.round(montant) + "</font></td>\n" +
                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                            "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                            "  </tr>";
                    marge = 0;
                    content += allchildren;
                    valeurGlobal +=montant;
                } catch (NumberFormatException e) {

                }

            }
        }
            

    }


    public String getContent() {
        return content;
    }

    private String content =
            "<!DOCTYPE html><html><head><meta charset=\"utf-8\"/><style type=\"text/css\">\n" +
            "body {padding-top: 60px !important; } " +
             " .classmain td { border-bottom: 1px dotted #ddd;\n" +
             "    border-top: 1px dotted #ddd;}" +
            "</style></head><body>\n" +
            "<table cellspacing=\"0\" border=\"0\">";

    public PrintController(HashMap dataMap){


        datefindevalidite = (String) dataMap.get("date_fin_demande");
        avance = (String) dataMap.get("avance");
        try{
                 if (avance.isEmpty()) avance = "0";
            } catch(NullPointerException as){
                avance = "0";
            }
        
        //delaidelivraion = String.valueOf(dataMap.get("delai_de_livraison"));
        delaidelivraion = String.valueOf(dataMap.get("nombreJour"));


        proformaNumber = (String) dataMap.get("proformaNumero");
        proformaName = (String) dataMap.get("proformaName");
        //ici c'est le nom de la boite
        clientname = (String) dataMap.get("clientname");
        clientfax = (String) dataMap.get("clientfax");
        if (clientfax != null){
            if (clientfax.isEmpty() || clientfax.length() == 0)
                clientfax = " ";
        }

        clienttelephone = (String) dataMap.get("clienttelephone");
        clientcode = (String) dataMap.get("clientcode");
        datededemande = (String) dataMap.get("date_demande");
        //ici c'est le responsable a qui est destiné le devis
        if (dataMap.get("partneref") != null)
            personneabout  = (String) dataMap.get("partneref");
        else
            personneabout  = (String) dataMap.get("clientraison");



    }

    public void entete(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String entente =
                "  <tr>\n" +
                        "    <td colspan=3 height=\"23\" align=\"left\" valign=middle><b><font size=2 color=\"#000000\">PROFORMA N°:</font></b></td>\n" +
                        "    <td colspan=6 align=\"center\" valign=middle><b><font size=2 color=\"#000000\">"+proformaNumber+"</font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td style=\"border-top: 2px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">CLIENT :</font></td>\n" +
                        "    <td style=\"border-top: 2px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=3 align=\"center\" valign=middle><b><font color=\"#000000\">"+clientname+"</font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td height=\"23\" align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=middle><b><font size=4 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">CODE CLIENT :</font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=3 align=\"center\" valign=middle><font color=\"#000000\">"+clientcode+"</font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td height=\"23\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">TEL :</font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=3 align=\"center\" valign=middle><font color=\"#000000\">"+clienttelephone+"</font></td>\n" +
                        "    <td align=\"left\" valign=middle><font color=\"#000000\"></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td height=\"23\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">FAX :</font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=3 align=\"center\" valign=middle><font color=\"#000000\">"+clientfax+"</font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "<tr>\n" +
                        "\t\t<td height=\"23\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 2px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"left\" valign=middle><font color=\"#000000\">A l'attention de  :</font></td>\n" +
                        "\t\t<td style=\"border-top: 1px solid #000000; border-bottom: 2px solid #000000; border-left: 1px solid #000000; border-right: 2px solid #000000\" colspan=3 align=\"center\" valign=middle><font color=\"#000000\">"+personneabout+"</font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t\t<td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "\t</tr>" +
                        "  <tr>\n" +
                        "    <td height=\"10\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"24\" align=\"center\" valign=middle><b><font color=\"#000000\">DATE</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=5 align=\"center\" valign=middle><b><font color=\"#000000\">Votre demande</font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 height=\"24\" align=\"center\" valign=middle sdval=\"42655\" sdnum=\"1033;1033;M/D/YYYY\"><font color=\"#000000\">"+sdf.format(date)+"</font></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=5 align=\"center\" valign=middle><font color=\"#000000\">DDP du "+datededemande+"</font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td height=\"11\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td height=\"9\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "  <tr>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=23 height=\"26\" align=\"center\" valign=middle id=\"ProjerObject\"><b><font size=3 color=\"#000000\">"+proformaName+"</font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>\n" +
                        "    <tr>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" height=\"9\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" align=\"center\" valign=middle><b><font size=3 color=\"#000000\"><br/></font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr> <tr>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"26\" align=\"center\" valign=middle><b><font color=\"#000000\">Réf</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"center\" valign=middle><b><font color=\"#000000\">Désignation</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle><b><font color=\"#000000\">Qté</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">P. Unitaire</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle><b><font color=\"#000000\">R%</font></b></td>\n" +
                        "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle><b><font color=\"#000000\">Prix Net HT (FCFA) </font></b></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                        "  </tr>";
        content += entente;
    }


    public void footer(){

        if (!Double.isNaN(Double.parseDouble(avance)) && Double.parseDouble(avance) > 0)
        {
            solde = String.valueOf(100 - Double.parseDouble(avance));
            double ttc = valeurGlobal + (valeurGlobal*0.18);
            acompte = String.valueOf(ttc * (Double.parseDouble(avance) / 100));
            escompte = String.valueOf(ttc - Double.parseDouble(acompte));
        }else{
            avance = "0";solde = "100"; escompte="0.0"; acompte="0.0";
        }
        String chargerliblle = "";

//        if (isRevision){
//            chargerliblle = "Revisé par";
//        }else{
//            chargerliblle = "Charger d'affaire";
//        }

        chargerliblle = "Chargé d'affaire";


        String html = "  <tr>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 height=\"8\" align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=14 align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"right\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=1 align=\"center\" valign=middle sdnum=\"1033;0;0%\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"10\" align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" height=\"24\" align=\"center\" valign=middle><b><font color=\"#000000\">Code</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Base</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" align=\"center\" valign=middle><b><font color=\"#000000\">Taux</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Montant</font></b></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle><b><font color=\"#000000\">Total HT</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle><b><font color=\"#000000\">Escompte</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Total TTC</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle><b><font color=\"#000000\">Acompte</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=6 align=\"center\" valign=middle><b><font color=\"#000000\">NET A PAYER</font></b></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" rowspan=2 height=\"40\" align=\"center\" valign=top><b><font color=\"#000000\">TVA</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000\" colspan=2 rowspan=2 align=\"center\" valign=top sdval=\"1600933.533\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round(this.valeurGlobal))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000\" rowspan=2 align=\"center\" valign=top sdval=\"0.18\" sdnum=\"1033;0;0%\"><font color=\"#000000\">18%</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 rowspan=2 align=\"center\" valign=top sdval=\"288168.03594\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round((this.valeurGlobal*0.18)))+"</font></td>\n" +
                "    <td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=3 align=\"center\" valign=middle sdval=\"1600933.533\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round(this.valeurGlobal))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=4 align=\"center\" valign=middle sdval=\"0\" sdnum=\"1033;\"><font color=\"#000000\">"+String.valueOf(Math.round(Double.parseDouble(escompte)))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"1889101.56894\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round(this.valeurGlobal+(this.valeurGlobal*0.18)))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"0\" sdnum=\"1033;\"><font color=\"#000000\">"+String.valueOf(Math.round(Double.parseDouble(acompte)))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=6 align=\"center\" valign=middle sdval=\"1889101.56894\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round(this.valeurGlobal+(this.valeurGlobal*0.18)))+"</font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" height=\"24\" align=\"center\" valign=middle><b><font color=\"#000000\">Total</font></b></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"1600933.533\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round(this.valeurGlobal))+"</font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-right: 1px solid #000000\" align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td style=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000\" colspan=2 align=\"center\" valign=middle sdval=\"288168.03594\" sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\">"+String.valueOf(Math.round((this.valeurGlobal*0.18)))+"</font></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" colspan=8 ><font color=\"#000000\"><br/> "+chargerliblle+"</font></td>\n" +
                "    <td align=\"left\"  valign=bottom><font color=\"#000000\"><br/> M. "+ chargerdaffaire.toUpperCase()+"</font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"15\" align=\"center\" valign=middle><b><font color=\"#000000\"><br/></font></b></td>\n" +
                "    <td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"center\" valign=middle sdnum=\"1033;0;_-* #,##0 _€_-;-* #,##0 _€_-;_-* &quot;-&quot;?? _€_-;_-@_-\"><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"21\" colspan=\"15\" align=\"left\" valign=middle><b><u><font color=\"#000000\">Condition de vente</font></u></b></td>\n" +
                "    \n" +
                "    <td align=\"left\" colspan=\"10\" valign=middle><b><u><font color=\"#000000\">Condition de paiement</font></u></b></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"21\" colspan=3 align=\"left\" Fin de validite valign=middle><font color=\"#000000\"> Date de fin:</font></td>\n" +
                "    <td colspan=4 align=\"left\"  valign=middle sdval=\"42685\" sdnum=\"1033;1033;M/D/YYYY\"><font color=\"#000000\">"+datefindevalidite+"</font></td>\n" +
                "    <td  colspan=8 valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\"  valign=middle><font color=\"#000000\">Avance:</font></td>\n" +
                "    <td  align=\"left\"  colspan=6 ><font color=\"#000000\">"+avance+"% à la commande</font></td>\n" +
                "  </tr>" +
                "  <tr>" +
                "    <td height=\"21\" colspan=3 align=\"left\" valign=middle><font color=\"#000000\">Délai de livraison :</font></td>\n" +
                "    <td colspan=4 align=\"left\" valign=middle><font color=\"#000000\">"+delaidelivraion+"</font></td>" +
                "    <td  colspan=8 valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\"  valign=middle><font color=\"#000000\">Solde :</font></td>\n" +
                "    <td colspan=6 align=\"left\" valign=middle><font color=\"#000000\">"+solde+"% à la réception</font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"21\" align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"center\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td colspan=5 align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "  <tr>\n" +
                "    <td height=\"21\" align=\"left\" valign=middle><b><u><font color=\"#000000\">Commercial</font></u></b></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=middle><b><u><font color=\"#000000\">Gérant</font></u></b></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "    <td align=\"left\" valign=bottom><font color=\"#000000\"><br/></font></td>\n" +
                "  </tr>\n" +
                "</table> </body>\n" +
                "</html>"  ;

        content += html;
    }


}
