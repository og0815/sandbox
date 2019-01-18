/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.eao;

import beta.server.assist.SingletonDatabase;
import beta.server.entity.Contact;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 */
@Stateless
public class ContactEao {

    private final Logger L = LoggerFactory.getLogger(ContactEao.class);

    @Inject
    private SingletonDatabase db;

    /**
     * Returns a random contact.
     *
     * @return a random contact.
     */
    public Contact findAny() {
        Random r = new Random();
        int size = db.allContacts().size();
        return db.allContacts().get(r.nextInt(size));
    }

    /**
     * Returns all contacts.
     *
     * @return all contacts.
     */
    public List<Contact> findAll() {
        L.info("Hashcode in findall {} ", System.identityHashCode(db.allContacts()));
        return new ArrayList<>(db.allContacts());
    }

    /**
     * Returns all contacts, within a range.
     *
     * @param start start in the total result
     * @param limit amount to return
     * @return all contacts, within a range.
     */
    public List<Contact> findAll(int start, int limit) {
        // TODO: Jens
        return null;
    }

    public List<Contact> find(String aSearchString) {
        // TODO: Jens
        return null;
    }

}
