/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.ea;

/**
 *
 * @author oliver.guenther
 */
public class MultiLineString {

    static String text = """
                            Hallo Welt!
                            Das ist ein Multiline String.
                            Mal sehen wie gut das geht
                            """;

    public static void main(String[] args) {
        System.out.println(text);

    }

}
