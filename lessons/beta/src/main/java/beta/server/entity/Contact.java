/*
 * Copyright (C) 2014 GG-Net GmbH - Oliver GÃ¼nther
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package beta.server.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A contact bound by a specific customer.
 * <p>
 * All contact relevant information is held here.
 * <p>
 * @has 0..n - 0..1 Sex
 * @has 0..1 - 0..n Address
 * @has 0..1 - 0..n Communication
 *
 * @author pascal.perau
 */
public class Contact implements Serializable {

    public enum Sex {

        MALE("m"), FEMALE("w");

        private final String sign;

        private Sex(String sign) {
            this.sign = sign;
        }

        public String getSign() {
            return sign;
        }

    }

    private long id;

    /**
     * Salutation for the contact. Seperated from the title for more
     * flexibility.
     */
    private Sex sex;

    /**
     * All titles the contact carries.
     */
    private String title;

    private String firstName;

    private String lastName;

    /**
     * All {@link Address}<code>es</code> associated with the contact.
     */
    private final List<Address> addresses = new ArrayList<>();

    /**
     * All {@link Address}<code>es</code> associated with the contact.
     */
    private final List<Communication> communications = new ArrayList<>();

    public Contact() {
    }

    /**
     * Constructor for tryouts, do not use in productive.
     *
     * @param id the db id.
     */
    public Contact(long id) {
        this.id = id;
    }

    public Contact(Sex sex, String title, String firstName, String lastName) {
        this.sex = sex;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Sex getSex() {
        return sex;
    }

    public String getTitle() {
        return title;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public List<Communication> getCommunications() {
        return communications;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    /**
     * Returns a human readable representation of title, first and lastname.
     *
     * @return a human readable representation of title, first and lastname.
     */
    public String toFullName() {
        return (title == null ? "" : title + " ") + (firstName == null ? "" : firstName + " ") + (lastName == null ? "" : lastName);
    }

    public String toHtml() {
        StringBuilder sb = new StringBuilder();
        sb.append(title == null ? "" : title + "&nbsp;").append(firstName == null ? "" : firstName + "&nbsp;").append(lastName == null ? "" : lastName)
                .append(sex == null ? "" : "&nbsp;(" + sex.getSign() + ")").append("<br />");
        if (!addresses.isEmpty()) {
            sb.append("Adresse(n):<ul>");
            for (Address address : addresses) {
                sb.append("<li>").append(address.toHtml()).append("</li>");
            }
            sb.append("</ul>");
        }
        if (!communications.isEmpty()) {
            sb.append("Kommunikationsinformationen:<ul>");
            for (Communication communication : communications) {
                sb.append("<li>").append(communication.toHtml()).append("</li>");
            }
            sb.append("</ul>");
        }
        return sb.toString();
    }

    /**
     * Returns null, if the Contact is valid. Rules are:
     * <ul>
     * <li>lastName is not blank</li>
     * <li>all Address have to be valid</li>
     * <li>all Communications have to be valid</li>
     * </ul>
     *
     * @return null if instance is valid, else a string representing the
     * invalidation.
     */
    public String getViolationMessage() {
        if (StringUtils.isBlank(lastName)) {
            return "LastName is blank";
        }
        if (addresses.stream().anyMatch(a -> a.getViolationMessage() != null)) {
            return "Address: " + addresses.stream().filter(a -> a.getViolationMessage() != null).map(a -> a.getViolationMessage()).reduce((t, u) -> t + ", " + u).get();
        }
        if (communications.stream().anyMatch(a -> a.getViolationMessage() != null)) {
            return "Communications: " + communications.stream().filter(a -> a.getViolationMessage() != null).map(a -> a.getViolationMessage()).reduce((t, u) -> t + ", " + u).get();
        }
        return null;
    }

    /**
     * Multi line String representation of the contact with addresses and
     * communications
     *
     * @return a multi line string
     */
    public String toMarkdown() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n");
        sb.append(String.join("", Collections.nCopies(this.getClass().getSimpleName().length() - 1, "="))).append("\n\n");

        sb.append(toFullName()).append("\n");

        if (!addresses.isEmpty()) {
            addresses.forEach((addresse) -> {
                sb.append("\n \t -").append(addresse.toSingleLineString());
            });
        }

        if (!communications.isEmpty()) {
            for (Communication communication : communications) {
                sb.append("\n \t -").append(communication.toSingleLineString());
            }
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (int) (this.id ^ (this.id >>> 32));
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
        final Contact other = (Contact) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}
