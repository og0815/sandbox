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
public class BestellungMax extends Bestellung {

    public BestellungMax(Tresen tresen, int wakeup) {
        super(tresen, wakeup);
    }

    @Override
    public void run() {
        try {
            Thread.sleep(wakeup);
        } catch (InterruptedException ex) {
            Logger.getLogger(BestellungHans.class.getName()).log(Level.SEVERE, null, ex);
        }

        outBestelle("Bier");
        tresen.bestelleBier();
        outBestelle("Cola");
        tresen.bestelleCola();
        outBestelle("Korn");
        tresen.bestelleKorn();
        outBezahle();
        tresen.bezahle();
    }

}
