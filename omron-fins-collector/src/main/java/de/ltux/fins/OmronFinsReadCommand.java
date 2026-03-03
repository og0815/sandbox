package de.ltux.fins;

/**
 * Representing a Command, that is correctly formated.
 *
 * @author oliver.guenther
 */
public class OmronFinsReadCommand {

    private final byte[] HEADER = {
        (byte) 0x80, // comand and response configuration byte
        (byte) 0x00, // reserved
        (byte) 0x02, // gateway count
        (byte) 0x00, // destination address
        (byte) 0x15, // destination node number (SYSMAC NET/LINK)
        (byte) 0x00, // destination unit address
        (byte) 0x00, // source network address
        (byte) 0x3d, // source node number (SYSMAC NET/LINK)
        (byte) 0x00, // source unit address PC (CPU)
        (byte) 0x03, // Service ID
        (byte) 0x01, 0x01 // command code (Memory Area Read)
    };

    private final byte memoryAreaCode = (byte) 0x82;
    
    private final int beginningAddress; // 0 - 65535

    private final int beginningAddressBits; // 0 - 255

    private final int numberOfItems; // 0 - 65535

    /**
     * New ReadCommand.
     *
     * @param beginningAddress the beginning memory address, allowed values
     * between 0 and 65535
     * @param beginningAddressBits the beginning address bits, allowd values
     * between 0 and 255
     * @param numberOfItems the size of the result, allowed values between 0 and
     * 65535
     */
    public OmronFinsReadCommand(int beginningAddress, int beginningAddressBits, int numberOfItems) {
        // TODO: Validate Input
        this.beginningAddress = beginningAddress;
        this.beginningAddressBits = beginningAddressBits;
        this.numberOfItems = numberOfItems;
    }
    
    public OmronFinsReadCommand(int beginningAddress, int beginningAddressBits, int numberOfItems, byte destinationNode) {
        // TODO: Validate Input
        this.beginningAddress = beginningAddress;
        this.beginningAddressBits = beginningAddressBits;
        this.numberOfItems = numberOfItems;
        HEADER[4] = destinationNode;
    }
    

    public int getNumberOfItems() {
        return numberOfItems;
    }

    /**
     * Returns the Command as a byte array, ready to be send via UDP to an SPS.
     *
     * @return the Command as a byte array, ready to be send via UDP to an SPS.
     */
    public byte[] toRaw() {
        byte[] command = toRawCommandData();
        byte[] raw = new byte[HEADER.length + command.length];
        System.arraycopy(HEADER, 0, raw, 0, HEADER.length);
        System.arraycopy(command, 0, raw, HEADER.length, command.length);
        return raw;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(Header=" + HexUtils.toString(" ", HEADER) + ", Command Data=" + HexUtils.toString(" ", toRawCommandData()) + ")";
    }

    private byte[] toRawCommandData() {
        return new byte[]{
            memoryAreaCode,
            (byte) ((beginningAddress & 0x0000FF00) >> 8),
            (byte) ((beginningAddress & 0x000000FF)),
            (byte) beginningAddressBits,
            (byte) ((numberOfItems & 0x0000FF00) >> 8),
            (byte) ((numberOfItems & 0x000000FF)),};
    }

    public static void main(String[] args) {
        OmronFinsReadCommand c = new OmronFinsReadCommand(1, 0, 100, (byte)0x29);
        System.out.println(c);
    }
    
}
