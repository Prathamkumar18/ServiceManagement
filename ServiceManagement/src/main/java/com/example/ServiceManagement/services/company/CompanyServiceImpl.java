package com.example.ServiceManagement.services.company;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.entity.Ad;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.repository.AdRepository;
import com.example.ServiceManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdRepository adRepository;

    public boolean postAd(Long userId, AdDTO adDTO) throws IOException {
        Optional<User> optionalUser=userRepository.findById(userId);
        if(optionalUser.isPresent()){
            Ad ad=new Ad();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());
            ad.setImg(adDTO.getImg().getBytes());
            ad.setUser(optionalUser.get());
            adRepository.save(ad);
            return true;
        }
        return false;
    }

    public List<AdDTO> getAllAds(Long userId){
        return adRepository.findAllByUserId(userId).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public AdDTO getAdById(Long adId){
        Optional<Ad> optionalAd=adRepository.findById(adId);
        if(optionalAd.isPresent()){
            return optionalAd.get().getAdDto();
        }
        return null;
    }

    public boolean updateAd(Long adId,AdDTO adDTO) throws IOException {
        Optional<Ad> optionalAd=adRepository.findById(adId);
        if(optionalAd.isPresent()){
            Ad ad=optionalAd.get();
            ad.setServiceName(adDTO.getServiceName());
            ad.setDescription(adDTO.getDescription());
            ad.setPrice(adDTO.getPrice());
            if(adDTO.getImg()!=null)ad.setImg(adDTO.getImg().getBytes());
            adRepository.save(ad);
            return true;
        }
        return false;
    }
}
