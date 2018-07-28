package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 21/10/2016.
 */
public class DetailsWorkforce {

    private final StringProperty responsabilité;
    private final StringProperty nom;
    private final StringProperty coefficient;
    private final StringProperty tauxhoraire;
    private final StringProperty montant;
    private final StringProperty id;

    public DetailsWorkforce(String responsabilité, String nom, String coefficient, String tauxhoraire, String montant, String id) {
        this.responsabilité = new SimpleStringProperty(responsabilité);
        this.nom = new SimpleStringProperty(nom);
        this.coefficient = new SimpleStringProperty(coefficient);
        this.tauxhoraire = new SimpleStringProperty(tauxhoraire);
        this.montant = new SimpleStringProperty(montant);
        this.id = new SimpleStringProperty(id);
    }

    public String getResponsabilité() {
        return responsabilité.get();
    }

    public StringProperty responsabilitéProperty() {
        return responsabilité;
    }

    public void setResponsabilité(String responsabilité) {
        this.responsabilité.set(responsabilité);
    }

    public String getNom() {
        return nom.get();
    }

    public StringProperty nomProperty() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom.set(nom);
    }

    public String getCoefficient() {
        return coefficient.get();
    }

    public StringProperty coefficientProperty() {
        return coefficient;
    }

    public void setCoefficient(String coefficient) {
        this.coefficient.set(coefficient);
    }

    public String getTauxhoraire() {
        return tauxhoraire.get();
    }

    public StringProperty tauxhoraireProperty() {
        return tauxhoraire;
    }

    public void setTauxhoraire(String tauxhoraire) {
        this.tauxhoraire.set(tauxhoraire);
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
