package de.ltux.security;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@RequestScoped
public class LogoutController {

    public String submit() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/faces/login/login.xhtml?faces-redirect=true";
    }
}
