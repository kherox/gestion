package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.MainApp;
import model.Products;
import org.apache.commons.logging.Log;

import java.util.HashMap;

/**
 * Created by Univetech Sarl on 02/11/2016.
 */
public class ProductsController {

    @FXML
    private TextField reference;
    @FXML
    private TextField libelle;
    @FXML
    private TextField quantite;
    @FXML
    private TextField prixu;
    @FXML
    private TextField marge;
    @FXML
    private TextField remise;
    @FXML
    private TextField montant;
    @FXML
    private TextField id;
    @FXML
    private Button validate;
    @FXML
    private Button quitter;
    @FXML
    private ComboBox fournisseurs;
    @FXML
    private ComboBox  materiel_type;

    private MainApp mainApp;
    private Stage stage;
    private Products products;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
       Cursor cursor =  this.mainApp.manager.getAllData("fournisseurs");
       while (cursor.hasNext()){
           Object object = cursor.next();
           HashMap map = (HashMap) object;
           String n = (String) map.get("name");
           fournisseurs.getItems().add(n);
       }
       cursor = this.mainApp.manager.getAllData("materiel_types");
       while (cursor.hasNext()){

           Object object = cursor.next();
           HashMap map = (HashMap) object;
           String n = (String) map.get("name");
           materiel_type.getItems().add(n);
       }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setProducts(Products products) {
        this.products = products;
        if (this.products != null){
            libelle.setText(products.getName());
            reference.setText(products.getRef());
            quantite.setText(products.getQte());
            prixu.setText(products.getPrix());
           // marge.setText(products.getMarge());
           /// remise.setText(products.getRemise());
            id.setText(products.getId());

        }
    }

    @FXML
    private void initialize(){




        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (isInputValid()){
                    //La marge devient le prix HT et la remise le prix TTC
                    //mise a jour
                    mainApp.mapObject = ConnectionManager.getR()
                            .hashMap("name",libelle.getText())
                            .with("reference",reference.getText())
                            .with("quantite",quantite.getText())
                            .with("prixu",prixu.getText())
                            .with("fournisseur",fournisseurs.getSelectionModel().getSelectedItem())
                            .with("marge","1.0")
                            .with("remise","1.0")
                            .with("materiel_type",materiel_type.getSelectionModel().getSelectedItem())
                            .with("montant",prixu.getText());

                    try{

                        if (!id.getText().isEmpty()){
                            //update
                            mainApp.manager.UpdateData(mainApp.mapObject,"products",id.getText());
                             Empty();
                        }else{
                            //test if name is existe is base
                             if(mainApp.manager.getDataByNameBoolean("products",libelle.getText()))
                                 new WindowZone("Ajout de doublons","Vous ne pouvez pas ajouter ce produit deux fois","Corriger le nomdu produit",true,stage);
                             else{

                                 mainApp.manager.SaveData(mainApp.mapObject,"products");
                                  Empty();
                             }
                        }

                       // mainApp.closeCurrentWindow(stage);
                       
                    }catch (NullPointerException e){
                       if(mainApp.manager.getDataByNameBoolean("products",libelle.getText()))
                                new WindowZone("Ajout de doublons","Vous ne pouvez pas ajouter ce produit deux fois","Corriger le nomdu produit",true,stage);
                             else
                                mainApp.manager.SaveData(mainApp.mapObject,"products");
                        Empty();
                    }


                    }


            }
        });


         quitter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               
               mainApp.closeCurrentWindow(stage);


            }
        });

       fournisseurs.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {
               if (event.getCode().name().matches("ENTER")){
                   String name = (String) fournisseurs.getSelectionModel().getSelectedItem();
                   try {

                       mainApp.createFournisseur(name);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }
                   fournisseurs.getItems().add(name);
               }

           }
       });
       materiel_type.setOnKeyPressed(new EventHandler<KeyEvent>() {
           @Override
           public void handle(KeyEvent event) {

               if (event.getCode().name().matches("ENTER")){
                   String name = (String) materiel_type.getSelectionModel().getSelectedItem();
                   try {
                       mainApp.createMaterielType(name);
                   } catch (Exception e) {
                       e.printStackTrace();
                   }

                   materiel_type.getItems().add(name);
               }

           }
       });
    }

    private void Empty(){
       // remise.setText("");
        prixu.setText("");
       // marge.setText("");
        libelle.setText("");
        reference.setText("");
        quantite.setText("");
    }



    private boolean isInputValid(){

        String errorMessage = "";
        if (reference.getText().isEmpty() || reference.getText() == null)
            errorMessage += "Vous devez renseigner la reference du produit";
        if (libelle.getText().isEmpty() || libelle.getText() == null)
            errorMessage += "Renseigner le libéllé";
       /* if (montant.getText().isEmpty() || montant.getText() == null)
            errorMessage += "Le Montant doit etre renseigner";*/
       /* if (Double.isNaN(Double.parseDouble(montant.getText())))
            errorMessage += "Le Montant doit etre un nombre";*/
        if (quantite.getText().isEmpty() || quantite.getText() == null)
            errorMessage += "La quantité  doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(quantite.getText())))
            errorMessage += "La quantite  doit etre un nombre";
        if (prixu.getText().isEmpty() || prixu.getText() == null)
            errorMessage += "Le prix unitaire  doit etre renseigner";
        if (Double.isNaN(Double.parseDouble(prixu.getText())))
            errorMessage += "Le prix unitaire doit etre un nombre";
        // if (marge.getText().isEmpty() || marge.getText() == null)
        //     errorMessage += "La marge doit etre renseigner";
        // if (Double.isNaN(Double.parseDouble(prixu.getText())))
        //     errorMessage += "La marge  doit etre un nombre";
        // if (remise.getText().isEmpty() || remise.getText() == null)
        //     errorMessage += "La remise doit etre renseigner";
        // if (Double.isNaN(Double.parseDouble(remise.getText())))
        //     errorMessage += "La remise  doit etre un nombre";
        if (!errorMessage.isEmpty()) {
            new WindowZone("Sauvegarde Impossible", errorMessage, "Erreur dans les données", false, stage);
            return false;
        }else
            return true;

    }

}
