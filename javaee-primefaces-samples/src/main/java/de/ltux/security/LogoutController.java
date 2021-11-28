package de.ltux.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;

@Named
@RequestScoped
public class LogoutController {

    @Inject
    private SecurityContext sc;

    @Inject
    private RolesManagerBean rm;

    public String getUserName() {
        return rm.getUserName();
//        if (sc == null) {
//            return "null";
//        }
//        return sc.getCallerPrincipal().getName();
    }

    public String getRoles() {
        return rm.getRoles();
//        if (sc == null) {
//            return "null";
//        }
//        StringBuilder sb = new StringBuilder();
//        for (String role : Arrays.asList("USER", "ADMIN", Roles.DEPOSIT_ALL_VIEW, Roles.DEPOSIT_MODIFY, Roles.DEPOSIT_VIEW)) {
//            sb.append(role).append("=").append(sc.isCallerInRole(role)).append("|");
//        }
//        return sb.toString();
    }

    public String submit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/faces/login/login.xhtml?faces-redirect=true";
    }
}
