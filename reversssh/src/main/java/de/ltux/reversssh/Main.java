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
public class Main {

    public static void main(String[] args) {
        // RequestReverseSsh request = new RequestReverseSsh("host", 8888, "user", "pass");
        // create();
        // list();
        // endAll();
    }

    private static void create(RequestReverseSsh request) {
        var session = SshSession.create(request);
        System.out.println("session.getStatus()");
        System.out.println(session.getStatus());
        System.out.println();
        System.out.println("session.getLog()");
        System.out.println(session.getLog());
    }

    private static void list() {
        for (SshSession session : SshSession.listMaster()) {
            System.out.println("session.getStatus()");
            System.out.println(session.getStatus());
            System.out.println();
            System.out.println("session.getLog()");
            System.out.println(session.getLog());
        }
    }

    private static void endAll() {
        for (SshSession session : SshSession.listMaster()) {
            if (session.getStatus().isAlive()) {
                System.out.println("session.end()");
                session.end().ifPresent(System.out::println);
            }
        }
    }
}
