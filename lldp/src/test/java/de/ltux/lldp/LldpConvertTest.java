/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.lldp;

import static de.ltux.lldp.Lldp.createDefaultObjectMapper;
import de.ltux.lldp.entity.LldpMapping;
import java.io.IOException;
import java.nio.charset.Charset;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

/**
 *
 * @author oliver.guenther
 */
public class LldpConvertTest {

    @Test
    public void convertLldp1() throws IOException {
        String lldp1 = new String(this.getClass().getResourceAsStream("lldp1.json").readAllBytes(), Charset.defaultCharset());
        assertThat(lldp1).isNotNull();
        LldpMapping m = createDefaultObjectMapper().readValue(lldp1, LldpMapping.class);        
        assertThat(m.lldp.get(0).interfaces.get(0).chassis.get(0).name.get(0).value).isEqualTo("fritz.box");
        assertThat(m.lldp.get(0).interfaces.get(0).chassis.get(0).mgmtIp.get(0).value).isEqualTo("192.168.2.1");
    }

}
