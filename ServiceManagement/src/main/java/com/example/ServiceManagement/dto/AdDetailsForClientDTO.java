package com.example.ServiceManagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdDetailsForClientDTO {
    private AdDTO adDTO;
    private List<ReviewDTO> reviewDTO;
}
