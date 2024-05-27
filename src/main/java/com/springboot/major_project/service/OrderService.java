package com.springboot.major_project.service;

import com.springboot.major_project.entity.Order;
import com.springboot.major_project.model.request.OrderDto;
import com.springboot.major_project.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository repository;

    public OrderDto createOrder(OrderDto request){
        Order order = Order.builder()
                .orderDate(request.getOrderDate())
                .address(request.getAddress())
                .amount(request.getAmount())
                .build();
        repository.save(order);
        return request;
    }

    public OrderDto viewById(int id){
        Order order = repository.findById(id).orElseThrow();
        return OrderDto.builder()
                .id(order.getId())
                .address(order.getAddress())
                .orderDate(order.getOrderDate())
                .amount(order.getAmount())
                .address(order.getAddress())
                .build();
    }

    public List<OrderDto> viewAll(){
        List<Order> list = repository.findAll();
        List<OrderDto> responseList = new ArrayList<>();
        for(Order order : list){
            OrderDto response = OrderDto.builder()
                    .id(order.getId())
                    .address(order.getAddress())
                    .orderDate(order.getOrderDate())
                    .amount(order.getAmount())
                    .address(order.getAddress())
                    .build();
            responseList.add(response);
        }
        return responseList;
    }

}

