/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

/**
 *
 * @author oliver.guenther
 */
public class OmronFinsCollector {

    public static void main(String[] args) throws Exception {

        while (true) {
            OmronFinsResponse response = null;
            try (DatagramSocket clientSocket = new DatagramSocket()) {
                Thread.sleep(1000);
                OmronFinsReadCommand command = new OmronFinsReadCommand(1, 0, 700);
//            System.out.println("Sending: " + command);

                InetAddress IPAddress = InetAddress.getByName("87.138.123.131");
                DatagramPacket sendPacket = new DatagramPacket(command.toRaw(), command.toRaw().length, IPAddress, 9600);
                clientSocket.send(sendPacket);

                OmronFinsResponseReader reader = new OmronFinsResponseReader(command);
                DatagramPacket receivePacket = reader.prepareDatagramPacket();

                clientSocket.setSoTimeout(2000); // Timeout after 2 seconds
                clientSocket.receive(receivePacket);

                response = reader.convert(receivePacket);

                System.out.println("Nettelburg Ost und West");
                System.out.println("  IdOst: " + response.getLong(255));
                System.out.println("  FreiPlätzeOst: " + response.getBcd(1));
                System.out.println("  IdWest: " + response.getLong(280));
                System.out.println("  FreiPlätzeWest: " + response.getBcd(21));
                System.out.println();

            } catch (SocketTimeoutException t) {
                System.out.println("Timeout");
            } catch (NumberFormatException e) {
                System.out.println("Respose Error: " + response);
                e.printStackTrace();
            }
        }
    }

}
