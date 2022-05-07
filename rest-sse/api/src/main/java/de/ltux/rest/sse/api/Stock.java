/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package de.ltux.rest.sse.api;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author oliver.guenther
 */
public class Stock {

    private Integer id;
    private String name;
    private BigDecimal price;
    LocalDateTime dateTime;

    public Stock() {
    }

    public Stock(Integer id, String name, BigDecimal price, LocalDateTime dateTime) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "Stock{" + "id=" + id + ", name=" + name + ", price=" + price + ", dateTime=" + dateTime + '}';
    }

}
