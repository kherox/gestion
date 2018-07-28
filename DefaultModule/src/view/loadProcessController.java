package view;

import com.rethinkdb.net.Cursor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import main.MainApp;
import model.Customer;
import model.Products;
import model.Project;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Univetech Sarl on 06/10/2016.
 */
public class loadProcessController {

    @FXML
    private Label label;
    @FXML
    private ComboBox<String> projetList;
    @FXML
    private Button btnValidate;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private ProgressBar progressBar;

    private MainApp mainApp;

    private Stage stage;

    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();


    public void setMainApp(MainApp mainApp,Stage stage) {
        this.mainApp = mainApp; this.stage = stage;
        Cursor cursor = this.mainApp.manager.AviallableProject("projects");
        for (Object obj : cursor){
            HashMap<Project,Object> hashMap = (HashMap<Project, Object>) obj;
            this.projetList.getItems().add(hashMap.get("name").toString());
            HashMap<String,String> clientMap =  (HashMap<String, String>) (((ArrayList) hashMap.get("client")).get(0));
            Project project = new Project(
                    hashMap.get("name").toString(),
                    clientMap.get("raison_social").toString(),
                    hashMap.get("ville").toString(),
                    hashMap.get("description").toString(),
                    hashMap.get("id").toString(),
                    hashMap.get("state").toString()

            );
            project.setRepresentant(clientMap.get("name").toString());
            project.setContact(clientMap.get("telephone").toString());

            projectObservableList.add(project);

            if (this.projetList.getItems().size() >= 1)
                btnValidate.setVisible(true);
        }
        this.mainApp.setProjectData(this.projectObservableList);

    }
    @FXML
    public void initialize(){
        progressBar.setVisible(false); btnValidate.setVisible(false);
    }

    @FXML
    private void handlerLoadProjetInfomation() throws Exception{
        btnValidate.setVisible(false);
        progressBar.setVisible(true);
        label.setVisible(false);
        projetList.setVisible(false);

        Project current = null;
        for (Project p : this.mainApp.getProjectData())
            if (p.getLabel() == this.projetList.selectionModelProperty().getValue().selectedItemProperty().getValue())
                current = p;

        String i = null;
        try {
            i = current.getState();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (i.equals("1")){
            //chargement de la creation de la liste des besoins materiels
            this.mainApp.BesoinListeAction(current,stage);
            this.mainApp.closeCurrentWindow(stage);
        }else if(i.equals("2")){
            //chargement de la version creation de schema
            this.mainApp.LoadPriceListeFormBesoinAction(current,stage);
            this.mainApp.closeCurrentWindow(stage);
        }else if (i.equals("3")){
            //chargement de la version creation du devis
            //this.mainApp.CoefficientWindow(current,stage);
            //this.mainApp.closeCurrentWindow(stage);
        }else if (i.equals("4")){
            // this.mainApp.AddComponent(current,stage);
            //this.mainApp.closeCurrentWindow(stage);
         
        }else if(i.equals("5")){
            this.mainApp.createSchemaMainDoeuvre(current,stage);
            this.mainApp.closeCurrentWindow(stage);
        }

        /*else if (i.equals("5")){
           /// this.mainApp.TransportCalculation(current);
        } else  if (i.equals("6")){
           // this.mainApp.TableauRecapitulatif(current);
        }else if (i.equals("7")){
           // this.mainApp.PreDevis(current);
        }*/



    }



}
