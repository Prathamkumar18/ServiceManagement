package com.example.ServiceManagement.services.authentication;

import com.example.ServiceManagement.dto.SignupRequestDTO;
import com.example.ServiceManagement.dto.UserDto;

public interface AuthService {
    public UserDto signupClient(SignupRequestDTO signupRequestDTO);

    public UserDto signupCompany(SignupRequestDTO signupRequestDTO);

    public Boolean presentByEmail(String email);
}
