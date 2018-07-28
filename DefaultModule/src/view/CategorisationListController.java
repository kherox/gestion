package view;

import com.rethinkdb.net.Cursor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MainApp;
import model.Categories;
import model.Operations;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Univetech Sarl on 20/01/2017.
 */
public class CategorisationListController {

    public MainApp mainApp;
    public Stage stage;

    @FXML
    TableView<Categories> categoList;
    @FXML
    TableColumn<Categories,String> libelle;
    @FXML
    TableColumn<Categories,String> id;
    @FXML
    TableColumn<Categories,String> abreviation;
    ObservableList<Categories> observableList = FXCollections.observableArrayList();


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

       Cursor cursor =  this.mainApp.manager.getAllData("project_type");
        Iterator iterator = cursor.iterator();
        while (iterator.hasNext()){
            HashMap<Categories,String> content = (HashMap<Categories, String>) iterator.next();
            this.observableList.add(new Categories(
                    content.get("name").toString(),
                    content.get("abreviation").toString(),
                    content.get("id").toString())
            );
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize(){

        libelle.setCellValueFactory(param -> param.getValue().nameProperty());
        abreviation.setCellValueFactory(param -> param.getValue().abreviationProperty());
        id.setCellValueFactory(param -> param.getValue().idProperty());
        this.categoList.setItems(this.observableList);
        this.categoList.setRowFactory(param -> {
            TableRow<Categories> p = new TableRow<Categories>();
            p.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !p.isEmpty()){
                    Categories categories = p.getItem();
                    try {
                        //mainApp.CategoriesEditionAction(categories);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            return p;
        });
    }
}
