/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock;

/**
 *
 * @author oliver.guenther
 */
public abstract class Bestellung implements Runnable {

    private static int counter = 0;

    protected final Tresen tresen;

    private final int number;

    protected final int wakeup;

    public Bestellung(Tresen tresen, int wakeup) {
        this.tresen = tresen;
        number = counter++;
        this.wakeup = wakeup;
    }

    protected void outBestelle(String drink) {
        System.out.println(this.getClass().getSimpleName() + "[" + number + "] bestellt " + drink);
    }

    protected void outBezahle() {
        System.out.println(this.getClass().getSimpleName() + "[" + number + "] bezahlt");
    }
}
