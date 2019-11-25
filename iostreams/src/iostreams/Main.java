/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iostreams;

import java.io.IOException;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
       // Filer.openFile("test.txt");
       Networker n = new Networker();
       n.listenAndSystemout(4444);
       n.connectAndSend("localhost", 4444);
       n.connectAndSend("localhost", 4444);
       n.connectAndSend("localhost", 4444);
    }
    
}
