/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.client;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.ltux.rest.sse.api.Stock;
import java.util.function.Consumer;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.sse.InboundSseEvent;
import javax.ws.rs.sse.SseEventSource;

/**
 *
 * @author oliver.guenther
 */
public class SseClient {

    private static final String url = "http://localhost:8080/stock/prices";

    private static ObjectMapper M = new ObjectMapper()
            .registerModules(new JavaTimeModule(), new Jdk8Module())
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

    public static void main(String... args) throws Exception {

        Client client = ClientBuilder.newClient();
        client.register(M);

        WebTarget target = client.target(url);
        try (SseEventSource eventSource = SseEventSource.target(target).build()) {

            eventSource.register(onEvent, onError, onComplete);
            eventSource.open();

            //Consuming events for one hour
            Thread.sleep(60 * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        client.close();
        System.out.println("End");
    }

    // A new event is received
    private static Consumer<InboundSseEvent> onEvent = (inboundSseEvent) -> {
        try {
//            var data = inboundSseEvent.readData(Stock.class, MediaType.APPLICATION_JSON_TYPE); // dat will nicht.
            var data = inboundSseEvent.readData();

            Stock result = M.readValue(data, Stock.class);
            System.out.println("id:" + inboundSseEvent.getId() + " | " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    //Error
    private static Consumer<Throwable> onError = (throwable) -> {
        throwable.printStackTrace();
    };

    //Connection close and there is nothing to receive
    private static Runnable onComplete = () -> {
        System.out.println("Done!");
    };

}
