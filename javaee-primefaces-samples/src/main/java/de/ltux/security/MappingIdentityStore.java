package de.ltux.security;

import java.util.EnumSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import org.slf4j.Logger;

@ApplicationScoped
public class MappingIdentityStore implements IdentityStore {

    @Inject
    private Logger log;

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        log.info("getCallerGroups({})", validationResult);
        if (validationResult.getCallerPrincipal().getName().equals("user-inmem")) {
            return Set.of(Roles.STEIN);
        }
        if (validationResult.getCallerPrincipal().getName().equals("admin-inmem")) {
            return Set.of(Roles.STEIN, Roles.DEPOSIT_CREATE);
        }
        return validationResult.getCallerGroups();
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(IdentityStore.ValidationType.PROVIDE_GROUPS);
    }

}
