package de.ltux;

import java.util.function.Supplier;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/info")
public class InfoRestImpl {

    @ConfigProperty(name = "olli.info")
    Supplier<String> olliInfo;
    
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String find() {
        System.out.println("Called");
        return "olli.info=" + olliInfo.get();
    }
}