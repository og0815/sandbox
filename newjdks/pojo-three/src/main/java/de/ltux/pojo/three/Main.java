/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.three;

/**
 *
 * @author oliver.guenther
 */
public class Main {
    
    public static void main(String[] args) {
        var p = new Paper();
        p.setColor("white");
        p.setContent("Bla Bla");
        p.setWrittenOn(true);
        
        System.out.println(p);
        System.out.println(p.toMarkdown());
        
        var s = new Stone("yellow", Stone.Type.OBSIDIAN);
        System.out.println(s);
        s = Stone.builder().color("grey").type(Stone.Type.GRANITE).build();
        System.out.println(s);
    }
    
}
