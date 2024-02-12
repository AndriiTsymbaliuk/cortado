package com.coffee.shop.cortado.model;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private final int id = LocalDateTime.now().getNano();
    private final List<Offering> offerings;
    private double total;
    public Order(List<Offering> offerings) {
        this.offerings = offerings;
    }
    public int getId() {
        return id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public List<Offering> getOfferings() {
        return offerings;
    }

}