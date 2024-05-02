package com.example.ServiceManagement.dto;

import com.example.ServiceManagement.entity.Ad;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.enums.ReservationStatus;
import com.example.ServiceManagement.enums.ReviewStatus;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Date;

@Data
public class ReservationDTO {
    private Long id;
    private Date bookDate;
    private String serviceName;
    private ReservationStatus reservationStatus;
    private ReviewStatus reviewStatus;
    private Long paidAmount;
    private Long userId;
    private String userName;
    private Long companyId;
    private Long adId;
}
