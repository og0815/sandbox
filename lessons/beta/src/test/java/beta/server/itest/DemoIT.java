/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.itest;

import java.net.URL;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Demo integration test.
 *
 * @author oliver.guenther
 */
@RunWith(Arquillian.class)
public class DemoIT {

    @ArquillianResource
    private URL deploymentUrl;

    @Drone
    private WebDriver browser;

    @FindBy(id = "in")
    private WebElement inText;

    @FindBy(id = "demo")
    private WebElement demoLink;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return Deployer.createDeployment();
    }

    /**
     * Test, that clicking on the main page opens the demo page.
     *
     * @throws InterruptedException
     */
    @Test
    public void clickDemo() throws InterruptedException {
        browser.get(deploymentUrl.toExternalForm());
        Thread.sleep(1000);
        demoLink.click();
        Thread.sleep(1000);
        assertThat(inText.isDisplayed()).isTrue();
    }

}
