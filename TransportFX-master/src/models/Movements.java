package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Movements {

    private final IntegerProperty id;
    private final StringProperty names;
    private final StringProperty origin;
    private final StringProperty destination;
    private final StringProperty time;

    public Movements(Integer id, String names, String origin, String destination, String time) {
        this.id = new SimpleIntegerProperty(id);
        this.names = new SimpleStringProperty(names);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.time = new SimpleStringProperty(time);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int value) {
        id.set(value);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getNames() {
        return names.get();
    }

    public void setNames(String value) {
        names.set(value);
    }

    public StringProperty namesProperty() {
        return names;
    }

    public String getOrigin() {
        return origin.get();
    }

    public void setOrigin(String value) {
        origin.set(value);
    }

    public StringProperty originProperty() {
        return origin;
    }

    public String getDestination() {
        return destination.get();
    }

    public void setDestination(String value) {
        destination.set(value);
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String value) {
        time.set(value);
    }

    public StringProperty timeProperty() {
        return time;
    }

}
