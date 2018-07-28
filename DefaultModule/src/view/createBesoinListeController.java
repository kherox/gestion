package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;
import org.w3c.dom.Document;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileStore;
import java.util.*;

/**
 * Created by Univetech Sarl on 10/10/2016.
 */
public class createBesoinListeController {

    @FXML
    private ComboBox<String> produit;
    @FXML
    private TableView<Products> besoinListe;
    @FXML
    private ListView<String> stringListView;
    @FXML
    private TableColumn<Products,String> design;
    @FXML
    private TableColumn<Products,String> qte;
    @FXML
    private TableColumn<Products,String> ref;
    @FXML
    private TableColumn<Products,String> id;

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
    private TextField rubrique;
    @FXML
    private CheckBox sb;
    @FXML
    private MenuItem saveItem;
    @FXML
    private ComboBox projetbox;
    @FXML
    private Button terminate;
     @FXML
    private Button quitter;
    @FXML
    private Button btnvalider;
    @FXML
    private Button btndelete;

    private HashMap projectmap = new HashMap();

    private HashMap datamap = new HashMap();
    private HashMap productmap = new HashMap();
    private HashMap containerMap = new HashMap();
    private HashMap desigmap = new HashMap();
    private HashMap refgmap = new HashMap();
    private ObservableList<Products> productListe = FXCollections.observableArrayList();
    private String refCollection;
    private String desigCollection;
    private String ids;



    public ObservableList<Products> getProductListe() {return productListe;}

    private MainApp mainApp;
    private Stage stage;
    private Project project;
    //recupere l'id du parent
    private boolean parentId =false ;
    //permet de savoir si la personne a deja creer un element enfant au parent principal
    private boolean authorize = false;
    private String name;

    public void setProject(Project project) {

        Cursor cursor = this.mainApp.manager.getAllData("projects");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap hashMap = (HashMap) iterator.next();
            String s = hashMap.get("name").toString();
            containerMap.put(s,hashMap);
            projetbox.getItems().add(s);
        }
        
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        Cursor cursor = this.mainApp.manager.getAllData("products");
        for (Object obj : cursor){
            HashMap hashMap = (HashMap) obj;
            produit.getItems().add((String) hashMap.get("name"));
        }
       besoinListe.setItems(this.productListe);
    }
    public void setStage(Stage stage) {this.stage = stage;}

    @FXML
    private void initialize(){
        saveItem.setVisible(false);
        produit.valueProperty().addListener(
                (obs,old,newValue) ->
                {
                    if (newValue != null && !produit.getItems().contains(newValue))
                    {
                        Products p = new Products();
                        p.setName(newValue);
                        p.setRef(newValue);
                        try {
                            this.mainApp.AddProductAction(p);
                            Cursor cursor = this.mainApp.manager.getDataById("products",this.mainApp.manager.getCurrentsaveId());
                            HashMap obj = (HashMap) cursor.next();
                            this.productListe.add(new Products(
                                    obj.get("name").toString(),
                                    obj.get("reference").toString(),
                                    obj.get("quantite").toString(),
                                    obj.get("prixu").toString(),
                                    obj.get("marge").toString(),
                                    obj.get("remise").toString(),
                                    obj.get("montant").toString(),
                                    obj.get("id").toString())
                            );
                            produit.getItems().add(obj.get("name").toString());
                        } catch (Exception e) {
                            //e.printStackTrace();
                        }






                    }
         });

        design.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        id.setCellValueFactory(param -> param.getValue().idProperty());

        design.setCellFactory(TextFieldTableCell.forTableColumn());
        ref.setCellFactory(TextFieldTableCell.forTableColumn());
        qte.setCellFactory(TextFieldTableCell.forTableColumn());

        qte.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Products, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Products, String> event) {

                if (event.getOldValue() != event.getNewValue()){
                    String qt = "";
                   if (event.getNewValue() == null){

                      qt = "1";
                   }else{
                       qt = event.getNewValue();
                   }

                    event.getRowValue().setQte(String.valueOf(qt));
                }

            }
        });


        saveItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    File file = new File("newfile.pdf");
                    FileWriter writer = new FileWriter(file);
                     WebView webView = new WebView();
                     WebEngine engine = webView.getEngine();
                    Document document = engine.getDocument();

                    writer.write("");
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        projetbox.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

                HashMap map = (HashMap) containerMap.get(newValue.toString());

                ArrayList arrayList =  (ArrayList) map.get("client");
                HashMap client = (HashMap) arrayList.get(0);
                boolean pass = false;
                updateViewWitExistingProduct((ArrayList) map.get("datamap"));
//                try
//                {
//                    String state = map.get("state").toString();
//                    if (state.equals("2")) {
//                        new WindowZone("Liste des besoins","Vous devez plus renseigner la liste des besoins","Etape valider",false,stage);
//                        pass = false;
//
//                    }else{
//
//                        pass = true;
//                    }
//                } catch (Exception as){
//                   // new WindowZone("Liste des besoins","Vous devez d'abord ","Etape valider",false,stage);
//
//                }


//                terminate.setVisible(pass);
//                btnvalider.setVisible(pass);
//                btndelete.setVisible(pass);
                
                name = map.get("name").toString();

                projectclient.setText(client.get("raison_social").toString());
                projectdescription.setText(map.get("description").toString());
                projectname.setText(map.get("name").toString());
                projectville.setText(client.get("city").toString());
                projectrepresentant.setText(client.get("name").toString());
                projectclientcontact.setText(client.get("telephone").toString());

                ids = map.get("id").toString();

            }
        });
        
     quitter.setOnAction(new EventHandler<ActionEvent>() {
         @Override
         public void handle(ActionEvent event) {
             mainApp.closeCurrentWindow(stage);
         }
     });
    }


    private void updateViewWitExistingProduct(ArrayList datamap){

        HashMap hmap = (HashMap) datamap.get(0);

        Collection collections = hmap.values();
        Iterator iterator = collections.iterator();
        Products products;

        while (iterator.hasNext()){
            /*

                    String montantht, String id,String rubr,String subr
             */
            HashMap obj = (HashMap) iterator.next();

            String rub = null;
            try {
                rub = obj.get("rub").toString();
            } catch (Exception e) {
                rub = " ";
            }

            String srb = null;
            try {
                srb = obj.get("srb").toString();
            } catch (Exception e) {
                srb = " ";
            }

            this.productListe.add(new Products(
                    obj.get("designation").toString(),
                    obj.get("reference").toString(),
                    obj.get("quantite").toString(),
                    obj.get("prixu").toString(),
                    obj.get("prixu").toString(),
                    obj.get("remise").toString(),
                   rub,
                    srb,
                    rub,
                    srb)
            );

        }

        this.authorize = true;


    }

    @FXML
    private void ProduitsSelected(){
      if (!this.productListe.contains(produit.selectionModelProperty().getValue().selectedItemProperty().getValue()) || this.authorize){

          Cursor cursor = this.mainApp.manager.getDataByName("products",produit.selectionModelProperty().getValue().selectedItemProperty().getValue());
          HashMap obj = (HashMap) cursor.next();

              this.productListe.add(new Products(
                      obj.get("name").toString(),
                      obj.get("reference").toString(),
                      obj.get("quantite").toString(),
                      obj.get("prixu").toString(),
                      obj.get("marge").toString(),
                      obj.get("remise").toString(),
                      obj.get("montant").toString(),
                      obj.get("id").toString())
              );

               desigmap.put(obj.get("name").toString(),desigCollection);
               refgmap.put(obj.get("name").toString(),refCollection);





      }else
          new WindowZone("Parent Inconnu","Vous devez d'abord creer une rubrique superieur","Rubrique parent vide",false,stage);

    }

    @FXML
    private boolean createRubriqueLigne(){
        boolean checked = false;

        if(sb.isSelected() && !rubrique.getText().isEmpty()){
            sb.setSelected(false);
            if (!parentId){
                rubrique.setText("");
                new WindowZone("Parent Inconnu","Vous devez d'abord creer une rubrique superieur","Rubrique parent vide",true,stage);
                return false;
            }else{
                refCollection = rubrique.getText();
                this.authorize = true;
            }
        }else{
            sb.setSelected(true);
            checked = true;
            desigCollection= rubrique.getText();
            this.parentId =  true;
        }

            if (checked){
                //creation de l'object name //Creation de l'object name qui est le parent
                this.productListe.add(new Products(rubrique.getText().toUpperCase(Locale.FRANCE),"","",""));
            }else{
                //creation de l'objet reference //Creation de l'object reference qui est l'enfant
                this.productListe.add(new Products("",rubrique.getText().toUpperCase(Locale.getDefault()),"",""));

            }

        rubrique.setText("");
        return true;
    }

    @FXML
    private void DeleteHandler(){
      int selected = besoinListe.selectionModelProperty().getValue().getSelectedIndex();
        Products products =  besoinListe.getItems().get(selected);
        besoinListe.getItems().remove(selected);
        desigCollection = "";
        refCollection = "";

    }

    @FXML
    private void FinishHandler(){
        if(besoinListe.getItems().size() <=0 )
            new WindowZone("Problem d'affichage","La liste des besoins doit etre renseigner","Liste de besoins vide",true,stage);

        Iterator iterator1 = besoinListe.getItems().iterator();
        int i = 0;
        while (iterator1.hasNext()){
            i++;
            Products p = (Products) iterator1.next();
            try {
                String rub = null;
                try {
                    rub = (String) desigmap.get(p.getName());
                } catch (Exception e) {
                    rub = " ";
                }
                String srb = null;
                try {
                    srb = (String) refgmap.get(p.getName());
                } catch (Exception e) {
                    srb = " ";
                }


                productmap.put("productmap_"+i,ConnectionManager.r
                        .hashMap("designation",p.getName())
                        .with("reference",p.getRef())
                        .with("quantite", p.getQte())
                        .with("prixu", p.getPrix())
                        .with("marge", p.getMarge())
                        .with("remise", p.getRemise())
                        .with("rub", rub)
                        .with("srb", srb)
                );

            } catch (NullPointerException e) {}

        }



        try {
            this.mainApp.mapObject = ConnectionManager.r
                    .hashMap("name",name)
                    .with("project_ref",this.ids)
                    .with("datamap",ConnectionManager.r.array(productmap))
                    .with("besoinliste",true)
                    .with("state","2");

            this.mainApp.manager.UpdateData(this.mainApp.mapObject,"projects",this.ids);
            this.mainApp.closeCurrentWindow(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
