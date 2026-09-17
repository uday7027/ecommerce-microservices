package com.ecommerce.order_service.dto;

import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
}