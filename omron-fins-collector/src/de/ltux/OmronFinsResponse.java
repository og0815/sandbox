package de.ltux;

/**
 *
 * @author oliver.guenther
 */
public class OmronFinsResponse {

    // Size: header 12 byte, response code: 2 byte, date size from command
    private final byte[] header = new byte[12];

    private final int responseCode;

    private final byte[] payload;

    public OmronFinsResponse(byte[] raw) {
        System.arraycopy(raw, 0, header, 0, 12);
        responseCode = Integer.parseUnsignedInt(String.format("%02X%02X", raw[12], raw[13]), 16);
        payload = new byte[raw.length - 14];
        System.arraycopy(raw, 14, payload, 0, payload.length);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(header=" + HexUtils.toString(" ", header) + ", responseCode=" + responseCode + ", payload=" + HexUtils.toString(" ", payload) + ")";
    }

    public String toPayloadLines(int b) {
        StringBuilder sb = new StringBuilder();
        int j = 0;
        for (int i = 0; i < payload.length; i = i + b) {
            j++;
            byte show[] = new byte[b];
            System.arraycopy(payload, i, show, 0, b);
            sb.append(j).append(" : ").append(HexUtils.toString(" ", show)).append("\n");
        }
        return sb.toString();
    }

    public long getLong(int address) {
        // Validate ganz viel
        final int index = (address * 2) - 1;
        String unsignedHex = String.format("%02X%02X%02X%02X", payload[index + 1], payload[index + 2], payload[index - 1], payload[index]);
        return Long.parseUnsignedLong(unsignedHex, 16);
    }

    public int getBcd(int address) {
        // TODO: validate the address (0 - length)
        final int index = (address * 2) - 1;
        // TODO: Validate, that the payload
        return Integer.parseInt(String.format("%02X", payload[index - 1]) + String.format("%02X", payload[index]));
    }

}
