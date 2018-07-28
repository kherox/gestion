package view;

import com.rethinkdb.net.Cursor;
import javafx.stage.Stage;
import main.MainApp;
import model.Users;

import java.util.HashMap;

/**
 * Created by Univetech Sarl on 04/11/2016.
 */
public class ProgessController {

    private MainApp mainApp;
    private Stage stage;

    public void setMainApp(MainApp mainApp) throws Exception {
        this.mainApp = mainApp;
        if (this.mainApp.manager.dbListe()){
            this.mainApp.initprocess = false;
            this.mainApp.closeCurrentWindow(this.stage);
            Long n = this.mainApp.manager.countUserNumber();
            if (n == 0){
                this.mainApp.UsersAction(null);
                Cursor cursor = mainApp.manager.getFirstUser();
                HashMap userMap = null;
                Object obj = cursor.next();
                userMap = (HashMap) obj;

                Users users1 = new Users(
                        userMap.get("name").toString(),userMap.get("prenom").toString(),
                        userMap.get("contact").toString(),userMap.get("psudo").toString(),
                        userMap.get("email").toString(),userMap.get("id").toString()
                        ,userMap.get("password").toString(),userMap.get("role").toString());
                this.mainApp.DefaultAction(this.stage,users1);
                //this.mainApp.closeCurrentWindow(this.stage);
            }else{
                this.mainApp.LoginAction();
            }

        }else{
            this.mainApp.initprocess = true;
            this.mainApp.closeCurrentWindow(this.stage);
            this.mainApp.UsersAction(null);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;

    }
}
