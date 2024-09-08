package com.thePiyushPriyadarshi.springStarter.repositories;

import com.thePiyushPriyadarshi.springStarter.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
