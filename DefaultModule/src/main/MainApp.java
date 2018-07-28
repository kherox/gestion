package main;/**
 * Created by Univetech Sarl on 03/10/2016.
 */

import com.rethinkdb.model.MapObject;
import controller.UserRole;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import utils.DefaultLoggerUtils;
import utils.Log4jLoggerUtils;
import view.*;
import view.ComponentsController;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;


public class MainApp extends Application {

    private Stage stage = new Stage();
    private Stage mainstage = new Stage();
    private Process process;
    private BorderPane rootLayout;
    private VBox vBox;
    private JSONObject object = new JSONObject();
    public MapObject mapObject;

    public ConnectionManager manager;

    public JSONObject getObject() {return object;}
    public void setObject(JSONObject object) {this.object = object;}
    public MapObject getMapObject() {return mapObject;}
    public void setMapObject(MapObject mapObject) {this.mapObject = mapObject;}


    private ObservableList<Customer> customersData = FXCollections.observableArrayList();
    private ObservableList<Rubriques> rubriquesData = FXCollections.observableArrayList();
    private ObservableList<Products> productsData = FXCollections.observableArrayList();
    private ObservableList<Project> projectData = FXCollections.observableArrayList();

    public void setCustomersData(ObservableList<Customer> customersData) {this.customersData = customersData;}

    public ObservableList<Project> getProjectData() {return projectData;}
    public void setProjectData(ObservableList<Project> projectData) {this.projectData = projectData;}
    public ObservableList<Rubriques> getRubriquesData() { return rubriquesData;}
    public ObservableList<Customer> getCustomersData() {
        return customersData;
    }
    public ObservableList<Products> getProductsData() {return productsData;}
    public boolean initprocess = false;
    private Stage closeProgressStage;
    private UserRole userRole;

    private ArrayList zeroTonenteen = new ArrayList();
    private ArrayList bignumber     = new ArrayList();

    private Log4jLoggerUtils logUtils = new Log4jLoggerUtils();

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public Stage getStage() {
        return stage;
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        this.initAction();
    }

    public MainApp() throws IOException, InterruptedException {
        logUtils.getLogger().trace("Lancement de l'activité principale "+ new Date());
        try { //192.168.1.254
            this.manager = new ConnectionManager("127.0.0.1",28015);
            this.manager.setMainApp(this);
        } catch (Exception e) {
            logUtils.getLogger().error("Database Connexion " + e.getMessage() + " "+new Date(),e.fillInStackTrace());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Connection au seveur");
            alert.setHeaderText("Connexion impossible");
            alert.setContentText("Impossible de se connecter au serveur d'application. Verifier que vous avez bien demarer le seveur d'abord");
            alert.showAndWait();
            System.exit(1);
        }


    }

    private void initAction() throws Exception {
        logUtils.getLogger().info("Init Action "+new Date());
        this.ProgessAction();
    }

    public void ProgessAction() throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "progress.fxml";
        String title = "Chargement";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(false);
        ProgessController controller = loader.getController();
        dialogstage.show();
        controller.setStage(dialogstage);
        controller.setMainApp(this);

    }
    public void LoginAction() throws Exception{
        logUtils.getLogger().info("Login action by date  "+new Date());
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "login.fxml";
        String title = "Panneau de connection";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(false);
        LoginController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        dialogstage.showAndWait();
    }

    public void DefaultAction(Stage stage, Users users) throws Exception {
        this.userRole = new UserRole(users);
        FXMLLoader  loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "default.fxml";
        String title = "Menu Principal";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,0);
        mainstage = dialogstage;
        dialogstage.setResizable(false);
        DefaultController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        dialogstage.show();
        this.closeCurrentWindow(stage);
    }

    public void SelectDevisModelAction(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "choixbox.fxml";
        String title = "Choississez le model";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(false);
        ChoixBoxController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        dialogstage.showAndWait();

    }
    public void InitProjectAction(Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "preprocess.fxml";
        String title = "CREATION D'OFFRE PROJET";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        preprocessController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        dialogStage.showAndWait();
    }

    public void loadProcessAction(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "loadProcess.fxml";
        String title = "Charger un projet en cours";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(false);
        loadProcessController controller = loader.getController();
        controller.setMainApp(this,dialogStage);
        dialogStage.showAndWait();
        //this.closeCurrentWindow(stage);
    }

    //chargement de la fenetre pour faire la liste des besoins
    public void BesoinListeAction(Project current,Stage stage) throws  Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "createbesoinliste.fxml";
        String title = "Liste des besoins";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(true);
        createBesoinListeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setProject(current);
        dialogStage.showAndWait();
        ///this.closeCurrentWindow(stage);
    }

    public void LoadPriceListeFormBesoinAction(Project current, Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "PriceandNeed.fxml";
        String title = "Besoins et prix";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        PriceandNeedsController controller = loader.getController();
        controller.setMainApp(this);
        controller.setProject(current);
        controller.setStage(dialogStage);
        dialogStage.showAndWait();
        //this.closeCurrentWindow(stage);
    }

    public void CoefficientWindow() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "newcoefficient.fxml";
        String title = "Calcul du coup de la main d'oeuvre";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        coefficientController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setProject();
        dialogStage.showAndWait();
    }

    public void AddComponent() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "addcomponents.fxml";
        String title = "Calcul de la valeur des equipements";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        AddComponentsController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setProject();
        dialogStage.showAndWait();
    }

    public void createSchemaMainDoeuvre(Project current, Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "schemaMainDoeuvre.fxml";
        String title = "Configuration de la main d'oeuvre";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        schemaMainDoeuvreController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setProject(current);
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
        this.closeCurrentWindow(stage);
    }
    public void TransportCalculation(Project current, Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "transport.fxml";
        String title = "Calcul du coût du transport";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        transportController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setProject(current);
        dialogStage.setResizable(true);
        dialogStage.showAndWait();
        //this.closeCurrentWindow(stage);
    }

    public void AddTransport() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "transportcreate.fxml";
        String title = "Creation du moyen de transport";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        TransportationController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        dialogStage.setResizable(false);
        dialogStage.showAndWait();
        //this.closeCurrentWindow(stage);
    }

    public void Predevis(Project current, Stage stage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "predevis.fxml";
        String title = "Composition du devis";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        preDevisController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        dialogStage.showAndWait();
    }

    public void MainDevisAction(Project project, ObservableList<Products> productsObservableList,
                                ObservableList<Products> selectedArrayListe, Stage stage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "maindevis.fxml";
        String title = "Devis projet";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        MainDevisController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setSelectedProduct(selectedArrayListe);
        controller.setAllProductses(productsObservableList);
        controller.setProject(project);
        stage1.showAndWait();

    }
    public void viewPrintAction(Project current, ObservableList<Products> printProductListe, HashMap printmap, Operations operations, Stage stage, Revision revision, boolean isproject) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "viewprint.fxml";
        String title = "Page d'impression";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(true);
        viewPrintController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setDatamap(printmap);
        controller.setProject(current);
        controller.setProductsObservableList(printProductListe);
        controller.setIsproject(isproject);
        dialogStage.showAndWait();
    }

    public void viewPrintOperation(Project current, ObservableList<Products> printProductListe, HashMap printmap, Operations operations, Stage stage, Revision revision, boolean isproject) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "viewPrintOperations.fxml";
        String title = "Apercu de l'operation";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(true);
        viewPrintOperations controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setIsproject(isproject);
        controller.setOperations(operations);
        dialogStage.showAndWait();
    }



    public void viewPrintActionRevision(Project current, ObservableList<Products> printProductListe, HashMap printmap, Operations operations, Stage stage, Revision revision, boolean isproject) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "viewPrintRevision.fxml";
        String title = "Apercu d'une revision";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(true);
        viewPrintRevisionController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setIsproject(isproject);
        controller.setProductsObservableList(printProductListe);
        controller.setRevision(revision);
        controller.setDatamap(printmap);
        dialogStage.showAndWait();
        //DefaultAction(stage);
    }


    public void viewPrintActionDevisSimple(Project current, ObservableList<Products> printProductListe, HashMap printmap, Operations operations, Stage stage,Revision revision) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "viewPrintDevisSimple.fxml";
        String title = "Apercu du devis";
        //Creation de la fenetre
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,1);
        dialogStage.setResizable(true);
        viewPrintDevisSimpleController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogStage);
        controller.setDatamap(printmap);
        controller.setProductsObservableList(printProductListe);
        dialogStage.showAndWait();

    }



    public void DevisAction(Operations operations, Stage stage, boolean b, boolean isproject) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "devis.fxml";
        String title = "Devis";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,0);
        dialogstage.setResizable(true);
        DevisController controller = loader.getController();
        controller.isRevision(b);
        controller.setMainApp(this);
        controller.isProject(isproject);
        controller.setStage(dialogstage);
        controller.setOperations(operations);

        dialogstage.showAndWait();
        //this.closeCurrentWindow(stage);
    }

    public void MaindoeuvreAction(ObservableList<Products> p) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "maindeouvre.fxml";
        String title = "Main d'oeuvre";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(true);
        MainDoeuveController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        controller.setObservableList(p);
        dialogstage.showAndWait();
    }

    public void TransportationAction(ObservableList<Products> p) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "transportation.fxml";
        String title = "Calcul du transport";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(true);
        TransportationController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
       // controller.setProductses(p);
        dialogstage.showAndWait();
    }


    public void ImpressionAction(HashMap<String,String> dataMap,ObservableList<Products> productsO) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "impression.fxml";
        String title = "Impression";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(true);
        ImpressionController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        controller.setDataMap(dataMap);
        controller.setProductsObservableList(productsO);
        dialogstage.showAndWait();
    }

    public void UsersAction(Users users ) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "users.fxml";
        String title = "Creation/Modification d'un utilisateur";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        stage1.setResizable(false);
        UsersController controller = loader.getController();
        controller.setMainApp(this);
        controller.setUsers(users);
        controller.setStage(stage1);
//        if (users == null){
//            stage1.show();
//        }else{
//            stage1.showAndWait();
//        }
        stage1.showAndWait();

    }

    public void UsersListAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "usersList.fxml";
        String title = "Liste des utilsateurs";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        stage1.setResizable(false);
        UsersListsController controller = loader.getController();
        controller.setMainApp(this);
        stage1.showAndWait();
    }


    public void ProjectTypeAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "projectType.fxml";
        String title = "Domaine d'activité client";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        stage1.setResizable(false);
        ProjetTypeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void ClientTypeAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "clienttype.fxml";
        String title = "Activité 2IP";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        stage1.setResizable(false);
        ClientTypeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void WorkforceAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "workforce.fxml";
        String title = "Main d'oeuvre";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        stage1.setResizable(true);
        WorkForceController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void EquipementAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "components.fxml";
        String title = "Equipements";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        stage1.setResizable(true);
        ComponentsController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void AddProductAction(Products products) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "products.fxml";
        String title = "Ajout d'un nouveau produit";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(false);
        ProductsController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        controller.setProducts(products);
        dialogstage.showAndWait();
    }

    public void AddCustomersAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "customers.fxml";
        String title = "Liste des client";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,1);
        dialogstage.setResizable(true);
        customersController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        dialogstage.showAndWait();
    }

    public void ProductListAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage dialogstage = new Stage();
        String path = "productList.fxml";
        String title = "Liste des produits";
        dialogstage = CreateDialogStage(path,title,dialogstage,loader,0);
        dialogstage.setResizable(true);
        ProductListController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(dialogstage);
        dialogstage.showAndWait();
    }

    public void ListProject() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage dialogStage = new Stage();
        String path = "projectList.fxml";
        String title = "Liste des projets";
        dialogStage = CreateDialogStage(path,title,dialogStage,loader,0);
        dialogStage.setResizable(true);
        projectListController controller = loader.getController();
        controller.setMainApp(this);
        controller.getProjectList();
        controller.setStage(dialogStage);
        dialogStage.showAndWait();
    }



    public void OperationsAction( ) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "operations.fxml";
        String title = "Liste des operations";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        stage1.setResizable(false);
        OperationsController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void PreoderTravailAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "preodreTravail.fxml";
        String title = "Numero Bon de commande client";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        PreodreTravailController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public void OrderTravailAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "ordreTravail.fxml";
        String title = "Ordre de Travail";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        OrdreTravailController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

    public boolean showPersonEditDialog(Customer person) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            String path="customereditdialog.fxml";
            String title = "Modification/Ajout  d'un client";
            dialogStage = this.CreateDialogStage(path,title,dialogStage,loader,1);
            dialogStage.setResizable(false);
            // Set the person into the controller.
            customerEditDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setCustomer(person);
            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();
            return controller.isOkClicked();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void RevisionAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "rliste.fxml";
        String title = "Revision";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        RListeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.setResizable(false);
        stage1.showAndWait();
    }

    public void RevisionAllListeAction() throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "RAllListe.fxml";
        String title = "Revisions effectuées";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        RAllListeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.setResizable(false);
        stage1.showAndWait();
    }

    public void RevisionListAction(HashMap map) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "revisionlist.fxml";
        String title = "Revision probable";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        RevisionListController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setHashMap(map);
        stage1.setResizable(false);
        stage1.showAndWait();
    }

    public void CategorieListAction() throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "categorisationList.fxml";
        String title = "Liste des categories";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        CategorisationListController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.setResizable(false);
        stage1.showAndWait();
    }

    public void CategoriesEditionAction(Categories categories) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "categorisationEdit.fxml";
        String title = "Modification d'une categories";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        CategorisationEditController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setCategories(categories);
        stage1.setResizable(false);
        stage1.showAndWait();
    }

    public void RevisionEditAction(Revision revision) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "devisreviser.fxml";
        String title = "Apercu des revisions";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        DevisReviserController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setCategories(revision);
        stage1.setResizable(false);
        stage1.showAndWait();


    }

    public void Deconnexion() throws Exception {
        logUtils.getLogger().trace("Deconnexion de l'application "+new Date());
        closeCurrentWindow(this.mainstage);
        LoginAction();
    }

    public void Quitter() {
        logUtils.getLogger().trace("Fermeture de l'application "+ new Date());
        System.exit(0);
    }








    private Stage CreateDialogStage(String path,String title,Stage stage,FXMLLoader loader,int i) throws Exception{
        loader.setLocation(ClassLoader.getSystemResource("view/"+path));
        AnchorPane pane = null;
        VBox vBox = null;
        Scene scene;
        if (i == 1){
            pane = loader.load();
            scene =  new Scene(pane);
        }else{
            vBox = loader.load();
            scene = new Scene(vBox);
        }

        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(this.stage);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/Logo_2IP.jpg")));
        stage.setScene(scene);
        return stage;
    }

    public void closeCurrentWindow(Stage stage){ stage.close();}
    public static void main(String[] args) {
       System.setProperty("file.encoding","UTF8");
        launch(args);
    }


    //creation d'un fournisseur
    public void createFournisseur(String name) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "fournisseur.fxml";
        String title = "Fournisseurs";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        FournisseurController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setName(name);
        stage1.setResizable(false);
        stage1.showAndWait();
    }


    //creation d'un type de materiel
    public void createMaterielType(String name) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "materieltype.fxml";
        String title = "Type de materiel";
        stage1 = CreateDialogStage(path,title,stage1,loader,1);
        MaterieltypeController controller = loader.getController();
        controller.setMainApp(this);
        controller.setStage(stage1);
        controller.setName(name);
        stage1.setResizable(false);
        stage1.showAndWait();
    }


    //List des devis simple
    public void devisList() throws Exception {
        FXMLLoader loader = new FXMLLoader();
        Stage stage1 = new Stage();
        String path = "devisliste.fxml";
        String title = "Liste des devis";
        stage1 = CreateDialogStage(path,title,stage1,loader,0);
        DevisListController controller = loader.getController();
        stage1.setResizable(false);
        controller.setMainApp(this);
        controller.setStage(stage1);
        stage1.showAndWait();
    }

}
