/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.client;

import de.ltux.rest.sse.api.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.ClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

/**
 *
 * @author oliver.guenther
 */
public class GreetingClient {

    public static void main(String[] args) {
        ResteasyClientBuilder builder = (ResteasyClientBuilder) ClientBuilder.newBuilder();
        ResteasyClient client = builder
                //                .register(new MapperProvider())
                .connectTimeout(1, TimeUnit.SECONDS).build();

        ResteasyWebTarget target = client.target("http://localhost:8080/");
        List<Person> persons = target.proxy(GreetingResource.class).findAll();
        System.out.println("Result of Rest: " + persons);

    }

}
