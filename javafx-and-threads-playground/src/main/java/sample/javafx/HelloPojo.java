package sample.javafx;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author oliver.guenther
 */
public class HelloPojo {
   
    private transient StringProperty firstNameProperty;
    
    private transient StringProperty lastNameProperty = new SimpleStringProperty(this, "lastName");

    private String firstName;
    
    private String lastName;

    public HelloPojo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }    
    
    public StringProperty firstNameProperty() {
         if (firstNameProperty == null ) {
             firstNameProperty = new SimpleStringProperty(this, "firstName");
             firstNameProperty.addListener((observable, oldValue, newValue) -> {  HelloPojo.this.firstName = newValue; });
         }         
        return firstNameProperty;
    }
    
    public StringProperty lastNameProperty() {
        return lastNameProperty;
    }
    
    public void setFirstName(String firstName) {
        if (firstNameProperty == null) this.firstName = firstName;
        else firstNameProperty.set(firstName);        
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    
    public void setLastName(String lastName) {
        lastNameProperty.set(lastName);
    }
    
    public String getLastName() {
        return lastNameProperty.get();
    }

    @Override
    public String toString() {
        return "HelloPojo{" + "firstName=" + firstNameProperty.get() + ", lastName=" + lastNameProperty.get() + '}';
    }
    
}
