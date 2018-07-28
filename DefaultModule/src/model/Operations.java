package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.converter.LocalDateTimeStringConverter;

import java.time.LocalDate;

/**
 * Created by Univetech Sarl on 03/11/2016.
 */
public class Operations {

    private final StringProperty name;
    private final StringProperty client;
    private final SimpleStringProperty  created;
    private final StringProperty opertorname;
    private final StringProperty username;
    private final StringProperty code;
    private final StringProperty id;

    public String getCreated() {
        return created.get();
    }

    public SimpleStringProperty createdProperty() {
        return created;
    }

    public void setCreated(String created) {
        this.created.set(created);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public Operations(String name, String client, String created, String opertorname, String username, String code,String id) {
        this.name = new SimpleStringProperty(name);
        this.client = new SimpleStringProperty(client);
        this.created = new SimpleStringProperty(created) ;
        this.opertorname = new SimpleStringProperty(opertorname);
        this.username = new SimpleStringProperty(username);
        this.code = new SimpleStringProperty(code);
        this.id = new SimpleStringProperty(id);
    }

    public String getId() {
        return id.get();
    }

    public StringProperty idProperty() {
        return id;
    }

    public void setId(String id) {
        this.id.set(id);
    }

    public Operations(){
        this(null,null,null,null,null,null,null);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getClient() {
        return client.get();
    }

    public StringProperty clientProperty() {
        return client;
    }

    public void setClient(String client) {
        this.client.set(client);
    }


    public String getOpertorname() {
        return opertorname.get();
    }

    public StringProperty opertornameProperty() {
        return opertorname;
    }

    public void setOpertorname(String opertorname) {
        this.opertorname.set(opertorname);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }
}
