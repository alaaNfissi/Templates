package model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author dan
 */
public class Trip {

    private final StringProperty tripID;
    private final StringProperty name1;
    private final StringProperty name2;
    private final StringProperty origin;
    private final StringProperty destination;
    private final StringProperty tripCost;
    private final StringProperty refundable;

    public Trip(String tripID, String name1, String name2, String origin, String destination, String tripCost, String refundable) {
        this.tripID = new SimpleStringProperty(tripID);
        this.name1 = new SimpleStringProperty(name1);
        this.name2 = new SimpleStringProperty(name2);
        this.origin = new SimpleStringProperty(origin);
        this.destination = new SimpleStringProperty(destination);
        this.tripCost = new SimpleStringProperty(tripCost);
        this.refundable = new SimpleStringProperty(refundable);
    }

    public StringProperty tripIDProperty() {
        return tripID;
    }

    public StringProperty name1Property() {
        return name1;
    }

    public StringProperty name2Property() {
        return name2;
    }

    public StringProperty originProperty() {
        return origin;
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public StringProperty tripCostProperty() {
        return tripCost;
    }

    public StringProperty refundableProperty() {
        return refundable;
    }

}
