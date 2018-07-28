package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 18/12/2016.
 */
public class Revision {

    private final StringProperty client;
    private final StringProperty name;
    private final StringProperty proforma;
    private final StringProperty type;
    private final StringProperty numero;
    private final StringProperty id;


    public Revision(String client, String name, String proforma, String type, String numero, String id) {
        this.client = new SimpleStringProperty(client);
        this.name = new SimpleStringProperty(name);
        this.proforma = new SimpleStringProperty(proforma);
        this.type = new SimpleStringProperty(type);
        this.numero = new SimpleStringProperty(numero);
        this.id = new SimpleStringProperty(id);
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

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getProforma() {
        return proforma.get();
    }

    public StringProperty proformaProperty() {
        return proforma;
    }

    public void setProforma(String proforma) {
        this.proforma.set(proforma);
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
