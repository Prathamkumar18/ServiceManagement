package com.example.ServiceManagement.entity;

import com.example.ServiceManagement.dto.UserDto;
import com.example.ServiceManagement.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private String phone;
    private UserRole role;

    public UserDto getDto(){
        UserDto userDto=new UserDto();
        userDto.setId(id);
        userDto.setName(name);
        userDto.setLastname(lastname);
        userDto.setEmail(email);
        userDto.setRole(role);
        userDto.setPhone(phone);
        return userDto;
    }
}
