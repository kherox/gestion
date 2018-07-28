package view;

import controller.WindowZone;
import database.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;

/**
 * Created by Dubai on 9/21/2017.
 */
public class FournisseurController {
    @FXML
    private TextField name;
    @FXML
    private TextField city;
    @FXML
    private TextField email;
    @FXML
    private TextField contact;
    @FXML
    private Button valider;

    private MainApp mainApp;
    private Stage stage;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setName(String name){
        if(!name.isEmpty() || name != null){
            this.name.setText(name);
        }
    }


    @FXML
    public void initialize(){

        valider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!ErrorCheck()){

                    mainApp.mapObject = ConnectionManager.r
                                        .hashMap("name",name.getText().toString())
                                        .with("city",city.getText().toString())
                                        .with("email",email.getText().toString())
                                        .with("contact",contact.getText().toString());
                    mainApp.manager.__save(mainApp.mapObject,"fournisseurs");
                    mainApp.closeCurrentWindow(stage);

                }else{
                    new WindowZone("Sauvegarde Impossible", "Le nom du fournisseur ne peut etre libre", "Erreur dans les données", false, stage);
                }
               // System.out.print(event.getSource().toString());
            }
        });

    }

    private boolean ErrorCheck(){
        boolean error = false;
        if(name.getText().isEmpty()){
            error = true;
        }
        return error;
    }
}
