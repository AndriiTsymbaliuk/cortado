package com.coffee.shop.cortado;

import java.util.Map;

public class Constants {

    private Constants() {}
    public static final String DELIMITER = ",";
    public static final String COFFEE_SMALL = "Coffee small";
    public static final String COFFEE_MEDIUM = "Coffee medium";
    public static final String COFFEE_LARGE = "Coffee large";
    public static final String COFFEE_CORRETO = "Coffee corretto";
    public static final String EXTRA_MILK = "Extra milk";
    public static final String FOAMED_MILK = "Foamed milk";
    public static final String SPECIAL_ROAST_COFFEE = "Special roast coffee";
    public static final String BACON_ROLL = "Bacon roll";
    public static final String JUICE = "Freshly squeezed orange juice (0.25l)";
    public static final Map<String, Double> OFFERINGS = Map.of(
            COFFEE_SMALL,2.55,
            COFFEE_MEDIUM,3.05,
            COFFEE_LARGE,3.55,
            COFFEE_CORRETO,5.0,
            BACON_ROLL, 4.53,
            JUICE, 3.95);

    public static final Map<String, Double> EXTRAS = Map.of(
            EXTRA_MILK,0.32,
            FOAMED_MILK,0.51,
            SPECIAL_ROAST_COFFEE,0.95);
}