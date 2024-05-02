package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Long> {
    List<Ad> findAllByUserId(Long userId);

    List<Ad> findAllByServiceNameContaining(String name);

    @Query(value = "select * from ads a order by a.price",nativeQuery = true)
    List<Ad> findAllAdsSortByPriceAsc();

    @Query(value = "select * from ads a order by a.price desc",nativeQuery = true)
    List<Ad> findAllAdsSortByPriceDesc();

    @Query(value = "select * from ads a order by a.average_rating",nativeQuery = true)
    List<Ad> findAllAdsSortByAverageRatingAsc();

    @Query(value = "select * from ads a order by a.average_rating desc",nativeQuery = true)
    List<Ad> findAllAdsSortByAverageRatingDesc();

    @Query(value = "select price from ads a where a.id=:adId",nativeQuery = true)
    Long findPriceByAdId(Long adId);
}
