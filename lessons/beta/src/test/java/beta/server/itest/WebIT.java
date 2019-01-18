/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beta.server.itest;

import beta.server.eao.ContactEao;
import java.io.File;
import java.net.URL;
import javax.inject.Inject;
import org.assertj.core.api.Assertions;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.GenericArchive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ExplodedImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.Coordinate;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.RUNTIME;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencies;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 *
 * @author oliver.guenther
 */
@RunWith(Arquillian.class)
public class WebIT {

//    @Inject
    private ContactEao contactEao;

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
        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importRuntimeDependencies()
                .addDependency(MavenDependencies.createDependency("org.assertj:assertj-core", RUNTIME, false)) // AssertJ Fluent Assertions                
                .resolve().withTransitivity().asFile();
        WebArchive war = ShrinkWrap.create(WebArchive.class, "beta-test.war")
                .addPackages(true, Filters.excludePaths("beta.server.itest", "beta.server.test"), "beta.server")
                .addClass(Coordinate.class) // Need this cause of the maven resolver is part of the deployment
                .addAsResource("beta/server/assist")
                .addAsLibraries(libs);
        // Only way to add all files under webapp
        war.merge(ShrinkWrap.create(GenericArchive.class).as(ExplodedImporter.class)
                .importDirectory("src/main/webapp").as(GenericArchive.class),
                "/", Filters.includeAll());
        return war;
    }

    @Ignore
    @Test
    public void findAll() throws InterruptedException {
        Assertions.assertThat(contactEao.findAll()).as("ContactsEao.findAll()").isNotNull().isNotEmpty();
    }

    @Test
    @RunAsClient
    public void clickDemo() throws InterruptedException {
        browser.get(deploymentUrl.toExternalForm());
        Thread.sleep(1000);
        demoLink.click();
        Thread.sleep(1000);
        assertThat(inText.isDisplayed()).isTrue();
    }

}
