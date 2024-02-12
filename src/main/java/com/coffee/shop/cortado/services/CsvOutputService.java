package com.coffee.shop.cortado.services;

import com.coffee.shop.cortado.Constants;
import com.coffee.shop.cortado.model.Order;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Currency;

public class CsvOutputService implements OutputService {

    private static final String COMMON_HEADERS = "ORDER#, NAME, PRICE, EXTRAS, EXTRAS COST";

    @Override
    public String console(Order order) {
        StringBuilder result = new StringBuilder();
        order.getOfferings().forEach(offering -> {
            appendWithDelimiter(result, order.getId());
            appendWithDelimiter(result, offering.getName());
            appendWithDelimiter(result, offering.getCost());
            offering.getExtras().forEach(extras -> {
                appendWithDelimiter(result, extras.getName());
                appendWithDelimiter(result, extras.getCost());
            });
            result.append("\n");
        });
        result.append("\n Order total: ")
                .append(order.getTotal())
                .append(" ")
                .append(Currency.getInstance("CHF").getCurrencyCode());
        return result.toString();
    }

    @Override
    public Path print(Order order) {
        Path path = Paths.get( order.getId() + ".csv");
        String total = COMMON_HEADERS + "\n" + console(order);
        try {
            Files.write(path, total.getBytes(),
                    StandardOpenOption.CREATE_NEW);
        } catch (IOException ex) {
            throw new com.coffee.shop.cortado.exceptions.IOException(ex.getMessage());
        }
        return path;
    }

    private StringBuilder appendWithDelimiter(StringBuilder sb, Object valueToAdd) {
        return sb.append(valueToAdd).append(Constants.DELIMITER);
    }
}
