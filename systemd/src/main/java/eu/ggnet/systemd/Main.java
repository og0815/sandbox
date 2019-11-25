/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.systemd;

import java.time.LocalTime;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    private static boolean run = true;

    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Shutdownhook called.");
            run = false;
            try {
                Thread.sleep(4000);
            } catch (InterruptedException ex) {
                //
            }
            
            System.out.println("Nach Shutdown");
        }));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (run) {
                        Thread.sleep(2000);
                        System.out.println("Doing something in the Thread at " + LocalTime.now());
                    }
                } catch (InterruptedException ex) {
                    System.out.println("Interupted Thread");
                }
                System.out.println("End of Thread");
            }
        }).start();
        
        try {
            while (run) {
                Thread.sleep(2000);
                System.out.println("Doing something at " + LocalTime.now());
            }
        } catch (InterruptedException ex) {
            System.out.println("Interupted");
        }

        System.out.println("Shutdown complete");
        System.err.println("Shutdown Error");
    }
}
