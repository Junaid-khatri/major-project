package com.springboot.major_project.repository;

import com.springboot.major_project.entity.RefreshToken;
import com.springboot.major_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByName(String name);
    Optional<User>findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
    Optional<User> findByRefreshToken(RefreshToken refreshToken);
}
