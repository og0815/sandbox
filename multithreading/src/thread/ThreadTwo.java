/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thread;

import java.util.Random;

/**
 *
 * @author oliver.guenther
 */
public class ThreadTwo extends Thread {

    private final static Random R = new Random();
    
    public static class RunnMe implements Runnable {

        @Override
        public void run() {
             syncOut();        
        }
        
    }
    
    
    @Override
    public void run() {
        syncOut();        
    }
    
    public static void asyncOut() {
        System.out.println("starting asyncOut, called by " + Thread.currentThread());
        sleep(150);
        System.out.println("stop asyncOut, called by " + Thread.currentThread());       
    }
    
    public static synchronized void syncOut() {
        System.out.println("starting syncOut, called by " + Thread.currentThread());
        sleep(150);
        System.out.println("stop syncOut, called by " + Thread.currentThread());       
    }        
    
    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
           //
        }
    }
    public static void rsleep(int time) {
        try {
            Thread.sleep(R.nextInt(time));
        } catch (InterruptedException ex) {
           //
        }
    }
    
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            new Thread(new RunnMe()).start();
        }
    }
}
