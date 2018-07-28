package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 04/11/2016.
 */
public class Users {

    private final  StringProperty name;
    private final  StringProperty prenom;
    private final  StringProperty contact;
    private final  StringProperty pseudo;
    private final  StringProperty email;
    private final  StringProperty id;
    private final  StringProperty password;
    private final  StringProperty role;

    public String getRole() {
        return role.get();
    }

    public StringProperty roleProperty() {
        return role;
    }

    public void setRole(String role) {
        this.role.set(role);
    }

    public Users(String name, String prenom, String contact, String pseudo, String email, String id, String password, String role) {
        this.name = new SimpleStringProperty(name) ;
        this.prenom = new SimpleStringProperty(prenom);
        this.contact = new SimpleStringProperty(contact);
        this.pseudo = new SimpleStringProperty(pseudo);
        this.email = new SimpleStringProperty(email);
        this.id = new SimpleStringProperty(id);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    public Users(){
        this(null,null,null,null,null,null,null,null);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public String getPrenom() {
        return prenom.get();
    }

    public StringProperty prenomProperty() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom.set(prenom);
    }

    public String getContact() {
        return contact.get();
    }

    public StringProperty contactProperty() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact.set(contact);
    }

    public String getPseudo() {
        return pseudo.get();
    }

    public StringProperty pseudoProperty() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo.set(pseudo);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
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
