package com.example.ServiceManagement.services.client;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.entity.Ad;
import com.example.ServiceManagement.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private AdRepository adRepository;

    public List<AdDTO> getAllAds(){
        return this.adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDTO> searchAdByName(String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }
}
