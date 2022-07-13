/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package can.model;

/**
 *
 * @author oliver.guenther
 */
public class CanModel {

    public static void main(String[] args) {
        String m1 = "g:1";
        String m2 = "g:2:0022";

        Can.RequestLedChannelPowerUsage.Command c = new Can.RequestLedChannelPowerUsage.Command("client1", "can0", 1);
        System.out.println(c);
        System.out.println(c.toMessage());

        System.out.println(Can.RequestLedChannelPowerUsage.Answer.matchesMessage(m1));
        System.out.println(Can.RequestLedChannelPowerUsage.Answer.matchesMessage(m2));

        System.out.println(Can.RequestLedChannelPowerUsage.Answer.fromMessage("client2", "can2", 10, m2));

        var b = new Can.RequestLedChannelPowerUsage.Answer("client1", "can9", 10, 1, 13);

        System.out.println(b);
        System.out.println(b.toMessage());

        System.out.println(Can.RequestLedChannelPowerUsage.Command.matchesMessage(m1));
        System.out.println(Can.RequestLedChannelPowerUsage.Command.matchesMessage(m2));

        System.out.println(Can.RequestLedChannelPowerUsage.Command.fromMessage("client2", "can2", m1));

    }
}
