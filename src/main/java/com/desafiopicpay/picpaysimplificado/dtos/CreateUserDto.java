package com.desafiopicpay.picpaysimplificado.dtos;

import com.desafiopicpay.picpaysimplificado.entities.user.UserType;

public record CreateUserDto(String fullName, String document, String email, String password, UserType type) {
}
