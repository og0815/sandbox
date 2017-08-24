/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.Test;

/**
 *
 * @author oliver.guenther
 */
public class ThreeHundredThreadsTest {

    @Test
    public void testThreeHundred() throws InterruptedException {
        Tresen t = new Tresen(10);
        ExecutorService pool = Executors.newFixedThreadPool(300);
        for (int i = 0; i < 20; i++) {
            pool.submit(new BestellungHans(t, 100));
            pool.submit(new BestellungUta(t, 200));
            pool.submit(new BestellungMax(t, 500));
        }

        pool.shutdown();
        pool.awaitTermination(20, TimeUnit.SECONDS);

    }
}
