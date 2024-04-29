package com.example.ServiceManagement.services.client;

import com.example.ServiceManagement.dto.AdDTO;

import java.util.List;

public interface ClientService {
    public List<AdDTO> getAllAds();
    public List<AdDTO> searchAdByName(String name);
}
