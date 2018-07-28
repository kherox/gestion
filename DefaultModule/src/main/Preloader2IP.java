package main;

import javafx.application.Preloader;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Univetech Sarl on 03/11/2016.
 */
public class Preloader2IP extends Preloader {

private  Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception {
      /*  this.stage = primaryStage;
        this.stage = primaryStage;
        this.stage.setTitle("2IP Gestion");
        this.stage.getIcons().add(new Image("file:ressources/images/Logo_2IP.jpg"));
        this.stage.setResizable(false);
        this.stage.show();*/


    }

    @Override
    public void handleApplicationNotification(PreloaderNotification notification){
        if(notification instanceof StateChangeNotification)
            this.stage.hide();
    }
}
