package com.coffee.shop.cortado.services;

import com.coffee.shop.cortado.model.Order;

import java.nio.file.Path;

public interface OutputService {

    String console(Order order);

    Path print(Order order);
}
