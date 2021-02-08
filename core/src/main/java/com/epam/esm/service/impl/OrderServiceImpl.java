package com.epam.esm.service.impl;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.mapper.OrderMapper;
import com.epam.esm.model.Order;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CertificateService certificateService;
    private final OrderMapper orderMapper;


    @Override
    public OrderDto addOrder(Map<String, Object> fields) {

        OrderDto newOrderDto = createNewOrder(fields);
        Order newOrder = orderMapper.toModel(newOrderDto);
        return orderMapper.toDTO(orderRepository.addOrder(newOrder));
    }

    private OrderDto createNewOrder(Map<String, Object> fields) {
        OrderDto order = new OrderDto();
        BigDecimal cost = new BigDecimal(0);
        order.setUserId(((Number) fields.get("userId")).longValue());
        List<Number> certificatesIdValues = (ArrayList<Number>) fields.get("certificateId");
        certificatesIdValues.forEach(certificateId -> {
            CertificateDto certificate = certificateService.findCertificateById(certificateId.longValue());
            order.getCertificates().add(certificate);
        });
        for (CertificateDto certificateDto : order.getCertificates()) {
            cost = cost.add(certificateDto.getPrice());
        }
        order.setCost(cost);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    @Override
    public List<OrderDto> getOrders(Map<String, String> params) {
        return getOrdersFromDatabase(params);
    }

    @Override
    public long getCount(Map<String, String> params) {
        return orderRepository.getCount();
    }

    private List<OrderDto> getOrdersFromDatabase(Map<String, String> params) {
        List<OrderDto> orders = new ArrayList<>();
        int limit = Integer.parseInt(params.get("size"));
        int offset = (Integer.parseInt(params.get("page")) - 1) * limit;
        long elementsCount = orderRepository.getCount();
        orderRepository.getOrders(limit, offset)
                .forEach(order -> orders.add(orderMapper.toDTO(order)));
        return orders;
    }


    @Override
    public OrderDto getOrderById(long id) {
        return orderMapper.toDTO(orderRepository.getOrderById(id));
    }
}
