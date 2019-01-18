/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.assist;

import java.util.List;
import javax.ejb.Singleton;
import beta.server.entity.Contact;
import java.util.ArrayList;
import javax.annotation.PostConstruct;

/**
 * Diese Classe darf nicht direkt verwendet werden.
 *
 * @author oliver.guenther
 */
@Singleton
public class SingletonDatabase {

    public List<Contact> allContacts = new ArrayList<>();

    @PostConstruct
    public void init() {
        Generator gen = new Generator();
        for (int i = 0; i < 200; i++) {
            allContacts.add(gen.makeContactWithId(i, i, i));
        }
    }

}
