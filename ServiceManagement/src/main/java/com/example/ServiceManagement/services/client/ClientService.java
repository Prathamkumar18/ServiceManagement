package com.example.ServiceManagement.services.client;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.dto.AdDetailsForClientDTO;
import com.example.ServiceManagement.dto.ReservationDTO;

import java.util.List;

public interface ClientService {
    List<AdDTO> getAllAds();
    List<AdDTO> searchAdByName(String name);
    boolean bookService(ReservationDTO reservationDTO);
    AdDetailsForClientDTO getAdDetailsByAdId(Long adId);
    List<ReservationDTO> getAllBookingsByUserId(Long userId);
}
