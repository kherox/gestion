package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 16/10/2016.
 */
public class Transport {
    private final StringProperty label;
    private final StringProperty ville;
    private final StringProperty distance;
    private final StringProperty montant;
    private final StringProperty id;

    public Transport(String label, String ville, String distance, String montant, String id) {
        this.label =  new SimpleStringProperty(label);
        this.ville = new SimpleStringProperty(ville);
        this.distance = new SimpleStringProperty(distance);
        this.montant = new SimpleStringProperty(montant);
        this.id = new SimpleStringProperty(id);
    }

    public Transport(){
        this(null,null,null,null,null);
    }


    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() {
        return label;
    }

    public void setLabel(String label) {
        this.label.set(label);
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

    public String getDistance() {
        return distance.get();
    }

    public StringProperty distanceProperty() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance.set(distance);
    }

    public String getMontant() {
        return montant.get();
    }

    public StringProperty montantProperty() {
        return montant;
    }

    public void setMontant(String montant) {
        this.montant.set(montant);
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
