/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.modweb.mod03web.entity;

import lombok.Data;

/**
 *
 * @author oliver.guenther
 */
@Data
public class Student {
    
    private String name;
 
    private Lecture primaryLecture;
    
}
