package com.desafiopicpay.picpaysimplificado.services;


import com.desafiopicpay.picpaysimplificado.dtos.CreateUserDTO;
import com.desafiopicpay.picpaysimplificado.entities.user.User;
import com.desafiopicpay.picpaysimplificado.entities.user.UserType;
import com.desafiopicpay.picpaysimplificado.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void createUser(CreateUserDTO user) throws Exception {
        Optional<User> alreadyExistsUser = userRepository.findUserByDocumentOrEmail(user.document(), user.email());

        if(alreadyExistsUser.isPresent()){
            throw new Exception("User already exists");
        }

//        User createdUser = new User(
//                user.fullName(),
//                user.document(),
//                user.email(),
//                user.password(),
//                user.password(),
//                new BigDecimal(0),
//                user.type()
//        );

//        return userRepository.save(createdUser);
    }

    public void validateTransaction(User user, BigDecimal amount) throws Exception {
        if(user.getType() == UserType.MERCHANT){
            throw new Exception("The merchant user cannot make a transaction");
        }

        if(user.getBalance().compareTo(amount) < 0){
            throw new Exception("The user does not have enough money to make this transaction");
        }
    }

    public User getUserById(String id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("User not found"));
    }

    public BigDecimal updateAmount(BigDecimal newAmount, User user){
        BigDecimal amount = user.getBalance();
        user.setBalance( amount.subtract(newAmount));

        userRepository.save(user);

        return amount;
    }


}
