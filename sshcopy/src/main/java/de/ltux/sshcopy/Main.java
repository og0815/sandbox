/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.sshcopy;

import java.io.IOException;
import net.schmizz.sshj.SSHClient;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {
        SSHClient ssh = new SSHClient();
        ssh.addHostKeyVerifier("");
        ssh.connect("");
        try {
            ssh.authPassword("", "");

            ssh.startSession().exec("/usr/bin/rm -f *.txt");

            ssh.newSCPFileTransfer().upload("/home/oliver.guenther/Saft.txt", ".");
        } finally {
            ssh.disconnect();
        }
    }
}
