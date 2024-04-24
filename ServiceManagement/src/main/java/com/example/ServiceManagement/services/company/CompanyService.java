package com.example.ServiceManagement.services.company;

import com.example.ServiceManagement.dto.AdDTO;

import java.io.IOException;

public interface CompanyService {
    public boolean postAd(Long userId, AdDTO adDTO) throws IOException;
}
