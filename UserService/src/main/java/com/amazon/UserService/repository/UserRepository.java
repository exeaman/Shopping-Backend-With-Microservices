package com.amazon.UserService.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.UserService.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    boolean existsByEmail(String email);
}
