/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.webbox;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author olive
 */
@Data
@AllArgsConstructor
public class Document {

    private String name;

    private String size;

    private String type;

}
