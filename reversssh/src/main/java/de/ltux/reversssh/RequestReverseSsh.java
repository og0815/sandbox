/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.reversssh;

/**
 *
 * @author oliver.guenther
 */
public class RequestReverseSsh {

    /**
     * Host for the reverse SSH.
     */
    private final String host;

    /**
     * Port for the reverse SSH. This port is to be uses to open the SSH Session on the remotehost to this host.
     */
    private final long port;

    /**
     * User to authenticate with.
     */
    private final String username;

    /**
     * Password to authenticate with.
     */
    private final String password;

    public RequestReverseSsh(String host, long port, String username, String password) {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public long getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

}
