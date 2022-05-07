package de.ltux.rest.sse.server;

import de.ltux.rest.sse.api.*;
import java.util.Arrays;
import java.util.List;
import org.jboss.logging.Logger;

public class GreetingResourceImpl implements GreetingResource {

    @Override
    public List<Person> findAll() {
        Logger.getLogger(GreetingResourceImpl.class).info("findAll called");
        return Arrays.asList(new Person("Oliver", "Günther", 45), new Person("Charlotte", "Elger-Günther", 37));
    }
}
