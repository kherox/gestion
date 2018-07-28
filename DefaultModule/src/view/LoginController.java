package view;

import controller.UserRole;
import controller.WindowZone;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.MainApp;
import model.Users;

import java.util.HashMap;

/**
 * Created by Univetech Sarl on 28/11/2016.
 */
public class LoginController {

    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button logged;
    @FXML
    private ImageView imageView;

    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize(){

        logged.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {

                    if (!username.getText().isEmpty() && !password.getText().isEmpty()){
                        if(mainApp.manager.loginAction(username.getText(),password.getText()))
                        {
                           HashMap currentUser =  mainApp.manager.getCurrentuser();
                            Users users = new Users(
                                    currentUser.get("name").toString(),currentUser.get("prenom").toString(),
                                    currentUser.get("contact").toString(),currentUser.get("psudo").toString(),
                                    currentUser.get("email").toString(),currentUser.get("id").toString(),
                                    "00000",currentUser.get("role").toString());

                            mainApp.DefaultAction(stage,users);
                             //mainApp.closeCurrentWindow(stage);
                        }else{
                            username.setText("");
                            password.setText("");
                            new WindowZone("Probleme de connexion","Nous ne pouvons par vous connecter","Verifier vos donnée",true,stage);
                        }
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //imageView.setImage(new Image("file:ressources/images/Logo_2IP.jpg"));
    }
}
