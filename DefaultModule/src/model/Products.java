package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 08/10/2016.
 */
public class Products {
    private final StringProperty name;
    private final StringProperty ref;
    private final StringProperty qte;
    private final StringProperty prix;
    private final StringProperty marge;
    private final StringProperty remise;
    private final StringProperty montantht;
    private final StringProperty id;
    private final StringProperty rubrique;
    private final StringProperty sousrubrique;

    public String getRubrique() {
        return rubrique.get();
    }

    public StringProperty rubriqueProperty() {
        return rubrique;
    }

    public void setRubrique(String rubrique) {
        this.rubrique.set(rubrique);
    }

    public String getSousrubrique() {
        return sousrubrique.get();
    }

    public StringProperty sousrubriqueProperty() {
        return sousrubrique;
    }

    public void setSousrubrique(String sousrubrique) {
        this.sousrubrique.set(sousrubrique);
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

    public Products(String name, String ref,
                    String qte, String prix,
                    String marge, String remise,
                    String montantht, String id) {
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.qte = new SimpleStringProperty(qte);
        this.prix = new SimpleStringProperty(prix);
        this.marge = new SimpleStringProperty(marge);
        this.remise = new SimpleStringProperty(remise);
        this.montantht = new SimpleStringProperty(montantht);
        this.id = new SimpleStringProperty(id);
        this.rubrique = null;
        this.sousrubrique = null;
    }

    public Products(String name, String ref,
                    String qte, String prix,
                    String marge, String remise,
                    String montantht, String id,String rubr,String subr) {
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.qte = new SimpleStringProperty(qte);
        this.prix = new SimpleStringProperty(prix);
        this.marge = new SimpleStringProperty(marge);
        this.remise = new SimpleStringProperty(remise);
        this.montantht = new SimpleStringProperty(montantht);
        this.id = new SimpleStringProperty(id);
        this.rubrique = new SimpleStringProperty(rubr);
        this.sousrubrique = new SimpleStringProperty(subr);
    }

    public Products(String name,String ref,String qte,String id){
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.qte = new SimpleStringProperty(qte);
        this.id = new SimpleStringProperty(id);
        this.prix = null;
        this.marge = null;
        this.remise = null;
        this.montantht = null;
        this.rubrique = null;
        this.sousrubrique = null;
    }

    public Products(String name,String ref,String qte,String price,String id){
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.qte = new SimpleStringProperty(qte);
        this.prix = new SimpleStringProperty(price);
        this.id = new SimpleStringProperty(id);
        this.marge = null;
        this.remise = null;
        this.montantht = null;
        this.rubrique = null;
        this.sousrubrique = null;
    }

    public Products(String name,String ref,String id){
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.id = new SimpleStringProperty(id);
        this.qte = null;
        this.prix = null;
        this.marge = null;
        this.remise = null;
        this.montantht = null;
        this.rubrique = null;
        this.sousrubrique = null;
    }

    public Products(String name,String ref,String qte,String prix,String remise,String marge,String id){
        this.name = new SimpleStringProperty(name);
        this.ref = new SimpleStringProperty(ref);
        this.qte = new SimpleStringProperty(qte);
        this.prix = new SimpleStringProperty(prix);
        this.marge = new SimpleStringProperty(marge);
        this.remise = new SimpleStringProperty(remise);
        this.id = new SimpleStringProperty(id);
        this.montantht = null;
        this.rubrique = null;
        this.sousrubrique = null;
    }

    public Products(){this(null,null,null,null,null,null,null,null,null,null);}

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {return name;}

    public void setName(String name) {
        this.name.set(name);
    }

    public String getRef() {
        return ref.get();
    }

    public StringProperty refProperty() {
        return ref;
    }

    public void setRef(String ref) {this.ref.set(ref);}

    public String getQte() {
        return qte.get();
    }

    public StringProperty qteProperty() {
        return qte;
    }

    public void setQte(String qte) {
        this.qte.set(qte);
    }

    public String getPrix() {
        return prix.get();
    }

    public StringProperty prixProperty() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix.set(prix);
    }

    public String getMarge() {
        return marge.get();
    }

    public StringProperty margeProperty() {
        return marge;
    }

    public void setMarge(String marge) {
        this.marge.set(marge);
    }

    public String getRemise() {
        return remise.get();
    }

    public StringProperty remiseProperty() {
        return remise;
    }

    public void setRemise(String remise) {
        this.remise.set(remise);
    }

    public String getMontantht() {
        return montantht.get();
    }

    public StringProperty montanthtProperty() {
        return montantht;
    }

    public void setMontantht(String montantht) {
        this.montantht.set(montantht);
    }
}
