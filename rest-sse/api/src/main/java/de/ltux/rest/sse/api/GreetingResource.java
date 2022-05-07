package de.ltux.rest.sse.api;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/persons")
public interface GreetingResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<Person> findAll();

}
