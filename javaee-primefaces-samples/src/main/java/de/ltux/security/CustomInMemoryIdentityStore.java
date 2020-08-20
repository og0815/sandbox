package de.ltux.security;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import org.slf4j.Logger;

@ApplicationScoped
public class CustomInMemoryIdentityStore implements IdentityStore {

    @Inject
    private Logger log;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        log.info("validate({})", credential);

        UsernamePasswordCredential login = (UsernamePasswordCredential) credential;

        if (login.getCaller().equals("admin@mail.com") && login.getPasswordAsString().equals("ADMIN1234")) {
            return new CredentialValidationResult("admin-inmem", new HashSet<>(Arrays.asList("ADMIN", Roles.DEPOSIT_VIEW, Roles.DEPOSIT_MODIFY)));
        } else if (login.getCaller().equals("user@mail.com") && login.getPasswordAsString().equals("USER1234")) {
            return new CredentialValidationResult("user-inmem", new HashSet<>(Arrays.asList(Roles.DEPOSIT_VIEW, "USER")));
        } else {
            return CredentialValidationResult.NOT_VALIDATED_RESULT;
        }
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        log.info("getCallerGroups({})", validationResult);
        return validationResult.getCallerGroups();
    }

}
