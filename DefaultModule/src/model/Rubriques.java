package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 04/10/2016.
 */
public class Rubriques {
    private final StringProperty name;

    public Rubriques(String name){this.name = new SimpleStringProperty(name);}
    public Rubriques(){
        this(null);
    }
    public String getName() {return name.get();}
    public StringProperty nameProperty() {
        return name;
    }
    public void setName(String name) {
        this.name.set(name);
    }
}
