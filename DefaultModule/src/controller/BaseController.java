package controller;

import database.ConnectionManager;
import javafx.stage.Stage;
import main.MainApp;

/**
 * Created by Dubai on 9/22/2017.
 */
public class BaseController {

    protected MainApp mainApp;
    protected Stage stage = null;
    protected ConnectionManager manager;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

   public BaseController(){
      this.manager =  new ConnectionManager("127.0.0.1",28015);
      this.mainApp = mainApp;
    }
}
