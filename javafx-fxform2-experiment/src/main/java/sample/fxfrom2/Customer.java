/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.fxfrom2;

import javafx.beans.property.*;

/**
 *
 * @author oliver.guenther
 */
public class Customer {

    private final StringProperty firstNameProperty = new SimpleStringProperty();

    private final StringProperty lastNameProperty = new SimpleStringProperty();

    private final IntegerProperty ageProperty = new SimpleIntegerProperty();

    private String title;

    public Customer() {
    }

    public Customer(String title, String firstName, String lastName, int age) {
        this.title = title;
        firstNameProperty.set(firstName);
        lastNameProperty.set(lastName);
        ageProperty.set(age);
    }

    public StringProperty firstNameProperty() {
        return firstNameProperty;
    }

    public StringProperty lastNameProperty() {
        return lastNameProperty;
    }

    public IntegerProperty ageProperty() {
        return ageProperty;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Customer{" + "title=" + title + ", firstName=" + firstNameProperty.get() + ", lastName=" + lastNameProperty.get() + ", age=" + ageProperty.get() + '}';
    }

}
