package main;

import com.sun.javafx.application.LauncherImpl;

/**
 * Created by Univetech Sarl on 03/11/2016.
 */
public class Main {

    public static  void main(String [] args){
        LauncherImpl.launchApplication(MainApp.class,Preloader2IP.class,args);
    }
}
