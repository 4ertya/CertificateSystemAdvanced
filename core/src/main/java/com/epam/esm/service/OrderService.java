package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;
import java.util.Map;

public interface OrderService {

    OrderDto addOrder(Map<String, Object> fields);

    List<OrderDto> getOrders(Map<String, String> params);

    OrderDto getOrderById(long id);

    long getCount(Map<String, String> params);
}
