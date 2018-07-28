package database;

import com.mongodb.MongoClient;

/**
 * Created by Dubai on 9/21/2017.
 */
public class MongodbManager {

    public MongoClient client;

    public MongodbManager(String host , int port) {
        client = new MongoClient();
    }
}
