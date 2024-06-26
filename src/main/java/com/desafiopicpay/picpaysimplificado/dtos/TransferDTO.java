package com.desafiopicpay.picpaysimplificado.dtos;

import java.math.BigDecimal;

public record TransferDTO(String payer, String payee, BigDecimal value) {
}
