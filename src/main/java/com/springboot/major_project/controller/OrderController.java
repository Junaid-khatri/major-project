package com.springboot.major_project.controller;

import com.springboot.major_project.model.request.OrderDto;
import com.springboot.major_project.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService service;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> create(@RequestBody OrderDto order){
        return ResponseEntity.ok(service.createOrder(order));
    }

    @GetMapping("/view")
    public ResponseEntity<OrderDto> viewById(@RequestParam int id){
        return ResponseEntity.ok(service.viewById(id));
    }

    @GetMapping("/viewAll")
    public ResponseEntity<List<OrderDto>> viewAll(){
        return ResponseEntity.ok(service.viewAll());
    }

}
