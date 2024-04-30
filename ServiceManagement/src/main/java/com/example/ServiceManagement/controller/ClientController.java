package com.example.ServiceManagement.controller;

import com.example.ServiceManagement.dto.ReservationDTO;
import com.example.ServiceManagement.dto.ReviewDTO;
import com.example.ServiceManagement.services.client.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping("/ads")
    public ResponseEntity<?> getAllAds() {
        return ResponseEntity.ok(clientService.getAllAds());
    }

    @GetMapping("/search/{name}")
    public ResponseEntity<?> searchAdByService(@PathVariable String name) {
        return ResponseEntity.ok(clientService.searchAdByName(name));
    }

    @PostMapping("book-service")
    public ResponseEntity<?> bookService(@RequestBody ReservationDTO reservationDTO) {
        boolean success = this.clientService.bookService(reservationDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/ad/{adId}")
    public ResponseEntity<?> getAdDetailsByAdId(@PathVariable Long adId) {
        return ResponseEntity.ok(clientService.getAdDetailsByAdId(adId));
    }

    @GetMapping("my-bookings/{userId}")
    public ResponseEntity<?> getAllBookingsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(clientService.getAllBookingsByUserId(userId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@RequestBody ReviewDTO reviewDTO) {
        boolean success = clientService.giveReview(reviewDTO);
        if (success) {
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
