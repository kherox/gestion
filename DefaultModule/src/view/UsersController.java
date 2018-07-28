package view;

import com.rethinkdb.net.Cursor;
import controller.WindowZone;
import database.ConnectionManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.MainApp;
import model.Users;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashMap;

/**
 * Created by Univetech Sarl on 04/11/2016.
 */
public class UsersController {

    @FXML
    private TextField name;
    @FXML
    private TextField prenom;
    @FXML
    private TextField contact;
    @FXML
    private TextField email;
    @FXML
    private TextField pseudo;
    @FXML
    private TextField password;
    @FXML
    private TextField id;
    @FXML
    private Button validate;

    private Stage stage;
    private MainApp mainApp;
    private Users users;
    String role = null;

    public void setUsers(Users users) {
        if (users != null){
            this.users = users;
            role = users.getRole();
            name.setText(this.users.getName());
            prenom.setText(this.users.getPrenom());
            contact.setText(this.users.getContact());
            email.setText(this.users.getEmail());
            pseudo.setText(this.users.getPseudo());
            password.setText(this.users.getPassword());
            id.setText(this.users.getId());
        }else{
            role = "user";
        }
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MainApp getMainApp() {
        return mainApp;
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void initialize(){
        validate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
              if (IsInputValid()){
                  MessageDigest digest = null;
                  try {
                       digest = MessageDigest.getInstance("MD5");
                      digest.update(password.getText().getBytes("UTF-8"));
                  } catch (NoSuchAlgorithmException e) {
                      e.printStackTrace();
                  } catch (UnsupportedEncodingException e) {
                      e.printStackTrace();
                  }

                  Long n =  mainApp.manager.countAll("users");


                  if (n ==0)
                  {
                      role = "admin";
                  }



                      mainApp.mapObject = ConnectionManager.getR()
                            .hashMap("name",name.getText())
                            .with("prenom",prenom.getText())
                            .with("contact",contact.getText())
                            .with("email",email.getText())
                            .with("psudo",pseudo.getText())
                            .with("password", password.getText().toString())
                            .with("created", LocalDate.now().toString())
                            .with("role", role);


                  if (!id.getText().isEmpty()){
                      //update
                      mainApp.manager.UpdateData(mainApp.mapObject,"users",id.getText());
                  }else{
                      //insert
                      mainApp.manager.SaveData(mainApp.mapObject,"users");
                  }
                   new WindowZone("Donnée Validée","Felicitation","Verifier les données",true,stage);
                  mainApp.closeCurrentWindow(stage);
                  if (mainApp.initprocess)
                  {
                      try {
                          if (n == 0){
                              Cursor cursor = mainApp.manager.getFirstUser();
                              HashMap userMap = null;
                              Object obj = cursor.next();
                              userMap = (HashMap) obj;

                              Users users1 = new Users(
                                                 userMap.get("name").toString(),userMap.get("prenom").toString(),
                                                 userMap.get("contact").toString(),userMap.get("psudo").toString(),
                                                 userMap.get("email").toString(),userMap.get("id").toString()
                                                ,userMap.get("password").toString(),userMap.get("role").toString());
                              users = users1;
                          }
                          mainApp.DefaultAction(stage, users);
                      } catch (Exception e) {
                          e.printStackTrace();
                      }
                  }


              }
            }
        });
    }
    private boolean IsInputValid(){
        String errorMessage = "";
        if (name.getText().isEmpty() || name.getText() == null)
            errorMessage +="Le nom ne peut pas etre vide";
        if (prenom.getText().isEmpty() || prenom.getText() == null)
            errorMessage +="Le prenom ne peut pas etre vide";
        if (contact.getText().isEmpty() || contact.getText() == null)
            errorMessage +="Le contact ne peut pas etre vide";
        if (email.getText().isEmpty() || email.getText() == null)
            errorMessage +="Le mail ne peut pas etre vide";
        if (pseudo.getText().isEmpty() || pseudo.getText() == null)
            errorMessage +="Le pseudo ne peut pas etre vide";
        if (password.getText().isEmpty() || password.getText() == null)
            errorMessage +="Le mot de passe ne peut pas etre vide";
        if (errorMessage.isEmpty()){
            return true;
        }
        WindowZone windowZone = new WindowZone("Donnée Incorrecte",errorMessage,"Verifier les données",true,this.stage);
        return false;
    }


}
