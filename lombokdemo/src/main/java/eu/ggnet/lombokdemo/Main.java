/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ggnet.lombokdemo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 *
 * @author oliver.guenther
 */
public class Main {
 
    public static void main(String[] args) throws JsonProcessingException, IOException {
        Person p = new Person("Max", "Mustermann", 2);
        System.out.println(p);
        
        FreePerson fp = new FreePerson.Builder()
                .setFirstName("Max")
                .setLastName("MaxMustermann")
                .setBirthTime(LocalDateTime.now())
                .setAge(122).build();
        System.out.println(fp);
        
        ObjectMapper m = new ObjectMapper();
        m.registerModule(new JavaTimeModule());
        String json = m.writeValueAsString(fp);
        
        System.out.println(" ----- ");
        System.out.println(json);
        
        
        FreePerson fp2 = m.readValue(json, FreePerson.class);
        System.out.println(fp2);

      
        System.out.println("------ ");
        String json2 = m.writeValueAsString(p);
        System.out.println(json2);
        
        
    }
    
}
