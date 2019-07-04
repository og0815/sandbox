/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrent;

import java.security.SecureRandom;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * @author oliver.guenther
 */
public class ShowExectutor {

    public static ReentrantLock L = new ReentrantLock();
    
    public static CountDownLatch C = new CountDownLatch(5);

    public static class RunTwo implements Runnable {

        @Override
        public void run() {
            try {
                L.lockInterruptibly();
                Thread.sleep(3000);
                System.out.println("In:" + toString());
                L.unlock();
            } catch (InterruptedException ex) {
                L.unlock();
                System.out.println("interupted");
            }
            C.countDown();
        }

    }

    public static class RunThree implements Runnable {

        @Override
        public void run() {
            SecureRandom R = new SecureRandom();
            int i = 0;
            L.lock();
            while (!Thread.interrupted() && i < 4) {
//            while(i < 10) {
                for (int j = 0; j < 5000000; j++) {
                    Math.log(R.nextDouble());
                }
                System.out.println("In:" + this + " count " + i);
                i++;
            }
            L.unlock();
            C.countDown();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 10; i++) {
            Future<?> f = es.submit(new RunTwo());
            Future<?> v = es.submit(new RunThree());
        }

        C.await();
        System.out.println("Calling shutdown");
        es.shutdownNow();
        System.out.println("Called shutdown");
    }

}
