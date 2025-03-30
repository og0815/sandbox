/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.fins;

/**
 *
 * @author oliver.guenther
 */
public class HexUtils {

    public static String toString(byte... data) {
        return toString(null, data);
    }

    public static String toString(String separator, byte... data) {
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X", b));
            if (separator != null) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
