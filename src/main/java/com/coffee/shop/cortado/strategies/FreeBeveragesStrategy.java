package com.coffee.shop.cortado.strategies;

import com.coffee.shop.cortado.model.Extras;
import com.coffee.shop.cortado.model.Offering;
import com.coffee.shop.cortado.model.Order;

import java.util.List;

/**
 *  The strategy finds the cheapest beverage and makes it free
 *  if the customer orders 5th or more beverages.
 */
public class FreeBeveragesStrategy implements InvoiceStrategy {

    public static final int DISCOUNT_THRESHOLD = 5;

    @Override
    public void apply(Order order) {
        List<Offering> offerings = order.getOfferings();
        int discountCount = offerings.size() % (DISCOUNT_THRESHOLD - 1);
        if ((offerings.size() % DISCOUNT_THRESHOLD) == 0) {

            final double totalDiscount = offerings.stream()
                    .map(offering -> offering.getCost() + offering.getExtras()
                            .stream()
                            .map(Extras::getCost)
                            .reduce(0d, Double::sum))
                    .sorted(Double::compare)
                    .limit(discountCount)
                    .reduce(0d, Double::sum);

            order.setTotal(order.getTotal() - totalDiscount);

            applyZeroPricesForFreeBeverages(order, totalDiscount);
        }
    }

    private void applyZeroPricesForFreeBeverages(Order order, double totalDiscount) {
        order.getOfferings().forEach(offering -> {
            Double extrasTotal = offering.getExtras().stream().map(Extras::getCost).reduce(0d, Double::sum);
            if ((offering.getCost() + extrasTotal) == totalDiscount) {
                offering.setCost(0);
                offering.getExtras().forEach(extras -> extras.setCost(0));
            }
        });
    }
}
