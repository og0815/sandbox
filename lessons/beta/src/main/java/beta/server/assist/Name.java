/*
 * Copyright (C) 2014 GG-Net GmbH - Oliver Günther
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
package beta.server.assist;

import java.util.Objects;

/**
 * A Name
 *
 * @author oliver.guenther
 */
public class Name {

    public static enum Gender {
        MALE, FEMALE
    }

    private final String first;

    private final String last;

    private final Gender gender;

    public Name(String first, String last, Gender gender) {
        this.first = first;
        this.last = last;
        this.gender = gender;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.first);
        hash = 67 * hash + Objects.hashCode(this.last);
        hash = 67 * hash + Objects.hashCode(this.gender);
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
        final Name other = (Name) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.last, other.last)) {
            return false;
        }
        if (this.gender != other.gender) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Name{" + "first=" + first + ", last=" + last + ", gender=" + gender + '}';
    }

}
