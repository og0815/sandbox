package de.ltux.jdk17;

import java.io.Serializable;

/**
 * Classdoc.
 *
 * @author oliver.guenther
 */
public record Person(String firstname, String lastname, int age) implements Serializable {

    /**
     * Javadoc für den Costructor
     *
     * @param firstname blas
     * @param lastname blus
     * @param age fds
     */
    public Person   {

    }

    /**
     * Javadoc, wenn unbeding benötigt.
     *
     * @return blu
     */
    public String lastname() {
        return lastname;
    }

    public String getLastname() {
        return lastname;
    }

}
