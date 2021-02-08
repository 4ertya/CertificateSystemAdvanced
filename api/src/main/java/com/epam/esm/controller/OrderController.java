package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto createOrder(@RequestBody Map<String, Object> fields) {
        return orderService.addOrder(fields);
    }


    @GetMapping
    public List<OrderDto> getAllOrders(@RequestParam Map<String, String> params) {
        List<OrderDto> orders = orderService.getOrders(params);
        long ordersCount = orderService.getCount(params);
        return orders;
    }

    @GetMapping("/{id}")
    public OrderDto getOrderById(@PathVariable("id") long id) {
        OrderDto orderDTO = orderService.getOrderById(id);
        return orderDTO;
    }
}
