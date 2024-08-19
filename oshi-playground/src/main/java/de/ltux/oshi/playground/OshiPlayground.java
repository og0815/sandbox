/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.ltux.oshi.playground;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;
import oshi.software.os.OSFileStore;

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

        System.out.println("OperatingSystem.Version: " + si.getOperatingSystem().toString());
        
        System.out.println("Memory:" + si.getHardware().getMemory());
        
        System.out.println("CpuTemp:" + si.getHardware().getSensors().getCpuTemperature());
        
        for (OSFileStore store : si.getOperatingSystem().getFileSystem().getFileStores()) {
            System.out.println("Store: " + store);
        }
        

        System.out.println("-----------");

        List<NetworkIF> networkIFs = si.getHardware().getNetworkIFs();
        if (System.getProperty("os.name").equals("Linux")) {
            networkIFs = DisconnectedLinuxNetworkIF.getNetworks(false);
        }

        for (NetworkIF n : networkIFs) {
            System.out.println(n);
            System.out.println("-----------");
        }

    }
}
