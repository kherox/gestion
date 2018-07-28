package model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by Univetech Sarl on 05/10/2016.
 */
public class Activities {
    private final StringProperty name;
    private final StringProperty code;
    private final BooleanProperty pole;
    private final StringProperty type;

    public Activities(String name,String code,boolean pole,String type){
        this.name = new SimpleStringProperty(name);
        this.code = new SimpleStringProperty(code);
        this.pole = new SimpleBooleanProperty(pole);
        this.type = new SimpleStringProperty(type);
    }
    public Activities(){
        this(null,null, false,null);
    }

    public String getType() {return type.get();}

    public StringProperty typeProperty() {return type;}

    public void setType(String type) {this.type.set(type);}

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCode() {
        return code.get();
    }

    public StringProperty codeProperty() {
        return code;
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public boolean isPole() {return pole.get();}

    public BooleanProperty poleProperty() {return pole;}

    public void setPole(boolean pole) {this.pole.set(pole);}
}
