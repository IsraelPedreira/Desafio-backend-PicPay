package com.desafiopicpay.picpaysimplificado.dtos;

import java.math.BigDecimal;

public record TransferDTO(String senderId, String receiverId, BigDecimal value) {
}
