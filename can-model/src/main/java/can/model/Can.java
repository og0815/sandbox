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
// TODO: Later ad the building id to all messages
public class Can {

    public static class RequestLedChannelPowerUsage {

        public static class Answer {

            private final static String REGEX = "g:[1-4]{1}:[0-9]{4}";

            private final String emberId;

            private final String emberCanDevice;

            private final int haloControllerId;

            private final int channel;

            private final int power;

            public Answer(String emberId, String emberCanDevice, int haloControllerId, int channel, int power) {
                this.emberId = Objects.requireNonNull(emberId, "clientId must not be null");
                this.emberCanDevice = Objects.requireNonNull(emberCanDevice, "controller must not be null");
                if (haloControllerId < 1 || haloControllerId > 526) 
                    throw new IllegalArgumentException("haloControllerId must be from 0 to 526. Supplied Value " + haloControllerId);                
                this.haloControllerId = haloControllerId;
                if (channel < 1 || channel > 4) 
                    throw new IllegalArgumentException("channel must be 1, 2, 3 or 4. Supplied Value " + channel);               
                this.channel = channel;
                if (power < 0 || power > 9999) 
                    throw new IllegalArgumentException("channel must from 0 to 9999. Supplied Value " + power);                
                this.power = power;
            }

            public static boolean matchesMessage(String message) {
                return (Pattern.matches(REGEX, message));
            }

            public static Answer fromMessage(String clientId, String emberCanDevice, int haloControllerId, String message) {
                return new Answer(clientId, emberCanDevice, haloControllerId, Integer.parseInt(message.substring(2, 3)), Integer.parseInt(message.substring(4, 8)));
            }

            public String toMessage() {
                // TODO(OG): Formater, so das power immer 4 stellig
                return "g:" + channel + ":" + power;
            }

            public String emberId() {
                return emberId;
            }

            public String emberCanDevice() {
                return emberCanDevice;
            }

            public int channel() {
                return channel;
            }

            public int power() {
                return power;
            }

            public int haloControllerId() {
                return haloControllerId;
            }

            @Override
            public String toString() {
                return "RequestLedChannelPowerUsage.Answer{" + "emberId=" + emberId + ", emberCanDevice=" + emberCanDevice + ", haloControllerId=" + haloControllerId + ", channel=" + channel + ", power=" + power + '}';
            }
            

        }

        public static class Command {

            private final static String REGEX = "g:[1-4]{1}";

            private final String clientId;

            private final String controller;

            private final int channel;

            public Command(String clientId, String controller, int channel) {
                this.clientId = Objects.requireNonNull(clientId, "clientId must not be null");
                this.controller = Objects.requireNonNull(controller, "controller must not be null");
                this.channel = channel;
                if (channel < 1 || channel > 4) {
                    throw new IllegalArgumentException("channel must be 1, 2, 3 or 4. Supplied Value " + channel);
                }
            }

            public static boolean matchesMessage(String message) {
                return (Pattern.matches(REGEX, message));
            }

            public static Command fromMessage(String clientId, String controller, String message) {
                return new Command(clientId, controller, Integer.parseInt(message.substring(2)));
            }

            public String clientId() {
                return clientId;
            }

            public String controller() {
                return controller;
            }

            public int channel() {
                return channel;
            }

            public String toMessage() {
                return "g:" + channel;
            }

            @Override
            public String toString() {
                return "RequestLedChannelPowerUsage.Command{" + "clientId=" + clientId + ", controller=" + controller + ", channel=" + channel + '}';
            }

        }
    }

}
