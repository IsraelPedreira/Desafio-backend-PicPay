package com.desafiopicpay.picpaysimplificado.repositories;

import com.desafiopicpay.picpaysimplificado.entities.transfer.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, String> {
}
