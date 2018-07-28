package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 16/10/2016.
 */
public class Project {

    private final StringProperty label;
    private final StringProperty client;
    private final StringProperty ville;
    private final StringProperty description;
    private final StringProperty id;
    private final StringProperty state;

    private String representant;
    private String contact;



    public Project(String label, String client, String ville, String description, String id, String state) {
        this.label = new SimpleStringProperty(label) ;
        this.client = new SimpleStringProperty(client);
        this.ville = new SimpleStringProperty(ville);
        this.description = new SimpleStringProperty(description);
        this.id = new SimpleStringProperty(id);
        this.state = new SimpleStringProperty(state);
    }

    public Project(String label,String id) {
        this.label = new SimpleStringProperty(label) ;
        this.client = null;
        this.ville = null;
        this.description = null;
        this.id = new SimpleStringProperty(id) ;
        this.state = null;
    }

    public String getRepresentant() {
        return representant;
    }

    public void setRepresentant(String representant) {
        this.representant = representant;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getState() {return state.get();}

    public StringProperty stateProperty() {return state;}

    public void setState(String state) {this.state.set(state);}

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
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

    public String getVille() {
        return ville.get();
    }

    public StringProperty villeProperty() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville.set(ville);
    }

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
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
