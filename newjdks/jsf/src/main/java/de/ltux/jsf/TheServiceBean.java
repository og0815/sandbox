/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.jsf;

import de.ltux.api.*;
import de.ltux.pojo.one.Person;
import de.ltux.pojo.three.Paper;
import de.ltux.pojo.three.Stone;
import java.util.EnumSet;
import javax.ejb.Stateless;

/**
 *
 * @author oliver.guenther
 */
@Stateless
public class TheServiceBean implements TheService {

    @Override
    public Stone getStone() {
        return Stone.builder().color("Yellow").type(Stone.Type.OBSIDIAN).build();
    }

    @Override
    public Person getPerson() {
        return new Person("Max", "Mustermann", 22);
    }

    @Override
    public Paper getPaper() {
        Paper p = new Paper();
        p.setColor("white");
        p.setContent("A letter");
        p.setWrittenOn(true);
        return p;
    }

    @Override
    public CustomerMetaData getCustomerMetaData() {
        return new CustomerMetaData(1, PaymentCondition.CUSTOMER, PaymentMethod.DIRECT_DEBIT, ShippingCondition.SIX_MIN_TEN, EnumSet.of(CustomerFlag.CS_UPDATE_CANDIDATE), EnumSet.of(SalesChannel.RETAILER), null);
    }

}
