package can.model;

import java.util.function.IntSupplier;
import java.util.regex.Pattern;

/**
 *
 * @author oliver.guenther
 */
public class RequestLedChannelPowerUsage extends AbstractMessage {

    public static class Answer extends AbstractMessage {

        private static final String REGEX = "g:[1-4]{1}:[0-9]{4}";
        private final int channel;
        private final int power;

        public Answer(String emberId, String emberCanDevice, int haloControllerId, String message) throws NullPointerException, IllegalArgumentException {
            this(emberId, emberCanDevice, haloControllerId, new IntSupplier() {
                @Override
                public int getAsInt() {
                    if (!validMessage(message)) throw new IllegalArgumentException(validationViolationsMessage(message));
                    return parseChannel(message);
                }
            }.getAsInt(), new IntSupplier() {
                @Override
                public int getAsInt() {
                    if (!validMessage(message)) throw new IllegalArgumentException(validationViolationsMessage(message));
                    return parsePower(message);
                }
            }.getAsInt());
        }

        public Answer(String emberId, String emberCanDevice, int haloControllerId, int channel, int power) throws NullPointerException, IllegalArgumentException {
            super(emberId, emberCanDevice, haloControllerId);
            if (!validPower(power)) throw new IllegalArgumentException(validationViolationsPower(power));
            if (!validChannel(channel)) throw new IllegalArgumentException(validationViolationsChannel(channel));
            this.power = power;
            this.channel = channel;
        }

        public static boolean validMessage(String message) {
            return validationViolationsMessage(message) == null;
        }

        public static String validationViolationsMessage(String message) {
            if (message == null) return "Message must not be null";
            if (!Pattern.matches(REGEX, message)) return "Message must match Pattern '" + REGEX + "' which '" + message + "' does not";
            int power = parsePower(message);
            if (!validPower(power)) return validationViolationsPower(power);
            int channel = parseChannel(message);
            if (!validChannel(channel)) return validationViolationsChannel(channel);
            return null;
        }

        private static int parsePower(String message) {
            return Integer.parseInt(message.substring(4, 8));
        }

        private static String validationViolationsPower(int power) {
            if (power < 0 || power > 9999) return "power must be from 0 to 9999. Supplied Value " + power;
            return null;
        }

        private static boolean validPower(int power) {
            return validationViolationsPower(power) == null;
        }

        @Override
        public String toMessage() {
            return "g:" + channel + ":" + String.format("%04d", power);
        }

        public int channel() {
            return channel;
        }

        public int power() {
            return power;
        }

        @Override
        public String toString() {
            return "RequestLedChannelPowerUsage.Answer{" + super.toString() + ", channel=" + channel + ", power=" + power + '}';
        }

    }

    private static final String REGEX = "g:[1-4]{1}";

    private final int channel;

    public RequestLedChannelPowerUsage(String emberId, String emberCanDevice, int haloControllerId, String message) throws NullPointerException, IllegalArgumentException {
        this(emberId, emberCanDevice, haloControllerId, new IntSupplier() {
            @Override
            public int getAsInt() {
                if (!validMessage(message)) throw new IllegalArgumentException(validationViolationsMessage(message));
                return parseChannel(message);
            }
        }.getAsInt());
    }

    public RequestLedChannelPowerUsage(String emberId, String emberCanDevice, int haloControllerId, int channel) throws NullPointerException, IllegalArgumentException {
        super(emberId, emberCanDevice, haloControllerId);
        if (!validChannel(channel)) throw new IllegalArgumentException(validationViolationsChannel(channel));
        this.channel = channel;
    }

    public static boolean validMessage(String message) {
        return validationViolationsMessage(message) == null;
    }

    public static String validationViolationsMessage(String message) {
        if (message == null) return "Message must not be null";
        if (!Pattern.matches(REGEX, message)) return "Message must match Pattern '" + REGEX + "' which '" + message + "' does not";
        int channel = parseChannel(message);
        if (!validChannel(channel)) return validationViolationsChannel(channel);
        return null;
    }

    private static int parseChannel(String message) {
        return Integer.parseInt(message.substring(2, 3));
    }

    private static String validationViolationsChannel(int channel) {
        if (channel < 1 || channel > 4) return "channel must be 1, 2, 3 or 4. Supplied Value " + channel;
        return null;
    }

    private static boolean validChannel(int channel) {
        return validationViolationsChannel(channel) == null;
    }

    public int channel() {
        return channel;
    }

    @Override
    public String toMessage() {
        return "g:" + channel;
    }

    @Override
    public String toString() {
        return "RequestLedChannelPowerUsage{" + super.toString() + "channel=" + channel + '}';
    }

}
