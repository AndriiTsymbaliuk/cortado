package com.coffee.shop.cortado.strategies;

import com.coffee.shop.cortado.model.Extras;
import com.coffee.shop.cortado.model.Offering;
import com.coffee.shop.cortado.model.Order;

import java.util.List;

import static com.coffee.shop.cortado.Constants.BACON_ROLL;
import static com.coffee.shop.cortado.Constants.COFFEE_CORRETO;
import static com.coffee.shop.cortado.Constants.COFFEE_LARGE;
import static com.coffee.shop.cortado.Constants.COFFEE_MEDIUM;
import static com.coffee.shop.cortado.Constants.COFFEE_SMALL;
import static com.coffee.shop.cortado.Constants.JUICE;


/**
 *  The strategy makes one of the extra free
 *  if customer orders beverage and snack in the same order.
 */
public class FreeExtrasInvoiceStrategy implements InvoiceStrategy{

    @Override
    public void apply(Order order) {
        applyZeroCostsForFreeExtras(order, getSnacksAndBeveragesCountInOrder(order.getOfferings()));
    }

    private void applyZeroCostsForFreeExtras(Order order, long freeExtrasCount) {
        for (Offering offering : order.getOfferings()) {
            for (Extras extra : offering.getExtras()) {
                if (freeExtrasCount > 0) {
                    order.setTotal(order.getTotal() - extra.getCost());
                    extra.setCost(0);
                    freeExtrasCount--;
                }
            }
        }
    }

    private long getSnacksAndBeveragesCountInOrder(List<Offering> offerings) {
        return Math.min(offerings.stream().
                        filter(offer -> COFFEE_SMALL.equals(offer.getName()) ||
                                COFFEE_MEDIUM.equals(offer.getName()) ||
                                COFFEE_LARGE.equals(offer.getName()) ||
                                COFFEE_CORRETO.equals(offer.getName()) ||
                                JUICE.equals(offer.getName()))
                        .count(),
                offerings.stream().
                        filter(offer -> BACON_ROLL.equals(offer.getName()))
                        .count());
    }
}
