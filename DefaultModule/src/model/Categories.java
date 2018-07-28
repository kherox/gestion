package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 20/01/2017.
 */
public class Categories {
    public  final StringProperty name;
    public final StringProperty abreviation;
    public final StringProperty id;

    public Categories(){ this(null,null,null);}

    public Categories(String name, String abreviation, String id) {
        this.name = new SimpleStringProperty(name);
        this.abreviation = new SimpleStringProperty(abreviation);
        this.id = new SimpleStringProperty(id);
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

    public String getAbreviation() {
        return abreviation.get();
    }

    public StringProperty abreviationProperty() {
        return abreviation;
    }

    public void setAbreviation(String abreviation) {
        this.abreviation.set(abreviation);
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
