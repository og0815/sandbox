/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deadlock;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliver.guenther
 */
public class BestellungHans extends Bestellung {

    public BestellungHans(Tresen tresen, int wakeup) {
        super(tresen, wakeup);
    }

    public void run() {
        try {
            Thread.sleep(wakeup);
        } catch (InterruptedException ex) {
            Logger.getLogger(BestellungHans.class.getName()).log(Level.SEVERE, null, ex);
        }
        outBestelle("Bier");
        tresen.bestelleBier();
        outBestelle("Korn");
        tresen.bestelleKorn();
        outBestelle("Cola");
        tresen.bestelleCola();
        outBezahle();
        tresen.bezahle();
    }

}
