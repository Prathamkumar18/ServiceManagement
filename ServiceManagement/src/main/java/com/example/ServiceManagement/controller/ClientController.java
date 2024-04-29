package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.services.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/ads")
    public ResponseEntity<? > getAllAds(){
        return ResponseEntity.ok(clientService.getAllAds());
    }
}
