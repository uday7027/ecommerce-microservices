package com.ecommerce.auth_service.dto;

import com.ecommerce.auth_service.entity.Role;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private Role role;
}