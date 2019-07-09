/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iostreams;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oliver.guenther
 */
public class Filer {

    public static void openFile(String filename) {
        File f = new File(filename);
        System.out.println(f.getAbsolutePath());
        try (FileInputStream fis = new FileInputStream(f);
             BufferedInputStream bis = new BufferedInputStream(fis);
             InputStreamReader isr = new InputStreamReader(bis);) {

            int result = 0;

            StringBuilder sb = new StringBuilder();

            do {
                char[] c = new char[1000];
                result = isr.read(c);
                sb.append(c);
            } while (result != -1);

            System.out.println("-------------------------------------");
            System.out.println(sb.toString());
            System.out.println("-------------------------------------");
        } catch (IOException ex) {
            Logger.getLogger(Filer.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
