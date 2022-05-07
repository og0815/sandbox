/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.server;

import de.ltux.rest.sse.api.SseResource;
import de.ltux.rest.sse.api.Stock;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.sse.OutboundSseEvent;
import javax.ws.rs.sse.Sse;
import javax.ws.rs.sse.SseBroadcaster;
import javax.ws.rs.sse.SseEventSink;

/**
 *
 * @author oliver.guenther
 */
@ApplicationScoped
public class SseResourceImpl implements SseResource {

    @Inject
    StockService stockService;

    private Sse sse;
    private SseBroadcaster sseBroadcaster;
    private OutboundSseEvent.Builder eventBuilder;

    @Context
    public void setSse(Sse sse) {
        this.sse = sse;
        this.eventBuilder = sse.newEventBuilder();
        this.sseBroadcaster = sse.newBroadcaster();
    }

    @Override
    public void prices(SseEventSink sseEventSink, int lastReceivedId) {

        int lastEventId = 1;
        if (lastReceivedId != -1) {
            lastEventId = ++lastReceivedId;
        }
        boolean running = true;
        while (running) {
            Stock stock = stockService.getNextTransaction(lastEventId);
            if (stock != null) {
                OutboundSseEvent sseEvent = this.eventBuilder
                        .name("stock")
                        .id(String.valueOf(lastEventId))
                        .mediaType(MediaType.APPLICATION_JSON_TYPE)
                        .data(Stock.class, stock)
                        .reconnectDelay(3000)
                        .comment("price change")
                        .build();
                sseEventSink.send(sseEvent);
                lastEventId++;
            }
            //Simulate connection close
            if (lastEventId % 5 == 0) {
                sseEventSink.close();
                break;
            }

            try {
                //Wait 5 seconds
                Thread.sleep(5 * 1000);
            } catch (InterruptedException ex) {
                // ...
            }
            //Simulatae a while boucle break
            running = lastEventId <= 2000;
        }
        sseEventSink.close();
    }

    @Override
    public void subscribe(@Context SseEventSink sseEventSink) {
        sseEventSink.send(sse.newEvent("Welcome !"));
        this.sseBroadcaster.register(sseEventSink);
        sseEventSink.send(sse.newEvent("You are registred !"));
    }

    @Override
    public void publish() {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int lastEventId = 0;
                boolean running = true;
                while (running) {
                    lastEventId++;
                    Stock stock = stockService.getNextTransaction(lastEventId);
                    if (stock != null) {
                        OutboundSseEvent sseEvent = eventBuilder
                                .name("stock")
                                .id(String.valueOf(lastEventId))
                                .mediaType(MediaType.APPLICATION_JSON_TYPE)
                                .data(Stock.class, stock)
                                .reconnectDelay(3000)
                                .comment("price change")
                                .build();
                        sseBroadcaster.broadcast(sseEvent);
                    }
                    try {
                        //Wait 5 seconds
                        Thread.currentThread().sleep(5 * 1000);
                    } catch (InterruptedException ex) {
                        // ...
                    }
                    //Simulatae a while boucle break
                    running = lastEventId <= 2000;
                }
            }
        };
        new Thread(r).start();
    }
}
