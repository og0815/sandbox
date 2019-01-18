/*
 * Copyright (C) 2018 GG-Net GmbH
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
package beta.server.test;

import org.junit.Before;
import org.junit.Test;

import beta.server.entity.*;
import beta.server.assist.Generator;
import beta.server.entity.Communication.Type;
import  static beta.server.entity.Communication.Type.SKYPE;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Ignore;

/**
 *
 * @author lucas.huelsen
 */
public class ContactTest {

    private Contact contact;

    private Generator GEN = new Generator();

    @Before
    public void executeBeforeEach() {
        contact = GEN.makeContact();
    }

    @Test
    public void testGetViolationMessages() {
        assertThat(contact.getViolationMessage()).as("Contact with valid values").isNull();
    }

    @Test
    public void testGetViolationMessagesNonValid() {
        contact.setLastName("");
        assertThat(contact.getViolationMessage()).as("Contact without lastName").isNotBlank();
    }

    @Test
    public void testGetViolationMessagesNonValid2() {
        contact.getAddresses().add(new Address());
        assertThat(contact.getViolationMessage()).as("Contact with invalid address").isNotBlank();
    }


}
