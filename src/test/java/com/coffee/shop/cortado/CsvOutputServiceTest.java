package com.coffee.shop.cortado;

import com.coffee.shop.cortado.model.Extras;
import com.coffee.shop.cortado.model.Offering;
import com.coffee.shop.cortado.model.Order;
import com.coffee.shop.cortado.services.CsvOutputService;
import com.coffee.shop.cortado.services.OrderingService;
import com.coffee.shop.cortado.services.OutputService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static com.coffee.shop.cortado.Constants.COFFEE_CORRETO;
import static com.coffee.shop.cortado.Constants.COFFEE_LARGE;
import static com.coffee.shop.cortado.Constants.COFFEE_MEDIUM;
import static com.coffee.shop.cortado.Constants.COFFEE_SMALL;
import static com.coffee.shop.cortado.Constants.DELIMITER;
import static com.coffee.shop.cortado.Constants.EXTRAS;
import static com.coffee.shop.cortado.Constants.EXTRA_MILK;
import static com.coffee.shop.cortado.Constants.FOAMED_MILK;
import static com.coffee.shop.cortado.Constants.JUICE;
import static com.coffee.shop.cortado.Constants.OFFERINGS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
class CsvOutputServiceTest {

    private OrderingService orderingService;
    private OutputService outputService;

    @BeforeEach
    void init() {
        orderingService = new OrderingService();
        outputService = new CsvOutputService();
    }

    @DisplayName("Validate multiple offerings output before save to file.")
    @Test
    void givenMultipleOfferingWitExtrasThenOutputToCsvBeforeSaveToFile() {
        Order order = orderingService.process(new Order(List.of(
                new Offering(COFFEE_SMALL, OFFERINGS.get(COFFEE_SMALL),
                        List.of(new Extras(EXTRA_MILK, EXTRAS.get(EXTRA_MILK)))),
                new Offering(COFFEE_LARGE, OFFERINGS.get(COFFEE_LARGE),
                        List.of(new Extras(FOAMED_MILK, EXTRAS.get(FOAMED_MILK))))
        )));

        String[] output = outputService.console(order).split(DELIMITER);
        assertEquals(COFFEE_SMALL, output[1]);
        assertEquals(OFFERINGS.get(COFFEE_SMALL).toString(), output[2]);
        assertEquals(EXTRA_MILK, output[3]);
        assertEquals(EXTRAS.get(EXTRA_MILK).toString(), output[4]);
        assertEquals(COFFEE_LARGE, output[6]);
        assertEquals(OFFERINGS.get(COFFEE_LARGE).toString(), output[7]);
        assertEquals(FOAMED_MILK, output[8]);
        assertEquals(EXTRAS.get(FOAMED_MILK).toString(), output[9]);
    }


    @DisplayName("Save multiple offerings with Beverages.")
    @Test
    void givenMultipleOfferingWitExtrasThenOutputToCsvAndSaveToFile() throws IOException {
        Order order = orderingService.process(fiveOfferings());
        Path path = outputService.print(order);
        assertNotNull(path.getFileName());
        Files.delete(path);
    }

    private Order fiveOfferings() {
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
}
