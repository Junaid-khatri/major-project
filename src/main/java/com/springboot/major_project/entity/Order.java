package com.springboot.major_project.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="ORDER_DETAILS")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String address;
    Date orderDate;
    int amount;


}
