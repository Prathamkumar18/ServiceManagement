package com.example.ServiceManagement.services.authentication;

import com.example.ServiceManagement.dto.SignupRequestDTO;
import com.example.ServiceManagement.dto.UserDto;
import com.example.ServiceManagement.entity.User;
import com.example.ServiceManagement.enums.UserRole;
import com.example.ServiceManagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    public UserDto signupClient(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setLastname(signupRequestDTO.getLastname());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.CLIENT);
        return userRepository.save(user).getDto();
    }

    public UserDto signupCompany(SignupRequestDTO signupRequestDTO) {
        User user = new User();
        user.setName(signupRequestDTO.getName());
        user.setEmail(signupRequestDTO.getEmail());
        user.setPhone(signupRequestDTO.getPhone());
        user.setPassword(new BCryptPasswordEncoder().encode(signupRequestDTO.getPassword()));
        user.setRole(UserRole.COMPANY);
        return userRepository.save(user).getDto();
    }

    public Boolean presentByEmail(String email) {
        return userRepository.findByEmail(email) != null;
    }

}
