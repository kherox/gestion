package view;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import model.Project;

/**
 * Created by Univetech Sarl on 26/10/2016.
 */
public class devisPreviewController {
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

    private MainApp mainApp;
    private Stage stage;
    private Project project;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @FXML
    private void initialize() {
        desig.setCellValueFactory(param -> param.getValue().nameProperty());
        ref.setCellValueFactory(param -> param.getValue().refProperty());
        qte.setCellValueFactory(param -> param.getValue().qteProperty());
        prix.setCellValueFactory(param -> param.getValue().prixProperty());
        marge.setCellValueFactory(param -> param.getValue().margeProperty());
        remise.setCellValueFactory(param -> param.getValue().remiseProperty());
        montantHt.setCellValueFactory(param -> param.getValue().montanthtProperty());
    }
}

