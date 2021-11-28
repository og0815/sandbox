/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.jdk17;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 *
 * @author oliver.guenther
 */
public class Main {

    /**
     *
     * @param args
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ClassNotFoundException {
        var p = new Person("Max", "Musterman", 22);
        System.out.println("Before: " + p);

        var out = new ObjectOutputStream(new FileOutputStream("serial.data"));
        out.writeObject(p);
        out.close();

        var in = new ObjectInputStream(new FileInputStream("serial.data"));
        var p2 = in.readObject();
        if (p2 instanceof Person) {
            System.out.println("Instance is Person");
        }
        System.out.println("After: " + p2);
        in.close();
    }

}
