package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 16/10/2016.
 * Main d'oeuvre
 */
public class Workforce {
    private final StringProperty label;
    private final StringProperty nbreEquipe;
    private final StringProperty coefficient_horaire;
    private final StringProperty taux_horaire;
    private final StringProperty valeur_equipement;
    private final StringProperty coefficient_equipement;
    private final StringProperty nbre_jours;
    private final StringProperty montant;
    private final StringProperty id;


    public Workforce(
            String label, String nbreEquipe, String coefficient_horaire,
            String taux_horaire, String valeur_equipement, String coefficient_equipement,
            String nbre_jours, String id,String montant) {
        this.label = new SimpleStringProperty(label);
        this.nbreEquipe = new SimpleStringProperty(nbreEquipe);
        this.coefficient_horaire = new SimpleStringProperty(coefficient_horaire);
        this.taux_horaire = new SimpleStringProperty(taux_horaire);
        this.valeur_equipement = new SimpleStringProperty(valeur_equipement);
        this.coefficient_equipement = new SimpleStringProperty(coefficient_equipement);
        this.nbre_jours = new SimpleStringProperty(nbre_jours);
        this.montant = new SimpleStringProperty(montant);
        this.id = new SimpleStringProperty(id);
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

    public String getNbreEquipe() {
        return nbreEquipe.get();
    }

    public StringProperty nbreEquipeProperty() {
        return nbreEquipe;
    }

    public void setNbreEquipe(String nbreEquipe) {
        this.nbreEquipe.set(nbreEquipe);
    }

    public String getCoefficient_horaire() {
        return coefficient_horaire.get();
    }

    public StringProperty coefficient_horaireProperty() {
        return coefficient_horaire;
    }

    public void setCoefficient_horaire(String coefficient_horaire) {
        this.coefficient_horaire.set(coefficient_horaire);
    }

    public String getTaux_horaire() {
        return taux_horaire.get();
    }

    public StringProperty taux_horaireProperty() {
        return taux_horaire;
    }

    public void setTaux_horaire(String taux_horaire) {
        this.taux_horaire.set(taux_horaire);
    }

    public String getValeur_equipement() {
        return valeur_equipement.get();
    }

    public StringProperty valeur_equipementProperty() {
        return valeur_equipement;
    }

    public void setValeur_equipement(String valeur_equipement) {
        this.valeur_equipement.set(valeur_equipement);
    }

    public String getCoefficient_equipement() {
        return coefficient_equipement.get();
    }

    public StringProperty coefficient_equipementProperty() {
        return coefficient_equipement;
    }

    public void setCoefficient_equipement(String coefficient_equipement) {
        this.coefficient_equipement.set(coefficient_equipement);
    }

    public String getNbre_jours() {
        return nbre_jours.get();
    }

    public StringProperty nbre_joursProperty() {
        return nbre_jours;
    }

    public void setNbre_jours(String nbre_jours) {
        this.nbre_jours.set(nbre_jours);
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

    public Workforce() {
        this(null,null,null,null,null,null,null,null,null);
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
}
