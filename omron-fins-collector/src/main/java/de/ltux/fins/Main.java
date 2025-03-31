package de.ltux.fins;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    private static final String IP_REGEX
            = "^((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])\\.){3}"
            + "(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])$";

    private static final String INT_REGEX = "-?\\d+";

    private static final String USAGE = "Need at least --ip=xxx.yyy.vvv.zzz --addess=aaaa --type=[bcd|long]. Optional --port=xxxx --raw=[true|false]";

    public static void main(String[] args) throws SocketException, UnknownHostException, IOException {
        if (args == null || args.length < 3) {
            throw new IllegalArgumentException(USAGE);
        }
        String ip = Arrays
                .asList(args)
                .stream()
                .filter(p -> p.startsWith("--ip="))
                .map(p -> p.substring(5, p.length()))
                .filter(p -> Pattern.compile(IP_REGEX).matcher(p).matches())
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(Arrays.toString(args) + " missing IP." + USAGE));

        String type = Arrays
                .asList(args)
                .stream()
                .filter(p -> p.startsWith("--type="))
                .map(p -> p.substring(7, p.length()))
                .filter(p -> p.equalsIgnoreCase("bcd") || p.equalsIgnoreCase("long"))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(Arrays.toString(args) + " missing type." + USAGE));

        int address = Arrays
                .asList(args)
                .stream()
                .filter(p -> p.startsWith("--address="))
                .map(p -> p.substring(10, p.length()))
                .filter(p -> Pattern.compile(INT_REGEX).matcher(p).matches())
                .mapToInt(p -> Integer.valueOf(p))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(Arrays.toString(args) + " missing address." + USAGE));

        int port = Arrays
                .asList(args)
                .stream()
                .filter(p -> p.startsWith("--port="))
                .map(p -> p.substring(7, p.length()))
                .filter(p -> Pattern.compile(INT_REGEX).matcher(p).matches())
                .mapToInt(p -> Integer.valueOf(p))
                .findAny()
                .orElse(9600);
        
        boolean raw = Arrays
                .asList(args)
                .stream()
                .filter(p -> p.startsWith("--raw="))
                .map(p -> p.substring(6, p.length()))
                .filter(p -> p.equalsIgnoreCase("true") || p.equalsIgnoreCase("false"))
                .map(p -> Boolean.valueOf(p))
                .findAny()
                .orElse(false);
        
        
        

        System.out.println("Collecting with Parameters:");
        System.out.println(" Ip:" + ip);
        System.out.println(" Port:" + port);
        System.out.println(" Address:" + address);
        System.out.println(" Type:" + type);
        System.out.println(" Raw:" + raw);
        System.out.println("");
        
        int length = 700;
        
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            OmronFinsReadCommand command = new OmronFinsReadCommand(1, 0, length,(byte)0x29);

            DatagramPacket sendPacket = new DatagramPacket(command.toRaw(), command.toRaw().length, InetAddress.getByName(ip), port);
            clientSocket.send(sendPacket);

            OmronFinsResponseReader reader = new OmronFinsResponseReader(command);
            DatagramPacket receivePacket = reader.prepareDatagramPacket();

            clientSocket.setSoTimeout(2000); // Timeout after 2 seconds
            clientSocket.receive(receivePacket);

            OmronFinsResponse response = reader.convert(receivePacket);
            
            if (raw) {                
                System.out.println("Raw:" + response);
                System.out.println("");
            }
            
            if (type.equalsIgnoreCase("long")) {
                System.out.println("Result:" + response.getLong(address));
            } else if (type.equalsIgnoreCase("bcd")) {
                System.out.println("Result:" + response.getBcd(address));                
            } else {
                throw new IllegalArgumentException("Should be impossible to reach, wrong type = " + type);
            }
        }

    }

}
