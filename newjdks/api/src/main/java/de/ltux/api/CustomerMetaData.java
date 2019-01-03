/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.ltux.api;

import java.io.Serializable;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *
 * @author oliver.guenther
 */
@Data // Wildfly 15 cannot deserialize final collection or @Value.
@AllArgsConstructor
public class CustomerMetaData implements Serializable {

    /**
     * Customer identifier.
     */
    private long id;

    /**
     * {@link PaymentCondition} on wich the customer buys.
     */
    private PaymentCondition paymentCondition;

    /**
     * {@link PaymentMethod} on wich the customer buys.
     */
    private PaymentMethod paymentMethod;

    /**
     * {@link ShippingCondition} on wich the customer buys.
     */
    private ShippingCondition shippingCondition;

    /**
     * {@link CustomerFlag}<code>s</code> of the customer.
     */
    private Set<CustomerFlag> flags;

    /**
     * Allowed {@link SalesChannel} for the customer.
     */
    private Set<SalesChannel> allowedSalesChannel;

    /**
     * Contains a violation info, if the customer is not valid based on the entity model (Caused by a migration in 2018).
     */
    private String violationMessage;
}

