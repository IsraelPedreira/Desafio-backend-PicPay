package com.desafiopicpay.picpaysimplificado.services;


import com.desafiopicpay.picpaysimplificado.dtos.CreateUserDTO;
import com.desafiopicpay.picpaysimplificado.entities.user.User;
import com.desafiopicpay.picpaysimplificado.entities.user.UserType;
import com.desafiopicpay.picpaysimplificado.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User createUser(CreateUserDTO user) throws Exception {
        Optional<User> alreadyExistsUser = userRepository.findUserByDocumentOrEmail(user.document(), user.email());

        if(alreadyExistsUser.isPresent()){
            throw new Exception("User already exists");
        }

        User createdUser = new User();
        createdUser.setBalance(user.balance());
        createdUser.setEmail(user.email());
        createdUser.setDocument(user.document());
        createdUser.setType(UserType.valueOf(user.type()));
        createdUser.setFullName(user.fullName());
        createdUser.setPassword(user.password());

        userRepository.save(createdUser);

        return userRepository.save(createdUser);
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

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public BigDecimal updateAmount(BigDecimal newAmount, User user){
        BigDecimal amount = user.getBalance();
        user.setBalance( amount.subtract(newAmount));

        userRepository.save(user);

        return amount;
    }
}
