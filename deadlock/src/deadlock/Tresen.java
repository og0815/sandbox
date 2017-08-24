/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock;

import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliver.guenther
 */
public class Tresen {

    private final ReentrantLock bier = new ReentrantLock();

    private final ReentrantLock korn = new ReentrantLock();

    private final ReentrantLock cola = new ReentrantLock();

    private final int speed;

    public Tresen(int speed) {
        this.speed = speed;
    }

    public void bestelleBier() {
        try {
            bier.lock();
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tresen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bestelleKorn() {
        try {
            korn.lock();
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tresen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bestelleCola() {
        try {
            cola.lock();
            Thread.sleep(speed);
        } catch (InterruptedException ex) {
            Logger.getLogger(Tresen.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void bezahle() {
        if (bier.isHeldByCurrentThread()) {
            bier.unlock();
        }
        if (cola.isHeldByCurrentThread()) {
            cola.unlock();
        }
        if (korn.isHeldByCurrentThread()) {
            korn.unlock();
        }
    }
}
