/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.server;

import de.ltux.rest.sse.api.Stock;
import io.quarkus.runtime.StartupEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

/**
 *
 * @author oliver.guenther
 */
@ApplicationScoped
public class StockService {

    private static final BigDecimal UP = BigDecimal.valueOf(1.05f);
    private static final BigDecimal DOWN = BigDecimal.valueOf(0.95f);

    List<String> stockNames = Arrays.asList("GOOG", "IBM", "MS", "GOOG", "YAHO");
    List<Stock> stocksDB = new ArrayList<>();
    private AtomicInteger counter = new AtomicInteger(0);

    public void init(@Observes StartupEvent init) {
        //Open price
        System.out.println("@Start Init ...");
        stockNames.forEach(stockName -> {
            stocksDB.add(new Stock(counter.incrementAndGet(), stockName, generateOpenPrice(), LocalDateTime.now()));
        });

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //Simulate Change price and put every x seconds
                while (true) {
                    int indx = new Random().nextInt(stockNames.size());
                    String stockName = stockNames.get(indx);
                    BigDecimal price = getLastPrice(stockName);
                    BigDecimal newprice = changePrice(price);
                    Stock stock = new Stock(counter.incrementAndGet(), stockName, newprice, LocalDateTime.now());
                    stocksDB.add(stock);

                    int r = new Random().nextInt(30);
                    try {
                        Thread.currentThread().sleep(r * 1000);
                    } catch (InterruptedException ex) {
                        // ...
                    }
                }
            }
        };
        new Thread(runnable).start();
        System.out.println("@End Init ...");
    }

    public Stock getNextTransaction(Integer lastEventId) {
        return stocksDB.stream().filter(s -> s.getId().equals(lastEventId)).findFirst().orElse(null);
    }

    BigDecimal generateOpenPrice() {
        float min = 70;
        float max = 120;
        return BigDecimal.valueOf(min + new Random().nextFloat() * (max - min)).setScale(4, RoundingMode.CEILING);
    }

    BigDecimal changePrice(BigDecimal price) {
        return Math.random() >= 0.5 ? price.multiply(UP).setScale(4, RoundingMode.CEILING) : price.multiply(DOWN).setScale(4, RoundingMode.CEILING);
    }

    private BigDecimal getLastPrice(String stockName) {
        return stocksDB.stream().filter(stock -> stock.getName().equals(stockName)).findFirst().get().getPrice();
    }
}
