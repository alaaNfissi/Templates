package _models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Student {

    private final SimpleIntegerProperty id;
    private final SimpleStringProperty names;
    private final SimpleStringProperty course;

    public Student(int id, String name, String course) {
        this.id = new SimpleIntegerProperty(id);
        this.names = new SimpleStringProperty(name);
        this.course = new SimpleStringProperty(course);
    }

    public void setId(Integer id) {
        this.id.set(id);
    }

    public void setNames(String names) {
        this.names.set(names);
    }

    public void setCourse(String course) {
        this.course.set(course);
    }

    public Integer getId() {
        return id.get();
    }

    public String getNames() {

        return names.get();
    }

    public String getCourse() {

        return course.get();
    }
    
    
    
    public SimpleIntegerProperty idProperty(){
    return id;
    }
    public SimpleStringProperty namesProperty(){
    return names;
    }
   public SimpleStringProperty courseProperty(){
    return course;
    }
    
    
    
    
    
    

}
