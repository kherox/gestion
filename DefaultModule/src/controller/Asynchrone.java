package controller;


import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Cursor;
import main.MainApp;

import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Created by Univetech Sarl on 27/01/2017.
 */
public class Asynchrone {
   private MainApp mainApp;
    //verification que le devis est bien enregistrer
    //mettre a jour le devis
    //supprimer le devis pour

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public void __get(byte[] hascode,MapObject object, String table){

            Cursor obj = mainApp.manager.__get(hascode,table);
           if (obj.hasNext()){
               HashMap map = (HashMap) obj.next();
               mainApp.manager.UpdateData(object,table,map.get("id").toString());
           }else{
               this.__save(object,table);
           }

    }

    public void __get(String hascode,MapObject object, String table){

        Cursor obj = mainApp.manager.__get(hascode,table);
        if (obj.hasNext()){
            HashMap map = (HashMap) obj.next();
            mainApp.manager.UpdateData(object,table,map.get("id").toString());
        }else{
            this.__save(object,table);
        }

    }

    public void __save(MapObject object,String table){
        mainApp.manager.__save(object,table);
    }


    public void __delete(String table,String id){
        mainApp.manager.__delete(table,id);
    }
}
