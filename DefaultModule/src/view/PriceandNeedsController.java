package view;
import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;

import java.util.*;

/**
 * Created by Univetech Sarl on 13/10/2016.
 */
public class PriceandNeedsController {

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
    private TableColumn<Products,String> remise;
    @FXML
    private TableColumn<Products,String> marge;
    @FXML
    private TableColumn<Products,String> montantHt;
    @FXML
    private Button preview;
    @FXML
    private Button quitter;
    @FXML
    private Button terminate;

    private MainApp mainApp;
    private ObservableList<Products> productListe = FXCollections.observableArrayList();
    private Stage stage;
    private Project project;
    public void setStage(Stage stage) {this.stage = stage;}

    private HashMap productmap = new HashMap();
    private String id;
    private String name;

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
    private ComboBox projetbox;
    @FXML
    private AnchorPane primarypane;

   // private String id;
    private HashMap projectmap = new HashMap();



    private Integer percent;


    public void setProject(Project project) {
        this.project = project;
        boolean finded = false;

        Cursor cursor = this.mainApp.manager.getAllData("projects");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap hashMap = (HashMap) iterator.next();
            String s = hashMap.get("name").toString();
            projectmap.put(s,hashMap);
            projetbox.getItems().add(s);
        }
        

        // projectclient.setText(this.project.getClient());
        // projectdescription.setText(this.project.getDescription());
        // projectname.setText(this.project.getLabel());
        // projectville.setText(this.project.getVille());
        // projectrepresentant.setText(this.project.getRepresentant());
        // projectclientcontact.setText(this.project.getContact());



        



    }


    public void setMainApp(MainApp mainApp) {this.mainApp = mainApp; }

    private double setQuantity(String quantite,String oldValue){
        double old,newv;
        if (quantite.contains("%")){
           String[] d = quantite.split("%");
             newv = Double.parseDouble(d[0]);
        }else
            newv = Double.parseDouble(quantite);

        if (oldValue.contains("%")){
            String[] d = oldValue.split("%");
            System.out.println(d[0]);
            old = Double.parseDouble(d[0]);
        }else
            old = Double.parseDouble(oldValue);

        if (oldValue.contains("%") && old == 50&& newv == 100)
            //ici on multitplie le price par 2
            this.percent = 0;
        if (oldValue.contains("%")&& old == 100 && newv == 50)
            //ici on divise le price par 2
            this.percent = 1;
        if (oldValue.equals("0%") || !oldValue.contains("%"))
            this.percent = 3;
        return newv;
    }

    private double parsePrice(String p,double q){
        double price = Double.parseDouble(p);
        double rprice = 0.0;
        if (this.percent == 0)
            rprice = price*2;
        if (this.percent == 1)
            rprice = price/2;
        if (this.percent == 3)
            rprice = price*q;
        return rprice;

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
        preview.setVisible(false);
        
        this.productsTableView.setItems(this.productListe);


        //desig.setCellFactory(TextFieldTableCell.forTableColumn());
        //ref.setCellFactory(TextFieldTableCell.forTableColumn());
        qte.setCellFactory(TextFieldTableCell.forTableColumn());

        marge.setCellFactory(TextFieldTableCell.forTableColumn());
        prix.setCellFactory(TextFieldTableCell.forTableColumn());
        remise.setCellFactory(TextFieldTableCell.forTableColumn());

        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {

                if (!event.getOldValue().equals(event.getNewValue())) {


                    double quantite = setQuantity(event.getNewValue(),event.getOldValue());
                    double price = parsePrice(event.getRowValue().getPrix(),quantite);
                    double marge = Double.parseDouble(event.getRowValue().getMarge());
                    double remise = Double.parseDouble(event.getRowValue().getRemise());
                    double montant = (price*marge) -(price*remise/100);
                    event.getRowValue().setMontantht(String.valueOf(montant));
                    event.getRowValue().setQte(String.valueOf(quantite));
                    //event.getRowValue().setPrix(String.valueOf(price));
                    montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());



                }

            }
        });


        prix.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {


                if (!event.getOldValue().equals(event.getNewValue())) {

                    double quantite = setQuantity(event.getRowValue().getQte(),"0%");
                    double price = parsePrice(event.getNewValue(),1);
                    double marge = Double.parseDouble(event.getRowValue().getMarge());
                    double remise = Double.parseDouble(event.getRowValue().getRemise());
                    double montant = (price*marge) -(price*remise/100);
                    event.getRowValue().setMontantht(String.valueOf(montant));
                    event.getRowValue().setPrix(String.valueOf(price));
                    montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());



                }

            }
        });



        marge.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {

                if (!event.getOldValue().equals(event.getNewValue())){

                    double quantite = setQuantity(event.getRowValue().getQte(),"0%");
                    double price = parsePrice(event.getRowValue().getPrix(),quantite);
                    double marge = Double.parseDouble(event.getNewValue());
                    double remise = Double.parseDouble(event.getRowValue().getRemise());
                    double montant = (price*marge) -(price*remise/100);
                    event.getRowValue().setMontantht(String.valueOf(montant));
                    event.getRowValue().setMarge(String.valueOf(marge));
                    montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());
                }


            }
        });

        remise.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override

            public void handle(TableColumn.CellEditEvent<Products, String> event) {
                if (!event.getOldValue().equals(event.getNewValue())){

                    double quantite = setQuantity(event.getRowValue().getQte(),"0%");
                    double price = parsePrice(event.getRowValue().getPrix(),quantite);
                    double marge = Double.parseDouble(event.getRowValue().getMarge());
                    double remise = Double.parseDouble(event.getNewValue());
                    double montant = (price*marge) -(price*remise/100);
                    event.getRowValue().setRemise(String.valueOf(remise));
                    event.getRowValue().setMontantht(String.valueOf(montant));
                    montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());
                }





            }
        });

        projetbox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                HashMap map = (HashMap) projectmap.get(newValue.toString());
                ArrayList arrayList =  (ArrayList) map.get("client");
                
                name = map.get("name").toString();

                HashMap client = (HashMap) arrayList.get(0);
                projectclient.setText(client.get("raison_social").toString());
                projectdescription.setText(map.get("description").toString());
                projectname.setText(map.get("name").toString());
                projectville.setText(client.get("city").toString());
                projectrepresentant.setText(client.get("name").toString());
                projectclientcontact.setText(client.get("telephone").toString());
                id = map.get("id").toString();
                boolean pass = false;

                try {
                    String state = map.get("state").toString();
                    if (state.equals("3")) {
                        ///new WindowZone("Liste des besoins","Les prix ont été déja validés","Etape valider",false,stage);
                        pass = false;

                    }else{

                        pass = true;
                    }

                    if (state.equals("1")) {
                        pass = false;

                    }

                } catch (Exception as){
                    pass = false;
                    new WindowZone("Liste des besoins","Vous devez  renseigner la liste des besoins","Etape Manquant",false,stage);

                }

               // quitter.setVisible(pass);
                terminate.setVisible(pass);
                primarypane.setVisible(pass);

                productsTableView.getItems().removeAll();
                productListe.remove(0,productListe.size());



            if (pass) {
                try{
                    arrayList = (ArrayList) map.get("datamap");
                    HashMap ls = (HashMap) arrayList.get(0);
                    Collection datamap = ls.values();
                    Iterator iterator1 = datamap.iterator();
                    Products products;
                    List lst = new ArrayList();
                      while (iterator1.hasNext()){
                          HashMap ds = (HashMap) iterator1.next();
                          products =  new Products(ds.get("rub").toString(), "", "");
                          if (!lst.contains(ds.get("rub")))
                              productListe.add(products);
                          lst.add(ds.get("rub"));
                          products =  new Products("", ds.get("srb").toString(), "");
                          if (!lst.contains(ds.get("srb").toString())){
                              productListe.add(products);
                              Iterator iterator2 = datamap.iterator();
                              while(iterator2.hasNext()){
                                 HashMap mapkey = (HashMap) iterator2.next();
                                  if (mapkey.get("srb").equals(ds.get("srb"))){
                                      products =  new Products(
                                              mapkey.get("designation").toString(),
                                              mapkey.get("reference").toString(),
                                              mapkey.get("quantite").toString(),
                                              mapkey.get("prixu").toString(),
                                              mapkey.get("marge").toString(),
                                              mapkey.get("remise").toString(),
                                              "000",
                                              "000",
                                              ds.get("rub").toString(), (String) mapkey.get("srb").toString());
                                      productListe.add(products);
                                  }
                              }
                      }
                      lst.add(ds.get("srb"));

                    }
                }catch (Exception as){}

             }
            }
        });

      quitter.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent event) {
              mainApp.closeCurrentWindow(stage);
          }
      });
    }

    @FXML
    private void PriceEditHandler(Event event){}
    @FXML
    private void RemiseEditHandler(){}
    @FXML
    private void MargeEditHandler(){}




    @FXML
    private boolean TerminateHandler() throws Exception {
        Iterator iterator = this.productListe.iterator();
        while (iterator.hasNext()){
            Products p = (Products)iterator.next();

            try {
                productmap.put("productmap_"+productmap.size(),ConnectionManager.r
                        .hashMap("designation",p.getName())
                        .with("reference",p.getRef())
                        .with("quantite", p.getQte())
                        .with("prixu",p.getPrix())
                        .with("marge", p.getMarge())
                        .with("remise", p.getRemise())
                        .with("rub", p.getRubrique())
                        .with("srb", p.getSousrubrique())
                );
            } catch (Exception e) {
               // e.printStackTrace();
            }
        }
        this.mainApp.mapObject = ConnectionManager.getR().hashMap("name",name)
        .with("datamap",ConnectionManager.r.array(productmap))
        .with("state","3")
        .with("priceneeds",true);
        this.mainApp.manager.UpdateData(this.mainApp.mapObject,"projects",this.id);
        this.mainApp.closeCurrentWindow(this.stage);
        return true;
    }

    @FXML
    private void  PreviewHandler() throws Exception {
     //this.mainApp.PreviewPriceandNeed(this.project,this.productListe);
    }

}


