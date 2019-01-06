/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.assist;

import beta.server.entity.Address;
import beta.server.entity.Communication;
import beta.server.entity.Communication.Type;
import beta.server.entity.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author oliver.guenther
 */
public class Generator {

    private final Random R = new Random();

    private final NameGenerator GEN = new NameGenerator();

    /**
     * Generates a {@link Contact}. {@link Contact#prefered} is never set.
     * <p>
     * @return a generated {@link Contact}.
     */
    public Contact makeContact() {
        return makeContact(new Contact(), makeAddress(), makeCommunication());
    }

    public Contact makeContactWithId(long contactId, long addressId, long communicationId) {
        return makeContact(new Contact(contactId), makeAddressWithId(addressId), makeCommunicationWithId(communicationId));
    }

    private Contact makeContact(Contact contact, Address address, Communication communication) {
        Name name = GEN.makeName();
        contact.setFirstName(name.getFirst());
        contact.setLastName(name.getLast());
        contact.setSex(name.getGender().ordinal() == 1 ? Contact.Sex.FEMALE : Contact.Sex.MALE);
        contact.setTitle(R.nextInt(1000) % 3 == 0 ? "Dr." : null);
        if (communication != null) {
            contact.getCommunications().add(communication);
        }
        if (address != null) {
            contact.getAddresses().add(address);
        }
        return contact;
    }

    /**
     * Generates a {@link Address}. {@link Address#preferedType} is never set.
     * <p>
     * @return a generated {@link Contact}.
     */
    public Address makeAddress() {
        return makeAddress(new Address());
    }

    public Address makeAddressWithId(long id) {
        return makeAddress(new Address(id));
    }

    private Address makeAddress(Address address) {
        GeneratedAddress genereratedAddress = GEN.makeAddress();
        address.setCity(genereratedAddress.getTown());
        address.setStreet(genereratedAddress.getStreet());
        address.setZipCode(genereratedAddress.getPostalCode());
        return address;
    }

    /**
     * Generates an amount of persisted {@link Address}.
     * <p>
     * @param amount the amount
     * @return the generated instances.
     */
    public List<Address> makeAddresses(int amount) {
        List<Address> addresses = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            addresses.add(makeAddress());
        }
        return addresses;
    }

    /**
     * Generates a non persisted {@link Communication}.
     * {@link Communication#prefered} is never set.
     * <p>
     * @return a generated {@link Communication}.
     */
    public Communication makeCommunication() {
        return makeCommunication(new Communication());
    }

    /**
     * Generates a non persisted valid {@link Communication} of the supplied
     * type.
     *
     * @param type the type to be generated
     * @return a new communication
     */
    public Communication makeCommunication(Type type) {
        return makeCommunication(new Communication(), type);
    }

    public Communication makeCommunicationWithId(long id) {
        return makeCommunication(new Communication(id));
    }

    private Communication makeCommunication(Communication c) {
        return makeCommunication(c, Communication.Type.values()[R.nextInt(Communication.Type.values().length)]);
    }

    private Communication makeCommunication(Communication c, Type type) {
        c.setType(type);
        switch (c.getType()) {
            case PHONE:
            case FAX:
            case MOBILE:
                c.setIdentifier("+49 123 45275642");
                break;
            case EMAIL:
                c.setIdentifier("test@demo.com");
                break;
            default:
                c.setIdentifier("12345abced");
                break;
        }
        return c;
    }

}
