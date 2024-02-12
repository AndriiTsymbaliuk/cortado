package com.coffee.shop.cortado.services;

import com.coffee.shop.cortado.calculator.OrderCalculator;
import com.coffee.shop.cortado.model.Order;

public class OrderingService {
    private OrderCalculator orderCalculator = new OrderCalculator();

    public Order process(Order order) {
        return orderCalculator.calculate(order);
    }
}
