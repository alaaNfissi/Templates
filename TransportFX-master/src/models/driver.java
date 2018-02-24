package models;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class driver {

    private final IntegerProperty id;
    private final StringProperty names;
    private final StringProperty dob;
    private final StringProperty gender;
    private final StringProperty phone;
    private final StringProperty status;
    private final StringProperty license;
    private final StringProperty issued;

    public driver(Integer id, String names, String dob, String gender, String phone, String status, String license, String issued) {
        this.id = new SimpleIntegerProperty(id);
        this.names = new SimpleStringProperty(names);
        this.dob = new SimpleStringProperty(dob);
        this.gender = new SimpleStringProperty(gender);
        this.phone = new SimpleStringProperty(phone);
        this.status = new SimpleStringProperty(status);
        this.license = new SimpleStringProperty(license);
        this.issued = new SimpleStringProperty(issued);
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

    public String getDob() {
        return dob.get();
    }

    public void setDob(String value) {
        dob.set(value);
    }

    public StringProperty dobProperty() {
        return dob;
    }

    public String getGender() {
        return gender.get();
    }

    public void setGender(String value) {
        gender.set(value);
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public String getPhone() {
        return phone.get();
    }

    public void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String value) {
        status.set(value);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getLicense() {
        return license.get();
    }

    public void setLicense(String value) {
        license.set(value);
    }

    public StringProperty licenseProperty() {
        return license;
    }

    public String getIssued() {
        return issued.get();
    }

    public void setissued(String value) {
        issued.set(value);
    }

    public StringProperty issuedProperty() {
        return issued;
    }

}
