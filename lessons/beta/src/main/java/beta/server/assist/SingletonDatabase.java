/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.assist;

import java.util.List;
import javax.ejb.Singleton;
import beta.server.entity.Contact;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Diese Classe darf nicht direkt verwendet werden.
 *
 * @author oliver.guenther
 */
@Singleton
public class SingletonDatabase implements Serializable {

    private final Logger L = LoggerFactory.getLogger(SingletonDatabase.class);

    private List<Contact> allContacts= new ArrayList<>();

    private final AtomicBoolean inited = new AtomicBoolean(false);
    
    public void initOnce() {        
        if (inited.get()) return;
        if (inited.getAndSet(true)) return;
        Generator gen = new Generator();
        allContacts = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            allContacts.add(gen.makeContactWithId(i, i, i));
        }
        L.info("{} Contacts created", allContacts.size());
        L.info("Hashcode in init {} ",System.identityHashCode(allContacts));
    }
    
    public List<Contact> allContacts() {
        initOnce();
        return allContacts;
    }
    
    
    

}
