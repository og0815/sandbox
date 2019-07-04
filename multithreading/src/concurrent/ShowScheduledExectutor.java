/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author oliver.guenther
 */
public class ShowScheduledExectutor {

    public static class RunFour implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(20);
                System.out.println("In:" + toString());
            } catch (InterruptedException ex) {
                System.out.println("interupted");
            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(3);
        es.scheduleAtFixedRate(new RunFour(), 2, 5, TimeUnit.SECONDS);
        Thread.sleep(30000);
        System.out.println("Calling shutdown");
        es.shutdown();
        System.out.println("Called shutdown");
    }

}
