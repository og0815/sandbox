/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.ltux.oshi.playground;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

/**
 *
 * @author oliver.guenther
 */
public class OshiPlayground {

    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        Duration uptime = Duration.ofSeconds(si.getOperatingSystem().getSystemUptime());
        System.out.println("Uptime: " + uptime.toDaysPart() + "|" + uptime.toHoursPart() + ":" + uptime.toMinutesPart());
        System.out.println("Hardware: " + si.getHardware().getComputerSystem());
        System.out.println("OperatingSystem.Family: " + si.getOperatingSystem().getFamily());
        System.out.println("System.getProperty(os.name): " + System.getProperty("os.name"));
        System.out.println("-----------");
        
        List<NetworkIF> networkIFs = si.getHardware().getNetworkIFs();
        if (System.getProperty("os.name").equals("Linux")) {
            networkIFs = DisconnectedLinuxNetworkIF.getNetworks(false);            
        }

        for (NetworkIF n : networkIFs) {
            System.out.println(n);
            System.out.println("+++++++++");

            System.out.println("DisplayName: " + n.getDisplayName());
            System.out.println("Name: " + n.getName());
            System.out.println("IfAlias: " + n.getIfAlias());
            System.out.println("Mac: " + n.getMacaddr());
            System.out.println("IPv4s: " + Arrays.toString(n.getIPv4addr()));
            System.out.println("IPv6s: " + Arrays.toString(n.getIPv6addr()));
            System.out.println("PacketsSent: " + n.getPacketsSent());
            System.out.println("PacketsReceivd: " + n.getPacketsRecv());
            System.out.println("Collisions: " + n.getCollisions());
            System.out.println("Drops: " + n.getInDrops());
            System.out.println("InErrors: " + n.getInErrors());
            System.out.println("OutErrors: " + n.getOutErrors());
            System.out.println("OperStatus: " + n.getIfOperStatus());
            System.out.println("Connector: " + n.isConnectorPresent());
            System.out.println("-----------");
        }

    }
}
