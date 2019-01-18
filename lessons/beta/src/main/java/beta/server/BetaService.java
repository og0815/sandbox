/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server;

import beta.server.assist.SingletonDatabase;
import beta.server.entity.Contact;
import java.util.Comparator;
import javax.ejb.Stateless;

/**
 * Allows storage of objects.
 * 
 * @author oliver.guenther
 */
@Stateless
public class BetaService {
    
    private SingletonDatabase db;    
    
    /**
     * Stores a contact in the database.
     * 
     * @param contact 
     */
    public void store(Contact contact) {
        if (contact.getId() == 0) {            
            Long nextid = db.allContacts().stream().map(Contact::getId).max(Comparator.naturalOrder()).get()+1;
            contact.setId(nextid);
        }
        if (!db.allContacts().contains(contact)) {
            db.allContacts().add(contact);
        }
    }
}
