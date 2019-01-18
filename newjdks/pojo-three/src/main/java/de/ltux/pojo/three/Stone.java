/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.three;

import java.io.Serializable;
import lombok.Builder;
import lombok.Value;

/**
 *
 * @author oliver.guenther
 */
@Value
@Builder
public class Stone implements Serializable {

    public enum Type {
        GRANITE, SANDSTONE, OBSIDIAN
    }

    private final String color;

    private final Type type;

    public String toMarkdown() {
        return this.getClass().getSimpleName() + "\n"
                + "=====\n"
                + "- color: " + color + "\n"
                + "- type: " + type + "\n";
    }

}
