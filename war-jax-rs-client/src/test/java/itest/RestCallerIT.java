/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itest;

import java.io.File;
import java.util.List;
import javax.inject.Inject;
import static org.assertj.core.api.Assertions.assertThat;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.Coordinate;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import static org.jboss.shrinkwrap.resolver.api.maven.ScopeType.RUNTIME;
import org.jboss.shrinkwrap.resolver.api.maven.coordinate.MavenDependencies;
import org.junit.Test;
import org.junit.runner.RunWith;
import rest.Person;
import rest.PersonCaller;

/**
 *
 * @author oliver.guenther
 */
@RunWith(Arquillian.class)
public class RestCallerIT {

    private final static String FULL_NAME = "rest-test";

    @Inject
    private PersonCaller caller;

    @Deployment
    public static WebArchive deployment() {
        File[] libs = Maven.resolver()
                .loadPomFromFile("pom.xml")
                .importCompileAndRuntimeDependencies()
                .addDependency(MavenDependencies.createDependency("org.assertj:assertj-core", RUNTIME, false)) // AssertJ Fluent Assertions
                .resolve().withTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class, FULL_NAME + ".war")
                .addPackages(true, Filters.exclude("itest"), "rest")
                .addClass(Coordinate.class) // Need this cause of the maven resolver is part of the deployment
                .addAsLibraries(libs);
        return war;
    }

    @Test
    public void callPersonsWithoutMapping() {
        assertThat(caller).isNotNull();
        String facts = caller.callPersonsManual("http://localhost:8080/" + FULL_NAME + "/rest");
        assertThat(facts).isNotBlank();
    }

    @Test
    public void callPersonsProxy() {
        assertThat(caller).isNotNull();
        List<Person> facts = caller.callPersonsProxy("http://localhost:8080/" + FULL_NAME + "/rest");
        assertThat(facts).isNotNull().isNotEmpty();
    }

}
