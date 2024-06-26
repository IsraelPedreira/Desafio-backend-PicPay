package com.desafiopicpay.picpaysimplificado.entities.transfer;

import com.desafiopicpay.picpaysimplificado.entities.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "senderId")
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiverId")
    private User receiver;

    LocalDateTime timestamp;

}
