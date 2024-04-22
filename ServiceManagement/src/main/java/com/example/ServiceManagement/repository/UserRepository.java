package com.example.ServiceManagement.repository;

import com.example.ServiceManagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Boolean findFirstByEmail(String email);
}
