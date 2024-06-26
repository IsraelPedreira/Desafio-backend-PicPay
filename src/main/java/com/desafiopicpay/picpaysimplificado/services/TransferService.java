package com.desafiopicpay.picpaysimplificado.services;

import com.desafiopicpay.picpaysimplificado.dtos.TransferDTO;
import com.desafiopicpay.picpaysimplificado.entities.transfer.Transfer;
import com.desafiopicpay.picpaysimplificado.entities.user.User;
import com.desafiopicpay.picpaysimplificado.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

public class TransferService {

    @Autowired
    UserService userService;

    @Autowired
    TransferRepository transferRepository;

    public void createTransfer(TransferDTO transfer) throws Exception {

        User sender = userService.getUserById(transfer.senderId());
        User receiver = userService.getUserById(transfer.receiverId());

        userService.validateTransaction(sender,  transfer.value());

        if(!this.authorizeTransfer()){
            throw new Exception("The operation was not authorized");
        }

        Transfer createdTransfer = new Transfer();
        createdTransfer.setAmount(transfer.value());
        createdTransfer.setReceiver(receiver);
        createdTransfer.setSender(sender);
        createdTransfer.setTimestamp(LocalDateTime.now());

        sender.setBalance(sender.getBalance().subtract(transfer.value()));
        receiver.setBalance(receiver.getBalance().add(transfer.value()));

        transferRepository.save(createdTransfer);
    }

    private boolean authorizeTransfer(){
        String url = "https://util.devi.tools/api/v2/authorize";
        RestTemplate restTemplate= new RestTemplate();

        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);

        if(response.getBody().get("status").equals("success")){
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            boolean authorization = (boolean) data.get("authorization");
            return authorization;
        }else{
            return false;
        }


    }
}
