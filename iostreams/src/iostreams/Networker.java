/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iostreams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliver.guenther
 */
public class Networker {

    private final ExecutorService ES = Executors.newCachedThreadPool();

    public void listenAndSystemout(int port) throws IOException {
        ES.submit(() -> {
            ServerSocket seso = new ServerSocket(port);
            while (true) {
                System.out.println("Starting to Listen");
                Socket socket = seso.accept();
                ES.submit(() -> {
                    try (InputStream os = socket.getInputStream();) {
                        System.out.println("Ready to receive on: " + socket);
                        while (true) {
                            System.out.println(socket + " receiving: " + os.read());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(Networker.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }

        });
    }

    public void connectAndSend(String host, int port) {
        ES.submit(() -> {
            try (Socket s = new Socket(host, port);
                 OutputStream os = s.getOutputStream();) {
                System.out.println("Starting to send");
                for (int i = 0; i < 100; i++) {
                    os.write(i);
                    Thread.sleep(500);
                }
            } catch (IOException ex) {
                Logger.getLogger(Networker.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Networker.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

}
