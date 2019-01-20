/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.itest;

import beta.server.eao.ContactEao;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test for the Server.
 *
 * @author oliver.guenther
 */
@RunWith(Arquillian.class)
public class ServerIT {

    @Inject
    private ContactEao contactEao;

    @Deployment
    public static WebArchive createDeployment() {
        return Deployer.createDeployment();
    }

    /**
     * Test find all.
     *
     * @throws InterruptedException
     */
    @Test
    public void findAll() throws InterruptedException {
        Assertions.assertThat(contactEao.findAll()).as("ContactsEao.findAll()").isNotNull().isNotEmpty();
    }

}
