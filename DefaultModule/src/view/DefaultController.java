package view;

import controller.WindowZone;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.MainApp;

/**
 * Created by Univetech Sarl on 01/11/2016.
 */
public class DefaultController {

    @FXML
    private MenuBar primaryMenu;
    @FXML
    private ImageView imageView;
    private MainApp mainApp;
    private Stage stage;
    private boolean isadmin;
    @FXML
    private Menu user;
    @FXML
    private Menu client;
    @FXML
    private Menu categories;



    public void setMainApp(MainApp mainApp) {

        this.mainApp = mainApp;

        if(mainApp.getUserRole().getRole().equals("admin")){
            isadmin = false;
        }else{
            user.setDisable(true);
           // client.setDisable(true);
            //categories.setDisable(true);
        }

    }

    public void setStage(Stage stage) {

        this.stage = stage;
    }

//    @FXML
//    public void creerUtilisateur() throws Exception {
//
//
//            try {
//                if (mainApp.getUserRole().getRole().equals("admin"))
//                    mainApp.UsersAction(null);
//                else
//                    new WindowZone("Permission refusée","Vous n'avez pas les permissions pour acceder a cette page","Access Administrateur",true,stage);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//
//    }

    @FXML
    private void initialize(){
        isadmin = true;
    primaryMenu.getMenus().forEach(menu -> menu.getItems().forEach(menuItem -> menuItem.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {

            /**
             *  Menu Devis operation et action
             */

            if (menuItem.getText().matches("Creer un devis")){

               try {
                   mainApp.DevisAction(null,stage,false,false);
               } catch (Exception e) {
                   e.printStackTrace();
               }
            }
            if (menuItem.getText().matches("Revision")){
                try {
                    mainApp.RevisionAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            /**
             * Menu Projet
             */
           if (menuItem.getText().matches("Creer un projet")){
               try {
                   mainApp.InitProjectAction(stage);
               } catch (Exception e) {
                   e.printStackTrace();
               }
           }


            if (menuItem.getText().matches("Besoin du projet")){
                try {
                    mainApp.BesoinListeAction(null,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (menuItem.getText().matches("Mise à jour des prix")){
                try {
                    mainApp.LoadPriceListeFormBesoinAction(null,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Main d'oeuvre")){
                try {
                    mainApp.CoefficientWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Outils & Equipement")){
                try {
                    mainApp.AddComponent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             *  Menu Produit
             */


            if (menuItem.getText().matches("Creer un article")){
                try {
                    mainApp.AddProductAction(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            /*
            Menu admibn
             */

            if (menuItem.getText().matches("Coût MO")){
                try {
                    mainApp.WorkforceAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Coût equipement")){
                try {
                    mainApp.EquipementAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Transport")){
                try {
                    mainApp.TransportCalculation(null,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Coût transport")){
                try {
                     // mainApp.TransportCalculation(null,stage);
                    mainApp.AddTransport();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().matches("Elaborer l'offre")){
                try {
                    mainApp.Predevis(null,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //

            /**
             *  Menu Utilisateur
             */

            if (menuItem.getText().equals("Utilisateurs")){

                try {
                    if (mainApp.getUserRole().getRole().equals("admin"))
                        mainApp.UsersAction(null);
                    else
                        new WindowZone("Permission refusée","Vous n'avez pas les permissions pour acceder a cette page","Access Administrateur",true,stage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des utilisateurs")){
                try {

                        mainApp.UsersListAction();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            /**
             * Menu Client
             */
            if (menuItem.getText().equals("Clients")){

                try {
                   // if (mainApp.getUserRole().getRole().equals("admin"))
                         mainApp.AddCustomersAction();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * Menu liste
             */

            if (menuItem.getText().equals("Liste des devis")){
                try {
                  mainApp.devisList();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des Clients")){
                try {
                    mainApp.AddCustomersAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des revisions")){
                try {
                    mainApp.RevisionAllListeAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des categories")){
                try {
                    //mainApp.ProductListAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des projets")){
                try {
                   mainApp.ListProject();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Listes des articles")){
                try {
                  mainApp.ProductListAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Liste des activités")){
                try {
                    if (mainApp.getUserRole().getRole().equals("admin"))
                        mainApp.CategorieListAction();
                    else
                        new WindowZone("Permission refusée","Vous n'avez pas les permissions pour acceder a cette page","Access Administrateur",true,stage);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /**
             * Menu categorisation
             */
            if (menuItem.getText().matches("Cout equipements & Outils")){

                try {
                    mainApp.EquipementAction();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Activités 2IP")){
                try {
                    mainApp.ProjectTypeAction();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (menuItem.getText().equals("Domaine d'activité")){
                try {
                    mainApp.ClientTypeAction();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

 //
            /**
             * Menu Options
             */
            if (menuItem.getText().equals("Quitter")){
                mainApp.Quitter();
            }

            if (menuItem.getText().equals("Deconnexion")){
                try{ mainApp.Deconnexion();}catch(Exception as){}

            }



        }
    })));

        imageView.setImage(new Image(getClass().getResourceAsStream("/images/Logo_2IP.png")));
    }
}
