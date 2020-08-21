/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.security;

/**
 *
 * @author oliver.guenther
 */
public class Roles {

    private final static String HEAD = "§de.ltux.sample:";

    public final static String DEPOSIT_VIEW = "DEPOSIT";

    public final static String DEPOSIT_MODIFY = "§de.ltux.sample:deposit.modify";

    public final static String DEPOSIT_ALL_VIEW = HEAD + "deposit-all.view";

    public final static String STEIN = "STEIN";

    public final static String DEPOSIT_CREATE = HEAD + "deposit.create";

    public final static String[] ALL = new String[]{DEPOSIT_VIEW, DEPOSIT_MODIFY, DEPOSIT_ALL_VIEW, STEIN};

}
