package com.coffee.shop.cortado.model;

import java.util.List;

public class Offering {

    public Offering(String name, double cost, List<Extras> extras) {
        this.name = name;
        this.cost = cost;
        this.extras = extras;
    }

    public Offering(String name, List<Extras> extras) {
        this.name = name;
        this.cost = 0;
        this.extras = extras;
    }
    private String name;
    private double cost;
    private List<Extras> extras;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCost() {
        return cost;
    }

    public List<Extras> getExtras() {
        return extras;
    }

    public void setExtras(List<Extras> extras) {
        this.extras = extras;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}