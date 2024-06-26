package com.desafiopicpay.picpaysimplificado.controllers;

import com.desafiopicpay.picpaysimplificado.dtos.CreateUserDTO;
import com.desafiopicpay.picpaysimplificado.dtos.ErrorMessageDTO;
import com.desafiopicpay.picpaysimplificado.entities.user.User;
import com.desafiopicpay.picpaysimplificado.entities.user.UserType;
import com.desafiopicpay.picpaysimplificado.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @Operation(description = "Create new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return created user"),
            @ApiResponse(responseCode = "400", description = "An error occurred creating user. This may have been caused by invalid data or existing data")
    })
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody() CreateUserDTO userDto){
        try{
            User createdUser = userService.createUser(userDto);
            return ResponseEntity.ok().body(createdUser);
        }catch (Exception e){
            ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }

    @Operation(description = "Show all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Return array with all users data"),
            @ApiResponse(responseCode = "400", description = "An error occurred while fetching user data")
    })
    @GetMapping
    public ResponseEntity<?> getAllUsers(){
        try{
            return ResponseEntity.ok().body(userService.getAllUsers());
        }catch (Exception e){
            ErrorMessageDTO errorMessage = new ErrorMessageDTO(
                    HttpStatus.BAD_REQUEST,
                    e.getMessage()
            );
            return ResponseEntity.badRequest().body(errorMessage);
        }
    }
}
