package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 18/12/2016.
 */
public class Devis {

    private final StringProperty client;
    private final StringProperty devis;
    private final StringProperty type;
    private final StringProperty created;
    private final StringProperty numero;
    private final StringProperty id;
    private final BooleanProperty state;



    public Devis(String client, String devis, String type, String created, String numero, String id,boolean state) {
        this.client = new SimpleStringProperty(client);
        this.devis = new SimpleStringProperty(devis);
        this.type = new SimpleStringProperty(type);
        this.created = new SimpleStringProperty(created);
        this.numero = new SimpleStringProperty(numero);
        this.id = new SimpleStringProperty(id);
        this.state = new SimpleBooleanProperty(state);
    }

    public Devis() {this(null,null,null,null,null,null,false);}

    public boolean isState() {
        return state.get();
    }

    public BooleanProperty stateProperty() {
        return state;
    }

    public void setState(boolean state) {
        this.state.set(state);
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

    public String getDevis() {
        return devis.get();
    }

    public StringProperty devisProperty() {
        return devis;
    }

    public void setDevis(String devis) {
        this.devis.set(devis);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getCreated() {
        return created.get();
    }

    public StringProperty createdProperty() {
        return created;
    }

    public void setCreated(String created) {
        this.created.set(created);
    }

    public String getNumero() {
        return numero.get();
    }

    public StringProperty numeroProperty() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero.set(numero);
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
}
