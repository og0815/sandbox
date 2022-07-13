/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package can;

import can.model.RequestLedChannelPowerUsage;
import java.util.Formatter;

/**
 *
 * @author oliver.guenther
 */
public class CanModel {

    public static void main(String[] args) {
        String m1 = "g:1";
        String m2 = "g:2:0022";

        RequestLedChannelPowerUsage c = new RequestLedChannelPowerUsage("client1", "can0",10, 1);
        System.out.println(c);
        System.out.println(c.toMessage());

        System.out.println(RequestLedChannelPowerUsage.Answer.validMessage(m1));
        System.out.println(RequestLedChannelPowerUsage.Answer.validationViolationsMessage(m1));
        System.out.println(RequestLedChannelPowerUsage.Answer.validMessage(m2));
        System.out.println(RequestLedChannelPowerUsage.Answer.validationViolationsMessage(m2));

        
        System.out.println(new RequestLedChannelPowerUsage.Answer("client2", "can2", 10, m2));

        can.model.RequestLedChannelPowerUsage.Answer b = new RequestLedChannelPowerUsage.Answer("client1", "can9", 10, 1, 13);

        System.out.println(b);
        System.out.println(b.toMessage());

        System.out.println(RequestLedChannelPowerUsage.validMessage(m1));
        System.out.println(RequestLedChannelPowerUsage.validMessage(m2));

        System.out.println(new RequestLedChannelPowerUsage("client2", "can2",10, m1));
       

    }
    
    /*
    
public class CanCommandTypeTest {

    private final List<String> SET_LED_CHANNEL_VALID_ANSWER_PAYLOADS = List.of("f:1100:o","f:1000:o","f:3100:o");

    private final List<String> SET_LED_CHANNEL_INVALID_ANSWER_PAYLOADS = List.of("f:1100","f:1000:e");
    
    private final List<String> GET_LED_POWER_VALID_ANSWER_PAYLOADS = List.of("g:1:0000","g:1:1020");

    private final List<String> GET_LED_POWER_INVALID_ANSWER_PAYLOADS = List.of("g:1");
    
    private final CanEmberController CONTROLLER = 
            new CanEmberController.Builder().setClientMode(EmberMode.ACTIVE).setPosition(1L).setSystemDevice("can0").build();
    
    @Test
    public void setLedChannelValid() {
        for (String payload : SET_LED_CHANNEL_VALID_ANSWER_PAYLOADS) {
            assertThat(CanAnswer.canAnswer(0, payload, CONTROLLER).validationViolations()).isNull();
        }
    }

    @Test
    public void setLedChannelInValid() {
        for (String payload : SET_LED_CHANNEL_INVALID_ANSWER_PAYLOADS) {
            assertThat(CanAnswer.canAnswer(0, payload, CONTROLLER).validationViolations()).isNotNull();
        }
    }


    @Test
    public void getLedPowerValid() {
        for (String payload : GET_LED_POWER_VALID_ANSWER_PAYLOADS) {
            assertThat(CanAnswer.canAnswer(0, payload, CONTROLLER).validationViolations()).isNull();
        }
    }

    @Test
    public void getLedPowerInValid() {
        for (String payload : GET_LED_POWER_INVALID_ANSWER_PAYLOADS) {
            assertThat(CanAnswer.canAnswer(0, payload, CONTROLLER).validationViolations()).isNotNull();
        }
    }

}
    */
}
