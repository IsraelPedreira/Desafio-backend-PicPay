package com.desafiopicpay.picpaysimplificado.dtos;

import org.springframework.http.HttpStatus;

public record ErrorMessageDTO(HttpStatus status, String message) {
}
