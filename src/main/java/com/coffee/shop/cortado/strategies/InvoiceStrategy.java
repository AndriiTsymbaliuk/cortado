package com.coffee.shop.cortado.strategies;

import com.coffee.shop.cortado.model.Order;

public interface InvoiceStrategy {
    void apply(Order order);
}
