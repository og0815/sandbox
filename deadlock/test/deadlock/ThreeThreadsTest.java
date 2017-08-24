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
public class ThreeThreadsTest {

    @Test
    public void testThree() throws InterruptedException {
        Tresen t = new Tresen(5);
        ExecutorService pool = Executors.newFixedThreadPool(3);
        pool.submit(new BestellungHans(t, 50));
        pool.submit(new BestellungUta(t, 100));
        pool.submit(new BestellungMax(t, 200));

        pool.shutdown();
        pool.awaitTermination(20, TimeUnit.SECONDS);

    }
}
