package com.desafiopicpay.picpaysimplificado.entities.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @NotNull
    private String fullName;

    @NotNull
    @Column(unique = true)
    private String document;

    @NotNull
    @Column(unique = true)
    private String email;

    @NotNull
    private String password;

    @NotNull
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @NotNull
    private UserType type;

}
