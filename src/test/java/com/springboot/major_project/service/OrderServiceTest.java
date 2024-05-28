package com.springboot.major_project.service;

import com.springboot.major_project.entity.Order;
import com.springboot.major_project.model.request.OrderDto;
import com.springboot.major_project.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Mock
    private OrderRepository repository;

    @InjectMocks
    private OrderService orderService;

    private OrderDto orderDto;
    private Order order;

    @BeforeEach
    public void setUp() {
        orderDto = new OrderDto();
        orderDto.setId(1);
        orderDto.setOrderDate(new Date());
        orderDto.setAddress("123 Test St.");
        orderDto.setAmount(100);

        order = Order.builder()
                .id(1)
                .orderDate(new Date())
                .address("123 Test St.")
                .amount(100)
                .build();
    }

    @Test
    public void createOrder_shouldSaveOrder_andReturnOrderDto() {
        when(repository.save(any(Order.class))).thenReturn(order);

        OrderDto result = orderService.createOrder(orderDto);

        verify(repository).save(any(Order.class));
        assertEquals(orderDto, result);
    }


    @Test
    void viewById() {

        when(repository.findById(1)).thenReturn(Optional.of(order));

        OrderDto found = orderService.viewById(1);

        assertEquals(order.getId(),found.getId());
        assertEquals(order.getAddress(),found.getAddress());

    }

    @Test
    void viewAll() {

        when(repository.findAll()).thenReturn(List.of(order));
        List<OrderDto> found = orderService.viewAll();
        assertTrue(!(found.isEmpty()));
    }
}