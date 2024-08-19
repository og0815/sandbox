package de.ltux.oshi.playground;

import com.sun.jna.platform.linux.Udev;
import java.io.File;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import oshi.annotation.concurrent.ThreadSafe;
import oshi.hardware.NetworkIF;
import oshi.hardware.common.AbstractNetworkIF;
import oshi.hardware.platform.linux.LinuxNetworkIF;
import static oshi.software.os.linux.LinuxOperatingSystem.HAS_UDEV;
import oshi.util.FileUtil;
import oshi.util.FormatUtil;
import oshi.util.Util;
import oshi.util.platform.linux.SysPath;

/**
 * LinuxNetworks class.
 */
@ThreadSafe
public final class DisconnectedLinuxNetworkIF implements NetworkIF {

    private static final int LOCAL_TYPE = 772; // See: https://elixir.bootlin.com/linux/v6.10.5/source/include/uapi/linux/if_arp.h#L30

    private static final Logger LOG = LoggerFactory.getLogger(DisconnectedLinuxNetworkIF.class);

    private final String name;
    private final String displayName;

    private int ifType;
    private boolean connectorPresent;
    private long bytesRecv;
    private long bytesSent;
    private long packetsRecv;
    private long packetsSent;
    private long inErrors;
    private long outErrors;
    private long inDrops;
    private long collisions;
    private long speed;
    private long timeStamp;
    private long mtu;
    private String macaddr;

    private String ifAlias = "";
    private NetworkIF.IfOperStatus ifOperStatus = NetworkIF.IfOperStatus.UNKNOWN;

    /**
     *
     *
     * @param name The name of the interface e.g. eth0. Must exist under
     * /sys/class/net/"name".
     * @throws InstantiationException
     */
    public DisconnectedLinuxNetworkIF(String name) throws InstantiationException {
        this.name = Objects.requireNonNull(name);
        File iface = new File(SysPath.NET + name);
        if (!iface.exists()) {
            throw new IllegalArgumentException(iface + "does not exits");
        }
        if (!iface.isDirectory()) {
            throw new IllegalArgumentException(iface + "is not a directory");
        }
        displayName = queryIfModel(name);
        updateAttributes();
    }

    private static String queryIfModel(String name) {
        if (!HAS_UDEV) {
            return queryIfModelFromSysfs(name);
        }
        Udev.UdevContext udev = Udev.INSTANCE.udev_new();
        if (udev != null) {
            try {
                Udev.UdevDevice device = udev.deviceNewFromSyspath(SysPath.NET + name);
                if (device != null) {
                    try {
                        String devVendor = device.getPropertyValue("ID_VENDOR_FROM_DATABASE");
                        String devModel = device.getPropertyValue("ID_MODEL_FROM_DATABASE");
                        if (!Util.isBlank(devModel)) {
                            if (!Util.isBlank(devVendor)) {
                                return devVendor + " " + devModel;
                            }
                            return devModel;
                        }
                    } finally {
                        device.unref();
                    }
                }
            } finally {
                udev.unref();
            }
        }
        return name;
    }

    private static String queryIfModelFromSysfs(String name) {
        Map<String, String> uevent = FileUtil.getKeyValueMapFromFile(SysPath.NET + name + "/uevent", "=");
        String devVendor = uevent.get("ID_VENDOR_FROM_DATABASE");
        String devModel = uevent.get("ID_MODEL_FROM_DATABASE");
        if (!Util.isBlank(devModel)) {
            if (!Util.isBlank(devVendor)) {
                return devVendor + " " + devModel;
            }
            return devModel;
        }
        return name;
    }

    /**
     * Gets network interfaces on this machine
     *
     * @param includeLocalInterfaces include local interfaces in the result
     * @return A list of {@link NetworkIF} objects representing the interfaces
     */
    public static List<NetworkIF> getNetworks(boolean includeLocalInterfaces) {
        List<NetworkIF> ifList = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (NetworkInterface ni : getNetworkInterfaces(includeLocalInterfaces)) {
            try {
                ifList.add(new LinuxNetworkIF(ni));
                names.add(ni.getName());
            } catch (InstantiationException e) {
                LOG.debug("Network Interface Instantiation failed: {}", e.getMessage());
            }
        }

        // Create all disconnected interfaces from the filesystem.
        File netPath = new File(SysPath.NET);
        for (File f : netPath.listFiles()) {
            System.out.println("debug1;" + f);
            if (!f.getName().startsWith(".") && !names.contains(f.getName())) {
                try {
                    System.out.println("debug2;" + f);
                    DisconnectedLinuxNetworkIF i = new DisconnectedLinuxNetworkIF(f.getName());
                    System.out.println("debug3;" + i);
                    if (i.getIfType() != LOCAL_TYPE) {
                        ifList.add(i);
                    }
                } catch (InstantiationException ex) {
                    LOG.debug("Network Interface Instantiation failed: {}", ex.getMessage());
                }
            }
        }

        return ifList;
    }

    /**
     * Returns network interfaces on this machine.
     *
     * @param includeLocalInterfaces include local interfaces in the result
     * @return A list of network interfaces
     */
    protected static List<NetworkInterface> getNetworkInterfaces(boolean includeLocalInterfaces) {
        List<NetworkInterface> interfaces = getAllNetworkInterfaces();

        return includeLocalInterfaces ? interfaces
                : getAllNetworkInterfaces().stream().parallel().filter(ni -> !isLocalInterface(ni))
                        .collect(Collectors.toList());
    }

    /**
     * Returns all network interfaces.
     *
     * @return A list of network interfaces
     */
    private static List<NetworkInterface> getAllNetworkInterfaces() {
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            return interfaces == null ? Collections.emptyList() : Collections.list(interfaces);
        } catch (SocketException ex) {
            LOG.error("Socket exception when retrieving interfaces: {}", ex.getMessage());
        }
        return Collections.emptyList();
    }

    private static boolean isLocalInterface(NetworkInterface networkInterface) {
        try {
            // getHardwareAddress also checks for loopback
            return networkInterface.getHardwareAddress() == null;
        } catch (SocketException e) {
            LOG.error("Socket exception when retrieving interface information for {}: {}", networkInterface,
                    e.getMessage());
        }
        return false;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public NetworkInterface queryNetworkInterface() {
        // Java & Linux only have NetworkInterfaces for active connections.
        return null;
    }

    @Override
    public int getIndex() {
        return -1;
    }

    @Override
    public String[] getIPv4addr() {
        return new String[]{};
    }

    @Override
    public Short[] getSubnetMasks() {
        return new Short[]{};
    }

    @Override
    public String[] getIPv6addr() {
        return new String[]{};
    }

    @Override
    public Short[] getPrefixLengths() {
        return new Short[]{};
    }

    @Override
    public boolean isKnownVmMacAddr() {
        return false;
    }

    @Override
    public int getIfType() {
        return this.ifType;
    }

    @Override
    public boolean isConnectorPresent() {
        return this.connectorPresent;
    }

    @Override
    public long getBytesRecv() {
        return this.bytesRecv;
    }

    @Override
    public long getBytesSent() {
        return this.bytesSent;
    }

    @Override
    public long getPacketsRecv() {
        return this.packetsRecv;
    }

    @Override
    public long getPacketsSent() {
        return this.packetsSent;
    }

    @Override
    public long getInErrors() {
        return this.inErrors;
    }

    @Override
    public long getOutErrors() {
        return this.outErrors;
    }

    @Override
    public long getInDrops() {
        return this.inDrops;
    }

    @Override
    public long getCollisions() {
        return this.collisions;
    }

    @Override
    public long getSpeed() {
        return this.speed;
    }

    @Override
    public long getTimeStamp() {
        return this.timeStamp;
    }

    @Override
    public String getIfAlias() {
        return ifAlias;
    }

    @Override
    public NetworkIF.IfOperStatus getIfOperStatus() {
        return ifOperStatus;
    }

    @Override
    public boolean updateAttributes() {
        String sysDeviceName = SysPath.NET + getName();
        try {
            File ifDir = new File(sysDeviceName + "/statistics");
            if (!ifDir.isDirectory()) {
                return false;
            }
        } catch (SecurityException e) {
            return false;
        }

        this.timeStamp = System.currentTimeMillis();
        this.ifType = FileUtil.getIntFromFile(sysDeviceName + "/type");
        this.connectorPresent = FileUtil.getIntFromFile(sysDeviceName + "/carrier") > 0;
        this.bytesSent = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/tx_bytes");
        this.bytesRecv = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/rx_bytes");
        this.packetsSent = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/tx_packets");
        this.packetsRecv = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/rx_packets");
        this.outErrors = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/tx_errors");
        this.inErrors = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/rx_errors");
        this.collisions = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/collisions");
        this.inDrops = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/statistics/rx_dropped");
        long speedMiB = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/speed");
        // speed may be -1 from file.
        this.speed = speedMiB < 0 ? 0 : speedMiB << 20;
        this.ifAlias = FileUtil.getStringFromFile(sysDeviceName + "/ifalias");
        this.ifOperStatus = parseIfOperStatus(FileUtil.getStringFromFile(sysDeviceName + "/operstate"));
        this.mtu = FileUtil.getUnsignedLongFromFile(sysDeviceName + "/mtu");
        this.macaddr = FileUtil.getStringFromFile(sysDeviceName + "/address");

        return true;
    }

    private static NetworkIF.IfOperStatus parseIfOperStatus(String operState) {
        switch (operState) {
            case "up":
                return NetworkIF.IfOperStatus.UP;
            case "down":
                return NetworkIF.IfOperStatus.DOWN;
            case "testing":
                return NetworkIF.IfOperStatus.TESTING;
            case "dormant":
                return NetworkIF.IfOperStatus.DORMANT;
            case "notpresent":
                return NetworkIF.IfOperStatus.NOT_PRESENT;
            case "lowerlayerdown":
                return NetworkIF.IfOperStatus.LOWER_LAYER_DOWN;
            case "unknown":
            default:
                return NetworkIF.IfOperStatus.UNKNOWN;
        }
    }

    @Override
    public long getMTU() {
        return this.mtu;
    }

    @Override
    public String getMacaddr() {
        return this.macaddr;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(getName());
        if (!getName().equals(getDisplayName())) {
            sb.append(" (").append(getDisplayName()).append(")");
        }
        if (!getIfAlias().isEmpty()) {
            sb.append(" [IfAlias=").append(getIfAlias()).append("]");
        }
        sb.append("\n");
        sb.append("  MAC Address: ").append(getMacaddr()).append("\n");
        sb.append("  MTU: ").append(getMTU()).append(", ").append("Speed: ").append(getSpeed()).append("\n");
        sb.append("  IPv4: []\n");
        sb.append("  IPv6: []\n");
        sb.append("  Traffic: received ").append(getPacketsRecv()).append(" packets/")
                .append(FormatUtil.formatBytes(getBytesRecv())).append(" (" + getInErrors() + " err, ")
                .append(getInDrops() + " drop);");
        sb.append(" transmitted ").append(getPacketsSent()).append(" packets/")
                .append(FormatUtil.formatBytes(getBytesSent())).append(" (" + getOutErrors() + " err, ")
                .append(getCollisions() + " coll);");
        return sb.toString();
    }

}
