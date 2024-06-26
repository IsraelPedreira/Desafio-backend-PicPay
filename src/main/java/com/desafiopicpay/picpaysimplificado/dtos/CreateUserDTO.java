package com.desafiopicpay.picpaysimplificado.dtos;

import com.desafiopicpay.picpaysimplificado.entities.user.UserType;

import java.math.BigDecimal;

public record CreateUserDTO(String fullName, String document, String email, String password, String type, BigDecimal balance) {
}
