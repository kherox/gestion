package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MainApp;
import model.Users;

import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Univetech Sarl on 04/11/2016.
 */
public class UsersListsController {

    @FXML
    private TableView<Users> usersListView;
    @FXML
    private TableColumn<Users,String> id;
    @FXML
    private TableColumn<Users,String> nom;
    @FXML
    private TableColumn<Users,String> prenom;
    @FXML
    private TableColumn<Users,String> contact;
    @FXML
    private TableColumn<Users,String> email;
    @FXML
    private TableColumn<Users,String> pseudo;

    public void  Reload() {
        this.usersListView.refresh();
    }

    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        Cursor cursor = this.mainApp.manager.getAllData("users");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap m = (HashMap) iterator.next();
            this.userListObservableList.add(
                    new Users(
                            m.get("name").toString(),m.get("prenom").toString(),
                            m.get("contact").toString(),m.get("psudo").toString(),
                            m.get("email").toString(),m.get("id").toString(),
                            m.get("password").toString(),m.get("role").toString()
                    )
            );
        }



    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }

    ObservableList<Users> userListObservableList = FXCollections.observableArrayList();


    @FXML
    private void initialize(){

        id.setCellValueFactory(param -> param.getValue().idProperty());
        nom.setCellValueFactory(param -> param.getValue().nameProperty());
        prenom.setCellValueFactory(param -> param.getValue().prenomProperty());
        contact.setCellValueFactory(param -> param.getValue().contactProperty());
        email.setCellValueFactory(param -> param.getValue().emailProperty());
        pseudo.setCellValueFactory(param -> param.getValue().pseudoProperty());
        this.usersListView.setItems(this.userListObservableList);

        this.usersListView.setRowFactory(param -> {
            TableRow<Users> p = new TableRow<Users>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    Users users = p.getItem();
                    try {
                        String name = mainApp.getUserRole().getUsername();
                        if (users.getPseudo().matches(name))
                        {
                            mainApp.UsersAction(users);

                        }else{
                            new WindowZone("Modification compte utilisateur","Vous ne pouvez pas modifier ce compte car vous n'est pas " +
                                    "le proprietaire","Information indisponible",false,stage);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return p;
        });

    }
}
