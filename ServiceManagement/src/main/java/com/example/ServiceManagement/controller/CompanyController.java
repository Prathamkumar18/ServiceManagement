package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.services.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/company")
public class CompanyController {
    @Autowired
    private CompanyService companyService;

    @PostMapping("/ad/{userId}")
    public ResponseEntity<String> postAd(@PathVariable Long userId, @ModelAttribute AdDTO adDTO) throws IOException {
        boolean success=companyService.postAd(userId,adDTO);
        if(success){
            return new ResponseEntity<>("Ad posted!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to post Ad!", HttpStatus.NOT_FOUND);
    }
}
