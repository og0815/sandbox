/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socketlogger;

import java.util.logging.Logger;

/**
 *
 * @author olive
 */
public class SocketLogger {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger L = Logger.getLogger(SocketLogger.class.getName());
        L.fine("Fine");
        L.info("Info");
        L.warning("Warn");
        L.severe("Severe");
        System.out.println("Ende");
        // TODO code application logic here
    }

}
