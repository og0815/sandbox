/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux;

import java.net.DatagramPacket;
import java.util.Objects;

/**
 * Reader class for command.
 *
 * @author oliver.guenther
 */
public class OmronFinsResponseReader {

    // Size: header 12 byte, response code: 2 byte, date size from command
    private final OmronFinsReadCommand command;

    /**
     * New ResponseReader.
     *
     * @param command the command, must not be null.
     * @throws NullPointerException if command is null.
     */
    public OmronFinsResponseReader(OmronFinsReadCommand command) throws NullPointerException {
        this.command = Objects.requireNonNull(command);
    }

    /**
     * Preapres a DatagramPacket that has the exact size to collect the response
     * of the supplied command.
     *
     * @return a DatagramPacket
     */
    public DatagramPacket prepareDatagramPacket() {
        int size = 12 + 2 + command.getNumberOfItems();
        return new DatagramPacket(new byte[size], size);
    }

    /**
     * Connverts a filled DatagramPacket to the Respose, validating its
     * contents.
     *
     * @param receivedDatagramPacket the received data.
     * @return the converted response.
     */
    public OmronFinsResponse convert(DatagramPacket receivedDatagramPacket) {
        return new OmronFinsResponse(receivedDatagramPacket.getData());
    }

}
