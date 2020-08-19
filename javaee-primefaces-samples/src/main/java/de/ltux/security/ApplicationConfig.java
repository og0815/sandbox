package de.ltux.security;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.annotation.FacesConfig;
import javax.security.enterprise.authentication.mechanism.http.CustomFormAuthenticationMechanismDefinition;
import javax.security.enterprise.authentication.mechanism.http.LoginToContinue;
import javax.security.enterprise.identitystore.LdapIdentityStoreDefinition;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/faces/login/login.xhtml",
                useForwardToLogin = false
        )
)
@LdapIdentityStoreDefinition(
        url = "ldap://localhost:10389",
        callerBaseDn = "ou=caller,dc=ltux,dc=de",
        groupSearchBase = "ou=group,dc=ltux,dc=de",
        groupSearchFilter = "(&(member=%s)(objectClass=groupOfNames))"
)
@FacesConfig
@ApplicationScoped
public class ApplicationConfig {
}
