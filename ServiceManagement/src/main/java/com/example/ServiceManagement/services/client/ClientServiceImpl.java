package com.example.ServiceManagement.services.client;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.dto.AdDetailsForClientDTO;
import com.example.ServiceManagement.dto.ReservationDTO;
import com.example.ServiceManagement.dto.ReviewDTO;
import com.example.ServiceManagement.entity.Ad;
import com.example.ServiceManagement.entity.Reservation;
import com.example.ServiceManagement.entity.Review;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.enums.ReservationStatus;
import com.example.ServiceManagement.enums.ReviewStatus;
import com.example.ServiceManagement.repository.AdRepository;
import com.example.ServiceManagement.repository.ReservationRepository;
import com.example.ServiceManagement.repository.ReviewRepository;
import com.example.ServiceManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private AdRepository adRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReviewRepository reviewRepository;

    public List<AdDTO> getAllAds() {
        return this.adRepository.findAll().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDTO> getAllAdsSorted(String category,String sortBy) {
        if(category.equals("price") && sortBy.equals("low")) return this.adRepository.findAllAdsSortByPriceAsc().stream().map(Ad::getAdDto).collect(Collectors.toList());
        else if(category.equals("price") && sortBy.equals("high"))return this.adRepository.findAllAdsSortByPriceDesc().stream().map(Ad::getAdDto).collect(Collectors.toList());
        else if(category.equals("average_rating") && sortBy.equals("low"))return this.adRepository.findAllAdsSortByAverageRatingAsc().stream().map(Ad::getAdDto).collect(Collectors.toList());
        else return this.adRepository.findAllAdsSortByAverageRatingDesc().stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public List<AdDTO> searchAdByName(String name) {
        return adRepository.findAllByServiceNameContaining(name).stream().map(Ad::getAdDto).collect(Collectors.toList());
    }

    public boolean bookService(ReservationDTO reservationDTO) {
        Optional<Ad> optionalAd = adRepository.findById(reservationDTO.getAdId());
        Optional<User> optionalUser = userRepository.findById(reservationDTO.getUserId());
        if (optionalUser.isPresent() && optionalAd.isPresent()) {
            Reservation reservation = new Reservation();
            reservation.setBookDate(reservationDTO.getBookDate());
            reservation.setReservationStatus(ReservationStatus.PENDING);
            reservation.setPaidAmount(-1L);
            reservation.setUser(optionalUser.get());
            reservation.setAd(optionalAd.get());
            reservation.setCompany(optionalAd.get().getUser());
            reservation.setReviewStatus(ReviewStatus.FALSE);
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    public AdDetailsForClientDTO getAdDetailsByAdId(Long adId) {
        Optional<Ad> optionalAd = adRepository.findById(adId);
        AdDetailsForClientDTO adDetailsForClientDTO = new AdDetailsForClientDTO();
        if (optionalAd.isPresent()) {
            adDetailsForClientDTO.setAdDTO(optionalAd.get().getAdDto());
            List<ReviewDTO> reviewDTOs = reviewRepository.findAllByAdId(adId).stream().map(Review::getReviewDTO).collect(Collectors.toList());
            adDetailsForClientDTO.setReviewDTO(reviewDTOs);
        }
        return adDetailsForClientDTO;
    }

    public List<ReservationDTO> getAllBookingsByUserId(Long userId) {
        return this.reservationRepository.findAllByUserId(userId).stream().map(Reservation::getReservationDto).collect(Collectors.toList());
    }

    public boolean giveReview(ReviewDTO reviewDTO) {
        Optional<User> optionalUser = userRepository.findById(reviewDTO.getUserId());
        Optional<Reservation> optionalReservation = reservationRepository.findById(reviewDTO.getBookId());
        if (optionalReservation.isPresent() && optionalUser.isPresent()) {
            Review review = new Review();
            review.setReview(reviewDTO.getReview());
            review.setReviewDate(new Date());
            review.setRating(reviewDTO.getRating());
            review.setUser(optionalUser.get());
            review.setAd(optionalReservation.get().getAd());
            reviewRepository.save(review);
            Reservation booking = optionalReservation.get();
            booking.setReviewStatus(ReviewStatus.TRUE);
            reservationRepository.save(booking);
            //updating average rating:
            Long adId=review.getAd().getId();
            Optional<Ad> optionalAd=adRepository.findById(adId);
            Double averageRating=optionalAd.get().getAverageRating();
            Long rating=reviewDTO.getRating();
            if(averageRating==null)averageRating= rating.doubleValue();
            else averageRating=(Double) (rating.doubleValue() + averageRating)/2.0;
            optionalAd.get().setAverageRating(averageRating);
            adRepository.save(optionalAd.get());
            return true;
        }
        return false;
    }

    public Long getPriceByAdId(Long adId) {
        Long price = adRepository.findPriceByAdId(adId);
        return price;
    }

    public boolean payAmount(Long bookId,Long amount){
        Optional<Reservation> optionalReservation=reservationRepository.findById(bookId);
        if(optionalReservation.isPresent()){
            optionalReservation.get().setPaidAmount(amount);
            reservationRepository.save(optionalReservation.get());
            return true;
        }
        return false;
    }
}