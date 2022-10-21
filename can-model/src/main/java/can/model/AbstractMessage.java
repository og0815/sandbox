/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package can.model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author oliver.guenther
 */
public abstract class AbstractMessage {

    private final static String EMBER_CAN_DEVICE_REGEX = "can[0-9]{1}";
    private final String emberId;
    private final String emberCanDevice;
    private final int haloControllerId;

    public AbstractMessage(String emberId, String emberCanDevice, int haloControllerId) throws NullPointerException, IllegalArgumentException {
        if (haloControllerId < 1 || haloControllerId > 526)
            throw new IllegalArgumentException("haloControllerId must be from 0 to 526. Supplied Value " + haloControllerId);
        this.emberId = Objects.requireNonNull(emberId, "clientId must not be null");
        if (!Pattern.matches(EMBER_CAN_DEVICE_REGEX, emberCanDevice))
            throw new IllegalArgumentException("emberCanDevice must match '" + EMBER_CAN_DEVICE_REGEX + "' but is " + emberCanDevice);
        this.emberCanDevice = Objects.requireNonNull(emberCanDevice, "controller must not be null");
        this.haloControllerId = haloControllerId;
    }

    public String emberId() {
        return emberId;
    }

    public String emberCanDevice() {
        return emberCanDevice;
    }

    public int haloControllerId() {
        return haloControllerId;
    }

    public abstract String toMessage();

    @Override
    public String toString() {
        return "emberId=" + emberId + ", emberCanDevice=" + emberCanDevice + ", haloControllerId=" + haloControllerId;
    }

}
