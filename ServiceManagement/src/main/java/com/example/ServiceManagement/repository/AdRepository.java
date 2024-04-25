package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.dto.AdDTO;
import com.example.ServiceManagement.entity.Ad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad,Long> {
    List<Ad> findAllByUserId(Long userId);
}
