package can.model;

/**
 *
 * @author oliver.guenther
 */
public class SetLetChannelBrightness {

    private static final String REGEX = "g:[1-4]{1}:[0-9]{4}";
    private final String emberId;
    private final String emberCanDevice;
    private final int haloControllerId;
    private final int channel;
    private final int brightness;

    public SetLetChannelBrightness(String emberId, String emberCanDevice, int haloControllerId, String message) {
        this.emberId = emberId;
        this.emberCanDevice = emberCanDevice;
        this.haloControllerId = haloControllerId;
        this.channel = 1;
        this.brightness = 1;
    }

    public SetLetChannelBrightness(String emberId, String emberCanDevice, int haloControllerId, int channel, int brightness) {
        this.emberId = emberId;
        this.emberCanDevice = emberCanDevice;
        this.haloControllerId = haloControllerId;
        this.channel = channel;
        this.brightness = brightness;
    }

}
