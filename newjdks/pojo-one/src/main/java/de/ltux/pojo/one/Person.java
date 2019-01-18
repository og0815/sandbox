/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.one;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author oliver.guenther
 */
public class Person implements Serializable {

    public enum Sex {
        MALE,FEMALE;
    } 
    
    private String firstname;

    private String lastname;

    private int age;

    public Person() {
    }

    public Person(String firstname, String lastname, int age) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String toMarkdown() {
        return this.getClass().getSimpleName() + "\n"
                + "=====\n"
                + "- firstName: " + firstname + "\n"
                + "- lastName: " + lastname + "\n"
                + "- age: " + age + "\n";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.firstname);
        hash = 19 * hash + Objects.hashCode(this.lastname);
        hash = 19 * hash + this.age;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.age != other.age) {
            return false;
        }
        if (!Objects.equals(this.firstname, other.firstname)) {
            return false;
        }
        if (!Objects.equals(this.lastname, other.lastname)) {
            return false;
        }
        return true;
    }

    public String toModuleInfo() {
        Module module = this.getClass().getModule();
        return this.getClass().getName() + "{module=" + module + ", name=" + module.getName() + ", isNamed=" + module.isNamed() + ", descriptor=" + module.getDescriptor() + "}";
    }

    @Override
    public String toString() {
        return "Person{" + "firstname=" + firstname + ", lastname=" + lastname + ", age=" + age + '}';
    }

    public static void main(String[] args) {
        var p = new Person("Max", "Mustermann", 99);
        p.setAge(20);
        System.out.println(p);
        System.out.println(p.toModuleInfo());
    }
}
