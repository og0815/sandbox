/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.test;

import beta.server.itest.Deployer;
import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 *
 * @author oliver.guenther
 */
public class IntegrationPreTest {

    @Test
    public void createDeployment() {
        Assertions.assertThat(Deployer.createDeployment()).isNotNull();
    }

}
