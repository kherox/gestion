package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.beans.binding.ListBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApp;
import model.ProjectType;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 08/11/2016.
 */
public class ProjetTypeController {

    @FXML
    private GridPane mainPane;
    @FXML
    private GridPane secondPane;
    @FXML
    private TextField type_libelle;
    @FXML
    private TextField type_abreviation;
    @FXML
    private Button type_validate;
    @FXML
    private TextField main_libelle;
    @FXML
    private TextField main_taux;
    @FXML
    private TextField main_valeur;

    @FXML
    private ComboBox main_equipement;
    @FXML
    private ComboBox main_qualification;
    @FXML
    private Button main_validate;
    @FXML
    private Button terminate_button;
    @FXML
    private Label main_label;
    @FXML
    private TableView<ProjectType> tableView;
    @FXML
    private TableColumn<ProjectType,String> activite;
    @FXML
    private TableColumn<ProjectType,String> taux;
    @FXML
    private TableColumn<ProjectType,String> qualification;
    @FXML
    private TableColumn<ProjectType,String> libelle;

    @FXML
    private Button delete;
    @FXML
    private TextField id;

    //private HashMap hashMap = new HashMap();
    private HashMap mainHashMap = new HashMap();
    private ObservableList ob = FXCollections.observableArrayList();
    private ObservableList<ProjectType> projectList = FXCollections.observableArrayList();


    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Cursor cursor = mainApp.manager.getAllData("project_type");
        Iterator iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap hashMap = (HashMap) iterator.next();

            projectList.add(new ProjectType(
                    hashMap.get("name").toString(),
                    hashMap.get("abreviation").toString(),"","",hashMap.get("id").toString(), hashMap.get("abreviation").toString()
            ));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){
        this.tableView.setItems(projectList);
        //activite;taux;qualification;libelle;
        activite.setCellValueFactory(param -> param.getValue().activiteProperty());
       // taux.setCellValueFactory(param -> param.getValue().tauxProperty());
//        qualification.setCellValueFactory(param -> param.getValue().qualificationProperty());
        libelle.setCellValueFactory(param -> param.getValue().labelProperty());
        secondPane.setVisible(false);
        main_label.setVisible(false);
        terminate_button.setVisible(false);
        main_qualification.getItems().add("Standard");
        main_qualification.getItems().add("Specialisé");

       main_equipement.valueProperty().addListener((observable, oldValue, newValue) -> {
           if (newValue != null && !main_equipement.getItems().contains(newValue) && newValue != "")
           {
               main_equipement.getItems().add(newValue);
               main_equipement.valueProperty().setValue("");
           }

       });


        type_validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(type_libelle.getText().isEmpty() || type_abreviation.getText().isEmpty()){
                    new WindowZone("Entrer invalid","Vous ne pouvez pas enregistrer un element vide","Données invalides",true,stage);
                }else{

                  // terminate_button.setVisible(true);
                    String currentsaveId =  "";
                    mainApp.mapObject = ConnectionManager.getR().hashMap("name",type_libelle.getText()).with("abreviation",type_abreviation.getText());
                    if (id.getText().isEmpty()){

                        mainApp.manager.SaveData(mainApp.mapObject,"project_type");
                        currentsaveId = mainApp.manager.getCurrentsaveId();
                    }else{
                        mainApp.manager.UpdateData(mainApp.mapObject,"project_type",id.getText());
                        currentsaveId = id.getText();
                    }




                    projectList.add(new ProjectType(
                            type_libelle.getText(), (String) type_libelle.getText(),
                            type_libelle.getText(),type_libelle.getText(),
                            currentsaveId,type_abreviation.getText()
                    ));

                type_libelle.setText("");
                type_abreviation.setText("");
               
                }



            }
        });

        tableView.setRowFactory( param ->  {TableRow<ProjectType> p = new TableRow<ProjectType>();
        p.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2 && !p.isEmpty()){
                ProjectType projectType = p.getItem();
                try {
                    type_libelle.setText(projectType.getLabel());
                    type_abreviation.setText(projectType.getActivite());
                    id.setText(projectType.getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
             return p;
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ProjectType selected = tableView.selectionModelProperty().getValue().getSelectedItem();
                Alert alerts = new Alert(Alert.AlertType.CONFIRMATION);
                alerts.initOwner(stage);
                alerts.setTitle("Confirmation");
                alerts.setHeaderText("Validation de la suppression");
                alerts.setContentText("Voulez-vous vraiment supprimer ce produits");
                alerts.showAndWait();
                if (alerts.getResult().getButtonData().isDefaultButton()){
                   mainApp.manager.deleteData("project_type",selected.getId());
                   int index = tableView.selectionModelProperty().getValue().getSelectedIndex();
                    tableView.getItems().remove(index);
                }
            }
        });

        main_validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                HashMap hashMap = new HashMap();
                hashMap.put("equipement",main_equipement.getItems());
                hashMap.put("taux_horaire",main_taux.getText());
                hashMap.put("valeur_equipement",main_valeur.getText());
                hashMap.put("libelle",main_libelle.getText());
                hashMap.put("qualification",main_qualification.getValue());
                mainHashMap.put(main_libelle.getText(),hashMap);

               main_equipement.setItems(null);
                main_equipement.setItems(ob);


                projectList.add(new ProjectType(
                        main_libelle.getText(), (String) main_qualification.getValue(),
                        main_taux.getText(),main_valeur.getText(),
                        "000",type_abreviation.getText()
                ));
                main_libelle.setText("");
                main_taux.setText("");
                main_valeur.setText("");
                terminate_button.setVisible(true);




            }
        });


//        terminate_button.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//               mainApp.mapObject = ConnectionManager.getR()
//                        .hashMap("name",type_libelle.getText())
//                        .with("abreviation",type_abreviation.getText())
//                        .with("datamap",mainHashMap);
//                //System.out.println(mainHashMap);
//                mainApp.manager.SaveData(mainApp.mapObject,"project_type");
//                mainApp.closeCurrentWindow(stage);
//
//            }
//        });

    }
}
