/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author oliver.guenther
 */
@ApplicationScoped
public class PersonCaller {

    private final static Logger L = LoggerFactory.getLogger(PersonCaller.class);

    public String callPersonsManual(String rootUrl) {
        ResteasyClientBuilder builder = (ResteasyClientBuilder) ClientBuilder.newBuilder();
        ResteasyClient client = builder
                .connectTimeout(1, TimeUnit.SECONDS).build();

        ResteasyWebTarget target = client.target(rootUrl);
        Response response = target.path("persons").request().get();
        Object entity = response.getEntity();
        String result;
        if (!(entity instanceof InputStream)) {
            result = entity.toString();
        } else {
            InputStream is = (InputStream) entity;
            result = new BufferedReader(
                    new InputStreamReader(is, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
        }
        L.info("callPersonsManual({}) resulted {}", rootUrl, result);
        response.close();
        return result;
    }

    public List<Person> callPersonsProxy(String rootUrl) {

        ResteasyClientBuilder builder = (ResteasyClientBuilder) ClientBuilder.newBuilder();
        ResteasyClient client = builder
                .register(new MapperProvider())
                .connectTimeout(1, TimeUnit.SECONDS).build();

        ResteasyWebTarget target = client.target(rootUrl);
        List<Person> result = target.proxy(Persons.class).findAll();
        L.info("callPersonsProxy({}) resulted {}", rootUrl, result);
        return result;
    }

}
