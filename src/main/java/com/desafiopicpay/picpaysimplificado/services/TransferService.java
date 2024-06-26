package com.desafiopicpay.picpaysimplificado.services;

import com.desafiopicpay.picpaysimplificado.dtos.TransferDTO;
import com.desafiopicpay.picpaysimplificado.entities.transfer.Transfer;
import com.desafiopicpay.picpaysimplificado.entities.user.User;
import com.desafiopicpay.picpaysimplificado.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransferService {

    @Autowired
    UserService userService;

    @Autowired
    TransferRepository transferRepository;

    @Autowired
    ExternalAPIsService externalAPIsService;

    public Transfer createTransfer(TransferDTO transfer) throws Exception {

        User sender = userService.getUserById(transfer.payer());
        User receiver = userService.getUserById(transfer.payee());

        userService.validateTransaction(sender,  transfer.value());

        if(!externalAPIsService.authorizeTransfer()){
            throw new Exception("The operation was not authorized");
        }

        Transfer createdTransfer = new Transfer();
        createdTransfer.setAmount(transfer.value());
        createdTransfer.setReceiver(receiver);
        createdTransfer.setSender(sender);
        createdTransfer.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transfer.value()));
        receiver.setBalance(receiver.getBalance().add(transfer.value()));

        Transfer returnedTransfer = transferRepository.save(createdTransfer);

        externalAPIsService.sendNotification(receiver.getEmail(), "Transfer received from " + sender.getFullName());

        return returnedTransfer;
    }


}
