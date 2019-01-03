/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.remote;

import de.ltux.api.TheService;
import java.util.Properties;
import java.util.concurrent.Callable;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.wildfly.security.auth.client.AuthenticationConfiguration;
import org.wildfly.security.auth.client.AuthenticationContext;
import org.wildfly.security.auth.client.MatchRule;

/**
 *
 * @author oliver.guenther
 */
public class Client {
 
    
    public static void main(String[] args) throws Exception {
          AuthenticationConfiguration ejbConfig = AuthenticationConfiguration.empty().useName("admin").usePassword("admin");
        AuthenticationContext context = AuthenticationContext.empty().with(MatchRule.ALL.matchHost("localhost"), ejbConfig);

        Callable<TheService> callable = () -> {

              try {
                  // create an InitialContext
                  Properties properties = new Properties();
                  properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.wildfly.naming.client.WildFlyInitialContextFactory");
                  properties.put(Context.PROVIDER_URL, "remote+http://localhost:8080");
                  InitialContext c = new InitialContext(properties);
                  
                  TheService instance = (TheService)c.lookup("ejb:/jsf//TheServiceBean!de.ltux.api.TheService");
             //     show(instance);
                  return instance;
              } catch (NamingException ex) {
                 throw new RuntimeException(ex);
              }
        };
        
        TheService service = context.runCallable(callable);
        show(service);
    }
    
    
    public static void show(TheService service) {
        System.out.println(service.getPaper());
        System.out.println(service.getPerson());
        System.out.println(service.getStone());        
        System.out.println(service.getCustomerMetaData());
    }
}
