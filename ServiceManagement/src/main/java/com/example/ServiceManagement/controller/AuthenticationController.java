package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.SignupRequestDTO;
import com.example.ServiceManagement.dto.UserDto;
import com.example.ServiceManagement.services.authentication.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthenticationController {
    @Autowired
    private AuthService authService;

    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome(){
        return new ResponseEntity<>("WELCOME!!!",HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/client/sign-up")
    public ResponseEntity<?> signupClient(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Client already exist with email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser=authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/company/sign-up")
    public ResponseEntity<?> signupCompany(@RequestBody SignupRequestDTO signupRequestDTO){
        if(authService.presentByEmail(signupRequestDTO.getEmail())){
            return new ResponseEntity<>("Company already exist with email!", HttpStatus.NOT_ACCEPTABLE);
        }
        UserDto createdUser=authService.signupClient(signupRequestDTO);
        return new ResponseEntity<>(createdUser,HttpStatus.OK);
    }
}
