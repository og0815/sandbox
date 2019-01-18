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
 * A Address
 *
 * @author oliver.guenther
 */
public class GeneratedAddress {

    private final String street;

    private final int number;

    private final String postalCode;

    private final String town;

    public GeneratedAddress(String street, int number, String postalCode, String town) {
        this.street = street;
        this.number = number;
        this.postalCode = postalCode;
        this.town = town;
    }

    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getTown() {
        return town;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.street);
        hash = 67 * hash + this.number;
        hash = 67 * hash + Objects.hashCode(this.postalCode);
        hash = 67 * hash + Objects.hashCode(this.town);
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
        final GeneratedAddress other = (GeneratedAddress) obj;
        if (this.number != other.number) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.postalCode, other.postalCode)) {
            return false;
        }
        if (!Objects.equals(this.town, other.town)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "GeneratedAddress{" + "street=" + street + ", number=" + number + ", postalCode=" + postalCode + ", town=" + town + '}';
    }

}
