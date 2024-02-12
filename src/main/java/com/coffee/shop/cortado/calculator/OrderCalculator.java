package com.coffee.shop.cortado.calculator;

import com.coffee.shop.cortado.model.Offering;
import com.coffee.shop.cortado.model.Order;
import com.coffee.shop.cortado.strategies.FreeBeveragesStrategy;
import com.coffee.shop.cortado.strategies.FreeExtrasInvoiceStrategy;
import com.coffee.shop.cortado.strategies.InvoiceStrategy;

import java.util.List;

import static com.coffee.shop.cortado.strategies.FreeBeveragesStrategy.DISCOUNT_THRESHOLD;

public class OrderCalculator implements Calculator {
    private final InvoiceStrategy freeBeveragesStrategy = new FreeBeveragesStrategy();
    private final InvoiceStrategy freeExtrasStrategy = new FreeExtrasInvoiceStrategy();

    @Override
    public Order calculate(Order order) {
        List<Offering> offerings = order.getOfferings();

        offerings.forEach(offering -> {
                    order.setTotal(order.getTotal() + offering.getCost());
                    offering.getExtras().forEach(extras -> order.setTotal(order.getTotal() + extras.getCost()));
                }
        );

        if (!offerings.isEmpty() && offerings.size() > 1) {
            freeExtrasStrategy.apply(order);
            if (offerings.size() > DISCOUNT_THRESHOLD - 1) {
                freeBeveragesStrategy.apply(order);
            }
        }
        return order;
    }
}
