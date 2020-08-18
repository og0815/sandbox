/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.can;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import tel.schich.javacan.CanChannels;
import tel.schich.javacan.CanFrame;
import tel.schich.javacan.NetworkDevice;
import tel.schich.javacan.RawCanChannel;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    public static void main(String[] args) {
        if (args == null || args.length == 0) {
            System.err.println("No Parameters");
            System.err.println("Options: --on --off --listen");
            System.exit(1);
        } else if (args[0].equals("--on")) {
            on();
        } else if (args[0].equals("--off")) {
            off();
        } else if (args[0].equals("--listen")) {
            listen();
        } else {
            System.err.println("Parameters not ok: " + Arrays.toString(args));
            System.err.println("Options: --on --off --listen");
        }
    }

    private static void on() {
        send(0x526, "f:1001".getBytes());
    }

    private static void off() {
        send(0x526, "f:1000".getBytes());
    }

    private static void listen() {
        try (RawCanChannel socket = CanChannels.newRawChannel()) {
            var can0 = NetworkDevice.lookup("can0");
            socket.bind(can0);
            while (true) {
                var result = socket.read();
                System.out.println("Id      : " + result.getId());
                byte[] data = new byte[result.getDataLength()];
                result.getData(data, 0, result.getDataLength());
                System.out.println("Frame : " + toString(data));
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }

    private static String toString(byte[] bytes) {
        return new BigInteger(1, bytes).toString(16);
    }

    private static void send(int id, byte[] payload) {
        try (RawCanChannel socket = CanChannels.newRawChannel()) {
            var can0 = NetworkDevice.lookup("can0");
            socket.bind(can0);

            var frame = CanFrame.create(id, CanFrame.FD_NO_FLAGS, payload);
            socket.write(frame);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

}
