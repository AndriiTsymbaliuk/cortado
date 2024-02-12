package com.coffee.shop.cortado;

import com.coffee.shop.cortado.model.Extras;
import com.coffee.shop.cortado.model.Offering;
import com.coffee.shop.cortado.model.Order;
import com.coffee.shop.cortado.services.OrderingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.coffee.shop.cortado.Constants.BACON_ROLL;
import static com.coffee.shop.cortado.Constants.COFFEE_CORRETO;
import static com.coffee.shop.cortado.Constants.COFFEE_LARGE;
import static com.coffee.shop.cortado.Constants.COFFEE_MEDIUM;
import static com.coffee.shop.cortado.Constants.COFFEE_SMALL;
import static com.coffee.shop.cortado.Constants.EXTRAS;
import static com.coffee.shop.cortado.Constants.EXTRA_MILK;
import static com.coffee.shop.cortado.Constants.FOAMED_MILK;
import static com.coffee.shop.cortado.Constants.JUICE;
import static com.coffee.shop.cortado.Constants.OFFERINGS;
import static com.coffee.shop.cortado.Constants.SPECIAL_ROAST_COFFEE;
import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderingServiceTest {
    private OrderingService orderingService;

    @BeforeEach
    void init() {
        orderingService = new OrderingService();
    }

    @Test
    @DisplayName("Order single offering without extras.")
    void givenSingleOfferingWithoutExtrasThenOrderTotalCalculated() {
        assertEquals(OFFERINGS.get(BACON_ROLL), orderingService.process(singleOffering()).getTotal());
    }


    @Test
    @DisplayName("Order single offering with extras.")
    void givenSingleOfferingWithExtrasThenOrderTotalCalculated() {
        assertEqualsNumbers(OFFERINGS.get(COFFEE_SMALL) + EXTRAS.get(EXTRA_MILK),
                orderingService.process(buildOfferingWithExtras(List.of(COFFEE_SMALL), List.of(EXTRA_MILK))).getTotal());
    }

    @Test
    @DisplayName("Order multiple offering without extras.")
    void givenMultipleOfferingWithoutExtrasThenOrderTotalCalculated() {
        assertEqualsNumbers(OFFERINGS.get(COFFEE_SMALL) + OFFERINGS.get(COFFEE_LARGE),
                orderingService.process(multipleOffering()).getTotal());
    }

    @Test
    @DisplayName("Order multiple offering with extras.")
    void givenMultipleOfferingWithExtrasThenOrderTotalCalculated() {
        assertEqualsNumbers(OFFERINGS.get(COFFEE_SMALL) + EXTRAS.get(EXTRA_MILK)
                        + OFFERINGS.get(COFFEE_LARGE) + EXTRAS.get(FOAMED_MILK),
                orderingService.process(buildMultipleOfferingsWithExtras()).getTotal());
    }

    @Test
    @DisplayName("Order multiple offering with extras and snacks and get one free extra.")
    void givenMultipleOfferingWithExtrasAndSnackThenGetOneExtraForFree() {
        Order order = orderingService.process(buildMultipleOfferingsWithExtrasAndSnack());
        assertEqualsNumbers(OFFERINGS.get(COFFEE_SMALL) +
                OFFERINGS.get(COFFEE_CORRETO) +
                OFFERINGS.get(BACON_ROLL) +
                EXTRAS.get(SPECIAL_ROAST_COFFEE), order.getTotal());

        order.getOfferings().forEach(offering -> {
            if (COFFEE_SMALL.equals(offering.getName())) {
                assertEquals(0.95, offering.getExtras().stream().map(Extras::getCost).reduce(0d, Double::sum));
            }
        });
    }

    @Test
    @DisplayName("Order with 5 beverages and get one for free.")
    void given5BeveragesThen1ForFree() {
        Order order = orderingService.process(buildFiveOfferings());
        assertEqualsNumbers(
                OFFERINGS.get(COFFEE_MEDIUM) + EXTRAS.get(FOAMED_MILK) +
                        OFFERINGS.get(COFFEE_LARGE) + EXTRAS.get(EXTRA_MILK) +
                        OFFERINGS.get(COFFEE_CORRETO) + EXTRAS.get(FOAMED_MILK) +
                        OFFERINGS.get(JUICE),
                order.getTotal());
        order.getOfferings().forEach(offering -> {
            if (COFFEE_SMALL.equals(offering.getName())) {
                assertEquals(0, offering.getCost());
                assertEquals(0, offering.getExtras().stream().map(Extras::getCost).reduce(0d, Double::sum));
            }
        });
    }

    private Order buildFiveOfferings() {
        return new Order(List.of(
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_SMALL),
                        List.of(new Extras(EXTRA_MILK, EXTRAS.get(EXTRA_MILK)))),
                new Offering(COFFEE_MEDIUM, OFFERINGS.get(COFFEE_MEDIUM),
                        List.of(new Extras(FOAMED_MILK, EXTRAS.get(FOAMED_MILK)))),
                new Offering(COFFEE_LARGE, OFFERINGS.get(COFFEE_LARGE),
                        List.of(new Extras(EXTRA_MILK, EXTRAS.get(EXTRA_MILK)))),
                new Offering(COFFEE_CORRETO, OFFERINGS.get(COFFEE_CORRETO),
                        List.of(new Extras(FOAMED_MILK, EXTRAS.get(FOAMED_MILK)))),
                new Offering(JUICE, OFFERINGS.get(JUICE), List.of())
        )
        );
    }

    private Order buildMultipleOfferingsWithExtras() {
        return new Order(List.of(
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_SMALL),
                        List.of(new Extras(EXTRA_MILK, EXTRAS.get(EXTRA_MILK)))),
                new Offering(COFFEE_LARGE, OFFERINGS.get(COFFEE_LARGE),
                        List.of(new Extras(FOAMED_MILK, EXTRAS.get(FOAMED_MILK))))
        )
        );
    }

    private Order buildMultipleOfferingsWithExtrasAndSnack() {
        return new Order(List.of(
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_SMALL),
                        List.of(
                                new Extras(EXTRA_MILK, EXTRAS.get(EXTRA_MILK)),
                                new Extras(SPECIAL_ROAST_COFFEE, EXTRAS.get(SPECIAL_ROAST_COFFEE))
                        )),
                new Offering(COFFEE_CORRETO, OFFERINGS.get(COFFEE_CORRETO), List.of()),
                new Offering(BACON_ROLL, OFFERINGS.get(BACON_ROLL), List.of()))
        );
    }

    private Order singleOffering() {
        return new Order(List.of(new Offering(BACON_ROLL, OFFERINGS.get(BACON_ROLL), List.of())));
    }

    private Order multipleOffering() {
        return new Order(List.of(
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_SMALL), List.of()),
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_LARGE), List.of()))
        );
    }

    private Order buildOfferingWithExtras(List<String> offerings, List<String> extras) {
        List<Offering> offeringList = new ArrayList<>();
        offerings.forEach(offering -> offeringList.add(new Offering(offering, OFFERINGS.get(offering),
                List.of(new Extras(extras.get(0), EXTRAS.get(extras.get(0)))))));
        return new Order(offeringList);
    }

    private void assertEqualsNumbers(double expected, double actual) {
        assertEquals(getFormattedValue(expected), getFormattedValue(actual));
    }

    private Double getFormattedValue(double value) {
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.valueOf(df.format(value));
    }
}
