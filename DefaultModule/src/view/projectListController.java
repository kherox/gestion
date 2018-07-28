package view;

import com.rethinkdb.net.Cursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import main.MainApp;
import model.Project;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Univetech Sarl on 18/10/2016.
 */
public class projectListController {

    @FXML
    private TableView<Project> projectTableView;
    @FXML
    private TableColumn<Project,String> labelColumn;
    @FXML
    private TableColumn<Project,String> clientColumn;
    @FXML
    private TableColumn<Project,String> stateColumn;
    @FXML
    private TableColumn<Project,String> idColumn;
    @FXML
    private Label label;
    @FXML
    private Label client;
    @FXML
    private Label description;
    @FXML
    private Label ville;
    @FXML
    private Label raison_social;

    @FXML
    private CheckBox checkBox1;
    @FXML
    private CheckBox checkBox2;
    @FXML
    private CheckBox checkBox3;
    @FXML
    private CheckBox checkBox4;

    private MainApp mainApp;
    private Stage stage;
    private ObservableList<Project> projectObservableList = FXCollections.observableArrayList();


    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    //  public void setStage(Stage stage) { this.stage = stage;}

    public void getProjectList(){
        Cursor cursor = this.mainApp.manager.getAllData("projects");
        //String label, String client, String ville, String description, String id, String state
        for (Object object : cursor){
           // HashMap<Project,String> p = (HashMap<Project,String>) object;
            HashMap hashMap  = (HashMap) object;
            ArrayList arrayList = (ArrayList) hashMap.get("client");
            HashMap clientmap = (HashMap) arrayList.get(0);

            this.projectObservableList.add(new Project(
              clientmap.get("raison_social").toString(),clientmap.get("name").toString()
              ,clientmap.get("city").toString(),hashMap.get("description").toString(),hashMap.get("name").toString(),
                hashMap.get("username").toString()
            ));
        }
    }

    @FXML
    private void initialize(){
       this.labelColumn.setCellValueFactory(param -> param.getValue().labelProperty());
       this.clientColumn.setCellValueFactory(param -> param.getValue().clientProperty());
       this.stateColumn.setCellValueFactory(param -> param.getValue().stateProperty());
       this.idColumn.setCellValueFactory(param -> param.getValue().idProperty());
       this.projectTableView.setItems(projectObservableList);

       this.projectTableView.setRowFactory(param -> {
           TableRow<Project> p = new TableRow<Project>();
           p.setOnMouseClicked(event -> {
               Project project = p.getItem();
               label.setText(project.getId());
               client.setText(project.getClient());
               description.setText(project.getDescription());
               ville.setText(project.getVille());
               raison_social.setText(project.getLabel());
           });
           return p;
       });
    }
}
