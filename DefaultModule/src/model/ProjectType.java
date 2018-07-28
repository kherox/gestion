package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 08/11/2016.
 */
public class ProjectType {

    private final StringProperty label;
    private final StringProperty qualification;
    private final StringProperty taux;
    private final StringProperty valeur;
    private final StringProperty id;
    private final StringProperty activite;

    public ProjectType(String label, String qualification, String taux, String valeur, String id, String activite) {
        this.label = new SimpleStringProperty(label);
        this.qualification = new SimpleStringProperty(qualification);
        this.taux = new SimpleStringProperty(taux);
        this.valeur = new SimpleStringProperty(valeur);
        this.id = new SimpleStringProperty(id);
        this.activite = new SimpleStringProperty(activite);
    }

    public ProjectType(){
        this(null,null,null,null,null,null);
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

    public String getQualification() {
        return qualification.get();
    }

    public StringProperty qualificationProperty() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification.set(qualification);
    }

    public String getTaux() {
        return taux.get();
    }

    public StringProperty tauxProperty() {
        return taux;
    }

    public void setTaux(String taux) {
        this.taux.set(taux);
    }

    public String getValeur() {
        return valeur.get();
    }

    public StringProperty valeurProperty() {
        return valeur;
    }

    public void setValeur(String valeur) {
        this.valeur.set(valeur);
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

    public String getActivite() {
        return activite.get();
    }

    public StringProperty activiteProperty() {
        return activite;
    }

    public void setActivite(String activite) {
        this.activite.set(activite);
    }
}
