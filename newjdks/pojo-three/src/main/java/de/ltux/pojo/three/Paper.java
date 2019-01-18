/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.pojo.three;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author oliver.guenther
 */
@Data
public class Paper implements Serializable {

    private String color;

    private boolean writtenOn;

    private String content;

    public String toMarkdown() {
        return "Paper\n"
                + "=====\n"
                + "- color: " + color + "\n"
                + "- content: " + content + "\n"
                + "- writtenOn: " + writtenOn + "\n";
    }
}
