/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.client;

import java.util.concurrent.TimeUnit;
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
public class SseBroadcastClient {

    private static final String subscribeUrl = "http://localhost:8080/stock/subscribe";

    public static void main(String... args) throws Exception {

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(subscribeUrl);
        try (final SseEventSource eventSource = SseEventSource.target(target)
                .reconnectingEvery(5, TimeUnit.SECONDS)
                .build()) {
            eventSource.register(onEvent, onError, onComplete);
            eventSource.open();
            System.out.println("Wainting for incoming event ...");

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
        String data = inboundSseEvent.readData();
        System.out.println(data);
    };

    //Error
    private static Consumer<Throwable> onError = (throwable) -> {
        System.err.println("Error: " + throwable.getClass().getSimpleName() + " | " + throwable.getMessage());
    };

    //Connection close and there is nothing to receive
    private static Runnable onComplete = () -> {
        System.out.println("Done!");
    };

}
