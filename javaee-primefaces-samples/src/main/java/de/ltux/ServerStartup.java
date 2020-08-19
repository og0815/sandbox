/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux;

import com.unboundid.ldap.listener.InMemoryDirectoryServer;
import com.unboundid.ldap.listener.InMemoryDirectoryServerConfig;
import com.unboundid.ldap.listener.InMemoryListenerConfig;
import com.unboundid.ldap.sdk.LDAPException;
import com.unboundid.ldif.LDIFReader;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import org.slf4j.Logger;

/**
 *
 * @author oliver.guenther
 */
@Singleton
@Startup
public class ServerStartup {

    private InMemoryDirectoryServer inMemoryDirectoryServer;

    @Inject
    private Logger log;

    @PostConstruct
    public void initLdap() {
        try {
            InMemoryDirectoryServerConfig config = new InMemoryDirectoryServerConfig("dc=ltux,dc=de");
            config.setListenerConfigs(InMemoryListenerConfig.createLDAPConfig("default", 10389));
            config.setSchema(null);
            inMemoryDirectoryServer = new InMemoryDirectoryServer(config);
            inMemoryDirectoryServer.importFromLDIF(true,
                    new LDIFReader(this.getClass().getResourceAsStream("/users.ldif")));
            inMemoryDirectoryServer.startListening();
            log.info("initLdap() successful");
        } catch (LDAPException e) {
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void shutdown() {
        inMemoryDirectoryServer.shutDown(true);
    }
}
