package com.desafiopicpay.picpaysimplificado.controllers;

import com.desafiopicpay.picpaysimplificado.dtos.ErrorMessageDTO;
import com.desafiopicpay.picpaysimplificado.dtos.TransferDTO;
import com.desafiopicpay.picpaysimplificado.entities.transfer.Transfer;
import com.desafiopicpay.picpaysimplificado.services.TransferService;
import jakarta.transaction.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/transfer")
@RestController
public class TransferController {

    @Autowired
    TransferService transferService;
    @PostMapping
    public ResponseEntity<?> createTransfer(@RequestBody TransferDTO transferDTO){
        try{
            Transfer transfer = transferService.createTransfer(transferDTO);
            return ResponseEntity.ok().body(transfer);
        }catch (Exception e){
            ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
