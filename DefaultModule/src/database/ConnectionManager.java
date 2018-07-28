package database;

import com.rethinkdb.RethinkDB;
import com.rethinkdb.gen.ast.Table;
import com.rethinkdb.gen.exc.ReqlNonExistenceError;
import com.rethinkdb.gen.exc.ReqlOpFailedError;
import com.rethinkdb.model.MapObject;
import com.rethinkdb.net.Connection;
import com.rethinkdb.net.Cursor;
import main.MainApp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONObject;
import utils.DefaultLoggerUtils;
import utils.Log4jLoggerUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.*;

/**
 * Created by Univetech Sarl on 19/10/2016.
 * Cette classe permet de se connecter a la base de donnée rethinkdb
 */
public class ConnectionManager {

    public static final RethinkDB r = RethinkDB.r;
    private Connection connection = null;
    private String dbName = "2ip";
    private String currentTableName;
    private String currentsaveId;
    private HashMap currentuser = new HashMap();

    private Log4jLoggerUtils log;

    private MainApp mainApp;
    private boolean isfirst = false;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }

    public HashMap getCurrentuser() {
        return currentuser;
    }

    public void setCurrentuser(HashMap currentuser) {
        this.currentuser = currentuser;
    }

    public String getCurrentsaveId() { return currentsaveId;}

    public void setCurrentsaveId(String currentsaveId) {this.currentsaveId = currentsaveId;}

    public static RethinkDB getR() {return r;}

    public  ConnectionManager(String hostname, int port){
       if (this.connection == null)
           //this.connection = r.connection().hostname(hostname).port(port).connect();
           this.connection = r.connection().hostname(hostname).user("admin","2ip").port(port).connect();
       log = new Log4jLoggerUtils();

    }

   public boolean CreateDatabase(){
     Object obj   =   r.dbCreate(this.dbName).run(this.connection);
       return true;
   }
   private boolean createAllTable(){
       r.db(this.dbName).tableCreate("activities").run(this.connection);
       r.db(this.dbName).tableCreate("besoinliste").run(this.connection);
       r.db(this.dbName).tableCreate("clients_type").run(this.connection);
       r.db(this.dbName).tableCreate("coefficients").run(this.connection);
       r.db(this.dbName).tableCreate("customers").run(this.connection);
       r.db(this.dbName).tableCreate("details_maindoeuvres").run(this.connection);
       r.db(this.dbName).tableCreate("devis_options").run(this.connection);
       r.db(this.dbName).tableCreate("needslists").run(this.connection);
       r.db(this.dbName).tableCreate("products").run(this.connection);
       r.db(this.dbName).tableCreate("project_type").run(this.connection);
       r.db(this.dbName).tableCreate("projects").run(this.connection);
       r.db(this.dbName).tableCreate("sections").run(this.connection);
       r.db(this.dbName).tableCreate("transports").run(this.connection); //ce transports contient les element lie au projet oubdevis
       r.db(this.dbName).tableCreate("transports_element_create").run(this.connection);
       r.db(this.dbName).tableCreate("users").run(this.connection);
       r.db(this.dbName).tableCreate("revisions").run(this.connection);
       r.db(this.dbName).tableCreate("temp_devis").run(this.connection);
       r.db(this.dbName).tableCreate("components").run(this.connection);
       r.db(this.dbName).tableCreate("workforce").run(this.connection);
       r.db(this.dbName).tableCreate("fournisseurs").run(this.connection);
       r.db(this.dbName).tableCreate("materiel_types").run(this.connection);

       isfirst = true;
       return true;
   }

   public boolean dbListe(){
       ArrayList dbliste = (ArrayList) r.dbList().run(this.connection);
       if (!dbliste.contains(this.dbName)){
           if(this.CreateDatabase())
               if(this.createAllTable())
                   return false;
       }
       log.getLogger().info("Database verification "+new Date()+" "+ConnectionManager.class.getName()+" Ligne 105 ");
       return true;

   }

   public Cursor getFirstUser(){

       return r.db(this.dbName).table("users").run(this.connection);
   }

   public Long countUserNumber(){
      return r.db(this.dbName).table("users").count().run(this.connection);
   }

   public Object createTable(String tablename){
       if (!tablename.isEmpty())
           this.currentTableName = tablename;
      return r.db(this.dbName).tableCreate(this.currentTableName).run(this.connection);
   }
   public void SaveData(String data,String table){}

   public void SaveData(JSONObject data, String table,String label){}

   public boolean SaveData(MapObject data, String table){

       String username;
       String user;

       if (!isfirst && this.mainApp.getUserRole() != null){
           data =  data
                   .with("username",this.mainApp.getUserRole().getUsername())
                   .with("user",this.mainApp.getUserRole().getUser())
                   .with("created", LocalDate.now().toString());
       }

       try {
           Object result = r.db(this.dbName).table(table).insert(data).run(this.connection);
           HashMap d = (HashMap) result;
           this.setCurrentsaveId(d.get("generated_keys").toString());
           return true;
       } catch (ReqlOpFailedError e) {
           this.createTable(table);
             this.SaveData(data, table);
       }
       return false;
   }

   public Cursor getDataById(String table, String id, boolean rs){
      Cursor cursor;
       cursor = r.db(this.dbName).table(table).filter(reqlExpr -> reqlExpr.g("project_ref").eq(id)).run(this.connection);
       return cursor;
   }

    public Cursor getDataById(String table, String id){
        Cursor cursor;
        cursor = r.db(this.dbName).table(table).filter(reqlExpr -> reqlExpr.g("id").eq(id)).run(this.connection);
        return cursor;
    }

   public Long countAll(String table){
      Long cursor =  r.db(this.dbName).table(table).count().run(this.connection);
       return cursor;
   }

   public boolean loginAction(String username,String password){

       Cursor obj =   r.db(this.dbName).table("users")
               .filter(
                       arg1 -> arg1.g("psudo").eq(username).and(
                               arg1.g("password").eq(password))
               ).run(this.connection);

       List arrayList = obj.toList();
       if (arrayList.size() > 0 ){
           HashMap hashMap = (HashMap) arrayList.get(0);

           if(hashMap.get("psudo").equals(username))
           {
               log.getLogger().info("User Connected "+username+" "+ new Date());
               this.setCurrentuser(hashMap);
               return true;

           }

           else {
               log.getLogger().info("Connexion error "+username+" "+ new Date());
               return false;
           }
       }

       log.getLogger().info("Connexion error "+username+" "+ new Date());
     return false;

   }

    public boolean getDataByNameBoolean(String table, String name){
        final Table table1 = r.db(this.dbName).table(table);
        try{
             Cursor cursor = table1.filter(reqlExpr -> reqlExpr.g("name").eq(name)).limit(1).run(this.connection);

             Object object = cursor.next();
           return true;
        }catch (NoSuchElementException as){
            return false;
        }

    }

    public Cursor getDataByName(String table, String name){
        final Table table1 = r.db(this.dbName).table(table);
        try{
           return  table1.filter(reqlExpr -> reqlExpr.g("name").eq(name)).limit(1).run(this.connection);

        }catch (NoSuchElementException as){
            return null;
        }

    }

    public Cursor getAllData(String table){
        Cursor cursor =  r.db(this.dbName).table(table).run(this.connection);
        return cursor;
    }
    //find project current
    public Cursor AviallableProject(String table){
        Cursor cursor =  r.db(this.dbName).table(table).filter(arg1 -> arg1.hasFields("devis").not()).run(this.connection);
        return cursor;
    }

    //find project current
    public Cursor FinishProject(String table){
        Cursor cursor =  r.db(this.dbName).table(table).filter(arg1 -> arg1.g("devis").eq(true)).run(this.connection);
        return cursor;
    }
    //find project current
    public Cursor AllFinishProject(String table){
        Cursor cursor =  r.db(this.dbName).table(table).filter(arg1 -> arg1.hasFields("devis")).run(this.connection);
        return cursor;
    }

    //permet de recuperer tout les produits liee a un project
    public Cursor getProductDataByProjectRef(String id){

        Cursor cursor = r.db(this.dbName).table("needslists").filter(reqlExpr -> reqlExpr.g("project_ref").eq(id)).run(this.connection);
        return cursor;
    }

    //permet de recuperer un devis bien specifique
    public Long getDevisOptions(String id){

        Long aLong = r.db(this.dbName).table("devis_options").filter(reqlExpr -> reqlExpr.g("proformacode").eq(id)).count().run(this.connection);
        return aLong;
    }


   //permet de recuperer toute rubriques liee a un projet
    public Cursor getSectionDataByProjectRef(String id){

        Cursor cursor = r.db(this.dbName).table("sections").filter(reqlExpr -> reqlExpr.g("project_ref").eq(id)).run(this.connection);
        return cursor;
    }

    public void returnDataByName(String table){
        r.db(this.dbName).table(table).withFields("name").run(this.connection);
    }


   public void UpdateData(MapObject data ,String table,String id){

       r.db(this.dbName).table(table).get(id).update(data
               .with("username",this.mainApp.getUserRole().getUsername())
               .with("user",this.mainApp.getUserRole().getUser())
               .with("created", LocalDate.now().toString())
        ).run(this.connection);

   }

    public void UpdateDataNeedList(MapObject data ,String table,String id){
        //r.db(this.dbName).table(table).filter(reqlExpr -> reqlExpr.g("project_ref").eq(id)).update(data).run(this.connection);
      Object o =   r.db(this.dbName).table(table).get(id).update(
              data.with("username",this.mainApp.getUserRole().getUsername())
                      .with("user",this.mainApp.getUserRole().getUser())
                      .with("created", LocalDate.now().toString())
      ).run(this.connection);


    }


    public ArrayList groupByData(String table,String id){
       ArrayList cursor =   r.db(this.dbName).table(table).filter(arg1 -> arg1.g("project_ref").eq(id)).group("coeff_name").run(this.connection);
        return cursor;

    }



    public boolean deleteData(String table,String id){
     Object resutl =  r.db(this.dbName).table(table).get(id).delete().run(this.connection);

        return  true;
    }
    public boolean deleteDataDevis(String table,String id){
        Object resutl =  r.db(this.dbName).table(table).filter(arg1 -> arg1.g("proformacode").eq(id)).delete().run(this.connection);
        return  true;
    }


    public Cursor __get(byte[] hacode, String table){
        try {
           return r.db(this.dbName).table(table).filter(arg1 -> arg1.g("hash_code").eq(hacode.toString())).run(this.connection);
        } catch (ReqlNonExistenceError e){
            return null;
        }


    }

    public Cursor __get(String hacode, String table){
        try {
            return r.db(this.dbName).table(table).filter(arg1 -> arg1.g("hash_code_numero").eq(hacode.hashCode())).run(this.connection);
        } catch (ReqlNonExistenceError e){
            return null;
        }


    }


    public void __save(MapObject object,String currentTableName){
      try{
         r.db(this.dbName).table(currentTableName).insert(
                 object
                         .with("username",this.mainApp.getUserRole().getUsername())
                         .with("user",this.mainApp.getUserRole().getUser())
                         .with("created", LocalDate.now().toString())
         ).run(this.connection);
        } catch(ReqlOpFailedError e){
          e.getStackTrace();
          this.createTable(currentTableName);
          this.__save(object,currentTableName);
        }
    }

    public Object __delete(String table,String hashcode){
        return  r.db(this.dbName).table(table).get("hash_code").g(hashcode).delete().run(this.connection);

    }



   public void CloseConnection(){
       connection.close();
   }




}
