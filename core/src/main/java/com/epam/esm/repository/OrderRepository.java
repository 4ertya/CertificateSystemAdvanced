package com.epam.esm.repository;

import com.epam.esm.model.Order;
import com.epam.esm.repository.specification.SearchSpecification;

import java.util.List;

public interface OrderRepository {
    Order addOrder(Order order);

    List<Order> getOrders(int limit, int offset);

    Order getOrderById(long id);

    long getCount();
}
