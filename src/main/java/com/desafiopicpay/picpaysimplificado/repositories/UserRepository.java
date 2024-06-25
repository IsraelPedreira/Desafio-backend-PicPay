package com.desafiopicpay.picpaysimplificado.repositories;


import com.desafiopicpay.picpaysimplificado.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findUserByDocument(String document);

    Optional<User> findUserByDocumentOrEmail(String document, String email);
}
