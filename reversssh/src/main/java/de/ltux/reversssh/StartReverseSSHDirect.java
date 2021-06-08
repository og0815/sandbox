/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.reversssh;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author oliver.guenther
 */
public class StartReverseSSHDirect {

    public static void main(String[] args) throws IOException, InterruptedException {
        // DO NOT CONMIT User and pass.
        RequestReverseSsh request = new RequestReverseSsh("host", 8888, "user", "pass");

        // Todo: Optimize at least with environment pass.
        List<String> command = Arrays.asList(
                "/usr/bin/sshpass", "-e",
                "ssh", "-N", "-T",
                "-R", "" + request.getPort() + ":localhost:22",
                "-o", "StrictHostKeyChecking=no",
                "-o", "VerifyHostKeyDNS=no",
                "-o", "CheckHostIP=no",
                "-v",
                request.getUsername() + "@" + request.getHost()
        );

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.environment().put("SSHPASS", request.getPassword());
        pb.redirectErrorStream(true);
        var process = pb.start();
        var is = process.getInputStream();
        // Thread for all messages.
        Thread t1 = new Thread(() -> {
            boolean canceled = false;
            while (!canceled && !Thread.interrupted()) {
                try {
                    Thread.sleep(1000);
                    try {
                        if (is.available() > 0) {
                            var bytes = is.readNBytes(is.available()); // is.readAllBytes() does not work here.
                            String out = new String(bytes, StandardCharsets.UTF_8);
                            System.out.println("Out: -------");
                            System.out.println(out); // TODO: Later Return as message.
                            // TODO: Also, check if some question is asked, and allways answer with yes.
                            System.out.println("------------");
                        }
                    } catch (IOException ex) {
                        System.err.println("Exception on Read Processoutput: " + ex.getMessage());
                        canceled = true;
                    }
                } catch (InterruptedException ex) {
                    canceled = true;
                }
            }
            System.out.println("End Log thread");
        });
        t1.setDaemon(true);
        t1.start();

        // Thread for exit status.
        Thread t2 = new Thread(() -> {
            try {
                var result = process.waitFor();
                System.out.println("Exit via Code: " + result);
            } catch (InterruptedException ex) {
                System.out.println("Exit via Interrupt: " + ex.getMessage());
            }
        });
        t2.start();

        Thread.sleep(10000);
        if (process.isAlive()) {
            System.out.println("Destroying process");
            process.destroy();
        }

    }
}
