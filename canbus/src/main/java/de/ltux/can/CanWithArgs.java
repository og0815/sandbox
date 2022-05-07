/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.can;

import com.beust.jcommander.Parameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import tel.schich.javacan.CanChannels;
import tel.schich.javacan.CanFrame;
import tel.schich.javacan.NetworkDevice;
import tel.schich.javacan.RawCanChannel;

/**
 *
 * @author oliver.guenther
 */
public class CanWithArgs {

    @Parameter(names = "--can", description = "The can controller, default to can0")
    private String canController = "can0";

    @Parameter(names = "--id", description = "The can id to use", required = true)
    private int id;

    @Parameter(names = "--action", description = "Action: allon, alloff, flood, floodalloff, listen, floodalloffandlisten", required = true)
    private String action;

    public void run() throws IOException, InterruptedException {
        Objects.requireNonNull(action, "action must not be null");
        if (action.equalsIgnoreCase("allon")) {
            allon();
        } else if (action.equalsIgnoreCase("alloff")) {
            alloff();
        } else if (action.equalsIgnoreCase("flood")) {
            flood();
        } else if (action.equalsIgnoreCase("floodalloff")) {
            floodAllOff();
        } else if (action.equalsIgnoreCase("floodalloffandlisten")) {
            floodAllOffAndListen();
        } else if (action.equalsIgnoreCase("listen")) {
            listen();
        }
    }

    private void allon() throws IOException, InterruptedException {
        RawCanChannel rc = CanChannels.newRawChannel(canController);

        for (int i = 1; i < 5; i++) {
            String command = "f:" + i + "100";
            CanFrame frame = CanFrame.create(id, CanFrame.FD_NO_FLAGS, command.getBytes());
            out(id, command.getBytes());
            rc.write(frame);
            Thread.sleep(250);
        }
    }

    private void alloff() throws IOException, InterruptedException {
        alloff(id);
    }

    private void alloff(int id) throws IOException, InterruptedException {
        RawCanChannel rc = CanChannels.newRawChannel(canController);
        for (int i = 1; i < 5; i++) {
            String command = "f:" + i + "000";
            CanFrame frame = CanFrame.create(id, CanFrame.FD_NO_FLAGS, command.getBytes());
            out(id, command.getBytes());
            rc.write(frame);
            Thread.sleep(250);
        }
    }

    // Simulation of the flood of messages, like our implementation. Keep raw open.
    private void flood() throws IOException, InterruptedException {
        while (true) {
            RawCanChannel rc = CanChannels.newRawChannel(canController);

            CanFrame frame = CanFrame.create(id, CanFrame.FD_NO_FLAGS, "a:".getBytes());
            out(id, "a:".getBytes());
            rc.write(frame);
            Thread.sleep(500);
            frame = CanFrame.create(id, CanFrame.FD_NO_FLAGS, "b:".getBytes());
            out(id, "b:".getBytes());
            rc.write(frame);
            Thread.sleep(500);
        }

    }

    // Simulation of the flood of messages, like our implementation.
    private void floodAllOff() throws IOException, InterruptedException {
        int ids = 0;
        while (true) {
            alloff(ids);
            if (ids > 10) {
                ids = 0;
            } else {
                ids++;
            }
        }

    }

    private void floodAllOffAndListen() throws IOException, InterruptedException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                listen();
            }

        }).start();

        int ids = 0;
        while (true) {
            alloff(ids);
            if (ids > 10) {
                ids = 0;
            } else {
                ids++;
            }
        }

    }

    private void listen() {
        try (RawCanChannel channel = CanChannels.newRawChannel()) {
            var can0 = NetworkDevice.lookup(canController);
            channel.bind(can0);
            while (true) {
                var result = channel.read();
                byte[] data = new byte[result.getDataLength()];
                result.getData(data, 0, result.getDataLength());
                System.out.println("Reading Id " + result.getId() + " : " + toString(data));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    public static List<Byte> asList(byte[] bytes) {
        Objects.requireNonNull(bytes, "bytes must not be null");
        List<Byte> result = new ArrayList<>();
        for (byte v : bytes) {
            result.add(v);
        }
        return result;
    }

    public static String toString(byte[] bytes) {
        return asList(bytes).stream().map(b -> String.format("%02X", b)).collect(Collectors.joining(" "));
    }

    public static void out(int id, byte[] data) {
        System.out.println("Writing Id:" + id + " Data:" + toString(data));
    }

}
