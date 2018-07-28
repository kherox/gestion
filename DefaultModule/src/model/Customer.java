package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 03/10/2016.
 */
public class Customer {

    private final StringProperty name;
    private final StringProperty label;
    private final StringProperty telephone;
    private final StringProperty fax;
    private  final StringProperty country;
    private final StringProperty city;
    private final StringProperty activity;
    private final StringProperty mobile;
    private final StringProperty id;
    private final StringProperty reglement;
    private final StringProperty activity_abreger;
    private final StringProperty type_client;
    private final StringProperty type_client_abreger;
    private final StringProperty code_client;

    public String getCode_client() {
        return code_client.get();
    }

    public StringProperty code_clientProperty() {
        return code_client;
    }

    public void setCode_client(String code_client) {
        this.code_client.set(code_client);
    }

    public String getActivity_abreger() {
        return activity_abreger.get();
    }

    public StringProperty activity_abregerProperty() {
        return activity_abreger;
    }

    public void setActivity_abreger(String activity_abreger) {
        this.activity_abreger.set(activity_abreger);
    }

    public String getType_client() {
        return type_client.get();
    }

    public StringProperty type_clientProperty() {
        return type_client;
    }

    public void setType_client(String type_client) {
        this.type_client.set(type_client);
    }

    public String getType_client_abreger() {
        return type_client_abreger.get();
    }

    public StringProperty type_client_abregerProperty() {
        return type_client_abreger;
    }

    public void setType_client_abreger(String type_client_abreger) {
        this.type_client_abreger.set(type_client_abreger);
    }

    public Customer(String name, String label, String telephone,
                    String fax, String country,
                    String city, String activity, String mobile,
                    String id, String reglement,
                    String activity_abreger, String type_client, String type_client_abreger,String code) {
        this.name = new SimpleStringProperty(name);
        this.label = new SimpleStringProperty(label);
        this.telephone = new SimpleStringProperty(telephone);
        this.fax = new SimpleStringProperty(fax);
        this.country = new SimpleStringProperty(country);
        this.city = new SimpleStringProperty(city);
        this.activity = new SimpleStringProperty(activity);
        this.mobile = new SimpleStringProperty(mobile);
        this.id = new SimpleStringProperty(id);
        this.reglement = new SimpleStringProperty(reglement);
        this.activity_abreger = new SimpleStringProperty(activity_abreger);
        this.type_client = new SimpleStringProperty(type_client);
        this.type_client_abreger = new SimpleStringProperty(type_client_abreger);
        this.code_client = new SimpleStringProperty(code);
    }

public Customer(){
    this(null,null,null,null,null,null,null,null,null,null,null,null,null,null);
}

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {return name;}

    public void setName(String name) {
        this.name.set(name);
    }

    public String getLabel() {
        return label.get();
    }

    public StringProperty labelProperty() { return label;}

    public void setLabel(String label) {
        this.label.set(label);
    }

    public String getTelephone() {
        return telephone.get();
    }

    public StringProperty telephoneProperty() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone.set(telephone);
    }

    public String getFax() {
        return fax.get();
    }

    public StringProperty faxProperty() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax.set(fax);
    }

    public String getCountry() {
        return country.get();
    }

    public StringProperty countryProperty() {
        return country;
    }

    public void setCountry(String country) {
        this.country.set(country);
    }

    public String getCity() {
        return city.get();
    }

    public StringProperty cityProperty() {
        return city;
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public String getActivity() {
        return activity.get();
    }

    public StringProperty activityProperty() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public String getMobile() {
        return mobile.get();
    }

    public StringProperty mobileProperty() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile.set(mobile);
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

    public String getReglement() {
        return reglement.get();
    }

    public StringProperty reglementProperty() {
        return reglement;
    }

    public void setReglement(String reglement) {
        this.reglement.set(reglement);
    }
}
