/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.util.concurrent.TimeUnit;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author oliver.guenther
 */
@ApplicationScoped
public class RestCaller {

    //https://cat-fact.herokuapp.com/facts/random
    public String callCatFacts() {
        ResteasyClientBuilder builder = (ResteasyClientBuilder) ClientBuilder.newBuilder();
        ResteasyClient client = builder
                .connectTimeout(1, TimeUnit.SECONDS).build();

        ResteasyWebTarget target = client.target("https://cat-fact.herokuapp.com/");
        Response response = target.path("facts/random").request().get();
        String result = response.getEntity().toString();
        response.close();
        return result;
    }
}
