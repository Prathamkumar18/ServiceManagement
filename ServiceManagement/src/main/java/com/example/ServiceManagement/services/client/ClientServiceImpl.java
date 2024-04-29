package com.example.ServiceManagement.services.client;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.dto.AdDetailsForClientDTO;
import com.example.ServiceManagement.dto.ReservationDTO;
import com.example.ServiceManagement.entity.Ad;
import com.example.ServiceManagement.entity.Reservation;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.enums.ReservationStatus;
import com.example.ServiceManagement.enums.ReviewStatus;
import com.example.ServiceManagement.repository.AdRepository;
import com.example.ServiceManagement.repository.ReservationRepository;
import com.example.ServiceManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;

    public List<AdDTO> getAllAds(){
        return this.adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDTO> searchAdByName(String name){
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public boolean bookService(ReservationDTO reservationDTO){
        Optional<Ad> optionalAd=adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser=userRepository.findById(reservationDTO.getUserId());
        if(optionalUser.isPresent() && optionalAd.isPresent()){
            Reservation reservation=new Reservation();
            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setUser(optionalUser.get());
            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public AdDetailsForClientDTO getAdDetailsByAdId(Long adId){
        Optional<Ad> optionalAd=adRepository.findById(adId);
        AdDetailsForClientDTO adDetailsForClientDTO=new AdDetailsForClientDTO();
        if(optionalAd.isPresent()){
            adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDto());
        }
        return adDetailsForClientDTO;
    }
}
