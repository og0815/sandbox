/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package de.ltux.lldp;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import de.ltux.lldp.entity.LldpMapping;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 *
 * @author oliver.guenther
 */
public class Lldp {

    public static ObjectMapper createDefaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModules(new Jdk8Module(), new JavaTimeModule());
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        mapper.writerWithDefaultPrettyPrinter();
        return mapper;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        ProcessBuilder pb = new ProcessBuilder("/usr/sbin/lldpcli", "-f", "json0", "show", "neighbors");
        pb.redirectErrorStream(true);
        Process p = pb.start();
        p.waitFor(10, TimeUnit.SECONDS);
        String out = new String(p.getInputStream().readAllBytes());
        System.out.println("RAW");
        System.out.println(out);
        System.out.println("------------------");
        
        LldpMapping m = createDefaultObjectMapper().readValue(out, LldpMapping.class);
        
        System.out.println(m);
        
        List<String> names = m.lldp.stream()
                .flatMap((lldp) -> lldp.interfaces.stream())
                .flatMap((iface) -> iface.chassis.stream())
                .flatMap((chassis) -> chassis.name.stream())
                .map((name) -> name.value)
                .collect(Collectors.toList());
        
        System.out.println(names);

    }
}
