package com.example.ServiceManagement.services.company;

import com.example.ServiceManagement.dto.AdDTO;

import java.io.IOException;
import java.util.List;

public interface CompanyService {
    public boolean postAd(Long userId, AdDTO adDTO) throws IOException;
    public List<AdDTO> getAllAds(Long userId);
    public AdDTO getAdById(Long adId);
    public boolean updateAd(Long adId,AdDTO adDTO) throws IOException;
}
