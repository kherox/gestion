package model;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import main.MainApp;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 09/02/2017.
 */
public class ComponentsController {


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
        Cursor cursor = mainApp.manager.getAllData("components");
        Iterator iterator = cursor.iterator();
        while(iterator.hasNext()){
            HashMap hashMap = (HashMap) iterator.next();

            projectList.add(new ProjectType(
                    hashMap.get("name").toString(),
                    hashMap.get("montant").toString(),"","",hashMap.get("id").toString(), hashMap.get("montabt").toString()
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
        taux.setCellValueFactory(param -> param.getValue().tauxProperty());
        qualification.setCellValueFactory(param -> param.getValue().qualificationProperty());
        libelle.setCellValueFactory(param -> param.getValue().labelProperty());

        terminate_button.setVisible(false);
        main_qualification.getItems().add("Standard");
        main_qualification.getItems().add("Specialisé");


        main_validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(main_libelle.getText().isEmpty() || main_taux.getText().isEmpty()){
                    new WindowZone("Entrer invalid","Vous ne pouvez pas enregistrer un element vide","Données invalides",true,stage);
                }else{

                    // terminate_button.setVisible(true);
                    String currentsaveId =  "";
                    mainApp.mapObject = ConnectionManager.getR()
                            .hashMap("name",type_libelle.getText())
                            .with("montant",main_taux.getText());
                    if (id.getText().isEmpty()){
                        mainApp.manager.SaveData(mainApp.mapObject,"components");
                        currentsaveId = mainApp.manager.getCurrentsaveId();
                    }else{
                        mainApp.manager.UpdateData(mainApp.mapObject,"components",id.getText());
                        currentsaveId = id.getText();
                    }

                    projectList.add(new ProjectType(
                            main_libelle.getText(),  main_qualification.getValue().toString(),
                            main_taux.getText(),type_libelle.getText(),
                            currentsaveId,main_taux.getText()
                    ));
                }



            }

        });


        tableView.setRowFactory( param ->  {TableRow<ProjectType> p = new TableRow<ProjectType>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    ProjectType projectType = p.getItem();
                    try {
                        main_libelle.setText(projectType.getLabel());
                        main_taux.setText(projectType.getTaux());
                        // main_qualification.getItems().
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
                    mainApp.manager.deleteData("components",selected.getId());
                    int index = tableView.selectionModelProperty().getValue().getSelectedIndex();
                    tableView.getItems().remove(index);
                }
            }
        });
    }
}
