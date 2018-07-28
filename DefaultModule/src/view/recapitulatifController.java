package view;

import com.rethinkdb.model.GroupedResult;
import com.rethinkdb.net.Cursor;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Univetech Sarl on 17/10/2016.
 */
public class recapitulatifController {

    @FXML
    private TableView<Products> projectTableView;
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
    private TableColumn<Products,String> id;

    @FXML
    private TextArea maindoeuvre;
    @FXML
    private TextArea transport;


    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;

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

    private MainApp mainApp;
    private Stage stage;
    private Project project;
    private ObservableList<Products> projectObservableList = FXCollections.observableArrayList();


    public void setProject(Project project) {
        this.project = project;

        projectclient.setText(this.project.getClient());
        projectdescription.setText(this.project.getDescription());
        projectname.setText(this.project.getLabel());
        projectville.setText(this.project.getVille());
        projectrepresentant.setText(this.project.getRepresentant());
        projectclientcontact.setText(this.project.getContact());

        for(Object obj : this.mainApp.manager.getSectionDataByProjectRef(this.project.getId())){
            HashMap<Products,String> d = (HashMap<Products,String> ) obj;
            if (d.containsKey("name")){
                this.projectObservableList.add(new Products(d.get("name").toString().toUpperCase(),"",d.get("id").toString()));
                this.setRef(this.mainApp.manager.getSectionDataByProjectRef(this.project.getId()));

            }


        }


        this.projectTableView.setItems(this.projectObservableList);

       ArrayList cursor = this.mainApp.manager.groupByData("details_maindoeuvres",this.project.getId());
        String datamaindoeuvre ="";

       Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            GroupedResult groupedResults = (GroupedResult) iterator.next();
            List list =  groupedResults.values;
            Iterator i = list.iterator();
            while (i.hasNext()){
                JSONArray jsonArray = (JSONArray) i.next();
               Iterator iterator1 =  jsonArray.iterator();
                double montant = 0;
                String coeffname ="";
                while (iterator1.hasNext()){
                    HashMap<Products,String> d = (HashMap<Products,String> ) iterator1.next();
                    montant += Double.parseDouble(String.valueOf(d.get("montant")));
                    coeffname = d.get("coeff_name") + "\t \t";
                }
                datamaindoeuvre += coeffname + String.valueOf(Math.round(montant)) + " F CFA \n";

            }
        }
        maindoeuvre.setText(datamaindoeuvre);

       Cursor cursor1 = this.mainApp.manager.getDataById("transports",this.project.getId(),true);
        String datatransport = "";
       // if (cursor.hasNext())
            while (cursor1.hasNext())
            {
                Object obj = cursor1.next();
                HashMap<Products,String> d = (HashMap<Products,String> ) obj;
                datatransport += d.get("name") + " \n \t"+ d.get("ville") +  " \n \t\t"+ d.get("distance") +"  Km \n \t\t\t "+ String.valueOf(d.get("montant")) + " FCFA\n";

            }
        transport.setText(datatransport);



    }





    private void setRef(Cursor cursor){
        for(Object obj : cursor) {
            HashMap<Products, String> d = (HashMap<Products, String>) obj;
            if (d.containsKey("reference")) {
                this.projectObservableList.add(new Products("", d.get("reference").toString(), d.get("id").toString()));
                recursive(d.get("id").toString(),this.mainApp.manager.getProductDataByProjectRef(this.project.getId()));
            }
        }
    }

    private boolean recursive(String parent_ref,Cursor data){
        String localParent ="["+parent_ref+"]";
        for (Object o : data){
            HashMap<Products,String> ds = (HashMap<Products,String> ) o;
            String parent =ds.get("parent_ref").toString();
            if(parent.compareTo(localParent) == 0)
            {
                this.projectObservableList.add(
                        new Products(
                                ds.get("name").toString(),
                                ds.get("reference").toString(),
                                String.valueOf(ds.get("quantite")),
                                String.valueOf(ds.get("prixu")),
                                String.valueOf(ds.get("marge")),
                                String.valueOf(ds.get("remise")) +"%",
                                String.valueOf(Math.round(
                                        (Double.parseDouble(ds.get("prixu"))
                                                *
                                                Double.parseDouble(ds.get("marge")))
                                                -
                                                ((Double.parseDouble(ds.get("prixu"))*Double.parseDouble(ds.get("marge")))
                                                        * (Double.parseDouble(ds.get("remise")) /100))
                                )),
                                ds.get("id").toString())
                );
            }
        }




        return true;
    }


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        this.projectTableView.setItems(this.projectObservableList);
    }



    public void setStage(Stage stage) { this.stage = stage;}

    @FXML
    private void initialize(){

        desig.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        prix.setCellValueFactory(param -> param.getValue().prixProperty());
        marge.setCellValueFactory(param -> param.getValue().margeProperty());
        remise.setCellValueFactory(param -> param.getValue().remiseProperty());
        montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());

    }

    @FXML
    private void TerminateHandler() throws Exception {
        this.mainApp.mapObject = ConnectionManager.r.hashMap("state","7");
        this.mainApp.manager.UpdateData(this.mainApp.mapObject,"projects",this.project.getId());
        this.mainApp.closeCurrentWindow(this.stage);
        //this.mainApp.PreDevis(this.project);
    }
}
