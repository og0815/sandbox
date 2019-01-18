/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.modweb.mod03web.entity;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;

/**
 *
 * @author oliver.guenther
 */
@Data
public class Lecture {
    
    private String name;
    
    private List<Student> students = new ArrayList<>();
    
}
